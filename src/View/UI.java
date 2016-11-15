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
	
	public static void drawHealth(int health) {
		switch (health) {
		case 5:
			health5.bind();
			break;
		case 4:
			health4.bind();
			break;
		case 3:
			health3.bind();
			break;
		case 2:
			health2.bind();
			break;
		case 1:
			health1.bind();
			break;
		default:
			health0.bind();
		}

		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(0, 0);
			GL11.glTexCoord2f(1, 0);
			GL11.glVertex2f(80, 0);
			GL11.glTexCoord2f(1, 1);
			GL11.glVertex2f(80, 16);
			GL11.glTexCoord2f(0, 1);
			GL11.glVertex2f(0, 16);
		}
		GL11.glEnd();
	}
}
