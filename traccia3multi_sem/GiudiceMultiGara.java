package it.unical.dimes.reti.traccia3multi_sem;

import java.io.IOException;

public class GiudiceMultiGara {

    private final static RegistroGare registro = new RegistroGare();

    public void avvia() {
        try {
            // Fase 1-2: ricevi richiesta e invio a partecipanti
            new ThreadRichiesteHandler(registro).start();
            // Fase 3: ricevi offerte dai partecipanti
            new ThreadOfferteHandler(registro).start();
        } catch (IOException e) { e.printStackTrace(); }
    }

    public static void main(String[] args) {
        new GiudiceMultiGara().avvia();
    }

}
