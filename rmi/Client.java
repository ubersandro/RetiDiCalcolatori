package it.unical.dimes.reti.rmi;
import java.rmi.*;

public class Client {
  public static void main(String[] args) {

    //System.setProperty("java.security.policy","/home/loris/IdeaProjects/reti/src/security.policy");

    //System.setSecurityManager(new SecurityManager());
    try {
      TitoloAzionario titolo = (TitoloAzionario)Naming.lookup ("rmi://localhost/soc2");
      // sostituire “localhost” con l’hostname del server remoto, se le 
      // applicazioni client e server sono eseguite su macchine diverse
      System.out.println(titolo.getNome()+": "+titolo.getQuotazione());
    }
    catch(Exception e) {  
      System.out.println("Errore " + e);
    }
  }
}