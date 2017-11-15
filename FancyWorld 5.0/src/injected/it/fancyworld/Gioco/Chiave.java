package injected.it.fancyworld.Gioco;

import injected.it.fancyworld.Utils.WorldConfig;

import java.io.Serializable;

import static injected.it.fancyworld.Utils.Utility.KEYS_NAMES;

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
        peso = WorldConfig.pesoChiavi[KEYS_NAMES.indexOf(tipo)];
        return peso;
    }

    public int getPeso() { return peso; }
}
