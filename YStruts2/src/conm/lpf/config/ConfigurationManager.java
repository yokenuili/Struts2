package conm.lpf.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 
 * ������ȡstruts.xml�����ļ�
 * 
 * @author yoke
 *
 */
public class ConfigurationManager {

	/**
	 * ��ȡstruts.xml�����Ե��ڴ�����name������constant��ǩ
	 * 
	 * @param name ƥ��content��ǩ��name����ֵ
	 * 
	 * @return ������ڷ���������content��ǩ�򷵻ظñ�ǩ��value����ֵ�����򷵻�null
	 */
	public static String getConstant(String name) {
		Document doc = getDocument();
		//XPath��䣬ѡȡ����nameֵΪname������ֵ��constant��ǩ
		String xpath = "//constant[@name='"+name+"']";
		Element con = (Element) doc.selectSingleNode(xpath);
		if(con!=null) {
			return con.attributeValue("value");
		}else {
			return null;
		}
	}

	/**
	 * ��ȡstrutx.xml������interceptor��ǩ����Ϣ
	 * 
	 * @return ����strutx.xml������interceptor��ǩ����Ϣ���б�
	 */
	public static List<String> getInterceptors() {
		List<String> interceptors = null;
		Document doc = getDocument();
		//XPath��䣬ѡȡ����interceptor��Ԫ�أ��������������ĵ��е�λ�á�
		String xpath = "//interceptor";
		List<Element> nodes = doc.selectNodes(xpath);
		if(nodes!=null&&nodes.size()>0) {
			interceptors = new ArrayList<String>();
			for(Element ele: nodes) {
				//���Class���Ե�ֵ
				String className = ele.attributeValue("class");
				interceptors.add(className);
			}
		}
		return interceptors;
	}

	/**
	 * ��ȡstruts.xml������action��ǩ����Ϣ
	 * @return ����struts.xml������action��ǩ����Ϣ��ӳ�䣬����keyΪaction��name
	 */
	public static Map<String,ActionConfig> getActions(){
		Map<String,ActionConfig> actionMap = null;
		Document doc = getDocument();
		//XPath��䣬ѡȡ����action��Ԫ�أ��������������ĵ��е�λ�á�
		String xpath = "//action";
		List<Element> nodes = doc.selectNodes(xpath);
		if(nodes!=null&&nodes.size()>0) {
			actionMap = new HashMap<String,ActionConfig>();
			for(Element ele: nodes) {
				ActionConfig ac = new ActionConfig();
				ac.setName(ele.attributeValue("name"));
				ac.setClassName(ele.attributeValue("class"));
				String method = ele.attributeValue("method");
				//�ж��Ƿ�����д��������û�еĻ���Ĭ��Ϊexecute
				method = (method==null||method.trim().equals(""))?"execute":method;
				ac.setMethod(method);
				//��ȡ��ǰaction��ǩ�µ�����result��ǩ
				List<Element> results = ele.elements("result");
				for(Element res:results) {
					ac.getResults().put(res.attributeValue("name"),res.getText());
				}
				actionMap.put(ac.getName(), ac);
			}
		}
		return actionMap;
	}
	/**
	 * ���������ļ�
	 * 
	 * @return ���������ļ���Ӧ���ĵ�Ŀ��
	 */
	private static Document getDocument() {
		//����������
		SAXReader reader = new SAXReader();
		//���������ļ�
		//ͨ��read������ȡһ���ļ� ת����Document����
		InputStream is = ConfigurationManager.class.getResourceAsStream("/struts.xml");
		Document doc = null;
		try {
			doc = reader.read(is);
		} catch (DocumentException e) {
			e.printStackTrace();
			throw new RuntimeException("�������ļ�����");
		}
		
		return doc;
	}
}
