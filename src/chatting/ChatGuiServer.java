package chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatGuiServer {

	ArrayList<ServerThread> list = new ArrayList<>();

	ServerSocket server;// Ŭ���̾�Ʈ�� ������ �� �ʿ��� Ŭ����
	Socket sk; //������ ������ Ŭ���̾�Ʈ�� ������ ��������
	DataOutputStream outputStream;//��� ��Ʈ��

	ServerThread st;
	String name ="";

	public ChatGuiServer() throws IOException{ //������

		server = new ServerSocket(9992);//���� ��Ʈ��ȣ ����

		while(true){
			System.out.println("\nClient������ ������Դϴ�....");
			sk = server.accept(); // Ŭ���̾�Ʈ ���� �����
			System.out.println(sk.getInetAddress()+"�� ����Ǿ����ϴ�. ");

			//���� Ŭ���̾�Ʈ�� ������ st��ü ����
			st = new ServerThread(sk);//�ʱ�ġ
			st.start();   
			list.add(st); //ArrayList �� �߰��ϱ�// ������ ������ ���� Ŭ���̾�Ʈ ������ �߰�

			System.out.println("������ �� : " + list.size()+"��");

		}
	}//������ -end




	class ServerThread extends Thread{//�������� �� Ŭ���̾�Ʈ�� ��û�� ó���� ������

		Socket socket1;
		DataInputStream inputStream; //����¿� ���� ��ü ����
		DataOutputStream outputStream;
		//ȫ�浿�� ���� ������ ����

		public ServerThread(Socket s) throws IOException{ //������
			socket1 = s;		
			inputStream = new DataInputStream(s.getInputStream());
			outputStream = new DataOutputStream(s.getOutputStream());
			//����� ��Ʈ��
		}//������-end


		@Override
		public void run() {

			try { 
				if(inputStream != null) {
					name = inputStream.readUTF();
					sendChat("### [ " +name + " ]���� �����ϼ̽��ϴ� ###"); //�޼ҵ� 2���� ȣ��
	
					//�� Ŭ���̾�Ʈ�� ó�� ������ ���� ���� ######
				}//��ü ȸ������ ���� ���� "����"�� ������ ���� �۾� ->sendChat(��ü���� ������ �޽���)

				while (inputStream != null) {
					
					sendChat(inputStream.readUTF());
					//Ŭ���̾�Ʈ�� ���� ä�� "����"��, ������ ��ο��� ����
				}//�ƹ��͵� �ȵ��� ������ �ݺ�.

			}catch(IOException e) {
				//e.printStackTrace(); //�������� ����� ���Ϸ��� �ּ�.

			}finally { //���� �������� �ε��� ã��

				for (int i = 0; i < list.size(); i++) {
					if(socket1.equals(list.get(i).socket1)) {
						list.remove(i); //���� �� remove

						try {
							sendChat(name + " �� ������ �����ϼ̽��ϴ�.");
							//������ ��� Ŭ���̾�Ʈ���� ���������� �����̶�� ����
						}catch(IOException e) {
							//e.printStackTrace();
						}

					}

				}//for-end
				System.out.println("������ �� : " + list.size()+ "��");
			}//finally-end

		}//run-end

		public void sendChat(String chat) throws IOException {
			for (int i = 0; i <list.size(); i++) {
				list.get(i).outputStream.writeUTF(chat);
			}		//���1 //��½�Ʈ����      //ä�� �Է��� ���� ������
					//���2 //��½�Ʈ�� ã�Ƽ� //ä�� ���� ������
			 		//=> ������ ȸ���� ã�ư��� ���� or ä�� ������ ����.
		}
	}

	public static void main(String[] args) throws IOException {
		new ChatGuiServer(); //��ü ����

	}


}
