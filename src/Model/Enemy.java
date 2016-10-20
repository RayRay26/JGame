package Model;

import java.util.Random;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.Input;
import org.newdawn.slick.Image;

public class Enemy extends BasicGame
{
	public static final int width=800;
	public static final int height=600;

	public static AppGameContainer appgc;

	private Vector<GameObject> enemies;

	public GameObject player;

	public int lastFrameTime=0;

	public static int deltaTime;
	Random r = new Random();

	public Enemy(String gamename)
	{
		super(gamename);
	}

	@Override
	public void init(GameContainer gc) throws SlickException
	{
		deltaTime= deltaTime();
		enemies=new Vector<GameObject>(50000);
		player=new GameObject("/art/bullet_shape.png");
		player.accelY=0;
		player.x+=200;
		for(int i=0;i<1000;i++){
			addRandomEnemy();
		}
	}

	private void addRandomEnemy()  throws SlickException
	{

		GameObject e = new GameObject("/art/bullet_green.png");
		//randomize velocity and position
		e.velX=r.nextFloat()-.5f;
		e.velY=r.nextFloat()-.5f;
		e.x=r.nextInt(width);
		e.y=r.nextInt(height);
		enemies.add(e);
	}


	//calculate delta time
	public int deltaTime()
	{
		long time=System.nanoTime()/1000000;
		int delta = (int)(time-lastFrameTime);
		lastFrameTime = (int)time;
		return delta;
	}

	@Override
	public void update(GameContainer gc, int n) throws SlickException
	{
		//calculate time since last frame has run
		deltaTime= deltaTime();
		if(appgc.getInput().isKeyDown(Input.KEY_D))
		{
			player.x++;
		}
		if(appgc.getInput().isKeyDown(Input.KEY_A))
		{
			player.x--;
		}
		if(appgc.getInput().isKeyDown(Input.KEY_S))
		{
			player.y++;
		}
		if(appgc.getInput().isKeyDown(Input.KEY_W))
		{
			player.y--;
		}
		if(appgc.getInput().isKeyDown(Input.KEY_Q))
		{

			for(int i=0;i<100;i++){
				addRandomEnemy();
			}
			//addRandomEnemy();
		}


		for(int i=0;i<enemies.size();i++)
		{
			if(enemies.elementAt(i).x>width||enemies.elementAt(i).x<0||enemies.elementAt(i).y>height||enemies.elementAt(i).y<0)
			{
				enemies.remove(i);
			}
			else
			{
				enemies.elementAt(i).update();
			}
		}
		//update the player
		player.update();

	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{

		for(int i=0;i<enemies.size();i++)
		{
			enemies.elementAt(i).i.draw(enemies.elementAt(i).x, enemies.elementAt(i).y);
		}
		player.i.draw(player.x,player.y);

		g.drawString("Press q to spawn more", 10, 100);
	}

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
