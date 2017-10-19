package injected.it.fancyworld.Gioco.Luoghi;

import java.io.Serializable;

/**
 * Created by enrico on 09/10/17.
 */
public class Goal extends Luogo implements Serializable {
    public Goal(int x, int y, String name) {
        super(x, y, name, "G");
    }
}
