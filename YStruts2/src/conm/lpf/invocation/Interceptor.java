package conm.lpf.invocation;
/**
 * �������ӿ�
 * @author yoke
 *
 */
public interface Interceptor {
	/**
 	* ִ����������ʼ������
 	*/
	public void init();
	/**
	 * ���ع��ܣ�������ǰ�������ִ��һЩ����
	 * @param invocation
	 * @return
	 */
	public String intercept(ActionInvocation invocation);
	/**
	 * ����������һЩ�ͷ���Դ����
	 */
	public void destory();
}
