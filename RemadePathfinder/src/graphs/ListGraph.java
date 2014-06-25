package graphs;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class ListGraph<K> extends Graphs implements Graph<K> {
	Multimap<K, List<F�rbindelse>> nodes;
	Multimap<K, K> directConnections;
	Multimap<K, K> connections;
	HashSet<K> bes�kta;
	List <F�rbindelse> v�gar;
	
	
	public ListGraph(){
		nodes  = ArrayListMultimap.create();
		directConnections  = ArrayListMultimap.create();
		connections  = ArrayListMultimap.create();
		bes�kta = new HashSet<K>();
		v�gar = new ArrayList<F�rbindelse>();
	}
	
	public void	add(K node) {
		nodes.put(node, new ArrayList<F�rbindelse>());
		System.out.println(node+" har adderats!");
	}
	
	public void connect(K from, K destination, String name, int weight) 
	{
		directConnections.put(from, destination);
		directConnections.put(destination, from);
		
		F�rbindelse f1 = new F�rbindelse(from, destination, name, weight);
		F�rbindelse f2 = new F�rbindelse(destination, from, name, weight);
		
		v�gar.add(f1);
		v�gar.add(f2);
		
		System.out.println(from+" �r nu sammanbunden med destinationen "+destination);
	}
	
	public void getEdgesFrom(K node)
	{
		for (Map.Entry entry : directConnections.entries()) 
		{
			if(entry.getKey().equals(node))
			{
				System.out.print(entry+"\n");
			}
		}
		
	}
	
	public boolean pathExists(K from, K destination) 
	{
		bes�kta.add(from);
		for (F�rbindelse<K> f : v�gar)
		{
			if (!bes�kta.contains(destination)) 
			{
				dfs(f.getDestination(), bes�kta);
			}
		}
		if(bes�kta.contains(destination)){
			System.out.println("True");
				}
		return bes�kta.contains(destination);
	}
	
	public void dfs(K where, HashSet<K> bes�kta) 
	{
		bes�kta.add(where);
		for (F�rbindelse<K> f : v�gar)
		{
			if (!bes�kta.contains(f.getDestination())) 
			{
				dfs(f.getDestination(), bes�kta);
			}
		}
	}
	

	

	/*
	private void dfs(K where, Set<Nod> bes�kta) {
		bes�kta.add(where); // Adderar noden som precis bes�ktes till den
							// bes�kta listan
		for (F�rbindelse f : nodes.get(where)) // Loopar genom alla f�rbindelser
												// fr�n staden som bes�ks(granne
												// till from)
		{
			if (!bes�kta.contains(f.getTo())) // Om staden ej bes�kts
												// f�rut(finns inte i listan
												// bes�kta)
			{ // utf�rs samma s�kning av f�rbindelser som gjordes p� from,
				// staden l�ggs sedan till i bes�kta.
				dfs(f.getTo(), bes�kta);
			}
		}
	}*/
	
	
	
	public void displayConnections() {
		for (Map.Entry entry : connections.entries()) {
			System.out.print(entry+"\n");
			}
		
	}

	public String toString(){
		String s = "";
		for(K k : nodes.keySet()){
			System.out.println(k+" har adderats i HashMapen");
		}
		return s;
	}


}
