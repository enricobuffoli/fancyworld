package injected.it.fancyworld;

import injected.it.fancyworld.Gioco.Gioco;
import injected.it.fancyworld.Utils.Utility;

public class Main {

    @SuppressWarnings("unused")
	public static void main(String[] args)
    {
        Utility.readPlacesFromFile();
        Gioco gioco = Gioco.getGioco();
    }
}