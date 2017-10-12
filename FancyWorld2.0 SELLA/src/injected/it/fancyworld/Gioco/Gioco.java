package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import static injected.it.fancyworld.Utils.Utility.*;

/**
 * Created by enrico on 07/10/17.
 */
public class Gioco extends Thread
{
    private Giocatore giocatore;
//    private ArrayList<Livello> livelli;
    private Mondo world;
//    private int currentLevel;
    private boolean isFinished;
    private static Gioco istanza = null;

    private Gioco()
    {
        System.out.println("Uscire (0), Giocare (qualsiasi altro tasto)");
        String decision = Utility.generalKeyInput();
        String nomeGiocatore = "";
        if(decision.equals("0"))
            System.exit(0);
        else
        {
            System.out.println("Inserisci il tuo nickname:");
            nomeGiocatore = Utility.generalKeyInput();
        }
        world = new Mondo();
//        Point startPos = new Point(9,9);
        giocatore = new Giocatore(world.getStartPos(), nomeGiocatore);
//        livelli = new ArrayList<>();
//        livelli.add(new Livello(1,startPos,null));
//        currentLevel = 1;
        isFinished = false;
        System.out.println("Benvenuto " + nomeGiocatore + " in Fancy World.");
        System.out.println("Il luogo di Goal si trova al livello 10.");
        super.start();
    }
    
    public static Gioco getGioco()
    {
        if(istanza==null)
        {
            istanza = new Gioco();
        }

        return istanza;
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            world.getCurrentLevel().playerIsPassedHere(giocatore.getPosition());
            System.out.println("Ora tu sei al livello " + world.getIndexCurrentLevel());
            System.out.println(TI_TROVI + world.getCurrentLevel().getNameFromPosition(giocatore.getPosition()));
            System.out.println("Premi (N,S,E,W,U,D) per spostarti. (-1 per terminare la sessione di gioco).");
            int way = Utility.fromStringToIntMove(Utility.keyInput());

            if(way==EXIT) {
                String temp = Utility.yN();
                if(temp.equals("Y"))
                    System.exit(0);
                else
                {
                    System.out.println("Ora tu sei al livello " + world.getIndexCurrentLevel());
                    System.out.println(TI_TROVI + world.getCurrentLevel().getNameFromPosition(giocatore.getPosition()));
                    System.out.println("Premi (N,S,E,W,U,D) per spostarti. (-1 per terminare la sessione di gioco).");
                    way = Utility.fromStringToIntMove(Utility.keyInput());
                }

            }

            System.out.print("\033[H\033[2J");
            /*
             * Commentato perché con Eclipse mi stampava ignorantemente questa stringa e non eseguiva il refresh
             */

            if(world.getCurrentLevel().isPossibleGoesThisWay(way, giocatore.getPosition()))
            {
                if(way == UP)
                {
                    /*if(world.getLevels().size() < world.getIndexCurrentLevel() + 1)
                    	/*world.nextLevel(giocatore.getPosition());*/
//                    {
//                        
//                    	
//                    	ArrayList<UpStairs> temp1 = world.getLevel(currentLevel - 1).getUpStairses();
//                        ArrayList<DownStairs> temp2 = new ArrayList<>();
//                        for (UpStairs temp3 : temp1)
//                            temp2.add(fromUpToDown(temp3));
//
//                        livelli.add(new Livello(currentLevel+1, giocatore.getPosition(), temp2));
//                    }

//                    ++currentLevel;
                    world.previousIncrementationLevel();
                }
                else if(world.getIndexCurrentLevel() - 2 >= 0 && way == DOWN)
                {
                	world.latterDecrementationLevel();
                }
                else
                    giocatore.move(way);

                System.out.print(world.getCurrentLevel().toString(giocatore.getPosition()));


            }
            else
                System.out.println(WALL);

            if(world.getCurrentLevel().isGoal(giocatore.getPosition()))
            {
                System.out.println("Complimenti hai trovato il luogo di Goal e sei riuscito a concludere il gioco.\n Vuoi fare una altra partita? (Y/N)");
                String temp = Utility.yN();
                if(temp.equals("N")) {
                    System.out.println("Confermi la tua scelta? (Y/N)");
                    String temp2 = Utility.yN();
                    if(temp2.equals("Y"))
                        System.exit(0);
                    else
                        resetGame();

                }
                else if(temp.equals("Y")){
                    System.out.println("Confermi la tua scelta? (Y/N)");
                    String temp2 = Utility.yN();
                    if(temp2.equals("N"))
                        System.exit(0);
                    else
                        resetGame();
                }
            }
        }
    }

    private void resetGame()
    {
        world = new Mondo();
        String name =  giocatore.getNome();
//        Point startPos = new Point(9,9);
        giocatore = new Giocatore(world.getStartPos(), giocatore.getNome());
//        livelli = new ArrayList<>();
//        livelli.add(new Livello(1,startPos,null));
//        currentLevel = 1;
        System.out.println("Benvenuto " + giocatore.getNome() + " in Fancy World.");
        System.out.println("Il luogo di Goal si trova al livello 10.");
    }
}
