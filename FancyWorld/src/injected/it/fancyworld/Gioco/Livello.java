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
    private int levelIndex,levUp,levTrap,connection;
    private Point startPos;
    private ArrayList<UpStairs> upStairses;
    private ArrayList<DownStairs> downStairses;
    private ArrayList<Trap> traps;
    private Start start;

    public Livello(int levelIndex, Point startPos, ArrayList<DownStairs> downStairs)
    {
        if(downStairs!=null)
        	this.downStairses = downStairs;
        this.levelIndex = levelIndex;
        this.startPos = startPos;
        levUp = NUMBER_OF_LEVELS - levelIndex - 4;
        levTrap = levelIndex;
        connection = levelIndex + 1;
        riga = new ArrayList<>();
        upStairses = new ArrayList<>();
        traps = new ArrayList<>();
        createLevel();
    }

    private void createLevel()
    {

        //Creazione Livelli

        for(int i = 0 ; i < MAP_DIMENSION; i++)
        {
            ArrayList<Luogo> colonna = new ArrayList<>();
            for(int j = 0; j < MAP_DIMENSION; j++)
                colonna.add(new Normal(i ,j ,"Void","N"));
            riga.add(colonna);
        }

        start = new Start(startPos.x,startPos.y,getPlaceName(),"S");
        riga.get(startPos.x).set(startPos.y, start);

        for(int i = 0; i < levUp; i++)
        {
            int x = Utility.getRandomNumber();
            int y = Utility.getRandomNumber();
            if(!(riga.get(x).get(y) instanceof Normal))
                i--;
            else
            {
                UpStairs temp = new UpStairs(x,y,getPlaceName(),"U");
                riga.get(temp.getPosition().x).set(temp.getPosition().y, temp);
                upStairses.add(temp);
            }
        }

        for(int i = 0; i < levTrap; i++)
        {
            int x = Utility.getRandomNumber();
            int y = Utility.getRandomNumber();
            if(!(riga.get(x).get(y) instanceof Normal))
                i--;
            else
            {
                Trap temp = new Trap(x,y,getPlaceName(),"T");
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
            	{
            		DownStairs temp = new DownStairs(x, y, getPlaceName(), "D");
            		riga.get(temp.getPosition().x).set(temp.getPosition().y, temp);
            	}
            }
        }

        linkPoint();

        for(int i = 0;i < 20; i++) {
            for (int j = 0; j < 20; j++)
                System.out.print(riga.get(i).get(j).getTag() + " ");
            System.out.println();
        }
    }

    private void linkPoint()
    {
        for(UpStairs temp : upStairses)
        {
            int x = temp.getPosition().x - startPos.x;
            int y = temp.getPosition().y - startPos.y;

            if(x < 0)
                for(int i = temp.getPosition().x; i < startPos.x; i++)
                {
                    if(riga.get(i).get(startPos.y).getTag().equals("N"))
                        riga.get(i).set(startPos.y, new Pass(i ,startPos.y,getPlaceName(),"P"));
                }
            else
            {	for(int i = startPos.x + 1; i <= temp.getPosition().x; i++)
            {
                if(riga.get(i).get(startPos.y).getTag().equals("N"))
                    riga.get(i).set(startPos.y, new Pass(i, startPos.y,getPlaceName(),"P"));
            }
            }

            if(y < 0)
            {
                for(int i = temp.getPosition().y ; i < startPos.y; i++)
                {
                    if(riga.get(temp.getPosition().x).get(i).getTag().equals("N"))
                        riga.get(temp.getPosition().x).set(i, new Pass(temp.getPosition().x ,i,getPlaceName(),"P"));
                }
            }
            else
            {
                for(int i = startPos.y; i < temp.getPosition().y; i++)
                {
                	if(riga.get(temp.getPosition().x).get(i).getTag().equals("N"))
                        riga.get(temp.getPosition().x).set(i, new Pass(temp.getPosition().x, i,getPlaceName(),"P"));
                }
            }

        }

        for(Trap temp : traps)
        {
            int x = temp.getPosition().x - startPos.x;
            int y = temp.getPosition().y - startPos.y;

            if(x < 0)
                for(int i = temp.getPosition().x; i < startPos.x; i++)
                {
                    if(!SPECIAL_TAG_VALUE.contains(riga.get(i).get(startPos.y).getTag()))
                        riga.get(i).set(startPos.y, new Pass(i ,startPos.y,getPlaceName(),"P"));
                }
            else
            {	for(int i = startPos.x + 1; i <= temp.getPosition().x; i++)
            {
                if(!SPECIAL_TAG_VALUE.contains(riga.get(i).get(startPos.y).getTag()))
                    riga.get(i).set(startPos.y, new Pass(i, startPos.y,getPlaceName(),"P"));
            }
            }

            if(y < 0)
            {
                for(int i = temp.getPosition().y ; i < startPos.y; i++)
                {
                    if(!SPECIAL_TAG_VALUE.contains(riga.get(i).get(startPos.y).getTag()))
                        riga.get(temp.getPosition().x).set(i, new Pass(temp.getPosition().x ,i,getPlaceName(),"P"));
                }
            }
            else
            {
                for(int i = startPos.y; i < temp.getPosition().y; i++)
                {
                    if(!SPECIAL_TAG_VALUE.contains(riga.get(i).get(startPos.y).getTag()))
                        riga.get(temp.getPosition().x).set(i, new Pass(temp.getPosition().x, i,getPlaceName(),"P"));
                }
            }
        }
        
        if(downStairses != null)
        {
        	for(DownStairs temp : downStairses)
            {
                int x = temp.getPosition().x - startPos.x;
                int y = temp.getPosition().y - startPos.y;
                
                if(x < 0)
                    for(int i = temp.getPosition().x; i < startPos.x; i++)
                    {
                        if(riga.get(i).get(startPos.y).getTag().equals("N"))
                            riga.get(i).set(startPos.y, new DownStairs(i ,startPos.y,getPlaceName(),"P"));
                    }
                else
                {	for(int i = startPos.x + 1; i <= temp.getPosition().x; i++)
                {
                    if(riga.get(i).get(startPos.y).getTag().equals("N"))
                        riga.get(i).set(startPos.y, new DownStairs(i ,startPos.y,getPlaceName(),"P"));
                }
                }

                if(y < 0)
                {
                    for(int i = temp.getPosition().y ; i < startPos.y; i++)
                    {
                        if(riga.get(temp.getPosition().x).get(i).getTag().equals("N"))
                            riga.get(temp.getPosition().x).set(i, new DownStairs(temp.getPosition().x ,i,getPlaceName(),"P"));
                    }
                }
                else
                {
                    for(int i = startPos.y; i < temp.getPosition().y; i++)
                    {
                    	if(riga.get(temp.getPosition().x).get(i).getTag().equals("N"))
                            riga.get(temp.getPosition().x).set(i, new DownStairs(temp.getPosition().x ,i,getPlaceName(),"P"));
                    }
                }

        }}
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
        System.out.println(x+" "+y+"  "+riga.get(x).get(y).getTag());
        return riga.get(x).get(y) instanceof Pass || riga.get(x).get(y) instanceof UpStairs || riga.get(x).get(y) instanceof Trap || riga.get(x).get(y) instanceof DownStairs || riga.get(x).get(y) instanceof Start;
    }
    
    public ArrayList<ArrayList<Luogo>> getRiga()
    {
    	return riga;
    }
}

