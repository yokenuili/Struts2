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
 * ��������action�ĵ�����
 * @author yoke
 *
 */
public class ActionInvocation {

	//�������� �����Ҫִ�е�����������Iterator������Ϊ���ں�����õ�ʱ�򷽱�ȡ����
	private Iterator<Interceptor> interceptors;
	//����Ҫ�����õ�actionʵ��  
	private Object action;
	//action������Ϣ ��Ҫ����������Ϣȥ����action�ķ���
	private ActionConfig config;
	//��������ActionCOntext ����������������ģ�Ϊ�⼸�����ݶ�׼���á�
	private ActionContext invocationContext;
	
	/**
	 * 
	 * @param interceptorClassNames �������������ÿ����������ȫ�޶�����
	 * @param config��Ҫ���õ�action��������Ϣ
	 * @param request
	 * @param response
	 */
	public ActionInvocation(List<String> interceptorClassNames,ActionConfig config,HttpServletRequest request,HttpServletResponse response) {
		
		//׼��Interceptor��
		if(interceptorClassNames!=null&&interceptorClassNames.size()>0) {
			List<Interceptor> list = new ArrayList<Interceptor>();
			for(String className:interceptorClassNames) {
				try {
					Interceptor interceptor = (Interceptor) Class.forName(className).newInstance();
					interceptor.init();
					list.add(interceptor);
				} catch (Exception e) {
					e.printStackTrace();
					throw new RuntimeException("����Interceptorʧ�ܣ�"+className);
				}
			}
			interceptors = list.iterator();
		}
		//׼��actionʵ��
		this.config = config;
		try {
			action = Class.forName(config.getClassName()).newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("����Actionʧ�ܣ�"+config.getClassName());
		}
		
		//׼����������ActionContext
		invocationContext = new ActionContext(request,response,action);
	}
	/**
	 * ��������������action������
	 * @param invocation
	 * @return
	 */
	public String invoke(ActionInvocation invocation) {
		//�����
		String result = null;
		//�ж����������Ƿ�����һ������������Υ������������result������null�Ļ�˵���Ѿ���������ֱ�ӷ��ؽ������
		if(interceptors!=null&&interceptors.hasNext()&&result==null) {
			//����һ����������������һ�������������ط���
			Interceptor next = interceptors.next();
			result = next.intercept(invocation);
		}else {
			//û����һ��������������actionʵ���Ĵ�����
			//��ȡ����ķ�����
			String methodName = config.getMethod();
			try {
				Method method = action.getClass().getMethod(methodName);
				result = (String)method.invoke(action);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new RuntimeException("action��������ʧ�ܣ�"+methodName);
			}
		}
		return result;
		
	}
	
	public ActionContext getInvocationContext() {
	    return invocationContext;
	}
	
}
