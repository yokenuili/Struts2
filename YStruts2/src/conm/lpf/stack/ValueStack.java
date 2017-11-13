package conm.lpf.stack;

import java.util.Stack;

/**
 * ֵջ���󣬽�actionѹ��ջ
 * 
 * @author yoke
 *
 */
public class ValueStack {

	private Stack<Object> stack = new Stack<Object>();
	
	//��ջ
	public void push(Object o) {
		stack.push(o);
	}
	
	//��ջ
	public Object pop() {
		return stack.pop();
	}
	
	//�鿴ջ��Ԫ��
	public Object peek() {
		return stack.peek();
	}
	
}
