package injected.it.fancyworld.Utils;

import injected.it.fancyworld.Gioco.Giocatore;
import injected.it.fancyworld.Gioco.Livello;
import injected.it.fancyworld.Gioco.Luogo;
import injected.it.fancyworld.Gioco.Mondo;

import java.awt.*;
import java.io.*;
import java.util.*;

import static injected.it.fancyworld.Utils.WorldConfig.*;


/**
 * Created by enrico on 05/10/17.
 */
public class Utility {

    public static final int EXIT = -1, ERROR = 0, NORMAL = 1, LIV_UP = 2, LIV_TRAP = 3, START = 4, PASS = 5, LIV_DOWN = 6, NUMBER_OF_LEVELS=8, MAP_DIMENSION = 20;
    public static final int UP = 0, DOWN = 1, NORD = 2, SUD = 3, OVEST = 4, EST = 5, SAVE = 6, LAY_DOWN=7;
    public static final String INPUT_VALUE = "-1NSEWUDQA", SPECIAL_TAG_VALUE = "UDTS", ERRORE_INPUT= "Hai selezionato un tasto non corretto, puoi solo selezione (N,S,E,W,U,D). Riprova.";
    public static final String Y_N = "YN";
    public static final String WALL= "Non puoi passare di qui, c'è un muro!";
    public static final String TI_TROVI = "Ti trovi nella contea di ";
    
    public static boolean randomChoice(int percentage)
    {
        return Math.random()*100 < percentage;
    }

    public static ArrayList<String> PLACES_NAMES, KEYS_NAMES;
    
    public static int getRandomNumber()
    {
        return (int) (Math.random() * 20);
    }


    private static Iterator keyIterator, placeIterator;

    public static int getRandomBetween2(int limit)
    {
        return (int) (Math.random() * limit);
    }

    public static String keyInput()
    {
        String s;
        do
        {
            Scanner input = new Scanner(System.in);
            s = input.nextLine();
            if(!INPUT_VALUE.contains(s) || s.length()==0)
                System.out.println(ERRORE_INPUT);
        }while(!INPUT_VALUE.contains(s) || s.length()==0);
        return s;
    }

    public static String generalKeyInput()
    {
        String s;
        do
        {
            Scanner input = new Scanner(System.in);
            s = input.nextLine();
            if(s.length()==0)
                System.out.println("Non hai premuto nessun tasto! Riprova.");
        }while(s.length()==0);
        return s;
    }

    public static String yN()
    {
        String s;
        do
        {
            Scanner input = new Scanner(System.in);
            s = input.nextLine();
            if(!Y_N.contains(s) || s.length()==0)
                System.out.println("Hai premuto un tasto non compreso tra le opzioni disponibili! Riprova.");
        }while(!Y_N.contains(s) || s.length()==0);
        return s;
    }

    public static void readPlacesFromFile()
    {
        PLACES_NAMES = new ArrayList<>();
        FileReader f = null;
        try {
            f = new FileReader("Nomi/nomi_luoghi.txt");
        } catch (FileNotFoundException e) {
            System.out.println("File contente i nomi dei luoghi non trovato.");
        }

        BufferedReader b;
        b = new BufferedReader(f);
        String s;
        try {
            while((s = b.readLine())!= null)
            {
                PLACES_NAMES.add(s);
            }
        } catch (IOException e) {
            System.out.println("C'è stato un errore nella lettura del file.");
        }

        placeIterator = PLACES_NAMES.iterator();

    }

    public static void readKeyNamesFromFile()
    {
        KEYS_NAMES = new ArrayList<>();
        FileReader f = null;
        try {
            f = new FileReader("Nomi/Chiavi/"+worlds.get(worldIndex)+".txt");
        } catch (FileNotFoundException e) {
            System.out.println("File contente i nomi delle chiavi non trovato.");
        }

        BufferedReader b;
        b = new BufferedReader(f);

        String s;
        try {
            while((s = b.readLine())!= null && KEYS_NAMES.size()<=gameValue[NUMBER_OF_KEY])
            {
                KEYS_NAMES.add(s);
            }
            if(KEYS_NAMES.size()<=gameValue[NUMBER_OF_KEY]) {
                System.out.println("Il numero di chiavi da te scelto è troppo grande, prima di mettere cosi tante chiavi aggiungi dei nomi al file txt");
                System.out.println("Per tanto per adesso prima della tua aggiunta verranno salvate " + KEYS_NAMES.size() + " chiavi");
            }

        } catch (IOException e) {
            System.out.println("C'è stato un errore nella lettura del file.");
        }
        keyIterator = KEYS_NAMES.iterator();
    }


    public static String getKeyName()
    {
        if(keyIterator.hasNext())
            return (String) keyIterator.next();
        else
        {
            keyIterator = KEYS_NAMES.iterator();
            return (String) keyIterator.next();
        }
    }

    public static String getPlaceName()
    {
        if(placeIterator.hasNext())
            return (String) placeIterator.next();

        placeIterator = PLACES_NAMES.iterator();
        return (String) placeIterator.next();
    }


    public static int fromStringToIntMove(String s)
    {
        switch (s)
        {
            case "N":
                return NORD;
            case "S":
                return SUD;
            case "E":
                return EST;
            case "W":
                return OVEST;
            case "U":
                return UP;
            case "D":
                return DOWN;
            case "-1":
                return EXIT;
            case "Q" :
                return LAY_DOWN;
            case "A":
                return SAVE;
            default:
                return ERROR;
        }
    }

    public static Luogo fromUpToDown(Luogo upStairs)
    {
        return new Luogo(upStairs.getPosition().x, upStairs.getPosition().y, getPlaceName(), "D", Livello.TipoLuoghi.DOWN);
    }

    public static void saveTheGame(Mondo world, Giocatore giocatore)
    {
        FileOutputStream fWorld = null;
        FileOutputStream fPg = null;
        ObjectOutputStream oosWorld = null;
        ObjectOutputStream oosPg = null;

        try
        {
            fWorld = new FileOutputStream("Salvataggi/Mondo");
            oosWorld = new ObjectOutputStream(fWorld);
            oosWorld.writeObject(world);
        }
        catch (IOException e)
        {
            System.out.println("File per salvare il mondo non trovato");
        }
        finally
        {
            try
            {
                if(fWorld != null) fWorld.close();
                if(oosWorld != null) oosWorld.close();
            }
            catch (IOException e)
            {

            }
        }

        try
        {
            fPg = new FileOutputStream("Salvataggi/Giocatore");
            oosPg = new ObjectOutputStream(fPg);
            oosPg.writeObject(giocatore);

        }
        catch (IOException e)
        {
            System.out.println("File per salvare il giocatore non trovato");
        }
        finally
        {
            try
            {
                if(fPg != null) fPg.close();
                if(oosPg != null) oosPg.close();
            }
            catch (IOException e)
            {

            }
        }
    }

    public static Mondo loadTheWorld()
    {
        Mondo world = null;
        FileInputStream f = null;
        ObjectInputStream ooi = null;

        try
        {
            f = new FileInputStream("Salvataggi/Mondo");
            ooi = new ObjectInputStream(f);
            world =(Mondo) ooi.readObject();
        }
        catch (IOException e)
        {
            //System.out.println("File per il caricamento del mondo non trovato");
            e.printStackTrace();
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return world;
    }

    public static Giocatore loadThePg()
    {
        Giocatore pg = null;
        FileInputStream f = null;
        ObjectInputStream ooi = null;

        try
        {
            f = new FileInputStream("Salvataggi/Giocatore");
            ooi = new ObjectInputStream(f);
            pg = (Giocatore) ooi.readObject();
        }
        catch (IOException e)
        {
            //System.out.println("File per il caricamento del giocatore non trovato");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return pg;
    }

    public static int intKeyInput(int in, int fin)
    {
        int i;
        do {
            Scanner input = new Scanner(System.in);
            i = input.nextInt();
            if(i < in || i > fin)
                System.out.println("Hai inserito un valore non valido. Riseleziona");
        }while(i < in || i > fin);
        return i;
    }
    public static Point simulMotion(int way, Giocatore giocatore)
    {
        int x = 0, y = 0;
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
        return new Point(x,y);
    }

}
