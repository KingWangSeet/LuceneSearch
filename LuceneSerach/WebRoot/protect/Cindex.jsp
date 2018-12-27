<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>蛋白质实体信息检索后台系统</title>

		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/admin-all.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/base.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/jquery-1.7.2.js">
</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/jquery-ui-1.8.22.custom.min.js">
</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/index.js">
</script>

		<script type="text/JavaScript">
	function dispTime() {
		document.getElementById("clock").innerHTML = "__tag_31$47_"
				+ (new Date()).toLocaleString() + "__tag_32$38_";// 将时间加粗显示在clock的div中，new Date()获取系统时间   
	}
	function init() { // 启动时钟显示
		dispTime(); //显示时间
		window.setInterval(dispTime, 1000); // 过1秒
</script>
	</head>

	<body onload="init()">

		<div class="warp">
			<!--头部开始    超级管理员页面菜单-->
			<div class="top_c">
				<div class="top-menu">
					<ul class="top-menu-nav">
						<li>
							<a target="Conframe"
								href="${pageContext.request.contextPath}/page/common/main-t.jsp">超级管理员首页</a>
						</li>

						<li>
							<a href="#">基础数据管理<i class="tip-up"></i> </a>
							<ul class="kidc">
								<li>
									<b class="tip"></b><a target="Conframe"
										href="index/initProteinSearch.action?rol=2">蛋白质信息</a>
								</li>
								<li>
									<b class="tip"></b><a target="Conframe"
										href="index/initIndexSearch.action?rol=2">索引信息管理</a>
								</li>
								<li>
									<b class="tip"></b><a target="Conframe"
										href="user/initSearch.action?rol=2">人员信息管理</a>
								</li>
								
							</ul>
						</li>
						<li>
							<a href="#">统计管理<i class="tip-up"></i> </a>
							<ul class="kidc">
								<li>
									<b class="tip"></b><a target="Conframe" href="prevent.jsp">统计结果查询</a>
								</li>
							</ul>
						</li>
						<li>
							<a href="#">辅助信息<i class="tip-up"></i> </a>
							<ul class="kidc">
								<li>
									<b class="tip"></b><a target="Conframe"
										href="user/modifyUserPassword.action">用户信息修改</a>
								</li>
							</ul>
						</li>
					</ul>
				</div>
				<div class="top-nav">
					<div id="clock" style="display: inline;"></div>
					&nbsp;&nbsp;欢迎您，${userInfo.userName}！&nbsp;&nbsp;
					<a target="Conframe" href="user/modifyUserPassword.action">修改密码</a>
					|
					<a href="user_exit.action">安全退出</a>
				</div>
			</div>
			<!--头部结束-->
			<!--左边菜单开始-->
			<div class="left_c left">
				<h1>
					系统操作菜单
				</h1>
				<div class="acc">

					<div>
							<a class="one">基础信息管理</a>
							<ul class="kid">
								<li>
									<b class="tip"></b><a target="Conframe"
										href="index/initProteinSearch.action?rol=2">蛋白质信息</a>
								</li>
								<li>
									<b class="tip"></b><a target="Conframe"
										href="index/initIndexSearch.action?rol=2">索引信息管理</a>
								</li>
								<li>
									<b class="tip"></b><a target="Conframe"
										href="user/initSearch.action?rol=2">人员信息管理</a>
								</li>
							</ul>
					</div>
					<div>
						<a class="one">统计管理</a>
						<ul class="kid">
							<li>
								<b class="tip"></b><a target="Conframe" href="prevent.jsp">统计结果查询</a>
							</li>
						</ul>
					</div>
					<div>
						<a class="one">辅助信息</a>
						<ul class="kid">
							<li>
								<b class="tip"></b><a class="ti" target="Conframe"
									href="user/modifyUserPassword.action">用户信息修改</a>
							</li>
						</ul>
					</div>
					<!--<div id="datepicker"></div>-->
				</div>

			</div>
			<!--左边菜单结束-->
			<!--右边框架开始-->
			<div class="right_c">
				<div class="nav-tip" onclick=javascript:  (0);
>
					&nbsp;
				</div>

			</div>
			<div class="Conframe">
				<iframe name="Conframe" id="Conframe"
					src="${pageContext.request.contextPath}/page/common/main-t.jsp"></iframe>
			</div>
			<!--右边框架结束-->

			<!--底部开始-->
			<div class="bottom_c">
				Copyright &copy;2015 版权所有
			</div>
			<!--底部结束-->
		</div>
	</body>
</html>

