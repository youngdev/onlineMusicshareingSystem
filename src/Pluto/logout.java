package Pluto;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class logout extends ActionSupport {
	public String execute() throws IOException {
		// 设置页面编码格式相关信息
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		HttpSession session = ServletActionContext.getRequest().getSession();
		session.removeAttribute("PlutoUser");
		out.println(function.PlutoJump("用户注销成功", "index.jsp"));
		return null;
	}
}
