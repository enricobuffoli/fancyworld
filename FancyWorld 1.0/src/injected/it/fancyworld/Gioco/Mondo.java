package injected.it.fancyworld.Gioco;

import static injected.it.fancyworld.Utils.Utility.fromUpToDown;

import java.awt.*;
import java.util.*;

import injected.it.fancyworld.Gioco.Luoghi.*;

public class Mondo 
{
	private ArrayList<Livello> livelli;
	private int currentLevel;
	private final Point startPos = new Point(9, 9);
	private final int numLivelli = 10;
	
	public Mondo()
	{
		currentLevel = 1;
		livelli = new ArrayList<>();
		livelli.add(new Livello(currentLevel - 1, startPos, null));
	}
	
	public void nextLevel(Point pgPosition)
	{
		ArrayList<UpStairs> upStrs = livelli.get(currentLevel - 1).getUpStairses();
		ArrayList<DownStairs> dwStrs = new ArrayList<>();
        for (UpStairs upStr : upStrs)
            dwStrs.add(fromUpToDown(upStr));

        livelli.add(new Livello(currentLevel + 1, pgPosition, dwStrs));
	}
	
	public void previousIncrementationLevel()
	{
		++currentLevel;
	}
	
	public void latterDecrementationLevel()
	{
		currentLevel--;
	}
	
	public Livello getLevel(int index)
	{
		return livelli.get(index);
	}
	
	public Livello getCurrentLevel()
	{
		return livelli.get(currentLevel - 1);
	}
	
	public ArrayList<Livello> getLevels()
	{
		return livelli;
	}
	
	public Point getStartPos()
	{
		return startPos;
	}
	
	public int getIndexCurrentLevel()
	{
		return currentLevel;
	}
}
