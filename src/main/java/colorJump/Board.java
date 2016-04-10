package colorJump;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
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
	private Peg fromPeg;
	private Peg toPeg;
	private boolean bonus;
	private boolean gameOver;
	private final int ROWS;

	public Board() {
		ROWS = 7;
		setLayout(new GridLayout(ROWS, ROWS, 5, 5));
		setOpaque(false);
		setBorder(new LineBorder(Color.BLACK));
		bonus = false;
		pegs = new Peg[ROWS][ROWS];
		addPegs();
	}

	private void addPegs() {
		// TODO Auto-generated method stub
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				pegs[i][j] = new Peg(getValue(), i, j);
				add(pegs[i][j]);
			}
		}
		// middle peg should be empty
		setMiddlePeg();
		enableAllPegs();
	}

	private void setMiddlePeg() {
		// TODO Auto-generated method stub
		int middle = ROWS / 2;
		pegs[middle][middle].setOpaque(false);
		pegs[middle][middle].setColor(0);
		checkEnablePeg(middle, middle);
	}

	public void enableAllPegs() {
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				checkEnablePeg(i, j);
			}
		}
	}

	private void checkEnablePeg(int i, int j) {
		// TODO Auto-generated method stub
		if (isEnabled(i, j)) {
			enablePeg(i, j);
		}else{
			disablePeg(i, j);
		}
	}

	private void disablePeg(int i, int j) {
		// TODO Auto-generated method stub
		pegs[i][j].setEnabled(false);
		pegs[i][j].setCursor(Cursor.getDefaultCursor());
	}

	public void enablePeg(int i, int j) {
		pegs[i][j].setEnabled(true);
		pegs[i][j].setCursor(new Cursor(Cursor.HAND_CURSOR));
	}

	public int getValue() {
		Random random = new Random();
		return random.nextInt(6) + 1;
	}

	public void restart() {
		for (int i = 0; i < ROWS; i++) {
			for (int j = 0; j < ROWS; j++) {
				pegs[i][j].setColor(getValue());
				checkEnablePeg(i, j);
			}
		}
		setMiddlePeg();
		bonus = false;
	}

	public void deselectFromPeg() {
		fromPeg.deselect();
		fromPeg = null;
	}

	private boolean gameOver() {
		int count = 0;
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[0].length; j++) {
				if (pegs[i][j].isEnabled()) {
					return false;
				}
				if (pegs[i][j].getPegColor() != 0) {
					count++;
				}
			}
		}
		if (count == 1) {
			bonus = true;
		}
		return true;
	}

	public boolean isEnabled(int x, int y) {
		/*
		 * check all 4 directions: if peg next to it exist and not same color
		 * then check if one next to that put on stack if exists and same color
		 * as previous then go to next and repeat else if doesn't exist then
		 * possible move else check next direction else if no possible moves in
		 * either direction then disable button
		 */
		if (pegs[x][y].getPegColor() == 0) {
			return false;
		}
		return (checkRight(x, y) || checkLeft(x, y) || checkUp(x, y) || checkDown(
				x, y));
	}

	private void setOpenSpots(int x, int y) {
		openRight(x, y);
		openLeft(x, y);
		openUp(x, y);
		openDown(x, y);
	}

	private void openUp(int x, int y) {
		// TODO Auto-generated method stub
		int row = x - 1;
		if (row < 0) {
			return;
		}
		int myColor = pegs[x][y].getPegColor();
		int jumpColor = pegs[row][y].getPegColor();
		if (myColor == jumpColor) {
			return;
		}
		int newJumpColor = jumpColor;
		row--;
		while (row >= 0 && newJumpColor == jumpColor) {
			if (pegs[row][y].getPegColor() == 0) {
				enablePeg(row, y);
				return;
			}
			newJumpColor = pegs[row][y].getPegColor();
		}
	}

	private void openLeft(int x, int y) {
		// TODO Auto-generated method stub
		int col = y - 1;
		if (col < 0) {
			return;
		}
		int myColor = pegs[x][y].getPegColor();
		int jumpColor = pegs[x][col].getPegColor();
		if (myColor == jumpColor) {
			return;
		}
		int newJumpColor = jumpColor;
		col--;
		while (col >= 0 && newJumpColor == jumpColor) {
			if (pegs[x][col].getPegColor() == 0) {
				enablePeg(x, col);
				return;
			}
			newJumpColor = pegs[x][col].getPegColor();
		}
	}

	private void openDown(int x, int y) {
		// TODO Auto-generated method stub
		int row = x + 1;
		if (row >= ROWS) {
			return;
		}
		int myColor = pegs[x][y].getPegColor();
		int jumpColor = pegs[row][y].getPegColor();
		if (myColor == jumpColor) {
			return;
		}
		int newJumpColor = jumpColor;
		row++;
		while (row < ROWS && newJumpColor == jumpColor) {
			if (pegs[row][y].getPegColor() == 0) {
				enablePeg(row, y);
				return;
			}
			newJumpColor = pegs[row][y].getPegColor();
		}
	}

	private void openRight(int x, int y) {
		// TODO Auto-generated method stub
		int col = y + 1;
		if (col >= ROWS) {
			return;
		}
		int myColor = pegs[x][y].getPegColor();
		int jumpColor = pegs[x][col].getPegColor();
		if (myColor == jumpColor) {
			return;
		}
		int newJumpColor = jumpColor;
		col++;
		while (col < ROWS && newJumpColor == jumpColor) {
			if (pegs[x][col].getPegColor() == 0) {
				enablePeg(x, col);
				return;
			}
			newJumpColor = pegs[x][col].getPegColor();
		}
	}

	private boolean checkRight(int x, int y) {
		int currColor = pegs[x][y].getPegColor();
		int col = y + 1;
		if (col >= ROWS) {
			return false;
		}
		int jumpOverColor = pegs[x][col].getPegColor();
		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		col++;
		int newJumpColor = jumpOverColor;
		while (col < ROWS && newJumpColor == jumpOverColor) {
			int landingColor = pegs[x][col].getPegColor();
			if (landingColor == 0) {
				return true;
			}
			col++;
			newJumpColor = landingColor;
		}
		return false;
	}

	private boolean checkLeft(int x, int y) {
		int currColor = pegs[x][y].getPegColor();
		int col = y - 1;
		if (col < 0) {
			return false;
		}
		int jumpOverColor = pegs[x][col].getPegColor();

		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		col--;
		int newJumpColor = jumpOverColor;
		while (col >= 0 && newJumpColor == jumpOverColor) {
			int landingColor = pegs[x][col].getPegColor();
			if (landingColor == 0) {
				return true;
			}
			col--;
			newJumpColor = landingColor;
		}
		return false;
	}

	private boolean checkUp(int x, int y) {

		int currColor = pegs[x][y].getPegColor();
		int row = x - 1;
		if (row < 0) {
			return false;
		}
		int jumpOverColor = pegs[row][y].getPegColor();

		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		row--;
		int newJumpColor = jumpOverColor;
		while (row >= 0 && newJumpColor == jumpOverColor) {
			int landingColor = pegs[row][y].getPegColor();
			if (landingColor == 0) {
				return true;
			}
			row--;
			newJumpColor = landingColor;
		}
		return false;
	}

	private boolean checkDown(int x, int y) {

		int currColor = pegs[x][y].getPegColor();
		int row = x + 1;
		if (row >= ROWS) {
			return false;
		}
		int jumpOverColor = pegs[row][y].getPegColor();
		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		row++;
		int newJumpColor = jumpOverColor;
		while (row < ROWS && newJumpColor == jumpOverColor) {
			int landingColor = pegs[row][y].getPegColor();
			if (landingColor == 0) {
				return true;
			}
			row++;
			newJumpColor = landingColor;
		}
		return false;
	}

	private void disableAll() {
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[0].length; j++) {
				pegs[i][j].setEnabled(false);
				pegs[i][j].setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
			}
		}
	}
	
	private void removeSpots() {
		int fromX = fromPeg.getXLocation();
		int toX = toPeg.getXLocation();
		int fromY = fromPeg.getYLocation();
		int toY = toPeg.getYLocation();
		if (fromX == toX) {
			// left
			if (fromY > toY) {
				for (int i = toY + 1; i <= fromY; i++) {
					erasePeg(fromX, i);
				}
			}
			// right
			else {
				for (int i = fromY; i < toY; i++) {
					erasePeg(fromX,i);
				}
			}
		} else {
			// up
			if (fromX > toX) {
				for (int i = toX + 1; i <= fromX; i++) {
					erasePeg(i,fromY);
				}
			}
			// down
			else {
				for (int i = fromX; i < toX; i++) {
					erasePeg(i, fromY);
				}
			}
		}
	}
	public void erasePeg(int i, int j) {
		pegs[i][j].setColor(0);
	}

	public int pegClicked(ActionEvent event) {
		// TODO Auto-generated method stub
		int points = 0;
		if (fromPeg == null) {
			fromPeg = (Peg) event.getSource();
			fromPeg.select();
			fromPeg.repaint();
			disableAll();
			setOpenSpots(fromPeg.getXLocation(), fromPeg.getYLocation());
		} else {
			toPeg = (Peg) event.getSource();
			if (toPeg.getBackground() != Color.WHITE) {
				toPeg.setBackground(Color.WHITE);
			}
			points = move();
			
			fromPeg.deselect();
			fromPeg = null;
			enableAllPegs();
			if (gameOver()) {
				gameOver = true;
			}
		}
		return points;
	}

	public int move() {
		toPeg.setColor(fromPeg.getPegColor());
		fromPeg.setColor(0);
		fromPeg.repaint();
		removeSpots();
		int spaces;
		int peg1X = fromPeg.getXLocation();
		int peg2X = toPeg.getXLocation();
		if (peg1X == peg2X) {
			spaces = Math.abs(fromPeg.getYLocation() - toPeg.getYLocation());
		} else {
			spaces = Math.abs(peg1X - peg2X);
		}
		return setMoveScore(spaces);

	}

	private int setMoveScore(int spaces) {
		// TODO Auto-generated method stub
		int score = 0;
		switch (spaces - 1) {
		case 1:
			score = 10;
			break;
		case 2:
			score = 40;
			break;
		case 3:
			score = 130;
			break;
		case 4:
			score = 400;
			break;
		case 5:
			score = 700;
			break;
		}
		return score;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void addListeners(ActionListener listener,
			MouseListener mouseListener) {
		// TODO Auto-generated method stub
		for (int i = 0; i < pegs.length; i++) {
			for (int j = 0; j < pegs[i].length; j++) {
				pegs[i][j].addActionListener(listener);
			//	pegs[i][j].addMouseListener(mouseListener);
			}
		}
	}

	public boolean isBonus() {
		return bonus;
	}
}