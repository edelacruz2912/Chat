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
		
		
		//SOCKET CLASS
		socket = new Socket(this.port);
		//primaryStage.setTitle(" " +socket.Ip + " "+socket.port + " ");
		primaryStage.setTitle("Chat Connector");
				
		Scene scene = new Scene(getChatConnectorContainer(),300,250);
		scene.getStylesheets().add("chat/Window.css");
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
//		connectorContainer.setPadding( new Insets(50));
		connectorContainer.setPadding( new Insets(50,0, 25, 0));
		
		destinationIPnumberTextA = new TextField();
		destinationIPnumberTextA.setPromptText("IP Number"); 
		destinationPortNumberTextA = new TextField();
		destinationPortNumberTextA.setPromptText("Port Number");
		
		/**
		 * setting the position for the textFields a button
		 * 
		 */
		destinationIPnumberTextA.setAlignment(Pos.TOP_CENTER);		
		destinationPortNumberTextA.setAlignment(Pos.BASELINE_CENTER);
		
		destinationIPnumberTextA.setPadding(new Insets(10));
		destinationPortNumberTextA.setPadding(new Insets(10));
		
			
		// Create new Window from Chat Connector. 
		// Passing in IP and Port info from input fields.
		// Adding this Window to hashMap lookup table. 
		Button newChat = new Button("Start Chat!");
		newChat.setAlignment(Pos.BOTTOM_CENTER);
		newChat.getStyleClass().add("button"); //CSS properties applied from Window.css
		newChat.setOnAction((e)-> { 
			String DestIp = destinationIPnumberTextA.getText();
			String DestPort = destinationPortNumberTextA.getText();
			//System.out.println("ip: " +DestIp);
			//System.out.println("port: " + DestPort);
			//Check whether inputs are empty
			if(DestIp.isEmpty() || DestPort.isEmpty()) { 
				//System.out.println("this run");
				return;
			}
			// If Chat w/ IP and PORT already open, dont open new chat window
			if(Socket.hashMapDataHolder.containsKey("Ip:"+ DestIp + "Port:"+ DestPort)) {
				return;
			}
			Window chatLayout = new Window(socket);
			chatLayout.setDestIP(DestIp);
			chatLayout.setDestPort(DestPort);
			chatLayout.updateTitle();
			//Setting the title when I start the conversation.
			//using the Ip(From destinationIp) and port(from destinationPort) 
			//chatLayout.newStageWindow.setTitle("IP: "+ DestIp + " " + " Port: "+DestPort);
			//System.out.println("DestIp :---- " + DestIp  + "  DestPort :" + DestPort);
			
			
			//keep tracks of conversation exists
			Socket.hashMapDataHolder.put(chatLayout.getIPandPort(), chatLayout);
			///hasHmap
			socket.dataInsideHasMap();	
			
			
			
		});
		
		connectorContainer.getChildren().addAll(destinationIPnumberTextA,destinationPortNumberTextA,newChat);
				
		return connectorContainer;
	}

}
