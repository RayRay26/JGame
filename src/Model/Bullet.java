package Model;

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import Controller.Game;
import View.Screen;
import View.SoundController;
import application.ExtraMath;

public class Bullet {
	public static Texture texture;
	public static int textureWidth;
	public static int textureHeight;
	
	private double bulletSpeed = 6.0;
	private double currentX, currentY, speedX, speedY;
	
	public Bullet(int cx, int cy, int mx, int my){
		this.currentX = cx;
		this.currentY = cy;
		double xDiff = mx - cx;
		double yDiff = my - cy;
		double distance = Math.sqrt(Math.pow(xDiff, 2) + Math.pow(yDiff, 2));
		speedX = xDiff * bulletSpeed / distance;
		speedY = yDiff * bulletSpeed / distance;
		SoundController.playSoundWithRandomPitch(SoundController.shoot);
	}
	public int getCurrentX() {
		return (int)currentX;
	}
	
	public int getCurrentY() {
		return (int)currentY;
	}

	public Rectangle getRectangle() {
		return new Rectangle((int)(currentX - textureHeight / 2f), (int)(currentY - textureHeight / 2f), textureHeight, textureHeight);
	}
	
	public void incrementValue(){
		currentX += speedX / 2f;
		currentY += speedY / 2f;
	}
	
	public void draw() {
		texture.bind();
		
		double angle = ExtraMath.PointDirection(0, 0, speedX, speedY);
		GL11.glTranslated(currentX, currentY, 0);
		GL11.glRotated(angle, 0, 0, -1);
		Screen.drawSprite(texture, -textureWidth * 3f / 4f, -textureHeight / 2f, textureWidth, textureHeight);
		Screen.resetToIdentity();
	}
}
