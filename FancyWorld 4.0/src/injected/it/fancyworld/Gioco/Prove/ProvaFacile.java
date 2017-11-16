package injected.it.fancyworld.Gioco.Prove;

import injected.it.fancyworld.Utils.Utility;

import java.io.Serializable;

public class ProvaFacile extends Prova implements Serializable
{
    private int percentageWin, winLosPoints;
    private Prova.TipoProva tipoProva;
    private String tag;

    public ProvaFacile()
    {
        percentageWin = 30;
        winLosPoints = 5;
        tipoProva = Prova.TipoProva.EASY;
        tag = "E";
    }

    @Override
    public boolean isAccomplished()
    {
        int result;
        result = Utility.getRandomBetween2(100);
        if(result >= percentageWin)
            return true;
        else
            return false;
    }

    @Override
    public TipoProva getTipo() {
        return tipoProva;
    }

    @Override
    public String getTag() {
        return tag;
    }

    @Override
    public int getPunteggio() {
        return winLosPoints;
    }

}
