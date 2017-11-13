package conm.lpf.filter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import conm.lpf.config.ActionConfig;
import conm.lpf.config.ConfigurationManager;
import conm.lpf.context.ActionContext;
import conm.lpf.invocation.ActionInvocation;
/**
 * 核心拦截器
 * @author yoke
 *
 */
public class StrutsPrepareAndExecuteFilter implements Filter{

	//准备好所有的配置信息,定义了以下几个成员变量，
	//这些对象就存放struts.xml中的全部配置信息。
	
	//存放拦截器链中每个拦截器全限定类名
	private List<String> interceptors;
	//请求后缀
	private String extension;
	//所有action配置信息
	private Map<String,ActionConfig> actionConfigs;
	
	
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//将request和response强转成HttpServletRequest和HttpServletResponse
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//获得请求路径
		String reqPath = req.getServletPath();
		//判断是否要访问action
		if(!reqPath.endsWith(extension)) {
			//请求路径名后缀不是指定的后缀名   ，方形
			chain.doFilter(request, response);
			return;
		}else {
			//以指定后缀名访问，说明需要访问action
			//获取需要访问action的名称，如Hello.action
			reqPath = reqPath.substring(1);
			reqPath = reqPath.replaceAll("."+extension, "");
			//查找action对应的配置信息
//			System.out.println(reqPath);
			ActionConfig config = actionConfigs.get(reqPath);
			if(config==null) {
				//未找到该action的配置信息
				throw new RuntimeException("未找到"+reqPath+"对应的路径");
			}
			//常见ActionInvocation，完成对拦截器链2和action的调用
			ActionInvocation invocation = new ActionInvocation(interceptors,config,req,resp);
			//获得结果串
			String result = invocation.invoke(invocation);
			//根据结果串查找配置信息中的对应路径
			String dispatcherPath = config.getResults().get(result);
			if(dispatcherPath==null||"".equals(dispatcherPath.trim())) {
				throw new RuntimeException("未找到"+result+"对应的路径");
			}
			//请求转发配置的路径
			req.getRequestDispatcher(dispatcherPath).forward(request, response);
			//释放资源
			ActionContext.actionContext.remove();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//准备拦截器链配置信息
		interceptors = ConfigurationManager.getInterceptors();
		//准备constant配置信息的访问后缀信息
		extension = ConfigurationManager.getConstant("struts.action.extension");
		//action配置信息
		actionConfigs = ConfigurationManager.getActions();
	}

}
