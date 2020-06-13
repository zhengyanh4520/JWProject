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
<title>浏览记录</title>

<!--引入文件  -->
<link rel="stylesheet" type="text/css" href="css/buyRecoding.css">
<script type="text/javascript" src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
<script src="https://cdn.jsdelivr.net/npm/vue@2.6.0"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
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

<div class="tab" style="margin: auto">
  <template>
    <el-table
      :data="salerList"
      style="width: 100%">
      <el-table-column
        prop="uId"
        label="销售人员账号"
        width="160">
      </el-table-column>
      <el-table-column
        prop="uName"
        label="用户名"
        width="160">
      </el-table-column>
      <el-table-column
        prop="uPsw"
        label="密码"
        width="150">
      </el-table-column>
      <el-table-column
        prop="uSex"
        label="性别"
        width="100">
      </el-table-column>
      <el-table-column
        prop="uIntroduce"
        label="简介"
        width="300">
      </el-table-column>
      <el-table-column
        prop="uEmail"
        label="邮箱"
        width="180">
      </el-table-column>
      <el-table-column
        prop="uPhone"
        label="电话"
        width="180">
      </el-table-column>
      <el-table-column
      label="操作"
      width="200px">
     <template slot-scope="scope">
        <el-button type="text" size="big" @click="change(scope.row)">修改信息</el-button>
		<el-button type="text" size="big" style="color:red" @click="deleteSaler(scope.row)">删除账户</el-button>

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
        	salerList: null,
        }
      },
      methods:{
    	  change(row){
    		    var uId=row.uId;
    			var uName=prompt("输入用户名:",row.uName);
    			var uPsw=prompt("输入密码:",row.uPsw);
    			var uPhone=prompt("输入电话:",row.uPhone);
    			var uEmail=prompt("输入邮箱:",row.uEmail);
    			var uIntroduce=prompt("输入简介:",row.uIntroduce);
    			var uType=1;
    			var uSex="women";
    			var operation="admin";
    			if(confirm("选择性别，是否为男性？")){
    				uSex="man";
    			}
    			alert("修改信息为:\n"
    					+"用户名："+uName+"\n"
    					+"密码："+uPsw+"\n"
    					+"电话："+uPhone+"\n"
    					+"邮箱："+uEmail+"\n"
    					+"简介："+uIntroduce+"\n"
    					+"性别："+uSex+"\n")
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
    						operation: operation,
    					},
    					dataType: 'json',
    					success:function(flag)
    					{
    						if(flag==1){
    							alert("修改成功")
    							location.reload();
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
    		},
      	deleteSaler(row){
    		if(confirm("删除后该账户名下商品及所有销售信息将会一并删除，是否确定删除此用户么？")){
    			var uId=row.uId;
              	$.ajax({
                	url: 'DeleteSalerServlet',
                	type: 'GET',
                	data:
                	{
                		uId: uId
                	},
                	dataType: 'json',
                	success:function(data)
                	{
                		if(data==1){
                			alert("删除账户成功！");
                			location.reload();
                		}else{
                			alert("未知错误，请刷新重试！");
                		}
                	},
                	error:function()
                	{
                		alert("未知错误，请刷新重试！");
                	}   	
              	  });
    		}else{
    			return;
    		}
        },
      },
      mounted() {
    	  var th=this;
    	  $.ajax({
    			url: 'GetSaleListServlet',
    			type: 'GET',
    			dataType: 'json',
    			success:function(data)
    			{
    				th.salerList=data;
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