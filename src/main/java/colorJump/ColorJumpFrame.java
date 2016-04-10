package colorJump;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

public class ColorJumpFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public ColorJumpFrame(Game game, HelpDialog help) {
		setTitle("PEG");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850, 650);
		setLayout(new BorderLayout());
		Container container = getContentPane();
		container.add(game);
		help.setLocationRelativeTo(this);
		ImageIcon icon = new ImageIcon(getClass().getResource("/pink_peg.png"));
		setIconImage(icon.getImage());
	}

	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new ColorJumpModule());
		ColorJumpFrame gui = injector.getInstance(ColorJumpFrame.class);
		gui.setVisible(true);

	}
}
