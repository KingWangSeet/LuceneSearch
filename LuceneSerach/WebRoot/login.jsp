<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>欢迎登录专业评估管理系统</title>

		<link rel="stylesheet" type="text/css" href="Styles/base.css" />
	    <link rel="stylesheet" type="text/css" href="Styles/admin-all.css" />
	    <link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css" />
	    <script type="text/javascript" src="Scripts/jquery-1.7.2.js"></script>
	    <script type="text/javascript" src="Scripts/jquery.spritely-0.6.js"></script>
	    <script type="text/javascript" src="Scripts/chur.min.js"></script>
	    <link rel="stylesheet" type="text/css" href="Styles/login.css" />
	    <script type="text/javascript">
	        $(function () {
	            $('#clouds').pan({ fps: 20, speed: 0.7, dir: 'right', depth: 10 });
	        })
	        
	       function reloadcode(obj,base){  
			  var rand=new Date().getTime(); //这里用当前时间作为参数加到url中，是为了使URL发生变化，这样验证码才会动态加载，  
			            //只是一个干扰作用，无确实意义，但却又非常巧妙，呵呵  
			  obj.src=base+"randomCode.action?abc="+rand; //其实服务器端是没有abc的字段的。  
		  } 
	    </script>
	</head>

	<body>
	
	<div id="clouds" class="stage"></div>
    <div class="loginmain"></div>

    <div class="row-fluid">
        <h1>蛋白质实体检索系统</h1>
        
        <form action="user_login.action" method="post">
	        <p>
	            <label>用&nbsp;户&nbsp;名&nbsp;&nbsp;
	            	<input type="text" id="uid" name="user.userNo"/>
	            </label>
	        </p>
	        <p>
	            <label>密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;&nbsp;
	            	<input type="password" id="pwd" name="user.userPasswd"/>
	            </label>
	        </p>
	        <p>
	        	<label>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型&nbsp;&nbsp;
	        		<select class="select" name="user.role.roleNo">
		        		<option value="0" >索引维护者</option>
		        		<option value="1" >系统管理者</option>
		        		<option value="2" >超级管理员</option>
	        		</select>
	        	</label>
	        </p>
	        <p class="pcode">
	            <label>效&nbsp;验&nbsp;码&nbsp;&nbsp;
		            <input name="chknumber" type="text" id="code" maxlength="6" class="code" />
		            <img  title="看不清楚请点击这里"   class="imgcode" src="<%=basePath%>randomCode.action" onclick="reloadcode(this,'<%=basePath%>')"/>
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            </label>
	        </p>
	        <p class="tip">&nbsp;
	        		<s:property value="message"/>
	        </p>
	        <hr />
	       
	        <input type="submit" value=" 登 录 " class="btn btn-primary btn-large" />
	        &nbsp;&nbsp;&nbsp;
	        <input type="reset" value=" 重 置 " class="btn btn-large" />
    	</form>
    </div>
	
	</body>
</html>
