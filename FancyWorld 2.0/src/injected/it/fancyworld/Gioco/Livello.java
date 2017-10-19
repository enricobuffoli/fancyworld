package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Gioco.Luoghi.*;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.DownStairs;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Pass;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.UpStairs;
import injected.it.fancyworld.Utils.Utility;

import java.awt.*;
import java.util.ArrayList;

import static injected.it.fancyworld.Utils.Utility.*;

/**
 * Created by enrico on 05/10/17.
 */

public class Livello
{
    private ArrayList<ArrayList<Luogo>> riga;
    private int levelIndex,levUp,levTrap;
    private Point startPos;
    private ArrayList<UpStairs> upStairses;
    private ArrayList<DownStairs> downStairses;
    private ArrayList<Trap> traps;
    private Start start;
    private Goal goal;
    private final int boardDim = 20;

    public Livello(int levelIndex, Point startPos, ArrayList<DownStairs> downStairs)
    {
        if(downStairs!=null)
        	this.downStairses = downStairs;
        this.levelIndex = levelIndex;
        this.startPos = startPos;
        levUp = NUMBER_OF_LEVELS - 4;
        levTrap = levelIndex;
        riga = new ArrayList<>();
        upStairses = new ArrayList<>();
        traps = new ArrayList<>();
        createLevel();
    }

    private void createLevel()
    {

        for(int i = 0 ; i < MAP_DIMENSION; i++)
        {
            ArrayList<Luogo> colonna = new ArrayList<>();
            for(int j = 0; j < MAP_DIMENSION; j++)
                colonna.add(new Normal(i ,j ,"Void"));
            riga.add(colonna);
        }

        if(levelIndex==1) {
            start = new Start(startPos.x, startPos.y, getPlaceName());
            riga.get(startPos.x).set(startPos.y, start);
        }


        if(levelIndex!=10) {

            for (int i = 0; i < levUp; i++) {
                int x = Utility.getRandomNumber();
                int y = Utility.getRandomNumber();
                if (!(riga.get(x).get(y) instanceof Normal))
                    i--;
                else {
                    UpStairs temp = new UpStairs(x, y, getPlaceName());
                    riga.get(temp.getPosition().x).set(temp.getPosition().y, temp);
                    upStairses.add(temp);
                }
            }
        }
        else
        {
            int x,y;
            do
            {
                x = Utility.getRandomNumber();
                y = Utility.getRandomNumber();

            }while(!(riga.get(x).get(y) instanceof Normal));

            goal = new Goal(x,y,getPlaceName());
            riga.get(x).set(y,goal);

        }

        for(int i = 0; i < levTrap; i++)
        {
            int x = Utility.getRandomNumber();
            int y = Utility.getRandomNumber();
            if(!(riga.get(x).get(y) instanceof Normal))
                i--;
            else
            {
                Trap temp = new Trap(x,y,getPlaceName());
                riga.get(temp.getPosition().x).set(temp.getPosition().y, temp);
                traps.add(temp);
            }
        }
        
        if(downStairses != null)
        {
        	for(int i = 0; i < downStairses.size(); i++)
            {
            	int x = downStairses.get(i).getPosition().x;
            	int y = downStairses.get(i).getPosition().y;
            	
            	if(!(riga.get(x).get(y) instanceof Start || riga.get(x).get(y) instanceof UpStairs))
            		riga.get(x).set(y, downStairses.get(i));
            	
            }
        }

        linkPoint();

    }

    private void linkPoint()
    {
        if(levelIndex!=10) {
            for (UpStairs temp : upStairses)
                placesConnectionPath(temp);
        }
        else
        {
            placesConnectionPath(goal);
        }
        for(Trap temp : traps)
            placesConnectionPath(temp);
        
        if(downStairses != null)
            for(DownStairs temp : downStairses)
                placesConnectionPath(temp);

    }

    private void placesConnectionPath(Luogo temp)
    {
        int x = temp.getPosition().x - startPos.x;
        int y = temp.getPosition().y - startPos.y;

        if(x < 0)
            generalConnectionPathX(temp.getPosition().x,startPos.x);
        else
            generalConnectionPathX(startPos.x+1,temp.getPosition().x);

        if(y < 0)
            generalConnectionPathY(temp.getPosition().y+1,startPos.y,temp.getPosition().x);
        else
            generalConnectionPathY(startPos.y+1,temp.getPosition().y,temp.getPosition().x);
    }

    private void generalConnectionPathX(int xIn ,int xFin)
    {
        for(int i = xIn; i < xFin; i++)
        {
            if(riga.get(i).get(startPos.y).getTag().equals("N"))
                riga.get(i).set(startPos.y, new Pass(i ,startPos.y,getPlaceName()));
        }
    }

    private void generalConnectionPathY(int yIn ,int yFin, int X)
    {
        for(int i = yIn; i < yFin; i++)
        {
            if(riga.get(X).get(i).getTag().equals("N"))
                riga.get(X).set(i, new Pass(X ,i,getPlaceName()));
        }
    }

    public boolean isGoal(Point point)
    {
        return riga.get(point.x).get(point.y).getTag()=="G";
    }

    public ArrayList<UpStairs> getUpStairses()
    {
        return upStairses;
    }

    public boolean isPossibleGoesThisWay(int way, Point currentPos)
    {
        switch (way)
        {
            case NORD:
                if(currentPos.x>0)
                    return walkableIstance(currentPos.x-1,currentPos.y);
                else
                    return false;
            case SUD:
                if(currentPos.x<19)
                    return walkableIstance(currentPos.x+1,currentPos.y);
                else
                    return false;
            case EST:
                if(currentPos.y<19)
                    return walkableIstance(currentPos.x,currentPos.y+1);
                else
                    return false;
            case OVEST:
                if(currentPos.y>0)
                    return walkableIstance(currentPos.x,currentPos.y-1);
                else
                    return false;
            case UP:
                return riga.get(currentPos.x).get(currentPos.y) instanceof UpStairs;

            case DOWN:
                return riga.get(currentPos.x).get(currentPos.y) instanceof DownStairs;

            default:
                return false;
        }
    }
    public String getNameFromPosition(Point point)
    {
        return riga.get(point.x).get(point.y).getName();
    }
    private boolean walkableIstance(int x, int y)
    {
        return riga.get(x).get(y) instanceof Pass || riga.get(x).get(y) instanceof UpStairs || riga.get(x).get(y) instanceof Trap || riga.get(x).get(y) instanceof DownStairs || riga.get(x).get(y) instanceof Start;
    }
    
    public ArrayList<ArrayList<Luogo>> getRiga()
    {
    	return riga;
    }

    public void playerIsPassedHere(Point point)
    {
        riga.get(point.x).get(point.y).alreadyPassed();
    }

    public String toString(Point point) {
        String map = "";

        for (int i = 0; i < boardDim; i++){
            for (int j = 0; j < boardDim; j++) {
                if (i == point.x && j == point.y)
                	map += "P";
//                    map += (char)0 + "" + (char)0;
                else {
                    if(riga.get(i).get(j).isPassed() && riga.get(i).get(j) instanceof Trap)
                    	map += "T";
                	if (riga.get(i).get(j).isPassed())
                        map += " ";
                    else
                    	map +="*";
//                        map += "██";
                }
                map += " ";
            }
            map+="\n";
        }

        return map;
    }

}

