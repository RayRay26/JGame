package Controller;



import application.MainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class MainMenuController {
	
	@FXML TextField txtField;
	@FXML Button levelsButton, endlessButton, helpButton;
	@FXML ToggleButton sfxToggle, musicToggle;
	
	private MainMenu main;
	
	//connects main class to controller
	public void setMain(MainMenu mainIn){
		main = mainIn;
	}
	
	//TODO 
	//Fill in what button calls should do
	public void handleButton(){
		if(levelsButton.isPressed()){
			//run game
		}
		else if(endlessButton.isPressed()){
			//run endless game
		}
		else if(helpButton.isPressed()){
			//toggle to help screen
			
		}
	}
	
	//handles toggle buttons
	public void handleToggle(){
		if(sfxToggle.isDisabled()){
			//sfx stops
			//*****also the style toggle is not working****
			sfxToggle.setStyle("-fx-background-color: red;");
		}else{
			//sfx plays
		}
		
		if(musicToggle.isDisabled()){
			//music stops
		}else{
			//music plays
		}
		
	}
	

}
