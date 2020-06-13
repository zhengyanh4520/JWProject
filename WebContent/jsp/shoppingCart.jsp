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
<title>购物车</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/buyRecoding.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.0"></script>
<!-- 引入样式 -->
<link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
<!-- 引入组件库 -->
<script src="https://unpkg.com/element-ui/lib/index.js"></script>
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


</script>

</head>
<body>

<div class="top">
    <ul>
    	<li><a onclick="judge()">个人中心</a></li>
    </ul>
</div>

<div class="tab">
  <template>
    <el-table
      :data="recoding"
      style="width: 100%">
      <el-table-column
        prop="bId"
        label="记录编号"
        width="200">
      </el-table-column>
      <el-table-column
        prop="uId"
        label="账号"
        width="180">
      </el-table-column>
      <el-table-column
        prop="gName"
        label="商品名"
        width="180">
      </el-table-column>
      <el-table-column
        prop="gClass"
        label="商品类别"
        width="150">
      </el-table-column>
      <el-table-column
        prop="bTime"
        label="加入购物车时间"
        width="200">
      </el-table-column>
      <el-table-column
        prop="bNumber"
        label="购买数量"
        width="150">
      </el-table-column>
      <el-table-column
        prop="gPrice"
        label="购买单价"
        width="150">
      </el-table-column>
      <el-table-column
        prop="bPrice"
        label="总花费">
      </el-table-column>
      <el-table-column
      fixed="right"
      label="操作"
      width="100">
      <template slot-scope="scope">
        <el-button @click="buyGoods(scope.row)" type="text" size="big">购买</el-button>
        <el-button @click="deleteGoods(scope.row)" type="text" size="big" style="color:red">移除</el-button>
      </template>
    </el-table-column>
    </el-table>
  </template>
</div>
</body>

<script>
   new Vue({
	   el: '.tab',
      data() {
        return {
        	recoding: null
        }
      },
      methods:{
    	  buyGoods(row){
    		  var bId=row.bId;
    		  var uId=row.uId;
    		  var gId=row.gId;
    		  var bPrice=row.bPrice;
    		  var bNumber=row.bNumber;
    		  alert("本次购买共花费："+bPrice+"元，点击确定付款！")
    		  $.ajax({
      			url: 'BuyOrCartServlet',
      			type: 'GET',
      			data:
      			{
      				bId: bId,
      				uId: uId,
      				gId: gId,
      				bPrice: bPrice,
      				bNumber: bNumber
      			},
      			dataType: 'json',
      			success:function(data)
      			{
      				if(data==1){
      					alert("该商品目前已没有足够数量可供购买！请删除该购物车记录并返回商城重新购买！");
      				}else if(data==2){
      					alert("余额不足，请先充值！当前余额为："+"${sessionScope.user.uMoney}");
      				}else{
      					alert("购买成功！");
      					window.location.href="jsp/shoppingCart.jsp";
      				}
      			},
      			error:function()
      			{
      				alert("未知错误，请刷新重试！");
      			}   		
      		});
    	  },
    	  deleteGoods(row){
    		  var bId=row.bId;
    		  $.ajax({
      			url: 'DeleteCartServlet',
      			type: 'GET',
      			data:
      			{
      				bId: bId
      			},
      			dataType: 'json',
      			success:function(data)
      			{
      				if(data==1){
      					alert("移除成功！");
      					window.location.href="jsp/shoppingCart.jsp";
      				}else{
      					alert("未知错误，请刷新重试！");
      				}
      			},
      			error:function()
      			{
      				alert("未知错误，请刷新重试！");
      			}   	
    	  		});
      		},
      },
      mounted() {
    	  var uId="${sessionScope.user.uId}";
    	  var th=this;
    	  $.ajax({
    			url: 'LoadCartServlet',
    			type: 'GET',
    			data:
    			{
    				uId: uId
    			},
    			dataType: 'json',
    			success:function(data)
    			{
    				th.recoding=data;
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