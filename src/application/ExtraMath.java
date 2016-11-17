package application;

public class ExtraMath {
	/// Returns theta in degrees from x1,y1 to x2,y2
	public static double PointDirection(double x1, double y1, double x2, double y2) {
		double dx = x2 - x1;
		double dy = y2 - y1;
		double angle = 0;
		if(dx == 0 && dy > 0)
			angle = 270;
		else if(dx == 0 && dy < 0)
			angle = 90;
		else {
			angle = Math.atan(-dy / dx) * 180f / Math.PI;
			if(dx < 0)
				angle += 180;
		}
		return angle;
	}
}
