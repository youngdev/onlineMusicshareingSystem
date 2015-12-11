package Pluto;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.jspsmart.upload.Request;

public class upload {
	
	private String title;
	private String singer;
	private String special;
	private String path;
	private String value;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSinger() {
		return singer;
	}
	public void setSinger(String singer) {
		this.singer = singer;
	}
	public String getSpecial() {
		return special;
	}
	public void setSpecial(String special) {
		this.special = special;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public String execute() throws Exception {
		// 设置页面编码格式相关信息
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		HttpSession session = ServletActionContext.getRequest().getSession();
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String filePath = request.getParameter("path");
		if (function.isInvalid(title) || function.isInvalid(singer) ||
				function.isInvalid(special) || function.isInvalid(path)) {
			
			out.println(function.PlutoJump("everything could not be null", "upload.jsp?path=" + filePath));
		} else {
			//获取文件后缀
			filePath = filePath.replace("upload", "upload\\");
			DBConnection conn = new DBConnection();
			long time = new Date().getTime();
			
			if (conn.execute("insert into music(title,singer,special,value,time,click,url) values('"
							+ title
							+ "','"
							+ singer
							+ "','"
							+ special
							+ "','"
							+ value + "','" + time + "',0,'" + filePath + "')")) {
				//添加tip信息
				String tip = "[" + session.getAttribute("PlutoUser").toString()
						+ "] 分享了歌曲 [" + title + "]";
				conn.execute("insert into tip(value) values('" + tip + "')");
				out.println(function.PlutoJump("submit success", "index.jsp"));
			} else {
				out.println(function.PlutoJump("submit filed", "upload.jsp?path="
						+ filePath));
			}
		}
		return null;
	}
}





