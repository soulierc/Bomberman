package mainPackage;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;

public class MenuView extends View implements MouseListener{

	
	public MenuView(Display motherFrame) {
		super(motherFrame);
		init();
		this.updateUI();
	}
	
	public void init()
	{
		this.setBackground(Color.blue);
		
		
		JButton b1 = new JButton("Nouveau jeu");
		b1.addMouseListener(this);
		this.add(b1);
		
		JButton b2 = new JButton("Quitter");
		b2.addMouseListener(this);
		
		JButton b3 = new JButton("Concepteur");
		b3.addMouseListener(this);
		this.add(b3);
		
		this.add(b2);
		System.err.println("MenuView.init()");
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		if(arg0.getSource() instanceof JButton)
		{
			JButton clicked = (JButton) arg0.getSource();
			if(clicked.getText().equals("Nouveau jeu"))
			{
				this.getMotherFrame().initGame();
				this.updateUI();
			}
			else if (clicked.getText().equals("Concepteur"))
			{
				this.getMotherFrame().initConcepteur();
				this.updateUI();
			}
			else
				System.exit(0);
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
