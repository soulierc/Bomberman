package mainPackage;

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.WindowStateListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

public class ConcepteurView extends View implements MouseListener{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5884570181773088200L;
	
	private Concepteur concepteur;
	private Map<State, Image> sprites;
	private Canvas canvasPlateau;
	private Canvas canvasCases;
	private JButton boutonPlus;
	private JButton boutonMoins;
	public ConcepteurView(Display motherFrame, Concepteur p) {
		super(motherFrame);
		concepteur  = p;
		init();
		this.addMouseListener(this);
		this.setFocusable(true);
		motherFrame.setSize(600, 500);
		this.updateUI();
	}


	private void init() 
	{
		this.sprites = new Hashtable<State, Image>();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		try {
			Image a = ImageIO.read(new File("ressources/sprites/caisse.png"));
			this.sprites.put(State.CAISSE,a);
			
			a = ImageIO.read(new File("ressources/sprites/vide.png"));
			this.sprites.put(State.VIDE,a);
			
			a = ImageIO.read(new File("ressources/sprites/infranchissable.png"));
			this.sprites.put(State.INCASSABLE,a);
			
			a = ImageIO.read(new File("ressources/sprites/beingDestroyed.png"));
			this.sprites.put(State.BEINGDESTROYED,a);
			
			a = ImageIO.read(new File("ressources/sprites/supprimee.png"));
			this.sprites.put(State.SUPPRIMEE,a);
			
			a = ImageIO.read(new File("ressources/sprites/bombe.png"));
			this.sprites.put(State.BOMBE,a);
			
			a = ImageIO.read(new File("ressources/sprites/depart.png"));
			this.sprites.put(State.DEPART,a);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.canvasCases = new CanvasCases(this.concepteur, sprites);
		this.canvasPlateau = new CanvasPlateau(concepteur,sprites);
		initButtons();
		this.add(this.canvasPlateau,CENTER_ALIGNMENT);
		this.add(this.canvasCases,CENTER_ALIGNMENT);
		int w = boutonPlus.getSize().width*2 + this.canvasCases.getWidth() + this.canvasPlateau.getWidth();
		int h = boutonPlus.getSize().height*2 + this.canvasCases.getHeight() + this.canvasPlateau.getHeight();
		this.getMotherFrame().setSize(w, h);
		
	}
	
	private void initButtons()
	{
		Box box = new Box(BoxLayout.X_AXIS);
		boutonMoins = new JButton("+");
		boutonMoins.addMouseListener(this);
		box.add(boutonMoins,Component.RIGHT_ALIGNMENT);
		boutonPlus = new JButton("-");
		boutonPlus.addMouseListener(this);
		box.add(boutonPlus,CENTER_ALIGNMENT);
		
		JButton save = new JButton("Sauvegarder");
		save.addMouseListener(this);
		box.add(save);
		boutonPlus.setPreferredSize(new Dimension(boutonMoins.getSize()));
		this.add(box);
		
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		
		if(arg0.getSource() instanceof JButton)
		{
			JButton b = (JButton) arg0.getSource();
			if(b.getText().equals("+"))
			{
				this.concepteur.setSizeGame(this.concepteur.getSizeGame()+1);
			}
			else if(b.getText().equals("-"))
			{
				this.concepteur.setSizeGame(this.concepteur.getSizeGame()-1);
			}
			else if(b.getText().equals("Sauvegarder"))
			{
				try {
					this.concepteur.saveInFile();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			this.concepteur.creerPlateau(this.concepteur.getSizeGame());
			maj(true);
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


	public void maj(boolean resize) {
		/*for(int i = 0; i<concepteur.getListCase().size(); i++)
			System.out.println(concepteur.getListCase().get(i).getBox());*/
		
		this.canvasCases.repaint();
		this.canvasPlateau.repaint();
		if(resize)
		{
			int w = this.canvasCases.getWidth() + this.canvasPlateau.getWidth() + 200;
			int h = this.canvasCases.getHeight() + this.canvasPlateau.getHeight() + 200;
			this.getMotherFrame().setSize(w, h);
		}
	}
}
