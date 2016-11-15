package Model;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Bullet {
	public static Texture texture;
	
	private double bulletSpeed = 4.0;
	private double currentX, currentY, speedX, speedY;
	
	public Bullet(int cx, int cy, int mx, int my){
		this.currentX = cx;
		this.currentY = cy;
		double xDiff = mx - cx;
		double yDiff = my - cy;
		double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		speedX = xDiff * bulletSpeed / distance;
		speedY = yDiff * bulletSpeed / distance;
	}
	public int getCurrentX() {
		return (int)currentX;
	}
	
	public int getCurrentY() {
		return (int)currentY;
	}
	
	public void incrementValue(){
		currentX += speedX;
		currentY += speedY;
	}
	
	public void draw() {
		texture.bind();

		int textureWidth = texture.getTextureWidth();
		int textureHeight = texture.getTextureHeight();
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f((float)currentX - textureWidth / 2f, (float)currentY - textureHeight / 2f);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f((float)currentX + textureWidth / 2f, (float)currentY - textureHeight / 2f);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f((float)currentX + textureWidth / 2f, (float)currentY + textureHeight / 2f);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f((float)currentX - textureWidth / 2f, (float)currentY + textureHeight / 2f);
		}
		GL11.glEnd();
	}
}
