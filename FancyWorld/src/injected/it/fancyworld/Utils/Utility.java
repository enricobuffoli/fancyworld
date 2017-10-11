package injected.it.fancyworld.Utils;

import injected.it.fancyworld.Gioco.Luoghi.DownStairs;
import injected.it.fancyworld.Gioco.Luoghi.UpStairs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;


/**
 * Created by enrico on 05/10/17.
 */
public class Utility {

    public static final int EXIT = -1, ERROR = 0, NORMAL = 1, LIV_UP = 2, LIV_TRAP = 3, START = 4, PASS = 5, LIV_DOWN = 6, NUMBER_OF_LEVELS=8, MAP_DIMENSION = 20;
    public static final int UP = 0, DOWN = 1, NORD = 2, SUD = 3, OVEST = 4, EST = 5;
    public static final String INPUT_VALUE = "-1NSEWUD", SPECIAL_TAG_VALUE= "UDTS", ERRORE_INPUT= "Hai selezionato un tasto non corretto, puoi solo selezione (N,S,E,W,U,D). Riprova.";
    public static final String Y_N = "YN";
    public static final String WALL= "Non puoi passare di qui, c'è un muro!";
    public static final String TI_TROVI = "Ti trovi nella contea di ";
    
    public static boolean randomChoice(int percentage)
    {
        return Math.random()*100 < percentage;
    }
    public static ArrayList<String> PLACES_NAMES;
    
    public static int getRandomNumber()
    {
        return (int) (Math.random()*20);
    }
    
    private static Iterator iterator;

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
            f = new FileReader("Luoghi/nomi_luoghi.txt");
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

        iterator = PLACES_NAMES.iterator();

    }


    public static String getPlaceName()
    {
        if(iterator.hasNext())
            return (String) iterator.next();

        iterator = PLACES_NAMES.iterator();
        return (String) iterator.next();
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
            default:
                return ERROR;
        }
    }

    public static DownStairs fromUpToDown(UpStairs upStairs)
    {
        return new DownStairs(upStairs.getPosition().x,upStairs.getPosition().y,getPlaceName());
    }


}
