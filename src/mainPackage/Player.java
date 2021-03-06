package mainPackage;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class Player {

	private Rectangle hitBox;
	public static final int DEFAULT_SIZE = 20;
	private boolean dead;
	private int bombes;
	private boolean placed;
	private int maxBombes;
	private int name;

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public Player(int x, int y, int name) {
		this.hitBox = new Rectangle(x, y, DEFAULT_SIZE, DEFAULT_SIZE);
		this.dead = false;
		this.bombes = 2;
		this.placed = true;
		this.maxBombes = 2;
		this.name = name;
	}

	public Rectangle getHitBox() {
		return hitBox;
	}

	public void setHitBox(Rectangle hitBox) {
		this.hitBox = hitBox;
		this.placed = true;
	}

	public boolean isDead() {
		return dead;
	}

	public void setDead(boolean dead) {
		this.dead = dead;
	}

	public void increaseNBBombes() {
		if (bombes < maxBombes)
			this.bombes++;
	}

	public void decreaseNBBombes() {
		this.bombes--;
	}

	public int getBombes() {
		return this.bombes;
	}

	public String toString() {
		String res = "Name : " + this.name;

		res += this.hitBox.toString() + "\n";

		if (!dead)
			res += "not";
		res += " dead \n";
		res += "bombes restantes : " + this.bombes;

		return res;
	}

	public int getMaxBombes() {
		return maxBombes;
	}

	public void setMaxBombes(int maxBombes) {
		this.maxBombes = maxBombes;
	}

	public void jouer(Game g) {
	}

	public int deplacer(Game g, Direction d) {

		if (d == null)
			return 1;

		if (!this.isDead()) {
			int x = this.getHitBox().x;
			int y = this.getHitBox().y;
			switch (d) {
			case HAUT:
				y -= Case.DEFAULT_SIZE;
				break;
			case BAS:
				y += Case.DEFAULT_SIZE;
				break;
			case DROITE:
				x += Case.DEFAULT_SIZE;
				break;
			case GAUCHE:
				x -= Case.DEFAULT_SIZE;
				break;
			}

			Rectangle r = new Rectangle(x, y, Player.DEFAULT_SIZE,
					Player.DEFAULT_SIZE); // Potentielle future position

			// Si le joueur peut aller sur la case, on le deplace
			if (Tools.getCaseAt(g.getCase(), x, y).canWalkOnIt()) {
				this.setHitBox(r);
				return 1;

			}

		}
		return 0;
	}

}
