package mainPackage;

import java.awt.Rectangle;

public class Case {

	private final Rectangle box;
	public static final int DEFAULT_SIZE = 30;
	private State etat;
	private int beingDestroyed;
	public final static int TIMEDESTROYED = 1000;
	
	public Case(int x, int y)
	{
		this.box = new Rectangle(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
		this.beingDestroyed = -1;
	}


	public Rectangle getBox() {
		return box;
	}


	public State getEtat() {
		return etat;
	}


	public void setEtat(State etat) {
		this.etat = etat;
	}
	
	public boolean canWalkOnIt()
	{
		return etat == State.BOMBE || etat == State.BONUS || etat == State.VIDE || etat == State.BEINGDESTROYED;
	}
	
	/**
	 * Destroy or not the current case
	 * @return true si la case etait une caisse ou une caisse incassable ou un terrain supprim� faux sinon, sert a arreter la d�tonation d'une bombe
	 */
	public boolean destroy()
	{
		State prev = etat;
		if(etat != State.INCASSABLE && etat != State.SUPPRIMEE)
		{
			if(etat != State.BOMBE)
			{
				this.etat = State.BEINGDESTROYED;
				this.setBeingDestroyed();
			}
		}
		
		return prev == State.CAISSE || prev == State.INCASSABLE || prev == State.SUPPRIMEE;
	}
	
	public void checkDestroy()
	{
		if(this.beingDestroyed == 0 && this.etat == State.BEINGDESTROYED)
		{
			this.beingDestroyed = -1;
			this.setEtat(State.VIDE);
		}
		else if( this.beingDestroyed > 0 && this.etat == State.BEINGDESTROYED)
		{
			this.decreaseBeingDestroyed(10);
		}
	}
	
	public String toString()
	{
		String res ="Caisse : ";
		res+= "[" + (int)this.box.x/Case.DEFAULT_SIZE +",";
		res+= (int)this.box.y/Case.DEFAULT_SIZE + ",";
		res+= etat +"]";
		return res;
	}


	public int getBeingDestroyed() {
		return beingDestroyed;
	}


	public void setBeingDestroyed() {
		this.beingDestroyed = Case.TIMEDESTROYED;
	}
	
	public void decreaseBeingDestroyed(int t)
	{
		this.beingDestroyed-=t;
	}
}
