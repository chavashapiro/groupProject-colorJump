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
	private JButton rightArrow;
	private JButton leftArrow;
	private JButton help;
	private ButtonsPanel buttonsPanel;
	private Board board;
	private int points;
	private GameOver gameOver;

	@Inject
	public Game(Board board, ButtonsPanel buttonsPanel, CheckAlgorithms checker, GameOver gameOver) {
		setBackground(new Color(176, 224, 230));
		setLayout(new BorderLayout());
		restartEasy = new JButton("NEW EASY GAME");
		rightArrow = new JButton(" >");
		leftArrow = new JButton("< ");
		help = new JButton("HELP");
		this.board = board;
		this.gameOver = gameOver;
		addListeners();
		buttonsPanel.addButton(help, restartEasy, rightArrow, leftArrow);
		this.buttonsPanel = buttonsPanel;
		add(board, BorderLayout.CENTER);
		add(this.buttonsPanel, BorderLayout.EAST);
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		restartEasy.addMouseListener(mouseListener);
		rightArrow.addMouseListener(mouseListener);
		leftArrow.addMouseListener(mouseListener);
		help.addMouseListener(mouseListener);
		this.board.addListeners(listener, pegMouseListener);
	}

	ActionListener listener = new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
			// TODO Auto-generated method stub
			Thread thread = new Thread() {
				public void run() {
					setCursor(new Cursor(Cursor.WAIT_CURSOR));
					points = board.pegClicked(e);
					if (points > 0) {
						buttonsPanel.addScore(points);
					}
					if (board.isGameOver()) {
						buttonsPanel.pauseTimer();
						if (board.isBonus()) {
							buttonsPanel.setBonus();
						}
						gameOver.setScoreTime(buttonsPanel.getScore(), buttonsPanel.getSeconds());
						gameOver.setVisible(true);
						board.setGameOver(false);
						board.restart();
					}
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			};
			thread.start();

		}

	};

	MouseListener pegMouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
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
			// if (b == restartEasy) {
			if (b.getText() == "NEW EASY GAME") {
				buttonsPanel.restart();
				board.setLevel(1);
				board.restart();
				// } else if (b == restartHard) {
			} else if (b.getText() == "NEW HARD GAME") {
				buttonsPanel.restart();
				board.setLevel(2);
				board.restart();
			} else if (b == help) {
				buttonsPanel.getHelp();
			} else if (b == rightArrow || b == leftArrow) {
				if (restartEasy.getText() == "NEW EASY GAME") {
					restartEasy.setText("NEW HARD GAME");
				} else {
					restartEasy.setText("NEW EASY GAME");
				}
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
