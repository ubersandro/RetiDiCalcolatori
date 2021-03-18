package it.unical.dimes.reti.ese8.ws;

public interface AeroportoIF {
	public String privoVolo(String citta, Data data);
	
	public Orario orarioVolo(String volo, Data data);

}
