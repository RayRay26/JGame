package Controller;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Model.Enemy;
import View.SoundController;
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
		Game.level = 4;
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

	public static boolean isSFXOn = true;
	public static boolean isMusicOn = true;

	public void handleSFXToggle() {
		isSFXOn = !isSFXOn;
		sfxToggle.setText("Sound OFF");
		if (isSFXOn)
			sfxToggle.setText("Sound ON");
	}

	public void handleMusicToggle() {
		isMusicOn = !isMusicOn;
		musicToggle.setText("Music OFF");
		SoundController.musicVolume = 0f;
		if (isMusicOn) {
			musicToggle.setText("Music ON");
			SoundController.musicVolume = 1f;
		}
	}

}