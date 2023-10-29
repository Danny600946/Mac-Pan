package nea;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.KeyStroke;

public class GameUI extends Menu implements Runnable {

	private ImageIcon one = new ImageIcon(getClass().getClassLoader().getResource("One.png")); // Declares and resizes																						// one
	private Image numOne = one.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon zero = new ImageIcon(getClass().getClassLoader().getResource("Zero.png")); // Declares and resizes zero
	private Image numZero = zero.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon two = new ImageIcon(getClass().getClassLoader().getResource("Two.png")); // Declares and resizes two
	private Image numTwo = two.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon three = new ImageIcon(getClass().getClassLoader().getResource("Three.png")); // Declares and resizes three
	private Image numThree = three.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
    private ImageIcon four = new ImageIcon(getClass().getClassLoader().getResource("Four.png")); // Declares and resizes four
	private Image numFour = four.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon five = new ImageIcon(getClass().getClassLoader().getResource("Five.png")); // Declares and resizes five
	private Image numFive = five.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon six = new ImageIcon(getClass().getClassLoader().getResource("Six.png")); // Declares and resizes six
	private Image numSix = six.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon seven = new ImageIcon(getClass().getClassLoader().getResource("Seven.png")); // Declares and resizes seven
	private Image numSeven = seven.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon Eight = new ImageIcon(getClass().getClassLoader().getResource("Eight.png"));// Declares and resizes eight
	private Image numEight = Eight.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);
	private ImageIcon nine = new ImageIcon(
			getClass().getClassLoader().getResource("Nine.png"));// Declares and resizes nine
	private Image numNine = nine.getImage().getScaledInstance(10, 20, Image.SCALE_DEFAULT);

	private MapGen wallBlocks = new MapGen(15); // contains values to be passed for wall tiles
	
	private Characters main = new Characters(0, 0, 15, false, false, false, false, 0, 0, false);
	private Characters enemyOne = new Characters(0, 0, 15, false, false, false, false, 0, 0, false);
	private Characters enemyTwo = new Characters(0, 0, 15, false, false, false, false, 0, 0, false);
	private Characters enemyThree = new Characters(0, 0, 15, false, false, false, false, 0, 0, false);
	private Characters enemyFour = new Characters(0, 0, 15, false, false, false, false, 0, 0, false);
	
	private Items itemOne = new Items(0, 0, 15, 100, false); // creates item one
	private Items itemTwo = new Items(0, 0, 15, 100, false); // creates item two
	private Items itemThree = new Items(0, 0, 15, 100, false); // creates item three
	private Items itemFour = new Items(0, 0, 15, 100, false); // creates item four
	
	private int[][] mapArray = wallBlocks.getMap();
	private InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW); // stores an input
	private ActionMap am = getActionMap(); // stores action
	private Random rand = new Random();
	private int randomX, randomY;
	private boolean found = false;
	private int previousDirection, yPrev, xPrev, currentDirection;
	private int j, probability;
	private int numP;
	private String longString;
	private int intendedDirection = 0;

	private ArrayList<Double> visited = new ArrayList<Double>();
	private ArrayList<Double> unvisited = new ArrayList<Double>();
	private ArrayList<Double> validPath = new ArrayList<Double>();
	private ArrayList<Integer> incorrectMoves = new ArrayList<Integer>();

	private double heuristic, finalValue, allDistancesBefore, currentRow, currentCol, prevRow, prevCol, characterRow,
			characterCol;

	private double smallestF = 10000000;

	protected void generateMap() {
        System.out.println(getClass().getClassLoader().getResource("Zero.png"));
        System.out.println("Ayo");
		wallBlocks.createMap();// generates the map
	}

	private void drawMaze(Graphics2D g2d) { // draws maze walls
		for (int i = 0; i < 50; i++) {
			for (int j = 0; j < 50; j++) {
				if (mapArray[i][j] == 0) {
					g2d.drawImage(wallBlocks.getImage(), 117 + (15 * j), 117 + (15 * i), this); // draws maze of images
				}
				else if (mapArray[i][j] == 3) {
					g2d.drawImage(wallBlocks.getImageEdge(), 117 + (15 * j), 117 + (15 * i), this); // draws maze of images
				}
			}
		}
	}

	protected void initialCharacterPosition() { // finds closest free location to the centre of the maze
		if (mapArray[24][24] == 1) {
			main.setX((360) + 117);
			main.setY((360) + 117);
		}

		else if (mapArray[23][24] == 1) {
			main.setX((360) + 117);
			main.setY((345) + 117);
		}

		else if (mapArray[24][25] == 1) {
			main.setX((375) + 117);
			main.setY((360) + 117);
		}

		else if (mapArray[25][24] == 1) {
			main.setX((360) + 117);
			main.setY((375) + 117);
		}

		else if (mapArray[24][23] == 1) {
			main.setX((345) + 117);
			main.setY((360) + 117);
		}
	}

	protected void enemyPositions() { // sets enemy positions as close to corners as possible
		if (mapArray[48][48] == 1) { // enemy One
			enemyOne.setX((720) + 117);
			enemyOne.setY((720) + 117);
		}

		else if (mapArray[48][47] == 1) {
			enemyOne.setX((705) + 117);
			enemyOne.setY((720) + 117);
		}

		else if (mapArray[47][48] == 1) {
			enemyOne.setX((720) + 117);
			enemyOne.setY((705) + 117);
		}

		else if (mapArray[47][47] == 1) {
			enemyOne.setX((705) + 117);
			enemyOne.setY((705) + 117);
		}

		if (mapArray[48][1] == 1) { // enemy Two
			enemyTwo.setX((15) + 117);
			enemyTwo.setY((720) + 117);
		}

		else if (mapArray[48][2] == 1) {
			enemyTwo.setX((30) + 117);
			enemyTwo.setY((720) + 117);
		}

		else if (mapArray[47][1] == 1) {
			enemyTwo.setX((15) + 117);
			enemyTwo.setY((705) + 117);
		}

		else if (mapArray[47][2] == 1) {
			enemyTwo.setX((30) + 117);
			enemyTwo.setY((705) + 117);
		}

		if (mapArray[1][1] == 1) { // enemy 3
			enemyThree.setX((15) + 117);
			enemyThree.setY((15) + 117);
		}

		else if (mapArray[1][2] == 1) {
			enemyThree.setX((30) + 117);
			enemyThree.setY((15) + 117);
		}

		else if (mapArray[2][1] == 1) {
			enemyThree.setX((15) + 117);
			enemyThree.setY((30) + 117);
		}

		else if (mapArray[2][2] == 1) {
			enemyThree.setX((30) + 117);
			enemyThree.setY((30) + 117);
		}

		if (mapArray[1][48] == 1) { // enemy 4
			enemyFour.setX((720) + 117);
			enemyFour.setY((15) + 117);
		}

		else if (mapArray[1][47] == 1) {
			enemyFour.setX((705) + 117);
			enemyFour.setY((15) + 117);
		}

		else if (mapArray[2][48] == 1) {
			enemyFour.setX((720) + 117);
			enemyFour.setY((30) + 117);
		}

		else if (mapArray[2][47] == 1) {
			enemyFour.setX((705) + 117);
			enemyFour.setY((30) + 117);
		}
	}

	private void items() {
		itemPositions(itemOne); // finds position for item one
		itemPositions(itemTwo); // finds position for item two
		itemPositions(itemThree); // finds position for item three
		itemPositions(itemFour); // finds position for item four
	}

	private void itemPositions(Items item) { // finds a valid position for an item in the level
		while (item.getOnMap() == false) {
			randomX = rand.nextInt(50); // gets a random number to represent the column chosen
			randomY = rand.nextInt(50); // gets a random number to represent the row chosen
			
			if (mapArray[randomY][randomX] == 1 && ((itemOne.getX() != ((randomX * 15) + 117)
					&& itemOne.getY() != ((randomY * 15) + 117))
					|| ((itemTwo.getX() != ((randomX * 15) + 117)) && itemTwo.getY() != ((randomY * 15) + 117))
					|| ((itemThree.getX() != ((randomX * 15) + 117)) && itemThree.getY() != ((randomY * 15) + 117))
					|| ((itemFour.getX() != ((randomX * 15) + 117)) && itemFour.getY() != ((randomY * 15) + 117)))) { // checks it is a valid location and there isn't an item there already

				item.setX(-item.getX()); // sets X coordinate to 0
				item.setY(-item.getY()); // sets Y coordinate to 0
				item.setX((randomX * 15) + 117);
				item.setY((randomY * 15) + 117);

				repaint();

				item.setOnMap(true); // sets onMap true so loop ends
			}
		}
	}

	private void drawCharacter(Graphics2D g2d) {
		g2d.drawImage(main.getImage(), main.getX(), main.getY(), this); // draws character image
		g2d.drawImage(enemyOne.getEnemy1Image(), enemyOne.getX(), enemyOne.getY(), this); // draws enemy One
		g2d.drawImage(enemyTwo.getEnemy2Image(), enemyTwo.getX(), enemyTwo.getY(), this); // draws enemy Two
		g2d.drawImage(enemyThree.getEnemy3Image(), enemyThree.getX(), enemyThree.getY(), this); // draws enemy Three
		g2d.drawImage(enemyFour.getEnemy4Image(), enemyFour.getX(), enemyFour.getY(), this); // draws enemy Four
	}

	protected void initiateBinds() {
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "DownPress"); // adds key to input map
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "UpPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "LeftPress");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "RightPress");

		am.put("DownPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) { // performs downward movement
				previousDirection = main.getCurrentDirection();
				xPrev = main.getX();
				yPrev = main.getY();
				intendedDirection = 0;
				main.setDirectionDown();
			}
		});

		am.put("UpPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) { // performs downward movement
				previousDirection = main.getCurrentDirection();
				xPrev = main.getX();
				yPrev = main.getY();
				intendedDirection = 0;
				main.setDirectionUp();
			}
		});

		am.put("LeftPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) { // performs downward movement
				previousDirection = main.getCurrentDirection();
				main.setDirectionLeft();
				xPrev = main.getX();
				yPrev = main.getY();
				intendedDirection = 0;
			}
		});

		am.put("RightPress", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) { // performs downward movement
				previousDirection = main.getCurrentDirection();
				xPrev = main.getX();
				yPrev = main.getY();
				intendedDirection = 0;
				main.setDirectionRight();
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;

		if (endGame == false) {
			g2d.setColor(Color.black); // checks if the game has ended
			g2d.fillRect(0, 0, 1000, 1000); // draws a black square which acts as the background
			
			drawMaze(g2d); // draws maze
			displayTime(g2d); // draws time in seconds
			displayScore(g2d); // draws score board
			drawItems(g2d); // draws items
			drawCharacter(g2d); // draws character
		}
	}

	private void drawItems(Graphics2D g2d) {
		g2d.drawImage(itemOne.getItemImage(), itemOne.getX(), itemOne.getY(), this); // draws item one image
		g2d.drawImage(itemTwo.getItemImage(), itemTwo.getX(), itemTwo.getY(), this); // draws item two image
		g2d.drawImage(itemThree.getItemImage(), itemThree.getX(), itemThree.getY(), this); // draws item three image
		g2d.drawImage(itemFour.getItemImage(), itemFour.getX(), itemFour.getY(), this); // draws item four image
	}

	private void mainCharacter() {
		currentDirection = main.getCurrentDirection();

		if (main.getDirectionUp() == true || intendedDirection == 1) {
			main.setDirectionUp();
			if (main.checkNextMove(mapArray, main.getY(), main.getX()) == true) {
				if (intendedDirection == 1) {
					intendedDirection = 0;

				}
				main.setY(-3);
			}

			else {
				main.changeCurrentDirection(currentDirection);
			}
		}

		if (main.getDirectionDown() == true || intendedDirection == 2) {
			main.setDirectionDown();
			if (main.checkNextMove(mapArray, main.getY(), main.getX()) == true) {
				if (intendedDirection == 2) {
					intendedDirection = 0;
				}
				main.setY(3);
			}

			else {
				main.changeCurrentDirection(currentDirection);
			}
		}

		if (main.getDirectionLeft() == true || intendedDirection == 3) {
			main.setDirectionLeft();
			if (main.checkNextMove(mapArray, main.getY(), main.getX()) == true) {
				if (intendedDirection == 3) {
					intendedDirection = 0;
				}
				main.setX(-3);
			}
			else {
				main.changeCurrentDirection(currentDirection);
			}
		}

		if (main.getDirectionRight() == true || intendedDirection == 4) {
			main.setDirectionRight();
			if (main.checkNextMove(mapArray, main.getY(), main.getX()) == true) {
				if (intendedDirection == 4) {
					intendedDirection = 0;
				}
				main.setX(3);
			}
			
			else {
				main.changeCurrentDirection(currentDirection);
			}

		}

		if (main.checkNextMove(mapArray, main.getY(), main.getX()) == false) {
			if (previousDirection == 1 && yPrev == main.getY() && xPrev == main.getX()) {
				intendedDirection = main.getCurrentDirection();
				if (intendedDirection == previousDirection) {
					intendedDirection = 0;
				}
				main.setDirectionUp();
			}

			else if (previousDirection == 2 && yPrev == main.getY() && xPrev == main.getX()) {
				intendedDirection = main.getCurrentDirection();
				if (intendedDirection == previousDirection) {
					intendedDirection = 0;
				}
				main.setDirectionDown();
			}

			else if (previousDirection == 3 && xPrev == main.getX() && yPrev == main.getY()) {
				intendedDirection = main.getCurrentDirection();
				if (intendedDirection == previousDirection) {
					intendedDirection = 0;
				}
				main.setDirectionLeft();
			}

			else if (previousDirection == 4 && xPrev == main.getX() && yPrev == main.getY()) {
				intendedDirection = main.getCurrentDirection();
				if (intendedDirection == previousDirection) {
					intendedDirection = 0;
				}
				main.setDirectionRight();
			}
		}
		repaint(); // redraws
	}

	private void enemiesMovement() {
		findNextMove(enemyOne);
		findNextMove(enemyTwo);
		findNextMove(enemyThree);
		findNextMove(enemyFour);
	}

	private void findNextMove(Characters enemy) {
		found = false; // resets false value
		// checks if enemy is on a valid grid spot and a correct path
		if (((enemy.getX() - 117) % 15 == 0 && (enemy.getY() - 117) % 15 == 0) && enemy.getIncorrectPath() == false) { 
			visited.clear(); // resets all values
			unvisited.clear();
			prevRow = 0;
			prevCol = 0;
			allDistancesBefore = 0;
			currentRow = (enemy.getY() - 117) / 15;
			currentCol = (enemy.getX() - 117) / 15;
			characterRow = (main.getY() - 117) / 15;
			characterCol = (main.getX() - 117) / 15;

			calcAllDistancesBefore(); // calculates g(n) for start node
			calcHeuristic(currentRow, currentCol); // calculates h(n) for start node

			while (found == false) {
				smallestF = 100000; // makes smallest F when comparing the next best node
				finalValue = allDistancesBefore + heuristic; // calculates F value
				visited.add(currentRow); // adds to visited
				visited.add(currentCol);
				visited.add(finalValue);
				visited.add(heuristic);
				visited.add(allDistancesBefore);
				visited.add(prevRow);
				visited.add(prevCol);
				// checks if the main characters location has been found
				if ((currentRow == characterRow) && (currentCol == characterCol)) {
					found = true;
					break;
				}

				potentialNodes(); // finds potential nodes to be used

				for (int h = 0; h < unvisited.size();) { // moves the next chosen node from unvisited to visited
					if (smallestF > unvisited.get(h + 2)) {
						smallestF = unvisited.get(h + 2);
						currentRow = unvisited.get(h);
						currentCol = unvisited.get(h + 1);
						heuristic = unvisited.get(h + 3);
						allDistancesBefore = unvisited.get(h + 4);
						prevRow = unvisited.get(h + 5);
						prevCol = unvisited.get(h + 6);
					}
					h += 7;
				}

				for (int g = 2; g < unvisited.size();) { // removes the next node from unvisited
					if (smallestF == unvisited.get(g)
							&& (unvisited.get(g - 2) == currentRow && unvisited.get(g - 1) == currentCol)) {

						unvisited.remove(g - 2);
						unvisited.remove(g - 2);
						unvisited.remove(g - 2);
						unvisited.remove(g - 2);
						unvisited.remove(g - 2);
						unvisited.remove(g - 2);
						unvisited.remove(g - 2);

					}
					g += 7;
				}
			}

			found = false;
			validPath.clear(); // clears array list
			j = 0; // resets j
			
			validPath.add(currentRow);
			validPath.add(currentCol);

			j += 2;

			while (found == false) {
				for (int i = 0; i < visited.size();) {
					if (visited.get(i) == currentRow && visited.get(i + 1) == currentCol) {
						j += 2;
						currentRow = visited.get(i + 5);
						currentCol = visited.get(i + 6);
						validPath.add(currentRow); // adds nodes to an array
						validPath.add(currentCol);

						if (currentRow == 0 && currentCol == 0) { // checks if it is the final node
							if (validPath.size() > 4) {
								enemy.setNextRow(validPath.get(j - 6)); // stores the next desired node corresponding to a specific enemy
								enemy.setNextCol(validPath.get(j - 5));

								found = true;
								break;

							}

							else {
								found = true;
								break;
							}
						}
					}
					i += 7;
				}
			}

			numP = 0;
			junctionDetection(enemy);

			if (numP > 2) {
				probability = rand.nextInt(100);
				if (probability < difficulty) { // makes it so there is a 0.15 chance an incorrect direction is chosen
					randomMove(enemy); // finds a random direction to move which is not correct
				}
			}
		} // end of if statement containing the path finder

		else if (((enemy.getX() - 117) % 15 == 0) && ((enemy.getY() - 117) % 15 == 0)) {
			numP = 0;
			junctionDetection(enemy);

			if ((enemy.getCurrentDirection() == 4
					&& mapArray[((enemy.getY() - 117) / 15)][((enemy.getX() - 117) / 15) + 1] == 1) && numP < 3) { // moves enemy right
				enemy.setNextCol(enemy.getNextCol() + 1);
			}

			else if ((enemy.getCurrentDirection() == 3
					&& mapArray[((enemy.getY() - 117) / 15)][((enemy.getX() - 117) / 15) - 1] == 1) && numP < 3) {// moves enemy left
				enemy.setNextCol(enemy.getNextCol() - 1);
			}

			else if ((enemy.getCurrentDirection() == 2
					&& mapArray[((enemy.getY() - 117) / 15) + 1][((enemy.getX() - 117) / 15)] == 1) && numP < 3) {// moves enemy down
				enemy.setNextRow(enemy.getNextRow() + 1);
			}

			else if ((enemy.getCurrentDirection() == 1
					&& mapArray[((enemy.getY() - 117) / 15) - 1][((enemy.getX() - 117) / 15)] == 1) && numP < 3) {// moves enemy up
				enemy.setNextRow(enemy.getNextRow() - 1);
			}

			else {
				enemy.setIncorrectPath(false); 
			}
		}

		if (enemy.getX() < (enemy.getNextCol() * 15) + 117) { // moves enemy
			enemy.setDirectionRight();
			enemy.setX(1);
		}

		else if (enemy.getX() > (enemy.getNextCol() * 15) + 117) {// moves enemy
			enemy.setDirectionLeft();
			enemy.setX(-1);
		}

		else if (enemy.getY() < (enemy.getNextRow() * 15) + 117) {// moves enemy
			enemy.setDirectionDown();
			enemy.setY(1);
		}

		else if (enemy.getY() > (enemy.getNextRow() * 15) + 117) {// moves enemy
			enemy.setDirectionUp();
			enemy.setY(-1);
		}
	}

	private void junctionDetection(Characters enemy) {
		if (mapArray[((enemy.getY() - 117) / 15) - 1][((enemy.getX() - 117) / 15)] == 1) { // checks for a valid move up
			numP++;
		}

		if (mapArray[((enemy.getY() - 117) / 15)][((enemy.getX() - 117) / 15) + 1] == 1) { // checks for a valid move to the right
			numP++;
		}

		if (mapArray[((enemy.getY() - 117) / 15) + 1][((enemy.getX() - 117) / 15)] == 1) { // checks for a valid move down
			numP++;
		}

		if (mapArray[((enemy.getY() - 117) / 15)][((enemy.getX() - 117) / 15) - 1] == 1) { // checks for a valid move to the left
			numP++;
		}

	}

	private void randomMove(Characters enemy) {

		enemy.setIncorrectPath(true);

		if (mapArray[((enemy.getY() - 117) / 15) - 1][((enemy.getX() - 117) / 15)] == 1) { // checks for a valid move up
			incorrectMoves.add(((enemy.getY() - 117) / 15) - 1);
			incorrectMoves.add(((enemy.getX() - 117) / 15));
		}

		if (mapArray[((enemy.getY() - 117) / 15)][((enemy.getX() - 117) / 15) + 1] == 1) { // checks for a valid move to the right
			incorrectMoves.add(((enemy.getY() - 117) / 15));
			incorrectMoves.add(((enemy.getX() - 117) / 15) + 1);
		}

		if (mapArray[((enemy.getY() - 117) / 15) + 1][((enemy.getX() - 117) / 15)] == 1) { // checks for a valid move down
			incorrectMoves.add(((enemy.getY() - 117) / 15) + 1);
			incorrectMoves.add(((enemy.getX() - 117) / 15));
		}

		if (mapArray[((enemy.getY() - 117) / 15)][((enemy.getX() - 117) / 15) - 1] == 1) { // checks for a valid move to the left
			incorrectMoves.add(((enemy.getY() - 117) / 15));
			incorrectMoves.add(((enemy.getX() - 117) / 15) - 1);
		}

		probability = rand.nextInt((incorrectMoves.size() / 2)); // ensures an equal probability of being chosen no matter in the number of optional paths
		switch (probability) { // sets the direction that the character will move

		case 0:
			enemy.setNextRow(incorrectMoves.get(0));
			enemy.setNextCol(incorrectMoves.get(1));
			break;

		case 1:
			enemy.setNextRow(incorrectMoves.get(2));
			enemy.setNextCol(incorrectMoves.get(3));
			break;

		case 2:
			enemy.setNextRow(incorrectMoves.get(4));
			enemy.setNextCol(incorrectMoves.get(5));
			break;

		case 3:

			enemy.setNextRow(incorrectMoves.get(6));
			enemy.setNextCol(incorrectMoves.get(7));
			break;
		}
		incorrectMoves.clear(); // clears array for next use
	}

	private void potentialNodes() { // finds the next potential nodes assigning their values to an array list
		if (mapArray[(int) (currentRow - 1)][(int) currentCol] == 1
				&& alreadyVisited((currentRow - 1), currentCol) == false) {

			unvisited.add(currentRow - 1);
			unvisited.add(currentCol);
			
			calcHeuristic((currentRow - 1), (currentCol)); // calculates heuristic value
			calcAllDistancesBefore(); // calculates distance value from start value
			
			finalValue = allDistancesBefore + heuristic; // calculates F
			unvisited.add(finalValue);
			unvisited.add(heuristic);
			unvisited.add(allDistancesBefore);
			prevRow = currentRow;
			prevCol = currentCol;
			unvisited.add(prevRow);
			unvisited.add(prevCol);

		}

		if (mapArray[(int) currentRow][(int) (currentCol + 1)] == 1
				&& alreadyVisited((currentRow), currentCol + 1) == false) {
			
			unvisited.add(currentRow);
			unvisited.add(currentCol + 1);
			
			calcHeuristic((currentRow), (currentCol + 1)); // calculates heuristic value
			calcAllDistancesBefore(); // calculates distance value from start value
			
			finalValue = allDistancesBefore + heuristic; // calculates F
			unvisited.add(finalValue);
			unvisited.add(heuristic);
			unvisited.add(allDistancesBefore);
			prevRow = currentRow;
			prevCol = currentCol;
			unvisited.add(prevRow);
			unvisited.add(prevCol);
		}

		if (mapArray[(int) (currentRow + 1)][(int) currentCol] == 1
				&& alreadyVisited((currentRow + 1), currentCol) == false) {

			unvisited.add(currentRow + 1);
			unvisited.add(currentCol);

			calcHeuristic((currentRow + 1), (currentCol)); // calculates heuristic value
			calcAllDistancesBefore(); // calculates distance value from start value

			finalValue = allDistancesBefore + heuristic; // calculates F
			unvisited.add(finalValue);
			unvisited.add(heuristic);
			unvisited.add(allDistancesBefore);
			prevRow = currentRow;
			prevCol = currentCol;
			unvisited.add(prevRow);
			unvisited.add(prevCol);
		}

		if (mapArray[(int) currentRow][(int) (currentCol - 1)] == 1
				&& alreadyVisited((currentRow), currentCol - 1) == false) {

			unvisited.add(currentRow);
			unvisited.add(currentCol - 1);
			
			calcHeuristic((currentRow), (currentCol - 1)); // calculates heuristic value
			calcAllDistancesBefore(); // calculates distance value from start value

			finalValue = allDistancesBefore + heuristic; // calculates F
			unvisited.add(finalValue);
			unvisited.add(heuristic);
			unvisited.add(allDistancesBefore);
			prevRow = currentRow;
			prevCol = currentCol;
			unvisited.add(prevRow);
			unvisited.add(prevCol);
		}
	}

	private void calcHeuristic(double currentRow, double currentCol) { // calculates the heuristic

		heuristic = Math.sqrt(((characterRow - currentRow) * (characterRow - currentRow)) // calculates the shortest straight line distance to the main character.
				+ ((characterCol - currentCol) * (characterCol - currentCol)));
	}

	private void calcAllDistancesBefore() { // finds the distance from the current node to the initial node
		for (int i = 0; i < visited.size();) {
			if (visited.get(i) == currentRow && visited.get(i + 1) == currentCol) {
				allDistancesBefore = visited.get(i + 4) + 1;
			}
			i += 7;
		}
	}

	private boolean alreadyVisited(double currentRow, double currentCol) { // determines if a node is already visited
		for (int i = 0; i < visited.size();) {
			if (visited.get(i) == currentRow && visited.get(i + 1) == currentCol) {
				return true;
			}
			i += 7;
		}

		for (int i = 0; i < unvisited.size();) {
			if (unvisited.get(i) == currentRow && unvisited.get(i + 1) == currentCol) {
				return true;
			}
			i += 7;
		}
		return false;
	}

	private void enemyCollisonDetection(Characters enemy) {
		if ((main.getX() - enemy.getX() < 15) && (main.getX() - enemy.getX() > -15) && (main.getY() - enemy.getY() < 15)
				&& (main.getY() - enemy.getY() > -15)) { // checks if the main character is in range of an enemy
			endGame = true; // changes if the game should end
		}
	}

	private void itemCollisonDetection(Items item) {
		if ((main.getX() - item.getX() < 15) && (main.getX() - item.getX() > -15) && (main.getY() - item.getY() < 15)
				&& (main.getY() - item.getY() > -15)) { // checks if the main character is in range of an enemy
			totalScore += item.getScore(); // increments score by how much the item is worth
			repaint();
			item.setOnMap(false);
		}
	}

	private void displayScore(Graphics2D g2d) { // displays score
		if (totalScore > 0) { // if total score is above 0
			longString = Long.toString(totalScore); // converts the long into a string
			for (int i = 0; i < longString.length(); i++) {
				if (longString.charAt(longString.length() - (i + 1)) == '0') { // used to print 0
					g2d.drawImage(numZero, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '1') { // used to print 1
					g2d.drawImage(numOne, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '2') { // used to print 2
					g2d.drawImage(numTwo, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '3') { // used to print 3
					g2d.drawImage(numThree, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '4') { // used to print 4
					g2d.drawImage(numFour, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '5') { // used to print 5
					g2d.drawImage(numFive, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '6') { // used to print 6
					g2d.drawImage(numSix, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '7') { // used to print 7
					g2d.drawImage(numSeven, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '8') { // used to print 8
					g2d.drawImage(numEight, ((49 - i) * 15) + 117, 80, this);
				}

				else if (longString.charAt(longString.length() - (i + 1)) == '9') { // used to print 9
					g2d.drawImage(numNine, ((49 - i) * 15) + 117, 80, this);
				}
			}
		}

		else { // Displays 3 zeros if the score is 0
			g2d.drawImage(numZero, ((49) * 15) + 117, 80, this);
			g2d.drawImage(numZero, ((48) * 15) + 117, 80, this);
			g2d.drawImage(numZero, ((47) * 15) + 117, 80, this);
		}
	}

	private void displayTime(Graphics2D g2d) { // displays timer
		longString = Long.toString((System.currentTimeMillis() / 1000) - startTimeSeconds); // converts the long into a string

		for (int i = 0; i < longString.length(); i++) {
			if (longString.charAt(longString.length() - (i + 1)) == '0') { // used to print 0
				g2d.drawImage(numZero, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '1') { // used to print 1
				g2d.drawImage(numOne, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '2') { // used to print 2
				g2d.drawImage(numTwo, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '3') { // used to print 3
				g2d.drawImage(numThree, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '4') { // used to print 4
				g2d.drawImage(numFour, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '5') { // used to print 5
				g2d.drawImage(numFive, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '6') { // used to print 6
				g2d.drawImage(numSix, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '7') { // used to print 7
				g2d.drawImage(numSeven, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '8') { // used to print 8
				g2d.drawImage(numEight, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}

			else if (longString.charAt(longString.length() - (i + 1)) == '9') { // used to print 9
				g2d.drawImage(numNine, (((longString.length() - 1) - i) * 15) + 117, 80, this);
			}
		}
	}

	@Override
	public void run() {

		while (true) {

			itemCollisonDetection(itemOne); // called to check if the main character is colliding with an item
			itemCollisonDetection(itemTwo); // called to check if the main character is colliding with an item
			itemCollisonDetection(itemThree); // called to check if the main character is colliding with an item
			itemCollisonDetection(itemFour); // called to check if the main character is colliding with an item
			items(); // calls items to handle items
			mainCharacter(); // calls mainCharacter to handle character movement
			enemiesMovement(); // calls enemiesMovement to handle enemy movements
			repaint();
			enemyCollisonDetection(enemyOne); // called to check if the main character is colliding with an enemy
			enemyCollisonDetection(enemyTwo); // called to check if the main character is colliding with an enemy
			enemyCollisonDetection(enemyThree); // called to check if the main character is colliding with an enemy
			enemyCollisonDetection(enemyFour); // called to check if the main character is colliding with an enemy

			if (endGame == true) {
				GameUI(); // calls GameUI to change the appearance of the frame
				break; // breaks while loop
			}

			try {
				Thread.sleep(17); // ** millisecond delay
			}

			catch (Exception e) {
				Thread.currentThread().interrupt();
			}
		}
	}
}
