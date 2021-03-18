package it.unical.dimes.reti.rmi;
import java.rmi.*;

public class BorsaTelematica {
  public static void main(String args[]) {
    try {
      TitoloAzionarioImpl ta1 = new TitoloAzionarioImpl("Societa’1 S.p.A.",100.00);
      TitoloAzionarioImpl ta2 = new TitoloAzionarioImpl("Societa’2 S.p.A.",50.00);
      TitoloAzionarioImpl ta3 = new TitoloAzionarioImpl("Societa’3 S.p.A.",50.00);      
      Naming.rebind("soc1", ta1);
      Naming.rebind("soc2", ta2);
      Naming.rebind("soc3", ta3);      
      System.out.println ("Borsa Telematica in ascolto...");
    }
    catch(Exception e) {
      System.out.println("Errore: " + e);
    }
  }
}