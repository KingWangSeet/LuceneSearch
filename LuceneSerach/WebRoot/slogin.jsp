<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>蛋白质实体信息检索后台登陆</title>
    
	
	<link rel="stylesheet" type="text/css" href="Styles/base.css" />
	<link rel="stylesheet" type="text/css" href="Styles/admin-all.css" />
	<link rel="stylesheet" type="text/css" href="Styles/bootstrap.min.css" />
	<link rel="stylesheet" href="Styles/css/public.css" />
    <link rel="stylesheet" href="Styles/css/style.css" />
    <link rel="stylesheet" href="Styles/css/login.css" />
    <script type="text/javascript" src="Scripts/jquery.min.js"></script>
    <script type="text/javascript" src="Scripts/jquery-1.9.1.js"></script>
    <script type="text/javascript" src="Scripts/jquery.js"></script>
    <script type="text/javascript" src="Scripts/superslide.2.1.js"></script>
    <script type="text/javascript" src="Scripts/jquery.easydropdown.min.js"></script>
	
	<style>
	.fielderror{
	color:green;
	}
	</style>
	<script type="text/javascript">
	       function reloadcode(obj,base){  
			  var rand=new Date().getTime(); //这里用当前时间作为参数加到url中，是为了使URL发生变化，这样验证码才会动态加载，  
			            //只是一个干扰作用，无确实意义，但却又非常巧妙，呵呵  
			  obj.src=base+"randomCode.action?abc="+rand; //其实服务器端是没有abc的字段的。  
		  } 
	    </script>
  </head>
  
  <body>
    
    <div class="loginbody">
        <div class="loginimg"> 
        </div>
        <form class="form" action="user_login.action" method="post">
            <h1><img src="img/pic1.png" /> 系统后台 </h1>
                <p>
                	<img src="img/user.png"/>：&nbsp;&nbsp;&nbsp;
                			<input type="text" name="user.userNo" id="uid" maxlength="32" placeholder="你的手机号/邮箱" onkeyup="value=value.replace(/\s/g,'')" onafterpaste="value=value.replace(/\s/g,'')">
                <script type="text/javascript">
                $("#uid").focusout(function(){
                    if($.trim($(this).val())==""){
                        $('.send1').find('span').html('用户名不能为空！');
                        $('.send1').fadeIn();
                    }
                    else if($.trim($(this).val()).length>0&&$.trim($(this).val()).length<6){
                        $('.send1').find('span').html('账号长度应在6到32个字符之间');
                        $('.send1').fadeIn();
                    }
                    else{                    
                            $('.send1').fadeOut();
                    }
                });
                
                </script>
                <div class="send send1">
                    <span></span>
                    <div class="arrow"></div>
                </div>
                </p>
                <p>
                 <img src="img/lock.png" />：&nbsp;&nbsp;&nbsp;
                 	<input type="password" name='user.userPasswd' id="pwd" maxlength="32" placeholder="密码" onkeyup="value=value.replace(/\s/g,'')" onpaste="return false;">
                  <script type="text/javascript">
                    $("#pwd").focusout(function(){
                    
                    if($.trim($(this).val())==""){
                        $('.send2').find('span').html('密码不能为空！');
                        $('.send2').fadeIn();
                    }
                    else if($.trim($(this).val()).length>0&&$.trim($(this).val()).length<6){
                        $('.send2').find('span').html('密码长度应在6到32个字符之间');
                        $('.send2').fadeIn();
                    }
                    else{                    
                            $('.send2').fadeOut();
                    }
                });
                 </script>
                <div class="send send2">
                    <span></span>
                    <div class="arrow"></div>
                </div>
                 <p>
	        	<label>类&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型&nbsp;&nbsp;
	        		<select class="select" name="user.role.roleNo">
		        		<option value="0" >索引维护者</option>
		        		<option value="1" >系统管理者</option>
		        		<option value="2" >超级管理员</option>
	        		</select>
	        	</label>
	        </p>
	        <p>效&nbsp;&nbsp;验&nbsp;&nbsp;码&nbsp;&nbsp;&nbsp;<input name="chknumber" type="text" id="code" maxlength="6" class="code" />
		            <img  title="看不清楚请点击这里"   class="imgcode" src="<%=basePath%>randomCode.action" onclick="reloadcode(this,'<%=basePath%>')"/>
	        </p>
	        <p class="tip">&nbsp;
	        		<s:property value="message"/>
	        </p>
	        <hr />
                <input type="checkbox" id="rem" name="remember"/>
            	<span id="rem1"> &nbsp;&nbsp;&nbsp;记住我</span>
            	<span id="rem2">不是自己的电脑上不要勾选此项</span>
            	
                <p>
                <input type="submit" id="loginbutton" name="submit" value="登录" >
                <a href="regist"><input type="button" id="registbutton"  value="注册"></a>
                </p>
            
            <div class="forget">
                <a href=""><span>忘记密码？</span></a>
            </div>
            <div class="nameistrue">
            <s:fielderror>
				<s:param>name</s:param>
			</s:fielderror>
            </div>
			<div class="passistrue">
			
			<s:fielderror>
				<s:param>password</s:param> 
			</s:fielderror>
			</div>
        </form>
        
    </div>
    
</body>
</html>