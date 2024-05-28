package Gui;

import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import java.awt.event.WindowListener;
import java.awt.event.WindowAdapter;

public class MainFrame extends JFrame {
	public MainFrame() {
		setSize(1200, 800);
		setLocation(300, 70);
		setTitle("Pocetni prozor");
		setVisible(true);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		MainFrame mf = new MainFrame();
	}

}
