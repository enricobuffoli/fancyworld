package injected.it.fancyworld.Gioco.StatoGiocatore;

import injected.it.fancyworld.Gioco.*;
import injected.it.fancyworld.Utils.Utility;

import static injected.it.fancyworld.Gioco.Gioco.*;

/**
 * Created by enrico on 07/11/17.
 */
public class StatoConChiavi extends Stato {

    public StatoConChiavi() { }
    
    @Override
    public void addChiave(Giocatore giocatore, Luogo luogo) {
        System.out.println("Numero di chiavi che avresti: " + (giocatore.getChiavi().size() + 1));
        if ((giocatore.getChiavi().size() + 1) <= LIM_CHIAVI)
        {
            System.out.println("Peso totale di chiavi in possesso: " + giocatore.calculateTotalWeight());
            if ((giocatore.calculateTotalWeight() + luogo.getChiaveDelLuogo().getPeso()) <= LIM_PESO)
                giocatore.getChiavi().add(luogo.pickUpKey());
            else
                System.out.println("Il peso totale che trasporteresti supererebbe il peso limite");
        }
        else
            System.out.println("Il numero massimo di chiavi che trasporteresti supererebbe il numero massimo");

    }

    @Override
    public void layDownKey(Giocatore giocatore, Luogo luogo) {
        System.out.println(giocatore.printKeys());
        System.out.println(giocatore.getChiavi().size() + 1 + ")Annulla\nQuale vuoi depositare?");
        int index = Utility.intKeyInput(0, giocatore.getChiavi().size()+1) - 1;
        if (giocatore.getChiavi().size() != 0 && index != giocatore.getChiavi().size()) {
            luogo.setChiaveDelLuogo(giocatore.getChiavi().get(index));
            giocatore.getChiavi().remove(index);
            if(giocatore.getChiavi().size()==0)
                giocatore.setStatoChiavi(new StatoSenzaChiavi());
        } else
            System.out.println("Annullato");


    }
}
