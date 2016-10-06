package Controller;

import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.LWJGLException;

import static org.lwjgl.opengl.GL11.*;

public class Game {
	
	public static void runGame() {
		
		//this creates the window that will be running the game
		try {
			Display.create();
      //pixel display at 1280x7200
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.setTitle("test");
		} catch (LWJGLException e) {
			System.out.println("Display wasnt initialized correctly");
		}
		
		//this runs the games actions
		while (!Display.isCloseRequested()) {
			Display.update();
      //frames per second and update ratio
			Display.sync(60);
		}
	}

}
