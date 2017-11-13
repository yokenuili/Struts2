package conm.lpf.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import conm.lpf.stack.ValueStack;

/**
 * ��������
 * 
 * ��ȡ���¶���
 * request
 * response
 * session
 * application
 * parameters���洢�������
 * ValueStack��ֵջ
 * 
 * @author yoke
 *
 */
public class ActionContext {
	
	public static final String REQUEST = "conm.lpf.request";
	public static final String RESPONSE = "conm.lpf.response";
	public static final String SESSION = "conm.lpf.session";
	public static final String APPLICATION = "conm.lpf.application";
	public static final String PARAMETERS = "conm.lpf.parameters";
	public static final String VALUESTACK = "conm.lpf.caluestack";

	private Map<String,Object> context;
	
	public static ThreadLocal<ActionContext> actionContext = new ThreadLocal<ActionContext>();
	
	public ActionContext(HttpServletRequest request,HttpServletResponse reqponse,Object action) {
		context = new HashMap<String,Object>();
		
		//׼����
		//request
		context.put(REQUEST, request);
		//response
		context.put(RESPONSE, reqponse);
		//session
		context.put(SESSION, request.getSession());
		//application
		context.put(APPLICATION, request.getSession().getServletContext());
		//parameters
		context.put(PARAMETERS,request.getParameterMap());
		//Valuestack
		ValueStack vs = new ValueStack();
		//��actionѹ��ջ��
		vs.push(action);
		//��ValueStack����request����
		request.setAttribute(VALUESTACK, vs);
		//��ValueStack����context����
		context.put(VALUESTACK, vs);
		//Ϊ��ǰ�����߳����ú�ActionContext
		actionContext.set(this);
	}

	/**
	 * 
	 * @return ��ǰ�̶߳�Ӧ��ActionContext����
	 */
	public static ActionContext getContext() {
		return actionContext.get();
	}

	public HttpServletRequest getRequest() {
		return (HttpServletRequest)context.get(REQUEST);
	}
	public HttpServletResponse getResponse() {
		return (HttpServletResponse)context.get(RESPONSE);
	}
	public HttpSession getSession() {
		return (HttpSession) context.get(SESSION);
	}
	public ServletContext getApplication() {
		return(ServletContext) context.get(APPLICATION);
	}
	//����������Map���棬Ȼ��ͨ������ĳ���ֵ��ȡ��Ӧ�Ķ���
	public Map<String,String[]> getPatams(){
		return (Map<String,String[]>)context.get(PARAMETERS);
	}
	public ValueStack getValueStack() {
		return (ValueStack)context.get(VALUESTACK);
 	}
	
	
}
