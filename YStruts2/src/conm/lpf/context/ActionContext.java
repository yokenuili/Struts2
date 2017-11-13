package conm.lpf.context;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import conm.lpf.stack.ValueStack;

/**
 * 数据中心
 * 
 * 获取以下对象
 * request
 * response
 * session
 * application
 * parameters：存储请求参数
 * ValueStack：值栈
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
		
		//准备域
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
		//将action压入栈顶
		vs.push(action);
		//将ValueStack放入request域中
		request.setAttribute(VALUESTACK, vs);
		//将ValueStack放入context域中
		context.put(VALUESTACK, vs);
		//为当前请求线程设置好ActionContext
		actionContext.set(this);
	}

	/**
	 * 
	 * @return 当前线程对应的ActionContext对象
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
	//所需对象放在Map里面，然后通过上面的常量值获取对应的对象
	public Map<String,String[]> getPatams(){
		return (Map<String,String[]>)context.get(PARAMETERS);
	}
	public ValueStack getValueStack() {
		return (ValueStack)context.get(VALUESTACK);
 	}
	
	
}
