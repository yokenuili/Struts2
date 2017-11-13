package conm.lpf.action;


/**
 * µÇÂ¼
 * @author yoke
 *
 */
public class LoginAction {
	
	private String name;
	private String password;
	

	public String login2() {
//		System.out.println("name="+name+"\npassword="+password);
		if("root".equals(name)&&"root".equals(password)) {
			return "success";
		}else {
			return "error";
		}
	}
	
	public String login() {
		return "success";
	}


	public String getName() {
		return name;
	}


	public String getPassword() {
		return password;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
