package chat;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;

public class Window extends BorderPane implements EventHandler<ActionEvent>{

	//Send Button 
	Button sendBtn;
	//Top are for the chat
	TextArea chatTopArea;
	
	//Top are for the chat
	TextArea chatButtonArea;
	
	
	
	
	
	
	
	
	
	
	public Window()
	{
		//chat Button
		sendBtn = new Button("send");
		//setting the listener to the button.
		sendBtn.setOnAction(this); 
		
		//Top area for the chat
		chatTopArea = new TextArea();
		chatTopArea.setEditable(false);
		
		//Bottom area for the chat
		chatButtonArea = new TextArea();
		chatButtonArea.setEditable(true);
		
		
		//setting component of the textArea to Top
		this.setTop(chatTopArea);
		
		//setting component of the textArea to Center
		this.setCenter(chatButtonArea);
		
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
