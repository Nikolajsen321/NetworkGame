package NetvrkSpil1;

import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Reader extends Thread {
    private BufferedReader reader;

    public Reader(Socket socket) throws IOException {
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String receivedMessage = reader.readLine();
                System.out.println(receivedMessage);


//                System.out.println(beskedListe[0]);
//                System.out.println(beskedListe.length);


                for (Player player : GUI.getPlayers()) {
                    Platform.runLater(() -> {
                                String[] beskedListe = receivedMessage.split(" ");
                                System.out.println("Er " + player.name + " samme som " + beskedListe[3]);
                                if (player.name.equals(beskedListe[3])) {
                                    System.out.println(player.name + "h " + beskedListe[3]);
                                    if (beskedListe[0].equals("MOVE")) {
                                        try {

                                        if (beskedListe[4].equals("up")) {
                                            GUI.playerMoved(0, -1, "up", player.name);
                                        } else if (beskedListe[4].equals("down")) {
                                            GUI.playerMoved(0, 1, "down", player.name);
                                        } else if (beskedListe[4].equals("left")) {
                                            GUI.playerMoved(-1, 0, "left", player.name);
                                        } else if (beskedListe[4].equals("right")) {

                                                GUI.playerMoved(1, 0, "right", player.name);

                                            }
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }


//                } else if (receivedMessage.equalsIgnoreCase("Player number: 3")) {
//                    GUI.player = GUI.players.get(2);

                                    }
                                    System.out.println("Received: " + receivedMessage);
                                }
                            });
//                    else {
//                        if (beskedListe[0].equals("MOVE")) {
//
//                            System.out.println("Hej");
//                            System.out.println("x " + beskedListe[1]);
//                            System.out.println("y " + beskedListe[2]);
//                            System.out.println("else navn " + player.name);
//
//
//                            GUI.playerMoved(Integer.parseInt(beskedListe[1]) - player.xpos,
//                                    Integer.parseInt(beskedListe[2]) - player.ypos,
//                                    beskedListe[4], player.name);
//                        }
//
//                    }
                    }
                }

        }catch(IOException e){
                e.printStackTrace();
            }
        }


}