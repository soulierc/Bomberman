package mainPackage;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Tools {

	
	/**
	 * Retourne une case de la liste l a une position donn�e ou null si pas de r�sultat
	 * @param l La liste dans laquelle rechercher
	 * @param x La coordonn�e x de la case
	 * @param y La coordonn�e y de la case
	 * @return Une case aux coordonn�es sp�cifi�es ou null si on a rien trouv�
	 */
	public static Case getCaseAt(List<Case> l, int x, int y)
	{
		Case res = null;
		Rectangle r = new Rectangle(x,y,Case.DEFAULT_SIZE,Case.DEFAULT_SIZE);
		for(int i = 0; i< l.size();i++ )
		{
			if(l.get(i).getBox().intersects(r))
			{
				res = l.get(i);
				break;
			}
		}
		return res;
	}
	
	/**
	 * Retourne un joueur de la liste l a une position donn�e ou null si pas de r�sultat
	 * @param l La liste dans laquelle rechercher
	 * @param x La coordonn�e x du joueur
	 * @param y La coordonn�e y du joueur
	 * @return Un joueur aux coordonn�es sp�cifi�es ou null si on a rien trouv�
	 */
	public static Player getPlayerAt(List<Player> l, int x, int y)
	{
		Player res = null;
		Rectangle r = new Rectangle(x,y,Case.DEFAULT_SIZE,Case.DEFAULT_SIZE);
		for(int i = 0; i< l.size();i++ )
		{
			if(l.get(i).getHitBox().intersects(r))
			{
				res = l.get(i);
				break;
			}
		}
		return res;
	}
	
	/**
	 * Retourne une case avec l'Etat s sp�cifi�
	 * @param l La liste dans laquelle rechercher
	 * @param s L'Etat a rechercher
	 * @param offset Si offset = 0 alors on retourne la premiere case trouv�e, si offset = 1 on retourne la 2�me etc ...
	 * @return une case ou null si pas de r�sultat
	 */
	public static Case getCaseWithState(List<Case> l, State s, int offset)
	{
		Case res = null;
		int off = offset;
		for(int i = 0; i< l.size();i++ )
		{
			if(l.get(i).getEtat().equals(s))
			{
				if(off == 0)
				{
					res = l.get(i);
					break;
				}
				else
					off--;
			}
		}
		
		return res;
	}
	
	/**
	 * Calcule les cases de l qui sont dans la portee de la bombe b
	 * @param l La liste des cases du plateau
	 * @param b La bombe b
	 * @return Une liste de cases qui sont atteintes par la bombe
	 */
	public static List<Case> getCaseInExplosion(List<Case> l, Bombe b)
	{
		List<Case> res = new ArrayList<Case>();
		if(b != null)
		{
			res.add(Tools.getCaseAt(l, b.getPosition().x, b.getPosition().y));
			for(int i =0; i<b.getPortee(); i++)
			{
				res.add(Tools.getCaseAt(l, b.getPosition().x+i*Case.DEFAULT_SIZE, b.getPosition().y));
				res.add(Tools.getCaseAt(l, b.getPosition().x, b.getPosition().y + i * Case.DEFAULT_SIZE));
			}
		}
		return res;
	}
	
	/**
	 * Retourne vrai si le player p est sur une des cases de la liste l.
	 * @param l Une liste de cases
	 * @param p Le joueur a tester
	 * @return vrai si le player p est sur une des cases de la liste l, faux sinon.
	 */
	public static boolean isPlayerOnCase(List<Case> l, Player p)
	{
		boolean res = false;
		for(int i = 0; i<l.size() && !res; i++)
		{
			res = res || p.getHitBox().intersects(l.get(i).getBox());
		}
		return res;
	}
	
	public static Rectangle centrerRectangleInRectangle(Rectangle leRectangle, Rectangle reference)
	{
		Rectangle res = null;
		int x = (reference.x+reference.width/2) - leRectangle.width/2;
		int y = (reference.y+reference.height/2) - leRectangle.height/2;
		res = new Rectangle(x,y,leRectangle.width, leRectangle.height);
		
		return res;
	}
}
