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
<title>购买记录</title>

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
    	<li><a onclick="judge()">返回个人中心</a></li>
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
        width="200">
      </el-table-column>
      <el-table-column
        prop="gName"
        label="商品名"
        width="200">
      </el-table-column>
      <el-table-column
        prop="gId"
        label="所属商家"
        width="200">
      </el-table-column>
      <el-table-column
        prop="gClass"
        label="商品类别"
        width="150">
      </el-table-column>
      <el-table-column
        prop="bTime"
        label="购买时间"
        width="200">
      </el-table-column>
      <el-table-column
        prop="bPrice"
        label="购买单价"
        width="150">
      </el-table-column>
      <el-table-column
        prop="bNumber"
        label="购买数量"
        width="150">
      </el-table-column>
      <el-table-column
        prop="bPrice"
        label="总花费">
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
      mounted() {
    	  var uId="${sessionScope.user.uId}";
    	  var uType=${sessionScope.user.uType};
    	  var th=this;
    	  $.ajax({
    			url: 'LoadBuyServlet',
    			type: 'GET',
    			data:
    			{
    				uId: uId,
    				uType: uType
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