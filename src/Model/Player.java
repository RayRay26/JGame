package Model;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class Player {

	public static float x, y;
	public static float vel = 0.96f;

	public Player(float x, float y) {

		this.x = x;
		this.y = y;

	}

	public void playerMovement() {
		drawPlayer();
		while (Keyboard.next()) {

			if (Keyboard.getEventKeyState()) {
				if (Keyboard.isKeyDown(Keyboard.KEY_D)) {// right
					x++;

				}
				if (Keyboard.isKeyDown(Keyboard.KEY_A)) {// left
					x--;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_S)) {// down
					y--;
				}
				if (Keyboard.isKeyDown(Keyboard.KEY_W)) {// up
					y++;
				}

			}

		}
	}

	public void drawPlayer() {

		glBegin(GL_QUADS); // Begins drawing the quad
		{
			glColor3f(0.5f, 0.4f, 0.3f); // Paints the quad
			glVertex2f(x, y);// Top left corner
			glVertex2f(x + 45, y);// Top right corner
			glVertex2f(x + 45, y + 45);// Bottom right corner
			glVertex2f(x, y + 45);// Bottom left corner
		}
		glEnd();// Ends quad drawing
	}

	// making a triangle and uses the slope of the hypotenuse to draw the shot
	public void shoot(int mouseX, int mouseY) {

		float slope;
		float xB = this.x; // copying x and y values
		float yB = this.y; // so movement doesnt mess with shots

		// y2 -y1 / x2 - x1 for slope
		slope = (mouseY - yB) / (mouseX - xB);

		// run loop incrementing shot x, y values until shot is off screen
		while ((xB < Display.getWidth() && xB > 0) || (yB < Display.getHeight() && yB > 0))
			// start drawing quad
			glBegin(GL_QUADS);
			{
				glColor3f(0.5f, 0.4f, 0.3f);
				glVertex2f(x, y);
				glVertex2f(x + 2, y);
				glVertex2f(x + 2, y + 2);
				glVertex2f(x, y + 2);
			}
		glEnd();

		x += 1;
		y += slope;

	}

}
