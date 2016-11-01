package Controller;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

public class Game {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private int playerX = 400;
	private int playerY = 400;
	public static AppGameContainer appgc;
	private Texture playerTexture;
	private Texture bulletTexture;

	public void run() {

		initGL(WIDTH, HEIGHT);
		init();
		while (true) { // Game Loop
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			render();
			Display.update();
			Display.sync(60);
			/*******************
			 * GAME LOOP
			 **************************************************/
			playerMovement();
			// if left click is down shoot
			if(Mouse.isButtonDown(0))
				playerShoot();

			if (Display.isCloseRequested()) {
				Display.destroy();
				System.exit(0);
			}
		}
	}

	public void init() {

		try {
			// loading textures to the screen
			playerTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player.png"));
			bulletTexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/spr_laser.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// creating the openGL window
	private void initGL(int width, int height) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Display.setVSyncEnabled(true);
		} catch (LWJGLException e) {
			e.printStackTrace();
			System.exit(0);
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);

		GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

		// enable alpha blending
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glViewport(0, 0, width, height);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);

		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, width, height, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
	}

	// this is drawing the player onto the screen
	public void render() {
		Color.white.bind();
		playerTexture.bind();

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(playerX, playerY);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(playerX + playerTexture.getTextureWidth(), playerY);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(playerX + playerTexture.getTextureWidth(), playerY + playerTexture.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(playerX, playerY + playerTexture.getTextureHeight());
		}
		GL11.glEnd();
	}

	//the nested if statements account for the player gonig off screen
	//the weird numbers for the if statements account for character padding
	public void playerMovement() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if(playerY > -20)
				playerY--;
			
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if(playerY < 560)
				playerY++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if(playerX < 780)
				playerX++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if(playerX > 0)
				playerX--;
		}
	}

	public void playerShoot() {

		Color.white.bind();
		bulletTexture.bind();
		int bulletX = playerX;
		int bulletY = playerY;
		int mouseX = Mouse.getX();
		int mouseY = Mouse.getY();

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(bulletX, bulletY);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(bulletX + bulletTexture.getTextureWidth(), bulletY);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(bulletX + bulletTexture.getTextureWidth(), bulletY + bulletTexture.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(bulletX, bulletY + bulletTexture.getTextureHeight());
		}
		GL11.glEnd();
	}
}
