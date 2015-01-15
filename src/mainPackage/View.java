package mainPackage;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class View extends JPanel {

	
	private final Display motherFrame;
	
	
	public View(Display motherFrame)
	{
		this.motherFrame = motherFrame;
		/*this.addKeyListener(new KeyController(motherFrame.getUserPolling()));
		this.setFocusable(true);
		this.requestFocusInWindow();*/
	}
	public Display getMotherFrame() {
		return motherFrame;
	}
	
}
