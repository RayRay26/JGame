package Controller;

import java.math.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.io.Console;
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
import Model.Player;
import View.UI;

public class Game {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	private Player player;
	public static AppGameContainer appgc;
	private Texture background;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int timer = 0;

	public void run() {

		initGL(WIDTH * 2, HEIGHT * 2);
		GL11.glScaled(2, 2, 1);
		init();

		while (true) { // Game Loop
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);

			// draws background then player on top of background
			drawBackGround();
			drawPlayer();
			// draws health meter
			UI.drawHealth(player.getHealth());
			/*******************
			 * GAME LOOP
			 **************************************************/

			// moves player then checks relative position of player then moves
			// enemy
			playerMovement();
			enemyMovement();

			// timer increases every frame aka 60/sec
			if (timer < 6000) {
				if (timer % 100 == 0)
					enemySpawn();
			} else if (timer < 8000) {
				if (timer % 50 == 0)
					enemySpawn();
			} else {
				if (timer % 25 == 0)
					enemySpawn();
			}

			player.update();
			
			// if left click is down shoot
			if (Mouse.isButtonDown(0)) {
				Bullet bullet = player.shoot();
				if(bullet != null)
					bullets.add(bullet);
			}

			// refreshing and drawing the enemies/bullets
			refreshDrawings();
			boolean wasHit = checkPlayerHit();
			if (wasHit) {
				player.takeDamage();
			}

			if (player.getHealth() <= 0) {
				gameOver();
			}

			timer++;
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
			Player.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player.png"));
			Bullet.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/spr_laser.png"));
			Enemy.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/robot.png"));
			background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/floor.png"));
			UI.health5 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar5.png"));
			UI.health4 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar4.png"));
			UI.health3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar3.png"));
			UI.health2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar2.png"));
			UI.health1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar1.png"));
			UI.health0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar0.png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		player = new Player(300, 200);
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
	public void drawPlayer() {
		player.draw();
	}

	// the nested if statements account for the player gonig off screen
	// the weird numbers for the if statements account for character padding
	public void playerMovement() {
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (player.getY() > 20)
				player.setY(player.getY() - 2);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (player.getX() > 20)
				player.setX(player.getX() - 2);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (player.getY() < HEIGHT - 20)
				player.setY(player.getY() + 2);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (player.getX() < WIDTH - 20)
				player.setX(player.getX() + 2);
		}
	}

	public void enemySpawn() {
		int x, y;
		do {// an attempt to not spawn the robot on the enemy
			x = (int) Math.floor(Math.random() * WIDTH);
			y = (int) Math.floor(Math.random() * HEIGHT);
		} while (Math.sqrt(Math.pow(x - player.getX(), 2) + Math.pow(y - player.getY(), 2)) < 80);
		Enemy robot = new Enemy(x, y);
		enemies.add(robot);
		robot.draw();
	}

	public void playerShoot() {

		
	}

	public void refreshDrawings() {

		// spawning bullets
		for (int i = 0; i < bullets.size(); i++) {
			bullets.get(i).incrementValue();
			bullets.get(i).draw();
		}

		// despawning hit enemies
		int bulletWidth = Bullet.texture.getTextureWidth();
		int bulletHeight = Bullet.texture.getTextureHeight();
		int robotWidth = Enemy.texture.getTextureWidth();
		int robotHeight = Enemy.texture.getTextureHeight();
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				// if bullet is greater than enemyX but less than enemyX +
				// texture width despawn
				if (bullets.get(i).getCurrentX() - (int) (bulletWidth / 2f) >= enemies.get(j).getX()
						- (int) (robotWidth / 2f)
						&& bullets.get(i).getCurrentX() + (int) (bulletWidth / 2f) <= enemies.get(j).getX()
								+ (int) (robotWidth / 2f)
						&& bullets.get(i).getCurrentY() - (int) (bulletHeight / 2f) >= enemies.get(j).getY()
								- (int) (robotHeight / 2f)
						&& bullets.get(i).getCurrentY() + (int) (bulletHeight / 2f) <= enemies.get(j).getY()
								+ (int) (robotHeight / 2f)) {
					enemies.remove(j);// remove enemy if hit
				}
			}
		}

		// drawing enemies
		for (int i = 0; i < enemies.size(); i++) {
			enemies.get(i).draw();
		}

		// remove bullets from array once they are out of bounds/memory
		// conservation
		for (int i = 0; i < bullets.size(); i++) {
			if (bullets.get(i).getCurrentX() > WIDTH + 100 || bullets.get(i).getCurrentX() < -100
					|| bullets.get(i).getCurrentY() < -100 || bullets.get(i).getCurrentY() > HEIGHT + 100) {
				bullets.remove(i);
			}
		}

	}

	public void enemyMovement() {
		for (int i = 0; i < enemies.size(); i++) {
			// checks position reletive to player then moves enemy
			if (enemies.get(i).getX() < player.getX() && enemies.get(i).getY() < player.getY()) {
				enemies.get(i).incrementX();
				enemies.get(i).incrementY();
			} else if (enemies.get(i).getX() > player.getX() && enemies.get(i).getY() < player.getY()) {
				enemies.get(i).decrementX();
				enemies.get(i).incrementY();
			} else if (enemies.get(i).getX() < player.getX() && enemies.get(i).getY() > player.getY()) {
				enemies.get(i).incrementX();
				enemies.get(i).decrementY();
			} else if (enemies.get(i).getX() > player.getX() && enemies.get(i).getY() == player.getY()) {
				enemies.get(i).decrementX();
			} else if (enemies.get(i).getX() == player.getX() && enemies.get(i).getY() > player.getY()) {
				enemies.get(i).decrementY();
			} else if (enemies.get(i).getX() < player.getX() && enemies.get(i).getY() == player.getY()) {
				enemies.get(i).incrementX();
			} else if (enemies.get(i).getX() == player.getX() && enemies.get(i).getY() < player.getY()) {
				enemies.get(i).incrementY();
			} else {
				enemies.get(i).decrementX();
				enemies.get(i).decrementY();
			}
		}
	}

	public void drawBackGround() {
		for (int x = 0; x < 800; x += background.getTextureWidth()) {
			for (int y = 0; y < 600; y += background.getTextureHeight()) {
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
					GL11.glVertex2f(x, y + background.getTextureHeight());
				}
				GL11.glEnd();
			}
		}
	}

	public void gameOver() {
		// TODO GAMEOVER STUFF
		System.exit(0);
	}

	public boolean checkPlayerHit() {
		boolean isHit = false;

		for (int i = 0; i < enemies.size(); i++) {
			if (enemies.get(i).getX() == player.getX() && enemies.get(i).getY() == player.getY()) {
				enemies.remove(i);
				isHit = true;
			}
		}

		return isHit;
	}
}
