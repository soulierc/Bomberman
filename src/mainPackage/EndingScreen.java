package mainPackage;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;


public class EndingScreen extends View {

	private final static String LOSS_SCREEN = "LOOSER !";
	private final static String WIN_SCREEN = "WINNER !";
	private int state;
	
	public EndingScreen(Display motherFrame) {
		super(motherFrame);
		this.state = 0;
		initView();
	}
	
	public EndingScreen(Display motherFrame, int state)
	{
		super(motherFrame);
		this.state = state;
		initView();
	}
	
	public void initView()
	{
		this.getMotherFrame().setSize(500, 450);
		if(state == 0)
		{
			this.setBackground(Color.red);
		}
		else
		{
			this.setBackground(Color.green);
		}
	}
	public void paintComponent(Graphics g)
	{
		g.setColor(this.getBackground());
		g.fillRect(0, 0, this.getWidth(), this.getWidth());
		g.setFont(new Font("Arial",Font.BOLD, 70));
		g.setColor(Color.BLACK);
		
		FontMetrics metrics = g.getFontMetrics();
		int hgt = metrics.getHeight();
		Rectangle window = g.getClipBounds();
		if(state == 0)
		{
			int adv = metrics.stringWidth(LOSS_SCREEN);
			Rectangle size = new Rectangle(0,0,adv+2, hgt+2);
			Rectangle r = Tools.centrerRectangleInRectangle(size, window);
			g.drawString(LOSS_SCREEN, r.x, r.y);
		}
		else
		{
			int adv = metrics.stringWidth(WIN_SCREEN);
			Rectangle size = new Rectangle(0,0,adv+2, hgt+2);
			Rectangle r = Tools.centrerRectangleInRectangle(size, window);
			g.drawString(WIN_SCREEN, r.x, r.y);
		}
	}
}
