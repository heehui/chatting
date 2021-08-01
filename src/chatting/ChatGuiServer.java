package chatting;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class ChatGuiServer {

	ArrayList<ServerThread> list = new ArrayList<>();

	ServerSocket server;// 클라이언트랑 연결할 때 필요한 클래스
	Socket sk; //서버로 접속한 클라이언트의 소켓을 저장해줌
	DataOutputStream outputStream;//출력 스트림

	ServerThread st;
	String name ="";

	public ChatGuiServer() throws IOException{ //생성자

		server = new ServerSocket(9992);//서버 포트번호 생성

		while(true){
			System.out.println("\nClient접속이 대기중입니다....");
			sk = server.accept(); // 클라이언트 접속 대기중
			System.out.println(sk.getInetAddress()+"가 연결되었습니다. ");

			//접속 클라이언트와 서버로 st객체 생성
			st = new ServerThread(sk);//초기치
			st.start();   
			list.add(st); //ArrayList 에 추가하기// 접속할 때마다 접속 클라이언트 스레드 추가

			System.out.println("접속자 수 : " + list.size()+"명");

		}
	}//생성자 -end




	class ServerThread extends Thread{//서버에서 각 클라이언트의 요청을 처리할 스레드

		Socket socket1;
		DataInputStream inputStream; //입출력에 대한 객체 생성
		DataOutputStream outputStream;
		//홍길동에 대한 소켓을 보냄

		public ServerThread(Socket s) throws IOException{ //생성자
			socket1 = s;		
			inputStream = new DataInputStream(s.getInputStream());
			outputStream = new DataOutputStream(s.getOutputStream());
			//입출력 스트림
		}//생성자-end


		@Override
		public void run() {

			try { 
				if(inputStream != null) {
					name = inputStream.readUTF();
					sendChat("### [ " +name + " ]님이 입장하셨습니다 ###"); //메소드 2형식 호출
	
					//ㄴ 클라이언트가 처음 보내는 것이 별명 ######
				}//전체 회원에게 지금 들어온 "별명"을 보내기 위한 작업 ->sendChat(전체에게 보내는 메시지)

				while (inputStream != null) {
					
					sendChat(inputStream.readUTF());
					//클라이언트가 보낸 채팅 "내용"을, 접속한 모두에게 보냄
				}//아무것도 안들어올 때까지 반복.

			}catch(IOException e) {
				//e.printStackTrace(); //에러내용 출력을 안하려면 주석.

			}finally { //나간 스레드의 인덱스 찾기

				for (int i = 0; i < list.size(); i++) {
					if(socket1.equals(list.get(i).socket1)) {
						list.remove(i); //퇴장 시 remove

						try {
							sendChat(name + " 님 퇴장이 퇴장하셨습니다.");
							//서버는 모든 클라이언트에게 누구누구님 퇴장이라고 보냄
						}catch(IOException e) {
							//e.printStackTrace();
						}

					}

				}//for-end
				System.out.println("접속자 수 : " + list.size()+ "명");
			}//finally-end

		}//run-end

		public void sendChat(String chat) throws IOException {
			for (int i = 0; i <list.size(); i++) {
				list.get(i).outputStream.writeUTF(chat);
			}		//사람1 //출력스트림에      //채팅 입력한 내용 보내기
					//사람2 //출력스트림 찾아서 //채팅 내용 보내기
			 		//=> 각각의 회원을 찾아가서 별명 or 채팅 내용을 전송.
		}
	}

	public static void main(String[] args) throws IOException {
		new ChatGuiServer(); //객체 생성

	}


}
