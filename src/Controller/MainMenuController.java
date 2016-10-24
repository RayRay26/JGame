package Controller;

import Model.GameObject;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Model.Enemy;
import application.MainMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenuController {

	Stage stage = new Stage();
	Scene scene;
	Parent root;
	// @FXML
	// ImageView playerPicture = new ImageView(new
	// Image("/art/player_strip4.png"));
	// @FXML
	// ImageView enemyPicture = new ImageView(new
	// Image("/art/robotmelee_strip4.png"));
	@FXML
	Text/* Field */ txtField;
	@FXML
	Button levelsButton, endlessButton, helpButton, backButton;
	@FXML
	ToggleButton sfxToggle, musicToggle;

	private MainMenu main;

	// connects main class to controller
	public void setMain(MainMenu mainIn) {
		main = mainIn;
	}

	public void handleButton() {

	}

	// TODO
	// Fill in what button calls should do
	public void handleLevelsButton() {
		Game game = new Game();
		game.run();
	}

	public void handleEndlessButton() {
		Game game = new Game();
		game.run();
	}

	public void handleHelpButton() {

		stage = (Stage) helpButton.getScene().getWindow();
		try {
			root = FXMLLoader.load(getClass().getResource("/View/Help.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene = new Scene(root);
		stage.setScene(scene);
	}

	public void handleBackButton() {

		stage = (Stage) backButton.getScene().getWindow();
		try {
			root = FXMLLoader.load(getClass().getResource("/View/MainMenuView.fxml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		scene = new Scene(root);
		stage.setScene(scene);
	}

	
	public void handleMusicToggle() {
//		
//		try{										//this didnt work unless full path for some reason?						//this isnt working unless i put the complete path to the audio file?
//			AudioInputStream ais = AudioSystem.getAudioInputStream(new File("C:/Users/grant/Documents/CSCI3300/CSCI3300_Project/src/art/test.wav"));
//			Clip audio = AudioSystem.getClip();
//			if(musicToggle.isSelected()){
//				audio.start();;
//			}
//			else{
//				audio.stop();
//			}
//		}catch(Exception e){
//			System.out.println("Error with audio");
//			e.printStackTrace();
//		}
	}
	
	private boolean isSFXOn = true;

	public void handleSFXToggle() {
		if (sfxToggle.isDisabled()) {
			isSFXOn = false;
		}
	}
	
	public boolean getisSFXOn(){
		return isSFXOn;
	}



}
