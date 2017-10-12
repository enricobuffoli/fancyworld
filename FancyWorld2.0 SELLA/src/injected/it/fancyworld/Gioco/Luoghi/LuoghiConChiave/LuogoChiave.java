package injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave;

import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Chiave.Chiave;
import injected.it.fancyworld.Gioco.Luoghi.Luogo;
import injected.it.fancyworld.Utils.Utility;

/**
 * Created by enrico on 11/10/17.
 */
public class LuogoChiave extends Luogo{

    private boolean locked;
    private String chiavePerSbloccare;
    private Chiave chiave;

    public LuogoChiave(int x, int y, String name, String tag) {
        super(x, y, name, tag);
        locked = false;
        chiave = null;
    }

    public void lock()
    {
        locked = true;
        chiavePerSbloccare = Utility.getKeyName();
    }

    public void generateKey(String tipo)
    {
        chiave = new Chiave(tipo);
    }

    public boolean hasKey()
    {
        return (chiave!=null);
    }

    public String whichKey()
    {
        return chiave.toString();
    }

    public Chiave pickUpKey()
    {
        Chiave temp = chiave.clone();
        chiave = null;
        return temp;
    }

    public void unlock()
    {
        locked = false;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public String keyToUnlock()
    {
        return chiavePerSbloccare;
    }
}
