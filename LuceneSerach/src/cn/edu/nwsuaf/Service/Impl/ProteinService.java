package cn.edu.nwsuaf.Service.Impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.nwsuaf.Model.SearchMode;
import cn.edu.nwsuaf.bean.Protein;
import cn.edu.nwsuaf.dao.Impl.QueryUtilDaoImpl;
/***
 * Protein基本数据表
 * @author WangYong
 *
 */

public class ProteinService extends BaseServiceImpl<Protein>{
		
		/***
		 * 初始化查找所有蛋白质信息
		 */
		@SuppressWarnings("unchecked")
		public List<Protein> findallProteinList(int page, int rows) {
	
			String hql = "from Protein";
			List<Protein> list = (List<Protein>) QueryUtilDaoImpl
					.executeQueryByPage(hql, null, page, rows);
			return list;
		}
		/***
		 * 初始化蛋白质列表 查询结果条数，从数据库读取信息
		 * @return
		 */
		public int countFirst() {
			int count=0;	
			String hql="select count(*) from Protein";
			count =  QueryUtilDaoImpl.getResultCountForHql(hql, null);
			return count;
		}
		
		
		/***
		 * 提供给后台人员查看系统中已经识别的蛋白质信息，查看蛋白质信息列表
		 * @param smode
		 * @param page
		 * @param rows
		 * @return
		 */
		@SuppressWarnings("unchecked")
		public List<Protein> findIndexList(SearchMode smode,
				int page, int rows) {
			String hql = "from Protein as a  where a.proteinName like :name ";
			Map mapParam = new HashMap();
			mapParam.put("name", "%" + smode.getKeyWords() + "%");
			List<Protein> list = (List<Protein>) QueryUtilDaoImpl.executeQueryByPage(hql, null, mapParam, page, rows);
			return list;
		}
		/**
		 *  提供给后台查看系统已经识别的蛋白质信息列表
		 *  查询结果条数
		 *  @param smode
		 */
		@SuppressWarnings("unchecked")
		public int countFindIndex(SearchMode smode) {
			int count = 0;
			String hql = "select count(*) from Protein as a  where a.proteinName like :name ";
			Map mapParam = new HashMap();
			mapParam.put("name", "%" + smode.getKeyWords() + "%");
			count = QueryUtilDaoImpl.getResultCountForHql(hql, null, mapParam);
			//System.out.println("count=========" + count);
			return count;

		}
}

