package chat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class Window extends BorderPane implements EventHandler<ActionEvent>{

	//Send Button 
	Button sendBtn;
	//Add Button 
	Button addButton;
	
	//Top are for the chat
	TextArea chatTopAreaTextArea;
	
	//Top are for the chat
	TextArea chatBottonAreaTextArea;
	
	//Label for the Ip and port Number
	Label IPandPort;	
	
	//vertical Box for the chatTopArea
	VBox TopWindowChatBoxLayout;
	
	//Horizontal Box for the chatTopArea
	HBox horizontalAddBoxLayout;
	
	//TextArea for IpNumber and PortNumber
	TextArea ipNumberInput;
	TextArea portNumBerInput;	
	
	
	String IpSource = "000.000.0.000";
	String portNumber = "00000";
	
	public Window()
	{
		//chat Button
		sendBtn = new Button("send");
		//add button 
		addButton = new Button("add");		
		//setting the listener to buttons.
		sendBtn.setOnAction(this); 
		addButton.setOnAction(this);
		
		//Label for the IP and port Number
		IPandPort = new Label("Ip: "+ IpSource +  " Port: " + portNumber);
		
		//for the topChatArea
		TopWindowChatBoxLayout = new VBox(8); 		
		//Top area for the chat
		chatTopAreaTextArea = new TextArea();
		chatTopAreaTextArea.setMaxWidth(400);
		//chatTopArea.setMaxHeight(450);
		chatTopAreaTextArea.setEditable(false);
		
		//Bottom area for the chat
		chatBottonAreaTextArea = new TextArea();
		chatBottonAreaTextArea.setMaxHeight(90);
		chatBottonAreaTextArea.setEditable(true);
		
		
		// Adding to the vBox and alignment to the layout Vbox .
		TopWindowChatBoxLayout.setAlignment(Pos.CENTER);
		
		TopWindowChatBoxLayout.getChildren().addAll(IPandPort,chatTopAreaTextArea);
		
		//--------Setting components to BorderRegions---------
		//setting component of the textArea to Top
		this.setTop(TopWindowChatBoxLayout);
		
		//setting component of the textArea to Center
		this.setCenter(chatBottonAreaTextArea);
		
		
		 
		
	}
	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == sendBtn)
		{
			System.out.println("button Works");
		}
		
		else if(event.getSource() == addButton)
		{
			System.out.println("add button works");
		}
		
		
		
		
	}

}
