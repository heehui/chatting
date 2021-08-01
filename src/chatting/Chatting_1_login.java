package chatting;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Toolkit;

public class Chatting_1_login extends JFrame {

	private JPanel contentPane;
	private JTextField txtId;
	private JPasswordField txtPw;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) throws NullPointerException{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chatting_1_login frame = new Chatting_1_login();
					frame.setVisible(true);
				} catch (Exception e) {
					//e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Chatting_1_login() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Chatting_1_login.class.getResource("/image2/speech-bubble.png")));
		setTitle("3\uC870 \uCC44\uD305\uBC29");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 600);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel Label_info = new JLabel("\uCC44\uD305 \uB85C\uADF8\uC778");
		Label_info.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 27));
		Label_info.setBounds(164, 95, 174, 63);
		contentPane.add(Label_info);
		
		txtId = new JTextField();
		txtId.setBounds(198, 330, 116, 21);
		contentPane.add(txtId);
		txtId.setColumns(10);
		
		JLabel Label_id = new JLabel("\uC544\uC774\uB514");
		Label_id.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		Label_id.setBounds(117, 333, 57, 15);
		contentPane.add(Label_id);
		
		JLabel Label_pw = new JLabel("\uBE44\uBC00\uBC88\uD638");
		Label_pw.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		Label_pw.setBounds(117, 380, 57, 15);
		contentPane.add(Label_pw);
		
		txtPw = new JPasswordField();
		txtPw.setColumns(10);
		txtPw.setBounds(198, 378, 116, 21);
		contentPane.add(txtPw);
		
		JButton Button_login = new JButton("\uB300\uD654\uCC38\uAC00");
		Button_login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					login();
					txtId.setText("");
					txtPw.setText("");
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				}
			
				
			}

			private void login() throws ClassNotFoundException, SQLException {
				String id = txtId.getText();
				String password = new String(txtPw.getPassword());
				
				if(txtId.getText().length()==0) {
					JOptionPane.showMessageDialog(null, "¾ÆÀÌµð¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä");
					txtId.requestFocus();
					txtId.setText("");
					txtPw.setText("");
					return;
				
				}else if(password.length()==0) {
					JOptionPane.showMessageDialog(null, "ºñ¹Ð¹øÈ£¸¦ ÀÔ·ÂÇØÁÖ¼¼¿ä.");
					txtPw.requestFocus();
					txtId.setText("");
					txtPw.setText("");
					return;
				}
				ChatinfoDAO dao = new ChatinfoDAO();
				dao.logincheck(id, password);
				
				
			}
		});
		Button_login.setFont(new Font("¸¼Àº °íµñ", Font.BOLD, 14));
		Button_login.setBackground(new Color(255, 153, 102));
		Button_login.setForeground(new Color(255, 255, 255));
		Button_login.setBounds(198, 428, 116, 28);
		contentPane.add(Button_login);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Chatting_1_login.class.getResource("/image2/chat1.png")));
		lblNewLabel.setBounds(107, 21, 342, 297);
		contentPane.add(lblNewLabel);
	}
}
