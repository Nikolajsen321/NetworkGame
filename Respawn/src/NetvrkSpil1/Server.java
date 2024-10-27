package NetvrkSpil1;


import java.net.*;
import java.util.ArrayList;

public class Server {
	private static ArrayList<ServerThread> serverThreadss = new ArrayList<>();
//	private static ArrayList<String> gameNames;

	
	/**
	 * @param args
	 */
	public static void main(String[] args)throws Exception {

//		gameNames.add("Benjamin");
//		gameNames.add("Mikkel");
		ServerSocket welcomeSocket = new ServerSocket(6791);



		while (true) {
			Socket connectionSocket = welcomeSocket.accept();
			ServerThread st = new ServerThread(connectionSocket);
					st.start();
			serverThreadss.add(st);
			System.out.println("Player "+serverThreadss.size()+ " connected");

//			st.playerInit(""+serverThreadss.size());
		}
	}





	public static synchronized void gennemThread(String string){
		for(ServerThread serverThread : serverThreadss){
				try {
					System.out.println("Server: " + serverThread.getName());
					serverThread.response(string);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}

	}

}
