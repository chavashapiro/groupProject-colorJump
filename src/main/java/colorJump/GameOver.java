package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@Singleton
public class GameOver extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int scoreNum, highScore, secondsNum;
	private JLabel score, seconds, secondsLbl, gameOver, scoreLbl, highScoreLbl, highScoreValueLbl;
	private JButton ok;
	private JPanel scorePanel;

	public GameOver() throws FileNotFoundException, ClassNotFoundException, IOException {
		setTitle("");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 450);
		setLayout(new BorderLayout());
		// setLocationRelativeTo(gameFrame);

		Container container = getContentPane();
		container.setBackground(new Color(176, 224, 230));
		highScore = 0;
		getSavedHighScore();
		scoreNum = 0;
		secondsNum = 0;
		JPanel title = new JPanel();
		title.setOpaque(false);
		title.setLayout(new GridBagLayout());
		title.setPreferredSize(new Dimension(this.getWidth(), 150));
		gameOver = new JLabel(new ImageIcon(getClass().getResource("/game_over.png")));
		gameOver.setHorizontalAlignment(JLabel.CENTER);
		gameOver.setVerticalAlignment(JLabel.CENTER);
		title.add(gameOver);
		scorePanel = new JPanel();
		scorePanel.setOpaque(false);
		scorePanel.setLayout(new GridLayout(3, 2));
		scorePanel.setBorder(new EmptyBorder(25, 100, 25, 100));
		instantiateLabels();

		setFonts();
		addScorePanelLabels();

		JPanel close = new JPanel();
		close.setOpaque(false);
		close.setPreferredSize(new Dimension(this.getWidth(), 75));
		close.setBorder(new EmptyBorder(0, 200, 0, 200));
		ok = new JButton("Close");
		ok.setFont(new Font("Bebas", Font.PLAIN, 15));
		ok.setContentAreaFilled(false);
		ok.setBorderPainted(false);
		ok.setFocusPainted(false);
		ok.addMouseListener(closeMouseListener);
		close.add(ok);
		container.add(title, BorderLayout.NORTH);
		container.add(scorePanel, BorderLayout.CENTER);
		container.add(close, BorderLayout.SOUTH);
		try {
			saveScore();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setScoreTime(int gameScore, int gameSeconds) {
		scoreNum = gameScore;
		secondsNum = gameSeconds;
		score.setText(String.valueOf(scoreNum));
		seconds.setText(String.valueOf(secondsNum));
	}

	private void instantiateLabels() {
		score = new JLabel(String.valueOf(scoreNum), SwingConstants.RIGHT);
		seconds = new JLabel(String.valueOf(secondsNum), SwingConstants.RIGHT);
		highScoreValueLbl = new JLabel(String.valueOf(highScore), SwingConstants.RIGHT);
		secondsLbl = new JLabel("Seconds:");
		scoreLbl = new JLabel("Score:");
		highScoreLbl = new JLabel("High Score:");
	}

	private void addScorePanelLabels() {
		scorePanel.add(scoreLbl);
		scorePanel.add(score);
		scorePanel.add(secondsLbl);
		scorePanel.add(seconds);
		scorePanel.add(highScoreLbl);
		scorePanel.add(highScoreValueLbl);
	}

	private void setFonts() {
		setFont(score);
		setFont(scoreLbl);
		setFont(seconds);
		setFont(secondsLbl);
		setFont(highScoreLbl);
		setFont(highScoreValueLbl);
	}

	MouseListener closeMouseListener = new MouseAdapter() {
		public void mouseClicked(MouseEvent arg0) {
			// game.restart();
			// buttonsPanel.setScore(0);
			dispose();
		}

		public void mouseEntered(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setForeground(Color.WHITE);
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		public void mouseExited(MouseEvent e) {
			JButton b = (JButton) e.getSource();
			b.setForeground(Color.BLACK);
			b.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
	};

	public void saveScore() throws FileNotFoundException, IOException {
		if (scoreNum > highScore) {
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("HighScore.ser"));
			output.writeObject(scoreNum);
			output.close();
		}
	}

	private void getSavedHighScore() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream("HighScore.ser"));

		highScore = (Integer) input.readObject();
		input.close();
	}

	public void setFont(JLabel l) {
		l.setFont(new Font("Bebas", Font.PLAIN, 20));
	}
}