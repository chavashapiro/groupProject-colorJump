package colorJump;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	private ButtonsPanel buttonsPanel;
	private GamePanel gamePanel;

	public GameFrame() {
		setTitle("PEG");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 650);
		setLayout(new BorderLayout());
		gamePanel = new GamePanel(this);
		buttonsPanel = new ButtonsPanel(gamePanel);

		Container container = getContentPane();
		container.add(gamePanel, BorderLayout.CENTER);
		container.add(buttonsPanel, BorderLayout.EAST);
		new GameOver(gamePanel).setVisible(true);
	}

	public GamePanel getGame() {
		return gamePanel;
	}

	public ButtonsPanel getButtonsPanel() {
		return buttonsPanel;
	}


	public static void main(String[] args) {
		GameFrame gui = new GameFrame();
		gui.setVisible(true);
		//gui.getButtonsPanel().getHelp();
	}
}
