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
	private JButton restartEasy;
	private JButton restartHard;
	private ButtonsPanel buttonsPanel;
	private Board board;
	private EasyBoard easyBoard;
	private HardBoard hardBoard;

	@Inject
	public Game(EasyBoard easyBoard, HardBoard hardBoard, ButtonsPanel buttonsPanel) {
		setBackground(new Color(176, 224, 230));
		setLayout(new BorderLayout());
		restartEasy = new JButton("NEW EASY GAME");
		restartEasy.addMouseListener(easyMouseListener);
		buttonsPanel.addEasyButton(restartEasy);
		restartHard = new JButton("NEW HARD GAME");
		restartHard.addMouseListener(hardMouseListener);
		buttonsPanel.addHardButton(restartHard);
		this.buttonsPanel = buttonsPanel;
		this.easyBoard = easyBoard;
		this.hardBoard = hardBoard;
		this.easyBoard.addListeners(listener, pegMouseListener);
		this.hardBoard.addListeners(listener, pegMouseListener);
		board = this.easyBoard;
		add(board, BorderLayout.CENTER);
		add(this.buttonsPanel, BorderLayout.EAST);
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
			System.out.println("mouse clicked");
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
	
	MouseListener easyMouseListener= new MouseListener(){
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == restartEasy) {
				buttonsPanel.restart();
				board.restart();
				board = easyBoard;
				board.restart();
				restartEasy.setBorderPainted(false);
				restartEasy.setBorder(null);
			}

		}

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == restartEasy) {
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
	
	MouseListener hardMouseListener= new MouseListener(){
		public void mouseClicked(MouseEvent e) {
			if (e.getSource() == restartHard) {
				buttonsPanel.restart();
				board.restart();
				board = hardBoard;
				board.restart();
				restartHard.setBorderPainted(false);
				restartHard.setBorder(null);
			}

		}

		public void mouseEntered(MouseEvent e) {
			if (e.getSource() == restartHard) {
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
