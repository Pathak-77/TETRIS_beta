package TETRIS;

import javax.swing.JFrame;

public class WindowGame {

	private static final int Width = 445;
	private static final int Height = 637;
	
	private JFrame window;
	private Board board;
	public WindowGame() {
		window = new JFrame("Tetris 1.0.1");
		
		window.setSize(Width,Height);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setLocationRelativeTo(null);	
		
		board = new Board();
		window.add(board);
		window.addKeyListener(board);
		window.setVisible(true);
	}
	
	
	public static void main(String[] args) {
		new WindowGame();
	}
}
