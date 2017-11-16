package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import static injected.it.fancyworld.Utils.Utility.*;

/**
 * Created by enrico on 07/10/17.
 */
public class Gioco extends Thread
{
    private Giocatore giocatore = null;
    private Mondo world = null;
    private static Gioco istanza = null;
    private final int LIM_CHIAVI = 5, LIM_PESO = 50;

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
                    System.out.println("Benvenuto, " + nomeGiocatore + ", nel mondo di Yormi.");
                    System.out.println("Il tuo obbiettivo consiste nel trovare lo scettro del potere, collocato in una stanza al decimo piano");
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
            world.getCurrentLevel().playerIsPassedHere(giocatore.getPosition());
            System.out.println("Ora tu sei al livello " + world.getIndexCurrentLevel());
            System.out.println(TI_TROVI + world.getCurrentLevel().getNameFromPosition(giocatore.getPosition()));
            System.out.println("Premi (N,S,E,W,U,D) per spostarti. (-1 per terminare la sessione di gioco).");
            int way = Utility.fromStringToIntMove(Utility.keyInput());

            if(way == EXIT)
            {
                System.out.println("Confermi la tua scelta? (Y/N)");
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

                if(giocatore.getChiavi().size() != 0)
                {
                    System.out.println("Vuoi tentare di aprire la porta? (Y/N)");
                    String temp = Utility.yN();
                    if(temp.equals("Y"))
                    {
                        System.out.println("Sei in possesso delle seguenti chiavi:");

                        int i = 0;
                        for (; i < giocatore.getChiavi().size(); i++)
                            System.out.println(i + 1 + ")Chiave di " + giocatore.getChiave(i));
                        System.out.println(i + 1 + ")Annulla\nQuale vuoi utilizzare?");

                        while(true)
                        {
                            int index = Utility.intKeyInput() - 1;
                            if (index > giocatore.getChiavi().size() || index < 0)
                                System.out.println("Hai inserito un valore non valido. Riseleziona");
                            else if(index == giocatore.getChiavi().size())
                            {
                                break;
                            }
                            else if (giocatore.getChiave(index).toString().equals(lockedPlace.getChiaveDiChiusura().toString()))
                            {
                                lockedPlace.unlock();
                                System.out.println("Hai sbloccato il passaggio");
                                break;
                            }
                            else
                            {
                                System.out.println("Non hai selezionato la chiave giusta.");
                            }
                        }
                    }
                }
                else
                    System.out.println("Il giocatore non possiede alcuna chiave");
            }
            else
                System.out.println(WALL);

            if(!(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getTipoLuogo().equals(Livello.TipoLuoghi.START)) && !(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getTipoLuogo().equals(Livello.TipoLuoghi.GOAL)))
            {
                if(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo() == null)
                {
                    System.out.println("Questo posto non ha chiavi");
                    if(giocatore.getChiavi().size() != 0)
                    {
                        System.out.println("Vuoi depositare la chiave di cui sei in possesso? (Y/N)");

                        String temp = Utility.yN();
                        if(temp.equals("Y"))
                        {
                            System.out.println("Sei in possesso delle seguenti chiavi:");
                            int i = 0;
                            for(; i < giocatore.getChiavi().size(); i++)
                                System.out.println(i + 1 + ")Chiave di " + giocatore.getChiave(i));
                            System.out.println(i + 1 + ")Annulla\nQuale vuoi depositare?");
                            int index = Utility.intKeyInput() - 1;
                            if(giocatore.getChiavi().size() != 0 && index != giocatore.getChiavi().size())
                            {
                                world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).setChiaveDelLuogo(giocatore.getChiave(index));
                                giocatore.getChiavi().remove(index);
                            }
                            else
                                System.out.println("Annullato");
                        }
                    }
                    else
                        System.out.println("Il giocatore non possiede chiavi da depositare");
                }
                else
                {
                    System.out.println("In questo luogo c'e' la chiave di " + world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo().toString() + " di peso " + world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo().getPeso()+ "\nVuoi raccoglierla? (Y/N)");
                    String temp = Utility.yN();
                    if(temp.equals("Y"))
                    {
                        System.out.println("Numero di chiavi che avresti: " + (giocatore.getChiavi().size() + 1));
                        if ((giocatore.getChiavi().size() + 1) <= LIM_CHIAVI)
                        {
                            System.out.println("Peso totale di chiavi in possesso: " + giocatore.calculateTotalWeight());
                            if ((giocatore.calculateTotalWeight() + world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).getChiaveDelLuogo().getPeso()) <= LIM_PESO)
                                giocatore.getChiavi().add(world.getCurrentLevel().getCurrentPlace(giocatore.getPosition()).pickUpKey());
                            else
                                System.out.println("Il peso totale che trasporteresti supererebbe il peso limite");
                        }
                        else
                            System.out.println("Il numero massimo di chiavi che trasporteresti supererebbe il numero massimo");
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
            else
            {
                String decision = "";
                System.out.println("Vuoi salvare la sessione attuale? (Y/N)");
                decision = Utility.yN();
                if(decision.equals("Y"))
                    Utility.saveTheGame(world, giocatore);
            }
        }

    }

    private void resetGame()
    {
        world = new Mondo();
        giocatore = new Giocatore(world.getStartPos(), giocatore.getNome());
        System.out.println("Benvenuto, " + giocatore.getNome() + ", nel mondo di Yormi.");
        System.out.println("Il tuo obbiettivo consiste nel trovare lo scettro del potere, collocato in una stanza al decimo piano");
    }
}
