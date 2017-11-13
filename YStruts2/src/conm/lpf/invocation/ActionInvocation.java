package conm.lpf.invocation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conm.lpf.config.ActionConfig;
import conm.lpf.context.ActionContext;

/**
 * 拦截链和action的调用类
 * @author yoke
 *
 */
public class ActionInvocation {

	//拦截器链 存放需要执行的拦截器，用Iterator类型是为了在后面调用的时候方便取出来
	private Iterator<Interceptor> interceptors;
	//即将要被调用的action实例  
	private Object action;
	//action配置信息 需要根据配置信息去调用action的方法
	private ActionConfig config;
	//数据中心ActionCOntext 本次请求的数据中心，为这几个数据都准备好。
	private ActionContext invocationContext;
	
	/**
	 * 
	 * @param interceptorClassNames 存放着拦截器链每个拦截器的全限定类名
	 * @param config需要调用的action的配置信息
	 * @param request
	 * @param response
	 */
	public ActionInvocation(List<String> interceptorClassNames,ActionConfig config,HttpServletRequest request,HttpServletResponse response) {
		
		//准备Interceptor链
		if(interceptorClassNames!=null&&interceptorClassNames.size()>0) {
			List<Interceptor> list = new ArrayList<Interceptor>();
			for(String className:interceptorClassNames) {
				try {
					Interceptor interceptor = (Interceptor) Class.forName(className).newInstance();
					interceptor.init();
					list.add(interceptor);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("创建Interceptor失败："+className);
				}
			}
			interceptors = list.iterator();
		}
		//准备action实例
		this.config = config;
		try {
			action = Class.forName(config.getClassName()).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("创建Action失败："+config.getClassName());
		}
		
		//准备数据中心ActionContext
		invocationContext = new ActionContext(request,response,action);
	}
	/**
	 * 调用拦截器连和action处理方法
	 * @param invocation
	 * @return
	 */
	public String invoke(ActionInvocation invocation) {
		//结果串
		String result = null;
		//判断拦截器链是否还有下一个拦截器或者违背拦截器拦截result不等于null的话说明已经有拦截器直接返回结果串）
		if(interceptors!=null&&interceptors.hasNext()&&result==null) {
			//有下一个拦截器，调用下一个拦截器的拦截方法
			Interceptor next = interceptors.next();
			result = next.intercept(invocation);
		}else {
			//没有下一个拦截器，调用action实例的处理方法
			//获取处理的方法名
			String methodName = config.getMethod();
			try {
				Method method = action.getClass().getMethod(methodName);
				result = (String)method.invoke(action);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("action方法调用失败："+methodName);
			}
		}
		return result;
		
	}
	
	public ActionContext getInvocationContext() {
	    return invocationContext;
	}
	
}
