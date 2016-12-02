package Controller;

import java.math.*;
import java.awt.Rectangle;
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
import Model.Wall;
import View.Screen;
import View.SoundController;
import View.UI;
import application.ExtraMath;

public class Game {

	public static final int WIDTH = 400;
	public static final int HEIGHT = 300;
	public static int level = 1;
	private Player player;
	private int enemiesKilled;
	public static AppGameContainer appgc;
	private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Wall> walls = new ArrayList<Wall>();
	public static int timer = 0;

	public void init() {

		try {
			// loading textures to the screen
			Player.texture0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player0.png"));
			Player.texture1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player1.png"));
			Player.texture3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/player3.png"));
			Player.textureWidth = Player.texture0.getTextureWidth();
			Bullet.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/spr_laser.png"));
			Bullet.textureWidth = Bullet.texture.getTextureWidth();
			Bullet.textureHeight = Bullet.texture.getTextureHeight();
			Enemy.texture0 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/robot0.png"));
			Enemy.texture1 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/robot1.png"));
			Enemy.texture3 = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/robot3.png"));
			Enemy.textureWidth = Enemy.texture0.getTextureWidth();
			Screen.background = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/floor.png"));
			Wall.texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("art/wall.png"));
			Wall.textureWidth = Wall.texture.getTextureWidth();
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
			SoundController.music = AudioLoader.getStreamingAudio("OGG", ResourceLoader.getResource("sound/music.ogg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		enemiesKilled = 0;
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
	
	private void loadLevel(int level) {
		walls.clear();
		enemies.clear();
		bullets.clear();
		enemiesKilled = 0;
		timer = 0;
		
		switch(level) {
		case 1:
			player = new Player(200, 150);
			walls.add(new Wall(0, 80));
			walls.add(new Wall(16, 80));
			walls.add(new Wall(32, 80));
			walls.add(new Wall(48, 80));
			walls.add(new Wall(0, 96));
			walls.add(new Wall(16, 96));
			walls.add(new Wall(32, 96));
			walls.add(new Wall(48, 96));
			
			walls.add(new Wall(336, 80));
			walls.add(new Wall(352, 80));
			walls.add(new Wall(368, 80));
			walls.add(new Wall(384, 80));
			walls.add(new Wall(336, 96));
			walls.add(new Wall(352, 96));
			walls.add(new Wall(368, 96));
			walls.add(new Wall(384, 96));
			
			walls.add(new Wall(0, 192));
			walls.add(new Wall(16, 192));
			walls.add(new Wall(32, 192));
			walls.add(new Wall(48, 192));
			walls.add(new Wall(0, 208));
			walls.add(new Wall(16, 208));
			walls.add(new Wall(32, 208));
			walls.add(new Wall(48, 208));
			
			walls.add(new Wall(336, 192));
			walls.add(new Wall(352, 192));
			walls.add(new Wall(368, 192));
			walls.add(new Wall(384, 192));
			walls.add(new Wall(336, 208));
			walls.add(new Wall(352, 208));
			walls.add(new Wall(368, 208));
			walls.add(new Wall(384, 208));

			walls.add(new Wall(128, 128));
			walls.add(new Wall(144, 128));
			walls.add(new Wall(128, 144));
			walls.add(new Wall(144, 144));
			walls.add(new Wall(128, 160));
			walls.add(new Wall(144, 160));

			walls.add(new Wall(240, 128));
			walls.add(new Wall(256, 128));
			walls.add(new Wall(240, 144));
			walls.add(new Wall(256, 144));
			walls.add(new Wall(240, 160));
			walls.add(new Wall(256, 160));
		break;
		case 2:
			player = new Player(200, 150);
			walls.add(new Wall(144, 96));
			walls.add(new Wall(160, 96));
			walls.add(new Wall(176, 96));
			walls.add(new Wall(192, 96));
			walls.add(new Wall(208, 96));
			walls.add(new Wall(224, 96));
			walls.add(new Wall(240, 96));
			walls.add(new Wall(144, 192));
			walls.add(new Wall(160, 192));
			walls.add(new Wall(176, 192));
			walls.add(new Wall(192, 192));
			walls.add(new Wall(208, 192));
			walls.add(new Wall(224, 192));
			walls.add(new Wall(240, 192));

			walls.add(new Wall(96, 96));
			walls.add(new Wall(96, 112));
			walls.add(new Wall(96, 128));
			walls.add(new Wall(96, 144));
			walls.add(new Wall(96, 160));
			walls.add(new Wall(96, 176));
			walls.add(new Wall(96, 192));
			walls.add(new Wall(288, 96));
			walls.add(new Wall(288, 112));
			walls.add(new Wall(288, 128));
			walls.add(new Wall(288, 144));
			walls.add(new Wall(288, 160));
			walls.add(new Wall(288, 176));
			walls.add(new Wall(288, 192));

			walls.add(new Wall(96, 48));
			walls.add(new Wall(112, 48));
			walls.add(new Wall(128, 48));
			walls.add(new Wall(144, 48));
			walls.add(new Wall(160, 48));
			walls.add(new Wall(176, 48));
			walls.add(new Wall(192, 48));
			walls.add(new Wall(208, 48));
			walls.add(new Wall(224, 48));
			walls.add(new Wall(240, 48));
			walls.add(new Wall(256, 48));
			walls.add(new Wall(272, 48));
			walls.add(new Wall(288, 48));
			walls.add(new Wall(96, 240));
			walls.add(new Wall(112, 240));
			walls.add(new Wall(128, 240));
			walls.add(new Wall(144, 240));
			walls.add(new Wall(160, 240));
			walls.add(new Wall(176, 240));
			walls.add(new Wall(192, 240));
			walls.add(new Wall(208, 240));
			walls.add(new Wall(224, 240));
			walls.add(new Wall(240, 240));
			walls.add(new Wall(256, 240));
			walls.add(new Wall(272, 240));
			walls.add(new Wall(288, 240));

			walls.add(new Wall(48, 48));
			walls.add(new Wall(48, 64));
			walls.add(new Wall(48, 80));
			walls.add(new Wall(48, 96));
			walls.add(new Wall(48, 112));
			walls.add(new Wall(48, 128));
			walls.add(new Wall(48, 144));
			walls.add(new Wall(48, 160));
			walls.add(new Wall(48, 176));
			walls.add(new Wall(48, 192));
			walls.add(new Wall(48, 208));
			walls.add(new Wall(48, 224));
			walls.add(new Wall(48, 240));
			walls.add(new Wall(336, 48));
			walls.add(new Wall(336, 64));
			walls.add(new Wall(336, 80));
			walls.add(new Wall(336, 96));
			walls.add(new Wall(336, 112));
			walls.add(new Wall(336, 128));
			walls.add(new Wall(336, 144));
			walls.add(new Wall(336, 160));
			walls.add(new Wall(336, 176));
			walls.add(new Wall(336, 192));
			walls.add(new Wall(336, 208));
			walls.add(new Wall(336, 224));
			walls.add(new Wall(336, 240));
		break;
		case 3:
			player = new Player(300, 200);
			for(int i = 0; i < 50; i++) {
				int newX = 0;
				int newY = 0;
				while(true) {
					newX = (int)Math.floor(Math.random() * WIDTH / 16.0) * 16;
					newY = (int)Math.floor(Math.random() * HEIGHT / 16.0) * 16;
					Rectangle rect = new Rectangle(newX, newY, 16, 16);
					if(rect.contains(player.getRectangle()))
						continue;
					boolean matchesAnother = false;
					for(int j = 0; j < i; j++) {
						if(newX == walls.get(j).getX() && newY == walls.get(j).getY()) {
							matchesAnother = true;
							break;
						}
					}
					if(!matchesAnother)
						break;
				}
				walls.add(new Wall(newX, newY));
			}
				
			break;
		default:
			player = new Player(300, 200);
		}
	}

	public void run() {

		initGL(WIDTH * 2, HEIGHT * 2);
		GL11.glScaled(2, 2, 1);
		init();
		loadLevel(level);
		SoundController.playMusic();

		while (true) { // Game Loop

			/*******************
			 * GAME LOOP
			 **************************************************/
			
			//moved sound controller here because
			//the music would play for one frame before
			//it cut off if disabled before starting game
			SoundController.continueMusic();
			
			
			// moves player then checks relative position of player then moves
			// enemy
			playerMovement();
			enemyMovement();

			// timer increases every frame aka 60/sec
			if(enemiesKilled + enemies.size() < 20 || level == 4) {
				if (timer < 1800) { //30 secs
					if (timer % 50 == 0)
						enemySpawn();
				} else if (timer < 3600) { //60 secs
					if (timer % 25 == 0)
						enemySpawn();
				} else if (timer < 5400){ //90 secs
					if (timer % 10 == 0)
						enemySpawn();
				}
				else{
					if (timer % 7 == 0)
						enemySpawn();
				}
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
				boolean damageAccepted = player.takeDamage();
				if(damageAccepted)
					SoundController.playSoundWithRandomPitch(SoundController.hitPlayer);
				else
					SoundController.playSoundWithRandomPitch(SoundController.hitEnemy);
			}

			if (player.getHealth() <= 0) {
				level = 0; //reset the levels back to zero if want to play again
				gameOver();
				break;
			}
			if(enemiesKilled >= 20) {
				if(level < 4) {
					level++;
					loadLevel(level);
				}
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
			for (int i = 0; i < walls.size(); i++) {
				walls.get(i).draw();
			}
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
		Rectangle playerRectangle = player.getRectangle();
		playerRectangle.x += dx;
		if(collisionWithWall(playerRectangle))
			dx = 0;
		playerRectangle = player.getRectangle();
		playerRectangle.y += dy;
		if(collisionWithWall(playerRectangle))
			dy = 0;
		player.move(dx, dy);
	}

	public void enemySpawn() {
		int x, y;
		while(true) {// an attempt to not spawn the robot on the enemy
			x = (int) Math.floor(Math.random() * WIDTH);
			y = (int) Math.floor(Math.random() * HEIGHT);
			if(Math.sqrt(Math.pow(x - player.getX(), 2) + Math.pow(y - player.getY(), 2)) < 80)
				continue;
			Rectangle rect = new Rectangle(x-8, y-8, 16, 16);
			if(!collisionWithWall(rect))
				break;
		}
		Enemy robot = new Enemy(x, y);
		enemies.add(robot);
		robot.draw();
	}
	
	public void performBulletCollisions() {
		// despawning hit enemies
		for (int i = 0; i < bullets.size(); i++) {
			boolean removeBullet = false;
			Rectangle bulletRectangle = bullets.get(i).getRectangle(); 
			for (int j = 0; j < enemies.size(); j++) {
				if(bulletRectangle.intersects(enemies.get(j).getRectangle())) {
					SoundController.playSoundWithRandomPitch(SoundController.hitEnemy);
					enemies.remove(j);// remove enemy if hit
					enemiesKilled++;
					removeBullet = true;
					break;// move on to the next bullet
				}
			}
			if(!removeBullet) {
				for(int j = 0; j < walls.size(); j++) {
					if(bulletRectangle.intersects(walls.get(j).getRectangle())) {
						removeBullet = true;
						break;// move on to the next bullet
					}
				}
			}
			if(removeBullet) {
				bullets.remove(i);// destroy bullet
				i--;// don't skip the next bullet
			}
		}

		// remove bullets from array once they are out of bounds/memory
		// conservation
		Rectangle gameArea = new Rectangle(-100, -100, WIDTH + 200, HEIGHT + 200);
		for (int i = 0; i < bullets.size(); i++) {
			if(!gameArea.contains(bullets.get(i).getCurrentX(), bullets.get(i).getCurrentY()))
				bullets.remove(i);
		}
	}

	public void enemyMovement() {
		for (int i = 0; i < enemies.size(); i++) {
			Enemy enemy = enemies.get(i);
			int dx = (int)Math.signum(player.getX() - enemy.getX());
			int dy = (int)Math.signum(player.getY() - enemy.getY());
			Rectangle rect = enemy.getRectangle();
			rect.x += dx;
			if(collisionWithWall(rect)) {
				rect.x -= dx;
				dx = 0;
			}
			rect.y += dy;
			if(collisionWithWall(rect))
				dy = 0;
			if(dx != 0 || dy != 0)
				enemy.setRotation(ExtraMath.PointDirection(0, 0, dx, dy));
			enemy.move(dx, dy);
			enemy.update();
		}
	}

	public void gameOver() {
		try{
			Display.destroy();
		}catch(Exception e){
			System.out.println("Error when destroying display.");
		}
	}

	public boolean checkPlayerHit() {
		boolean isHit = false;

		Rectangle playerRectangle = player.getRectangle();
		for (int i = 0; i < enemies.size(); i++) {
			if(playerRectangle.contains(enemies.get(i).getX(), enemies.get(i).getY())) {
				enemies.remove(i);
				isHit = true;
			}
		}

		return isHit;
	}

	/**
	 * Check if the rectangle overlaps any wall
	 * @param      rectangle  The rectangle
	 * @return     True if overlapping any wall
	 */
	public boolean collisionWithWall(Rectangle rectangle) {
		for(int i = 0; i < walls.size(); i++) {
			if(walls.get(i).getRectangle().intersects(rectangle))
				return true;
		}
		return false;
	}
}
