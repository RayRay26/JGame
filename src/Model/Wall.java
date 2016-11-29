package Model;

import java.awt.Rectangle;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import View.Screen;

public class Wall {
	public static Texture texture;
	public static int textureWidth;
	
	private int x;
	private int y;
	
	public Wall(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, textureWidth, textureWidth);
	}
	
	public void draw() {
		texture.bind();

		GL11.glTranslatef(x, y, 0);
		Screen.drawSprite(texture, 0, 0, textureWidth, textureWidth);
		Screen.resetToIdentity();
	}
}
