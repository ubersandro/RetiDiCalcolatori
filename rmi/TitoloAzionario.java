package it.unical.dimes.reti.rmi;
import java.rmi.*;
public interface TitoloAzionario extends Remote {
  String getNome() throws RemoteException;
  double getQuotazione() throws RemoteException;
}