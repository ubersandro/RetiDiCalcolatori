package it.unical.dimes.reti.ese8.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Orario implements Serializable{
	private int ore, minuti;

	public Orario(int ore, int minuti) {
		this.ore = ore;
		this.minuti = minuti;
	}	

	public int getOre() {
		return ore;
	}
	public int getMinuti() {
		return minuti;
	}

	public int hashCode() {
		return (ore+"-"+minuti).hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Orario other = (Orario) obj;
		if (minuti != other.minuti)
			return false;
		if (ore != other.ore)
			return false;
		return true;
	}

	public String toString() {
		return "Orario [ore=" + ore + ", minuti=" + minuti + "]";
	}
}
