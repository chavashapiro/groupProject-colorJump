package colorJump;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
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
public class ButtonsPanel extends JPanel{
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
	private HelpDialog helpDialog;
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
	public void getHelp() {
		helpDialog.setLocationRelativeTo(this);
		helpDialog.setVisible(true);
	}


	public void addButton( JButton helpButton, JButton easyRestart, JButton hardRestart) {
		this.easyRestart = easyRestart;
		formatButton(this.easyRestart);
		this.hardRestart = hardRestart;
		formatButton(this.hardRestart);
		add(logo);
		add(scorePanel);
		add(this.easyRestart);
		add(this.hardRestart);
		help=helpButton;
		formatButton(help);
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
