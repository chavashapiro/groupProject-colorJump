package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.inject.Singleton;
import javax.swing.JFrame;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

@Singleton
public class GameFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ButtonsPanel buttonsPanel;
	private GamePanel gamePanel;

	@Inject
	public GameFrame(Board board) {
		setTitle("PEG");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 650);
		setLayout(new BorderLayout());

		gamePanel = new GamePanel(this, board);
		buttonsPanel = new ButtonsPanel(gamePanel);
		Container container = getContentPane();
		container.setBackground(new Color(176, 224, 230));
		container.add(gamePanel, BorderLayout.CENTER);
		container.add(buttonsPanel, BorderLayout.EAST);
	}

	public GamePanel getGame() {
		return gamePanel;
	}

	public ButtonsPanel getButtonsPanel() {
		return buttonsPanel;
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new ColorJumpModule());
		GameFrame gui = injector.getInstance(GameFrame.class);
		gui.setVisible(true);
		gui.getButtonsPanel().getHelp();
	}
}
