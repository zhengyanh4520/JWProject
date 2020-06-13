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
<title>用户推荐系统</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/GoodsCenter.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.0"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<script type="text/javascript">

function changeInfo(){
	window.location.href="jsp/changeInfo.jsp";
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
    	<li><a onclick="judge()">个人中心</a></li>
    	<li><a href="jsp/shoppingCart.jsp">购物车</a></li>
    </ul>
</div>

<!-- 商品 -->
<div id="gPart">
	<!-- 搜索 -->
	<div class="search">
		<h2>推荐系统的商品基于用户浏览过的商品内容</h2>
		<h2>选取与你相似的用户,将他们喜好的商品推荐给你</h2>
		<h2>如果你还未浏览过商品或浏览次数太少,推荐系统的内容即为商城内容</h2>
	</div>
	<div class="gList" v-for="(i,index) in goods">
		<img v-bind:src='i.gUrl' class="gImg">
		<div class="gInfo" >
			<h3 class="gName">{{i.gName}}</h3>
			<p class="gText">描述：{{i.gDescribe}}</p>
			<P class="gText">单价：{{i.gPrice}}</P>
		</div>
		<button class="allButton" type="button" @click="searchGoods(index)">查看详情</button>
	</div>
</div>



</body>

<script type="text/javascript">
   new Vue({
        el: '#gPart',
        data() {
            return{
            	goods: null,
            	readMore: false
            } 
        },
        methods: {
        	searchGoods(index)
        	{
        		var uId="${sessionScope.user.uId}";
        		var gId=this.goods[index].gId;
        		$.ajax({
        			url: 'SearchGoodsServlet',
        			type: 'GET',
        			data:
        			{
        				uId: uId,
        				gId: gId,
        			},
        			dataType: 'json',
        			success:function(data)
        			{
        				window.location.href="jsp/goodsDetails.jsp";
        			},
        			error:function()
        			{
        				alert("未知错误，请刷新重试！");
        			}   		
        		});
        	},
        	
        },
        mounted() {
        	var th=this;
    		$.ajax({
    			url: 'RecommendServlet',
    			type: 'GET',
    			dataType: 'json',
    			success:function(data)
    			{
    				th.goods=data;
    			},
    			error:function()
    			{
    				alert("未知错误，请刷新重试！");
    			}   		
    		});
        }
    })
</script>

</html>