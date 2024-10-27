package NetvrkSpil1;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Writer extends Thread {
    private DataOutputStream writer;
    private String text;

    public Writer(Socket socket) throws IOException {
        this.writer = new DataOutputStream(socket.getOutputStream());
    }

    public void setColetionText(String text){
        this.text = text;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.println(writer);
                String message;
                if(text == null) {
                    message= scanner.nextLine();
                }else {
                    message = text;
                    writer.writeBytes(message + '\n');
                    break;
//                    writer.close();
                }

//                if (message.equalsIgnoreCase("exit")) {
//                    writer.close();
//                }

                writer.writeBytes(message + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}