package Model;

public class Bullet {

	private int currentX, currentY, mouseX, mouseY, slope;
	private boolean isInPositiveX, isInPositiveY;
	
	public Bullet(int cx, int cy, int mx, int my){
		this.currentX = cx;
		this.currentY = cy;
		this.mouseX   = mx;
		this.mouseY   = my;
		
		if(cx <= mx)
			this.isInPositiveX = true;
		else
			this.isInPositiveX = false;
		
		if(cy <= my)
			this.isInPositiveY = false;
		else
			this.isInPositiveY = true;
		
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
		if(isInPositiveX && isInPositiveY){
			currentX++;
			currentY++;
		}
		else if(isInPositiveX && !isInPositiveY){
			currentX++;
			currentY--;
		}
		else if(!isInPositiveX && isInPositiveY){
			currentX--;
			currentY++;
		}
		else{
			currentX--;
			currentY--;
		}
	}
	
	
	
	
	
	
	
}
