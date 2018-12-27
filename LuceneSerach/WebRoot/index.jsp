<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>蛋白质信息查询</title>

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
<script type="text/javascript" src="${pageContext.request.contextPath}/Scripts/ajax/MajorByDno.js"></script>

		<script type="text/javascript">

//查询
function findContestApply() {
	document.getElementById("form1").submit();
}

function jump(op) {
	if ("first" == op) {
		currentPage = 1;
	} else if ("up" == op) {
		currentPage = parseInt(currentPage) - 1;
	} else if ("down" == op) {
		currentPage = parseInt(currentPage) + 1;
	} else if ("last" == op) {
		currentPage = pages;
	} else {
		var jumpPage = parseInt(document.getElementById("jumpPage").value);
		if (jumpPage <= pages && jumpPage > 0)
			currentPage = jumpPage;
		else
			alert("超出页码范围");
	}
	var pageSize=parseInt(document.getElementById("select_plan_rows_id").value);
	var keywords = ""+'${searchMode.keyWords}'; 
	keywords = encodeURI(keywords); 
 	keywords = encodeURI(keywords);
	window.location.href = "find.action?searchMode.keyWords="+keywords+"&currentPage="+ currentPage+ "&pageSize=" + pageSize;
}

function setRows(pageSize) {
var keywords = ""+'${searchMode.keyWords}'; 
	keywords = encodeURI(keywords); 
 	keywords = encodeURI(keywords);
	window.location.href = "find.action?searchMode.keyWords="+keywords+"&pageSize=" + pageSize.value;			
}
</script>

	</head>

	<body >
		<form action="find.action?currentPage=1&pageSize=10" id="form1" name="form1"
			method="post" enctype="multipart/form-data">
			<input type="hidden" id="rowCount"
				value="<s:property value="rowCount"/>" />
			<table class="table table-striped table-bordered table-condensed" align="right">
					<tr align="center">
						<td align="right" style="color:red;font-size:20px ">
							请输入检索关键字信息：<input size="25" type="text" name="searchMode.keyWords"  id="keyWords" />
						</td>
						<td colspan="2">
							<a href="javascript:findContestApply();"
								class="btn btn-primary add">查询信息</a>&nbsp;&nbsp;
						</td>
						<!--<td colspan="2">
							<a href="search/create.action"
								class="btn btn-primary add">创建索引</a>&nbsp;&nbsp;
						</td>
					--></tr>
			</table>
		</form>

		<table class="table table-striped table-bordered table-condensed">
		   <s:if test="rowCount>0">
			<caption class="t_caption">
				本次查询到相关的结果<s:property  value="rowCount"/>条,本次查找耗时<s:property  value="costSearchTime"/>毫秒
			</caption>
				<tr>
					<td>
						#
					</td>
     				<td>
					信息查询结果
					</td>
				</tr>
			</s:if>
			
			
			<tbody>
					<s:if test="dispaleList.size() == 0">
						<tr>
							<td colspan="99" style="text-align: center;">没有查询信息！</td>
						</tr>
					</s:if>
					<s:else>
					<s:iterator value="dispaleList" status="L" >
						<tr>
							<td>
								<s:property value="#L.index+1"/>	
							</td>
							
							<td>
								<a href='<s:property value="path"/>'>
									<s:property value="fileName"/>
									<s:property value="title"/>
									<s:property value="author"/>
									<s:property value="keyword"/>
									<s:property value="summary"/>
									<s:property value="score"/>
								</a>
							</td>
							<td>								
								<a class="btn btn-mini btn-primary" href='<s:property value="path"/>'>
									查看
								</a>
							</td>
						</tr>
					</s:iterator>
					</s:else>
			</tbody>
			<tfoot>
				<s:if test="dispaleList.size() > 0">
					<tr>
						<td colspan="99">
							<s:include value="paging.jsp"></s:include>
						</td>
					</tr>
				</s:if>
			</tfoot>
		</table>
	</body>
</html>
