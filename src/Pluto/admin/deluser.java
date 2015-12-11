package Pluto.admin;

import java.io.PrintWriter;

import org.apache.catalina.User;
import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import Pluto.DBConnection;
import Pluto.function;

public class deluser extends ActionSupport  {
	
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
			out.println(function.PlutoJump("操作错误!", "user.jsp"));
		}
		
		DBConnection conn = new DBConnection();
		
		String sql = "delete from user where id=" + id + "limit 1";
		boolean del = conn.execute(sql);
		
		if (del) {
			out.println(function.PlutoJump("删除成功", "user.jsp"));
		} else {
			out.println(function.PlutoJump("删除失败", "user.jsp"));
		}
		return null;
	}
}








