package nea;
import java.awt.Image;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

public class MapGen {
	private int[][] mapArray = new int[50][50]; // Too hold all the tiles and give them a structure
	private int dimensions;
	private Random rand = new Random();
	private int randCol = rand.nextInt(48); // generates a random number between 0 - 48
	private int randRow = rand.nextInt(48);
	private int currentCol, currentRow, newCol, newRow, currentBaseCol, currentBaseRow;
	private int randNextLocal;
	private ImageIcon mapBlock = new ImageIcon(getClass().getClassLoader().getResource("BrickWall.png"));
	private Image i = mapBlock.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
	private ImageIcon edge = new ImageIcon(getClass().getClassLoader().getResource("SandWall.png"));
	private Image j = edge.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);
	private ArrayList<Integer> baseCol = new ArrayList<Integer>();
	private ArrayList<Integer> baseRow = new ArrayList<Integer>();
	private ArrayList<Integer> potentialLocalCol = new ArrayList<Integer>();
	private ArrayList<Integer> potentialLocalRow = new ArrayList<Integer>();

	MapGen(int dimensions) { // Adds the objects values to variables
		this.dimensions = dimensions;
	}

	protected int getDimensions() {
		return this.dimensions;
	}

	protected void createMap() { // function to generate map
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				if (i == 0 || j == 0 || i == 49 || j == 49) {
					mapArray[i][j] = 3;
				} else {
					mapArray[i][j] = 0; // changes each index to 0
				}
			}
		}

		currentCol = randCol + 1;
		currentRow = randRow + 1;

		while (true) {
			mapArray[currentRow][currentCol] = 1; // creates a start point
			if (currentRow - 2 >= 0 && mapArray[currentRow - 2][currentCol] != 1
					&& mapArray[currentRow - 2][currentCol] != 3) { // stores coordinates for potential spots
				baseCol.add(currentCol);
				baseRow.add(currentRow);
				potentialLocalCol.add(currentCol);
				potentialLocalRow.add(currentRow - 2);
			}

			if (currentRow + 2 < 50 && mapArray[currentRow + 2][currentCol] != 1
					&& mapArray[currentRow + 2][currentCol] != 3) {
				baseCol.add(currentCol);
				baseRow.add(currentRow);
				potentialLocalCol.add(currentCol);
				potentialLocalRow.add(currentRow + 2);
			}

			if (currentCol - 2 >= 0 && mapArray[currentRow][currentCol - 2] != 1
					&& mapArray[currentRow][currentCol - 2] != 3) {
				baseCol.add(currentCol);
				baseRow.add(currentRow);
				potentialLocalCol.add(currentCol - 2);
				potentialLocalRow.add(currentRow);
			}

			if (currentCol + 2 < 50 && mapArray[currentRow][currentCol + 2] != 1
					&& mapArray[currentRow][currentCol + 2] != 3) {
				baseCol.add(currentCol);
				baseRow.add(currentRow);
				potentialLocalCol.add(currentCol + 2);
				potentialLocalRow.add(currentRow);
			}

			randNextLocal = rand.nextInt(potentialLocalRow.size()); // selects a random number within bounds or arrayList size
			newCol = potentialLocalCol.get(randNextLocal);
			newRow = potentialLocalRow.get(randNextLocal);// gets the next random locations
			currentBaseCol = baseCol.get(randNextLocal);
			currentBaseRow = baseRow.get(randNextLocal);

			for (int d = 0; d < potentialLocalCol.size(); d++) {
				if (newCol == potentialLocalCol.get(d) && newRow == potentialLocalRow.get(d)) {// removes repeats of potential roots
					potentialLocalCol.remove(d);
					potentialLocalRow.remove(d);
					baseCol.remove(d);
					baseRow.remove(d);
				}
			}

			if (newCol - currentBaseCol == 2) {
				mapArray[currentBaseRow][currentBaseCol + 1] = 1; // these statements find from which direction the new path is going
			}

			else if (newCol - currentBaseCol == -2) {
				mapArray[currentBaseRow][currentBaseCol - 1] = 1;
			}

			else if (newRow - currentBaseRow == 2) {
				mapArray[currentBaseRow + 1][currentBaseCol] = 1;
			}

			else if (newRow - currentBaseRow == -2) {
				mapArray[currentBaseRow - 1][currentBaseCol] = 1;
			}

			currentCol = newCol; // sets randomly chosen spot as new current spot to repeat the process
			currentRow = newRow;

			if (potentialLocalCol.size() == 0) {
				mapArray[currentRow][currentCol] = 1;
				break;
			}
		}
	}

	protected int[][] getMap() {
		return mapArray;
	}

	protected Image getImage() {
		return i;
	}

	protected Image getImageEdge() {
		return j;
	}
}
