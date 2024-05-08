package TETRIS;

import static 	TETRIS.Board.BLOCK_SIZE;
import static 	TETRIS.Board.BOARD_HEIGHT;

import java.awt.Color;
import java.awt.Graphics;


public class Shapes {
	
	static int x=4; 
	static int y=0;
	static int Delx=0;
	private final int normal = 600;
	private int fast =50;
	private int delayTimeMovement =normal;
	private long beginTime;
	
	private boolean collision = false;
	private Board board;
	private int[][] coords;
	private Color color;
	
	
      Shapes(int[][] coords,Board board, Color colors) {
		this.coords=coords;
		this.board= board;
		this.color= colors;
	}
      
    public void reset() {
    	this.x =4;
    	this.y=0;
    	collision=false;
    }
	
	public void setX(int x) {
		this.x =x;
	}
	
	public void setY(int y) {
		this.y=y;
	}

	

	public void update() {
		
		 if(collision) {
			 //FILL THE BOARD COLOR ON COLLISION	
			 
			 for(int row = 0; row<coords.length;row++) {
				 for(int col=0;col<coords[0].length;col++) {
					 if(coords[row][col]!=0) {
					 board.getBoard()[y+row][x+col]=color;
					}
				}
			 }
			 checkLine();
			
			 //set current shape
			 	board.setShape();
			 	
				return;
			}
			
			//LEFT OR RIGHT ON PRESS
			boolean moveX= true;
			if (!(x+Delx+coords[0].length>10) && !(x+Delx<0)) {
				for(int row = 0; row<coords.length;row++) {
					for(int col=0;col<coords[row].length;col++) {
					  if(coords[row][col]!=0) {
						if(board.getBoard()[y+row][x+col+Delx]!=null) {
							moveX = false;
						}
					  }
					}
				}
				if(moveX) {
					x+=Delx;
				}
				
				
			}
				Delx=0;
				
			//Timer and Brick drop rate modifier
			if(System.currentTimeMillis()-beginTime>delayTimeMovement) {
				if(!(y+1+coords.length>BOARD_HEIGHT)) {
					for(int row = 0; row<coords.length;row++) {
						for(int col=0;col<coords[0].length;col++) {
							if(coords[row][col]!=0) {
								if(board.getBoard()[y+row+1][x+col+Delx]!=null) {
									collision =true;
								}
							}
						}
					}
					
					if(!collision) {
						y++;
					}
					
					
				}
				else {
					collision= true;
				}
			
			beginTime  = System.currentTimeMillis();
			}
	 }
	
	private void checkLine() {
		int bottomLine = board.getBoard().length-1;
		for (int topLine = board.getBoard().length-1; topLine > 0; topLine--) {
			int count = 0;
			for(int col =0;col<board.getBoard()[0].length;col++) {
				if( board.getBoard()[topLine][col]!=null) {
					count ++;
					
				}
				 
				board.getBoard()[bottomLine][col] = board.getBoard()[topLine][col];
			}
			
			if(count<board.getBoard()[0].length) {
					bottomLine--;
			}
			
		}
	}
	// This will change the shape on click
	public void rotateShape() {
	int[][] rotatedShape = transposeMatrix(coords);
		reverseRows(rotatedShape);
		
		// check for side and bottom
		if(x + rotatedShape[0].length > Board.BOARD_WIDTH || (y+ rotatedShape.length > 
		Board.BOARD_HEIGHT)) {
		return ;
				}
		
		// check collision
		for(int row = 0 ; row< rotatedShape.length; row++) {
			for(int col = 0; col < rotatedShape[row].length; col++) {
				if(board.getBoard()[y + row][x + col] != null) {
					return ;
				}
			}
		}
		
		coords = rotatedShape;

	}
	
	private int[][] transposeMatrix(int[][] matrix) {
		int[][] temp = new int[matrix[0].length][matrix.length];
		for(int row=0; row<matrix.length; row++) {
			for(int col=0 ;col<matrix[0].length; col++) {
				temp[col][row] = matrix[row][col];
			}
		}
		return temp;
	}
	
	private void reverseRows(int matrix[][]) {
		int middle = matrix.length /2; 
		for(int row= 0 ; row< middle ; row++) {
			int[] temp = matrix[row];
			matrix[row] = matrix[matrix.length - row -1];
			matrix[matrix.length - row -1] = temp;
		}
	}
	
	
	
	 public void render(Graphics g) {
			//THE coords
			for (int row=0;row<coords.length;row++) {
				for (int col=0;col<coords[0].length;col++) {
					if(coords[row][col]!=0) {
					g.setColor(Color.RED);
					g.fillRect(col*BLOCK_SIZE+ x*BLOCK_SIZE, row*BLOCK_SIZE+ y *BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE);
					}
				}
			}
	 }
	 
	 public void speedUp() {
		 delayTimeMovement = fast;
	 }
	
	 public void speedDown() {
		 delayTimeMovement = normal;
	 }
	 
	 public void goLeft() {
		 Delx = -1;
						
	 }
	 
	 public void goRight() {
		 Delx = +1;
		
	 }
	 
	 public int getX() {
		 return x;
	 }
	 
	 public int getY() {
		 return y;
	 }
	 
	 public int[][] getCoords(){
		 return coords;
	 }
	 
	 
	 
}
