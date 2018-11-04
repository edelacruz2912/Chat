package chat;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application{

	//SOCKET CLASS
	Socket  socket;
	private int port = 7070;
	private TextField destinationIPnumberTextA;
	private TextField destinationPortNumberTextA;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
		
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("CHAT with port: " + this.port);
		
		//SOCKET CLASS
		socket = new Socket(this.port);
		
		
				
		Scene scene = new Scene(getChatConnectorContainer(),300,100);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	/**
	 * Create a container for the components for
	 * Chat Connector and 'Start Chat' button event handling.
	 * @return VBox - with the components for Chat Connector
	 */
	public VBox getChatConnectorContainer() {
		//FOR THE LEFT REGION
		VBox connectorContainer = new VBox();
		connectorContainer.setAlignment(Pos.BASELINE_CENTER);
		
		destinationIPnumberTextA = new TextField();
		destinationIPnumberTextA.setPromptText("IP Number"); 
		destinationPortNumberTextA = new TextField();
		destinationPortNumberTextA.setPromptText("Port Number");
		
		
		// Create new Window from Chat Connector. 
		// Passing in IP and Port info from input fields.
		// Adding this Window to hashMap lookup table. 
		Button newChat = new Button("Start Chat!");
		newChat.setOnAction((e)-> { 
			String ip = destinationIPnumberTextA.getText();
			String DestPort = destinationPortNumberTextA.getText();
			System.out.println("ip: " +ip);
			System.out.println("port: " + DestPort);
			//Check whether inputs are empty
			if(ip.equals(DestPort)) {
				//System.out.println("this run");
				return;
			}
			// If Chat w/ IP and PORT already open, dont open new chat window
			if(Socket.hashMapDataHolder.containsKey("Ip:"+ ip + "Port:"+ DestPort)) {
				return;
			}
			Window chatLayout = new Window(socket);
			chatLayout.setDestIP(ip);
			chatLayout.setDestPort(DestPort);
			
			//keep tracks of conversation exists
			Socket.hashMapDataHolder.put(chatLayout.getIPandPort(), chatLayout);
			///hasHmap
			socket.dataInsideHasMap();	
		});
		
		connectorContainer.getChildren().addAll(destinationIPnumberTextA,destinationPortNumberTextA,newChat);
				
		return connectorContainer;
	}

}
