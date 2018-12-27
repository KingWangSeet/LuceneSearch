package cn.edu.nwsuaf.Action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

//import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;


import cn.edu.nwsuaf.Service.Impl.RoleService;
import cn.edu.nwsuaf.Service.Impl.UserinfoService;
import cn.edu.nwsuaf.bean.User;


/**
 * 1.用户登录（） 2.用户注销
 * 
 * @author WangYong
 * @date 2016-4-30
 * 
 */
public class LoginAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2629017292622107950L;

	
	private UserinfoService userinfoService;
	private RoleService roleService;
	
	private User user;
	// page forward
	private String returnPage;
	private String message;
	
	// private List<String> funcRoleList;
	private List<User> userList;
	
	
	private String chknumber;
	/**
	 * 用户登录
	 * @return
	 */
	public String login(){
			//先判断用户名是否为空
		System.out.println("开始");
		String userId = user.getUserNo();
		System.out.println("用户名："+userId);
		String userPwd =  user.getUserPasswd();
		System.out.println("密码"+userPwd);
		int userRole =user.getRole().getRoleNo();
		System.out.println("角色"+userRole);
		boolean loginSuccess = false;
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		String code = (String) session.getAttribute("randomCode");
		
		if(userId!= null&&!userId.equals("")){
				//判断验证码是否正确
			//System.out.println("判断验证码是否正确:前台"+chknumber+"后台："+code+"结果："+code.equals(chknumber));
			User userinfo=new User();
			if(code.equals(chknumber)){
					userinfo =userinfoService.findById(User.class,userId);
					
					//判断用户是否存在
					if(userinfo == null){
						message = "用户名不存在";
					}
					else if(userinfo.getUserPasswd().equals(userPwd)){
						//判断角色是否匹配
						if(userinfo.getRole().getRoleNo()==userRole){
							if(userinfo.getRole().getRoleNo()==0){ //索引维护人员，用于索引的维护工作
								loginSuccess = true;	
								returnPage = "/protect/Sindex.jsp";
							}else if(userinfo.getRole().getRoleNo()==1){ //管理者人员，用于索引人员的管理
								loginSuccess = true;
								returnPage = "/protect/Mindex.jsp";
							}else {//对索引维护者和系统管理者的管理
								loginSuccess = true;	
								returnPage = "/protect/Cindex.jsp";
							}
						}else{
							message = "角色不匹配";
							return "error";
						}
						
					}else{
						message = "密码错误";
						return "error";
					}
						
				}else{
					message = "验证码错误";
					return "error";
				}
				System.out.println("登录转台："+loginSuccess);
				//登陆成功
				if(loginSuccess){
					user=userinfo;
					session.setAttribute("userInfo", user);
					session.setAttribute("sessionName", "userInfo");
					return "login";
				}
			}else{
				message = "用户名不能为空";
				return "error";
			}
			return "error";
	}
	
	/**
	 * 用户注销
	 * @return
	 */
	public String exit(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		session.removeAttribute("userInfo");
		session.removeAttribute("randomCode");
		return "exit";
	}

	public UserinfoService getUserinfoService() {
		return userinfoService;
	}

	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getReturnPage() {
		return returnPage;
	}

	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getChknumber() {
		return chknumber;
	}

	public void setChknumber(String chknumber) {
		this.chknumber = chknumber;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	
	
}
