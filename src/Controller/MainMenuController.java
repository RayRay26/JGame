package Controller;



import Model.GameObject;
import Model.Enemy;
import application.MainMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.text.Text;

public class MainMenuController {
	
	@FXML Text/*Field*/ txtField;
	@FXML Button levelsButton, endlessButton, helpButton;
	@FXML ToggleButton sfxToggle, musicToggle;
	
	private MainMenu main;
	
	//connects main class to controller
	public void setMain(MainMenu mainIn){
		main = mainIn;
	}
	
	public void handleButton(){
		
	}
	
	//TODO 
	//Fill in what button calls should do
	public void handleLevelsButton(){
		Game game = new Game();
		game.run();
	}
	
	public void handleEndlessButton(){
		Game game = new Game();
		game.run();
	}
	
	public void handleHelpButton(){
		
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
