package injected.it.fancyworld.Utils;

import java.io.*;
import java.util.ArrayList;

import com.sun.org.apache.xalan.internal.xsltc.compiler.Template;

import jdk.internal.org.objectweb.asm.tree.IntInsnNode;

import java.nio.file.*;
import static injected.it.fancyworld.Utils.Utility.KEYS_NAMES;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by enrico on 07/11/17.
 */
public class WorldConfig {

    public static final int NUMBER_OF_KEY = 0, SUP_LIM_KEY_WEIGHT = 1, MAX_NUMBER_OF_KEYS = 2, MAX_KEYS_WEIGHT = 3;
    public static final int NUMBER_OF_QUESTS = 4, SUP_LIM_QUEST_VALUE = 5, START_POINTS = 6, WIN_POINTS = 7;
    public static int [] pesoChiavi;
    public static int [] valoreProve;
    public static int [] valorePercentualeVittoria;
    public static int[] gameValue = new int[8];
    public static ArrayList<String> worlds;
    public static int worldIndex;
    public static final String[] PARAMETERS_STRING = {"Numero di chiavi", "Limite superiore del peso di una chiave", "Numero massimo di chiavi che al giocatore è permesso di possedere contemporaneamente","Peso massimo cumulativo delle chiavi che al giocatore è permesso di possedere contemporaneamente","Numero di tipologie di prove","Limite massimo del valore di una prova","Punti assegnati al giocatore all'inizio della partita","Numero dei punti per poter vincere"};
    public static final String PESO_CHIAVI = "9) Modificare il peso delle chiavi:";
    
    public static void choice()
    {
        System.out.println("Cosa vuoi fare?\n1) Creare un Nuovo Mondo\n2) Modificare un mondo\n3) Giocare");
        int index = Utility.intKeyInput(1,3);
        switch (index){
            case 1:
                createWorld();
                break;
            case 2:
                whichWorld();
                getConfig();
                changeConfig();
                break;
            case 3:
                whichWorld();
                getConfig();
                break;
        }
    }

    public static void createWorld()
    {
        System.out.println("Che nome vuoi dare al tuo mondo?");
        String name = Utility.generalKeyInput();
        new File("ConfigurazioniMondi/"+name).mkdir();
        new File("Nomi/"+name);

        try {
            Files.copy(FileSystems.getDefault().getPath(new File("Nomi/Luoghi/Default.txt").getPath()), FileSystems.getDefault().getPath(new File("Nomi/Luoghi/"+name+".txt").getPath()), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(FileSystems.getDefault().getPath(new File("Nomi/Chiavi/Default.txt").getPath()), FileSystems.getDefault().getPath(new File("Nomi/Chiavi/"+name+".txt").getPath()), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(FileSystems.getDefault().getPath(new File("ConfigurazioniMondi/Default/Default").getPath()), FileSystems.getDefault().getPath(new File("ConfigurazioniMondi/"+name+"/"+name).getPath()), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.copy(FileSystems.getDefault().getPath(new File("ConfigurazioniMondi/Default/DefaultpesoChiavi").getPath()), FileSystems.getDefault().getPath(new File("ConfigurazioniMondi/"+name+"/"+name+"pesoChiavi").getPath()), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Files.copy(FileSystems.getDefault().getPath(new File("ConfigurazioniMondi/Default/DefaultvaloriProve").getPath()), FileSystems.getDefault().getPath(new File("ConfigurazioniMondi/"+name+"/"+name+"valoriProve").getPath()), REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        worlds = new ArrayList<>();
        File[] temp = new File("ConfigurazioniMondi").listFiles();
        for(int i = 0;i<temp.length;i++)
        {
            worlds.add(temp[i].toString().split("/")[1]);
        }

        worlds.add(name);
        worldIndex = worlds.indexOf(name);
        defaultConfig();
        changeConfig();
    }

    public static void saveQuestConfig()
    {
        DataOutputStream osValori = null, osPercenutali = null;
        try {
            osValori = new DataOutputStream(new FileOutputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)+"valoriProve"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String temp="";
            for(int i = 0; i < gameValue[NUMBER_OF_QUESTS]; i++)
                temp += String.valueOf(valoreProve[i])+" ";
            osValori.writeUTF(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            osValori.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        try {
            osValori = new DataOutputStream(new FileOutputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)+"valoriPercentuali"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String temp="";
            for(int i = 0; i < gameValue[NUMBER_OF_QUESTS]; i++)
                temp += String.valueOf(valorePercentualeVittoria[i])+" ";
            osValori.writeUTF(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            osValori.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    public static void whichWorld()
    {
        worlds = new ArrayList<>();
        File[] temp = new File("ConfigurazioniMondi").listFiles();
        for(int i = 0; i < temp.length; i++)
        {
            if(i > 0)
                System.out.println(i+") " + temp[i].toString().split("/")[1]);
            worlds.add(temp[i].toString().split("/")[1]);
        }
        worldIndex = Utility.intKeyInput(1, worlds.size() - 1);
        System.out.println("Hai scelto " + worlds.get(worldIndex));
    }
    
    public static void savePesoChiavi()
    {
        DataOutputStream os = null;
        String config = null;
        try {
            os = new DataOutputStream(new FileOutputStream("ConfigurazioniMondi/Default/DefaultpesoChiavi"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String temp="";
            for(int i = 0; i < KEYS_NAMES.size(); i++)
                temp += String.valueOf(pesoChiavi[i])+" ";
            os.writeUTF(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void changeConfig()
    {
        int index = 1;

        do{
            System.out.println("Quale valore vuoi modificare? (0 per uscire)");
            do 
            {
                for (int i = 0; i < 8; i++)
                    System.out.println(i + 1 + ") " + PARAMETERS_STRING[i]);
                
                System.out.println(PESO_CHIAVI);
                System.out.println("10) Cambia valori delle singole prove");
                System.out.println("11) Cambia valori delle percentuali di vittoria delle singole prove");
                
                index = Utility.intKeyInput(0,11);
                
                if(index < 0 || index > 11)
                {
                    System.out.println("Hai inserito un numero sbagliato, Reinserisci.");
                }
            }
            while(index < 0 || index > 11);
            
            if(index != 0 && index != 9 && index != 10 && index != 11) 
            {
                System.out.println("Inserisci il valore:");
                int value = Integer.valueOf(Utility.generalKeyInput());

                System.out.println("Confermi l'inserimento? (Y/N)");
                String temp = Utility.yN();
                
                if (temp.equals("Y"))
                    gameValue[index-1] = value;

                if(index - 1 == NUMBER_OF_KEY) 
                {
                    Utility.readKeyNamesFromFile();
                    getPesoChiavi();
                }
                
                if(index - 1 == NUMBER_OF_QUESTS) 
                {
                    getValoriQuests();
                    getValoriPercentualiVittoria();
                }

            }
            else if (index==9)
                changePesoChiavi();
            else if(index==10)
                changeValoriProve();
            else if (index == 11)
            	changeValoriPercentualiVittoria();

        } while( index != 0);
        
        System.out.println("Vuoi salvare i cambiamenti?(Y/N)");
        String temp = Utility.yN();
        if(temp.equals("Y"));
            saveConfig();
    }

    public static void changePesoChiavi()
    {
        int index = 0;
        Utility.readKeyNamesFromFile();
        do {
            for (int i = 0; i < KEYS_NAMES.size(); i++)
                System.out.println(i + 1 + ") " + KEYS_NAMES.get(i));
            System.out.println("(0 per uscire)");
            index = Utility.intKeyInput(0, KEYS_NAMES.size());
            if(index!=0)
            {
                System.out.println("Inserisci valore:");
                int value = Utility.intKeyInput(0, gameValue[SUP_LIM_KEY_WEIGHT]);
                pesoChiavi[index - 1] = value;
            }
        }while(index!=0);
    }

    public static void changeValoriProve()
    {
        int index = 0;
        Utility.readKeyNamesFromFile();
        do {
            for (int i = 0; i < gameValue[NUMBER_OF_QUESTS]; i++)
                System.out.println(i + 1 + ") Quest "+i);
            System.out.println("(0 per uscire)");
            index = Utility.intKeyInput(0, gameValue[NUMBER_OF_QUESTS]);
            if(index!=0)
            {
                System.out.println("Inserisci valore:");
                int value = Utility.intKeyInput(0, gameValue[SUP_LIM_QUEST_VALUE]);
                valoreProve[index - 1] = value;
            }
        }while(index!=0);
    }
    
    public static void changeValoriPercentualiVittoria()
    {
        int index = 0;
        Utility.readKeyNamesFromFile();
        do {
            for (int i = 0; i < gameValue[NUMBER_OF_QUESTS]; i++)
                System.out.println(i + 1 + ") Quest "+i);
            System.out.println("(0 per uscire)");
            index = Utility.intKeyInput(0, gameValue[NUMBER_OF_QUESTS]);
            if(index!=0)
            {
                System.out.println("Inserisci valore:");
                int value = Utility.intKeyInput(0, 70);
                valorePercentualeVittoria[index - 1] = value;
            }
        }while(index!=0);
    }

    public static void saveConfig()
    {
        DataOutputStream os = null;
        String config = null;
        try {
            os = new DataOutputStream(new FileOutputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String temp="";
            for(int i = 0; i < 8; i++)
                temp += String.valueOf(gameValue[i])+" ";
            os.writeUTF(temp);

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        savePesoChiavi();
        saveQuestConfig();
    }

    public static void getPesoChiavi()
    {
        Utility.readKeyNamesFromFile();
        DataInputStream is = null;
        String config = null;
        
        try
        {
            is = new DataInputStream(new FileInputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)+"pesoChiavi"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            config = is.readUTF();
            String[] temp = config.split(" ");
            pesoChiavi = new int[KEYS_NAMES.size()];
            if(temp.length < KEYS_NAMES.size())
            {
                temp = new String[KEYS_NAMES.size()];
                for(int i = 0; i< KEYS_NAMES.size();i++)
                {
                    if(config.split(" ").length > i)
                        temp[i]=  config.split(" ")[i];
                    else
                        temp[i] = "5";
                }
                System.out.println("Il valore delle nuove chiavi aggiunte è stato configurato di default a 5");
            }
            
            for(int i = 0; i < KEYS_NAMES.size() ; i++)
            {
                pesoChiavi[i] = Integer.valueOf(temp[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getValoriPercentualiVittoria()
    {
        DataInputStream is = null;
        String config = null;
        
        try 
        {
            is = new DataInputStream(new FileInputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)+"valoriPercentuali"));
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        
        try
        {
            config = is.readUTF();
            String[] temp = config.split(" ");
            valorePercentualeVittoria = new int[gameValue[NUMBER_OF_QUESTS]];
            if(temp.length < gameValue[NUMBER_OF_QUESTS])
            {
                temp = new String[gameValue[NUMBER_OF_QUESTS]];
                for(int i = 0; i< gameValue[NUMBER_OF_QUESTS];i++)
                {
                    if(config.split(" ").length > i)
                        temp[i]=  config.split(" ")[i];
                    else
                        temp[i] = String.valueOf(Utility.getRandomBetween2(70));
                }
                System.out.println("Il valore delle nuove prove aggiunte è stato configurato di default con un valore compreso tra 0 e 70");
            }

            for(int i = 0; i < gameValue[NUMBER_OF_QUESTS] ; i++)
            {
                valorePercentualeVittoria[i] = Integer.valueOf(temp[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getValoriQuests()
    {
        DataInputStream is = null;
        String config = null;
        
        try 
        {
            is = new DataInputStream(new FileInputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)+"valoriProve"));
        }
        catch (FileNotFoundException e) 
        {
            e.printStackTrace();
        }
        
        try
        {
            config = is.readUTF();
            String[] temp = config.split(" ");
            valoreProve = new int[gameValue[NUMBER_OF_QUESTS]];
            if(temp.length < gameValue[NUMBER_OF_QUESTS])
            {
                temp = new String[gameValue[NUMBER_OF_QUESTS]];
                for(int i = 0; i< gameValue[NUMBER_OF_QUESTS];i++)
                {
                    if(config.split(" ").length>i)
                        temp[i]=  config.split(" ")[i];
                    else
                        temp[i] = "5";
                }
                System.out.println("Il valore delle nuove prove aggiunte è stato configurato di default a 5");
            }

            for(int i = 0; i < gameValue[NUMBER_OF_QUESTS] ; i++)
            {
                valoreProve[i] = Integer.valueOf(temp[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void getConfig()
    {
        DataInputStream is = null;
        String config = null;
        try 
        {
            is = new DataInputStream(new FileInputStream("ConfigurazioniMondi/"+worlds.get(worldIndex)+"/"+worlds.get(worldIndex)));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        try
        {

            config = is.readUTF();
            String[] temp = config.split(" ");
            for(int i = 0; i < 8 ; i++)
            {
                gameValue[i] = Integer.valueOf(temp[i]);
            }

        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            is.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        getPesoChiavi();
        getValoriQuests();
        getValoriPercentualiVittoria();
    }
    
    public static void defaultConfig()
    {
        DataInputStream is = null;
        String config = null;
        try {
            is = new DataInputStream(new FileInputStream("ConfigurazioniMondi/Default/Default"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            config = is.readUTF();
            String[] temp = config.split(" ");
            for(int i = 0; i < 8 ; i++)
            {
                gameValue[i] = Integer.valueOf(temp[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        defaultPesoChiaviConfig();
        defaultValoriProveConfig();

    }

    public static void defaultPesoChiaviConfig()
    {
        Utility.readKeyNamesFromFile();
        DataInputStream is = null;
        String config = null;
        try {
            is = new DataInputStream(new FileInputStream("ConfigurazioniMondi/Default/DefaultpesoChiavi"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            config = is.readUTF();
            pesoChiavi = new int[KEYS_NAMES.size()];
            String[] temp = config.split(" ");
            for(int i = 0; i < KEYS_NAMES.size() ; i++)
            {
                pesoChiavi[i] = Integer.valueOf(temp[i]);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void defaultValoriProveConfig()
    {
        DataInputStream isValori = null, isPercenutali = null;
        String config = null;
        
        try {
            isValori = new DataInputStream(new FileInputStream("ConfigurazioniMondi/Default/DefaultvaloriProve"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            config = isValori.readUTF();
            valoreProve = new int[gameValue[NUMBER_OF_QUESTS]];
            
            String[] temp = config.split(" ");


            for(int i = 0; i < gameValue[NUMBER_OF_QUESTS] ; i++)
            {
                valoreProve[i] = Integer.valueOf(temp[i]);
            }
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            isValori.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        try {
            isPercenutali = new DataInputStream(new FileInputStream("ConfigurazioniMondi/Default/DefaultvaloriPercentuali"));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {

            config = isPercenutali.readUTF();
            valorePercentualeVittoria = new int[gameValue[NUMBER_OF_QUESTS]];
            
            String[] temp = config.split(" ");


            for(int i = 0; i < gameValue[NUMBER_OF_QUESTS] ; i++)
            {
                valorePercentualeVittoria[i] = Integer.valueOf(temp[i]);
            }
            
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        try 
        {
            isValori.close();
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }
}
