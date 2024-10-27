package game2024;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

public class Server {
	private static ArrayList<ServerThread> serverThreadss = new ArrayList<>();

	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {

		ServerSocket welcomeSocket = new ServerSocket(6791);
		ArrayList<Player> players = new ArrayList<>();

		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			ServerThread st = new ServerThread(connectionSocket);
			st.start();
			serverThreadss.add(st);
		}
	}


	public static void gennemThread(String string){
		for(ServerThread serverThread : serverThreadss){
			try {
				System.out.println(serverThread.getName());
				serverThread.response(string);
			}catch (Exception e){
				e.printStackTrace();
			}

		}
	}


}
