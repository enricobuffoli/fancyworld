package injected.it.fancyworld.Gioco;

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
    private ArrayList<Luogo> upStairses, traps, downStairses;
    private Luogo start, goal;
    private final int boardDim = 20;

    public enum TipoLuoghi
    {
        DOWN, GOAL, NORMAL, PASS, START, TRAP, UP;
    }

    public Livello(int levelIndex, Point startPos, ArrayList<Luogo> downStairs)
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
            for(int j = 0; j < MAP_DIMENSION; j++) {
                Luogo temp = new Luogo(i, j, "void", "N", TipoLuoghi.NORMAL);
                colonna.add(temp);
            }
            riga.add(colonna);
        }

        if(levelIndex == 1)
        {
            start = new Luogo(startPos.x, startPos.y, getPlaceName(), "S", TipoLuoghi.START);
            riga.get(startPos.x).set(startPos.y, start);
        }


        if(levelIndex!=10) {

            for (int i = 0; i < levUp; i++) {
                int x = Utility.getRandomNumber();
                int y = Utility.getRandomNumber();
                if (!(riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.NORMAL)))
                    i--;
                else {
                    Luogo temp = new Luogo(x, y, getPlaceName(), "U", TipoLuoghi.UP);
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

            }while(!(riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.NORMAL)));

            goal = new Luogo(x,y,getPlaceName(), "G", TipoLuoghi.GOAL);
            riga.get(x).set(y,goal);

        }

        for(int i = 0; i < levTrap; i++)
        {
            int x = Utility.getRandomNumber();
            int y = Utility.getRandomNumber();
            if(!(riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.NORMAL)))
                i--;
            else
            {
                Luogo temp = new Luogo(x,y,getPlaceName(), "T", TipoLuoghi.TRAP);
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
            	
            	if(!(riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.START) || riga.get(x).get(y).equals(TipoLuoghi.UP)))
            		riga.get(x).set(y, downStairses.get(i));
            	
            }
        }

        linkPoint();

    }

    private void linkPoint()
    {
        if(levelIndex!=10) {
            for (Luogo temp : upStairses)
                placesConnectionPath(temp);
        }
        else
        {
            placesConnectionPath(goal);
        }
        for(Luogo temp : traps)
            placesConnectionPath(temp);
        
        if(downStairses != null)
            for(Luogo temp : downStairses)
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
                riga.get(i).set(startPos.y, new Luogo(i ,startPos.y,getPlaceName(), "P", TipoLuoghi.PASS));
        }
    }

    private void generalConnectionPathY(int yIn ,int yFin, int X)
    {
        for(int i = yIn; i < yFin; i++)
        {
            if(riga.get(X).get(i).getTag().equals("N"))
                riga.get(X).set(i, new Luogo(X ,i,getPlaceName(), "P", TipoLuoghi.PASS));
        }
    }

    public boolean isGoal(Point point)
    {
        return riga.get(point.x).get(point.y).getTag()=="G";
    }

    public ArrayList<Luogo> getUpStairses()
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
                return riga.get(currentPos.x).get(currentPos.y).getTipoLuogo().equals(TipoLuoghi.UP);

            case DOWN:
                return riga.get(currentPos.x).get(currentPos.y).getTipoLuogo().equals(TipoLuoghi.DOWN);

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
        return riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.PASS) || riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.UP) || riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.TRAP) || riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.DOWN) || riga.get(x).get(y).getTipoLuogo().equals(TipoLuoghi.START);
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
                else {
                    if(riga.get(i).get(j).isPassed() && riga.get(i).get(j).getTipoLuogo().equals(TipoLuoghi.TRAP))
                        map += "T";
                    else if(riga.get(i).get(j).isPassed() && riga.get(i).get(j).getTipoLuogo().equals(TipoLuoghi.UP))
                        map += "U";
                    else if(riga.get(i).get(j).isPassed() && riga.get(i).get(j).getTipoLuogo().equals(TipoLuoghi.DOWN))
                        map += "D";
                    else if(riga.get(i).get(j).isPassed() && riga.get(i).get(j).getTipoLuogo().equals(TipoLuoghi.START))
                        map += "S";
                    else if(riga.get(i).get(j).isPassed() && riga.get(i).get(j).getTipoLuogo().equals(TipoLuoghi.GOAL))
                        map += "G";
                    else if(riga.get(i).get(j).isPassed() && riga.get(i).get(j).getTipoLuogo().equals(TipoLuoghi.PASS))
                        map += " ";
                    else
                        map +="*";
                }
                map += " ";
            }
            map+="\n";
        }

        return map;
    }

}

