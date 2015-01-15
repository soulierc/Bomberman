package mainPackage;

import java.util.List;
import java.util.Random;

public class PlayerIA extends Player {

	private int timeBeforeAction;
	public PlayerIA(int x, int y, int name) {
		super(x, y, name);
		this.timeBeforeAction = 300;
		// TODO Auto-generated constructor stub
	}

	public void jouer(Game g)
	{
		this.timeBeforeAction-=5;
		if(timeBeforeAction <= 0 && !this.isDead())
		{
			//Si l'ordi est sur une case qui va etre detruite ... 
			if(Tools.isPlayerOnCase(Tools.getCaseInExplosion(g.getCase(), this.chercheBombeLaPlusProche(g.getBombes())), this))
			{
				deplacerAleatoire(g); // ... ON LE SORT DE LA !
				System.out.println("Je suis n°" + this.getName());
			}
			else
			{
				deplacerVers(g,chercheEnnemiLePlusProche(g.getPlayers()));
				if(this.getBombes() >= this.getMaxBombes() /2 && detruireautour(g.getCase()))
					g.poserBombe(this);
				System.out.println("\tJe suis n°" + this.getName());
			}
			this.timeBeforeAction = 300;
		}
	}
	
	private void deplacerAleatoire(Game g) {
		Random r = new Random(System.nanoTime());
		while(g.deplacer(Direction.values()[r.nextInt(4)], this)==0);
		
	}

	private boolean detruireautour(List<Case> l)
	{
		if(Tools.getCaseAt(l, this.getHitBox().x+Case.DEFAULT_SIZE, this.getHitBox().y).getEtat() == State.CAISSE)
		{
			return true;
		}
		else
			return false;
	}
	
	private void deplacerVers(Game g, Player p)
	{
		Direction d1 = null;
		Direction d2 = null;
		if(p.getHitBox().x >= this.getHitBox().x && p.getHitBox().y >= this.getHitBox().y)
		{
			d1 = Direction.DROITE;
			d2 = Direction.BAS;
		}
		else if(p.getHitBox().x < this.getHitBox().x && p.getHitBox().y >= this.getHitBox().y)
		{
			d1 = Direction.GAUCHE;
			d2 = Direction.BAS;
		}
		else if(p.getHitBox().x >= this.getHitBox().x && p.getHitBox().y < this.getHitBox().y)
		{
			d1 = Direction.DROITE;
			d2 = Direction.HAUT;
		}
		else if(p.getHitBox().x < this.getHitBox().x && p.getHitBox().y < this.getHitBox().y)
		{
			d1 = Direction.GAUCHE;
			d2 = Direction.HAUT;
		}
		if(d1!=null || d2!=null)
		{
			g.deplacer(d1, this);
			g.deplacer(d2, this);
		}
		
	}
	
	private Player chercheEnnemiLePlusProche(List<Player> l)
	{
		Player res  = l.get(0);
		int v = Integer.MAX_VALUE;
		for(int i = 1; i<l.size();i++)
		{
			int t = Math.abs((int)Math.sqrt(Math.pow((res.getHitBox().x - this.getHitBox().x),2) + Math.pow((res.getHitBox().x - this.getHitBox().y),2)));
					
			if(t != 0 && t < v)
				res=l.get(i);
		}
		return res;
	}
	
	/**
	 * Trouve la bombe la plus proche de ce joueur
	 * @param l La liste des bombes actuellemnt en jeu (posees par terre)
	 * @return Une bombe qui est la plus proche
	 */
	private Bombe chercheBombeLaPlusProche(List<Bombe> l)
	{
		Bombe res = null;
		if(l!=null && l.size()>0)
			{
			res  = l.get(0);
			int v = Integer.MAX_VALUE;
			for(int i = 1; i<l.size();i++)
			{
				int t = Math.abs((int)Math.sqrt(Math.pow((res.getPosition().x - this.getHitBox().x),2) + Math.pow((res.getPosition().x - this.getHitBox().y),2)));
						
				if(t != 0 && t < v)
					res=l.get(i);
			}
		}
		return res;
	}
}
