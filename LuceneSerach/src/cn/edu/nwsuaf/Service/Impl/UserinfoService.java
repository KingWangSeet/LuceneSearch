package cn.edu.nwsuaf.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;




import cn.edu.nwsuaf.Model.UserModel;
import cn.edu.nwsuaf.bean.User;
import cn.edu.nwsuaf.dao.Impl.QueryUtilDaoImpl;
import cn.edu.nwsuaf.exception.ServiceException;

/***
 * user数据表的信息操作
 * @author WangYong
 * @date 2016-4-29
 */

public class UserinfoService extends BaseServiceImpl<User> {
	
	/**初始化查找所有索引维护者，此操作是超级管理员管理者的操作
	 * @param page ,rows ,roleNo 表示自身的角色
	 * @return List
	 * @author WangYong
	 * 
	 */
	@SuppressWarnings("unchecked")
	public List<User> findallUserList(int page, int rows,int roleNo) {
		String hql="";
		List<User> list =null ;
		if(roleNo==1){
			hql = "from User user where user.role.roleNo=0";
		}else if(roleNo==2){
			hql = "from User user where user.role.roleNo=1 or user.role.roleNo=0";
		}else{
			hql=" from User user";
			
		}
		hql += " order by user.userNo ASC ";
		list = (List<User>) QueryUtilDaoImpl.executeQueryByPage(hql, null, page, rows);
		System.out.println("list.size()==="+list.size());
		return list;
	}
	/***
	 * 修改用户密码
	 * @param User
	 */
	
	public void modifyPasswd(User User){
		try {
			this.update(User);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/***
	 * 多条件查询,查询条件有用户编号和用户名和用户角色   同时拒绝查询高级别的信息
	 * @param usermodel
	 * @param page
	 * @param rows
	 * @param role
	 * @return
	 */
	@SuppressWarnings("unchecked")
	
	public List<User> findUserList(UserModel usermodel,
			int page, int rows,int role) {
		String hql = "from User  userinfo where userinfo.role.roleNo < "+role 
			+" and userinfo.userNo like :tpno and "
			+ "userinfo.userName like :tpn";
		Map mapParam = new HashMap();
		// 用户编号
		//System.out.println("id="+usermodel.getUserNo()+"name"+usermodel.getUserName()+"end");
		mapParam.put("tpno", "%" + usermodel.getUserNo() + "%");
		//用户名
		mapParam.put("tpn", "%" + usermodel.getUserName() + "%");
		List<User> list=null;
		
		//用户类型
		if (usermodel.getUserType() != null) {
			hql += " and userinfo.role.roleNo ="
					+ usermodel.getUserType() ;
		}/*else if(role==2){
			hql += " and userinfo.role.roleNo =0 or userinfo.role.roleNo =1";
		}else if(role==1){
			hql += " and userinfo.role.roleNo =0 ";
		}*/
		hql += " order by userinfo.userNo ASC ";
		//System.out.println(hql);
		list = (List<User>) QueryUtilDaoImpl
				.executeQueryByPage(hql, null, mapParam, page, rows);
		//System.out.println(list.size());
		
		return list;

	}
	
	
	/***
	 * 多条件查询结果条数    同时拒绝查询高级别的信息
	 * @param UserModel
	 * @return 
	 */
	 
	@SuppressWarnings("unchecked")
	public int countFindUser(UserModel usermodel,int role) {
		int count;
		String hql = "select count(*) from User  userinfo "
			+ " where userinfo.userNo like :tpno and "
			+ "userinfo.userName like :tpn";
		Map mapParam = new HashMap();
		// 用户编号
		mapParam.put("tpno", "%" + usermodel.getUserNo() + "%");
		//用户名
		mapParam.put("tpn", "%" + usermodel.getUserName() + "%");
		
		//角色编号
		if (usermodel.getUserType() != null) {
			hql += " and userinfo.role.roleNo="
				+ usermodel.getUserType() ;
		}else if(role==2){
			hql += " and userinfo.role.roleNo =0 or userinfo.role.roleNo =1";
		}else if(role==1){
			hql += " and userinfo.role.roleNo =0 ";
		}
		
		//System.out.println(hql);
		count = QueryUtilDaoImpl.getResultCountForHql(hql,null, mapParam);
		System.out.println("count=========" + count);
		return count;

	}
	/***
	 * 检查用户是否存在
	 * @param userNo
	 * @return
	 */
	public int isExist(String userNo) {
		
		int count=0;
		String hql = "select count(*) from User as user where "
				+ "user.userNo= '"+ userNo+"'";
		count = QueryUtilDaoImpl.getResultCountForHql(hql, null, null);
		return count;
	}
	/***
	 * 初始化查询结果条数
	 * @param roleNo
	 * @return
	 * @throws ServiceException
	 */
	
	public int count(int roleNo)throws ServiceException {
		int count=0;	
		String hql="";
		if(roleNo==1){
			hql="select count(*) from User as user where user.role.roleNo=0";
			count =  QueryUtilDaoImpl.getResultCountForHql(hql, null);
		}else if(roleNo==2){
			hql="select count(*) from User as user where user.role.roleNo=0 or user.role.roleNo=1 ";
			count =  QueryUtilDaoImpl.getResultCountForHql(hql, null);
			
		}else{
			hql="select count(*) from User";
			count =  QueryUtilDaoImpl.getResultCountForHql(hql, null);
			
		}
		return count;

	}
	
}
