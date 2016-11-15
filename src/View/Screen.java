package View;

import org.lwjgl.opengl.GL11;

public class Screen {
	public static void resetToIdentity() {
		GL11.glLoadIdentity();
		GL11.glScaled(2, 2, 1);
	}
}
