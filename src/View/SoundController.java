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
	public static Audio music;
	public static float musicVolume = 1f;
	static int musicInstance;

	public static void playSoundWithRandomPitch(Audio sound) {
		if(MainMenuController.isSFXOn)
			sound.playAsSoundEffect(0.9f + ThreadLocalRandom.current().nextFloat() * 0.2f, 1.0f, false);
	}
	
	public static void playSoundWithRandomPitch(Audio sound, float gain) {
		if(MainMenuController.isSFXOn)
			sound.playAsSoundEffect(0.9f + ThreadLocalRandom.current().nextFloat() * 0.2f, gain, false);
	}
	
	public static void playMusic() {
		musicInstance = music.playAsMusic(1f, musicVolume, true);
	}
	
	public static void continueMusic() {
        // polling is required to allow streaming to get a chance to
        // queue buffers.
        SoundStore.get().poll(0);
        SoundStore.get().setMusicVolume(musicVolume);
	}
}
