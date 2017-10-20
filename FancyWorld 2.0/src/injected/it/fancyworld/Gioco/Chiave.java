package injected.it.fancyworld.Gioco;

/**
 * Created by enrico on 11/10/17.
 */
public class Chiave {

    private String tipo;

    public Chiave(String tipo)
    {
        this.tipo = tipo;
    }

    public String toString() {
        return tipo;
    }

    public Chiave clone()
    {
        return new Chiave(tipo);
    }
}
