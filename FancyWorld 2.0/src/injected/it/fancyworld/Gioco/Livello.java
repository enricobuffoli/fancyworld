package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Gioco.Luoghi.*;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Chiave.Chiave;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.DownStairs;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.LuogoBloccato;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Pass;
import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.UpStairs;
import injected.it.fancyworld.Utils.Utility;

import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static injected.it.fancyworld.Utils.Utility.*;

/**
 * Created by enrico on 05/10/17.
 */

public class Livello
{
    private ArrayList<ArrayList<Luogo>> riga;
    private int levelIndex,levUp,levTrap,levDoor;
    private Point startPos,startUp;
    private ArrayList<UpStairs> upStairses;
    private ArrayList<DownStairs> downStairses;
    private ArrayList<Trap> traps;
    private List<LuogoBloccato> possibleWays;
    private Start start;
    private Goal goal;
    private UpStairs unlockedUp;
    private HashMap<UpStairs,Chiave> keys;
    private final int boardDim = 20;
    private HashMap<UpStairs,ArrayList<LuogoBloccato>> passageX ,passageY ;
    private final int backward = -1, forward = 1, ableToMove = 0, unableToMove = 1, wall = 2;

    public Livello(int levelIndex, Point startPos, ArrayList<DownStairs> downStairs, Point startUp)
    {
        passageX = new HashMap<>();
        passageY = new HashMap<>();
        possibleWays = new ArrayList<>();
        keys = new HashMap<>();
        if(downStairs!=null)
        	this.downStairses = downStairs;
        this.levelIndex = levelIndex;
        this.startPos = startPos;
        this.startUp = startUp;
        levUp = NUMBER_OF_LEVELS - 4;
        levTrap = levelIndex;
        levDoor = levUp;
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

        if(levelIndex==1)
        {
            start = new Start(startPos.x, startPos.y, getPlaceName());
            riga.get(startPos.x).set(startPos.y, start);
        }


        if(levelIndex!=10)
        {

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

        Lock l = new ReentrantLock();
        l.lock();
        try
        {
            linkPoint();
        }
        finally
        {
            l.unlock();
        }

        if(levelIndex!=10)
            setLock();

    }

    private void setLock()
    {
        for(int i=0;i<levUp;i++)
        {
            ArrayList<LuogoBloccato> temp = passageY.get(upStairses.get(i));
            if ((startUp != null && !passageY.get(upStairses.get(i)).contains(riga.get(startUp.x).get(startUp.y))) || startUp==null) {
                if (temp.size() == 0) {
                    upStairses.get(i).lock();
                    keys.put(upStairses.get(i), upStairses.get(i).getChiaveDiChiusura());
                } else {
                    int b = Utility.getRandomBetween2(temp.size() - 1);
                    if (temp.get(b).isLocked())
                        i--;
                    else {
                        temp.get(b).lock();
                        keys.put(upStairses.get(i), temp.get(b).getChiaveDiChiusura());
                    }
                }
            }
            else
                i--;
        }
        getPossibleWays();

        generateKey();
    }
    private void getPossibleWays()
    {
        for(UpStairs temp: upStairses)
            for(int i = 0; i<passageY.get(temp).size();i++)
                if(passageY.get(temp).get(i).isLocked() || i+1 == passageY.get(temp).size()) {
                    possibleWays.addAll(passageX.get(temp));
                    possibleWays.addAll(passageY.get(temp).subList(0,i));
                    break;
                }
    }

    private void generateKey()
    {
        int a = Utility.getRandomBetween2(upStairses.size()-1);
        unlockedUp = upStairses.get(a);
        int b = Utility.getRandomBetween2(possibleWays.size()-1);
        possibleWays.get(b).generateKey(keys.get(unlockedUp).toString());
    }
    public Point getUnlockedUpPos()
    {
        return unlockedUp.getPosition();
    }
    private void linkPoint()
    {
        if(levelIndex != 10)
            for(UpStairs temp : upStairses)
                placesConnectionPath(temp);
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
        if(temp instanceof UpStairs) {
            ArrayList<LuogoBloccato> tempArray = new ArrayList<>();
            if (x < 0)
                passageX.put((UpStairs) temp, generalConnectionPathX(startPos.x, Math.abs(x)+1,backward));
            else
                passageX.put((UpStairs) temp, generalConnectionPathX(startPos.x, Math.abs(x)+1,forward));

            if (y < 0)
                passageY.put((UpStairs) temp, generalConnectionPathY(startPos.y-1, Math.abs(y), backward, temp.getPosition().x));
            else
                passageY.put((UpStairs) temp, generalConnectionPathY(startPos.y+1, Math.abs(y), forward, temp.getPosition().x));

        }
        else
        {
            if (x < 0)
                generalConnectionPathX(startPos.x, Math.abs(x),backward);
            else
                generalConnectionPathX(startPos.x, Math.abs(x),forward);

            if (y < 0)
                generalConnectionPathY(startPos.y, Math.abs(y), backward, temp.getPosition().x);
            else
                generalConnectionPathY(startPos.y, Math.abs(y), forward, temp.getPosition().x);

        }
    }

    private ArrayList<LuogoBloccato> generalConnectionPathX(int xIn , int x, int direction)
    {
        ArrayList<LuogoBloccato> temp = new ArrayList<>();
        for(int i = 0; i < x; i++) {

            int c = i;
            i = (int) Math.copySign(i,direction);
            if (riga.get(xIn+i).get(startPos.y).getTag().equals("N")) {
                Pass tempPass = new Pass(xIn+i, startPos.y, getPlaceName());
                riga.get(xIn+i).set(startPos.y, tempPass);
                temp.add(tempPass);
            }
            else if (riga.get(xIn+i).get(startPos.y).getTag().equals("P")) {
                temp.add((Pass) riga.get(xIn+i).get(startPos.y));
            }
            i = c;
        }
        return temp;
    }

    private ArrayList<LuogoBloccato> generalConnectionPathY(int yIn , int y, int direction, int xStart)
    {
        ArrayList<LuogoBloccato> temp = new ArrayList<>();
        for(int i = 0; i < y; i++)
        {
            int c = i;
            i = (int) Math.copySign(i,direction);
            if(riga.get(xStart).get(yIn+i).getTag().equals("N"))
            {
                Pass tempPass =  new Pass(xStart, yIn+i, getPlaceName());
                riga.get(xStart).set(yIn+i,tempPass);
                temp.add(tempPass);
            }
            else if (riga.get(xStart).get(yIn+i).getTag().equals("P")) {
                temp.add((Pass) riga.get(xStart).get(yIn+i));
            }
            i = c;
        }
        return temp;
    }

    public boolean isGoal(Point point)
    {
        return riga.get(point.x).get(point.y).getTag()=="G";
    }

    public Luogo getCurrentPlace(Point point)
    {
        return riga.get(point.x).get(point.y);
    }

    public boolean isKeyPlace(Point point)
    {
        return riga.get(point.x).get(point.y).getTag() == "L";
    }

    public ArrayList<UpStairs> getUpStairses()
    {
        return upStairses;
    }



    public int isPossibleGoesThisWay(int way, Point currentPos)
    {
        //Gli passo la posizione prima dello spostamento
        int result = 7;
        //Numero casuale, sono sicuro di sovrascriverlo, ma Java segnava che la variabile, se non istanziata, poteva non avere valore

        //Verifico che la posizione di destinazione sai raggiungibile

        switch(way)
        {
            case NORD:
                if(currentPos.x > 0)
                    result = walkableIstance(currentPos.x - 1, currentPos.y);
                break;
            case SUD:
                if(currentPos.x < 19)
                    result = walkableIstance(currentPos.x + 1, currentPos.y);
                break;
            case EST:
                if(currentPos.y < 19)
                    result = walkableIstance(currentPos.x,currentPos.y + 1);
                break;
            case OVEST:
                if(currentPos.y > 0)
                    result = walkableIstance(currentPos.x,currentPos.y - 1);
                break;
            default:
                result = walkableIstance(currentPos.x ,currentPos.y);
                break;
        }
        return result;
    }

    public String getNameFromPosition(Point point)
    {
        return riga.get(point.x).get(point.y).getName();
    }

    private int walkableIstance(int x, int y)
    {
        if(riga.get(x).get(y) instanceof LuogoBloccato && riga.get(x).get(y).isLocked())
            return unableToMove;
        else if(riga.get(x).get(y) instanceof Normal)
            return wall;
        else
            return ableToMove;
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
            for (int j = 0; j < boardDim; j++)
            {
                if (i == point.x && j == point.y)
                	map += "C";
//                    map += (char)0 + "" + (char)0;
                else
                {
                    if(riga.get(i).get(j) instanceof LuogoBloccato && riga.get(i).get(j).isLocked())
                        map+="L";
                    else
                        map+=riga.get(i).get(j).getTag();
                }
                map += " ";
            }
            map+="\n";
        }

        return map;
    }

}

