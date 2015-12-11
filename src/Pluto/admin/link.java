package Pluto.admin;

import java.io.PrintWriter;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import Pluto.DBConnection;
import Pluto.function;

public class link extends ActionSupport {
	
	private String title;
	
	private String value;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String execute() throws Exception {
		// 设置页面编码格式相关信息
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);

		if (function.isInvalid(title) || function.isInvalid(value)) {
			out.println(function.PlutoJump("请填入网站名称和地址!", "link.jsp"));
		}
		
		DBConnection conn = new DBConnection();
		
		String sql = "insert into link(title,value) values('"
				+ title + "','" + value + "')";
		boolean insert = conn.execute(sql);
		
		if (insert) {
			out.println(function.PlutoJump("添加成功", "link.jsp"));
		} else {
			out.println(function.PlutoJump("添加失败", "link.jsp"));
		}
		return null;
	}
}
