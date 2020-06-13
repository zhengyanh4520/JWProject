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
<title>修改商品</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/AddGoods.css">

<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script type="text/javascript">

function jump(){
	window.location.href="jsp/manageGoods.jsp";
}

function changeGoods(){
	var gId="${sessionScope.goodsDetail.gId}";
	var gName=$("#gName").val();
	var gDescribe=$("#gDescribe").val();
	var gClass=$('input:radio[name="gClass"]:checked').val();
	var gPrice=$("#gPrice").val();
	var gNumber=$("#gNumber").val();
	var gBelong="${sessionScope.user.uId}";
	if(gName==""){
		alert("商品名不能为空！");
		return false;
	}else if(gDescribe==""){
		alert("商品描述不能为空！");
		return false;
	}else if(gPrice==""){
		alert("商品单价不能为空！");
		return false;
	}else if(!(/(^[0-9]*[1-9][0-9]*$)/.test(gPrice))){
		alert("商品单价必须为正整数！");
		return false;
	}else if(gNumber==""){//校验新密码
		alert("商品数量不能为空！");
		return false;
	}else if(!(/(^[0-9]*[1-9][0-9]*$)/.test(gNumber))){//校验新密码
		alert("商品数量必须为正整数！");
		return false;
	}else{

		var fd= new FormData();
		fd.append("gName",gName);
		fd.append("gDescribe",gDescribe);
		fd.append("gBelong",gBelong);
		fd.append("gPrice",gPrice);
		fd.append("gNumber",gNumber);
		fd.append("gClass",gClass);
		fd.append("gId",gId);
		var file=$("#myfile")[0].files[0];
		console.log(file);
		fd.append("myfile",file)
		$.ajax({
				url: 'ChangeGoodsServlet',
				type: 'POST',
				data: fd,
				cache : false,
		        contentType : false, 
		        processData : false,
				dataType: 'json',
				success: function(flag)
				{
					if(flag==1){
						alert("修改商品成功！返回到个人页面！")
						window.location.href="jsp/saleInfo.jsp";
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

</script>

</head>
<body>

<div class="top">
    <ul>
    	<li><a onclick="judge()">返回个人中心</a></li>
    </ul>
</div>


<div class="form-item">
			<div style="margin-bottom:15px; ">
			<img src="${sessionScope.goodsDetail.gUrl}" class="gImg" style="width:300px;height:180px;border-radius: 15px;">
			</div>
			商品类别：<input type="radio" class="gClass" name="gClass" value="日常" checked="checked">日常
			<input type="radio" class="gClass" name="gClass" value="办公">办公
			<input type="radio" class="gClass" name="gClass" value="家具">家具
			<input type="radio" class="gClass" name="gClass" value="电器">电器<br>
			<input type="radio" class="gClass" name="gClass" value="娱乐">娱乐
			<input type="radio" class="gClass" name="gClass" value="美食">美食
			<input type="radio" class="gClass" name="gClass" value="学习">学习
			<input type="radio" class="gClass" name="gClass" value="运动">运动<br>
	商品名称：<input type="text" class="input1" id="gName" value="${sessionScope.goodsDetail.gName}" ><br>
	商品描述：<input type="text" class="input1" id="gDescribe" value="${sessionScope.goodsDetail.gDescribe}"><br>
	商品单价：<input type="text" class="input1" id="gPrice"  value="${sessionScope.goodsDetail.gPrice}" ><br>
	商品数量：<input type="text" class="input1" id="gNumber" value="${sessionScope.goodsDetail.gNumber}"><br>
	商品图片：<input type="file" class="input1" id="myfile" name="myfile" placeholder="选择上传图片" ><br>
	<button type="button" onclick="changeGoods()">修改</button>
	<button type="button" onclick="jump()">返回</button>
</div>

</body>
</html>