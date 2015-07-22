package setup;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class mainFrame extends JFrame implements myConstants{
	
	private static final long serialVersionUID = 1L;
	
	mainFrame() {
		super("2048");
		try { UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");}
		catch (UnsupportedLookAndFeelException ex) {}
		catch (IllegalAccessException ex) {} 
		catch (InstantiationException ex) {} 
		catch (ClassNotFoundException ex) {}
        UIManager.put("swing.boldMetal", Boolean.FALSE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		mainCanvas canvas = new mainCanvas(this);
		add(canvas, BorderLayout.CENTER);
		
		setBackground(Color.GRAY);
		// set it's size and make it visible
		setSize(25+10*3+ESCALA*4, 60+10*3+ESCALA*4);
		setVisible(true);		
		// now that is visible we can tell it that we will use 2 buffers to do the repaint
		// before being able to do that, the Canvas has to be visible
		canvas.createBufferStrategy(2);
	}
	
	// just to start the application
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new mainFrame();
			}
		});
	}
}

