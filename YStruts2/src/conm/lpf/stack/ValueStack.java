package conm.lpf.stack;

import java.util.Stack;

/**
 * 值栈对象，将action压入栈
 * 
 * @author yoke
 *
 */
public class ValueStack {

	private Stack<Object> stack = new Stack<Object>();
	
	//入栈
	public void push(Object o) {
		stack.push(o);
	}
	
	//出栈
	public Object pop() {
		return stack.pop();
	}
	
	//查看栈顶元素
	public Object peek() {
		return stack.peek();
	}
	
}
