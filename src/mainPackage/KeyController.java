package mainPackage;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener {

	private UserPolling u;
	
	public KeyController(UserPolling u)
	{
		this.u = u;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode())
		{
		case KeyEvent.VK_DOWN : u.deplacer(Direction.BAS,u.getThePlayer());
								break;
		case KeyEvent.VK_UP : u.deplacer(Direction.HAUT,u.getThePlayer());
								break;
		case KeyEvent.VK_RIGHT : u.deplacer(Direction.DROITE,u.getThePlayer());
								break;
		case KeyEvent.VK_LEFT : u.deplacer(Direction.GAUCHE,u.getThePlayer());
								break;
		case KeyEvent.VK_SPACE : u.poserBombe(u.getThePlayer());
		default:break;
		
		}
			
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
