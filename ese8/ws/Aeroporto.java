package it.unical.dimes.reti.ese8.ws;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;

public class Aeroporto implements AeroportoIF{
	
	private HashMap<Data, HashMap<String, LinkedList<Volo>>> db;
	
	public Aeroporto(){
		db = new HashMap<Data, HashMap<String, LinkedList<Volo>>>();
		
		addVolo(new Volo("Roma", new Data(23, 1, 2016), new Orario(10, 00), "Roma10.00"));
		addVolo(new Volo("Roma", new Data(23, 1, 2016), new Orario(12, 00), "Roma12.00"));
		addVolo(new Volo("Roma", new Data(23, 1, 2016), new Orario(18, 00), "Roma18.00"));
		
		addVolo(new Volo("Roma", new Data(24, 1, 2016), new Orario(10, 00), "Roma10.00"));
		
		addVolo(new Volo("Roma", new Data(25, 1, 2016), new Orario(12, 00), "Roma12.00"));
		addVolo(new Volo("Roma", new Data(25, 1, 2016), new Orario(18, 00), "Roma18.00"));
		
		addVolo(new Volo("Milano", new Data(25, 1, 2016), new Orario(6, 00), "Roma06.00"));
		
	}
	
	

	public String privoVolo(String citta, Data data) {
		if(!db.containsKey(data) || !db.get(data).containsKey(citta))
			return "Nessun aereo";
		
		return db.get(data).get(citta).getFirst().getVoloId();
	}

	public Orario orarioVolo(String voloId, Data data) {		
		LinkedList<Volo> tmp;
		if(db.containsKey(data)){		
			for (Entry<String, LinkedList<Volo>> entry: db.get(data).entrySet()) {
				tmp = entry.getValue();
				for (Volo volo2 : tmp) {
					if(volo2.getVoloId().equals(voloId))
						return volo2.getOrario();
				}
			}
		}
		return new Orario(-1, -1);
	}
	
	private void addVolo (Volo v){
		if(!db.containsKey(v.getData()))
			db.put(v.getData(), new HashMap<String, LinkedList<Volo>>());
		
		HashMap<String, LinkedList<Volo>> hm = db.get(v.getData());
		
		if(!hm.containsKey(v.getCitta()))
			hm.put(v.getCitta(), new LinkedList<Volo>());
		
		LinkedList<Volo> list = hm.get(v.getCitta());
		list.add(v);
		
		Collections.sort(list, new OrarioComparator());		
	}
	
	public static void main(String[] args) {
		Aeroporto ae = new Aeroporto();
		System.out.println(ae);
		
		System.out.println(ae.privoVolo("Roma", new Data(25, 1, 2016)));
		System.out.println(ae.orarioVolo("Roma10.00", new Data(24, 1, 2016)));
	}
	
}
