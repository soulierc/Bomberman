package mainPackage;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.plaf.ButtonUI;
import javax.swing.plaf.basic.BasicOptionPaneUI.ButtonActionListener;

public class CanvasPlateau extends Canvas implements MouseListener{

	private Concepteur concepteur;
	private Map<State, Image> sprites;
	
	public CanvasPlateau(Concepteur c, Map<State, Image> sprites2) {
		super();
		this.concepteur = c;
		this.sprites =sprites2;
		this.setSize(concepteur.getSizeGame()*Case.DEFAULT_SIZE, concepteur.getSizeGame()*Case.DEFAULT_SIZE);
		this.addMouseListener(this);
	}

	@Override
	public void paint(Graphics g) {
		int x = ((ConcepteurView)this.getParent()).getMotherFrame().getSize().width/2 - this.getSize().width/2;
		int y = ((ConcepteurView)this.getParent()).getMotherFrame().getSize().height/2 - this.getSize().height/2;
		this.setBounds(x, y, concepteur.getSizeGame()*Case.DEFAULT_SIZE+10, concepteur.getSizeGame()*Case.DEFAULT_SIZE+10);
		afficherCases(concepteur.getListCase(), g);
	}
	
	private void fillRect(Image a, Rectangle r, Graphics g)
	{
		if(a == null)
			g.fillRect(r.x, r.y, r.width, r.height);
		else
			g.drawImage(a, r.x, r.y, r.width, r.height, null);
	}
	private void drawRect(Rectangle r, Graphics g)
	{
		g.drawRect(r.x, r.y, r.width, r.height);
	}

	private void afficherCases(List<Case> l, Graphics g)
	{
		g.setColor(Color.gray);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
		Image a = null;
		for(int i=0; i<l.size(); i++)
		{
			g.setColor(Color.white);
			a = sprites.get(l.get(i).getEtat());
			
			fillRect(a, l.get(i).getBox(), g);
			g.setColor(Color.black);
			drawRect(l.get(i).getBox(),g);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		State s = this.concepteur.getSelectedState();
		Case modif = Tools.getCaseAt(this.concepteur.getListCase(), e.getX(), e.getY());
		if(modif != null)
		{
			if(e.getButton() == 1)
				modif.setEtat(s);
			else if (e.getButton() == 3)
				modif.setEtat(State.VIDE);
			((ConcepteurView)this.getParent()).maj(false);
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
}
