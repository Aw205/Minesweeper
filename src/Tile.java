import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Tile {
	
	int number=0;
	boolean hasMine=false;
	boolean isClicked=false;
	private boolean mineClicked=false;
	public boolean revealed=false;
	private boolean hasFlag=false;
	public boolean isPressed=false;
	
	static Image[] numberImage= new Image[8];
	static Image flag;
	static Image flagMineWrong;
	static Image mineWrong;
	static Image covered;
	static Image emptyBlock;
	static Image mine;
	
	static {		
		for(int i=0;i<8;i++) {	
			String name = "number-"+(i+1)+".png";		
				try {
					numberImage[i]= ImageIO.read(new File(name)).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} 
		try {
			flag= ImageIO.read(new File("flag-mine.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			flagMineWrong=ImageIO.read(new File("flag-mine-wrong.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			mineWrong=ImageIO.read(new File("mine-wrong.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			covered=ImageIO.read(new File("covered.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			emptyBlock= ImageIO.read(new File("emptyBlock.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
			mine= ImageIO.read(new File("mine.png")).getScaledInstance(40, 40, Image.SCALE_SMOOTH);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}  
	
	public Tile() {
		
		
	}

	public void update(JPanel panel, int row, int col) {

		if (hasMine) {
			mineClicked = true;
			Board.revealMines(panel);
		} else if (number == 0) {
			Board.floodFill(row, col);
			Board.numRevealedTiles++;
		} else {
			Board.numRevealedTiles++;
		}
	}

	public void draw(Graphics2D g, int x, int y) {

		if (!revealed) {
			if (hasFlag) {
				g.drawImage(flag, x, y, null);
			} 
			else {
				g.drawImage(covered, x, y, null);
			}
			if (isPressed) {
				g.drawImage(emptyBlock, x, y, null);
			}
		} 
		else if (hasFlag) {
			if (!hasMine) {
				g.drawImage(flagMineWrong, x, y, null);
			}
			else {
				g.drawImage(flag, x, y,null);
			}
		} 
		else if (number > 0) {
			g.drawImage(numberImage[number-1], x, y, null);
		} 
		else if (hasMine) {
			if (mineClicked) {
				g.drawImage(mineWrong, x, y, null);
			} 
			else {
				g.drawImage(mine, x, y, null);
			}
		} 
		else {
			g.drawImage(emptyBlock, x, y, null);
		}

	}

	public void updateFlag(int row, int col, JPanel panel) {
		
		this.hasFlag=!hasFlag;
		Board.flagCounter+= (hasFlag) ? 1 : -1;
		panel.repaint();
	}

}
