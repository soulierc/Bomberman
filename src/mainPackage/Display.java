package mainPackage;

import javax.swing.JFrame;

public class Display extends JFrame {

	private UserPolling userPolling;
	private Concepteur concepteur;
	public Display()
	{
		initMenu();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	public void initMenu()
	{
		this.setSize(500, 450);
		this.setContentPane(new MenuView(this));
		((MenuView)this.getContentPane()).updateUI();
	}
	public void initGame() {
		System.err.println("Display.initGame()");
		this.userPolling.stop();
		this.setContentPane(new GameView(this, this.getUserPolling()));
		this.setFocusable(false);
		this.userPolling.initialise();
		if(userPolling.getTheConfiguration().isFULLSCREEN())
		{
			this.setSize(this.getMaximumSize());
		}
		else
		{
			int l = (userPolling.getTheConfiguration().getNB_CASES()+1) * Case.DEFAULT_SIZE;
			System.out.print(l);
			this.setSize(l, l);
		}
		this.requestFocus();
		this.userPolling.go();
	}

	public void initConcepteur()
	{
		this.setContentPane(new ConcepteurView(this, this.concepteur));
		int w = this.concepteur.getSizeGame()* Case.DEFAULT_SIZE+150;
		int h = this.concepteur.getSizeGame()* Case.DEFAULT_SIZE + 300;
		this.setSize(w, h);
	}
	
	public UserPolling getUserPolling() {
		return userPolling;
	}

	public void setUserPolling(UserPolling userPolling) {
		this.userPolling = userPolling;
	}

	public void afficher() {
		if(this.getContentPane() instanceof GameView)
		{
			GameView g = (GameView) this.getContentPane();
			
			g.repaint();
		}
		this.getContentPane().repaint();
	}
	
	public void updateConcepteur()
	{
		((ConcepteurView)this.getContentPane()).maj(true);
	}
	
	public void displayLoose()
	{
		this.setContentPane(new EndingScreen(this,0));
	}
	
	public void displayWin()
	{
		this.setContentPane(new EndingScreen(this,1));
	}

	public Concepteur getConcepteur() {
		return concepteur;
	}

	public void setConcepteur(Concepteur concepteur) {
		this.concepteur = concepteur;
	}
}
