package mainPackage;

public class Bomberman {

	
	public static void main(String args[])
	{
		Configuration config = new Configuration();
		
		
		Display d = new Display();
		Game g = new Game(d,config);
		d.setUserPolling(g);
		Concepteur c = new Concepteur(d);
		d.setConcepteur(c);
		while(true){
			while(!(d.getContentPane() instanceof GameView) && !(d.getContentPane() instanceof ConcepteurView))
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			if(d.getContentPane() instanceof GameView)
			{
				System.err.println("Start of game");
				g.start();
			}
			else if(d.getContentPane() instanceof ConcepteurView)
			{
				c.start();
			}
			System.err.println("end of game");
		}	
		//g.start();
	}
}
