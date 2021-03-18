package it.unical.dimes.reti.ese8.ws;

import java.util.Comparator;

public class OrarioComparator implements Comparator<Volo> {

	public int compare(Volo o1, Volo o2) {
		if(o1.getOrario().getOre()==o2.getOrario().getOre()
				&& o1.getOrario().getMinuti()==o2.getOrario().getMinuti())
			return 0;		
		if(o1.getOrario().getOre()<o2.getOrario().getOre())
			return -1;
		else if(o1.getOrario().getOre()>o2.getOrario().getOre())
			return +1;
		else if (o1.getOrario().getMinuti()<o2.getOrario().getMinuti())
			return -1;
		else if (o1.getOrario().getMinuti()>o2.getOrario().getMinuti())
			return +1;
		
		return 0;
	}

}
