package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@Singleton
public class ButtonsPanel extends JPanel implements MouseListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton restart;
	private JButton help;
	private JLabel score;
	private JLabel scoreNum;
	private JPanel scorePanel;
	private JLabel logo;
	private GamePanel gamePanel;
	private Font bebasFont;
	@Inject
	public ButtonsPanel(final GamePanel gamePanel) {
		setLayout(new GridLayout(4, 0));
		setOpaque(false);
		Dimension d = new Dimension(200, 650);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);

		this.gamePanel = gamePanel;
		this.bebasFont = null;

		try {
			GraphicsEnvironment ge = GraphicsEnvironment
					.getLocalGraphicsEnvironment();

			InputStream in = getClass().getResource("/BEBAS__.TTF")
					.openStream();
			bebasFont = Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(20f);

			ge.registerFont(bebasFont);

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logo = new JLabel(new ImageIcon(getClass().getResource("/Peg2.png")));

		restart = new JButton("New Game");
		restart.setFont(bebasFont);
		restart.setContentAreaFilled(false);
		restart.addMouseListener(this);
		removeDecor(restart);
		help = new JButton("Help");
		help.setFont(bebasFont);
		help.setContentAreaFilled(false);
		help.addMouseListener(this);
		removeDecor(help);

		scorePanel = new JPanel(new BorderLayout());
		removeDecor(scorePanel);

		scoreNum = new JLabel("0");
		scoreNum.setFont(bebasFont);
		scoreNum.setPreferredSize(new Dimension(75, 50));
		removeDecor(scoreNum);

		score = new JLabel("SCORE: ", SwingConstants.RIGHT);
		score.setFont(bebasFont);
		removeDecor(score);

		scorePanel.add(score, BorderLayout.CENTER);
		scorePanel.add(scoreNum, BorderLayout.EAST);

		add(logo);
		add(scorePanel);
		add(restart);
		add(help);
	}

	public void setScore(int s) {
		scoreNum.setText(String.valueOf(s));
	}

	public void removeDecor(JComponent c) {
		c.setOpaque(false);
		c.setBorder(null);
		c.setFont(bebasFont);
	}

	public void mouseClicked(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		if (b == restart) {
			gamePanel.restart();
			setScore(0);
			gamePanel.resetScore();
		} else if (b == help) {
			getHelp();
		}
		b.setBorderPainted(false);
		b.setBorder(null);
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

	public void mouseReleased(MouseEvent e) {
	}

	public void getHelp() {
		HelpDialog help = new HelpDialog();
		help.setLocationRelativeTo(gamePanel.getGameFrame());
	}
}
