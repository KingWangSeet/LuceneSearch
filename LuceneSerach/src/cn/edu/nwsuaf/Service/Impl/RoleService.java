package cn.edu.nwsuaf.Service.Impl;

import java.util.List;

import cn.edu.nwsuaf.bean.Role;
import cn.edu.nwsuaf.dao.Impl.QueryUtilDaoImpl;
/***
 * Role基本数据表
 * @author WangYong
 *
 */

public class RoleService extends BaseServiceImpl<Role>{
		
		/***
		 * //查找所有角色数据
		 */
		@SuppressWarnings("unchecked")
		public List<Role> findallRoleList(int page, int rows) {
	
			String hql = "from Role";
			List<Role> list = (List<Role>) QueryUtilDaoImpl
					.executeQueryByPage(hql, null, page, rows);
			return list;
		}
		/****
		 * 查找角色
		 * @param page
		 * @param rows
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public List<Role> findAllRoleList(int role) {
			String hql="";
			
			if(role==1){
				 hql = "from Role role where role.roleNo=0";
			}else if(role==2){
				hql = "from Role role where role.roleNo=0 or role.roleNo=1";
			}
			
			List<Role> list = (List<Role>) QueryUtilDaoImpl.executeQuery(hql, null);
			return list;
		}
		/***
		 * 
		 * @param tpno
		 * @return
		 */
		public int isExist(int tpno) {
			
			int count=0;
			String hql = "select count(*) from Role as role where "
					+ "role.roleNo ="+ tpno;
			System.out.println(hql);
			count = QueryUtilDaoImpl.getResultCountForHql(hql, null, null);
			return count;
		}
}

