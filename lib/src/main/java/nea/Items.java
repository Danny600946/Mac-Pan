package nea;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Items extends GameObjects {
	private int score;
	private boolean onMap;

	private ImageIcon I1 = new ImageIcon(getClass().getClassLoader().getResource("Item2.png"));
	private Image itemImage = I1.getImage().getScaledInstance(15, 15, Image.SCALE_DEFAULT);

	Items(int x, int y, int dimensions, int score, boolean onMap) { // creates an item object
		super(x, y, dimensions);
		this.score = score;
		this.onMap = onMap;
	}

	protected void setOnMap(boolean onMap) { // sets onMap
		this.onMap = onMap;
	}

	protected boolean getOnMap() { // gets onMap
		return onMap;
	}

	protected Image getItemImage() { // used to fetch the image for the items
		return itemImage;
	}

	protected int getScore() { // gets the score per item
		return score;
	}
}