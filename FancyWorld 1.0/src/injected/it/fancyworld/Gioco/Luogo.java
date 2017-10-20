package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Gioco.Livello;

import java.awt.*;

/**
 * Created by enrico on 05/10/17.
 */
public class Luogo {

    private Point position;
    private String name,tag;
    private boolean passed;
    private Livello.TipoLuoghi tipoLuogo;

    public Luogo(int x, int y, String name, String tag, Livello.TipoLuoghi tipoLuogo)
    {
        position = new Point(x,y);
        this.name = name;
        this.tag = tag;
        this.tipoLuogo = tipoLuogo;
        this.passed = false;
    }


    public Point getPosition()
    {
        return position;
    }

    public String getName()
    {
        return name;
    }

    public String getTag()
    {
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

    public Livello.TipoLuoghi getTipoLuogo() {
        return tipoLuogo;
    }
}
