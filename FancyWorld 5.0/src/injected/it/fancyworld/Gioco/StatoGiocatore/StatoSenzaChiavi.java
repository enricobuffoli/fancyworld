package injected.it.fancyworld.Gioco.StatoGiocatore;

import injected.it.fancyworld.Gioco.Chiave;
import injected.it.fancyworld.Gioco.Giocatore;
import injected.it.fancyworld.Gioco.Luogo;

/**
 * Created by enrico on 07/11/17.
 */
public class StatoSenzaChiavi extends Stato {

    public StatoSenzaChiavi() { }

    @Override
    public void addChiave(Giocatore giocatore, Luogo luogo) {
        System.out.println("La chiave " + luogo.getChiaveDelLuogo().toString()+ " è stata aggiunta");
        giocatore.getChiavi().add(luogo.pickUpKey());
        giocatore.setStatoChiavi(new StatoConChiavi());
    }

    @Override
    public void layDownKey(Giocatore giocatore, Luogo luogo) {
        System.out.println("Non sei in possesso di alcuna Chiave");
    }
}
