<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>系统识别的蛋白质实体信息</title>

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
	var keyWords = ""+'${searchMode.keyWords}'; 
	
	keyWords = encodeURI(keyWords); 
 	keyWords = encodeURI(keyWords);
 
	window.location.href = "findProtein.action?"
					+ "searchMode.keyWords=" + keyWords
					+ "&rows=" + rows.value 
					+ "&page=" + page;
}

function setRows(rows) {
	var keyWords = ""+'${searchMode.keyWords}'; 
	keyWords = encodeURI(keyWords); 
 	keyWords = encodeURI(keyWords);

	window.location.href = "findProtein.action?"
					+ "searchMode.keyWords=" + keyWords
					+ "&rows=" + rows.value;
}
</script>
	</head>

	<body>
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>系统识别蛋白质基本信息
			<b class="tip"></b>查询系统识别蛋白质信息
		</div>

		<form action="findProtein.action?page=1&rows=10" id="form1"
			name="form1" method="post" enctype="multipart/form-data">

			<input type="hidden" id="rol" value="<s:property value="rol"/>" />
			<input type="hidden" id="totalRows"
				value="<s:property value="totalRows"/>" />
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td colspan="99">
							系统识别蛋白质查询
						</td>
					</tr>
				</thead>
				<tbody id="mytable">
					<tr>
						<td style="width: 60px;">
							蛋白质名称
						</td>
						<td>
							<input name="searchMode.keyWords" type="text" />
						</td>
					</tr>


				</tbody>
				<tfoot>
					<tr>
						<td colspan="5">
							<a href="javascript:findContestApply();"
								class="btn btn-primary add">查询</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							
						</td>
					</tr>
				</tfoot>
			</table>
		</form>

		<table class="table table-striped table-bordered table-condensed">
			<caption class="t_caption">
				系统识别蛋白质信息查询结果
			</caption>
			<thead>
				<tr>
					<td>
						#
					</td>
					<td>
						识别蛋白质编号
					</td>
					
					<td>
						识别蛋白质名称
					</td>
					<td>
						操作
					</td>
				</tr>
			</thead>
			<tbody>
				<s:if test="proteinList.size() == 0">
					<tr>
						<td colspan="99" style="text-align: center;">
							没有查询到相关蛋白质索引信息！
						</td>
					</tr>
				</s:if>
				<s:iterator value="proteinList" id="protein" status="L">
					<tr>
						<td>
							<s:property value="#L.index+1" />
						</td>
						<td>
							<s:property value="id" />
						</td>
						<td>
							<s:property value="proteinName" />
						</td>
						<td>
						<span class="edit">
							<a class="btn btn-mini btn-primary"
								href="findIndex.action?searchMode.keyWords=<s:property value="proteinName"/>&page=1&rows=10">
								查看 </a>
						</span>
						</td>
					</tr>
				</s:iterator>
			</tbody>
			<tfoot>
				<s:if test="proteinList.size() > 0">
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
