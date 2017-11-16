package injected.it.fancyworld.Gioco.Prove;

import injected.it.fancyworld.Utils.*;

import java.io.Serializable;

public class Prova implements Serializable
{
    private int winLosPoints, percentageWin;
    
    public Prova()
    {
    	randomGeneration();
    }

	private void randomGeneration()
    {
    	int tipo = Utility.getRandomBetween2(WorldConfig.valoreProve.length);
    	
    	winLosPoints = WorldConfig.valoreProve[tipo];
    	percentageWin = WorldConfig.valorePercentualeVittoria[tipo];
    }

    public boolean isAccomplished()
    {
        int result;
        result = Utility.getRandomBetween2(100);
        if(result >= percentageWin)
            return true;
        else
            return false;
    }
    
    public int getPunteggio() { return winLosPoints; }
}
