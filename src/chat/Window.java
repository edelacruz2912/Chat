package chat;

import java.net.InetAddress;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Window  implements EventHandler<ActionEvent>{

	
	
	//NOTE : LA PRIMERA WINDOW CUANDO ES CREADA NO TIENE IP. NEED TO BE FIXED
	
	
	//For the Top part of chat in center region
	private VBox centerContainerVLayout;	
	private TextArea topPartOfChatTextA;	
	
	//For the bottom part of chat in center region
	//private VBox centerRegionVBoxLayout;	
	private TextArea bottomPartOfChatTextA;
	
	//Component button For center regions	
	private Button sendBtn;
	/**
	//Components For the Left regions to connect to other computer
	private VBox leftRegionVBoxLayout;	
	public TextField destinationIPnumberTextA;
	public TextField destinationPortNumberTextA;
	private Button newChat;
	private Button exitBtn;	**/
	
	//SOCKET CLASS FROM 
	private Socket socket;
	
	//variable holder to get data from chat and Ip number/port
	private String senderText = null;
	private String destinationIpn = null;
	private String destinationPortN = null;
	
	Scene newSceneWindow;
	Stage newStageWindow;
	
	BorderPane borderP;
	
	int port;
	
	public Window(Socket referenceOfSocket)
	{
		//reference to the socket
		this.socket = referenceOfSocket;
		
		borderP = new BorderPane();			
				
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
		sendBtn.setDefaultButton(true);
		
		//adding all components to the VBox
		centerContainerVLayout.getChildren().addAll(topPartOfChatTextA,bottomPartOfChatTextA,sendBtn);
		
		
		//FOR THE LEFT REGION
		//leftRegionVBoxLayout = new VBox();
		centerContainerVLayout.setPadding(new Insets(5));
		centerContainerVLayout.setSpacing(2);
		
		//---COMPONETS FOR LEFT REGION----
		//destinationIPnumberTextA = new TextField();
		//destinationIPnumberTextA.setPromptText("IP Number"); 
		
		//destinationPortNumberTextA = new TextField();
		//destinationPortNumberTextA.setPromptText("Port Number"); 
		
		//newChat = new Button("Connect");
		//exitBtn = new Button("Exit");
		
		//setting the listener of each Button 
		//send Button for the center Region
		sendBtn.setOnAction(this);
		
		//connection Button for the left Region
		//newChat.setOnAction(this);
		
		//exit Button for the left Region
		//exitBtn.setOnAction(this);		
		
		//adding components to the left Region to leftRegionVBoxLayout
		//leftRegionVBoxLayout.getChildren().addAll(destinationIPnumberTextA,destinationPortNumberTextA,newChat,exitBtn);
	
		//Setting components for each region in the BorderPane 
		borderP.setCenter(centerContainerVLayout);//adding all the components to the center regions
		//borderP.setLeft(leftRegionVBoxLayout);//adding all the components for left region inside the VBox
		
		//----- FOR	WINDOW -------
		Platform.runLater(() -> 
		{
			newSceneWindow = new Scene(borderP,500,500);		
			newStageWindow = new Stage();
			
			newStageWindow.setTitle("IP: "+ referenceOfSocket.getIncomingIp() + " " + " Port: "+referenceOfSocket.getIncomingPort());
			
			newStageWindow.setScene(newSceneWindow);
			newStageWindow.show();
			//When chat Window is closed.
			newStageWindow.setOnCloseRequest((e) -> {
				Socket.hashMapDataHolder.remove(getIPandPort());
			});
		}); 
		
		
		///-----------------------
		
	}	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == sendBtn)
		{
			System.out.println("sendBtn button Works");
		
			//Getting packet info From GUI
			senderText = bottomPartOfChatTextA.getText();
			//destinationIpn = destinationIPnumberTextA.getText();
			//destinationPortN = destinationPortNumberTextA.getText();
			bottomPartOfChatTextA.clear();				// Clear the bottom input field
			appendTxtToTextArea("Me: " + senderText);	// Append msg from input field
			
			/*
			System.out.println("inside window method :");
			System.out.println("destinationIpn : " + destinationIpn);
			System.out.println("destinationPortN : " + destinationPortN);
			*/
			//sendPackageDToHashM();
					
			
			//If Port and IP is not in the hashMap
			if(!Socket.hashMapDataHolder.containsKey(getIPandPort()))
			{
				sendPackageDToHashM();
			}			
			try 
			{
				socket.send(senderText, InetAddress.getByName(destinationIpn),Integer.valueOf(destinationPortN));
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}			
			
		}		
		
	}
	
	public void setDestIP(String ip) {
		this.destinationIpn = ip;
	}
	public void setDestPort(String port) {
		this.destinationPortN = port;
	}
	/**
	 * Append MSN to the Top Part of the Chat
	 * @param text string MSN.
	 */
	
	public void appendTxtToTextArea(String text)
	{
		Platform.runLater( () -> topPartOfChatTextA.appendText(text + "\n"));
	}
	
	public String getDataUserInput()
	{
		return bottomPartOfChatTextA.getText();
	}
	
	public String getIPandPort() 
	{
		return "Ip:"+ this.destinationIpn + "Port:"+this.destinationPortN;
	}
	
	
	//put package data in the hashMap
	private void sendPackageDToHashM()
	{
		
			Socket.hashMapDataHolder.put("Ip:"+destinationIpn + "Port:"+destinationPortN,this);
			
	}	
	
	public String getPacketInfo()
	{
		return "Ip:"+this.destinationIpn + "Port:"+this.destinationPortN + "MSN:"+this.senderText;
	}
	
	//returning the reference of the window chat.
	public Window getChatWindow()
	{
		return this;
	}
	
	
	
	
	

}
