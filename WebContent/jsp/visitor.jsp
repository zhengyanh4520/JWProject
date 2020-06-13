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
<title>百货中心商城</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/GoodsCenter.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.0"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<script type="text/javascript">

function judge(){
	window.location.href="jsp/Login.jsp";
}

</script>

</head>
<body>

<div class="top">
    <ul>
    	<li><a onclick="judge()">返回登录</a></li>
    </ul>
</div>

<!-- 商品 -->
<div id="gPart">
	<!-- 搜索 -->
	<div class="search">
	<input type="text" class="input1" id="gName" placeholder="搜索" >
	<button class="sButton" type="button" @click="doSearch()">搜索</button>
	</div>
	<div class="classSearch">
		<h2>分类</h2>
		<button class="classButton" type="button" @click="classSearch(1)" value="日常">日常</button>
		<button class="classButton" type="button" @click="classSearch(2)">办公</button>
		<button class="classButton" type="button" @click="classSearch(3)">家具</button>
		<button class="classButton" type="button" @click="classSearch(4)">电器</button>
		<button class="classButton" type="button" @click="classSearch(5)">娱乐</button>
		<button class="classButton" type="button" @click="classSearch(6)">美食</button>
		<button class="classButton" type="button" @click="classSearch(7)">学习</button>
		<button class="classButton" type="button" @click="classSearch(8)">运动</button>
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
        		alert("请先登录");
        	},
        	doSearch()
        	{
        		var gName=$("#gName").val();
        		var th=this;
        		$.ajax({
        			url: 'SearchGoodsServlet',
        			type: 'GET',
        			data:
        			{
        				gName: gName
        			},
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
        	},
        	classSearch(i)
        	{
        		var gClass="";
        		if(i==1){
        			gClass="日常";
        		}else if(i==2){
        			gClass="办公";
        		}else if(i==3){
        			gClass="家具";
        		}else if(i==4){
        			gClass="电器";
        		}else if(i==5){
        			gClass="娱乐";
        		}else if(i==6){
        			gClass="美食";
        		}else if(i==7){
        			gClass="学习";
        		}else if(i==8){
        			gClass="运动";
        		}
        		var th=this;
        		$.ajax({
        			url: 'SearchGoodsServlet',
        			type: 'GET',
        			data:
        			{
        				gClass: gClass
        			},
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
        },
        mounted() {
        	var th=this;
    		$.ajax({
    			url: 'LoadGoodsServlet',
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