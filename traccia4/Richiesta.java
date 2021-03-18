package it.unical.dimes.reti.traccia4;

import java.io.Serializable;

public class Richiesta implements Serializable {
    int idProdotto;
    int quantita;

    public Richiesta(int idProdotto, int quantita) {
        super();
        this.idProdotto = idProdotto;
        this.quantita = quantita;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }
}
