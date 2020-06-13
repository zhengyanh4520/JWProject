package jWeb.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.Gson;

import jWeb.pojo.Goods;
import jWeb.rand.randNo;
import jWeb.service.GoodsService;

/**
 * 添加商品
 */
@WebServlet("/AddGoodsServlet")
public class AddGoodsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//设置请求编码格式
		req.setCharacterEncoding("utf-8");
	//设置响应编码格式
		resp.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
	
	//创建DiskFileItemFactory工厂对象
		DiskFileItemFactory factory=new DiskFileItemFactory();
	//设置文件缓存目录，若不存在则新建
		String webRoot=this.getServletContext().getRealPath("");
		String tempPath=webRoot+"tempFolder";
		Goods goods=new Goods();
		String ip=(String) req.getSession().getAttribute("ip");
		String city=(String) req.getSession().getAttribute("city");
		randNo r=new randNo();
		goods.setgId(r.createOtherNo());
		File f=new File(tempPath);
		System.out.println(tempPath);
		if(!f.exists()) {
			f.mkdirs();
		}
	//设置文件缓存路径
		factory.setRepository(f);
	//创建servletFileUpload对象
		ServletFileUpload fu=new ServletFileUpload(factory);
	//设置字符编码
		fu.setHeaderEncoding("utf-8");
	try{
	//解析request得到上传文件的FileItem对象
		List<FileItem> fileItems=fu.parseRequest(req);
	//遍历集合
		//区分开文件还是普通字段
		System.out.println(fileItems.size());
		
		for(int i=0;i<fileItems.size();i++) {
			if(fileItems.get(i).isFormField()) {
				//普通字段FileItem		
				//获取请求数据uNo tNo content
				String s=fileItems.get(i).getString();
				//这里出现乱码iso-8859-1 与utf-8
				s=new String(s.getBytes("iso8859-1"),"utf-8");
				//参数待定
				if(i==0) {
					goods.setgName(s);
				}else if(i==1) {
					goods.setgDescribe(s);
				}else if(i==2) {
					goods.setgBelong(s);
				}else if(i==3) {
					goods.setgPrice(Integer.parseInt(s));
				}else if(i==4) {
					goods.setgNumber(Integer.parseInt(s));
				}else if(i==5) {
					goods.setgClass(s);
				}
				System.out.println(s);  	
			}else {
				//文件
				//获取上传文件名
				String format=fileItems.get(i).getName();
				//截取文件名后缀
				format=format.substring(format.lastIndexOf("."));
				//gUrl
				String url="pictures/goods/"+goods.getgId()+format;
				goods.setgUrl(url);
				//重新命名
				String webPath="";
				String fileName=goods.getgId()+format;
				webPath=webRoot+"pictures"+File.separator
								+"goods"+File.separator;
				//合并完整路径
				String filePath=webPath+fileName;
				System.out.println(filePath);
				//创建
				File file=new File(filePath);
				//若没有这句话表示直接创建filePath文件夹，而不是文件	
				file.getParentFile().mkdirs();
				file.createNewFile();
				//获得文件流
				InputStream in=fileItems.get(i).getInputStream();
				//打开服务器上传文件
				FileOutputStream out=new FileOutputStream(file);
				//对拷
				byte[] buffer=new byte[1024];
				int len;
				//读取
				while((len=in.read(buffer))>0) {
					out.write(buffer,0,len);
				}
				in.close();
				out.close();
				//删除零时文件
				fileItems.get(i).delete();	
				System.out.println("图片上传成功!");
				GoodsService g=new GoodsService();
				int flag=g.AddGoodS(goods,ip,city);
				resp.getWriter().write(new Gson().toJson(flag));	
			}
		}
	}catch(Exception e) {
			throw new RuntimeException(e);
	}
}
       


}
