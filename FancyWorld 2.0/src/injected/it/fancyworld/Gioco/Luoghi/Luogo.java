package injected.it.fancyworld.Gioco.Luoghi;

import injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Chiave.Chiave;
import injected.it.fancyworld.Utils.Utility;

import java.awt.*;

/**
 * Created by enrico on 05/10/17.
 */
public class Luogo {

    private Point position;
    protected String name,tag;
    private boolean passed, locked;
    private Chiave chiaveDelLuogo;

    public Luogo(int x, int y, String name, String tag)
    {
        position = new Point(x,y);
        this.name = name;
        this.tag = tag;
        this.passed = false;
        this.chiaveDelLuogo = null;   /*chiave presente in questo luogo*/
    }

    public Point getPosition()
    {
        return position;
    }

    public void generateKey(String nome)
    {
        chiaveDelLuogo = new Chiave(nome);
    }

    public boolean hasPlaceKey()
    {
        return (chiaveDelLuogo!=null);
    }

    public Chiave getChiaveDelLuogo()
    {
        return chiaveDelLuogo;
    }

    public void setChiaveDelLuogo(Chiave chiave) {this.chiaveDelLuogo = chiave; }

    public Chiave pickUpKey()
    {
        Chiave temp = chiaveDelLuogo.clone();
        chiaveDelLuogo = null;
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

    public String getName()
    {
        return name;
    }

    public String getTag()
    {
        if(chiaveDelLuogo != null)
            return "K";
        else
            return tag;
    }

    public void setTag(String s)
    {
        tag = s;
    }

    public void setName(String name)
    {
    	this.name = name;
    }

    public void alreadyPassed()
    {
        passed = true;
    }

    public boolean isPassed()
    {
        return passed;
    }
}
