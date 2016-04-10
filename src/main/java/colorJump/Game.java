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
	private int points;

	@Inject
	public Game(Board board, ButtonsPanel buttonsPanel, CheckAlgorithms checker) {
		setBackground(new Color(176, 224, 230));
		setLayout(new BorderLayout());
		restartEasy = new JButton("NEW EASY GAME");
		restartHard = new JButton("NEW HARD GAME");
		help = new JButton("HELP");
		this.board = board;
		addListeners();
		buttonsPanel.addButton(help, restartEasy, restartHard);
		this.buttonsPanel = buttonsPanel;
		add(board, BorderLayout.CENTER);
		add(this.buttonsPanel, BorderLayout.EAST);
	}

	private void addListeners() {
		// TODO Auto-generated method stub
		restartEasy.addMouseListener(mouseListener);
		restartHard.addMouseListener(mouseListener);
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
						if (board.isBonus()) {
							buttonsPanel.setBonus();
						}
						GameOver gameOver = new GameOver(
								buttonsPanel.getScore(),
								buttonsPanel.getBonus());
						gameOver.setVisible(true);
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
			if (b == restartEasy) {
				buttonsPanel.restart();
				board.setLevel(1);
				board.restart();
			} else if (b == restartHard) {
				buttonsPanel.restart();
				board.setLevel(2);
				board.restart();
			} else {

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
