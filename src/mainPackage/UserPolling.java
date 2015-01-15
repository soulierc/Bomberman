package mainPackage;

import java.util.List;

public interface UserPolling {

	public void initialise();
	
	public int deplacer(Direction d, Player p);
	
	public int poserBombe(Player p);
	
	public List<Player> getPlayers();
	
	public List<Case> getCase();
	
	public Player getThePlayer();
	
	public Configuration getTheConfiguration();
	
	public void go();
	
	public void stop();
	
}
