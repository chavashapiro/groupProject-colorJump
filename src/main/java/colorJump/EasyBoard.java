package colorJump;

public class EasyBoard extends Board {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EasyBoard() {
		super();
	}

	protected void removeSpots() {
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
					erasePeg(fromX, i);
				}
			}
		} else {
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
