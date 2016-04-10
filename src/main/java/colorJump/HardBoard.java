package colorJump;

import java.util.Random;

public class HardBoard extends Board {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Random random;

	public HardBoard() {
		super();
		random = new Random();
	}

	protected void removeSpots() {
		int fromX = fromPeg.getXLocation();
		int toX = toPeg.getXLocation();
		int fromY = fromPeg.getYLocation();
		int toY = toPeg.getYLocation();
		if (fromX == toX) {
			int spots = Math.abs(fromY - toY);
			if (spots == 2) { // only skipped over 1
				if (fromY > toY) {
					pegs[fromX][fromY - 1].setColor(random.nextInt(6) + 1);
				} else {
					pegs[fromX][toY - 1].setColor(random.nextInt(6) + 1);
				}
			} else { // more than one skipped over
				// left
				if (fromY > toY) {
					for (int i = toY + 1; i <= fromY; i++) {
						erasePeg(fromX, i);
					}
				}
				// right
				else {
					for (int i = fromY; i < toY; i++) {
						erasePeg(fromX, i);
					}
				}
			}
		} else { // Y's are the same
			int spots = Math.abs(fromX - toX);
			if (spots == 2) { // only skipped over 1
				if (fromX > toX) {
					pegs[fromX - 1][fromY].setColor(random.nextInt(6) + 1);
				} else {
					pegs[toX - 1][fromY].setColor(random.nextInt(6) + 1);
				}
			} else { // more than one skipped over
				// up
				if (fromX > toX) {
					for (int i = toX + 1; i <= fromX; i++) {
						erasePeg(i, fromY);
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
	}

}
