<%@ page language="java" contentType="text/html; charset=utf-8"
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
<title>注册</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/Register.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>

<script type="text/javascript">

function doRegister(){
	var reg=/^[a-zA-Z0-9_-]+@([a-zA-Z0-9]+\.)+(com|cn|net|org)$/;
	var uId=$("#uId").val();
	var uName=$("#uName").val();
	var uPsw=$("#uPsw").val();
	var uPsw2=$("#uPsw2").val();
	var uType=$('input:radio[name="uType"]:checked').val();
	var uPhone=$("#uPhone").val();
	var uSex=$('input:radio[name="uSex"]:checked').val();
	var uEmail=$("#uEmail").val();
	var uIntroduce=$("#uIntroduce").val();
	if(uId==""){
		alert("账号不能为空！");
		return false;
	}else if(uEmail==""){
		alert("邮箱不能为空！");
		return false;
	}else if(!(reg.test(uEmail))){
		alert("邮箱格式错误！");
		return false;
	}else if(uName==""){
		alert("用户名不能为空！");
		return false;
	}else if(uPhone==""){
		alert("电话号码不能为空！");
		return false;
	}else if(uPhone.length!=11){
		alert("电话号码长度有误！");
		return false;
	}else if(uPsw==""){//校验新密码
		alert("新密码不能为空！");
		return false;
	}else if(uPsw2==""){//校验确认密码
		alert("确认密码不能为空！");
		return false;
	}else if(uPsw!=uPsw2){//校验新密码和确认密码是否一致
		alert("两次密码不一致！");
		return false;
	}else{
		$.ajax({
			url: 'RegisterServlet',
			type: 'GET',
			data:
			{
				uId: uId,
				uName: uName,
				uPsw: uPsw,
				uType: uType,
				uPhone: uPhone,
				uSex: uSex,
				uEmail: uEmail,
				uIntroduce: uIntroduce
			},
			dataType: 'json',
			success:function(flag)
			{
				if(flag==1){
					alert("注册成功！跳转到登录页面！")
					window.location.href="jsp/Login.jsp";
				}else if(flag==0){
					alert("注册失败！已有重复账号！")
				}
			},
			error:function()
			{
				alert("未知错误，请刷新重试！");
			}   		
		});
	}
}
</script>

</head>
<body>
    
    
<div class="form-item">
	<div class="logo"></div>
	除简介以外各项不能为空！<br><br>
	注册类型：<input type="radio" class="uType" name="uType" value="user" checked="checked">用户
	
	<input type="text" class="input1" id="uId" placeholder="账号" ><br>
	<input type="text" class="input1" id="uEmail" placeholder="邮箱" ><br>
	<input type="text" class="input1" id="uName" placeholder="用户名" ><br>
	<input type="text" class="input1" id="uIntroduce" placeholder="简介" ><br>
	<input type="text" class="input1" id="uPhone" placeholder="电话号码" ><br>
	<input type="password" class="input1" id="uPsw" placeholder="密码" ><br>
	<input type="password" class="input1" id="uPsw2" placeholder="确认密码" ><br>
	
	性别：<input type="radio" class="uSex" name="uSex" value="man" checked="checked">男
	<input type="radio" class="uSex" name="uSex" value="women">女<br>
	
	<button type="button" onclick="doRegister()">注册完成</button>
	<button type="button" onclick="jumpLogin()">返回登录</button>
</div>


</body>
</html>