<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
 

<style>
	/*分页*/
	.pagination{
		margin-top: -4px;
		margin-bottom: -16px;
		
	}
	.pagination ul {
		display: inline;
		margin: 0;
		padding:0;
	}
	.pagination ul li {
	  	display: inline;
	}
	.pagination ul li a, .pagination ul li span {
		float: left;
		padding: 2px 12px;
		line-height: 20px;
		text-decoration: none;
		border-top:none;
		border-bottom:none;
		border-left:none;
		margin-bottom: -4px;
	}
	.pagination ul li a:hover{
		color: red;
	}
</style>

<div class="pagination">
	<ul>
		<li class="disabled" >
			<a style="color: black;padding-left: 5px;">每页显示条数：
				<select id="select_plan_rows_id" onchange="setRows(this)" name="pageSize" style="margin-top: -3px;padding: 0px;height: 22px;width: 80px;border:1px solid #bac7d2;">
					<option value="1" <s:if test="pageSize == 1">selected="selected"</s:if>>1</option>
					<option value="2" <s:if test="pageSize == 2">selected="selected"</s:if>>2</option>
					<option value="5" <s:if test="pageSize == 5">selected="selected"</s:if>>5</option>
					<option value="10" <s:if test="pageSize == 10">selected="selected"</s:if>>10</option>
					<option value="20" <s:if test="pageSize == 20">selected="selected"</s:if>>20</option>
					<option value="30" <s:if test="pageSize == 30">selected="selected"</s:if>>30</option>
				</select>
			</a>
		</li>
		<li class="disabled">
			<a style="color: black;">共&nbsp;<s:property  value="rowCount"/>&nbsp;条记录</a>
		</li>
		<li class="disabled">
			<a style="color: black;">当前显示：&nbsp;<s:property value="currentPage"/>/<s:property value="pages"/>&nbsp;页</a>
		</li>

		<li id="pageOp1" class="first-child">
			<a id="href1" href="javascript:jump('first')">首页</a>
		</li>
		<li id="pageOp2">
			<a id="href2" href="javascript:jump('up')">上一页</a>
		</li>
		<li id="pageOp3">
			<a id="href3" href="javascript:jump('down')">下一页</a>
		</li>
		<li id="pageOp4" class="last-child">
			<a id="href4" href="javascript:jump('last')">尾页</a>
		</li>
		<li class="active">
			<span style="color:black;">跳转到
			<input id="jumpPage" name="currentPage" style="margin-top: -3px; width: 40px; height: 16px;ime-mode:disabled;border:1px solid #95CACA;" 
				onkeydown="if(event.keyCode==13) event.keyCode=9" onKeyPress="if ((event.keyCode<48 || event.keyCode>57)) 
				event.returnValue=false" />
			页</span>
		</li>
		<li>
			<a href="javascript:jump('jump')">跳转</a>
		</li>
	</ul>
</div>

<script type="text/javascript">
var rowCount = '<s:property value="rowCount"/>';
var currentPage = '<s:property value="currentPage"/>';
var pages = '<s:property value="pages"/>';
var pageSize = '<s:property value="pageSize"/>';
if(pages == 1){
	for(var i=1;i<=4;i++){
		document.getElementById("pageOp"+i).setAttribute("class",'disabled');
		document.getElementById("href"+i).removeAttribute("href");
	}
}
else if(currentPage == 1){
	for(var i=1;i<=2;i++){
		document.getElementById("pageOp"+i).setAttribute("class",'disabled');
		document.getElementById("href"+i).removeAttribute("href");
	}
}else if(currentPage == pages){
	for(var i=3;i<=4;i++){
		document.getElementById("pageOp"+i).setAttribute("class",'disabled');
		document.getElementById("href"+i).removeAttribute("href");
	}
}
</script>
