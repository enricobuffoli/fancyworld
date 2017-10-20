package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import static injected.it.fancyworld.Utils.Utility.*;

/**
 * Created by enrico on 07/10/17.
 */
public class Gioco extends Thread
{
    private Giocatore giocatore;
    private Mondo world;
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
        giocatore = new Giocatore(world.getStartPos(), nomeGiocatore, null);
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

            if(world.getCurrentLevel().isPossibleGoesThisWay(way, giocatore.getPosition()) == 0)
            {
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
            }
            else if(world.getCurrentLevel().isPossibleGoesThisWay(way, giocatore.getPosition()) == 1)
            {
                int x = 0, y = 0;
                //Variabili istanziate con numeri casuali per evitare che Java segnalasse aventuali problemi

                switch(way)
                {
                    case NORD:
                        if(giocatore.getPosition().x > 0) {
                            x = giocatore.getPosition().x - 1;
                            y = giocatore.getPosition().y;
                        }
                        break;
                    case SUD:
                        if(giocatore.getPosition().x < 19) {
                            x = giocatore.getPosition().x + 1;
                            y = giocatore.getPosition().y;
                        }
                        break;
                    case EST:
                        if(giocatore.getPosition().y < 19) {
                            x = giocatore.getPosition().x;
                            y = giocatore.getPosition().y + 1;
                        }
                        break;
                    case OVEST:
                        if(giocatore.getPosition().y > 0) {
                            x = giocatore.getPosition().x;
                            y = giocatore.getPosition().y - 1;
                        }
                        break;
                    default:
                        x = giocatore.getPosition().x;
                        y = giocatore.getPosition().y;
                        break;
                }

                //Questo Switch deve restituire le coordinate del luogo in cui c'è il blocco che mi impedisce il muovermi

                Luogo lockedPlace = world.getCurrentLevel().getRiga().get(x).get(y);

                System.out.println("Questo passaggio è bloccato dalla chiave " + lockedPlace.getChiaveDiChiusura().toString());

                if(giocatore.getChiave() != null)
                {
                    System.out.println("Vuoi tentare di aprire la porta? (Y/N)");
                    String temp = Utility.yN();
                    if(temp.equals("Y"))
                    {
                        if(giocatore.getChiave().toString().equals(lockedPlace.getChiaveDiChiusura().toString()))
                        {
                            lockedPlace.unlock();
                            System.out.println("Hai sbloccato il passaggio");
                        }
                        else
                            System.out.println("Non sei in possesso della chiave giusta");
                    }
                }
                else
                    System.out.println("Il giocatore non è in possesso di nessuna chiave");
            }
            else
                System.out.println(WALL);

            if(!(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getTipoLuogo().equals(Livello.TipoLuoghi.START)) && !(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getTipoLuogo().equals(Livello.TipoLuoghi.GOAL)))
            {
                if(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo() == null)
                {
                    System.out.println("Questo posto non ha chiavi");
                    if(giocatore.getChiave() != null)
                    {
                        System.out.println("Vuoi depositare la chiave di cui sei in possesso?");

                        String temp = Utility.yN();
                        if(temp.equals("Y")) {
                            world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).setChiaveDelLuogo(giocatore.getChiave());
                            giocatore.setChiave(null);
                        }
                    }
                    else
                        System.out.println("Il giocatore non possiede alcuna chiave");
                }
                else
                {
                    System.out.println("In questo luogo c'e' la chiave di " + world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo().toString() + "\nVuoi raccoglierla?");
                    String temp = Utility.yN();
                    if(temp.equals("Y"))
                    {
                        if(giocatore.getChiave() == null)
                            giocatore.setChiave(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo());
                        else
                            System.out.println("Devi prima depositare la chiave di cui sei in possesso");
                    }
                }

            }

            System.out.print(world.getCurrentLevel().toString(giocatore.getPosition()));

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
        giocatore = new Giocatore(world.getStartPos(), giocatore.getNome(), null);
        System.out.println("Benvenuto " + giocatore.getNome() + " in Fancy World.");
        System.out.println("Il luogo di Goal si trova al livello 10.");
    }
}
