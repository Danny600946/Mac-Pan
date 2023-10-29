package nea;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Characters extends GameObjects {
	private volatile boolean up, down, left, right, incorrectPath;
	private int row, col;
	private double nextRow, nextCol;

	private ImageIcon macPan = new ImageIcon(getClass().getClassLoader().getResource("Main Character.png")); // used to set and scale images
	private Image i = macPan.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
	private ImageIcon enemyOne = new ImageIcon(getClass().getClassLoader().getResource("EnemyOne.png")); // used to set and scale images
	private Image e1 = enemyOne.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
	private ImageIcon enemyTwo = new ImageIcon(getClass().getClassLoader().getResource("EnemyTwo.png")); // used to set and scale images
	private Image e2 = enemyTwo.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
	private ImageIcon enemyThree = new ImageIcon(getClass().getClassLoader().getResource("EnemyThree.png")); // used to set and scale images
	private Image e3 = enemyThree.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
	private ImageIcon enemyFour = new ImageIcon(getClass().getClassLoader().getResource("EnemyFour.png")); // used to set and scale images
	private Image e4 = enemyFour.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);

	Characters(int x, int y, int dimensions, boolean up, boolean down, boolean left, boolean right, int nextRow,
			int nextCol, boolean incorrectPath) { // creates a character object
		super(x, y, dimensions);
		this.up = up;
		this.down = down;
		this.left = left;
		this.right = right;
		this.incorrectPath = incorrectPath;
	}

	protected void setNextRow(double row) {
		nextRow = row;
	}

	protected void setNextCol(double col) {
		nextCol = col;
	}

	protected double getNextRow() {
		return nextRow;
	}

	protected double getNextCol() {
		return nextCol;
	}

	protected void setIncorrectPath(boolean path) {
		incorrectPath = path;
	}

	protected boolean getIncorrectPath() {
		return incorrectPath;
	}

	protected void setDirectionUp() {
		up = true;
		down = false;
		left = false;
		right = false;
	}

	protected void setDirectionDown() {
		up = false;
		down = true;
		left = false;
		right = false;
	}

	protected void setDirectionLeft() {
		up = false;
		down = false;
		left = true;
		right = false;
	}

	protected void setDirectionRight() {
		up = false;
		down = false;
		left = false;
		right = true;
	}

	protected int getCurrentDirection() {

		if (getDirectionUp() == true) {
			return 1;
		}

		else if (getDirectionDown() == true) {
			return 2;
		}

		else if (getDirectionLeft() == true) {
			return 3;
		}

		else if (getDirectionRight() == true) {
			return 4;
		}
		return 0;
	}

	protected void changeCurrentDirection(int currentDirection) {

		if (currentDirection == 1) { // changes direction to up
			setDirectionUp();

		}

		else if (currentDirection == 2) { // changes direction to down
			setDirectionDown();
		}

		else if (currentDirection == 3) { // changes direction to left
			setDirectionLeft();
		}

		else if (currentDirection == 4) { // changes direction to right
			setDirectionRight();
		}
	}

	protected boolean getDirectionUp() {
		return up;
	}

	protected boolean getDirectionDown() {
		return down;
	}

	protected boolean getDirectionLeft() {
		return left;
	}

	protected boolean getDirectionRight() {
		return right;
	}

	protected Image getImage() { // used to retrieve the image when being used
		return i;
	}

	protected Image getEnemy1Image() { // used to retrieve the image when being used
		return e1;
	}

	protected Image getEnemy2Image() { // used to retrieve the image when being used
		return e2;
	}

	protected Image getEnemy3Image() { // used to retrieve the image when being used
		return e3;
	}

	protected Image getEnemy4Image() { // used to retrieve the image when being used
		return e4;
	}

	protected boolean checkNextMove(int[][] mapArray, int yLocation, int xLocation) { // checks the next move is valid

		if (((yLocation - 117) % 15) == 0 && ((xLocation - 117) % 15) == 0) {
			row = ((yLocation - 117) / 15);
			col = ((xLocation - 117) / 15);
		}

		if (getDirectionUp() == true) {
			if (mapArray[row - 1][col] == 1 && ((col * 15) + 117) == xLocation) {
				return true;
			}

			else if (((yLocation - 117) % 15) != 0) {
				return true;
			}
		}

		else if (getDirectionDown() == true && ((col * 15) + 117) == xLocation) {
			if (mapArray[row + 1][col] == 1) {
				return true;
			}

			else if (((yLocation - 117) % 15) != 0) {
				return true;
			}
		}

		else if (getDirectionLeft() == true && ((row * 15) + 117) == yLocation) {
			if (mapArray[row][col - 1] == 1) {
				return true;
			}

			else if (((xLocation - 117) % 15) != 0) {
				return true;
			}
		}

		else if (getDirectionRight() == true && ((row * 15) + 117) == yLocation) {
			if (mapArray[row][col + 1] == 1) {
				return true;
			}

			else if (((xLocation - 117) % 15) != 0) {
				return true;
			}

		}
		return false;
	}
}
