package injected.it.fancyworld.Gioco;

import java.io.Serializable;

/**
 * Created by enrico on 11/10/17.
 */
public class Chiave implements Serializable {

    private String nome;
    private int peso;

    public Chiave(String nome) 
    {
        this.nome = nome;
        this.peso = generatekeyWeight();
    }

    public String toString() {
        return nome;
    }

    public Chiave clone()
    {
        return new Chiave(nome);
    }
    
    private int generatekeyWeight()
    {
    	int peso;
    	peso = (int) (Math.random() * 25) + 1;
    	return peso;
    }

    public int getPeso()
    {
        return peso;
    }
}
