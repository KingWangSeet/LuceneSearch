<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>


		<title>首页</title>

		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/admin-all.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/jquery-1.7.2.js">
		</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/jquery-ui-1.8.22.custom.min.js">
		</script>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />

		<link href="${pageContext.request.contextPath}/Styles/main/main.css"
			rel="stylesheet" type="text/css" />

	</head>

	<body>
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>首页
		</div>


		<div
			style="margin-bottom: 10px; margin-top: 10px; font-size: 14px; font-weight: bold;">
			<img src="../../img/main/person.png" alt="user"
				style="margin: 2px; padding-right: 5px; width: 24px; height: 24px;"></img>
			${userInfo.userName},${userInfo.role.roleName},您好！欢迎使用蛋白质实体信息检索系统
		</div>

		<div style="margin-bottom: 10px; margin-top: 5px;">
			<span style="font-size: 14px; font-weight: bold;"> <img
					src="../../img/main/dp.png"
					style="margin: 2px; padding-right: 5px; width: 24px; height: 24px;"></img>蛋白质实体信息检索系统使用指南</span>
			<br />
			<span style="font-size: 14px; margin-left: 40px;"><img
					src="../../img/main/flag_blue.png"></img>基本信息管理</span>
			<ul>
				<li>
					<img src="../../img/main/ulist.png"></img>
					<span style="font-weight: bold;">索引信息管理</span>&nbsp;&nbsp;(维护索引基本信息)
				</li>
				<li>
					<img src="../../img/main/ulist.png"></img>
					<span style="font-weight: bold;">人员信息管理</span>&nbsp;&nbsp;(维护索引维护人员及管理者基本信息)
				</li>
				
				
			</ul>
			<span style="font-size: 14px; margin-left: 40px;"><img
					src="../../img/main/flag_blue.png"></img>统计管理</span>
			<ul>
				<li>
					<img src="../../img/main/ulist.png"></img>
					<span style="font-weight: bold;">热词统计</span>&nbsp;&nbsp;(主要用于统计显示蛋白质的检索热度，可以显示每天，每周，及每月的热词信息)
				</li>
				<li>
					<img src="../../img/main/ulist.png"></img>
					<span style="font-weight: bold;">评估结果</span>&nbsp;&nbsp;(显示统计结果)
				</li>
			</ul>
		</div>
		<div
			style="margin-bottom: 10px; margin-top: 10px; font-size: 14px; font-weight: bold;">
			<img src="../../img/main/t05.png"
				style="width: 24px; height: 24px; margin: 2px; padding-right: 5px;"></img>
			密码修改
		</div>

	</body>
</html>
