package View;

import java.util.concurrent.ThreadLocalRandom;

import org.lwjgl.openal.AL;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import Controller.MainMenuController;

public class SoundController {
	public static Audio shoot;
	public static Audio step0;
	public static Audio step1;
	public static Audio hitEnemy;
	public static Audio hitPlayer;

	public static void playSoundWithRandomPitch(Audio sound) {
		if(MainMenuController.isSFXOn)
			sound.playAsSoundEffect(0.9f + ThreadLocalRandom.current().nextFloat() * 0.2f, 1.0f, false);
	}
	
	public static void playSoundWithRandomPitch(Audio sound, float gain) {
		if(MainMenuController.isSFXOn)
			sound.playAsSoundEffect(0.9f + ThreadLocalRandom.current().nextFloat() * 0.2f, gain, false);
	}
}
