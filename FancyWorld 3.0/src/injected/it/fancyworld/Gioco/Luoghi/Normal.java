package injected.it.fancyworld.Gioco.Luoghi;

import injected.it.fancyworld.Gioco.Luoghi.Luogo;

import java.io.Serializable;

/**
 * Created by enrico on 05/10/17.
 */
public class Normal extends Luogo implements Serializable {

    public Normal(int x, int y, String name) {
        super(x, y, name, "N");
    }
}
