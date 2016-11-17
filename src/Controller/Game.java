package Controller;

import java.math.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.io.Console;
import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.openal.AL;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Color;
import org.newdawn.slick.Input;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.openal.AudioLoader;
import org.newdawn.slick.openal.SoundStore;
import org.newdawn.slick.util.ResourceLoader;

import Model.Bullet;
import Model.Enemy;
import Model.Player;
import View.Screen;
import View.SoundController;
import View.UI;

public class Game {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	private Player player;
	public static AppGameContainer appgc;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private int timer = 0;

	public void run() {

		initGL(WIDTH * 2, HEIGHT * 2);
		GL11.glScaled(2, 2, 1);
		init();

		while (true) { // Game Loop

			// draws health meter
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

			for(int repeat = 0; repeat < 2; repeat++) {
				for (int i = 0; i < bullets.size(); i++) {
					bullets.get(i).incrementValue();
				}
				performBulletCollisions();
			}

			boolean wasHit = checkPlayerHit();
			if (wasHit) {
				player.takeDamage();
				SoundController.playSoundWithRandomPitch(SoundController.hitPlayer);
			}

			if (player.getHealth() <= 0) {
				gameOver();
			}

			timer++;
			/*******************
			 * END GAME LOOP
			 **************************************************/
			/*******************
			 * DRAW
			 */
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
			Screen.drawBackGround();
			for (int i = 0; i < bullets.size(); i++) {
				bullets.get(i).draw();
			}
			for (int i = 0; i < enemies.size(); i++) {
				enemies.get(i).draw();
			}
			player.draw();
			UI.drawHealth(player.getHealth());
			UI.drawOverheat(player.getOverheat() / player.getMaxOverheat());
			/*******************
			 * END DRAW
			 */
			Display.update();
			Display.sync(60);

			if (Display.isCloseRequested()) {
				Display.destroy();
				AL.destroy();
				System.exit(0);
			}
		}
	}

	public void init() {

		try {
			// loading textures to the screen
			Player.texture0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player0.png"));
			Player.texture1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player1.png"));
			Player.texture3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player3.png"));
			Bullet.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/spr_laser.png"));
			Enemy.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/robot.png"));
			Screen.background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/floor.png"));
			UI.health5 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar5.png"));
			UI.health4 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar4.png"));
			UI.health3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar3.png"));
			UI.health2 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar2.png"));
			UI.health1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar1.png"));
			UI.health0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/healthBar0.png"));
			UI.overheatFill = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/overheatFill.png"));
			UI.overheatBack = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/overheatBack.png"));
			SoundController.shoot = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/shootGun.wav"));
			SoundController.step0 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/step0.wav"));
			SoundController.step1 = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/step1.wav"));
			SoundController.hitEnemy = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/hitEnemy.wav"));
			SoundController.hitPlayer = AudioLoader.getAudio("WAV", ResourceLoader.getResourceAsStream("sound/hitPlayer.wav"));
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

	// the nested if statements account for the player gonig off screen
	// the weird numbers for the if statements account for character padding
	public void playerMovement() {
		int dx = 0;
		int dy = 0;
		if (Keyboard.isKeyDown(Keyboard.KEY_W) || Keyboard.isKeyDown(Keyboard.KEY_UP)) {
			if (player.getY() > 20)
				dy -= 2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A) || Keyboard.isKeyDown(Keyboard.KEY_LEFT)) {
			if (player.getX() > 20)
				dx -= 2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S) || Keyboard.isKeyDown(Keyboard.KEY_DOWN)) {
			if (player.getY() < HEIGHT - 20)
				dy += 2;
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D) || Keyboard.isKeyDown(Keyboard.KEY_RIGHT)) {
			if (player.getX() < WIDTH - 20)
				dx += 2;
		}
		player.move(dx, dy);
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
	
	public void performBulletCollisions() {
		// despawning hit enemies
		int bulletSize = Bullet.texture.getTextureHeight();
		int robotWidth = Enemy.texture.getTextureWidth();
		int robotHeight = Enemy.texture.getTextureHeight();
		for (int i = 0; i < bullets.size(); i++) {
			for (int j = 0; j < enemies.size(); j++) {
				// if bullet is greater than enemyX but less than enemyX +
				// texture width despawn
				if (bullets.get(i).getCurrentX() - (int) (bulletSize / 2f) >= enemies.get(j).getX()
						- (int) (robotWidth / 2f)
						&& bullets.get(i).getCurrentX() + (int) (bulletSize / 2f) <= enemies.get(j).getX()
								+ (int) (robotWidth / 2f)
						&& bullets.get(i).getCurrentY() - (int) (bulletSize / 2f) >= enemies.get(j).getY()
								- (int) (robotHeight / 2f)
						&& bullets.get(i).getCurrentY() + (int) (bulletSize / 2f) <= enemies.get(j).getY()
								+ (int) (robotHeight / 2f)) {
					SoundController.playSoundWithRandomPitch(SoundController.hitEnemy);
					enemies.remove(j);// remove enemy if hit
					bullets.remove(i);// destroy bullet
					i--;// don't skip the next bullet
					break;// move on to the next bullet
				}
			}
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
				enemies.get(i).setRotation(315);
			} else if (enemies.get(i).getX() > player.getX() && enemies.get(i).getY() < player.getY()) {
				enemies.get(i).decrementX();
				enemies.get(i).incrementY();
				enemies.get(i).setRotation(225);
			} else if (enemies.get(i).getX() < player.getX() && enemies.get(i).getY() > player.getY()) {
				enemies.get(i).incrementX();
				enemies.get(i).decrementY();
				enemies.get(i).setRotation(45);
			} else if (enemies.get(i).getX() > player.getX() && enemies.get(i).getY() == player.getY()) {
				enemies.get(i).decrementX();
				enemies.get(i).setRotation(180);
			} else if (enemies.get(i).getX() == player.getX() && enemies.get(i).getY() > player.getY()) {
				enemies.get(i).decrementY();
				enemies.get(i).setRotation(90);
			} else if (enemies.get(i).getX() < player.getX() && enemies.get(i).getY() == player.getY()) {
				enemies.get(i).incrementX();
				enemies.get(i).setRotation(0);
			} else if (enemies.get(i).getX() == player.getX() && enemies.get(i).getY() < player.getY()) {
				enemies.get(i).incrementY();
				enemies.get(i).setRotation(270);
			} else {
				enemies.get(i).decrementX();
				enemies.get(i).decrementY();
				enemies.get(i).setRotation(135);
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
			if (enemies.get(i).getX() >= player.getX() - 4
					&& enemies.get(i).getX() < player.getX() + 4
					&& enemies.get(i).getY() >= player.getY() - 4
					&& enemies.get(i).getY() < player.getY() + 4) {
				enemies.remove(i);
				isHit = true;
			}
		}

		return isHit;
	}
}
