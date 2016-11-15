package Model;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;

import Controller.Game;
import View.Screen;

public class Player {
	public static Texture texture;
	
	private int x;
	private int y;
	private int health = 5;
	private int shootTimer = 20;
	private int invincibility = 0;

	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void update() {
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
	
	public int getHealth() {
		return health;
	}
	
	public void setHealth(int health) {
		this.health = health;
	}
	
	public Bullet shoot() {
		if(shootTimer <= 0) {
			shootTimer = 20;
			Bullet bullet = new Bullet(x, y, Mouse.getX() / 2, Game.HEIGHT - Mouse.getY() / 2);
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
		Color.white.bind();
		texture.bind();

		int textureWidth = texture.getTextureWidth();
		int textureHeight = texture.getTextureHeight();
		float dx = Mouse.getX() / 2f - x;
		float dy = Game.HEIGHT - Mouse.getY() / 2f - y;
		double angle = 0;
		if(dx == 0 && dy > 0)
			angle = 90;
		else if(dx == 0 && dy < 0)
			angle = 270;
		else {
			angle = Math.atan(-dy / dx) * 180f / Math.PI;
			if(dx < 0)
				angle += 180;
		}
		GL11.glTranslatef(x, y, 0);
		GL11.glRotated(angle, 0, 0, -1);
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(-textureWidth / 2f, -textureHeight / 2f);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(textureWidth / 2f, -textureHeight / 2f);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(textureWidth / 2f, textureHeight / 2f);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(-textureWidth / 2f, textureHeight / 2f);
		}
		GL11.glEnd();
		Screen.resetToIdentity();
	}
}
