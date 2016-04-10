package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

@Singleton
public class Game extends JComponent {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton restartEasy;
	private JButton restartHard;
	private JButton help;
	private ButtonsPanel buttonsPanel;
	private Board board;
	private final EasyBoard easyBoard;
	private final HardBoard hardBoard;
	private int points;

	@Inject
	public Game(EasyBoard easyBoard, HardBoard hardBoard,
			ButtonsPanel buttonsPanel, CheckAlgorithms checker) {
		setBackground(new Color(176, 224, 230));
		setLayout(new BorderLayout());
		restartEasy = new JButton("NEW EASY GAME");
		restartEasy.addMouseListener(mouseListener);
		restartHard = new JButton("NEW HARD GAME");
		restartHard.addMouseListener(mouseListener);
		help = new JButton("HELP");
		help.addMouseListener(mouseListener);
		buttonsPanel.addButton(help, restartEasy, restartHard);
		this.buttonsPanel = buttonsPanel;
		this.easyBoard = easyBoard;
		this.hardBoard = hardBoard;
		this.easyBoard.addListeners(listener, pegMouseListener);
		this.hardBoard.addListeners(listener, pegMouseListener);
		board = this.easyBoard;
		add(board, BorderLayout.CENTER);
		add(this.buttonsPanel, BorderLayout.EAST);
	}

	ActionListener listener = new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			System.out.println("Action listener-wait cursor");
			// TODO Auto-generated method stub

			Thread thread = new Thread() {
				public void run() {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
					System.out.println("trying");
					points = board.pegClicked(e);
					System.out.println("done checking");
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			};
			thread.start();

			System.out.println("Action listener-peg clicked");
			if (points > 0) {
				System.out.println("Action listener-add score");
				buttonsPanel.addScore(points);
			}
			System.out.println("Action listener-check if game over");
			if (board.isGameOver()) {
				if (board.isBonus()) {
					buttonsPanel.setBonus();
				}
				GameOver gameOver = new GameOver(buttonsPanel.getScore(),
						buttonsPanel.getBonus());
				gameOver.setVisible(true);
			}

		}

	};

	MouseListener pegMouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("mouse clicked");
			if (SwingUtilities.isRightMouseButton(e)) {
				board.deselectFromPeg();
				board.enableAllPegs();
			}
		}

	};
	MouseListener mouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setBorderPainted(false);
			b.setContentAreaFilled(false);
			b.setBorder(null);
			if (b == restartEasy) {
				buttonsPanel.restart();
				board.restart();
				board = easyBoard;
				board.restart();

			} else if (b == restartHard) {
				buttonsPanel.restart();
				board.restart();
				board = hardBoard;
				board.restart();
			}

			else {
				buttonsPanel.getHelp();
			}
		}

		public void mouseEntered(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setForeground(Color.GRAY);
		}

		public void mouseExited(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setForeground(Color.BLACK);
		}

		public void mousePressed(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setContentAreaFilled(false);
		}
	};

}
