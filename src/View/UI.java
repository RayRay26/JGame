package View;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class UI {
	public static Texture health5;
	public static Texture health4;
	public static Texture health3;
	public static Texture health2;
	public static Texture health1;
	public static Texture health0;
	public static Texture overheatFill;
	public static Texture overheatBack;
	
	public static void drawHealth(int health) {
		Texture texture;
		switch (health) {
		case 5:
			texture = health5;
			break;
		case 4:
			texture = health4;
			break;
		case 3:
			texture = health3;
			break;
		case 2:
			texture = health2;
			break;
		case 1:
			texture = health1;
			break;
		default:
			texture = health0;
		}

		Screen.drawSprite(texture, 0, 0, 80, 16, 0.625f, 1f);
	}
	
	public static void drawOverheat(float overheatRatio) {
		Screen.drawSprite(overheatBack, 0, 16, 80, 16, 0.625f, 1f);
		Screen.drawSprite(overheatFill, 0, 16, overheatRatio * 80f, 16, overheatRatio * 0.625f, 1f);
	}
}
