package chat;

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
	private TextField IPnumberTextA;
	private TextField PortNumberTextA;
	private Button connectBtn;
	private Button exitBtn;	
	
	
	
	public Window()
	{
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
		IPnumberTextA = new TextField();
		PortNumberTextA = new TextField();
		
		connectBtn = new Button("Connect");
		exitBtn = new Button("Exit");
		
		//adding components to the left Region to leftRegionVBoxLayout
		leftRegionVBoxLayout.getChildren().addAll(IPnumberTextA,PortNumberTextA,connectBtn,exitBtn);
		
		
		
		//Setting components for each region in the BorderPane 
		this.setCenter(centerContainerVLayout);//adding all the components to the center regions
		this.setLeft(leftRegionVBoxLayout);//adding all the components for left region inside the VBox
		
	}
	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
		if(event.getSource() == sendBtn)
		{
			System.out.println("sendbutton Works");
		}
		
		else if(event.getSource() == connectBtn)
		{
			System.out.println("connectBtn button works");
		}
		else if(event.getSource() == exitBtn)
		{
			System.out.println("exitBtn button works");
		}
		
		
		
	}

}
