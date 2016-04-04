package colorJump;

import java.util.Random;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JPanel;

@Singleton
public class Board extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[][] numbers;
	private Random random;

	public Board() {
		numbers = new int[7][7];
		random = new Random();
		newGame();
	}

	public void newGame() {
		for (int i = 0; i < numbers.length; i++) {
			for (int j = 0; j < numbers[0].length; j++) {
				numbers[i][j] = random.nextInt(6) + 1;
			}
		}
		numbers[3][3] = 0;
	}

	public int getValue(int x, int y) {
		return numbers[x][y];
	}

	public int move(int fromX, int fromY, int toX, int toY) {
		numbers[toX][toY] = numbers[fromX][fromY];
		numbers[fromX][fromY] = 0;

		int dif, score = 0;

		if (toX == fromX) {
			dif = Math.abs(fromY - toY);
		} else {
			dif = Math.abs(fromX - toX);
		}

		switch (dif - 1) {
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

	public int[][] getBoard() {
		return numbers;
	}
}