package it.unical.dimes.reti.ese8.ws;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Data implements Serializable{
	private int giorno, mese, anno;

	public Data(int giorno, int mese, int anno) {
		this.giorno = giorno;
		this.mese = mese;
		this.anno = anno;
	}

	public int getGiorno() {
		return giorno;
	}
	public int getMese() {
		return mese;
	}
	public int getAnno() {
		return anno;
	}

	public int hashCode() {
		return (anno+"-"+mese+"-"+anno).hashCode();
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Data other = (Data) obj;
		if (anno != other.anno)
			return false;
		if (giorno != other.giorno)
			return false;
		if (mese != other.mese)
			return false;
		return true;
	}

	public String toString() {
		return "Data [giorno=" + giorno + ", mese=" + mese + ", anno=" + anno + "]";
	}
}
