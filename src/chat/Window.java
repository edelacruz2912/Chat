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

	
	//For the Top regions
	VBox topRegionVBoxLayout;	
	TextArea topRegionTextA;	
	
	//For the Center regions
	VBox centerRegionVBoxLayout;	
	TextArea centerRegionTextA;	
	
	//For the Left regions
	VBox leftRegionVBoxLayout;	
	TextArea leftRegionTextA;
	Button connect;
	Button exit;	
	
	//For the Bottom regions	
	Button send;
	
	public Window()
	{
		
		
		
		 
		
	}
	
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		
	/*if(event.getSource() == )
		{
			System.out.println("button Works");
		}
		
		else if(event.getSource() == )
		{
			System.out.println("add button works");
		}
	*/	
		
		
		
	}

}
