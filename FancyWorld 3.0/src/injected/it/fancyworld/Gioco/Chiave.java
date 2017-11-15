package injected.it.fancyworld.Gioco;

import java.io.Serializable;

/**
 * Created by enrico on 11/10/17.
 */
public class Chiave implements Serializable
{

    private String tipo;
    private int peso;

    public Chiave(String tipo)
    {
        this.tipo = tipo;
        this.peso = generatekeyWeight();
    }

    public String toString() {
        return tipo;
    }

    public Chiave clone()
    {
        return new Chiave(tipo);
    }

    private int generatekeyWeight()
    {
        int peso;
        peso = (int) (Math.random() * 25) + 1;
        return peso;
    }

    public int getPeso() { return peso; }
}
