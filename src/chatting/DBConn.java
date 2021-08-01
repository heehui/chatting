package chatting;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConn { 
	private Connection con; //접속객체
	
	public Connection getConnection() {
		return con;
	}
	
	public DBConn() //공통으로 접속객체 사용을 위해서
			throws ClassNotFoundException, SQLException {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","hr","hr");
	}

}
