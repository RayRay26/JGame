package View;

import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

public class Screen {
	public static Texture background;
	
	public static void resetToIdentity() {
		GL11.glLoadIdentity();
		GL11.glScaled(2, 2, 1);
		GL11.glColor4f(1f, 1f, 1f, 1f);
	}

	public static void drawBackGround() {
		for (int x = 0; x < 800; x += background.getTextureWidth()) {
			for (int y = 0; y < 600; y += background.getTextureHeight()) {
				background.bind();

				GL11.glBegin(GL11.GL_QUADS);
				{
					GL11.glTexCoord2f(0, 0);
					GL11.glVertex2f(x, y);
					GL11.glTexCoord2f(1, 0);
					GL11.glVertex2f(x + background.getTextureWidth(), y);
					GL11.glTexCoord2f(1, 1);
					GL11.glVertex2f(x + background.getTextureWidth(), y + background.getTextureHeight());
					GL11.glTexCoord2f(0, 1);
					GL11.glVertex2f(x, y + background.getTextureHeight());
				}
				GL11.glEnd();
			}
		}
	}
	
	public static void drawSprite(Texture texture, float x, float y, float width, float height) {
		drawSprite(texture, x, y, width, height, 1f, 1f);
	}
	
	public static void drawSprite(Texture texture, float x, float y, float width, float height, float textureWidthRatio, float textureHeightRatio) {
		texture.bind();
		GL11.glBegin(GL11.GL_QUADS);
		{
			GL11.glTexCoord2f(0, 0);
			GL11.glVertex2f(x, y);
			GL11.glTexCoord2f(textureWidthRatio, 0);
			GL11.glVertex2f(x + width, y);
			GL11.glTexCoord2f(textureWidthRatio, textureHeightRatio);
			GL11.glVertex2f(x + width, y + height);
			GL11.glTexCoord2f(0, textureHeightRatio);
			GL11.glVertex2f(x, y + height);
		}
		GL11.glEnd();
	}
}
