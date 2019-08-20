package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OracleJdbcTest {
	// 数据库连接对象
	private static Connection conn = null;

	private static String driver = "oracle.jdbc.driver.OracleDriver"; // 驱动

	private static String url = "jdbc:oracle:thin:@//localhost:1521/orcl"; // 连接字符串

	private static String username = "scott"; // 用户名

	private static String password = "tiger"; // 密码
	
	private String rr;

	// 获得连接对象
	private static synchronized Connection getConn() {
		if (conn == null) {
			try {
				Class.forName(driver);
				conn = DriverManager.getConnection(url, username, password);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conn;
	}

	

	// 执行查询语句
	public void query(String sql, boolean isSelect) throws SQLException {
		PreparedStatement pstmt;

		try {
			pstmt = getConn().prepareStatement(sql);
			// 建立一个结果集，用来保存查询出来的结果
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				rr = rs.getString("RR");
				
				
			}
			rs.close();
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void query(String sql) throws SQLException {
		PreparedStatement pstmt;
		pstmt = getConn().prepareStatement(sql);
		pstmt.execute();
		pstmt.close();
	}

	// 关闭连接
	public void close() {
		try {
			getConn().close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}



	public String getRr() {
		return rr;
	}



	public void setRr(String rr) {
		this.rr = rr;
	}

}