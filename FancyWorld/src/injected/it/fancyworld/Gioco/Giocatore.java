package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import java.awt.*;

import static injected.it.fancyworld.Utils.Utility.*;
import static injected.it.fancyworld.Utils.Utility.DOWN;
import static injected.it.fancyworld.Utils.Utility.UP;

/**
 * Created by enrico on 07/10/17.
 */
public class Giocatore {
    private Point position;
    private String nome;

    public Giocatore(Point point, String nome)
    {
        position = point;
        this.nome = nome;
    }

    public void move(int way) 
    {
        switch (way) {
            case NORD:
                position.x -= 1;
                break;
            case SUD:
                position.x += 1;
                break;
            case EST:
                position.y += 1;
                break;
            case OVEST:
                position.y -= 1;
                break;
        }
    }
    public void setPosition(int x, int y)
    {
        position.x = x;
        position.y = y;
    }
    public Point getPosition()
    {
        return position;
    }
}
