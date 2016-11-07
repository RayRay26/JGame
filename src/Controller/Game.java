package Controller;

import java.math.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
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

import Model.Bullet;
import Model.Enemy;

public class Game {

	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	private int playerX = 400;
	private int playerY = 400;
	public static AppGameContainer appgc;
	private Texture playerTexture;
	private Texture bulletTexture;
	private Texture robotTexture;
	public Timer t = new Timer();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();

	public void run() {

		initGL(WIDTH, HEIGHT);
		init();
		
		while (true) { // Game Loop
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			render();
			/*******************
			 * GAME LOOP
			 **************************************************/

			
			playerMovement();
			
			
			if(Mouse.isButtonDown(1)){
				enemySpawn();
			}
			
			// if left click is down shoot
			if (Mouse.isButtonDown(0))
				playerShoot();
			
			//refreshing and drawing the enemies/bullets
			refreshDrawings();

			

			/*******************
			 * END GAME LOOP
			 **************************************************/
			Display.update();
			Display.sync(60);
			

			

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
			robotTexture  = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/robot.png"));

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

	// the nested if statements account for the player gonig off screen
	// the weird numbers for the if statements account for character padding
	public void playerMovement() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (playerY > -20)
				playerY--;

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (playerY < 560)
				playerY++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (playerX < 780)
				playerX++;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (playerX > 0)
				playerX--;
		}
	}

	public void playerShoot() {

		Bullet bullet = new Bullet(playerX, playerY, Mouse.getX(), Mouse.getY());
		bullets.add(bullet);
	}

	public void enemySpawn() {
		int x, y;
		do {//an attempt to not spawn the robot on the enemy
			x = (int) Math.floor(Math.random() * 801);
			y = (int) Math.floor(Math.random() * 601);
		} while (x + 10 > playerX && y > playerX + 10);
		Enemy robot = new Enemy(x, y);
		enemies.add(robot);
		enemyDraw(robot);
	}
	
	public void bulletDraw(Bullet bullet){

		bulletTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(bullet.getCurrentX(), bullet.getCurrentY());
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(bullet.getCurrentX() + bulletTexture.getTextureWidth(), bullet.getCurrentY());
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(bullet.getCurrentX() + bulletTexture.getTextureWidth(), bullet.getCurrentY() + bulletTexture.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(bullet.getCurrentX(), bullet.getCurrentY() + bulletTexture.getTextureHeight());
		}
		GL11.glEnd();
	}
	
	public void enemyDraw(Enemy robot){
		
		robotTexture.bind();
		
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(robot.getX(), robot.getY());
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(robot.getX() + robotTexture.getTextureWidth(), robot.getY());
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(robot.getX() + robotTexture.getTextureWidth(), robot.getY() + robotTexture.getTextureHeight());
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(robot.getX(), robot.getY() + robotTexture.getTextureHeight());
		}
		GL11.glEnd();
	}
	
	public void refreshDrawings(){
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).incrementValue();
			bulletDraw(bullets.get(i));
		}
		for(int i = 0; i < enemies.size(); i++){
			enemyDraw(enemies.get(i));
		}
	}
	
}
