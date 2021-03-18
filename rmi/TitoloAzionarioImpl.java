package it.unical.dimes.reti.rmi;
import java.rmi.*;
import java.rmi.server.*;
public class TitoloAzionarioImpl 
                    extends UnicastRemoteObject implements TitoloAzionario {
  private String nome;
  private double quotazione;
  public TitoloAzionarioImpl(String nome, double quotazioneApertura) 
             throws RemoteException {
    super();
    this.nome = nome; quotazione = quotazioneApertura;
  }
  public String getNome() throws RemoteException {
    return nome;
  }
  public void setQuotazione(double nuovaQuotazione){
    quotazione = nuovaQuotazione;
  }
  public double getQuotazione() throws RemoteException {
    return quotazione;
  }
}