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
<title>用户中心</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/userInfo.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="http://pv.sohu.com/cityjson?ie=utf-8"></script>
<script type="text/javascript">
function jumpLogin(){
	var uId="${sessionScope.user.uId}";
	var ip=returnCitySN['cip'];
	var city=returnCitySN['cname'];
	$.ajax({
		url: 'LoginOutServlet',
		type: 'GET',
		data:
		{
			uId: uId,
			ip: ip,
			city: city,
		},
		dataType: 'json',
		success:function(data)
		{
			if(data==1){
				alert("退出成功！");
				window.location.href="jsp/Login.jsp";
			}else{
				alert("未知错误！请重试！");
			}
		},
		error:function()
		{
			alert("未知错误，请刷新重试！");
		}   		
	});
}

function changeInfo(){
	window.location.href="jsp/changeInfo.jsp";
}

function recommend(){
	window.location.href="jsp/recommend.jsp";
}

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

function recharge(){
	var r=Math.random()*10000;
	r=Math.ceil(r);
	var uId="${sessionScope.user.uId}";
	var uMoney="${sessionScope.user.uMoney}";
	$.ajax({
		url: 'AddMoneyServlet',
		type: 'GET',
		data:
		{
			uId: uId,
			rand: r,
			uMoney: uMoney
		},
		dataType: 'json',
		success:function(data)
		{
			if(data==1){
				alert("充值成功！本次充值得到："+r+"元");
				window.location.href="jsp/userInfo.jsp";
			}else{
				alert("未知错误！请重试！");
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
<body>

<div class="top">
    <ul>
    	<li><a onclick="judge()">个人中心</a></li>
    	<li><a href="jsp/shoppingCart.jsp">购物车</a></li>
    	<li><a href="jsp/goodsCenter.jsp">商城</a></li>
    	<li><a href="jsp/buyRecoding.jsp">购买记录</a></li>
    	<li><a href="jsp/browseRecoding.jsp">浏览记录</a></li>
    	<li><a href="jsp/ipRecoding.jsp">登录/退出记录</a></li>
    	<li>
    		<button class="button1" type="button" onclick="jumpLogin()">退出</button>
    	</li>    
    </ul>
    
    
</div>


<div class="form-item">
	<div class="logo"></div>
	<p id="uName">${sessionScope.user.uName}</p>
	<p id="uSex">${sessionScope.user.uSex}</p>
	<p id="uIntroduce">简介：${sessionScope.user.uIntroduce}</p>
	<p class="p1" id="uEmail">邮箱：${sessionScope.user.uEmail}</p>
	<p class="p1" id="uPhone">电话：${sessionScope.user.uPhone}</p>
	<p class="p1" id="uMoney">余额：${sessionScope.user.uMoney}</p>
	
	<button type="button" onclick="changeInfo()">修改信息</button>
	<button type="button" onclick="recharge()">充值</button>
	<button type="button" onclick="recommend()">推荐系统</button>
</div>


</body>
</html>