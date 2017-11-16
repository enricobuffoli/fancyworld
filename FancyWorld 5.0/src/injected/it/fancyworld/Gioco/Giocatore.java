package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Gioco.StatoGiocatore.Stato;
import injected.it.fancyworld.Gioco.StatoGiocatore.StatoSenzaChiavi;
import injected.it.fancyworld.Utils.WorldConfig;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

import static injected.it.fancyworld.Utils.Utility.*;
import static injected.it.fancyworld.Utils.WorldConfig.START_POINTS;
import static injected.it.fancyworld.Utils.WorldConfig.gameValue;

/**
 * Created by enrico on 07/10/17.
 */
public class Giocatore implements Serializable
{
    private Point position;
    private String nome;
    private Stato stato;
    private int pesoTotale, punteggio;
    private ArrayList<Chiave> chiavi;
    private final int LIM_INF_PUNTEGGIO = 0;

    public Giocatore(Point point, String nome)
    {
        this.position = point;
        this.nome = nome;
        chiavi = new ArrayList<>();
        this.pesoTotale = 0;
        this.punteggio = gameValue[START_POINTS];
        stato = new StatoSenzaChiavi();
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

    public void layChiave(Luogo luogo) {
        stato.layDownKey(this, luogo);
    }

    public ArrayList<Chiave> getChiavi() {
        return chiavi;
    }

    public void addChiave(Luogo luogo) {
        stato.addChiave(this, luogo);
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

    public int getPunteggio() { return punteggio;}

    public void setPunteggio(int punteggio, Gioco.Sign sign)
    {
        if(sign.equals(Gioco.Sign.PLUS))
            this.punteggio += punteggio;
        else
            this.punteggio -= punteggio;
    }

    public void setPunteggioToMin() {
        punteggio = LIM_INF_PUNTEGGIO;

    }
    public void setStatoChiavi(Stato stato)
    {
        this.stato = stato;
    }
    public String printKeys()
    {
        String temp = "Sei in possesso delle seguenti chiavi:\n";
        for (int i = 0; i <this.getChiavi().size(); i++)
            temp += String.valueOf(i + 1) + ")Chiave di " + this.getChiavi().get(i)+"\n";
        return temp;
    }
}
