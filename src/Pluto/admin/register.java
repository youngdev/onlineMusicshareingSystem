package Pluto.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import Pluto.DBConnection;
import Pluto.function;

public class register extends ActionSupport {

	private String userName = null;
	private String userPwd = null;
	private String confirmPwd = null;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String execute() throws SQLException, IOException {
		//编码格式
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "No-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		//判断请求参数是否为空
		if (function.isInvalid(userName) || function.isInvalid(userPwd) || function.isInvalid(confirmPwd)) {
			out.println(function.PlutoJump("用户名或密码输入错误!", "new.jsp"));
		}
		//判断密码与确认密码是否一致
		if (!userPwd.equals(confirmPwd)) {
			out.println(function.PlutoJump("两次输入的密码不一致!", "new.jsp"));
		}
		//获取数据库连接
		DBConnection conn = new DBConnection();
		//判断注册用户名是否存在
		ResultSet rs = conn.executeQuery("select * from admin where name = '" + userName + "'");
		if (rs.next()) {
			out.println(function.PlutoJump("用户名已经存在", "new.jsp"));
		} else {
			//超级管理员注册
			boolean insert = conn.execute("insert into admin(name, pwd) values('" +
			userName + "','" + function.MD5Encode(userPwd) + "')");
			if (insert) {
				out.println(function.PlutoJump("注册成功，请登录!", "new.jsp"));
			} else {
				out.println(function.PlutoJump("注册失败!", "new.jsp"));
			}
		}
		return null;
	}
}
