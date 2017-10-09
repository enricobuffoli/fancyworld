package injected.it.fancyworld.Gioco;

import java.awt.*;

/**
 * Created by enrico on 05/10/17.
 */
public class Luogo {

    private Point position;
    private String name,tag;

    public Luogo(int x, int y, String name, String tag)
    {
        position = new Point(x,y);
        this.name = name;
        this.tag = tag;
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
}
