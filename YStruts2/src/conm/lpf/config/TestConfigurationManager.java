package conm.lpf.config;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import java.util.Set;

/**
 * ����Ŷ��ȡ�����ļ���ConfigurationManager
 * 
 * @author yoke
 *
 */
public class TestConfigurationManager {

	/**
	 * ���Զ�ȡconstant��ǩ��Ϣ
	 */
	@Test
	public void testGetConstant() {
		String extension = ConfigurationManager.getConstant("struts.action.extension");
		String encoding = ConfigurationManager.getConstant("struts.i18n.encoding");
		System.out.println("struts.action.extension = " + extension);
		System.out.println("struts.i18n.encoding = " + encoding);
	}

	/**
	 * ���Զ�ȡ��������Ϣ
	 */
	@Test
	public void testGetIntercpetors() {
		List<String> interceptors = ConfigurationManager.getInterceptors();
		for (String str : interceptors) {
			System.out.println(str);
		}
	}

	/**
	 * ���Զ�ȡaction��Ϣ
	 */
	@Test
	public void testGetActions() {

		Map<String, ActionConfig> actions = ConfigurationManager.getActions();
		Set<Entry<String, ActionConfig>> entrySet = actions.entrySet();
		for (Entry<String, ActionConfig> entry : entrySet) {
			System.out.println(entry.getKey() + " = " + entry.getValue());
		}
	}
	
}
