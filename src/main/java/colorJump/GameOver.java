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

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@Singleton
public class GameOver extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int scoreNum, secondsNum;
	private JLabel score, seconds, secondsLbl, gameOver, scoreLbl,
			highScoreLbl, highScoreLbl1, highScoreValueLbl1, highScoreLbl2,
			highScoreLbl3, highScoreValue2, highScoreValue3;
	private JButton ok;
	private JPanel scorePanel;
	private HighScoreInfo highScoreInfo;

	@Inject
	public GameOver(HighScoreInfo highScoreInfo) throws FileNotFoundException,
			ClassNotFoundException, IOException {
		setTitle("");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(650, 450);
		setLayout(new BorderLayout());
		// setLocationRelativeTo(gameFrame);

		Container container = getContentPane();
		container.setBackground(new Color(176, 224, 230));
		this.highScoreInfo = highScoreInfo;
		getSavedHighScore();
		scoreNum = 0;
		secondsNum = 0;

		JPanel title = new JPanel();
		title.setOpaque(false);
		title.setLayout(new GridBagLayout());
		title.setPreferredSize(new Dimension(this.getWidth(), 150));
		gameOver = new JLabel(new ImageIcon(getClass().getResource(
				"/game_over.png")));
		gameOver.setHorizontalAlignment(JLabel.CENTER);
		gameOver.setVerticalAlignment(JLabel.CENTER);
		title.add(gameOver);
		scorePanel = new JPanel();
		scorePanel.setOpaque(false);
		scorePanel.setLayout(new GridLayout(6, 2));
		scorePanel.setBorder(new EmptyBorder(0, 100, 0, 100));
		instantiateLabels();

		setFonts();
		addScorePanelLabels();

		JPanel close = new JPanel();
		close.setOpaque(false);
		close.setPreferredSize(new Dimension(this.getWidth(), 75));
		close.setBorder(new EmptyBorder(0, 75, 0, 75));
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
	}

	public void setScoreTime(int gameScore, int gameSeconds) {
		scoreNum = gameScore;
		secondsNum = gameSeconds;
		score.setText(String.valueOf(scoreNum));
		seconds.setText(String.valueOf(secondsNum));
		setHighScoreLabels();
	}

	private void instantiateLabels() {
		score = new JLabel(String.valueOf(scoreNum), SwingConstants.RIGHT);
		seconds = new JLabel(String.valueOf(secondsNum), SwingConstants.RIGHT);
		secondsLbl = new JLabel("Seconds:");
		scoreLbl = new JLabel("Score:");
		highScoreLbl = new JLabel("<HTML><U>HIGH SCORE:</U></HTML>");
		highScoreValueLbl1 = new JLabel("", SwingConstants.RIGHT);
		highScoreValue2 = new JLabel("", SwingConstants.RIGHT);
		highScoreValue3 = new JLabel("", SwingConstants.RIGHT);
		highScoreLbl1 = new JLabel();
		highScoreLbl2 = new JLabel();
		highScoreLbl3 = new JLabel();
		setHighScoreLabels();
	}

	public void setHighScoreLabels() {
		int[] highScores = highScoreInfo.getHighScore();
		highScoreValueLbl1.setText((String.valueOf(highScores[0])));
		highScoreValue2.setText(String.valueOf(highScores[1]));
		highScoreValue3.setText(String.valueOf(highScores[2]));
		String[] highScoreNames = highScoreInfo.getHighScoreNames();
		highScoreLbl1.setText(highScoreNames[0]);
		highScoreLbl2.setText(highScoreNames[1]);
		highScoreLbl3.setText(highScoreNames[2]);

	}

	private void addScorePanelLabels() {
		scorePanel.add(scoreLbl);
		scorePanel.add(score);
		scorePanel.add(secondsLbl);
		scorePanel.add(seconds);
		scorePanel.add(highScoreLbl);
		scorePanel.add(new JLabel());
		scorePanel.add(highScoreLbl1);
		scorePanel.add(highScoreValueLbl1);
		scorePanel.add(highScoreLbl2);
		scorePanel.add(highScoreValue2);
		scorePanel.add(highScoreLbl3);
		scorePanel.add(highScoreValue3);
	}

	private void setFonts() {
		setFont(score);
		setFont(scoreLbl);
		setFont(seconds);
		setFont(secondsLbl);
		setFont(highScoreLbl);
		setFont(highScoreValueLbl1);
		setFont(highScoreLbl1);
		setFont(highScoreValue2);
		setFont(highScoreLbl2);
		setFont(highScoreValue3);
		setFont(highScoreLbl3);
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
		if (scoreNum > highScoreInfo.lowestScore()) {
			String name = JOptionPane
					.showInputDialog(null,
							"Congratulations! You've made it to the high score panel! \nEnter Player Name:");
			highScoreInfo.setNewHighScore(scoreNum, name);
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream("HighScore.ser"));
			output.writeObject(highScoreInfo);
			output.close();
		}
	}

	private void getSavedHighScore() throws FileNotFoundException, IOException,
			ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(
				"HighScore.ser"));
		highScoreInfo = (HighScoreInfo) input.readObject();
		// highScore = (Integer) input.readObject();
		input.close();
	}

	public void setFont(JLabel l) {
		l.setFont(new Font("Bebas", Font.PLAIN, 22));
	}
}