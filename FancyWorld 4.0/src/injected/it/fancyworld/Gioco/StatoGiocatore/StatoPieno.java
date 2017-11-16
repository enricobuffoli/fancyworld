package injected.it.fancyworld.Gioco.StatoGiocatore;

import injected.it.fancyworld.Gioco.Giocatore;
import injected.it.fancyworld.Gioco.Luogo;
import injected.it.fancyworld.Utils.Utility;

/**
 * Created by enrico on 07/11/17.
 */
public class StatoPieno extends Stato {

    public StatoPieno() { }
    
    @Override
    public void addChiave(Giocatore giocatore, Luogo luogo) {
        System.out.println("Non puoi prendere altre chiavi, hai già raggiunto il peso limite.");
    }

    @Override
    public void layDownKey(Giocatore giocatore, Luogo luogo) {
        System.out.println(giocatore.printKeys());
        System.out.println(giocatore.getChiavi().size() + 1 + ")Annulla\nQuale vuoi depositare?");
        int index = Utility.intKeyInput(0, giocatore.getChiavi().size()) - 1;
        if (giocatore.getChiavi().size() != 0 && index != giocatore.getChiavi().size()) {
            luogo.setChiaveDelLuogo(giocatore.getChiavi().get(index));
            giocatore.getChiavi().remove(index);
            giocatore.setStatoChiavi(new StatoConChiavi());
        } else
            System.out.println("Annullato");

    }
}
