package colorJump;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@Singleton
public class ButtonsPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton easyRestart;
	private JPanel restartPanel;
	private JButton help;
	private JLabel scoreLabel;
	private JLabel scoreNumLabel;
	private JPanel scorePanel;
	private JLabel logo;
	private Font bebasFont;
	private HelpDialog helpDialog;
	private int points;
	private int bonus;
	private JLabel gameTimer;
	private int seconds;
	private ScheduledExecutorService executor;
	private DecimalFormat format;

	@Inject
	public ButtonsPanel(HelpDialog helpDialog) {
		setLayout(new GridLayout(5, 0));
		setOpaque(false);
		setPanelSize();
		this.helpDialog = helpDialog;
		restartPanel = new JPanel();
		restartPanel.setLayout(new FlowLayout());
		setFonts();

		points = 0;
		bonus = 0;
		seconds = 0;

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
		format = new DecimalFormat("000");
		gameTimer = new JLabel("000", SwingConstants.CENTER);
		formatLabel(gameTimer);
		addTimer();

	}

	private void addTimer() {
		Runnable timer = new Runnable() {
			public void run() {
				seconds++;
				gameTimer.setText(format.format(seconds));
			}
		};
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(timer, 0, 1, TimeUnit.SECONDS);
	}

	private void formatLabel(JLabel label) {
		label.setFont(bebasFont);
		removeDecor(label);
	}

	private void formatButton(JButton button) {
		button.setFont(bebasFont);
		button.setContentAreaFilled(false);
		removeDecor(button);

	}

	private void setFonts() {
		try {
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			InputStream in = getClass().getResource("/BEBAS__.TTF").openStream();
			bebasFont = Font.createFont(Font.TRUETYPE_FONT, in).deriveFont(20f);

			ge.registerFont(bebasFont);

		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setPanelSize() {
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
		points *= 2;
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

	public void addButton(JButton helpButton, JButton easyRestart, JButton rightArrow, JButton leftArrow) {
		formatButton(rightArrow);
		formatButton(leftArrow);
		this.easyRestart = easyRestart;
		this.restartPanel.add(leftArrow);
		restartPanel.add(this.easyRestart);
		restartPanel.add(rightArrow);
		formatButton(this.easyRestart);
		help = helpButton;
		formatButton(help);
		add(logo);
		add(gameTimer);
		add(scorePanel);
		add(restartPanel);
		add(help);
	}

	public void restart() {
		points = 0;
		bonus = 0;
		seconds = 0;
		setScore();
	}

	public int getSeconds() {
		return seconds;
	}

	public void addScore(int points) {
		this.points += points;
		setScore();
	}

	public void setScore() {
		scoreNumLabel.setText(String.valueOf(points));
	}

	public void pauseTimer() {
		executor.shutdown();
	}
}
