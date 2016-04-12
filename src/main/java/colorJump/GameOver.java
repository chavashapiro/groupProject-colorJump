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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class GameOver extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int scoreNum, bonusNum, totalNum, highScore, secondsNum;
	private JLabel score, bonus, total, seconds, secondsLbl, gameOver,
			scoreLbl, bonusLbl, totalLbl, highScoreLbl, highScoreValueLbl;
	private JButton ok;
	private JPanel scorePanel;

	public GameOver(int gameScore, int gameBonus, int gameSeconds)
			throws FileNotFoundException, ClassNotFoundException, IOException {
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 450);
		setLayout(new BorderLayout());
		// setLocationRelativeTo(gameFrame);

		Container container = getContentPane();
		container.setBackground(new Color(176, 224, 230));
		highScore = 0;
		getSavedHighScore();
		scoreNum = gameScore;
		bonusNum = gameBonus;
		secondsNum = gameSeconds;
		totalNum = scoreNum + bonusNum;
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
		scorePanel.setLayout(new GridLayout(5, 2));
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void instantiateLabels() {
		// TODO Auto-generated method stub
		score = new JLabel(String.valueOf(scoreNum), SwingConstants.RIGHT);
		bonus = new JLabel(String.valueOf(bonusNum), SwingConstants.RIGHT);
		total = new JLabel(String.valueOf(totalNum), SwingConstants.RIGHT);
		seconds = new JLabel(String.valueOf(secondsNum), SwingConstants.RIGHT);
		highScoreValueLbl = new JLabel(String.valueOf(highScore),
				SwingConstants.RIGHT);
		secondsLbl = new JLabel("Seconds:");
		scoreLbl = new JLabel("Score:");
		bonusLbl = new JLabel("Bonus:");
		totalLbl = new JLabel("Total:");
		highScoreLbl = new JLabel("High Score:");
	}

	private void addScorePanelLabels() {
		// TODO Auto-generated method stub
		scorePanel.add(scoreLbl);
		scorePanel.add(score);
		scorePanel.add(bonusLbl);
		scorePanel.add(bonus);
		scorePanel.add(secondsLbl);
		scorePanel.add(seconds);
		scorePanel.add(highScoreLbl);
		scorePanel.add(highScoreValueLbl);
		scorePanel.add(totalLbl);
		scorePanel.add(total);
	}

	private void setFonts() {
		// TODO Auto-generated method stub
		setFont(score);
		setFont(scoreLbl);
		setFont(bonus);
		setFont(bonusLbl);
		setFont(totalLbl);
		setFont(total);
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
			// TODO Auto-generated method stub
			ObjectOutputStream output = new ObjectOutputStream(
					new FileOutputStream("HighScore.ser"));
			output.writeObject(scoreNum);
			output.close();
		}
	}

	private void getSavedHighScore() throws FileNotFoundException, IOException,
			ClassNotFoundException {
		ObjectInputStream input = new ObjectInputStream(new FileInputStream(
				"HighScore.ser"));

		highScore = (Integer) input.readObject();
		input.close();
	}

	public void setFont(JLabel l) {
		l.setFont(new Font("Bebas", Font.PLAIN, 20));
	}
}