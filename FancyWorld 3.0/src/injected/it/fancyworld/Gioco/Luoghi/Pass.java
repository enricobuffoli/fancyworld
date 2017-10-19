package injected.it.fancyworld.Gioco.Luoghi;

import java.io.Serializable;

/**
 * Created by enrico on 05/10/17.
 */
public class Pass extends LuogoBloccato implements Serializable
{
    public Pass(int x, int y, String name) {
        super(x, y, name, "P");
    }
}
