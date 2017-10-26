package injected.it.fancyworld.Gioco.Prove;

import java.io.Serializable;

public abstract class Prova implements Serializable
{
    public enum TipoProva
    {
        EASY, MEDIUM, HARD;
    }

    public abstract boolean isAccomplished();

    public abstract TipoProva getTipo();

    public abstract String getTag();

    public abstract int getPunteggio();
}
