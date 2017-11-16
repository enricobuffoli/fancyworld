package injected.it.fancyworld;

import injected.it.fancyworld.Gioco.Gioco;
import injected.it.fancyworld.Utils.Utility;

public class Main {

    public static void main(String[] args) {

        Utility.readPlacesFromFile();
        Utility.readKeyNamesFromFile();
        Gioco gioco = Gioco.getGioco();
    }

}