package Controller;

import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import application.Player;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

public class Game {

	private Player player = new Player(25, 25);

	public void runGame() {

		// this creates the window that will be running the game
		initDisplay();

		// this runs the games actions
		gameLoop();

		// this handles memory issue of if the game is closed
		Display.destroy();

	}

	private static void initDisplay() {
		try {
			Display.setDisplayMode(new DisplayMode(1280, 720));
			Display.create();
			Display.setTitle("Robots are Invading!");
		} catch (LWJGLException e) {
			System.out.println("Display wasnt initialized correctly");
		}
	}

	private void gameLoop() {
		while (!Display.isCloseRequested()) {
			// game code goes here

			glClear(GL_COLOR_BUFFER_BIT); // clears screen before drawing

			player.drawPlayer();
			player.playerMovement();

			if (Mouse.isButtonDown(0)) {
				player.shoot(Mouse.getX(), Mouse.getY());
			}

			if (Display.isCloseRequested()) {
				System.exit(0);
			}

			Display.update(); // draw everything before the screen is updated
			Display.sync(60);
		}
	}

}
