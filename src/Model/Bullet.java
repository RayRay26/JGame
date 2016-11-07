package Model;

public class Bullet {

	private int currentX, currentY, mouseX, mouseY, slope;
	private boolean isInPositiveDirection;
	
	public Bullet(int cx, int cy, int mx, int my){
		this.currentX = cx;
		this.currentY = cy;
		this.mouseX   = mx;
		this.mouseY   = my;
		
		if(cx < mx)
			this.isInPositiveDirection = true;
		else
			this.isInPositiveDirection = false;
		
		try{
			slope = (int)((double)((my-cy)/(mx-cx)));
		}catch(ArithmeticException e){
			slope = 1;
		}
	}
	

	public int getCurrentX() {
		return currentX;
	}

	public void setCurrentX(int currentX) {
		this.currentX = currentX;
	}

	public int getCurrentY() {
		return currentY;
	}

	public void setCurrentY(int currentY) {
		this.currentY = currentY;
	}

	public int getMouseX() {
		return mouseX;
	}

	public void setMouseX(int mouseX) {
		this.mouseX = mouseX;
	}

	public int getMouseY() {
		return mouseY;
	}

	public void setMouseY(int mouseY) {
		this.mouseY = mouseY;
	}
	
	public void incrementValue(){
		if(isInPositiveDirection){
			currentX++;
			currentY += slope;
		}
		else{
			currentX--;
			currentY += slope;
		}
	}
	
	
	
	
	
	
	
}
