package injected.it.fancyworld.Gioco.Luoghi.LuoghiConChiave.Chiave;

import injected.it.fancyworld.Utils.Utility;

/**
 * Created by enrico on 11/10/17.
 */
public class Chiave {

    private String nome;

    public Chiave(String nome) {
        this.nome = nome;
    }

    public String toString() {
        return nome;
    }

    public Chiave clone()
    {
        return new Chiave(nome);
    }
}
