package conm.lpf.interceptor;


import org.apache.commons.beanutils.BeanUtils;

import conm.lpf.context.ActionContext;
import conm.lpf.invocation.ActionInvocation;
import conm.lpf.invocation.Interceptor;
import conm.lpf.stack.ValueStack;
/**
 * �����������������������װ��action������
 * @author yoke
 *
 */
public class ParametersInterceptor implements Interceptor{

	@Override
	public void init() {
	}

	@Override
	public String intercept(ActionInvocation invocation) {
		ActionContext ac = invocation.getInvocationContext();
		ValueStack valueStack = ac.getValueStack();
		System.out.println("valueStack"+valueStack);
		//��ȡaction����
		Object action = valueStack.peek();
		System.out.println(ac.getRequest().getParameterMap());
		try {
			BeanUtils.populate(action, ac.getRequest().getParameterMap());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return invocation.invoke(invocation);
	}

	@Override
	public void destory() {
	}

	
}
