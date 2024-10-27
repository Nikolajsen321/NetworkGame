package game2024;

import javafx.application.Application;

import java.io.IOException;

public class App {
	public static void main(String[] args) {
		try {
			GUI.setUpMethod();
			Application.launch(GUI.class);
		}catch (Exception e){
			e.printStackTrace();
		}


	}
}
