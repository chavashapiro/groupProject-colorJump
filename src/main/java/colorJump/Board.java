package colorJump;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.inject.Singleton;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

@Singleton
public class Board extends JPanel {

	/**
	 * 
	 */

	private static final long serialVersionUID = 1L;
	private Peg[][] pegs;
	static final int ROWS = 7;

	public Board() {
		setLayout(new GridLayout(ROWS, ROWS, 5, 5));
		setOpaque(false);
		setBorder(new LineBorder(Color.BLACK));
		pegs = new Peg[ROWS][ROWS];
		addPegs();
	}

	private void addPegs() {
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				pegs[i][j] = new Peg(getRandomValue(), i, j);
				add(pegs[i][j]);
			}
		}
	}

	public void erasePeg(int i, int j) {
		setPegColor(i, j, 0);
	}

	public int getPegColor(int i, int j) {
		return pegs[i][j].getPegColor();
	}

	public void setPegColor(int x, int y, int color) {
		pegs[x][y].setColor(color);
	}

	public void setPegRandomColor(int x, int y) {
		pegs[x][y].setColor(getRandomValue());
	}

	public int getRandomValue() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}

	public void enablePeg(int i, int j) {
		pegs[i][j].setEnabled(true);
		pegs[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public void disablePeg(int i, int j) {
		pegs[i][j].setEnabled(false);
		pegs[i][j].setCursor(Cursor.getDefaultCursor());
	}

	public boolean isPegEnabled(int i, int j) {
		if (pegs[i][j].isEnabled()) {
			return true;
		}
		return false;
	}

	public void addListeners(ActionListener listener, MouseListener mouseListener) {
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				pegs[i][j].addActionListener(listener);
				pegs[i][j].addMouseListener(mouseListener);
			}
		}
	}
}