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
	private int playerX = 600;
	private int playerY = 400;
	public static AppGameContainer appgc;
	private Texture playerTexture;
	private Texture bulletTexture;
	private Texture robotTexture;
	private Texture background;
	public Timer t = new Timer();
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Integer> indexToRemove = new ArrayList<Integer>();

	public void run() {

		initGL(WIDTH, HEIGHT);
		init();
		
		//spawn 20 enemies to begin with
		for(int i = 0; i < 20; i++){
			enemySpawn();
		}
		
		while (true) { // Game Loop
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			
			
			//draws background then player on top of background
			drawBackGround();
			render();
			/*******************
			 * GAME LOOP
			 **************************************************/

			//moves player then checks reletive position of player then moves enemy
			playerMovement();
			enemyMovement();
			
			
			if(Mouse.isButtonDown(1)){
				enemySpawn();
			}
			
			// if left click is down shoot
			if (Mouse.isButtonDown(0))
				playerShoot();
			
			//refreshing and drawing the enemies/bullets
			refreshDrawings();

			
			
			if(enemies.isEmpty()){
				gameOver();
			}
			

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
			background	  = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/floor.png"));
			
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
				playerY-=2;

		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (playerY < 560)
				playerY+=2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (playerX < 780)
				playerX+=2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (playerX > 0)
				playerX-=2;
		}
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
	
	public void playerShoot() {

		Bullet bullet = new Bullet(playerX, playerY, Mouse.getX(), Mouse.getY());
		bullets.add(bullet);
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
		
		//spawning bullets
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).incrementValue();
			bulletDraw(bullets.get(i));
		}
		
		//despawning hit enemies
		for(int i = 0; i < bullets.size(); i++){
			for(int j = 0; j < enemies.size(); j++){
				//if bullet is greater than enemyX but less than enemyX + texture width despawn
				if(bullets.get(i).getCurrentX() + (int)(bulletTexture.getTextureWidth()/2) >= enemies.get(j).getX() - robotTexture.getTextureWidth() && bullets.get(i).getCurrentX() + (int)(bulletTexture.getTextureWidth()/2) <= enemies.get(j).getX()
				&& bullets.get(i).getCurrentY() - (int)(bulletTexture.getTextureHeight()/2) >= enemies.get(j).getY() + robotTexture.getTextureHeight() && bullets.get(i).getCurrentY() - (int)(bulletTexture.getTextureWidth()/2) <= enemies.get(j).getX()){
					enemies.remove(j);//remove enemy if hit
				}
			}
		}
		

		//drawing enemies
		for(int i = 0; i < enemies.size(); i++){
			enemyDraw(enemies.get(i));
		}
		
		
		//remove bullets from array once they are out of bounds/memory conservation
		for(int i = 0; i < bullets.size(); i++){
			if(bullets.get(i).getCurrentX() > 900 || bullets.get(i).getCurrentX() < 0 || bullets.get(i).getCurrentY() < 0 || bullets.get(i).getCurrentY() > 900){
				bullets.remove(i);
			}
		}
		
	}
	
	public void enemyMovement(){
		for(int i = 0; i < enemies.size(); i++){
			//checks position reletive to player then moves enemy
			if(enemies.get(i).getX() < playerX && enemies.get(i).getY() < playerY){
				enemies.get(i).incrementX();
				enemies.get(i).incrementY();
			}
			else if(enemies.get(i).getX() > playerX && enemies.get(i).getY() < playerY){
				enemies.get(i).decrementX();
				enemies.get(i).incrementY();
			}
			else if(enemies.get(i).getX() < playerX && enemies.get(i).getY() > playerY){
				enemies.get(i).incrementX();
				enemies.get(i).decrementY();
			}
			else if(enemies.get(i).getX() > playerX && enemies.get(i).getY() == playerY){
				enemies.get(i).decrementX();
			}
			else if(enemies.get(i).getX() == playerX && enemies.get(i).getY() > playerY){
				enemies.get(i).decrementY();
			}
			else if(enemies.get(i).getX() < playerX && enemies.get(i).getY() == playerY){
				enemies.get(i).incrementX();
			}
			else if(enemies.get(i).getX() == playerX && enemies.get(i).getY() < playerY){
				enemies.get(i).incrementY();
			}	
			else{
				enemies.get(i).decrementX();
				enemies.get(i).decrementY();
			}
		}
	}

	public void drawBackGround(){
		for(int x = 0; x < 800; x += background.getTextureWidth()){
			for(int y = 0; y < 600; y += background.getTextureHeight()){
				background.bind();

				GL11.glBegin(GL11.GL_QUADS);
				{
					GL11.glTexCoord2f(0, 0);
					GL11.glVertex2f(x, y);
					GL11.glTexCoord2f(1, 0);
					GL11.glVertex2f(x + background.getTextureWidth(), y);
					GL11.glTexCoord2f(1, 1);
					GL11.glVertex2f(x + background.getTextureWidth(), y + background.getTextureHeight());
					GL11.glTexCoord2f(0, 1);
					GL11.glVertex2f(x, playerY + background.getTextureHeight());
				}
				GL11.glEnd();
			}
		}
	}
	
	public void gameOver(){
		//TODO GAMEOVER STUFF
	}

	
}
