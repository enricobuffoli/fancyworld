package injected.it.fancyworld.Gioco;

import static injected.it.fancyworld.Utils.Utility.fromUpToDown;

import java.awt.*;
import java.util.*;

import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.DownStairs;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.UpStairs;
import injected.it.fancyworld.Utils.Utility;

public class Mondo 
{
	private ArrayList<Livello> livelli;
	private int currentLevel;
	private final Point startPos = new Point(9, 9);
	private final int numLivelli = 10, widthLvl = 20;
	
	public Mondo()
	{
		currentLevel = 1;
		livelli = new ArrayList<>();
		createWorld();
	}

	public void createWorld()
	{
		livelli.add(new Livello(currentLevel, startPos, null,null));
		currentLevel++;

		for(; currentLevel < numLivelli + 1; currentLevel++) {
			int nextX = Utility.getRandomNumber();
			int nextY = Utility.getRandomNumber();
			Point nextStart = new Point(nextX, nextY);

			nextLevel(startPos);
		}

		currentLevel = 1;
	}
	
	public void nextLevel(Point nextStart)
	{
		ArrayList<UpStairs> upStrs = livelli.get(currentLevel - 2).getUpStairses();
		ArrayList<DownStairs> dwStrs = new ArrayList<>();
        for (UpStairs upStr : upStrs)
            dwStrs.add(fromUpToDown(upStr));

        livelli.add(new Livello(currentLevel, nextStart, dwStrs, livelli.get(currentLevel-2).getUnlockedUpPos()));
	}
	
	public void previousIncrementationLevel()
	{
		currentLevel = currentLevel + 1;
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
