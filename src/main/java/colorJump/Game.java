package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
	private int level;
	private GameOver gameOver;
	private ScheduledExecutorService musicExecutor;
	private MusicThread music;

	@Inject
	public Game(Board board, ButtonsPanel buttonsPanel,
			CheckAlgorithms checker, GameOver gameOver, MusicThread gameMusic) {
		setBackground(new Color(176, 224, 230));
		setLayout(new BorderLayout());
		restartEasy = new JButton("NEW EASY GAME");
		restartEasy.setForeground(Color.BLUE);
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
		level = 1;
		this.music=gameMusic;
		this.musicExecutor = Executors.newScheduledThreadPool(1);

		Runnable playSound = new Runnable() {
			public void run() {
				
				music= new MusicThread();
				music.start();
			}
		};
		this.musicExecutor.scheduleAtFixedRate(playSound, 0, 15,
				TimeUnit.SECONDS);
	}

	private void addListeners() {
		restartEasy.addMouseListener(mouseListener);
		rightArrow.addMouseListener(mouseListener);
		leftArrow.addMouseListener(mouseListener);
		help.addMouseListener(mouseListener);
		this.board.addListeners(listener, pegMouseListener);
	}

	ActionListener listener = new ActionListener() {
		public void actionPerformed(final ActionEvent e) {
			setCursor(new Cursor(Cursor.WAIT_CURSOR));
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
						gameOver.setScoreTime(buttonsPanel.getScore(),
								buttonsPanel.getSeconds());
						gameOver.setVisible(true);
						try {
							gameOver.saveScore();
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						board.setGameOver(false);
						board.restart();
						buttonsPanel.restart();
					}
					setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
			};
			thread.start();

		}

	};

	MouseListener pegMouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent e) {
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
			if (b.getText() == "NEW EASY GAME") {
				buttonsPanel.restart();
				level = 1;
				board.setLevel(level);
				board.restart();
			} else if (b.getText() == "NEW HARD GAME") {
				buttonsPanel.restart();
				level = 2;
				board.setLevel(level);
				board.restart();
			} else if (b == help) {
				buttonsPanel.getHelp();
			} else if (b == rightArrow || b == leftArrow) {
				if (restartEasy.getText() == "NEW EASY GAME") {
					restartEasy.setText("NEW HARD GAME");
					if (level == 2) {
						restartEasy.setForeground(Color.BLUE);
					} else {
						restartEasy.setForeground(Color.BLACK);
					}
				} else {
					restartEasy.setText("NEW EASY GAME");
					if (level == 1) {
						restartEasy.setForeground(Color.BLUE);
					} else {
						restartEasy.setForeground(Color.BLACK);
					}
				}
			}
		}

		public void mouseEntered(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setForeground(Color.GRAY);
		}

		public void mouseExited(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			if (b.getText().equals("NEW EASY GAME") && level == 1) {
				b.setForeground(Color.BLUE);
			} else if (b.getText().equals("NEW HARD GAME") && level == 2) {
				b.setForeground(Color.BLUE);
			} else {
				b.setForeground(Color.BLACK);
			}
		}

		public void mousePressed(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setContentAreaFilled(false);
		}
	};

}
