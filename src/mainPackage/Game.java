package mainPackage;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Game implements UserPolling{

	private final Display display;
	private List<Player> players;
	private int timeLeft; 
	private List<Case> cases;
	private Player thePlayer;
	private List<Bombe> bombes;
	private boolean started;
	private final Configuration theConfiguration;
	private boolean continuer;
	private static final int TIME_BEFORE_DESTRUCTION = 60000;
	private int countBombe;
	
	/*
	 * Create a new game with the appropriate display, and according to the configuration given
	 * 
	 */
	public Game(Display theDisplay, Configuration conf)
	{
		this.display = theDisplay;
		this.players = new ArrayList<Player>();
		this.cases = new ArrayList<Case>();
		this.bombes = new ArrayList<Bombe>();
		this.timeLeft = TIME_BEFORE_DESTRUCTION;
		this.started = false;
		this.theConfiguration = conf;
		this.continuer = true;
		this.countBombe = 0;
	}
	
	public Display getDisplay() {
		return display;
	}
	
	/**
	 * Boucle principale du jeu
	 */
	public void start() {
		
		
		boolean endingGameStarted = false;
		int stateOfDestruction = 0;
		this.timeLeft = TIME_BEFORE_DESTRUCTION;
		System.err.println("Game.start() : Attente du debut .... ");
		while(!started)
		{
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.err.println("Game.start() : Fin de l'attente le jeu va commencer ...");
		while(this.continuer)
		{
			dereaseTimeOfBombes();
			checkStateCases();
			jouerIA();
			
			//Gere le temps restant avant la destruction du plateau
			try {
				Thread.sleep(10);
				this.timeLeft-=10;
				
				if(this.timeLeft == 0)
				{
					if(endingGameStarted)
					{
						destroyPlateau(stateOfDestruction);
						stateOfDestruction++;
					}
					else
					{
						endingGameStarted = true;
					}
					this.timeLeft = TIME_BEFORE_DESTRUCTION;
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(thePlayer != null && thePlayer.isDead())
			{
				continuer =false;
				display.displayLoose();
			}
			else if (checkWin())
			{
				continuer =false;
				display.displayWin();
			}
			display.afficher(); // ON REECRIT LE PLATEAU
		}
		display.afficher(); // ON REECRIT LE PLATEAU
		System.err.println("Game.start() : Fin de la partie, ecran de fin, attente de 3sec");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.err.println("Affichage du menu");
		display.initMenu();
	}
	
	/*
	 * Fait jouer les IA
	 */
	private void jouerIA() {
		
		for(int i=0;i<this.players.size()-1;i++) //Le dernier joueur est le joueur humain
		{
			this.players.get(i).jouer(this);
		}
		
	}
	/*
	 * Met a jour l'etat des cases qui ont des bombes
	 */
	private void checkStateCases()
	{
		for(int i =0; i < this.cases.size(); i++)
		{
			this.cases.get(i).checkDestroy();
		}
	}
	
	/**
	 * Detruit progressivement le plateau
	 * @param stateOfDestruction
	 */
	private void destroyPlateau(int stateOfDestruction) {
		
		for(int i = 0; i<Math.sqrt(this.cases.size()); i++)
		{
			Case c = Tools.getCaseAt(cases, stateOfDestruction*Case.DEFAULT_SIZE, i*Case.DEFAULT_SIZE);
			if(c!=null)
				c.setEtat(State.SUPPRIMEE);
			
			c = Tools.getCaseAt(cases, (int) (((Math.sqrt(this.cases.size())-1)-stateOfDestruction)*Case.DEFAULT_SIZE), i*Case.DEFAULT_SIZE);
			if(c!=null)
				c.setEtat(State.SUPPRIMEE);
			
			Player p = Tools.getPlayerAt(players, stateOfDestruction*Case.DEFAULT_SIZE, i*Case.DEFAULT_SIZE);
			if(p!=null)
				p.setDead(true);
			
			 p = Tools.getPlayerAt(players,  (int) (((Math.sqrt(this.cases.size())-1)-stateOfDestruction)*Case.DEFAULT_SIZE), i*Case.DEFAULT_SIZE);
			if(p!=null)
				p.setDead(true);
		}
		
		
		
	}
	/*
	 * Test la victoire du joueur humain
	 * 
	 */
	private boolean checkWin() {
		boolean res = true;
		
		for(int i=0;i < this.players.size()-1;i++)
		{
			res = res && this.players.get(i).isDead();
		}
		return res;
	}

	@Override
	public void initialise() {
		
		thePlayer = new Player(-1,-1,99999);
		this.players.removeAll(this.players);
		this.bombes.removeAll(this.bombes);
		for(int i= 0; i<theConfiguration.getNB_PLAYERS()-1; i++)
		{
			this.players.add(new PlayerIA(-1,-1,i));
		}
		this.players.add(thePlayer);
		creerPlateau(theConfiguration.getNB_CASES());
		placerPlayers();
		this.continuer = true;
	}
	
	private void creerPlateau(int nbCases)
	{
		try {
			this.cases = theConfiguration.getCases();
		} catch (CorruptedFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void placerPlayers()
	{
		int i =0;
		Case c = Tools.getCaseWithState(cases, State.DEPART, 0);
		while(c != null)
		{
			c.setEtat(State.VIDE);
			this.players.get(i).setHitBox(new Rectangle(c.getBox().x, c.getBox().y, Player.DEFAULT_SIZE, Player.DEFAULT_SIZE));
			i++;
			c = Tools.getCaseWithState(cases, State.DEPART, 0);
		}		
	}
	@Override
	public int deplacer(Direction d, Player p) {
			return p.deplacer(this, d);
	}
	@Override
	public int poserBombe(Player p) {
		
		for(int i=0; i<cases.size(); i++)
		{
			if(p.getHitBox().intersects(cases.get(i).getBox()) && cases.get(i).getEtat() != State.BOMBE && p.getBombes() >0)
			{
				cases.get(i).setEtat(State.BOMBE);
				p.decreaseNBBombes();
				this.bombes.add(new Bombe(cases.get(i).getBox(), p, 3000));
				System.out.println("Bombe n°"+this.countBombe+" placée par " + p.getName());
				this.countBombe++;
			}
		}
		return 0;
	}
	
	@Override
	public List<Player> getPlayers() {
		return this.players;
	}

	public int getTimeLeft() {
		return timeLeft;
	}

	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}

	@Override
	public List<Case> getCase() {
		
		return this.cases;
	}
	
	public void dereaseTimeOfBombes()
	{
		for(int i=0; i<this.bombes.size(); i++)
		{
			this.bombes.get(i).setTimeLeft(bombes.get(i).getTimeLeft()-10);
			if(this.bombes.get(i).getTimeLeft() <= 0)
			{
				this.bombes.get(i).destroyCases(cases, players);
				this.bombes.remove(i);
				i--;
			}
		}
	}

	@Override
	public Player getThePlayer() {
		// TODO Auto-generated method stub
		return this.thePlayer;
	}

	@Override
	public void go() {
		this.started = true;
		
	}
	
	@Override
	public void stop() {
		this.started = false;
		
	}

	@Override
	public Configuration getTheConfiguration() {

		return this.theConfiguration;
	}

	public List<Bombe> getBombes() {
		return bombes;
	}
	
}
