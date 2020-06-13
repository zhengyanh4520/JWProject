package jWeb.rand;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class randNo {
	public String createOtherNo() {
		String No="";
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		//随机数
		Random r = new Random();
		//当前时间
		Date now = new Date();
		//当前时间中穿插46个字母中两个随机字母组成16位主键
		SimpleDateFormat f = new SimpleDateFormat("yyyyMM'"+str.charAt(r.nextInt(str.length()))+"'dd'"+
		str.charAt(r.nextInt(str.length()))+"'HHmmss");
		No=f.format(now);
		return No;
	}
}
