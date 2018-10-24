package chat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Window extends BorderPane implements EventHandler<ActionEvent>{

	//Send Button 
	Button sendBtn;
	//Top are for the chat
	TextArea chatTopArea;
	
	//Top are for the chat
	TextArea chatBottonArea;
	
	//Label for the Ip and port Number
	Label IPandPort;	
	
	//vertical Box for the chatTopArea
	VBox virtualChatBox;
	
	//Horizontal Box for the chatTopArea
	HBox horizontalChatBox;
	
	public Window()
	{
		//chat Button
		sendBtn = new Button("send");
		//setting the listener to the button.
		sendBtn.setOnAction(this); 
		
		//for the topChatArea
		virtualChatBox = new VBox(20); 
		//for the centerChatArea
		//horizontalChatBox = new HBox(20);
		
		//Top area for the chat
		chatTopArea = new TextArea();
		chatTopArea.setEditable(false);
		
		//Bottom area for the chat
		chatBottonArea = new TextArea();
		chatBottonArea.setEditable(true);
		
		////Label for the IP and port Number
		IPandPort = new Label("Ip: 000.000.0.000 Port: 00 000");
		
		// Adding to the vBox.
		virtualChatBox.getChildren().addAll(IPandPort,chatTopArea);
		// Adding to the hBox.
		//horizontalChatBox.getChildren().addAll(chatButtonArea,);
		
		//--------Setting components to BorderRegions---------
		//setting component of the textArea to Top
		this.setTop(virtualChatBox);
		
		//setting component of the textArea to Center
		this.setCenter(chatBottonArea);
		
		//setting components to the regions in the BorderPane
		this.setBottom(sendBtn);
	}
	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == sendBtn)
		{
			System.out.println("button Works");
		}
		
		
		
	}

}
