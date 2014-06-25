package graphs;

import java.util.*;
import java.util.Map.Entry;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class ListGraph<K> extends Graphs implements Graph<K> {
	Multimap<K, List<Förbindelse>> nodes;
	Multimap<K, K> directConnections;
	Multimap<K, K> connections;
	HashSet<K> besökta;
	List <Förbindelse> vägar;
	
	
	public ListGraph(){
		nodes  = ArrayListMultimap.create();
		directConnections  = ArrayListMultimap.create();
		connections  = ArrayListMultimap.create();
		besökta = new HashSet<K>();
		vägar = new ArrayList<Förbindelse>();
	}
	
	public void	add(K node) {
		nodes.put(node, new ArrayList<Förbindelse>());
		System.out.println(node+" har adderats!");
	}
	
	public void connect(K from, K destination, String name, int weight) 
	{
		directConnections.put(from, destination);
		directConnections.put(destination, from);
		
		Förbindelse f1 = new Förbindelse(from, destination, name, weight);
		Förbindelse f2 = new Förbindelse(destination, from, name, weight);
		
		vägar.add(f1);
		vägar.add(f2);
		
		System.out.println(from+" är nu sammanbunden med destinationen "+destination);
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
		besökta.add(from);
		for (Förbindelse<K> f : vägar)
		{
			if (!besökta.contains(destination)) 
			{
				dfs(f.getDestination(), besökta);
			}
		}
		if(besökta.contains(destination)){
			System.out.println("True");
				}
		return besökta.contains(destination);
	}
	
	public void dfs(K where, HashSet<K> besökta) 
	{
		besökta.add(where);
		for (Förbindelse<K> f : vägar)
		{
			if (!besökta.contains(f.getDestination())) 
			{
				dfs(f.getDestination(), besökta);
			}
		}
	}
	

	

	/*
	private void dfs(K where, Set<Nod> besökta) {
		besökta.add(where); // Adderar noden som precis besöktes till den
							// besökta listan
		for (Förbindelse f : nodes.get(where)) // Loopar genom alla förbindelser
												// från staden som besöks(granne
												// till from)
		{
			if (!besökta.contains(f.getTo())) // Om staden ej besökts
												// förut(finns inte i listan
												// besökta)
			{ // utförs samma sökning av förbindelser som gjordes på from,
				// staden läggs sedan till i besökta.
				dfs(f.getTo(), besökta);
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
