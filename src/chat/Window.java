package chat;

import java.net.InetAddress;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Window extends BorderPane implements EventHandler<ActionEvent>{

	
	//For the Top part of chat in center region
	private VBox centerContainerVLayout;	
	private TextArea topPartOfChatTextA;	
	
	//For the bottom part of chat in center region
	//private VBox centerRegionVBoxLayout;	
	private TextArea bottomPartOfChatTextA;
	
	//Component button For center regions	
	private Button sendBtn;
	
	//Components For the Left regions to connect to other computer
	private VBox leftRegionVBoxLayout;	
	private TextField destinationIPnumberTextA;
	private TextField destinationPortNumberTextA;
	private Button newChat;
	private Button exitBtn;	
	
	//SOCKET CLASS FROM 
	Socket socket;
	
	//variable holder to get data from chat and Ip number/port
	String senderText = null;
	String destinationIpn = null;
	String destinationPortN = null;
	
	
	public Window(Socket referenceOfSocket)
	{
		//reference to the socket
		this.socket = referenceOfSocket;
		
		
		
		//For the Center regions
		centerContainerVLayout = new VBox();
		centerContainerVLayout.setPadding(new Insets(10));
		centerContainerVLayout.setSpacing(8);
		
		//--------COMPONENTS FOR CENTER-----------
		
		//top part of the Chat on the center Region
		topPartOfChatTextA = new TextArea();
		topPartOfChatTextA.setEditable(false);
		//Bottom part of the chat on the center Region
		bottomPartOfChatTextA = new TextArea();
		bottomPartOfChatTextA.setEditable(true);
		//sendBtn
		sendBtn = new Button("Send");
		//adding all components to the VBox
		centerContainerVLayout.getChildren().addAll(topPartOfChatTextA,bottomPartOfChatTextA,sendBtn);
		
		
		//FOR THE LEFT REGION
		leftRegionVBoxLayout = new VBox();
		centerContainerVLayout.setPadding(new Insets(5));
		centerContainerVLayout.setSpacing(2);
		
		//---COMPONETS FOR LEFT REGION----
		destinationIPnumberTextA = new TextField();
		destinationIPnumberTextA.setPromptText("IP Number"); 
		
		destinationPortNumberTextA = new TextField();
		destinationPortNumberTextA.setPromptText("Port Number"); 
		
		newChat = new Button("Connect");
		exitBtn = new Button("Exit");
		
		//setting the listener of each Button 
		//send Button for the center Region
		sendBtn.setOnAction(this);
		
		//connection Button for the left Region
		newChat.setOnAction(this);
		
		//exit Button for the left Region
		exitBtn.setOnAction(this);
		
		
		//adding components to the left Region to leftRegionVBoxLayout
		leftRegionVBoxLayout.getChildren().addAll(destinationIPnumberTextA,destinationPortNumberTextA,newChat,exitBtn);
		
		
		//Setting components for each region in the BorderPane 
		this.setCenter(centerContainerVLayout);//adding all the components to the center regions
		this.setLeft(leftRegionVBoxLayout);//adding all the components for left region inside the VBox
		
	}	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == sendBtn)
		{
			System.out.println("sendBtn button Works");
			//extract msn from textArea the port Number and ipNumber
			senderText = bottomPartOfChatTextA.getText();
			destinationIpn = destinationIPnumberTextA.getText();
			destinationPortN = destinationPortNumberTextA.getText();
			
			//(sendDataToSocket)send data to hashMap to retrieve who
			//you have to talk to.
			sendDataToSocket();
			//using the send method from socket class
			//reference from socket class been pass 
			//through the constructor
			try 
			{
				socket.send(senderText, InetAddress.getByName(destinationIpn),Integer.valueOf(destinationPortN));
				
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}			
			
		}
		
		else if(event.getSource() == newChat)
		{
			System.out.println("connectBtn button works");
		}
		else if(event.getSource() == exitBtn)
		{
			System.out.println("exitBtn button works");
		}	
		
	}
	
	private void sendDataToSocket()
	{
			this.socket.hashMapDataHolder.put(destinationIpn + destinationPortN,this);
	}
	
	public String getSenderText()
	{
		return this.senderText;
	}
	
	public String getDestinationIpn()
	{
		return this.destinationIpn;
	}
	
	public String getDestinationPortN()
	{
		return destinationPortN;
	}

}
