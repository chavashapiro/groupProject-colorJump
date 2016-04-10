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
	private JButton easyRestart;
	private JButton hardRestart;
	private JButton help;
	private JLabel scoreLabel;
	private JLabel scoreNumLabel;
	private JPanel scorePanel;
	private JLabel logo;
	private Font bebasFont;
	HelpDialog helpDialog;
	private int points;
	private int bonus;

	@Inject
	public ButtonsPanel(HelpDialog helpDialog) {
		setLayout(new GridLayout(5, 0));
		setOpaque(false);
		setPanelSize();
		this.helpDialog = helpDialog;
		points = 0;
		bonus = 0;
		setFonts();
		logo = new JLabel(new ImageIcon(getClass().getResource("/Peg2.png")));
		help = new JButton("Help");
		formatButton(help);
		help.addMouseListener(this);
		scorePanel = new JPanel(new BorderLayout());
		removeDecor(scorePanel);
		scoreNumLabel = new JLabel("0");
		scoreNumLabel.setPreferredSize(new Dimension(75, 50));
		scoreLabel = new JLabel("SCORE: ", SwingConstants.RIGHT);
		formatLabel(scoreNumLabel);
		formatLabel(scoreLabel);
		scorePanel.add(scoreLabel, BorderLayout.CENTER);
		scorePanel.add(scoreNumLabel, BorderLayout.EAST);

	}

	private void formatLabel(JLabel label) {
		// TODO Auto-generated method stub
		label.setFont(bebasFont);
		removeDecor(label);
	}

	private void formatButton(JButton button) {
		// TODO Auto-generated method stub
		button.setFont(bebasFont);
		button.setContentAreaFilled(false);
		removeDecor(button);

	}

	private void setFonts() {
		// TODO Auto-generated method stub
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
	}

	private void setPanelSize() {
		// TODO Auto-generated method stub
		Dimension d = new Dimension(200, 650);
		this.setPreferredSize(d);
		this.setMaximumSize(d);
		this.setMinimumSize(d);
	}

	public int getScore() {
		return points;
	}

	public int getBonus() {
		return bonus;
	}

	public void setBonus() {
		this.bonus += points * 2;
	}

	public void removeDecor(JComponent c) {
		c.setOpaque(false);
		c.setBorder(null);
		c.setFont(bebasFont);
	}

	public void mouseClicked(MouseEvent e) {
		JButton b = (JButton) e.getSource();
		if (b == help) {
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
		helpDialog.setLocationRelativeTo(this);
		helpDialog.setVisible(true);
	}

	public void addEasyButton(JButton restartButton) {
		// TODO Auto-generated method stub
		this.easyRestart = restartButton;
		formatButton(easyRestart);
	}
	
	public void addHardButton(JButton restartButton) {
		// TODO Auto-generated method stub
		this.hardRestart = restartButton;
		formatButton(hardRestart);
		add(logo);
		add(scorePanel);
		add(easyRestart);
		add(hardRestart);
		add(help);

	}

	public void restart() {
		// TODO Auto-generated method stub4
		points = 0;
		bonus = 0;
		setScore();
	}

	public void addScore(int points) {
		// TODO Auto-generated method stub
		this.points += points;
		setScore();
	}

	public void setScore() {
		scoreNumLabel.setText(String.valueOf(points));
	}
}
