
import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class MenuController extends JPanel {
	
	
	JMenuItem easy = new JMenuItem("Easy");
	JMenuItem medium = new JMenuItem("Medium");
	JMenuItem hard = new JMenuItem("Hard");
	
	JMenuItem[] buttons = {easy,medium,hard};
	JButton reset = new JButton();
	
	public static DigitalClock flagCounter;
	
	
	public MenuController(JFrame frame) {
		
		this.setLayout(new BorderLayout());
		createMenuBar();
		addDifficultyLevelListener();
		createFlagCounter();
		createResetButton(); 
		createDigitalClock();
	}
	
	private void createFlagCounter() {
		
		flagCounter=new DigitalClock(1);
		
		this.add(flagCounter,BorderLayout.LINE_START);
	}
	
	private void createMenuBar() {
		
		JMenuBar mb= new JMenuBar();
		JMenu difficultyMenu = new JMenu("Difficulty");
	
		difficultyMenu.add(easy);
		difficultyMenu.add(medium);
		difficultyMenu.add(hard);
	
		mb.add(difficultyMenu);
		this.add(mb,BorderLayout.PAGE_START);
	}
	
	private void createDigitalClock() {
		
		this.add(new DigitalClock(0),BorderLayout.LINE_END);
	}
	
	private void createResetButton() {
		
		try {
			reset.setIcon(new ImageIcon(ImageIO.read(new File("happyFace.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		reset.setContentAreaFilled(false);
		reset.setBorderPainted(false);
		
		reset.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				try {
					reset.setIcon(new ImageIcon(ImageIO.read(new File("smileFacePressed.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				
				try {
					reset.setIcon(new ImageIcon(ImageIO.read(new File("happyFace.png")).getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		
		
		
		
		reset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Board.createInitialTiles();
				MouseController.firstClick=false;
				Board.flagCounter=0;
				flagCounter.updateFlagCounter();
				GameScreen.panel.repaint();
			}
		});
		
		this.add(reset,BorderLayout.CENTER);
	}

	private void addDifficultyLevelListener() {
		for(int i=0;i<buttons.length;i++) {
			AbstractButton b = buttons[i];
			buttons[i].addActionListener(new ActionListener() {		
				@Override
				public void actionPerformed(ActionEvent e) {

					String s = b.getText();
					switch (s) {
	
						case "Easy":
							Board.height = 9;
							Board.width = 9;
							Board.mineLimit = 10;
							break;
							
						case "Medium":
							Board.height = 16;
							Board.width = 16;
							Board.mineLimit = 40;
							break;
							
						case "Hard":
							Board.height = 16;
							Board.width = 30;
							Board.mineLimit = 99;
					}
					reset.doClick();
				}
			});
		}
		
	}
}
