<%@ page language="java" import="javax.servlet.http.HttpSession" contentType="text/html; charset=utf-8"
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
<title>修改信息</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/ChangeInfo.css">

<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script type="text/javascript">

function judge(){
	var i=${sessionScope.user.uType};
	if(i==0){
		window.location.href="jsp/userInfo.jsp";
	}else if(i==2){
		window.location.href="jsp/adminInfo.jsp";
	}else{
		window.location.href="jsp/saleInfo.jsp";
	}
}

function change(){
	var uId="${sessionScope.user.uId}";
	var uName=$("#uName").val();
	var uPsw=$("#uPsw").val();
	var uPhone=$("#uPhone").val();
	var uSex=$('input:radio[name="uSex"]:checked').val();
	var uEmail=$("#uEmail").val();
	var uIntroduce=$("#uIntroduce").val();
	var uType=${sessionScope.user.uType};
	if(uEmail==""){
		alert("邮箱不能为空！");
		return false;
	}else if(uEmail.indexOf("@")==-1){
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
	}else{
		$.ajax({
			url: 'ChangeInfoServlet',
			type: 'GET',
			data:
			{
				uId: uId,
				uName: uName,
				uPsw: uPsw,
				uPhone: uPhone,
				uSex: uSex,
				uEmail: uEmail,
				uIntroduce: uIntroduce,
				uType: uType,
				operation: "us",
			},
			dataType: 'json',
			success:function(flag)
			{
				if(flag==1){
					alert("修改成功！返回到个人页面！")
					if(uType==2){
						window.location.href="jsp/adminInfo.jsp";
					}else if(uType==0){
						window.location.href="jsp/userInfo.jsp";
					}else{
						window.location.href="jsp/saleInfo.jsp";
					}
				}else{
					alert("未知错误，请刷新重试 ！")
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

<div class="top">
    <ul>
    	<li><a onclick="judge()">返回个人中心</a></li>  
    </ul>
</div>


<div class="form-item">
	<div class="logo"></div>
	性别：<input type="radio" class="uSex" name="uSex" value="man" checked="checked">男
	<input type="radio" class="uSex" name="uSex" value="women">女<br>
	用户名&nbsp;&nbsp;&nbsp;：<input type="text" class="input1" id="uName" value="${sessionScope.user.uName}"><br>
	邮箱&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：<input type="text" class="input1" id="uEmail" value="${sessionScope.user.uEmail}"><br>
	简介&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：<input type="text" class="input1" id="uIntroduce"  value="${sessionScope.user.uIntroduce}"><br>
	电话号码：<input type="text" class="input1" id="uPhone"  value="${sessionScope.user.uPhone}" ><br>
	密码&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;：<input type="text" class="input1" id="uPsw" value="${sessionScope.user.uPsw}"><br>
	
	<button type="button" onclick="change()">修改完成</button>
	<button type="button" onclick="judge()">返回</button>
</div>

</body>
</html>