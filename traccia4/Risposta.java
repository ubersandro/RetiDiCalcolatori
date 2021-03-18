package it.unical.dimes.reti.traccia4;

public class Risposta {

    private int prezzo;
    private int quantita;
    private int idVenditore;
    private int idProdotto;

    public Risposta(int idProdotto, int quantita, int prezzo, int idVenditore) {
        this.prezzo = prezzo;
        this.quantita = quantita;
        this.idVenditore = idVenditore;
        this.idProdotto = idProdotto;
    }

    public int getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(int prezzo) {
        this.prezzo = prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    public void setQuantita(int quantita) {
        this.quantita = quantita;
    }

    public int getIdVenditore() {
        return idVenditore;
    }

    public void setIdVenditore(int idVenditore) {
        this.idVenditore = idVenditore;
    }

    public int getIdProdotto() {
        return idProdotto;
    }

    public void setIdProdotto(int idProdotto) {
        this.idProdotto = idProdotto;
    }

}
