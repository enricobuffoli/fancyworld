package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import static injected.it.fancyworld.Utils.Utility.fromUpToDown;

import java.awt.*;
import java.io.Serializable;
import java.util.*;

public class Mondo implements Serializable
{
	private ArrayList<Livello> livelli;
	private int currentLevel;
	private final Point startPos = new Point(9, 9);
	private final int numLivelli = 10;
	
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
		ArrayList<Luogo> upStrs = livelli.get(currentLevel - 2).getUpStairses();
		ArrayList<Luogo> dwStrs = new ArrayList<>();
        for (Luogo upStr : upStrs)
            dwStrs.add(fromUpToDown(upStr));

        livelli.add(new Livello(currentLevel, nextStart, dwStrs, livelli.get(currentLevel - 2).getUnlockedUpPos()));
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
