package chatting;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ChatinfoDAO extends JFrame{

	private Connection con;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	
	public ChatinfoDAO() throws ClassNotFoundException, SQLException {
		con = new DBConn().getConnection();
		
	}
	

	public ChatinfoDAO(Chatting_1_login chatting_1_login) {
		// TODO Auto-generated constructor stub
	}


	public boolean logincheck(String id, String password) throws SQLException {
		
		boolean b= false;
		String sql = "select * from join_table where id =? and password=?";
	
		pstmt = con.prepareStatement(sql);///db sql 문장으로 만들어줌
		pstmt.setString(1, id);//로그인화면에서 아이디 입력한 거
		pstmt.setString(2, password); //로그인 화면에서 비번 입력한 거
		rs = pstmt.executeQuery();//조회를 실행해라
		if(rs.next()) {// 저렇게 조회하면 값이 있나?
			b=true;// 있으면 true
			dispose();
			setVisible(false);
			JOptionPane.showMessageDialog(this, "[" + id +"]" + "님 환영합니다 :)");
			new ChatGuiClient().setVisible(true);
			id="";
			password="";
		}else {
			JOptionPane.showMessageDialog(this,"일치하는 정보가 없습니다.");
			id="";
			password="";
		}
   
        return b;//없으면 false

    }
	
}