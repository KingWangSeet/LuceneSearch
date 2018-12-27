package cn.edu.nwsuaf.Action;



import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;


import cn.edu.nwsuaf.Model.UserModel;
import cn.edu.nwsuaf.Service.Impl.RoleService;
import cn.edu.nwsuaf.Service.Impl.UserinfoService;
import cn.edu.nwsuaf.bean.Role;
import cn.edu.nwsuaf.bean.User;
import cn.edu.nwsuaf.exception.ServiceException;

/***
 * 用于人员基本信息的管理
 * @date 2016-4-30
 * @author WangYong
 *
 */
public class UserinfoAction {
	// Service
	private UserinfoService userinfoService;//用户信息表
	private RoleService roleService;//角色信息表
	
	// 传到前台下拉列表从数据库中读取显示
	private List<Role> roleList;//角色
	private List<User> userList;//用户列表
	
	// 前台获取属性
	private int page = 1;
	private int rows = 10;
	private int totalRows;
	private int totalPage;		
	
	private User user;
	private UserModel usermodel=new UserModel();		
	private int userNo;
	private int rol;
	
	private String returnPage;
	private String newPwd;
	
	//错误提示
	private String errstring;
	
	/***
	 * 登陆成功后获取Seession中的User
	 */
	public User getUserFromSession(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession();
		User user =(User) session.getAttribute("userInfo");
		return user;
	}
	/**
	 * 获取Session中的用户对象
	 * @return
	 */
	public String modifyUserPassword(){
		user =getUserFromSession();
		System.out.println("旧密码："+user.getUserPasswd());
		return "success";
	}
	
	/***
	 * 更新用户密码
	 */
	public String updateysuserinfo(){
		try {
			user=getUserFromSession();
			System.out.println("新密码是："+newPwd);
			user.setUserPasswd(newPwd);
			userinfoService.update(user);
			if(user.getRole().getRoleNo()==0){
				returnPage = "/Sindex.jsp";
			}else if(user.getRole().getRoleNo()==1){
				returnPage = "/Mindex.jsp";
			}else if(user.getRole().getRoleNo()==2){
				returnPage = "/Cindex.jsp";
			}else {
				returnPage = "/index.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}
	/***
	 * //初始化修改or添加页面
	 * @return
	 * @throws ServiceException
	 */

	public String initEdit() throws ServiceException{
		//user=getUserFromSession();
		//roleList = roleService.findAllRoleList(user.getRole().getRoleNo())	;
		roleList = roleService.findAll(Role.class);
		if (userNo == 0) {
			user=null;
		} else {
			user = userinfoService.findById(User.class,""+userNo);
		}	
		return "success";
	}
	/***
	 * //修改or添加
	 * @return
	 */
	
	public String modifiSysuserinfo(){
		try {
			//System.out.println("userNo"+userNo);
			List<User> s=new ArrayList<User>();
			s=userinfoService.findByHQL("from User where userNo='"+userNo+"'");
			if(s.size()==0){
				userinfoService.save(user);
			}else{
				//System.out.println(user.toString());
				userinfoService.update(user);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}
	/***
	 * 删除用户信息
	 * @return
	 */
	
	public String deleteUserinfo(){
		try {
			User deluser=userinfoService.findById(User.class, userNo);			
			System.out.println("RoleCode"+deluser.getRole().getRoleNo());
			userinfoService.delete(deluser);
		} catch (Exception e) {
			return "error";
		}
		
		return "success";
	}
	/***
	 * 初始化信息，用于页面显示数据库中用户信息
	 * @return
	 */
	
	public String initSearch() {
		
		try {
			page = 1;
			rows = 10;
			user=getUserFromSession();
			rol=user.getRole().getRoleNo();
			roleList = roleService.findAllRoleList(rol)	;
			userList = userinfoService.findallUserList(page, rows, rol);	
			totalRows =  userinfoService.count(rol);
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}
		return "success";

	}
	
	/***
	 * 查找用户信息
	 */
	public String find() {
		try {
			//System.out.println("Role:"+rol);
			user=getUserFromSession();
			rol=user.getRole().getRoleNo();
			//System.out.println("userRole:"+user.getRole().getRoleNo());
			roleList = roleService.findAllRoleList(rol)	;
			userList = userinfoService.findUserList(usermodel, page, rows,rol);
			totalRows = userinfoService.countFindUser(usermodel,rol);
			if (totalRows % rows == 0) {
				totalPage = totalRows / rows;
			} else {
				totalPage = totalRows == 0 ? 1 : (totalRows / rows + 1);
			}
			//System.out.println("page"+page);
		} catch (Exception e) {
			
			return "error";
		}
		return "success";
	}
	public UserinfoService getUserinfoService() {
		return userinfoService;
	}
	public void setUserinfoService(UserinfoService userinfoService) {
		this.userinfoService = userinfoService;
	}
	public List<Role> getRoleList() {
		return roleList;
	}
	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserModel getUsermodel() {
		return usermodel;
	}
	public void setUsermodel(UserModel usermodel) {
		this.usermodel = usermodel;
	}
	
	public int getRol() {
		return rol;
	}
	public void setRol(int rol) {
		this.rol = rol;
	}
	public String getReturnPage() {
		return returnPage;
	}
	public void setReturnPage(String returnPage) {
		this.returnPage = returnPage;
	}
	public String getNewPwd() {
		return newPwd;
	}
	public void setNewPwd(String newPwd) {
		this.newPwd = newPwd;
	}
	public void setErrstring(String errstring) {
		this.errstring = errstring;
	}
	public String getErrstring() {
		return errstring;
	}

	public RoleService getRoleService() {
		return roleService;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public int getUserNo() {
		return userNo;
	}

	public void setUserNo(int userNo) {
		this.userNo = userNo;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
}
