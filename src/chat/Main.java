package chat;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application{

	//SOCKET CLASS
	Socket  socket;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("CHAT");
		
		//SOCKET CLASS
		socket = new Socket(12345);
		
		Window chatLayout = new Window(socket);
		
		
		
		Scene scene = new Scene(chatLayout,500,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}
