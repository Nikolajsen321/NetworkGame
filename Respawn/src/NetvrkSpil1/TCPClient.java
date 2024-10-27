package NetvrkSpil1;//package game2024;

import java.net.Socket;

public class TCPClient {

	public static void main(String[] args) {
		try {
			Socket clientSocket = new Socket("localhost", 6791);

			Reader readerThread = new Reader(clientSocket);
			Writer writerThread = new Writer(clientSocket);

			readerThread.start();
			writerThread.start();

//			clientSocket.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}