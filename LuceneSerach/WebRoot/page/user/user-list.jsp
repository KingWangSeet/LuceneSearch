<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>系统用户信息</title>

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
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
		<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/ajax/UserMajorByDno.js">
</script>
		<script type="text/javascript">
$(document).ready(function() {
	$("#mytable tr:even td").css("background", "#fff");
	$("#mytable tr:even td").attr("bg", "#fff");
	$("#mytable tr:odd td").attr("bg", "#fff");
	$("#mytable tr td").hover(function() {
		$(this).parent().find("td").css("background", "#fff")
	}, function() {
		var bgc = $(this).attr("bg");
		$(this).parent().find("td").css("background", bgc)
	});
})

//查询
function findContestApply() {
	document.getElementById("form1").submit();
}

function jump(op) {
	if ("first" == op) {
		page = 1;
	} else if ("up" == op) {
		page = parseInt(page) - 1;
	} else if ("down" == op) {
		page = parseInt(page) + 1;
	} else if ("last" == op) {
		page = totalPage;
	} else {
		var jumpPage = parseInt(document.getElementById("jumpPage").value);
		if (jumpPage <= totalPage && jumpPage > 0)
			page = jumpPage;
		else
			alert("超出页码范围");
	}
	window.location.href = "find.action?usermodel.userNo=" + '${usermodel.userNo}'
			+ "&usermodel.userName=" + '${usermodel.userName}' 
			+ "&usermodel.userType=" + '${usermodel.userType}'
			+ "&rows=" + rows + "&page=" + page;
}

function setRows(rows) {
	window.location.href ="find.action?usermodel.userNo=" + '${usermodel.userNo}'
			+ "&usermodel.userName=" + '${usermodel.userName}' 
			+ "&usermodel.userType=" + '${usermodel.userType}'
			+ "&rows=" + rows.value;
}
</script>
	</head>

	<body>
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>系统用户列表
			<b class="tip"></b>查询用户信息
		</div>
		<form action="find.action?page=1&rows=10" id="form1" name="form1"
			method="post" enctype="multipart/form-data">


			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td colspan="99">
							用户信息查询
						</td>
					</tr>
				</thead>
				<tbody id="mytable">
					<tr>
						<td style="width: 60px;">
							用户编号
						</td>
						<td>
							<input type="text" name="usermodel.userNo"
								value='<s:property value="teachermodel.userNo"/>' />
						</td>
						<td style="width: 60px;">
							用户姓名
						</td>
						<td>
							<input type="text" name="usermodel.userName"
								value='<s:property value="usermodel.userName"/>' />
						</td>
						<td>
							角色名称
						</td>
						<td>
							<select name="usermodel.userType" id="role">
								<option value="">
									全部角色
								</option>
								<s:iterator value="roleList">
									<<option value="<s:property value="roleNo"/>">
										<s:property value="roleName" />
									</option>
								</s:iterator>
							</select>
						</td>
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">
							<a href="javascript:findContestApply();"
								class="btn btn-primary add">查询</a>
						</td>
						<td align="center">
							<a href="editToSysuserinfo.action?userNo=0" style=""
								class="btn btn-primary add">添加</a>
						</td>
					</tr>
				</tfoot>
			</table>
		</form>

		<table class="table table-striped table-bordered table-condensed">
			<caption class="t_caption">
				用户信息查询结果
			</caption>
			<thead>
				<tr>
					<td>
						#
					</td>
					<td>
						用户编号
					</td>
					<td>
						用户名
					</td>
					<td>
						密码
					</td>
					<td>
						角色
					</td>
					<td>
						备注
					</td>
					<td>
						操作
					</td>
				</tr>
			</thead>
			<tbody>
				<s:if test="userList.size() == 0">
					<tr>
						<td colspan="99" style="text-align: center;">
							没有查询到相关用户信息！
						</td>
					</tr>
				</s:if>
				<s:iterator value="userList" id="user" status="L">
					<tr>
						<td>
							<s:property value="#L.index+1" />
						</td>
						<td>
							<s:property value="userNo" />
						</td>
						<td>
							<s:property value="userName" />
						</td>
						<td>
							<s:property value="userPasswd" />
						</td>
						<td>
							<s:property value="role.roleName" />
						</td>
						<td>
							<s:property value="note" />
						</td>
						<td>
							<a class="btn btn-mini btn-primary"
								href="editToSysuserinfo.action?userNo=<s:property value="userNo"/>">
								修改 </a>
							<a class="btn btn-mini btn-primary"
								href="deleteSysuserinfo.action?userNo=<s:property value="userNo"/>">
								删除 </a>
						</td>
					</tr>
				</s:iterator>
			</tbody>
			<tfoot>
				<s:if test="userList.size() > 0">
					<tr>
						<td colspan="99">
							<s:include value="../common/paging.jsp"></s:include>
						</td>
					</tr>
				</s:if>
			</tfoot>
		</table>

	</body>
</html>
