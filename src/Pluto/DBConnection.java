package Pluto;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


import java.sql.*;



public class DBConnection {
	//设置数据库驱动建立连接
	static private String strUrl = "jdbc:mysql://localhost/onlinemusic?useUnicode=true&characterEncoding=utf-8";
	
	static private String strUser = "snowalker";
	
	static private String strPwd = "admin";
	
	static private String strDriver = "com.mysql.jdbc.Driver";
	
	private Connection conn = null;
	
	private Statement stmt = null;
	
	private PreparedStatement pstmt = null;
	
	private ResultSet rs = null;
	//静态快
	static {
		try {
			Class.forName(strDriver);
		} catch (ClassNotFoundException e) {
			System.out.println("加载错误" + strDriver);
		}
	}
	
	public DBConnection() {
		System.out.println("建立连接中...");
	}
	//建立连接
	private Connection getConnection(){
		try {
			if (conn == null || conn.isClosed()) {
				conn = DriverManager.getConnection(strUrl, strUser, strPwd);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return conn;
	}
	//关闭连接
	@SuppressWarnings("unused")
	private void close() {
		try {
			if (rs != null) {
				rs.close();
				rs = null;
			}
			if (pstmt != null) {
				pstmt.close();
				pstmt = null;
			}
			if (conn != null) {
				conn.close();
				conn = null;
			}
		} catch (Exception e) {
			System.err.println("关闭失败" + e.getMessage());
		}
	}
	//查找方法
	public ResultSet executeQuery(String sql) {
		try {
			pstmt = getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			System.err.println("查询失败\t" + e.getMessage());
		}
		return rs;
	}
	//执行方法
	public boolean execute(String sql) {
		
		try {
			pstmt = getConnection().prepareStatement(sql);
			if (pstmt.execute()) {
				return true;
			}
		} catch (SQLException e) {
			System.err.println("查询失败\t" + e.getMessage());
			return false;
		}
		return true;
	}
	//更新方法
	public int executeUpdate(String sql) {
		int resultNum = 0;
		try {
			pstmt = getConnection().prepareStatement(sql);
			resultNum = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.err.println("更新失败" + e.getMessage());
		} finally {
		}
		return resultNum;
	}
	
	public void test() {
		System.err.println("test");
		System.err.println(getClass());
	}
}
