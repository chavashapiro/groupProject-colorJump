package colorJump;

import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.inject.Inject;

public class BoardManager {
	private Peg fromPeg;
	private Peg toPeg;
	private Board board;
	private int level;
	private final int level1 = 1;
	private final int level2 = 2;
	private boolean bonus;
	private boolean gameOver;

	@Inject
	public BoardManager(Board board) {
		this.board = board;
		fromPeg = null;
		toPeg = null;
		bonus = false;
		gameOver = false;
		setLevel(level1);
		setMiddlePeg();
		enableAllPegs();
	}
	public boolean isGameOver() {
		return gameOver;
	}
	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}
	public boolean isBonus() {
		return bonus;
	}
	public void setLevel(int newLevel) {
		if (newLevel == level1) {
			level = level1;
		} else if (newLevel == level2) {
			level = level2;
		}
	}

	public void restart() {
		resetBoard();
		deselectFromPeg();
		bonus = false;
		enableAllPegs();
	}

	public void resetBoard() {
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				board.setPegRandomColor(i, j);
				checkEnablePeg(i, j);
			}
		}
		setMiddlePeg();
	}
	public void disableAll() {
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				board.disablePeg(i, j);
			}
		}
	}

	public void deselectFromPeg() {
		if (fromPeg != null) {
			fromPeg.deselect();
		}
		fromPeg = null;
	}

	

	public int pegClicked(ActionEvent event) {
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

	private void setOpenSpots(int x, int y) {
		checkOpenLeft(x, y);
		checkOpenUp(x, y);
		checkOpenRight(x, y);
		checkOpenDown(x, y);
	}

	private boolean checkOpenDown(int x, int y) {
		int currColor = board.getPegColor(x, y);
		int jumpOverColor;
		if (x + 1 < Board.ROWS
				&& currColor != (jumpOverColor = board.getPegColor(x + 1, y))
				&& jumpOverColor != 0) {
			while (x + 1 < Board.ROWS
					&& (jumpOverColor == board.getPegColor(x + 1, y) || board
							.getPegColor(x + 1, y) == 0)) {
				x++;
				if (board.getPegColor(x, y) == 0) {

					board.enablePeg(x, y);

					return true;
				}

			}
		}
		return false;
	}

	private boolean checkOpenRight(int x, int y) {
		int currColor = board.getPegColor(x, y);
		int jumpOverColor;
		if (y + 1 < Board.ROWS
				&& currColor != (jumpOverColor = board.getPegColor(x, y + 1))
				&& jumpOverColor != 0) {
			while (y + 1 < Board.ROWS
					&& (jumpOverColor == board.getPegColor(x, y + 1) || board
							.getPegColor(x, y + 1) == 0)) {
				y++;
				if (board.getPegColor(x, y) == 0) {

					board.enablePeg(x, y);

					return true;
				}

			}
		}
		return false;
	}

	private boolean checkOpenLeft(int x, int y) {
		int currColor = board.getPegColor(x, y);
		int jumpOverColor;
		if (y > 0 && currColor != (jumpOverColor = board.getPegColor(x, y - 1))
				&& jumpOverColor != 0) {
			while (y > 0
					&& (jumpOverColor == board.getPegColor(x, y - 1) || board
							.getPegColor(x, y - 1) == 0)) {
				y--;
				if (board.getPegColor(x, y) == 0) {

					board.enablePeg(x, y);

					return true;
				}

			}
		}
		return false;
	}

	private boolean checkOpenUp(int x, int y) {
		int currColor = board.getPegColor(x, y);
		int jumpOverColor;
		if (x > 0 && currColor != (jumpOverColor = board.getPegColor(x - 1, y))
				&& jumpOverColor != 0) {
			while (x > 0
					&& (jumpOverColor == board.getPegColor(x - 1, y) || board
							.getPegColor(x - 1, y) == 0)) {
				x--;
				if (board.getPegColor(x, y) == 0) {

					board.enablePeg(x, y);

					return true;
				}

			}
		}
		return false;
	}

	public int move() {
		toPeg.setColor(fromPeg.getPegColor());
		fromPeg.setColor(0);
		fromPeg.repaint();

		if (level == level1) {
			removeSpotsEasy();
		} else {
			removeSpotsHard();
		}

		int spaces;
		int peg1X = fromPeg.getXLocation();
		int peg2X = toPeg.getXLocation();

		if (peg1X == peg2X) {
			spaces = Math.abs(fromPeg.getYLocation() - toPeg.getYLocation());
		} else {
			spaces = Math.abs(peg1X - peg2X);
		}

		if (level == level1) {
			return setMoveScoreEasy(spaces);
		} else {
			return setMoveScoreHard(spaces);
		}

	}

	private boolean gameOver() {
		int count = 0;
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				if (board.isPegEnabled(i, j)) {
					return false;
				}
				if (board.getPegColor(i, j) != 0) {
					count++;
				}
			}
		}
		if (count == 1) {
			bonus = true;
		}
		return true;
	}

	public int setMoveScoreEasy(int spaces) {
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

	private int setMoveScoreHard(int spaces) {
		int score = 0;

		if (spaces - 1 > 1) {
			score = 10 * (int) Math.pow(spaces - 1, 3);
		}

		return score;
	}

	public void removeSpotsEasy() {
		int fromX = fromPeg.getXLocation();
		int toX = toPeg.getXLocation();
		int fromY = fromPeg.getYLocation();
		int toY = toPeg.getYLocation();
		if (fromX == toX) {
			// left
			if (fromY > toY) {
				for (int i = toY + 1; i <= fromY; i++) {
					board.erasePeg(fromX, i);
				}
			}
			// right
			else {
				for (int i = fromY; i < toY; i++) {
					board.erasePeg(fromX, i);
				}
			}
		} else {
			// up
			if (fromX > toX) {
				for (int i = toX + 1; i <= fromX; i++) {
					board.erasePeg(i, fromY);
				}
			}
			// down
			else {
				for (int i = fromX; i < toX; i++) {
					board.erasePeg(i, fromY);
				}
			}
		}
	}

	public void removeSpotsHard() {
		int fromX = fromPeg.getXLocation();
		int toX = toPeg.getXLocation();
		int fromY = fromPeg.getYLocation();
		int toY = toPeg.getYLocation();
		if (fromX == toX) {
			int spots = Math.abs(fromY - toY);
			if (spots == 2) { // only skipped over 1
				int maxY = Math.max(fromY, toY);
				board.setPegRandomColor(fromX, maxY - 1);
			} else { // more than one skipped over
				// left
				if (fromY > toY) {
					for (int i = toY + 1; i <= fromY; i++) {
						board.erasePeg(fromX, i);
					}
				}
				// right
				else {
					for (int i = fromY; i < toY; i++) {
						board.erasePeg(fromX, i);
					}
				}
			}
		} else { // Y's are the same
			int spots = Math.abs(fromX - toX);
			if (spots == 2) { // only skipped over 1
				int maxX = Math.max(fromX, toX);
				board.setPegRandomColor(maxX, fromY);
			} else { // more than one skipped over
				// up
				if (fromX > toX) {
					for (int i = toX + 1; i <= fromX; i++) {
						board.erasePeg(i, fromY);
					}
				}
				// down
				else {
					for (int i = fromX; i < toX; i++) {
						board.erasePeg(i, fromY);
					}
				}

			}
		}
	}

	

	private void setMiddlePeg() {
		int middle = Board.ROWS / 2;
		// pegs[middle][middle].setOpaque(false);
		board.setPegColor(middle, middle, 0);
		checkEnablePeg(middle, middle);
	}

	public void enableAllPegs() {
		for (int i = 0; i < Board.ROWS; i++) {
			for (int j = 0; j < Board.ROWS; j++) {
				checkEnablePeg(i, j);
			}
		}
	}

	private void checkEnablePeg(int i, int j) {
		if (shouldEnable(i, j)) {
			board.enablePeg(i, j);
		} else {
			board.disablePeg(i, j);
		}
	}

	public boolean shouldEnable(int x, int y) {
		/*
		 * check all 4 directions: if peg next to it exist and not same color
		 * then check if one next to that put on stack if exists and same color
		 * as previous then go to next and repeat else if doesn't exist then
		 * possible move else check next direction else if no possible moves in
		 * either direction then disable button
		 */
		if (board.getPegColor(x, y) == 0) {
			return false;
		}
		return (checkRight(x, y) || checkLeft(x, y) || checkUp(x, y) || checkDown(
				x, y));
	}

	private boolean checkRight(int x, int y) {
		int currColor = board.getPegColor(x, y);
		int col = y + 1;
		if (col >= Board.ROWS) {
			return false;
		}
		int jumpOverColor = board.getPegColor(x, col);
		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		col++;
		int newJumpColor = jumpOverColor;
		while (col < Board.ROWS && newJumpColor == jumpOverColor) {
			int landingColor = board.getPegColor(x, col);
			if (landingColor == 0) {
				return true;
			}
			col++;
			newJumpColor = landingColor;
		}
		return false;
	}

	private boolean checkLeft(int x, int y) {
		int currColor = board.getPegColor(x, y);
		int col = y - 1;
		if (col < 0) {
			return false;
		}
		int jumpOverColor = board.getPegColor(x, col);

		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		col--;
		int newJumpColor = jumpOverColor;
		while (col >= 0 && newJumpColor == jumpOverColor) {
			int landingColor = board.getPegColor(x, col);
			if (landingColor == 0) {
				return true;
			}
			col--;
			newJumpColor = landingColor;
		}
		return false;
	}

	private boolean checkUp(int x, int y) {

		int currColor = board.getPegColor(x, y);
		int row = x - 1;
		if (row < 0) {
			return false;
		}
		int jumpOverColor = board.getPegColor(row, y);

		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		row--;
		int newJumpColor = jumpOverColor;
		while (row >= 0 && newJumpColor == jumpOverColor) {
			int landingColor = board.getPegColor(row, y);
			if (landingColor == 0) {
				return true;
			}
			row--;
			newJumpColor = landingColor;
		}
		return false;
	}

	private boolean checkDown(int x, int y) {

		int currColor = board.getPegColor(x, y);
		int row = x + 1;
		if (row >= Board.ROWS) {
			return false;
		}
		int jumpOverColor = board.getPegColor(row, y);
		if (jumpOverColor == 0 || jumpOverColor == currColor) {
			return false;
		}
		row++;
		int newJumpColor = jumpOverColor;
		while (row < Board.ROWS && newJumpColor == jumpOverColor) {
			int landingColor = board.getPegColor(row, y);
			if (landingColor == 0) {
				return true;
			}
			row++;
			newJumpColor = landingColor;
		}
		return false;
	}

}
