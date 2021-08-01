package chatting;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ChatGuiClient extends JFrame implements ActionListener, Runnable {
  
  JTextField input_Text = new JTextField();
  JTextArea textArea = new JTextArea();
  JScrollPane scrollPane = new JScrollPane();
  
  Socket sk; //서버와 통신을 위해서
  DataInputStream inputStream;//클라이언트에서의 문자열 입력 스트림
  DataOutputStream outputStream; //문자열 출력 스트림
  
  
  String name ="";

  public ChatGuiClient() {
      super("Chat client"); 
      setIconImage(Toolkit.getDefaultToolkit().getImage(ChatGuiClient.class.getResource("/image2/speech-bubble.png")));
      setTitle("3\uC870 \uCC44\uD305\uBC29");
      getContentPane().setBackground(new Color(210, 180, 140));
      getContentPane().setLayout(null);
      input_Text.setBounds(14, 450, 370, 30);
      getContentPane().add(input_Text);
      
      textArea.setEditable(false);//편집 x
      
      JButton btnNewButton = new JButton("exit");
      btnNewButton.setBounds(273, 17, 111, 23);
      btnNewButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if(JOptionPane.showConfirmDialog(null, "채팅 종료","채팅을 종료하시겠습니까?",
      				JOptionPane.YES_NO_OPTION,
      				JOptionPane.INFORMATION_MESSAGE) != JOptionPane.NO_OPTION)
      				{
      						dispose();
      						setVisible(false);
      						setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      				}
      		
      	}
      });
      getContentPane().add(btnNewButton);
      setSize(418, 538);
      setVisible(true);
      setLocationRelativeTo(null);
      
   
//      input_Text.requestFocus(); 
//
//      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      input_Text.addActionListener(this); 
   try {
      sk = new Socket("127.0.0.1", 9992);//클라이언트 측 소켓 정보를 초기화
     

      name = JOptionPane.showInputDialog(this, "*** 사용하실 닉네임을 입력해주세요 ***");


      
      //서버와 스트림을 연결
      inputStream = new DataInputStream(sk.getInputStream());
      outputStream = new DataOutputStream(sk.getOutputStream());
      
	  outputStream.writeUTF(name);
	 
	  scrollPane.setBounds(14, 52, 370, 386);
	  
	  getContentPane().add(scrollPane);
	  scrollPane.setViewportView(textArea);
	  

   }catch(Exception e) {
	   System.out.println("접속오류>>" + e);
   }
      
	//서버로 부터 받아 textarea에 뿌려주는 스레드
	Thread th1 = new Thread(this);
	th1.start();//run() 호출
   
      
      
  }// 생성자 끝

  Toolkit tk1 = Toolkit.getDefaultToolkit();//chat 올 때마다 beep음 울리게 하려고
 

  
  
  public void closeWork() throws IOException{
		outputStream.close();
		inputStream.close();
		System.exit(0);
	}

 
	

  public static void main(String[] args) {
    

  }

  
  @Override
  public void run() {// Runnable 인터페이스 run() 메소드 오버라이딩
	  try {
			while(true) {
				String strServer1 = inputStream.readUTF(); //서버로 부터
				if(strServer1 == null) {
					textArea.append("\n"+"종료"); //append : 문자열 뒤에 추가하는 메소드
					return;
				}
				textArea.append("\n"+strServer1); // 서버에서 온 것을 textarea에 추가

				int kkeut = textArea.getText().length();
				textArea.setCaretPosition(kkeut);//커서를 맨 뒤로
				  
				tk1.beep(); 
				
				
			}
		}catch(Exception eee) {
			textArea.append("\n"+eee);    
			
			}
		}//run-end
  @Override
  public void actionPerformed(ActionEvent e) {
	  if(e.getSource() == input_Text) {
			try {										//입력한 채팅 내용
				outputStream.writeUTF("[ " + name + " ]"+input_Text.getText());
					//name과 client의 chat을 서버로 보내기
				
			} catch (IOException ee) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			input_Text.setText(""); //서버로 보내고 해당 칸을 빈문자열로 비우기
	  }
	
}
}