package conm.lpf.action;

import conm.lpf.context.ActionContext;
/**
 * Action类  用于测试struts2的功能可行性
 * 
 * @author yoke
 *
 */
public class HelloAction {

	private String name;
	private Integer year;
	
	public String execute(){
		//将year放到request域中  
		ActionContext.getContext().getRequest().setAttribute("year", year);
		//将name放到request域中
		ActionContext.getContext().getRequest().setAttribute("name", name);
		//将Yoke字符串放到request域中
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
	