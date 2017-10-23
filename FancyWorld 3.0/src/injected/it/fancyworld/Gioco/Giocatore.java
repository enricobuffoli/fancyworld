package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.Utility;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static injected.it.fancyworld.Utils.Utility.*;
import static injected.it.fancyworld.Utils.Utility.DOWN;
import static injected.it.fancyworld.Utils.Utility.UP;

/**
 * Created by enrico on 07/10/17.
 */
public class Giocatore implements Serializable
{
    private Point position;
    private String nome;
    private int pesoTotale;
    private ArrayList<Chiave> chiavi;
    private static final int LIM_CHIAV = 5, LIM_PESO = 50;

    public Giocatore(Point point, String nome)
    {
        this.position = point;
        this.nome = nome;
        chiavi = new ArrayList<>();
        this.pesoTotale = 0;
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

    public Chiave getChiave(int index) {
        return chiavi.get(index);
    }

    public ArrayList<Chiave> getChiavi() {
        return chiavi;
    }

    public void addChiave(Chiave chiave) {
        chiavi.add(chiave);
    }

    public Point getPosition()
    {
        return position;
    }

    public String getNome()
    {
        return nome;
    }

    public int calculateTotalWeight()
    {
        int peso = 0;
        for(int i = 0; i < chiavi.size(); i++)
            peso += chiavi.get(i).getPeso();

        return peso;
    }
}
