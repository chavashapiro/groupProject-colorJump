package colorJump;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

@Singleton
public class Game extends JComponent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton restart;
	private ButtonsPanel buttonsPanel;
	private Board board;

	@Inject
	public Game(Board gameBoard, ButtonsPanel buttonsPanel) {
		setBackground(new Color(176, 224, 230));
		setLayout(new BorderLayout());
		restart = new JButton("NEW GAME");
		restart.addMouseListener(mouseListener);
		buttonsPanel.addButton(restart);
		this.buttonsPanel = buttonsPanel;
		board = gameBoard;
		board.addListeners(listener, pegMouseListener);
		add(board, BorderLayout.CENTER);
		add(buttonsPanel, BorderLayout.EAST);
	}
	ActionListener listener = new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int points = board.pegClicked(e);
			if (points > 0) {
				
				buttonsPanel.addScore(points);
			}
			if (board.isGameOver()) {
				if(board.isBonus()){
				buttonsPanel.setBonus();}
				GameOver gameOver = new GameOver(buttonsPanel.getScore(),
						buttonsPanel.getBonus());
				gameOver.setVisible(true);
			}
		}

		
	};
	MouseListener pegMouseListener= new MouseListener(){

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			System.out.println("mouse cliked");
			if (SwingUtilities.isRightMouseButton(e)) {
				board.deselectFromPeg();
				board.enableAllPegs();
			}
		}

		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			Peg peg = (Peg) e.getSource();
			if (board.isEnabled(peg.getXLocation(), peg.getYLocation())) {
				peg.setCursor(new Cursor(Cursor.HAND_CURSOR));
			}
		}

		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	};
	MouseListener mouseListener= new MouseListener(){
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == restart) {
				buttonsPanel.restart();
				board.restart();
				restart.setBorderPainted(false);
				restart.setBorder(null);
			}

		}

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == restart) {
				JButton b = (JButton) e.getSource();
				b.setForeground(Color.GRAY);
			}
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
			// TODO Auto-generated method stub
			
		}
	};
	
}
