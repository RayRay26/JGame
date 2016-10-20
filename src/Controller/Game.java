package Controller;

import org.lwjgl.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;

import Model.Enemy;
import Model.Player;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;

import static org.lwjgl.opengl.GL11.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Game {

	public static final int width=800;
	public static final int height=600;

	public static AppGameContainer appgc;

	//this is where the gameloop is located
	public void run()
	{

		try
		{

			appgc = new AppGameContainer(new Enemy("Enemy"));
			appgc.setDisplayMode(width, height, false);
			appgc.start();


		}
		catch (SlickException ex)
		{
			Logger.getLogger(Enemy.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

}


