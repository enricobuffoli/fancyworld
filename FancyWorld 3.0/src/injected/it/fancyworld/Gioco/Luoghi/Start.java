package injected.it.fancyworld.Gioco.Luoghi;

import injected.it.fancyworld.Gioco.Luoghi.Luogo;

import java.io.Serializable;

/**
 * Created by enrico on 05/10/17.
 */
public class Start extends Luogo implements Serializable
{
    public Start(int x, int y, String name) {
        super(x, y, name, "S");
    }
}
