package chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class Main extends Application{

	//SOCKET CLASS
	Socket  socket;
	private int port = 64000;
	//private TextField destinationIPnumberTextA;
	//private TextField destinationPortNumberTextA;
	private Text txtTitle;
	private TextField inputName;
	private Button strBroadCast;
	private String nameOfPerson;
	
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
	 * BroadCast to all devices connected to the same netWork
	 */	
	public void broadcast(String personName,InetAddress address) throws IOException
	{
		DatagramSocket broadCastSocket = new DatagramSocket();
		broadCastSocket.setBroadcast(true);	
		String broadCastRS = "????? "+ personName + " ##### Eduardo";
		byte[] buffer = broadCastRS.getBytes();		
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, this.port);
		broadCastSocket.send(packet);
       	broadCastSocket.close();
		
	}
	
	/**
	 * This method get the name from the user
	 * to look for on the Net by making a broadCast
	 * @return VBox (The BroadCastingWindow)
	 */
	public VBox getChatConnectorContainer() {
		VBox broadContainer = new VBox();
		broadContainer.setAlignment(Pos.CENTER);
		broadContainer.setPadding(new Insets(5,5,5,5));
		broadContainer.setSpacing(8);
		
		txtTitle = new Text("Enter Name Please");
		txtTitle.setFont(Font.font("Arial", FontWeight.BOLD, 14));		
		
		inputName = new TextField();		
		
		strBroadCast = new Button("Start BroadCast");
		strBroadCast.setAlignment(Pos.BOTTOM_CENTER);
		strBroadCast.setOnAction((e)-> { 
			//System.out.println("BroadCastButton works");
			
			nameOfPerson = inputName.getText();
			Platform.runLater(()->
			{
				inputName.clear();
			});			
			
			
			//System.out.println("nameofPerson  " + nameOfPerson);
			
			/**
			 * include if statments 
			 * to check if you have the name's Ip 
			 * and port
			 * 
			 */
			
			//once the button is clicked 
			//check called broadCast method.
			
			if(!nameOfPerson.isEmpty()) {
				try 
				{
					System.out.println("this runs");
					broadcast(nameOfPerson,InetAddress.getByName("255.255.255.255"));
					System.out.println("shit");	
					
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			
			}
			
		});		
		
		broadContainer.getChildren().addAll(txtTitle,inputName,strBroadCast);
		
		
		return broadContainer;	
	}



}
