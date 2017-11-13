package conm.lpf.action;

import conm.lpf.context.ActionContext;
/**
 * Action��  ���ڲ���struts2�Ĺ��ܿ�����
 * 
 * @author yoke
 *
 */
public class HelloAction {

	private String name;
	private Integer year;
	
	public String execute(){
		//��year�ŵ�request����  
		ActionContext.getContext().getRequest().setAttribute("year", year);
		//��name�ŵ�request����
		ActionContext.getContext().getRequest().setAttribute("name", name);
		//��Yoke�ַ����ŵ�request����
//		ActionContext.getContext().getRequest().setAttribute("name", "Yoke");
		return "success";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
	
}
	