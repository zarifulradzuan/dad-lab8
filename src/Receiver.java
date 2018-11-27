import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Receiver {
	public static void main(String []args){
		try{
			@SuppressWarnings("resource")
			ServerSocket serverSocket = new ServerSocket(8081);
			
			while(true){
				Socket socket = serverSocket.accept();
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
				String strFromSender = dataInputStream.readUTF();
				String strToSender = "";
				try{
					int intCast = Integer.parseInt(strFromSender.trim());
					
					if(intCast%2==0)
						strToSender="You sent even number";
					else
						strToSender="You sent odd number";	
				}catch(NumberFormatException e){
					strToSender="You have not entered a valid number";
		
				}
				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				dataOutputStream.writeUTF(strToSender);
				System.out.println(strToSender);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
