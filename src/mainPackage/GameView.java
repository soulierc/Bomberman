package mainPackage;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;


public class GameView extends View{

	private UserPolling poll;
	private Map<State, Image> sprites;
	
	public GameView(Display motherFrame, UserPolling p) {
		super(motherFrame);
		poll  = p;
		init();
		this.addKeyListener(new KeyController(getMotherFrame().getUserPolling()));
		this.setFocusable(true);
		/*this.requestFocusInWindow();*/
		// TODO Auto-generated constructor stub
	}

	private void init() {
		this.sprites = new Hashtable<State, Image>();
		
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
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.setBackground(Color.white);
		System.err.println("GameView.init()");
	}
	
	public void paintComponent(Graphics g)
	{
		
		afficherCases(poll.getCase(), g);
		afficherPlayers(this.poll.getPlayers(), this.poll.getCase(), g);
		
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
	
	@SuppressWarnings("deprecation")
	private void afficherCases(List<Case> l, Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, this.size().width, this.size().height);
		for(int i=0; i<l.size(); i++)
		{
			g.setColor(Color.white);
			Image a = sprites.get(l.get(i).getEtat());
			
			
			fillRect(a, l.get(i).getBox(), g);
			if(l.get(i).getEtat() != State.SUPPRIMEE)
			{
				g.setColor(Color.black);
				drawRect(l.get(i).getBox(),g);
			}
		}
	}
	
	private void afficherPlayers(List<Player> l, List<Case> l2, Graphics g)
	{
		for(int i=0; i<l.size(); i++)
		{
			if(l.get(i).isDead())
				g.setColor(Color.GRAY);
			else
				if(l.get(i).getName() != 99999)
					{
						g.setColor(new Color(l.get(i).getName()*125,255,255));
					}
				else
					g.setColor(Color.BLUE);
			Rectangle t = null;
			for(int j=0; j<l2.size(); j++) //La boucle sert a chercher sur quelle case se trouve le player i, on en a besoin pour centrer le player dans la case
			{
				if(l.get(i).getHitBox().intersects(l2.get(j).getBox()))
					t=l2.get(j).getBox();
					
			}
			if(t!=null)
			fillRect(null, Tools.centrerRectangleInRectangle(l.get(i).getHitBox(), t),g);
		}
	}
}
