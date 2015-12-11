package Pluto.admin;

import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import Pluto.DBConnection;
import Pluto.function;

@SuppressWarnings("serial")
public class login extends ActionSupport {
	
	private String adminName;
	
	private String adminPwd;

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminPwd() {
		return adminPwd;
	}

	public void setAdminPwd(String adminPwd) {
		this.adminPwd = adminPwd;
	}
	
	public String execute() throws Exception {
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		HttpSession session = ServletActionContext.getRequest().getSession();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control",
				"no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		if (function.isInvalid(adminName) || function.isInvalid(adminPwd)) {
			out.println(function.PlutoJump("用户名或密码不能为空", "index.jsp"));
		} else {
			session.setAttribute("PlutoAdmin", adminName);
			adminPwd = function.MD5Encode(adminName);
			DBConnection conn = new DBConnection();
			ResultSet rs = conn.executeQuery("select * from admin where name = '"
					+ session.getAttribute("PlutoAdmin").toString() + "'and pwd = '"
					+ adminPwd + "'");
			if (rs.next()) {
				out.println("<script language='javascript'>location.href='frame.jsp';</script>");
			} else {
				session.removeAttribute("PlutoAdmin");
				out.println(function.PlutoJump("用户名密码错误", "index.jsp"));
			}
		}
		return null;
	}
	
}










