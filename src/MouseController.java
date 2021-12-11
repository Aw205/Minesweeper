import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JPanel;

public class MouseController{
	
	JPanel panel;
	Board board;
	private int row;
	private int col;
	public static boolean firstClick=false;
	private Tile previous;
	
	public MouseController(JPanel panel,Board board) {
		this.board=board;
		this.panel=panel;
		this.panel.addMouseListener(new MouseAdapter() {
			
			@Override 
			public void mouseReleased(MouseEvent e) {
				
				if (e.getModifiers()==MouseEvent.BUTTON1_MASK && inBounds(row, col)) {
					if (!firstClick) {
						firstClick = true;
						board.setTileProperties(row, col);
					}					
					Tile t = Board.boardArray[row][col];
					t.revealed = true;
					t.update(panel, row, col);

				}
				panel.repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				updateCurrentRowAndColumn(e);
				if(e.getModifiers()==MouseEvent.BUTTON3_MASK) {
					updateCurrentRowAndColumn(e);
					
					if(inBounds(row,col)) {
						Tile t= Board.boardArray[row][col];
						t.updateFlag(row,col,panel);
						MenuController.flagCounter.updateFlagCounter();
					}
				}
				else if(e.getModifiers()==MouseEvent.BUTTON1_MASK) {
					if(inBounds(row,col)) {
						Tile t= Board.boardArray[row][col];
						if(!t.revealed) {
							t.isPressed=true;
						}
					}
					panel.repaint();
				}
			}
		});
		
		this.panel.addMouseMotionListener(new MouseMotionAdapter() {
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
				if(inBounds(row,col)) {
					previous=Board.boardArray[row][col];
				}
				else if(previous!=null) {
					previous.isPressed=false;
				}
				
				updateCurrentRowAndColumn(e);
				
				if(e.getModifiers()==MouseEvent.BUTTON1_MASK) {				
					if(inBounds(row,col)) {
						Tile t= Board.boardArray[row][col];
						if(previous!=t) {
							previous.isPressed=false;
							previous=t;
						}
						if(!t.revealed) {
							t.isPressed=true;
						}
					}				
				}
				panel.repaint();
			}
		});
	}
	

	private void updateCurrentRowAndColumn(MouseEvent e) {

		this.row=(e.getY()-50)/Tile.covered.getHeight(null);
		this.col=(e.getX()-50)/Tile.covered.getWidth(null);
	}

	public static boolean inBounds(int row, int col) {
		return row<Board.height && row>-1 && col>-1 && col<Board.width;
	}
}
