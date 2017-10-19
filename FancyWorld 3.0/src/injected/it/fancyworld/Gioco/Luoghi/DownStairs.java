package injected.it.fancyworld.Gioco.Luoghi;

import java.io.Serializable;

/**
 * Created by enrico on 07/10/17.
 */
public class DownStairs extends LuogoBloccato implements Serializable {

    public DownStairs(int x, int y, String name) {
        super(x, y, name, "D");
    }
}
