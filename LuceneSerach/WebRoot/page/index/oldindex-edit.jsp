<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>

		<title>编辑系统索引信息</title>

		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/bootstrap.min.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/admin-all.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/other.css" />
		<link rel="stylesheet" type="text/css"
			href="${pageContext.request.contextPath}/Styles/ui-lightness/jquery-ui-1.8.22.custom.css" />
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
<script type="text/javascript"
			src="${pageContext.request.contextPath}/Scripts/cal.js">
			
</script>
		

<script type="text/javascript">
function modifyEdit(){
    if ((document.getElementById("proteinName").value.length ==0)
        ||(document.getElementById("proteinName").value==" ")){
	        alert("蛋白质实体不能为空");
			document.getElementById("proteinName").focus();
	}
    else if (confirm("是否保存")) {
			document.getElementById("editIndex").submit();
	}
}

</script>
</head>
<body onload="init();">
		<div class="alert alert-info">
			当前位置
			<b class="tip"></b>查询系统索引信息
			<b class="tip"></b>完善系统索引信息
		</div>

		<form action="editIndex.action?id=<s:property value="index.id"/>" id="editIndex" method="post">
			
			<table class="table table-striped table-bordered table-condensed">
				<caption class="t_caption">
					系统索引信息编辑
				</caption>
				<tbody id="mytable">
					<tr>
						<td style="width: 60px;">
							蛋白质名称：
							<font color="red">*</font>
						</td>
						<td>
							<input type="hidden"  id="id" name="index.id" value='<s:property value="index.id"/>'/>
							<input type="text"  id="proteinName" name="index.proteinName" value='<s:property value="index.proteinName"/>'
								<s:if test="index.proteinName !=''">readonly="readonly"</s:if>
							/>
						</td>
						<td style="width: 60px;">
							文件名称：
						</td>
						<td>
							<input type="text" id="fileName" name="index.fileName"  value='<s:property value="index.fileName"/>' 
								<s:if test="index.fileName !=''">readonly="readonly"</s:if>
							/>
						</td>
						<td >
							文件路径：
						</td>
						<td>
							<input style="width:800px;" type="text" id="path" name="index.path" value='<s:property value="index.path"/>'
								<s:if test="index.path !=''">readonly="readonly"</s:if>
							/>
						</td>
					</tr>
					<tr>
						<td style="width: 60px;">
							文件标题：
							
						</td>
						<td>
							<input type="text" id="title" name="index.title" value='<s:property value="index.title"/>'
								<s:if test="index.title !=''">readonly="readonly"</s:if>
							/>
						</td>
						<td style="width: 60px;">
							作者：
						</td>
						<td>
							<input type="text"  id="author" name="index.author"  value='<s:property value="index.author"/>'
								<s:if test="index.author !=''">readonly="readonly"</s:if>
							/>
						</td>
						<td style="width: 60px;">
							文件关键字：
							
						</td>
						<td>
							<input type="text"  id="keyWord" name="index.keyWord"  value='<s:property value="index.keyWord"/>'
								<s:if test="index.keyWord !='''">readonly="readonly"</s:if>
							/>
						</td>
						</tr>
					<tr>
						<td style="width: 60px;">
							创建时间：
							
						</td>
						<td>
							<input type="text" name="index.dateTime" id="dateTime" onclick="fPopCalendar(event,this,this)"  value='<fmt:formatDate pattern="yyyy-MM-dd" value="${index.dateTime}"/>'
								<s:if test="index.dateTime !=null">readonly="readonly"</s:if>
							/>
						</td>
						<td style="width: 120px;">
							摘要：
							
						</td>
						<td>
							<input type="text" id="summary" name="index.summary" value='<s:property value="index.summary" />' 
								<s:if test="index.summary !=''">readonly="readonly"</s:if>
							/>
						</td>
						
					</tr>
				</tbody>
				<tfoot>
					<tr>
						<td colspan="99" >
							<a class="btn btn-primary add" href="javascript:history.back(-1)">返回上一页</a>&nbsp;&nbsp;
							<a class="btn btn-primary add" id="save" href="javascript:modifyEdit()">保存</a>&nbsp;&nbsp;
						</td>
					</tr>
				</tfoot>
			</table>
		</form>
	</body>
</html>
