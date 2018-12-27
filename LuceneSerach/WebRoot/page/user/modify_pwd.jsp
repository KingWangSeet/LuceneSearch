<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>修改用户密码</title>

		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/admin-all.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/other.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/jquery-1.7.2.js">
</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/jquery-ui-1.8.22.custom.min.js">
</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/textarea.js">
</script>
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/tip.js">
</script>
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
		<script type="text/javascript" src="${pageContext.request.contextPath}/Scripts/ajax/MajorByDno.js">
</script>

		<script type="text/javascript">
			//日历插件
			$(function() {
				$(".datepicker").datepicker();
			})
			
			//验证输入的旧密码是否正确
			function onblurOldPwd(){
			var oldflag = false;
				var op = document.getElementById("oldPwd").value;
				var pwd = '<s:property value="user.userPasswd" />';
				if(op == pwd){
					document.getElementById("old").innerHTML = "输入旧密码正确";
					//document.getElementById("old").style = "color:red;font-size:12px;";
					oldflag = true;
				}else{
					document.getElementById("old").innerHTML = "输入旧密码错误";
					//document.getElementById("old").style = "color:red;font-size:12px;";
				}
				return oldflag;
			}
			//验证输入的新密码是否符合
			function onblurNewPwd1(){
			var newflag1 = false;
				var on1 = document.getElementById("newPwd").value;
				if(on1.toString().length <= 5 || on1.toString().length > 15){
					document.getElementById("new1").innerHTML = "输入新密码为6-15位字符";
					//document.getElementById("new1").style = "color:red;font-size:12px;";
				}else{
					document.getElementById("new1").innerHTML = "输入新密码正确";
					//document.getElementById("new1").style = "color:green;font-size:12px;";
					newflag1 = true;
				}
				//alert("newflag1"+newflag1);
				return newflag1;
			}
			//验证再次输入的密码是否与新密码相同
			function onblurNewPwd2(){
				var newflag2 = false;
				var on1 = document.getElementById("newPwd").value;
				var on2 = document.getElementById("newPwd2").value;
				if(on1 == on2 && on1.toString().length >=6 && on1.toString().length <= 15){
					document.getElementById("new2").innerHTML = "输入新密码正确";
					//document.getElementById("new2").style = "color:green;font-size:12px;";
					newflag2 = true;
				}else{
					document.getElementById("new2").innerHTML = "两次输入密码不同";
					//document.getElementById("new2").style = "color:red;font-size:12px;";
				}
				//alert("newflag2"+newflag2);
				return newflag2;
			}
			function modifyPwd(){
				var ol1=onblurOldPwd();
				var ol2=onblurOldPwd1();
				var ol3=onblurOldPwd2();
				alert("ol"+ol1+ol2+ol3);
			}
			
		</script>

	</head>

	<body onload="init()">
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>辅助信息
			<b class="tip"></b>修改用户密码
		</div>

		<form id="form1" action="updateUserPassword.action">
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<td colspan="4">
						用户密码修改
					</td>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="width: 60px;text-align: right;">
						用户账号
					</td>
					<td>
						<s:property value="user.userNo" />
					</td>
				</tr>
				<tr>
					<td style="width: 60px;text-align: right;">
						用户姓名
					</td>
					<td>
						<s:property value="user.userName" />
					</td>
				</tr>
				<tr>
					<td style="width: 60px;text-align: right;">
						请输入旧密码
					</td>
					<td>
						<input type="text" name="oldPwd" id="oldPwd" onblur="onblurOldPwd()"/>
						<span id="old"></span>
					</td>
				</tr>
				<tr>
					<td style="width: 60px;text-align: right;">
						请输入新密码
					</td>
					<td>
						<input type="text" name="newPwd" id="newPwd" onblur="onblurNewPwd1()"/>
						<span id="new1" style="font-size:12px;">(*6-15位字符)</span>
					</td>
				</tr>
				<tr>
					<td style="width: 60px;text-align: right;">
						再次输入新密码
					</td>
					<td>
						<input type="text" name="newPwd2" id="newPwd2" onblur="onblurNewPwd2()"/>
						<span id="new2"></span>
					</td>
				</tr>
				
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4">
						<a class="btn btn-primary add" 
							href="javascript:history.back(-1)">返回上一页</a>&nbsp;&nbsp;
						<s:submit class="btn btn-primary add" onclick="javascript:modifyPwd()" value="保存"></s:submit>&nbsp;&nbsp;
					</td>
				</tr>
			</tfoot>
		</table>
		</form>

	</body>
</html>
