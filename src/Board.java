
import java.awt.Graphics2D;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.JPanel;

public class Board {

	private int currentMines=0;
	public static int mineLimit=10;
	public static int flagCounter=0;
	
	public static int height=9;
	public static int width=9;
	int x=50;
	int y=50;
	
	static Tile[][] boardArray;
	public static int numRevealedTiles=0;
	
	
	public Board() {	
		createInitialTiles();
	}
	
	public void draw(Graphics2D g) {
		
		int x= this.x;
		int y=this.y;
		for(Tile[] row: boardArray) {
			for(Tile col: row) {
				col.draw(g,x,y);
				x+=Tile.covered.getWidth(null);
			}
			x=this.x;
			y+=Tile.covered.getHeight(null);
		}
		y=this.y;
	}
	
	
	public static void floodFill(int row, int col) {
		
		for(int i=-1;i<2;i++) {
			for(int p=-1;p<2;p++) {
				if(MouseController.inBounds(row+i,col+p)) {
					Tile b= boardArray[row+i][col+p];
					if(!b.revealed) {
						b.revealed=true;
						numRevealedTiles++;
						if(b.number==0) {
							floodFill(row+i,col+p);
						}
					}
				}
			}
		}
	}
	
	public void setTileProperties(int userX,int userY) {
		
		setMines(userX,userY);	
		setNumberTiles();
	}
	
	private void setMines(int userX,int userY) {
		
		List<Integer> excludedTiles = Arrays.asList(userX,userX+1,userX-1,userY,userY+1,userY-1);
		Random rand = new Random();
		while(currentMines<mineLimit) {
			int randomX= rand.nextInt(height);
			int randomY= rand.nextInt(width);
			if(!excludedTiles.contains(randomX) || !excludedTiles.contains(randomY)) {
				Tile b = boardArray[randomX][randomY];
				b.hasMine=true;
				currentMines++;
				
			}
		}
		currentMines=0;
	}
	
	private void setNumberTiles() {
			
		for(int row=0;row<height;row++) {
			for(int col=0;col<width;col++) {			
				if(boardArray[row][col].hasMine) {
					continue;
				}
				int counter =0;
				for(int i=-1;i<2;i++) {
					for(int p=-1;p<2;p++) {
						if(row+i<height && row+i>-1 && col+p>-1 && col+p<width) {
							if(boardArray[row+i][col+p].hasMine) {
								counter++;
							}
						}
					}
				}
				boardArray[row][col].number=counter;
			}
		}

	}
	
	public static void createInitialTiles() {
		
		boardArray = new Tile[height][width];	
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				boardArray[i][j]= new Tile();
			}
		}
	}
	

	public static void revealMines(JPanel panel) {
		
		for(int i=0;i<height;i++) {
			for(int j=0;j<width;j++) {
				if(boardArray[i][j].hasMine) {
					boardArray[i][j].revealed=true;
				}
			}
		}
		panel.repaint();
	}
}
