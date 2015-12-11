package Pluto.admin;

import java.io.PrintWriter;
import java.sql.ResultSet;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.Occurs;

import Pluto.DBConnection;
import Pluto.function;

public class changepwd extends ActionSupport {

	private String oldPwd;

	private String newPwd1;

	private String newPwd2;

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getNewPwd1() {
		return newPwd1;
	}

	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1;
	}

	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}

	@Override
	public String execute() throws Exception {
		// 设置页面编码格式相关信息
		ServletActionContext.getResponse().setCharacterEncoding("UTF-8");
		PrintWriter out = ServletActionContext.getResponse().getWriter();
		ServletActionContext.getResponse().setHeader("Pragma", "No-cache");
		ServletActionContext.getResponse().setHeader("Cache-Control", "no-cache");
		ServletActionContext.getResponse().setDateHeader("Expires", 0);
		
		String adminName = ServletActionContext.getContext().getSession().get("PlutoAdmin").toString();

		if (function.isInvalid(oldPwd) || function.isInvalid(newPwd1) || function.isInvalid(newPwd2)) {
			out.println(function.PlutoJump("请填写密码!", "changepwd.jsp"));
		}
		if (newPwd1.equals(newPwd2)) {
			DBConnection conn = new DBConnection();
			ResultSet rs = conn.executeQuery("select pwd from admin where name = '" + adminName + "'");
			rs.next();
			if (function.MD5Encode(oldPwd).equals(rs.getString("pwd"))) {
				// 新密码更新数据
				boolean update = conn.execute(
						"update admin set pwd  = '" + function.MD5Encode(newPwd1) + "' where name='" + adminName + "'");
				if (update) {
					out.println(function.PlutoJump("密码修改成功!", "changepwd.jsp"));
				} else {
					out.println(function.PlutoJump("密码修改失败", "changepwd.jsp"));
				}
			} else {
				out.println(function.PlutoJump("旧密码不对!", "changepwd.jsp"));
			}

		} else {
			// 新密码与确认密码不同
			out.println(function.PlutoJump("两次输入的密码不一致", "changepwd.jsp"));
		}

		return null;

	}
}
