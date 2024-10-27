package NetvrkSpil1;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

public class GUI extends Application {

	private static Reader reader;
	private static Socket clientSocket;

	private static DataOutputStream outToServer;
	private static GUI uniqueInstance;

	public static final int size = 20;
	public static final int scene_height = size * 20 + 100;
	public static final int scene_width = size * 20 + 200;

	public static Image image_floor;
	public static Image image_wall;
	public static Image hero_right,hero_left,hero_up,hero_down;

	public static Player player;
	public static List<Player> players = new ArrayList<Player>();

	private static Label[][] fields;
	private static TextArea scoreList;





	private static String[] board = {    // 20x20
			"wwwwwwwwwwwwwwwwwwww",
			"w        ww        w",
			"w w  w  www w  w  ww",
			"w w  w   ww w  w  ww",
			"w  w               w",
			"w w w w w w w  w  ww",
			"w w     www w  w  ww",
			"w w     w w w  w  ww",
			"w   w w  w  w  w   w",
			"w     w  w  w  w   w",
			"w ww ww        w  ww",
			"w  w w    w    w  ww",
			"w        ww w  w  ww",
			"w         w w  w  ww",
			"w        w     w  ww",
			"w  w              ww",
			"w  w www  w w  ww ww",
			"w w      ww w     ww",
			"w   w   ww  w      w",
			"wwwwwwwwwwwwwwwwwwww"
	};


	// -------------------------------------------
	// | Maze: (0,0)              | Score: (1,0) |
	// |-----------------------------------------|
	// | boardGrid (0,1)          | scorelist    |
	// |                          | (1,1)        |
	// -------------------------------------------

	@Override
	public void start(Stage primaryStage) {
		try {
//			setUpMethod();
			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			Text mazeLabel = new Text("Maze:");
			mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			Text scoreLabel = new Text("Score:");
			scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			scoreList = new TextArea();

			GridPane boardGrid = new GridPane();

			image_wall  = new Image(getClass().getResourceAsStream("Image/wall4.png"),size,size,false,false);
			image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"),size,size,false,false);

			hero_right  = new Image(getClass().getResourceAsStream("Image/heroRight.png"),size,size,false,false);
			hero_left   = new Image(getClass().getResourceAsStream("Image/heroLeft.png"),size,size,false,false);
			hero_up     = new Image(getClass().getResourceAsStream("Image/heroUp.png"),size,size,false,false);
			hero_down   = new Image(getClass().getResourceAsStream("Image/heroDown.png"),size,size,false,false);

			fields = new Label[20][20];
			for (int j=0; j<20; j++) {
				for (int i=0; i<20; i++) {
					switch (board[j].charAt(i)) {
					case 'w':
						fields[i][j] = new Label("", new ImageView(image_wall));
						break;
					case ' ':
						fields[i][j] = new Label("", new ImageView(image_floor));
						break;
					default: throw new Exception("Illegal field value: "+board[j].charAt(i) );
					}
					boardGrid.add(fields[i][j], i, j);
				}
			}
			scoreList.setEditable(false);

			grid.add(mazeLabel,  0, 0);
			grid.add(scoreLabel, 1, 0);
			grid.add(boardGrid,  0, 1);
			grid.add(scoreList,  1, 1);

			Scene scene = new Scene(grid,scene_width,scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			setupPlayers();
			setUpMethod();

//			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				switch (event.getCode()) {
				case UP:
					try {
//						playerMoved(0,-1,"up",player);
						outToServer.writeBytes("MOVE "+player.xpos+" "+player.ypos+" "+player.name +
								" up"+ '\n');
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
				case DOWN:
					try {
//						playerMoved(0,+1,"down",player);
						outToServer.writeBytes("MOVE "+player.xpos+" "+player.ypos+" "+player.name + " down"+ '\n');
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
				case LEFT:
					try {
//						playerMoved(-1,0,"left",player);
						outToServer.writeBytes("MOVE "+player.xpos+" "+player.ypos+" "+player.name + " left"+ '\n');
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
				case RIGHT:
					try {
//						playerMoved(+1,0,"right",player);
						outToServer.writeBytes("MOVE "+player.xpos+" "+player.ypos+" "+player.name + " right"+ '\n');
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					break;
				default: break;
				}
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setupPlayers(){
		openDialog();

		// Setting up standard players
		player = new Player("Kaj",14,15,"up");
		players.add(player);
		fields[14][15].setGraphic(new ImageView(hero_up));


		Player harry =new Player("Ib",9,4,"up");
		fields[9][4].setGraphic(new ImageView(hero_up));
		players.add(harry);

//		Player emil = new Player("Emil", 15, 2, "up");
//		players.add(emil);
//		fields[15][2].setGraphic(new ImageView(hero_up));

		scoreList.setText(getScoreList());
	}
	// Metode til at åbne det lille vindue
	private void openDialog() {
		// Opret et nyt vindue (dialogvindue)
		Stage dialogStage = new Stage();

		// Sæt modality til APPLICATION_MODAL, så brugeren skal lukke dette vindue før hovedvinduet kan bruges igen
		dialogStage.initModality(Modality.APPLICATION_MODAL);

		// Opret Labels til at beskrive formålet med tekstfelterne
		Label nameLabel = new Label("Indtast dit navn:");
		Label ageLabel = new Label("Indtast din x position");
		Label emailLabel = new Label("Indtast din y position:");

		// Opret tekstfelter
		TextField nameField = new TextField();
		TextField xField = new TextField();
		TextField yField = new TextField();

		// Opret en layout container (VBox) for dialogvinduet
		VBox dialogLayout = new VBox(10); // 10 er afstanden mellem elementerne i VBox'en
		dialogLayout.getChildren().addAll(nameLabel, nameField, ageLabel, xField, emailLabel, yField); // Tilføj labels og tekstfelter til VBox'en

		// Opret en scene med VBox'en som roden for dialogvinduet
		Scene dialogScene = new Scene(dialogLayout, 250, 200); // 250 er bredden, 200 er højden

		// Sæt scenen i dialogvinduet
		dialogStage.setScene(dialogScene);

		// Sæt vinduets titel
		dialogStage.setTitle("Indtast oplysninger");

		// Vis dialogvinduet
		dialogStage.show();
	}



	public static void playerMoved(int delta_x, int delta_y, String direction,String navn) throws IOException {
		for(Player player : getPlayers()){
			if(player.name.equals(navn)) {
				player.direction = direction;
				int x = player.getXpos(), y = player.getYpos();

				if (board[y + delta_y].charAt(x + delta_x) == 'w') {
					player.addPoints(-1);
				} else {
					Player p = getPlayerAt(x + delta_x, y + delta_y);
					if (p != null) {
						player.addPoints(10);
						p.addPoints(-10);
						respawnGui(p.getXpos(),p.getYpos());
						p.respawn(p.getXpos(),p.getYpos());
					} else {
						player.addPoints(1);

						fields[x][y].setGraphic(new ImageView(image_floor));
						x += delta_x;
						y += delta_y;

						if (direction.equals("right")) {
							fields[x][y].setGraphic(new ImageView(hero_right));
						}
						;
						if (direction.equals("left")) {
							fields[x][y].setGraphic(new ImageView(hero_left));
						}
						;
						if (direction.equals("up")) {
							fields[x][y].setGraphic(new ImageView(hero_up));
						}
						;
						if (direction.equals("down")) {
							fields[x][y].setGraphic(new ImageView(hero_down));
						}
						;
						player.setXpos(x);
						player.setYpos(y);
					}
				}
			}
			}
			scoreList.setText(getScoreList());

	}

	private static void respawnGui(int x, int y) {
		fields[x][y].setGraphic(new ImageView(image_floor));

		//Denne skal gøres random
		if(x==9 && y == 4){
			fields[6][12].setGraphic(new ImageView(hero_up));

		}else {
			fields[9][4].setGraphic(new ImageView(hero_up));
		}
	}


	public static String getScoreList() {
		StringBuffer b = new StringBuffer(100);
		for (Player p : players) {
			b.append(p+"\r\n");
		}
		return b.toString();
	}

	public static Player getPlayerAt(int x, int y) {
		for (Player p : players) {
			if (p.getXpos()==x && p.getYpos()==y) {
				return p;
			}
		}
		return null;
	}

	public static void setUpMethod(){
		try {
			clientSocket = new Socket("localhost",6791);
			reader = new Reader(clientSocket);
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			reader.start();

		}catch (IOException e){
			e.printStackTrace();
		}

}


public void updatePlayerPosition(int deltaX, int deltaY, Player player){
		int newX = player.getXpos() + deltaX;
		int newY = player.getYpos() + deltaY;

		player.setXpos(newX);
		player.setYpos(newY);
}





	public static List<Player> getPlayers() {
		return new ArrayList<>(players);
	}
}

