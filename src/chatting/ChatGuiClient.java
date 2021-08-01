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
  
  Socket sk; //������ ����� ���ؼ�
  DataInputStream inputStream;//Ŭ���̾�Ʈ������ ���ڿ� �Է� ��Ʈ��
  DataOutputStream outputStream; //���ڿ� ��� ��Ʈ��
  
  
  String name ="";

  public ChatGuiClient() {
      super("Chat client"); 
      setIconImage(Toolkit.getDefaultToolkit().getImage(ChatGuiClient.class.getResource("/image2/speech-bubble.png")));
      setTitle("3\uC870 \uCC44\uD305\uBC29");
      getContentPane().setBackground(new Color(210, 180, 140));
      getContentPane().setLayout(null);
      input_Text.setBounds(14, 450, 370, 30);
      getContentPane().add(input_Text);
      
      textArea.setEditable(false);//���� x
      
      JButton btnNewButton = new JButton("exit");
      btnNewButton.setBounds(273, 17, 111, 23);
      btnNewButton.addActionListener(new ActionListener() {
      	public void actionPerformed(ActionEvent e) {
      		if(JOptionPane.showConfirmDialog(null, "ä�� ����","ä���� �����Ͻðڽ��ϱ�?",
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
      sk = new Socket("127.0.0.1", 9992);//Ŭ���̾�Ʈ �� ���� ������ �ʱ�ȭ
     

      name = JOptionPane.showInputDialog(this, "*** ����Ͻ� �г����� �Է����ּ��� ***");


      
      //������ ��Ʈ���� ����
      inputStream = new DataInputStream(sk.getInputStream());
      outputStream = new DataOutputStream(sk.getOutputStream());
      
	  outputStream.writeUTF(name);
	 
	  scrollPane.setBounds(14, 52, 370, 386);
	  
	  getContentPane().add(scrollPane);
	  scrollPane.setViewportView(textArea);
	  

   }catch(Exception e) {
	   System.out.println("���ӿ���>>" + e);
   }
      
	//������ ���� �޾� textarea�� �ѷ��ִ� ������
	Thread th1 = new Thread(this);
	th1.start();//run() ȣ��
   
      
      
  }// ������ ��

  Toolkit tk1 = Toolkit.getDefaultToolkit();//chat �� ������ beep�� �︮�� �Ϸ���
 

  
  
  public void closeWork() throws IOException{
		outputStream.close();
		inputStream.close();
		System.exit(0);
	}

 
	

  public static void main(String[] args) {
    

  }

  
  @Override
  public void run() {// Runnable �������̽� run() �޼ҵ� �������̵�
	  try {
			while(true) {
				String strServer1 = inputStream.readUTF(); //������ ����
				if(strServer1 == null) {
					textArea.append("\n"+"����"); //append : ���ڿ� �ڿ� �߰��ϴ� �޼ҵ�
					return;
				}
				textArea.append("\n"+strServer1); // �������� �� ���� textarea�� �߰�

				int kkeut = textArea.getText().length();
				textArea.setCaretPosition(kkeut);//Ŀ���� �� �ڷ�
				  
				tk1.beep(); 
				
				
			}
		}catch(Exception eee) {
			textArea.append("\n"+eee);    
			
			}
		}//run-end
  @Override
  public void actionPerformed(ActionEvent e) {
	  if(e.getSource() == input_Text) {
			try {										//�Է��� ä�� ����
				outputStream.writeUTF("[ " + name + " ]"+input_Text.getText());
					//name�� client�� chat�� ������ ������
				
			} catch (IOException ee) {
				// TODO Auto-generated catch block
				//e1.printStackTrace();
			}
			input_Text.setText(""); //������ ������ �ش� ĭ�� ���ڿ��� ����
	  }
	
}
}