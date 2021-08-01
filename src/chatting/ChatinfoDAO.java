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
	
		pstmt = con.prepareStatement(sql);///db sql �������� �������
		pstmt.setString(1, id);//�α���ȭ�鿡�� ���̵� �Է��� ��
		pstmt.setString(2, password); //�α��� ȭ�鿡�� ��� �Է��� ��
		rs = pstmt.executeQuery();//��ȸ�� �����ض�
		if(rs.next()) {// ������ ��ȸ�ϸ� ���� �ֳ�?
			b=true;// ������ true
			dispose();
			setVisible(false);
			JOptionPane.showMessageDialog(this, "[" + id +"]" + "�� ȯ���մϴ� :)");
			new ChatGuiClient().setVisible(true);
			id="";
			password="";
		}else {
			JOptionPane.showMessageDialog(this,"��ġ�ϴ� ������ �����ϴ�.");
			id="";
			password="";
		}
   
        return b;//������ false

    }
	
}