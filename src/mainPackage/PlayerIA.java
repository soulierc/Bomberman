package mainPackage;

import java.awt.Rectangle;
import java.util.List;
import java.util.Random;

public class PlayerIA extends Player {

	private int timeBeforeAction;
	private Random r;
	public PlayerIA(int x, int y, int name) {
		super(x, y, name);
		this.timeBeforeAction = 1000;
		long a = System.nanoTime();
		r = new Random(a);
		System.out.println("Seed is " + a);
		// TODO Auto-generated constructor stub
	}

	public void jouer(Game g)
	{
		this.timeBeforeAction-=5;
		if(timeBeforeAction <= 0 && !this.isDead())
		{
			Bombe b = this.chercheBombeLaPlusProche(g.getBombes());
			System.out.println("Bombe la plus proche :" + b);
			//Si l'ordi est sur une case qui va etre detruite ... 
			if(Tools.isPlayerOnCaseOf(Tools.getCaseInExplosion(g.getCase(), b), this))
			{
				fuirBombe(g,g.getCase(),b);
				//deplacerAleatoire(g); // ... ON LE SORT DE LA !
				System.out.println("Je suis n°" + this.getName());
			}
			else
			{
				deplacerVersJoueur(g,chercheEnnemiLePlusProche(g.getPlayers()));
				if(this.getBombes() >= this.getMaxBombes() /2 && detruireautour(g.getCase()))
					g.poserBombe(this);
				System.out.println("\tJe suis n°" + this.getName());
			}
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			this.timeBeforeAction = 100;
		}
	}
	
	private void fuirBombe(Game g, List<Case> l,Bombe b)
	{
	
		if(b!=null)
		{
			Rectangle bPos = b.getPosition();
			Direction d1 = null;
			Direction d2 = null;
			if (bPos.x > this.getHitBox().x)
				d1 = Direction.GAUCHE;
			else if (bPos.x < this.getHitBox().x)
				d1 = Direction.DROITE;
			
			if(bPos.y > this.getHitBox().y)
				d2 = Direction.HAUT;
			else if (bPos.y < this.getHitBox().y)
				d2 = Direction.BAS;
			
			//System.out.println(this.getName() +" : "+ d1 + "  " + d2);
			if(d1 == null && d2 == null)
				deplacerAleatoire(g,b);
			else
			{
				deplacer(g,d1);
				deplacer(g,d2);
			}
			
		}
	}
	
	private void deplacerAleatoire(Game g, Bombe b) {
		
		Case[] cs = Tools.getCasesAdjacentToOneCase(g.getCase(), Tools.getCaseAt(g.getCase(),this.getHitBox().x, this.getHitBox().y));
		boolean moved = false;
		for(int i = 0; i < cs.length && !moved; i++ )
		{
			if( !Tools.isCaseInExplosion(g.getCase(),b,cs[i]) && cs[i].getEtat() != State.BOMBE && cs[i].canWalkOnIt()){
				this.setHitBox(new Rectangle(cs[i].getBox().x, cs[i].getBox().y, Player.DEFAULT_SIZE,Player.DEFAULT_SIZE));
				System.out.println(!Tools.isCaseInExplosion(g.getCase(),b,cs[i]));
				moved = true;
			}
		}
		if(!moved) //On a pas bouger c'est donc qu'on etait coincé pas des bombes, on bouge donc aléatoirement
			while(deplacer(g, Direction.values()[r.nextInt(4)])==0);
		
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
	
	/**
	 * Deplace ce joueur d'une case vers le joueur p
	 * @param g Le jeu courrant
	 * @param p Le joueur cible
	 */
	private void deplacerVersJoueur(Game g, Player p)
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
			Case c1 = Tools.getCaseBeforeDeplacement(g.getCase(), p.getHitBox(), d1);
			Case c2 = Tools.getCaseBeforeDeplacement(g.getCase(), p.getHitBox(), d2);
			//PROBLEME ICI if(c1 != null && Tools.)
			deplacer(g,d1);
			deplacer(g,d2);
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
						
				if(t != 0 && t < v){
					res=l.get(i);
					v = t;
				}
			}
		}
		return res;
	}
}
