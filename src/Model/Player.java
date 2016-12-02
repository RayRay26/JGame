package Model;

import java.awt.Rectangle;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import application.ExtraMath;
import Controller.Game;
import View.Screen;
import View.SoundController;

public class Player {
	public static Texture texture0;
	public static Texture texture1;
	public static Texture texture2;
	public static Texture texture3;
	public static int textureWidth;
	
	private int x;
	private int y;
	private int health = 5;
	private float overheat = 0f;
	private float overheatMax = 2f;
	private float overheatClick = 0.3f;
	private int shootTimer = 20;
	private int animationTimer = 0;
	private int invincibility = 0;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
		overheat -= 1/60f;
		if(overheat < 0f)
			overheat = 0f;
		shootTimer--;
		invincibility--;
	}
	
	public int getX() {
		return x;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x - 4, y - 4, 8, 8);
	}
	
	public void move(int dx, int dy) {
		x += dx;
		y += dy;
		if(dx != 0 || dy != 0) {
			animationTimer++;
			if(animationTimer == 20)
				SoundController.playSoundWithRandomPitch(SoundController.step0, 0.5f);
			if(animationTimer >= 40) {
				SoundController.playSoundWithRandomPitch(SoundController.step1, 0.5f);
				animationTimer -= 40;
			}
		}
		else {
			animationTimer = 0;
		}
	}
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public float getOverheat() {
		return overheat;
	}
	
	public float getMaxOverheat() {
		return overheatMax;
	}
	
	public Bullet shoot() {
		if(shootTimer <= 0 && overheat < (overheatMax - overheatClick)) {
			shootTimer = 9;
			overheat += overheatClick;
			Bullet bullet = new Bullet(x, y, Mouse.getX() / 2, Game.HEIGHT - Mouse.getY() / 2);
			for(int repeat = 0; repeat < 4; repeat++) {
				bullet.incrementValue();
			}
			SoundController.playSoundWithRandomPitch(SoundController.shoot);
			return bullet;
		}
		return null;
	}
	
	public boolean takeDamage() {
		if(invincibility <= 0) {
			health--;
			invincibility = 40;
			return true;
		}
		return false;
	}
	
	public void draw() {
		Texture texture = texture0;
		switch(animationTimer / 10) {
		case 1:
			texture = texture1;
			break;
		case 3:
			texture = texture3;
			break;
		}

		double angle = ExtraMath.PointDirection(x, y, Mouse.getX() / 2f, Game.HEIGHT - Mouse.getY() / 2f);
		if(invincibility > 0)
		GL11.glColor4f(1f, 1f, 1f, 0.5f);
		GL11.glTranslatef(x, y, 0);
		GL11.glRotated(angle, 0, 0, -1);
		Screen.drawSprite(texture, -textureWidth / 2f, -textureWidth / 2f, textureWidth, textureWidth);
		Screen.resetToIdentity();
	}
}
