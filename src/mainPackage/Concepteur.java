package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import javax.tools.Tool;

public class Concepteur {

	public static final int DEFAULT_SIZE = 20;
	private final Display theDisplay;
	private int sizeGame;
	private List<Case> listCase;
	public boolean continuer;
	private State selectedState;
	
	public Concepteur(Display theDisplay)
	{
		this.theDisplay = theDisplay;
		this.listCase = new ArrayList<Case>();
		this.sizeGame = Concepteur.DEFAULT_SIZE;
		creerPlateau(this.sizeGame);
		this.selectedState = State.DEPART;
	}
	
	public void saveInFile() throws FileNotFoundException
	{
		System.out.println("ecriture");
		PrintStream p = new PrintStream(new File("/home/clement/niveau.txt"));
		p.println("NBcasesOnLine : " + sizeGame);
		p.println("fullscreen : false");
		int nbP = 0;
		while(Tools.getCaseWithState(listCase, State.DEPART, nbP) != null)
		{
			nbP++;
		}
		p.println("nbPlayers : " + nbP);
		for(int i=0; i<this.listCase.size();i++)
		{
			p.println("case : " + "[" + listCase.get(i).getBox().x/Case.DEFAULT_SIZE + "," + listCase.get(i).getBox().y/Case.DEFAULT_SIZE
			+ "," + listCase.get(i).getEtat() + "]");
		}
		p.println(" ");
		System.out.println("ecriture finie");
	}
	
	public void creerPlateau(int size)
	{
		if(size > 0)
		{
			this.listCase = new ArrayList<Case>();
			for(int i = 0; i < size; i++)
			{
				for(int j = 0; j<size; j++)
				{
					Case c = new Case(i*Case.DEFAULT_SIZE, j*Case.DEFAULT_SIZE);
					c.setEtat(State.VIDE);
					this.listCase.add(c);
				}
			}
		}
	}
	
	public void enregistrerCase(State e, int x, int y)
	{
		Case c = Tools.getCaseAt(listCase, x, y);
		if(c != null)
			c.setEtat(e);
	}

	public Display getTheDisplay() {
		return theDisplay;
	}

	public int getSizeGame() {
		return sizeGame;
	}

	public void setSizeGame(int sizeGame) {
		if(sizeGame > 0)
			this.sizeGame = sizeGame;
	}

	public List<Case> getListCase() {
		return listCase;
	}
	
	public void start()
	{
		this.continuer = true;
		while(this.continuer)
		{
			//this.theDisplay.updateConcepteur();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public State getSelectedState() {
		return selectedState;
	}

	public void setSelectedState(State selectedState) {
		this.selectedState = selectedState;
	}
}
