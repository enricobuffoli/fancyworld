package injected.it.fancyworld;

import injected.it.fancyworld.Gioco.Gioco;
import injected.it.fancyworld.Gioco.Livello;
import injected.it.fancyworld.Utils.Utility;

import java.awt.*;


public class Main {

    public static void main(String[] args) {

        Utility.readPlacesFromFile();
        Gioco gioco = new Gioco("Enrico");

    }

}