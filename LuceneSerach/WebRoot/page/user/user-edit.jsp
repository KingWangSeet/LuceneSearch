<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>编辑用户信息</title>

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
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/ajax/user_uniqueness.js">
</script>
		
		<script type="text/javascript">
function modifyEdit() {
	if (document.getElementById("userNoError").innerHTML== "该用户已存在") {
		alert("请完善用户信息");
	} else {
		if (document.getElementById("userNo").value != "") {
			if (document.getElementById("userName").value != "") {
			   if(document.getElementById("userPasswd").value != ""){
			   		 if(document.getElementById("roleNo").value != ""){
			   		 	if (confirm("是否保存")) {
						document.getElementById("edit").submit();
						}
			   		 }else{
			   		 	document.getElementById("roleNo").focus();
			   			document.getElementById("rolErroe").innerHTML = "角色不能为空";
						document.getElementById("rolErroe").style.color = "red";
						alert("请选择用户角色");
			  		 }
			   }else{
			   		document.getElementById("userPasswd").focus();
			   		document.getElementById("pwdErroe").innerHTML = "用户密码不能为空";
					document.getElementById("pwdErroe").style.color = "red";
					alert("请输入用户密码");
			   }
				
			} else {
				document.getElementById("userName").focus();
				document.getElementById("nameErroe").innerHTML = "用户名不能为空";
				document.getElementById("nameErroe").style.color = "red";
				alert("请输入用户名");
			}
		} else {
			alert("请输入用户编号");
		}
	}
}
</script>
	</head>

	<body onload="init();">
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>查询用户信息
			<b class="tip"></b>编辑用户信息
		</div>

		<form action="editSysuserinfo.action?userNo=<s:property value="user.userNo" />" id="edit" method="post">


			<table class="table table-striped table-bordered table-condensed">
				<caption class="t_caption">
					用户信息编辑
				</caption>
				<thead>
					<tr>
						<td colspan="99">
							用户信息编辑
						</td>
					</tr>
				</thead>
				<tbody id="mytable">
					<tr>
						<td>
							用户编号
							<font color="red">*</font>
						</td>
						<td>
						
							<input type="text" name="user.userNo" id="userNo" value='<s:property value="user.userNo" />'
								onblur="useronblur();"/>
							<span id="userNoError"></span>
						</td>
						<td>
							用户名称
							<font color="red">*</font>
						</td>
						<td>
							<input type="text" name="user.userName" id="userName"
								value='<s:property value="user.userName" />' />
							<span id="nameErroe"></span>
						</td>
						<td>
							密码
							<font color="red">*</font>
						</td>
						<td>
							<input type="text" name="user.userPasswd" id="userPasswd"
								value='<s:property value="user.userPasswd" />' />
							<span id="pwdErroe"></span>
						</td>
					</tr>
					<tr>
						
						<td>
							角色
							<font color="red">*</font>
						</td>
						<td>
							<select name="user.role.roleNo" id="roleNo">
								<option value="">
									全部角色
								</option>
								<s:iterator value="roleList" var="role">
									<option value="<s:property value="roleNo"/>"
										<s:if test="#role.roleNo == user.role.roleNo">selected="selected"</s:if>>
										<s:property value="roleName" />
									</option>
								</s:iterator>
							</select>
							<span id="rolErroe"></span>
						</td>
						<td>
							备注
						</td>
						<td colspan="3">
							<input type="text" name="user.note" id="note"
								value='<s:property value="user.note" />' />
						</td>
					</tr>

				</tbody>
				<tfoot>
					<tr>
						<td colspan="99">
							<a class="btn btn-primary add" href="javascript:history.back(-1)">返回上一页</a>&nbsp;&nbsp;
							<a class="btn btn-primary add" href="javascript:modifyEdit()">保存</a>&nbsp;&nbsp;
							<span id="userError"></span>
						</td>

					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
