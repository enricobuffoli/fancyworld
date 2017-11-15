package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Gioco.Chiave;
import injected.it.fancyworld.Gioco.Prove.Prova;
import injected.it.fancyworld.Gioco.Prove.ProvaFacile;
import injected.it.fancyworld.Utils.Utility;

import java.awt.*;
import java.io.Serializable;

import static injected.it.fancyworld.Gioco.Gioco.LIM_INF_PUNTEGGIO;

/**
 * Created by enrico on 05/10/17.
 */
public class Luogo implements Serializable
{

    private Point position;
    protected String name,tag;
    private boolean passed, locked;
    private Chiave chiaveDelLuogo, chiaveDiChiusura;
    private Livello.TipoLuoghi tipoLuogo;
    private Prova prova;

    public Luogo(int x, int y, String name, String tag, Livello.TipoLuoghi tipoLuogo)
    {
        position = new Point(x,y);
        this.name = name;
        this.tag = tag;
        this.passed = false;
        this.chiaveDelLuogo = null;   /*chiave presente in questo luogo*/
        this.chiaveDiChiusura = null;
        this.tipoLuogo = tipoLuogo;
        prova = null;
    }

    public Livello.TipoLuoghi getTipoLuogo()
    {
        return tipoLuogo;
    }

    public void giocatoreNelLuogo(Giocatore giocatore)
    {
        if(chiaveDelLuogo!=null)
        {
            System.out.println("In questo luogo c'e' la chiave di " + chiaveDelLuogo.toString() + " di peso " + chiaveDelLuogo.getPeso()+ "\nVuoi raccoglierla? (Y/N)");
            String temp = Utility.yN();
            if(temp.equals("Y"))
            {
                giocatore.addChiave(this);
            }
        }
        else if(chiaveDelLuogo==null)
            System.out.println("Questo posto non ha chiavi");
        if(prova!=null)
        {
            String decision = "";
            System.out.println("In questo luogo è presente una prova; vuoi sostenerla? (Y/N)");
            decision = Utility.yN();
            if(decision.equals("Y"))
            {
                if(prova.isAccomplished())
                {
                    giocatore.setPunteggio(prova.getPunteggio(), Gioco.Sign.PLUS);
                    System.out.println("Complimenti, hai guadagnato " + prova.getPunteggio() + " punti, arrivando a " + giocatore.getPunteggio() + " punti");
                }
                else
                {
                    giocatore.setPunteggio(prova.getPunteggio(), Gioco.Sign.MINUS);
                    if(giocatore.getPunteggio() < LIM_INF_PUNTEGGIO)
                        giocatore.setPunteggioToMin();
                    System.out.println("Che peccato, hai perso " + prova.getPunteggio() + " punti, arrivando a " + giocatore.getPunteggio() + " punti");
                }
            }
        }
    }

    public Point getPosition()
    {
        return position;
    }

    public void generateKey(String nome)
    {
        chiaveDelLuogo = new Chiave(nome);
    }

    public boolean hasKey()
    {
        return (chiaveDelLuogo != null);
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
        else if(locked)
            return "L";
        else if(prova != null)
            return prova.getTag();
        else
            return tag;
    }

    public void alreadyPassed()
    {
        passed = true;
    }

    public boolean isPassed()
    {
        return passed;
    }

    public void lock()
    {
        locked = true;
        String name = Utility.getKeyName();
        chiaveDiChiusura = new Chiave(name);
    }
    public void tryToUnlock(Giocatore giocatore)
    {
        System.out.println("Questo passaggio è bloccato dalla chiave " + this.getChiaveDiChiusura().toString());

        if(giocatore.getChiavi().size() != 0)
        {
            System.out.println("Vuoi tentare di aprire la porta? (Y/N)");
            String temp = Utility.yN();
            if(temp.equals("Y"))
            {
                System.out.println(giocatore.printKeys());
                System.out.println(giocatore.getChiavi().size() + 1 + ")Annulla\nQuale vuoi utilizzare?");

                while(true)
                {
                    int index = Utility.intKeyInput(0,giocatore.getChiavi().size() + 1 ) - 1;

                    if(index == giocatore.getChiavi().size())
                    {
                        break;
                    }

                    else if (giocatore.getChiavi().get(index).toString().equals(this.getChiaveDiChiusura().toString()))
                    {
                        this.unlock();
                        System.out.println("Hai sbloccato il passaggio");
                        break;
                    }

                    else
                    {
                        System.out.println("Non hai selezionato la chiave giusta.");
                    }
                }
            }
        }
        else
            System.out.println("Il giocatore non possiede alcuna chiave");

    }
    public Chiave getChiaveDiChiusura()
    {
        return chiaveDiChiusura;
    }

    public Prova getProva() {
        return prova;
    }

    public void setProva(Prova prova) { this.prova = prova;}

    public boolean hasProva()
    {
        return (prova!=null);
    }
}
