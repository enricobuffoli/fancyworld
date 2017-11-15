package injected.it.fancyworld.Gioco.StatoGiocatore;

import injected.it.fancyworld.Gioco.Chiave;
import injected.it.fancyworld.Gioco.Giocatore;
import injected.it.fancyworld.Gioco.Luogo;

/**
 * Created by enrico on 07/11/17.
 */
public abstract class Stato 
{
	public abstract void addChiave(Giocatore giocaatore, Luogo luogo);
    public abstract void layDownKey(Giocatore giocatore, Luogo luogo);
}
