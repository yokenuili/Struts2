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
 * ����������
 * @author yoke
 *
 */
public class StrutsPrepareAndExecuteFilter implements Filter{

	//׼�������е�������Ϣ,���������¼�����Ա������
	//��Щ����ʹ��struts.xml�е�ȫ��������Ϣ��
	
	//�������������ÿ��������ȫ�޶�����
	private List<String> interceptors;
	//�����׺
	private String extension;
	//����action������Ϣ
	private Map<String,ActionConfig> actionConfigs;
	
	
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//��request��responseǿת��HttpServletRequest��HttpServletResponse
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse) response;
		//�������·��
		String reqPath = req.getServletPath();
		//�ж��Ƿ�Ҫ����action
		if(!reqPath.endsWith(extension)) {
			//����·������׺����ָ���ĺ�׺��   ������
			chain.doFilter(request, response);
			return;
		}else {
			//��ָ����׺�����ʣ�˵����Ҫ����action
			//��ȡ��Ҫ����action�����ƣ���Hello.action
			reqPath = reqPath.substring(1);
			reqPath = reqPath.replaceAll("."+extension, "");
			//����action��Ӧ��������Ϣ
//			System.out.println(reqPath);
			ActionConfig config = actionConfigs.get(reqPath);
			if(config==null) {
				//δ�ҵ���action��������Ϣ
				throw new RuntimeException("δ�ҵ�"+reqPath+"��Ӧ��·��");
			}
			//����ActionInvocation����ɶ���������2��action�ĵ���
			ActionInvocation invocation = new ActionInvocation(interceptors,config,req,resp);
			//��ý����
			String result = invocation.invoke(invocation);
			//���ݽ��������������Ϣ�еĶ�Ӧ·��
			String dispatcherPath = config.getResults().get(result);
			if(dispatcherPath==null||"".equals(dispatcherPath.trim())) {
				throw new RuntimeException("δ�ҵ�"+result+"��Ӧ��·��");
			}
			//����ת�����õ�·��
			req.getRequestDispatcher(dispatcherPath).forward(request, response);
			//�ͷ���Դ
			ActionContext.actionContext.remove();
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		//׼����������������Ϣ
		interceptors = ConfigurationManager.getInterceptors();
		//׼��constant������Ϣ�ķ��ʺ�׺��Ϣ
		extension = ConfigurationManager.getConstant("struts.action.extension");
		//action������Ϣ
		actionConfigs = ConfigurationManager.getActions();
	}

}
