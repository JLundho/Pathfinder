package graphs;

import java.util.*;
import java.util.Map.Entry;

import javax.print.attribute.standard.Destination;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;


public class ListGraph<K> extends Graphs implements Graph<K> {
	Multimap<K, List<Förbindelse>> nodes;
	Multimap<K, K> directConnections;
	List <Förbindelse<K>> vägar;
	
	public ListGraph(){
		nodes  = ArrayListMultimap.create();
		directConnections  = ArrayListMultimap.create();
		vägar = new ArrayList<Förbindelse<K>>();

	}
	
	public void	add(K node) {
		nodes.put(node, new ArrayList<Förbindelse>());
	}
	
	public void connect(K from, K destination, String name, int weight) 
	{
		directConnections.put(from, destination);
		directConnections.put(destination, from);
		
		Förbindelse f1 = new Förbindelse(from, destination, name, weight);
		Förbindelse f2 = new Förbindelse(destination, from, name, weight);
		
		vägar.add(f1);
		vägar.add(f2);
	}
	
	public List<K> getEdgesFrom(K node)
	{
		List <K> fromVägar = new ArrayList<K>();
		for(Förbindelse<K> f : vägar)
		{
			if(f.getFrom().equals(node))
			{
				fromVägar.add(f.getDestination());
			}
		}
		return fromVägar;
	}
	
	
	public List findAnyPath(K from, K destination)
	{
		List<Förbindelse<K>> via = new ArrayList<Förbindelse<K>>();
		HashSet<K> besökta = new HashSet<K>();
		besökta.add(from);
		K whereFrom = from;
		
		if(pathExists(from, destination))
		{
			while(!whereFrom.equals(destination))
			{
			List <K> fromVägar = getEdgesFrom(whereFrom);
				for(K k : fromVägar)
				{
					if(!besökta.contains(k))
					{
					if(k.equals(destination))	//Om den angränsande noden är destinationen loopas förbindelser igenom, rätt förbindelse hittas och loopen avslutas
					{
						for(Förbindelse f : vägar)
						{
							if(f.getFrom().equals(whereFrom) && f.getDestination().equals(destination))
							{
								via.add(f);
								return via;

							}
						}
					}

					else if(pathExists(k, destination) && !k.equals(from) && !besökta.contains(k)) //Om en väg finns mellan noden och destinationen samt att noden inte är from-noden
					{
						besökta.add(k);
						for(Förbindelse f : vägar)
						{
							if(k.equals(f.getFrom()))
							{
								whereFrom = k;
								if(!via.contains(f))
								{
									via.add(f);
								}
							}
						}
						
					}
					else
					{
						besökta.add(k);
					}
				}
				}
			}
		}
		else
		{
			System.out.println("Ingen väg finns!");
			return null;
		}
		return via;


		
	}


		
		
	public void dfs(K where, HashSet<K> besökta) 
	{
		besökta.add(where);
		for (Förbindelse<K> f : vägar)
		{
			K to = f.getDestination();
			if (!besökta.contains(to)) 
			{
				dfs(to, besökta);
			}
		}
	}
	
	
	public boolean pathExists(K from, K destination) 
	{
		HashSet<K> besökta = new HashSet<K>();
		dfs(from, besökta);
		return besökta.contains(destination);
	}



	public Förbindelse getEdgesBetween(K from, K destination) {
		for(Förbindelse f : vägar)
		{
			if(f.getFrom().equals(from) && f.getDestination().equals(destination))
			{
				return f;
			}
		}
		return null;
		
	}





}
