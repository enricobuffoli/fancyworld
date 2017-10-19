package injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Chiave;

/**
 * Created by enrico on 11/10/17.
 */
public class Chiave {

    private String tipo;
    private String nome;

    public Chiave(String tipo) {
        this.tipo = tipo;
        //getNamefromType in Utility
    }

    public String toString() {
        return nome;
    }

    public Chiave clone()
    {
        return new Chiave(tipo);
    }
}
