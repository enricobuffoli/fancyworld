package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import java.awt.*;
import java.util.ArrayList;

import static injected.it.fancyworld.Utils.Utility.*;

/**
 * Created by enrico on 07/10/17.
 */
public class Gioco extends Thread
{
    private Giocatore giocatore;
    private ArrayList<Livello> livelli;
    private int currentLevel;
    private boolean isFinished;

    public Gioco(String nomeGiocatore)
    {
        Point startPos = new Point(9,9);
        giocatore = new Giocatore(startPos,nomeGiocatore);
        livelli = new ArrayList<>();
        livelli.add(new Livello(1,startPos,null));
        currentLevel = 1;
        isFinished = false;
        super.start();
    }

    @Override
    public void run()
    {
        while(!isFinished)
        {
            int way = Utility.fromStringToIntMove(Utility.keyInput());
            
            if(livelli.get(currentLevel-1).isPossibleGoesThisWay(way, giocatore.getPosition())) 
            {
                if(way == UP)
                {
                    if(livelli.size()<currentLevel+1) {
                        ArrayList<UpStairs> temp1 = livelli.get(currentLevel - 1).getUpStairses();
                        ArrayList<DownStairs> temp2 = new ArrayList<>();
                        for (UpStairs temp3 : temp1)
                            temp2.add(fromUpToDown(temp3));

                        livelli.add(new Livello(currentLevel+1, giocatore.getPosition(), temp2));
                    }
                    System.out.println("Nuovo Livello " + currentLevel+1);
                    for(int i = 0;i < 20; i++) {
                        for (int j = 0; j < 20; j++)
                            System.out.print(livelli.get(currentLevel).getRiga().get(i).get(j).getTag() + " ");
                        System.out.println();
                    }
                    ++currentLevel;
                }
                else if(currentLevel - 2 >= 0 && way == DOWN)
                {
                	currentLevel--;
                	for(int i = 0;i < 20; i++) {
                        for (int j = 0; j < 20; j++)
                            System.out.print(livelli.get(currentLevel - 1).getRiga().get(i).get(j).getTag() + " ");
                        System.out.println();
                	}
                }
                else 
                {
                    giocatore.move(way);
                    System.out.println(TI_TROVI + livelli.get(currentLevel - 1).getNameFromPosition(giocatore.getPosition()));
                }

            }
            else
                System.out.println(WALL);
        }


    }
}
