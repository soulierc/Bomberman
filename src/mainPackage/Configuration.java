package mainPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Configuration {

	private boolean FULLSCREEN;
	private int NB_CASES;
	private int NB_PLAYERS;
	
	public Configuration()
	{
		try {
			loadConfiguration();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void loadConfiguration() throws FileNotFoundException
	{
		File f = new File("conf.txt");
		Scanner scan = new Scanner(f);
		
		String line = "";
		String w = "";
		
		line = scan.nextLine();
		w = line.substring(line.indexOf(":"));
		w = w.replaceAll("\\D*", "");
		System.out.print(w);
		this.NB_CASES = Integer.parseInt(w);
		
		line = scan.nextLine();
		w = line.substring(line.indexOf(":"));
		w = w.replaceAll(":*\\s*\\d*", "");
		System.out.print(w);
		this.FULLSCREEN = w.equals("true");
		
		line = scan.nextLine();
		w = line.substring(line.indexOf(":"));
		w = w.replaceAll("\\D*", "");
		System.out.print(w);
		this.NB_PLAYERS = Integer.parseInt(w);
		
		
	}

	public List<Case> getCases() throws CorruptedFileException
	{
		ArrayList<Case> l = new ArrayList<Case>();
		File f = new File("conf.txt");
		try 
		{
			
			Scanner scan = new Scanner(f);
			scan.nextLine();
			scan.nextLine();
			scan.nextLine();
			for(int i=0; i<NB_CASES*NB_CASES; i++)
			{
				l.add(composeCase(scan.nextLine()));
			}
			
			
		} 
		catch (FileNotFoundException e) 
		{

			e.printStackTrace();
		}
		return l;
	}
	
	private Case composeCase(String nextLine) throws CorruptedFileException{
		
		if(!nextLine.startsWith("case"))
			throw new CorruptedFileException();
		
		nextLine = nextLine.substring(nextLine.indexOf(":"));
		nextLine = nextLine.replaceAll(":*\\s*\\[*\\]*", ""); //Un peu de nettoyage
		String[] a = nextLine.split(",");
		Case c = new Case(Integer.parseInt(a[0]) * Case.DEFAULT_SIZE, Integer.parseInt(a[1])* Case.DEFAULT_SIZE);
		c.setEtat(State.valueOf(a[2]));
		return c;
	}

	public boolean isFULLSCREEN() {
		return FULLSCREEN;
	}

	public void setFULLSCREEN(boolean fULLSCREEN) {
		FULLSCREEN = fULLSCREEN;
	}

	public int getNB_CASES() {
		return NB_CASES;
	}

	public void setNB_CASES(int nB_CASES) {
		NB_CASES = nB_CASES;
	}

	public int getNB_PLAYERS() {
		return NB_PLAYERS;
	}

	public void setNB_PLAYERS(int nB_PLAYERS) {
		NB_PLAYERS = nB_PLAYERS;
	}
}
