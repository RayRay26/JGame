package Model;

import java.util.Random;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;


public class GameObject {
		public float x;
		public float y;
		public float velY;
		public float velX;
		public float accelY;
		public float accelX;
		public float oldVelY;
		public float oldVelX;

		Image i;
		static Random r=new Random();

		GameObject(String path) throws SlickException
		{
			x=0;
			y=0;
			velX=0;
			velY=0;
			accelY=.0005f;
			accelX=0;
			i=new Image(path);
		}

		void update(){
			oldVelY=velY;
			oldVelX=velX;
			velY+=accelY*Enemy.deltaTime;
			velX+=accelX*Enemy.deltaTime;
			//add velocity to position
			x+=(velX+oldVelX)*.5 *Enemy.deltaTime;
			y+=(velY+oldVelY)*.5 *Enemy.deltaTime;
		}
}
