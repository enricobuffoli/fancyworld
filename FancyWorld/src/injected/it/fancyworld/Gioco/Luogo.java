package injected.it.fancyworld.Gioco;

import java.awt.*;

/**
 * Created by enrico on 05/10/17.
 */
public class Luogo {

    private Point position;
    private String name;

    public Luogo(int x, int y, String name)
    {
        position = new Point(x,y);
        this.name = name;
    }

    public Point getPosition()
    {
        return position;
    }

    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
    	this.name = name;
    }
}
