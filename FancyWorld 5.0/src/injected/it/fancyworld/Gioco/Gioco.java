package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import java.awt.*;

import static injected.it.fancyworld.Utils.Utility.*;
import static injected.it.fancyworld.Utils.WorldConfig.*;

/**
 * Created by enrico on 07/10/17.
 */
public class Gioco extends Thread
{
    private Giocatore giocatore = null;
    private Mondo world = null;
    private static Gioco istanza = null;
    public static final int LIM_CHIAVI = gameValue[MAX_NUMBER_OF_KEYS], LIM_PESO = gameValue[MAX_KEYS_WEIGHT], LIM_INF_PUNTEGGIO = 0, PUNTEGGIO_MINIMO_VITTORIA = gameValue[WIN_POINTS];

    public enum Sign
    {
        MINUS, PLUS;
    }

    private Gioco()
    {
        System.out.println("Uscire (0), Giocare (qualsiasi altro tasto)");
        String decision = Utility.generalKeyInput();
        if(decision.equals("0"))
            System.exit(0);
        else
        {
            while(world == null && giocatore == null)
            {
                System.out.println("Vuoi caricare i dati precedentemente salvati? (Y/N)");
                String input = Utility.yN();
                if(input.equals("Y"))
                {
                    world = Utility.loadTheWorld();
                    giocatore = Utility.loadThePg();
                }
                else
                {
                    String nomeGiocatore = "";
                    System.out.println("Inserisci il tuo nickname:");
                    nomeGiocatore = Utility.generalKeyInput();
                    world = new Mondo();
                    giocatore = new Giocatore(world.getStartPos(), nomeGiocatore);
                    System.out.println("Benvenuto " + nomeGiocatore + " "+worlds.get(worldIndex));
                    System.out.println("Il luogo di Goal si trova al livello 10.");
                    System.out.println("Il punteggio minimo per concludere la partita, una volta raggiunto il Goal, è pari a " + PUNTEGGIO_MINIMO_VITTORIA);
                }
            }
        }

        super.start();
    }
    
    public static Gioco getGioco()
    {
        if(istanza == null)
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
            System.out.print(world.getCurrentLevel().toString(giocatore.getPosition()));
            world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).giocatoreNelLuogo(giocatore);
            int way = motionInput();
            switch (way)
            {
                case EXIT:
                    System.out.println("Confermi la tua scelta? (Y/N)");
                    String temp = Utility.yN();
                    if(temp.equals("Y"))
                        System.exit(0);
                    break;
                case LAY_DOWN:
                    if(!(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getTipoLuogo().equals(Livello.TipoLuoghi.START)) && !(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getTipoLuogo().equals(Livello.TipoLuoghi.GOAL))) {
                        giocatore.layChiave(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()));
                    }
                    else
                     System.out.println("Non puoi depositare la chiave");
                    break;
                case SAVE:
                    String decision = "";
                    System.out.println("Vuoi salvare la sessione attuale? (Y/N)");
                    decision = Utility.yN();
                    if(decision.equals("Y"))
                        Utility.saveTheGame(world, giocatore);
                    break;
                default:
                    switch(world.getCurrentLevel().isPossibleGoesThisWay(way, giocatore.getPosition()))
                    {
                        case 0:
                            if(way == UP)
                            {
                                world.previousIncrementationLevel();
                            }
                            else if(world.getIndexCurrentLevel() - 2 >= 0 && way == DOWN)
                            {
                                world.latterDecrementationLevel();
                            }
                            else
                                giocatore.move(way);
                            break;
                        case 1:
                            Point tempPoint = simulMotion(way,giocatore);
                            Luogo lockedPlace = world.getCurrentLevel().getRiga().get(tempPoint.x).get(tempPoint.y);
                            lockedPlace.tryToUnlock(giocatore);
                            break;
                        case 2:
                            System.out.println(WALL);
                            break;
                    }
                    break;

            }
            System.out.print("\033[H\033[2J");

            if(world.getCurrentLevel().isGoal(giocatore.getPosition()))
            {
                if(giocatore.getPunteggio() >= PUNTEGGIO_MINIMO_VITTORIA)
                {
                    System.out.println("Complimenti, hai trovato il luogo di Goal con un punteggio sufficiente a concludere la partita.\n Vuoi fare una altra partita? (Y/N)");
                    String temp = Utility.yN();
                    if (temp.equals("N")) {
                        System.out.println("Confermi la tua scelta? (Y/N)");
                        String temp2 = Utility.yN();
                        if (temp2.equals("Y"))
                            System.exit(0);
                        else
                            resetGame();

                    } else if (temp.equals("Y")) {
                        System.out.println("Confermi la tua scelta? (Y/N)");
                        String temp2 = Utility.yN();
                        if (temp2.equals("N"))
                            System.exit(0);
                        else
                            resetGame();
                    }
                }
                else
                {
                    System.out.println("Hai trovato il luogo di Goal, ma non possiedi un punteggio sufficiente a concludere la partita.\nDevi continuare ad esplorare questo mondo finché non raggiungerai un punteggio pari o superiore a " + PUNTEGGIO_MINIMO_VITTORIA);
                }
            }
        }

    }

    private void resetGame()
    {
        world = new Mondo();
        String name =  giocatore.getNome();
        giocatore = new Giocatore(world.getStartPos(), giocatore.getNome());
        System.out.println("Benvenuto " + giocatore.getNome() +" "+ worlds.get(worldIndex));
        System.out.println("Il luogo di Goal si trova al livello 10.");
        System.out.println("Il punteggio minimo per concludere la partita, una volta raggiunto il Goal, è pari a " + PUNTEGGIO_MINIMO_VITTORIA);
    }

    private int motionInput()
    {
        world.getCurrentLevel().playerIsPassedHere(giocatore.getPosition());
        System.out.println("Ora tu sei al livello " + world.getIndexCurrentLevel());
        System.out.println(TI_TROVI + world.getCurrentLevel().getNameFromPosition(giocatore.getPosition()));
        System.out.println("Al momento hai un punteggio pari a " + giocatore.getPunteggio());
        System.out.println("Premi (N,S,E,W,U,D) per spostarti (Q Depositare la chiave, A Salvare Gioco). (-1 per terminare la sessione di gioco).");
        int way = Utility.fromStringToIntMove(Utility.keyInput());
        return way;
    }
}
