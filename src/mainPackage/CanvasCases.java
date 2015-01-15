package mainPackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

public class CanvasCases extends Canvas implements MouseListener{

	private Map<State, Image> sprites;
	private Concepteur concepteur;
	
	public CanvasCases(Concepteur c,Map<State, Image>  sprites2) {
		super();
		this.setSize(State.values().length*Case.DEFAULT_SIZE,Case.DEFAULT_SIZE);
		this.sprites = sprites2;
		this.concepteur = c;
		this.addMouseListener(this);
		Map<State, Image> m = new Hashtable<State, Image>();
		try {
			m.put(State.BEINGDESTROYED, ImageIO.read(new File("ressources/sprites/caisse.png")));
			System.out.print(m.get(State.BEINGDESTROYED));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	@Override
	public void paint(Graphics g) {

		int x = ((ConcepteurView)this.getParent()).getMotherFrame().getSize().width/2 - this.getSize().width/2;
		int y = (int) (((ConcepteurView)this.getParent()).getMotherFrame().getSize().height-75);
		this.setBounds(x, y, State.values().length*Case.DEFAULT_SIZE,Case.DEFAULT_SIZE);
		
		g.setColor(Color.white);
		g.fillRect(0, 0, this.size().width, this.size().height);
		Image a = null;
		for(int i=0; i<State.values().length; i++)
		{
			a = sprites.get(State.values()[i]);
			
			g.drawImage(a, i*Case.DEFAULT_SIZE, 0, Case.DEFAULT_SIZE, Case.DEFAULT_SIZE, null);
		}
		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		State[] tab = State.values();
		this.concepteur.setSelectedState(tab[arg0.getX()/Case.DEFAULT_SIZE]);
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		State[] tab = State.values();
		String str = tab[arg0.getX()/Case.DEFAULT_SIZE].toString();
		
		this.getGraphics().drawString(str, arg0.getX()+10, arg0.getY()+5);
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
