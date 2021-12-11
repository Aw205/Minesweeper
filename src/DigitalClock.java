
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class DigitalClock extends JPanel {
	
	private Timer timer = new Timer(1000, null);
	ImageIcon[] digitImages = new ImageIcon[10];
	private int seconds=1;
	
	JLabel ones = new JLabel();
	JLabel tens = new JLabel();
	JLabel hundreds = new JLabel();
	
	
	/**
	 * 0- clock 
	 * 1-flagCounter
	 */
	public DigitalClock(int type) {
		
		loadImages();
		setLayout();		
		if(type==0) {
			setUpTimer();	
		}
		else {
			updateFlagCounter();
		}
	}
	
	public void updateFlagCounter() {
		
		ones.setIcon(digitImages[Board.flagCounter % 10]);
		tens.setIcon(digitImages[(Board.flagCounter / 10) % 10]);
		hundreds.setIcon(digitImages[(Board.flagCounter / 100) % 10]);
	}
	
	private void setUpTimer() {
		timer.setInitialDelay(0);
		timer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (MouseController.firstClick) {
					ones.setIcon(digitImages[seconds % 10]);
					tens.setIcon(digitImages[(seconds / 10) % 10]);
					hundreds.setIcon(digitImages[(seconds / 100) % 10]);
					seconds++;
				} else {
					seconds = 0;
					ones.setIcon(digitImages[0]);
					tens.setIcon(digitImages[0]);
					hundreds.setIcon(digitImages[0]);
				}
			}
		});
		timer.start();
	}
	
	private void setLayout() {
		
		FlowLayout layout = new FlowLayout();
		layout.setHgap(0);
		
		this.setLayout(layout);
		this.add(hundreds);
		this.add(tens);
		this.add(ones);
	}
	
	private void loadImages() {
		
		for(int i=0;i<digitImages.length;i++) {
			try {
				digitImages[i]= new ImageIcon(ImageIO.read(new File("digits/digit"+i+".png")));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
