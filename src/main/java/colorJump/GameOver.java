package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
	private int scoreNum, bonusNum, totalNum;
	private JLabel score, bonus, total, gameOver, scoreLbl, bonusLbl, totalLbl;
	private JButton ok;

	public GameOver(final GamePanel game, final ButtonsPanel buttonsPanel) {
		setTitle("");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(650, 450);
		setLayout(new BorderLayout());
		setLocationRelativeTo(game.getGameFrame());

		Container container = getContentPane();
		container.setBackground(new Color(176, 224, 230));

		scoreNum = game.getScore();
		bonusNum = game.getBonus();
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

		JPanel scorePanel = new JPanel();
		scorePanel.setOpaque(false);
		scorePanel.setLayout(new GridLayout(3, 2));
		scorePanel.setBorder(new EmptyBorder(25, 100, 25, 100));
		score = new JLabel(String.valueOf(scoreNum), SwingConstants.RIGHT);
		bonus = new JLabel(String.valueOf(bonusNum), SwingConstants.RIGHT);
		total = new JLabel(String.valueOf(totalNum), SwingConstants.RIGHT);
		scoreLbl = new JLabel("Score:");
		bonusLbl = new JLabel("Bonus:");
		totalLbl = new JLabel("Total:");

		setFont(score);
		setFont(scoreLbl);
		setFont(bonus);
		setFont(bonusLbl);
		setFont(totalLbl);
		setFont(total);

		scorePanel.add(scoreLbl);
		scorePanel.add(score);
		scorePanel.add(bonusLbl);
		scorePanel.add(bonus);
		scorePanel.add(totalLbl);
		scorePanel.add(total);

		JPanel close = new JPanel();
		close.setOpaque(false);
		close.setPreferredSize(new Dimension(this.getWidth(), 75));
		close.setBorder(new EmptyBorder(0, 200, 0, 200));
		ok = new JButton("Close");
		ok.setFont(new Font("Bebas", Font.PLAIN, 15));
		ok.setContentAreaFilled(false);
		ok.setBorderPainted(false);
		ok.setFocusPainted(false);
		ok.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent arg0) {
				game.restart();
				buttonsPanel.setScore(0);
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

			public void mousePressed(MouseEvent arg0) {
			}

			public void mouseReleased(MouseEvent arg0) {
			}
		});

		close.add(ok);

		container.add(title, BorderLayout.NORTH);
		container.add(scorePanel, BorderLayout.CENTER);
		container.add(close, BorderLayout.SOUTH);
	}

	public void setFont(JLabel l) {
		l.setFont(new Font("Bebas", Font.PLAIN, 20));
	}
}