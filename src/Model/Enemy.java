package Model;

import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.opengl.Texture;

import View.Screen;
import View.SoundController;

import org.newdawn.slick.Input;
import org.newdawn.slick.Image;

public class Enemy {
	public static Texture texture0;
	public static Texture texture1;
	public static Texture texture3;
	public static Texture textureShooter0;
	public static Texture textureShooter1;
	public static Texture textureShooter3;
	public static int textureWidth;

	private boolean isShooter = false;
	private float x;
	private float y;
	private double rotation = 0;
	private int animationTimer = 0;
	private int bulletTimer = 0;

	public Enemy(float x, float y) {
		this.x = x;
		this.y = y;
	}

	public void declareAsShooter() {
		isShooter = true;
	}
	
	public boolean isShooter() {
		return isShooter;
	}

	public void update() {
		animationTimer++;
		if (animationTimer >= 40)
			animationTimer -= 40;

		if (isShooter)
			bulletTimer++;
	}

	public void move(float dx, float dy) {
		x += dx;
		y += dy;
	}

	public void setX(int main) {
		this.x = main;
	}

	public float getX() {
		return this.x;
	}

	public void setY(int main) {
		this.y = main;
	}

	public float getY() {
		return this.y;
	}

	public boolean isReadyToShoot() {
		return (bulletTimer > 150);
	}

	public Bullet shootAt(int x, int y) {
		bulletTimer = (int)(Math.random() * 30);
		Bullet bullet = new Bullet((int)this.x, (int)this.y, x, y);
		bullet.setFromEnemy(true);
		return bullet;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int)x - 8, (int)y - 8, 16, 16);
	}

	public double getRotation() {
		return rotation;
	}

	public void setRotation(double rotation) {
		this.rotation = rotation;
	}

	public void draw() {
		Texture texture = texture0;
		if (isShooter) {
			texture = textureShooter0;
			switch (animationTimer / 10) {
			case 1:
				texture = textureShooter1;
				break;
			case 3:
				texture = textureShooter3;
				break;
			}
		} else {
			switch (animationTimer / 10) {
			case 1:
				texture = texture1;
				break;
			case 3:
				texture = texture3;
				break;
			}
		}

		texture.bind();

		GL11.glTranslatef(x, y, 0);
		GL11.glRotated(rotation, 0, 0, -1);
		Screen.drawSprite(texture, -textureWidth / 2f, -textureWidth / 2f, textureWidth, textureWidth);
		Screen.resetToIdentity();
	}
}