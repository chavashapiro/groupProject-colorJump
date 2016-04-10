package colorJump;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

public class Peg extends JButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int color;
	private int x;
	private int y;
	private String[] colorArray = new String[] { "/empty_space.png",
			"/green_peg.png", "/yellow_peg.png", "/purple_peg.png",
			"/blue_peg.png", "/pink_peg.png", "/red_peg.png" };

	public Peg(int color, int x, int y) {
		this.color = color;
		this.x = x;
		this.y = y;
		setColor(color);

		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setBorder(null);
		this.setFocusPainted(false);
		this.setRolloverEnabled(false);
	}

	public int getXLocation() {
		return x;
	}

	public int getYLocation() {
		return y;
	}

	public int getPegColor() {
		return color;
	}

	public void setColor(int c) {
		this.color = c;
		String name = colorArray[c];
		this.setIcon(new ImageIcon(getClass().getResource(name)));
		this.setDisabledIcon(new ImageIcon(getClass().getResource(name)));
	}

	public void select() {
		this.setBorder(new LineBorder(Color.BLACK));
	}

	public void deselect() {
		this.setBorder(null);
	}

}