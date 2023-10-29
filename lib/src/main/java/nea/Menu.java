package nea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Menu extends JPanel implements ActionListener, MouseListener {

	private static JFrame frame = new JFrame("Mac Pan");
	private static JPanel titleBar = new JPanel();
	private static JPanel leftSide = new JPanel();
	private static JPanel core = new JPanel();
	private static JLabel titleImage = new JLabel();
	private static JButton easy = new JButton("Easy");
	private static JButton normal = new JButton("Normal");
	private static JButton hard = new JButton("Hard");
	private static JButton instructions = new JButton("Instructions");
	private static JButton back = new JButton("Return");
	private static JTextArea guide = new JTextArea(
			"\n Welcome To The Mac Pan Guide :) \n \n 1) Select a difficulty. Don't be scared to challenge yourself. You can restart if needed. \n \n 2) Navigate the maze using your arrow keys. Be careful. The maze changes each game! \n \n 3) Watch out for the ghosts. They will try and stop you from getting around the maze. If you are caught, you lose! \n \n 4) Collect as many items as you can. The higher your score, the better! \n \n 5) Most importantly have fun!");
	private static JTextArea welcome = new JTextArea("Welcome To Mac Pan!" + "\n"
			+ "\n If You Are New Here And Are Looking For Some Help To Navigate The Mazes Of Mac Pan Feel Free To Browse The Instructions."
			+ "\n"
			+ "\n If You Are Confident In Your Ability Feel Free To Jump In By Selecting A Difficulty On The Left."
			+ "\n" + "\n Have Fun!");

	protected static int difficulty;
	protected boolean endGame = false; // dictates if the game should end

	private static JTextArea gameRecap = new JTextArea();
	protected static long startTimeSeconds = System.currentTimeMillis() / 1000; // records start time in seconds
	protected static long totalScore; // stores the total score

	private void DisplayMenu() {
		titleImage.setIcon(new ImageIcon(getClass().getClassLoader().getResource("Title.png"))); // sets title Image to the desired Image
		titleBar.add(titleImage); // adds the title to the titleBar
		titleBar.setBackground(new Color(42, 159, 163));
		frame.add(titleBar, BorderLayout.NORTH);
		leftSide.setBackground(new Color(200, 159, 163));
		leftSide.setLayout(new GridLayout(3, 1));
		customButton(easy);
		leftSide.add(easy); // adds buttons to the leftSide JPanel
		customButton(normal);
		leftSide.add(normal);
		customButton(hard);
		leftSide.add(hard);
		frame.add(leftSide, BorderLayout.WEST);
		core.setBackground(Color.BLACK);
		welcome.setForeground(Color.WHITE);// Sets texts colour
		welcome.setBackground(Color.BLACK);
		welcome.setEditable(false); // makes the text unable to be edited when run
		core.add(welcome);
		customButton(instructions);
		core.add(instructions);
		frame.add(core);
		frame.setSize(1000, 500); // sets frame size
		frame.setLocationRelativeTo(null); // makes the window centred
		frame.setResizable(false);
		frame.setVisible(true); // makes window visible

	}

	private void customButton(JButton button) { // changes JButtons appearance
		button.setPreferredSize(new Dimension(250, 100));
		button.setForeground(Color.WHITE); // sets text white
		button.setFont(new Font("Arial", Font.PLAIN, 15)); // sets font and text size
		button.setFocusPainted(false);
		button.setBorder(BorderFactory.createLineBorder(new Color(42, 159, 163))); // sets border colour
		button.setBackground(Color.BLACK);
		button.addActionListener(this); // enables the program to detect when the button has been presses
		button.addMouseListener(this);
	}

	private void DisplayGuide() {// sets up the instructions window
		leftSide.setVisible(false); // sets items in the JFrame that are not required false
		titleBar.setVisible(false);
		instructions.setVisible(false);
		welcome.setVisible(false);
		guide.setForeground(new Color(0, 255, 255));
		guide.setBackground(Color.black);
		guide.setEditable(false);
		guide.setFont(guide.getFont().deriveFont(19f)); // changes font size of JTextArea
		customButton(back);
		core.add(guide); // adds new Text area guide
		back.addActionListener(this);
		back.setVisible(true);
		guide.setVisible(true);
		core.add(back);
		core.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) { // performs actions dependent on inputs
		if (e.getSource() == instructions) {
			DisplayGuide();
		}

		else if (e.getSource() == back) {
			guide.setVisible(false);
			back.setVisible(false);
			leftSide.setVisible(true); // sets items in the JFrame that are required true and not required false
			titleBar.setVisible(true);
			instructions.setVisible(true);
			welcome.setVisible(true);
		}

		else if (e.getSource() == easy) {
			difficulty = 50;
			System.out.println("EASY");
			System.out.println(difficulty);
			GameUI();
		}

		else if (e.getSource() == normal) {
			difficulty = 25;
			GameUI();
		}

		else if (e.getSource() == hard) {
			difficulty = 10;
			GameUI();
		}
	}

	protected void GameUI() {

		if (endGame == false) {
			GameUI UI = new GameUI();
			Thread running = new Thread(UI);

			startTimeSeconds = System.currentTimeMillis() / 1000;
			totalScore = 0;
			leftSide.setVisible(false); // sets items in the JFrame that are not required false
			titleBar.setVisible(false);
			core.setVisible(false);
			instructions.setVisible(false);
			welcome.setVisible(false);
			frame.setSize(1000, 1000); // sets up the frame where the game will take place
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);
			frame.add(UI);
			frame.setVisible(true);

			UI.generateMap(); // generates map
			UI.enemyPositions(); // sets enemies initial positions
			UI.initialCharacterPosition(); // sets characters initial position
			UI.initiateBinds(); // sets the keys to be used

			running.start(); // runs the game
		}

		else {
			core.setLayout(null);
			gameRecap.setBounds(20, 20, 500, 500); // sets position and size of text area

			gameRecap.setForeground(new Color(0, 255, 255));// Sets the texts colour
			gameRecap.setBackground(Color.BLACK);
			gameRecap.setFont(new Font("Arial", Font.TRUETYPE_FONT, 50));
			gameRecap.setEditable(false); // makes the text unable to be edited when run
			gameRecap.setText("\nScore : " + totalScore + "\n\nTime : "
					+ ((System.currentTimeMillis() / 1000) - startTimeSeconds));

			core.add(gameRecap);
			gameRecap.setVisible(true);

			frame.setSize(1000, 500); // changes frame size
			frame.setResizable(false);
			frame.setLocationRelativeTo(null);

			core.setVisible(true);
			leftSide.setVisible(true);
			titleBar.setVisible(true); // sets components visible
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {// when mouse enters the text colour changes

		if (e.getSource() == easy) {
			easy.setForeground(new Color(0, 255, 255));

		} else if (e.getSource() == normal) {
			normal.setForeground(new Color(0, 255, 255));

		} else if (e.getSource() == hard) {
			hard.setForeground(new Color(0, 255, 255));

		} else if (e.getSource() == instructions) {
			instructions.setForeground(new Color(0, 255, 255));

		} else if (e.getSource() == back) {
			back.setForeground(new Color(0, 255, 255));
		}
	}

	@Override
	public void mouseExited(MouseEvent e) { // when mouse exits the text colour changes
		if (e.getSource() == easy) {
			easy.setForeground(Color.white);
		}

		else if (e.getSource() == normal) {
			normal.setForeground(Color.white);
		}

		else if (e.getSource() == hard) {
			hard.setForeground(Color.white);
		}

		else if (e.getSource() == instructions) {
			instructions.setForeground(Color.white);
		}

		else if (e.getSource() == back) {
			back.setForeground(Color.white);
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu();
		menu.DisplayMenu();
	}

	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
	}
}