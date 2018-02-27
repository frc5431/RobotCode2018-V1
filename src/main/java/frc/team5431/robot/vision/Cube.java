package frc.team5431.robot.vision;

public class Cube {
	public int x, y, w, h;
	public Cube(int xP, int yP, int wP, int hP) {
		x = xP;
		y = yP;
		w = wP;
		h = hP;
	}
	
	public int getArea() {
		return w * h;
	}
	
	public int getWidth() {
		return w;
	}
	
	public int getHeight() {
		return h;
	}
	
	public String toString() {
		return String.format("(Cube) x: %d, y: %d, width: %d, height: %d", x, y, w, h);
	}
}
