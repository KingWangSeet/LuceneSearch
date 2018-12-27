/*package cn.edu.nwsuaf.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import cn.edu.nwsuaf.bean.College;
import cn.edu.nwsuaf.bean.Contestapply;
import cn.edu.nwsuaf.bean.Major;
import cn.edu.nwsuaf.service.CollegeService;
import cn.edu.nwsuaf.service.ContestApplyService;
import cn.edu.nwsuaf.service.MajorService;
import cn.edu.nwsuaf.service.impl.CollegeServiceImpl;
import cn.edu.nwsuaf.service.impl.ContestApplyServiceImpl;
import cn.edu.nwsuaf.service.impl.MajorServiceImpl;

public class ServiceHelper {
	
	private static CollegeService collegeService = new CollegeServiceImpl();
	private static MajorService majorService = new MajorServiceImpl();
	private static ContestApplyService contestApplyService = new ContestApplyServiceImpl();
	
	//设置教师赛项编号
	public String setContestNo(Integer cId, String clId,String ctId) {
		
		//生成赛项编号：TXM+年份+级别编号（2位）+类型编号（3位）+格式化Id（6）
		Calendar date=Calendar.getInstance();//获得系统当前日期
        Integer year=date.get(Calendar.YEAR);
        
		String contestNo = "T" + year.toString() + 
			formatContestLevelId(clId) +
			formatContestTypeId(ctId) +
			formatContestId(cId);
		return contestNo;
	}
	
	//设置学院赛项编号
	public String setContestNoC(Integer cId, String clId,String ctId) {
		
		//生成赛项编号：TXM+年份+级别编号（2位）+类型编号（3位）+格式化Id（6）
		Calendar date=Calendar.getInstance();//获得系统当前日期
        Integer year=date.get(Calendar.YEAR);
        
		String contestNo = "C" + year.toString() + 
			formatContestLevelId(clId) +
			formatContestTypeId(ctId) +
			formatContestId(cId);
		return contestNo;
	}
	
	//设置教务处赛项编号
	public String setContestNoS(Integer cId, String clId,String ctId) {
		
		//生成赛项编号：TXM+年份+级别编号（2位）+类型编号（3位）+格式化Id（6）
		Calendar date=Calendar.getInstance();//获得系统当前日期
        Integer year=date.get(Calendar.YEAR);
        
		String contestNo = "S" + year.toString() + 
			formatContestLevelId(clId) +
			formatContestTypeId(ctId) +
			formatContestId(cId);
		return contestNo;
	}
	
	//格式化赛项Id（contestId）
	public static String formatContestId(Integer contestId){
		String conId = contestId.toString();
		while(conId.length() < 6){
			conId = "0"+conId;
		}
		return conId;
	}
	
	//格式化赛项级别Id（contestLevelId）
	public static String formatContestLevelId(String contestLevelId){
		while(contestLevelId.length() < 2){
			contestLevelId = "0" + contestLevelId;
		}
		return contestLevelId;
	}
	
	//格式化赛项类型Id（contestTypeId）
	public static String formatContestTypeId(String contestTypeId){
		while(contestTypeId.length() < 3){
			contestTypeId = "0" + contestTypeId;
		}
		return contestTypeId;
	}
	
	
	
	public static List<Integer> findContestRegSetStuSum(Integer contestId){
		//返回学院应报名学生总数
		List<Integer> sumStuCollege = new ArrayList<Integer>();
		Contestapply contestApply = contestApplyService.findById(Contestapply.class, contestId);
		String regSet = contestApply.getContestRegSet().toString();
		String regYear = contestApply.getContestStuRegEdate().substring(2, 4);
		String regMonth = contestApply.getContestStuRegEdate().substring(5, 7);
		
		if(Integer.parseInt(regMonth) < 8){
			regYear = String.valueOf(Integer.parseInt(regYear)-1); //一年级
		}
		String[] grade = {regYear, String.valueOf(Integer.parseInt(regYear)-1), 
				String.valueOf(Integer.parseInt(regYear)-2), String.valueOf(Integer.parseInt(regYear)-3)};
		
		if(regSet != null){
			String[] rs = regSet.split(",");
			List<College> collegeList = collegeService.findAll(College.class);
			for(int i = 0; i < collegeList.size(); i++){
				int sumMajorStu = 0;
				List<Major> majorList = majorService.findByCollegeIdListMajor(collegeList.get(i).getCollegeId());
				for(int j = 0; j < majorList.size(); j++){
					for(int k = 0; k < rs.length; k++){
						if(majorList.get(j).getMajorId().equals(rs[k].substring(0, 3))){
							if(rs[k].endsWith("1")){
								String countHql = "SELECT COUNT(*) FROM Student AS s WHERE s.major.majorId = ?" +
									" AND s.studentClass LIKE :grade1";
								String[] param = {majorList.get(j).getMajorId()};
								Map<String, String> mapParam = new HashMap<String, String>();
								mapParam.put("grade1", grade[0]+"%");
								sumMajorStu += QueryUtil.getResultCountForHql(countHql, param, mapParam);
							}else if(rs[k].endsWith("2")){
								String countHql = "SELECT COUNT(*) FROM Student AS s WHERE s.major.majorId = ?" +
									" AND s.studentClass LIKE :grade2";
								String[] param = {majorList.get(j).getMajorId()};
								Map<String, String> mapParam = new HashMap<String, String>();
								mapParam.put("grade2", grade[1]+"%");
								sumMajorStu += QueryUtil.getResultCountForHql(countHql, param, mapParam);
							}else if(rs[k].endsWith("3")){
								String countHql = "SELECT COUNT(*) FROM Student AS s WHERE s.major.majorId = ?" +
									" AND s.studentClass LIKE :grade3";
								String[] param = {majorList.get(j).getMajorId()};
								Map<String, String> mapParam = new HashMap<String, String>();
								mapParam.put("grade3", grade[2]+"%");
								sumMajorStu += QueryUtil.getResultCountForHql(countHql, param, mapParam);
							}else if(rs[k].endsWith("4")){
								String countHql = "SELECT COUNT(*) FROM Student AS s WHERE s.major.majorId = ?" +
									" AND s.studentClass LIKE :grade4";
								String[] param = {majorList.get(j).getMajorId()};
								Map<String, String> mapParam = new HashMap<String, String>();
								mapParam.put("grade4", grade[3]+"%");
								sumMajorStu += QueryUtil.getResultCountForHql(countHql, param, mapParam);
							}
						}
					}
				}
				sumStuCollege.add(sumMajorStu);
			}
		}
		return sumStuCollege;
	} 
	
}
*/