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

	
	
	//For the Top part of chat in center region
	private VBox centerContainerVLayout;	
	private TextArea topPartOfChatTextA;	
	
	//For the bottom part of chat in center region
	//private VBox centerRegionVBoxLayout;	
	private TextArea bottomPartOfChatTextA;
	
	//Component button For center regions	
	private Button sendBtn;
	
	
	//SOCKET CLASS FROM 
	private Socket socket;
	
	//variable holder to get data from chat and Ip number/port
	private String senderText = null;
	private String destinationIpn = null;
	private String destinationPortN = null;
	
	public Scene newSceneWindow;
	public Stage newStageWindow;
	
	BorderPane borderP;
	
	int port;
	
	InetAddress testIP;
	
	public Window(Socket referenceOfSocket)
	{
		//reference to the socket
		this.socket = referenceOfSocket;
		
		borderP = new BorderPane();
		borderP.setPadding(new Insets(15));
				
		//For the Center regions
		centerContainerVLayout = new VBox();
		//centerContainerVLayout.setPadding(new Insets(50));
		centerContainerVLayout.setSpacing(10);
		
		
		//--------COMPONENTS FOR CENTER-----------
		
		//top part of the Chat on the center Region
		topPartOfChatTextA = new TextArea();
		topPartOfChatTextA.setEditable(false);
		topPartOfChatTextA.setId("topTxtArea"); //Setting the ID for the CSS.
		//System.out.println("HEIGHT " + topPartOfChatTextA.getHeight() +" WIDTH " +topPartOfChatTextA.getWidth());
		topPartOfChatTextA.setMaxHeight(480);
		topPartOfChatTextA.setMaxWidth(500);
		
		
		//Bottom part of the chat on the center Region
		bottomPartOfChatTextA = new TextArea();
		bottomPartOfChatTextA.setEditable(true);
		bottomPartOfChatTextA.setId("bottomTxtArea"); //Setting the ID for the CSS. 
		bottomPartOfChatTextA.setMaxHeight(100);
		//bottomPartOfChatTextA.setPadding(new Insets(10));
		//sendBtn
		sendBtn = new Button("Send");
		sendBtn.setDefaultButton(true);
		
		//adding all components to the VBox
		centerContainerVLayout.getChildren().addAll(topPartOfChatTextA,bottomPartOfChatTextA,sendBtn);
		
				
		//setting the listener of each Button 
		//send Button for the center Region
		sendBtn.setOnAction(this);
		
	
		//Setting components for each region in the BorderPane 
		borderP.setCenter(centerContainerVLayout);//adding all the components to the center regions
		//borderP.setLeft(leftRegionVBoxLayout);//adding all the components for left region inside the VBox
		
		//----- FOR	WINDOW -------
		Platform.runLater(() -> 
		{
			newSceneWindow = new Scene(borderP,500,300); //scene of the chat Window		
			newSceneWindow.getStylesheets().add("chat/Window.css");
			
			newStageWindow = new Stage(); //Stage of the chat Window
			
			newStageWindow.setTitle("IP: "+ referenceOfSocket.getIncomingIp() + " " + " Port: "+referenceOfSocket.getIncomingPort());
			
			newStageWindow.setScene(newSceneWindow);
			newStageWindow.show();
			//When you want to close chat Window.
			newStageWindow.setOnCloseRequest((e) -> {
				Socket.hashMapDataHolder.remove(getIPandPort());
			});
		}); 
		
	
		
	}	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == sendBtn)
		{
			System.out.println("sendBtn button Works");
		
			//Getting packet info From GUI
			senderText = bottomPartOfChatTextA.getText();
			
			bottomPartOfChatTextA.clear();				// Clear the bottom input field
			appendTxtToTextArea("Me: " + senderText);	// Append msg from input field
							
			
			//If Port and IP is not in the hashMap
			if(!Socket.hashMapDataHolder.containsKey(getIPandPort()))
			{
				sendPackageDToHashM();
			}	
			
			try 
			{
				testIP =  InetAddress.getByName(destinationIpn);
				
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}			
			System.out.println("destinationPortN in sender " + destinationPortN);
			System.out.println("destinationPortN in sender with converter " + Integer.valueOf(destinationPortN));
			socket.send(senderText, testIP,Integer.valueOf(destinationPortN));
		}		
		
	}
	
	public void updateTitle()
	{
		newStageWindow.setTitle("IP: "+ this.destinationIpn + " " + " Port: "+this.destinationPortN);
	}
	
	
	public void setDestIP(String ip) {
		this.destinationIpn = ip;
		System.out.println("setDestinationIPOnWindowClass " + this.destinationIpn);
	}
	public void setDestPort(String port) {
		this.destinationPortN = port;
		System.out.println("setDestinationPortOnWindowClass " + this.destinationPortN);
	}
	/**
	 * Append MSN to the Top Part of the Chat
	 * @param text string MSN.
	 */
	
	public void appendTxtToTextArea(String text)
	{
		Platform.runLater( () -> topPartOfChatTextA.appendText(text + "\n"));
	}
	
	/**
	 * Method that gets the user input from the Chat.
	 * @return a String 
	 */
	
	public String getDataUserInput()
	{
		return bottomPartOfChatTextA.getText();
	}
	
	/**
	 * return the IP destination and port destination.
	 * @return String
	 */
	
	public String getIPandPort() 
	{
		return "Ip:"+ this.destinationIpn + "Port:"+this.destinationPortN;
	}
	
	
	/**
	 * Insert data inside of the hasHmap To keep track of the windows 
	 * opened.
	 */
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
