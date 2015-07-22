package setup;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mainChrono implements ActionListener {

	mainCanvas gc;
	// constructor that receives the GameCanvas that we will repaint every 60 milliseconds
	mainChrono(mainCanvas gc) {
		this.gc = gc;
	}
	// calls the method to repaint the anim
	public void actionPerformed(ActionEvent arg0) {
		gc.myRepaint();
	}

}

