<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>系统索引基本信息</title>

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

		<script type="text/javascript">
//日历插件
$(function() {
	$(".datepicker").datepicker();
})


//查询索引信息
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
 
	window.location.href = "findIndex.action?searchMode.id="+${searchMode.id}
					+ "&searchMode.keyWords=" + keyWords
					+ "&rows=" + rows.value 
					+ "&page=" + page;
}

function setRows(rows) {
	var keyWords = ""+'${searchMode.keyWords}'; 
	keyWords = encodeURI(keyWords); 
 	keyWords = encodeURI(keyWords);

	window.location.href = "findIndex.action?searchMode.id="+${searchMode.id}
					+ "&searchMode.keyWords=" + keyWords
					+ "&rows=" + rows.value;
}
</script>

	</head>

	<body>
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>系统索引基本信息
			<b class="tip"></b>查询系统索引信息
		</div>

		<form action="findIndex.action?page=1&rows=10" id="form1"
			name="form1" method="post" enctype="multipart/form-data">

			<input type="hidden" id="rol" value="<s:property value="rol"/>" />
			<input type="hidden" id="totalRows"
				value="<s:property value="totalRows"/>" />
			<table class="table table-striped table-bordered table-condensed">
				<thead>
					<tr>
						<td colspan="99">
							系统索引信息查询
						</td>
					</tr>
				</thead>
				<tbody id="mytable">
					<tr>
						<td style="width: 60px;">
							索引编号
						</td>
						<td>
							<input name="searchMode.id" type="text" />
						</td>
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
							<a id="add" href="editToIndex.action?id=0" style=""
								class="btn btn-primary add">添加</a>
						</td>
						<td align="center">
							<a href="createIndex.action"  
								class="btn btn-primary add">更新索引信息</a> &nbsp;&nbsp;
						</td>
					</tr>
				</tfoot>
			</table>
		</form>

		<table class="table table-striped table-bordered table-condensed">
			<caption class="t_caption">
				系统索引信息查询结果
			</caption>
			<thead>
				<tr>
					<td>
						#
					</td>
					<td>
						索引编号
					</td>
					
					<td>
						蛋白质名称
					</td>
					<td>
						文件名称
					</td>
					<td>
						文件路径
					</td>
					<td>
						文件标题
					</td>
					<td>
						作者
					</td>
					<td>
						关键字
					</td>
					<td>
						摘要
					</td>
					<td>
						建立时间
					</td>
					<td>
						操作
					</td>
				</tr>
			</thead>
			<tbody>
				<s:if test="indexList.size() == 0">
					<tr>
						<td colspan="99" style="text-align: center;">
							没有查询到相关蛋白质索引信息！
						</td>
					</tr>
				</s:if>
				<s:iterator value="indexList" id="index" status="L">
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
							<s:property value="fileName" />
						</td>
						<td>
							<s:property value="path" />
						</td>
						<td>
							<s:property value="title" />
						</td>
						<td>
							<s:property value="author" />

						</td>
						<td>
							<s:property value="keyWord" />

						</td>
						<td>
							<s:property value="summary" />
						</td>
						<td>
							<s:property value="dateTime" />
						</td>
						<td>
						<span class="edit">
							<a class="btn btn-mini btn-primary"
								href="editToIndex.action?id=<s:property value="id"/>">
								修改 </a>
						</span>
								<span class="del">
							<a class="btn btn-mini btn-primary"
								href="deleteIndex.action?id=<s:property value="id"/>" onclick="return confirm('是否删除')">
								删除 </a></span>
						</td>
					</tr>
				</s:iterator>
			</tbody>
			<tfoot>
				<s:if test="indexList.size() > 0">
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
