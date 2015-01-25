package mainPackage;

import java.awt.Rectangle;
import java.util.List;

public class Bombe {

	private Rectangle position;
	private int timeLeft;
	private int portee;
	private Player owner;
	
	public static final int DEFAULT_PORTEE = 1;
	public Bombe(Rectangle pos, Player owner, int time)
	{
		this.position = pos;
		this.timeLeft = time;
		this.portee = DEFAULT_PORTEE;
		this.owner = owner;
	}
	public Rectangle getPosition() {
		return position;
	}
	public void setPosition(Rectangle position) {
		this.position = position;
	}
	public int getTimeLeft() {
		return timeLeft;
	}
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	
	/**
	 * Detruit les cases a detruire et tue les joueurs s'ils sont a portee
	 * @param cases Toutes les cases constituant le plateau
	 * @param players Les joueurs de la partie
	 */
	public void destroyCases(List<Case> cases, List<Player> players)
	{
		Case c = Tools.getCaseAt(cases, position.x, position.y);
		if(c != null)
		{
			if(c.getEtat() == State.BONUS || c.getEtat() == State.CAISSE || c.getEtat() == State.BOMBE)
			{
				c.setEtat(State.BEINGDESTROYED);
				c.setBeingDestroyed();
			}
			Player p = Tools.getPlayerAt(players, position.x, position.y);
			if(p != null)
				p.setDead(true);
		}
		for(int i = 1; i<=portee;i++)
		{
			Case c2 = Tools.getCaseAt(cases, position.x + (i*Case.DEFAULT_SIZE), position.y);
			if(c2 != null)
			{
				Player p = Tools.getPlayerAt(players, position.x + (i*Case.DEFAULT_SIZE), position.y);
				if(p != null)
					p.setDead(true);
				if(c2.destroy())
					break;
			}
		}
		
		for(int i = 1; i<=portee;i++)
		{
			Case c2 = Tools.getCaseAt(cases, position.x - (i*Case.DEFAULT_SIZE), position.y);
			if(c2 != null)
			{
				Player p = Tools.getPlayerAt(players, position.x - (i*Case.DEFAULT_SIZE), position.y);
				if(p != null)
					p.setDead(true);
				if(c2.destroy())
					break;
			}
		}
		
		for(int i = 1; i<=portee;i++)
		{
			Case c2 = Tools.getCaseAt(cases, position.x, position.y + (i*Case.DEFAULT_SIZE) );
			if(c2 != null)
			{
				Player p = Tools.getPlayerAt(players, position.x, position.y + (i*Case.DEFAULT_SIZE) );
				if(p != null)
					p.setDead(true);
				if(c2.destroy())
					break;
			}
		}
		
		for(int i = 1; i<=portee;i++)
		{
			Case c2 = Tools.getCaseAt(cases, position.x, position.y - (i*Case.DEFAULT_SIZE) );
			if(c2 != null)
			{
				Player p = Tools.getPlayerAt(players, position.x, position.y - (i*Case.DEFAULT_SIZE));
				if(p != null)
					p.setDead(true);
				if(c2.destroy())
					break;
			}
		}
		this.owner.increaseNBBombes();
	}
	
	
	public int getPortee() {
		return portee;
	}
	public void setPortee(int portee) {
		this.portee = portee;
	}

}
