package nea;

public class GameObjects {
	private int x, y;

	GameObjects(int x, int y, int dimensions) {
		this.x = x;
		this.y = y;
	}

	protected void setX(int a) { // sets X coordinate
		x += a;
	}

	protected void setY(int b) { // sets Y coordinate
		y += b;
	}

	protected int getX() {
		return x;
	}

	protected int getY() {
		return y;
	}
}
