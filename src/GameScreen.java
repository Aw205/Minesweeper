import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class GameScreen {
	
	private static JFrame frame = new JFrame("Minesweeper");
	public static JPanel panel;
	private Board board = new Board();

	
	
	public static void main(String[] args) throws IOException {
		
		new GameScreen().createFrame();	
	}
	
	private void createFrame() throws IOException {
		
		frame.setVisible(true);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		createPanel();
		createMenu();
		frame.add(panel);
		frame.pack();
		
	}
	
	private void createPanel() {		
		panel = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				board.draw((Graphics2D) g);
			}
		};
		
		panel.setPreferredSize(new Dimension(1300,750));
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setBorder(BorderFactory.createLineBorder(Color.black));
		
		MouseController m = new MouseController(panel,board);
		
	}
	
	private void createMenu() {
		MenuController mc = new MenuController(frame);
		frame.add(mc);
	}
}
