package TETRIS;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements KeyListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int Game_Play = 0;
	public static int Game_Pause = 1;
	public static int Game_Over = 2;
	
	
	
	private static int state = Game_Play;
	
	private static final int FPS = 60;
	private static final int DELAY = FPS/1000;
	
	public static final int BOARD_WIDTH =10;
	public static final int BOARD_HEIGHT = 20;
	public static final int BLOCK_SIZE = 30;
	private static Random random;
	
	private Timer loop;
	private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];
	
	
	
	private Shapes[] shape= new Shapes[7];
	private Shapes currentshape;
	private Color[]	colors= {Color.GRAY,Color.BLUE,Color.GREEN,
							 Color.pink,Color.YELLOW,Color.ORANGE
							 ,Color.RED};

			
	
	public Board() {
		
        random = new Random();
		//I shaper
		shape[0]= new Shapes(new int[][] {
			{1,1,1,1}
		}, this, colors[0]);
		//L shaper
		shape[1]= new Shapes(new int[][] {
			{1,1,1},
			{1,0,0}
		}, this, colors[1]);
		//J shaper
		shape[2]= new Shapes(new int[][] {
			{1,1,1},
			{0,0,1}
		}, this, colors[2]);
		//S shaper
		shape[3]= new Shapes(new int[][] {
			{0,1,1},
			{1,1,0}
		}, this, colors[3]);
		//z shaper
		shape[4]= new Shapes(new int[][] {
			{1,1,0},
			{0,1,1}

		}, this, colors[4]);
		//O shaper
		shape[5]= new Shapes(new int[][] {
			{1,1},
			{1,1}
		}, this, colors[5]);
		//T shaper 
		
		shape[6]= new Shapes(new int[][] {
			{1,1,1},
			{0,1,0}
		}, this, colors[6]);
		
		currentshape = shape[random.nextInt(shape.length)];
		
		loop= new Timer(DELAY, new ActionListener() {
	
	
		@Override
	   public void actionPerformed(ActionEvent e) {
			update();
			repaint();
			}
			
		});
		loop.start();
		}
	
	public void setShape() {
		this.currentshape = shape[random.nextInt(shape.length)];
		currentshape.reset();	
		gameOver();
		}
	
	private void update() {
		if(state == Game_Play) {
		currentshape.update();
		}
	}
	
	public void gameOver() {
		int[][]	 coords = currentshape.getCoords();
		for(int row= 0; row < coords.length; row++) {
			for(int col= 0; col < coords[0].length; col++) {
				if(coords[row][col] !=0) {
					if(board[row + currentshape.getY()][col + currentshape.getX()]
							!= null) {
						
						state = Game_Over;
					}
				}
			}
		}
	}
	

	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		currentshape.render(g);
		
	
		
		 for(int row = 0; row<board.length;row++) {
			 for(int col=0;col<board[row].length;col++) {
				 
				 if(board[row][col]!=null) {
					 g.setColor(board[row][col]);
					 g.fillRect(col*BLOCK_SIZE, row*BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
				}
			}
		 }
		
		//THE BOARD 
		
		g.setColor(Color.white);
		for(int row=0;row<BOARD_HEIGHT;row++) {
			g.drawLine(0, BLOCK_SIZE*row, BOARD_WIDTH*BLOCK_SIZE, BLOCK_SIZE*row);
		}
		
		for(int col=0;col<BOARD_WIDTH+1;col++) {
			
			g.drawLine(BLOCK_SIZE*col,0, col*BLOCK_SIZE, BLOCK_SIZE*BOARD_HEIGHT);
		}
		
		if(state == Game_Over) {
			 String gameOverString = ("GAME ");
	            g.setColor(Color.WHITE);
	            g.setFont(new Font("Georgia", Font.BOLD, 30));
	            g.drawString(gameOverString+"OVER", 200, BOARD_HEIGHT*2 );
		}
		 
		else if(state == Game_Pause) {
			String gamePausedString = ("GAME PAUSED");
            g.setColor(Color.WHITE);
            g.setFont(new Font("Georgia", Font.BOLD, 20));
            g.drawString(gamePausedString, 230, BOARD_HEIGHT*2);
			}
		
		
	}
	
	public Color[][] getBoard(){
		return board;
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_S) {
	        this.currentshape.speedUp();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_D) {
	        currentshape.goRight();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_A) {
	        currentshape.goLeft();
	    }
	    if (e.getKeyCode() == KeyEvent.VK_SPACE) {
	        currentshape.rotateShape();
	    }
	    
		if(state == Game_Over) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				 for(int row = 0; row<board.length;row++) {
					 for(int col=0;col<board[row].length;col++) {
						 	board[row][col]= null;
						 	
						 
					}
				 }
				 setShape();
				 state = Game_Play;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(state == Game_Play) {
				 state = Game_Pause;
			}
			else if(state == Game_Pause) {
				state = Game_Play;
			}
		}
	}

 @Override
	public void keyReleased(KeyEvent e) {
	    if (e.getKeyCode() == KeyEvent.VK_S) {
	        this.currentshape.speedDown();
	    }
	}
 
 
}
