<%@ page language="java"  import="java.util.*,jWeb.pojo.*" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<title>登录</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/Login.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script type="text/javascript">
function jump(){
	window.location.href="jsp/register.jsp";
}

function visitor(){
	window.location.href="jsp/visitor.jsp";
}

function onEnterDown(){
	alert("");
    if(window.event.keyCode == 13){
    	doLogin();
    }
}

function doLogin()
{
	var uId=$("#uId").val();
	var uPsw=$("#uPsw").val();
	var uType=$('input:radio[name="uType"]:checked').val();
	var ip=returnCitySN['cip'];
	var city=returnCitySN['cname'];
	$.ajax({
		url: 'LoginServlet',
		type: 'GET',
		data:
		{
			uId: uId,
			uPsw: uPsw,	
			uType: uType,
			ip: ip,
			city: city,
		},
		dataType: 'json',
		success:function(flag)
		{
			if(flag==1){
				$('.tip1').show();
			}else if(flag==2){
				$('.tip2').show();
			}else if(uType=='user'){
				window.location.href="jsp/userInfo.jsp";
			}else if(uType=='admin'){
				window.location.href="jsp/adminInfo.jsp";
			}else if(uType=='sale'){
				window.location.href="jsp/saleInfo.jsp";
			}
		},
		error:function()
		{
			alert("未知错误，请刷新重试！");
		}   		
	});
}

</script>



</head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>

<div class="form-item" οnkeydοwn="onEnterDown();">
	<div class="logo"></div>
	选择登录类型：<input type="radio" class="uType" value="user" name="uType" checked="checked">用户
				<input type="radio" class="uType" name="uType" value="sale">销售人员<br><br>
				<input type="radio" class="uType" name="uType" value="admin">管理员<br><br>
	
	<input type="text" class="input1" id="uId" placeholder="账号" ><br>
	<p class="tip1">该账号不存在</p>	
	<input type="password" class="input1" id="uPsw" placeholder="密码" ><br>
	<p class="tip2">密码不正确</p>
	<button type="button" onclick="doLogin()">登录</button>
	<button type="button" onclick="jump()">注册</button>
	<button type="button" onclick="visitor()">游客入口</button>
</div>

</body>
</html>