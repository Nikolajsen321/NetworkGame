package game2024;
import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class ServerThread extends Thread{
	Socket connSocket;
	ArrayList<ServerThread> serverThreads;
	DataOutputStream outputStream1 = null;
	public ServerThread(Socket connSocket) {
		this.connSocket = connSocket;
		try {
			outputStream1 = new DataOutputStream(connSocket.getOutputStream());
		}catch (IOException e){
			e.printStackTrace();
		}

	}

	public void updateThreads(ArrayList<ServerThread> serverThreadss){
		this.serverThreads = serverThreadss;
	}
	public void run() {
		try {
			BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connSocket.getInputStream()));

			while (true) {

//
//				outputStream1.writeBytes("ecco " + clientSentence + '\n');
				String clientSentence = inFromClient.readLine();

				Server.gennemThread(clientSentence);

			}
		
		} catch (IOException e) {
			e.printStackTrace();
		}		
		// do the work here
	}
	public void response(String string) {
		try {
			System.out.println(string);
			outputStream1.writeBytes(string + "\n");
		}catch (IOException e){
			e.printStackTrace();
		}


	}
}
