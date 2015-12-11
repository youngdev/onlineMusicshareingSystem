package Pluto.admin;

import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import Pluto.DBConnection;
import Pluto.function;

/**
 * 删除友情链接
 * 
 * @author Administrator
 * @date 2015年12月10日
 * @name delink.java
 */
public class dellink extends ActionSupport {

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String execute() throws Exception {
		// 设置页面编码格式相关信息
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		
		if (function.isInvalid(id)) {
			out.println(function.PlutoJump("出现错误!", "link.jsp"));
		} 
		
		DBConnection conn = new DBConnection();
		boolean del = conn.execute("delete from link where id=" + id + "limit 1");
		
		if (del) {
			out.println(function.PlutoJump("删除成功", "删除失败"));
		} else {
			out.println(function.PlutoJump("删除失败", "link.jsp"));
		}
		return null;
	}
}
