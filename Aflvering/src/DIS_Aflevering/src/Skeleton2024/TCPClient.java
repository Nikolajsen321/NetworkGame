//package DIS_Aflevering.src.Skeleton2024;
//
//import game2024.Reader;
//import game2024.Writer;
//
//import java.net.Socket;
//
//
//
//
//
//public class TCPClient {
//
//	public static void main(String argv[]) throws Exception{
//		try {
//			Socket clientSocket = new Socket("localhost", 6791);
//
//			Reader readerThread = new Reader(clientSocket);
//			Writer writerThread = new Writer(clientSocket);
//
//			readerThread.start();
//			writerThread.start();
//
//
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//
