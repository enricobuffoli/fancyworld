package injected.it.fancyworld.Gioco.Luoghi;

import injected.it.fancyworld.Gioco.Chiave;
import injected.it.fancyworld.Utils.Utility;

import java.io.Serializable;

/**
 * Created by enrico on 11/10/17.
 */
public class LuogoBloccato extends Luogo implements Serializable {

    private boolean locked;
    private Chiave chiaveDiChiusura;

    public LuogoBloccato(int x, int y, String name, String tag)
    {
        super(x, y, name, tag);
        locked = false;
        chiaveDiChiusura = null; /*chiave ceh tiene chiuso questo luogo*/
    }

    public void lock()
    {
        locked = true;
        chiaveDiChiusura = new Chiave(Utility.getKeyName());
    }

    public Chiave getChiaveDiChiusura()
    {
        return chiaveDiChiusura;
    }

    public void unlock()
    {
        locked = false;
    }

    public boolean isLocked()
    {
        return locked;
    }

    public Chiave keyToUnlock()
    {
        return chiaveDiChiusura;
    }

    @Override
    public String getTag()
    {
        if(locked)
            return "L";
        else
            return super.getTag();
    }
}
