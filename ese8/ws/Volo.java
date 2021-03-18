package it.unical.dimes.reti.ese8.ws;

public class Volo{
	
	private String citta;
	private Data data;
	private Orario orario;
	private String voloId;
	
	public Volo(String citta, Data data, Orario orario, String voloId) {
		super();
		this.citta = citta;
		this.data = data;
		this.orario = orario;
		this.voloId = voloId;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Volo other = (Volo) obj;
		if (citta == null) {
			if (other.citta != null)
				return false;
		} else if (!citta.equals(other.citta))
			return false;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (orario == null) {
			if (other.orario != null)
				return false;
		} else if (!orario.equals(other.orario))
			return false;
		if (voloId == null) {
			if (other.voloId != null)
				return false;
		} else if (!voloId.equals(other.voloId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((citta == null) ? 0 : citta.hashCode());
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((orario == null) ? 0 : orario.hashCode());
		result = prime * result + ((voloId == null) ? 0 : voloId.hashCode());
		return result;
	}
	
	//metodi get
	
	public String getCitta() {
		return citta;
	}
	public Data getData() {
		return data;
	}
	public Orario getOrario() {
		return orario;
	}
	public String getVoloId() {
		return voloId;
	}
}
