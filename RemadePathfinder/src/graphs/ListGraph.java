package graphs;

import java.util.*;
import java.util.Map.Entry;

import javax.print.attribute.standard.Destination;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.sun.org.apache.xerces.internal.dom.DeepNodeListImpl;


public class ListGraph<K> extends Graphs implements Graph<K> {
	Multimap<K, List<F�rbindelse>> nodes;
	Multimap<K, K> directConnections;
	List <F�rbindelse<K>> v�gar;
	
	public ListGraph(){
		nodes  = ArrayListMultimap.create();
		directConnections  = ArrayListMultimap.create();
		v�gar = new ArrayList<F�rbindelse<K>>();

	}
	
	public void	add(K node) {
		nodes.put(node, new ArrayList<F�rbindelse>());
	}
	
	public void connect(K from, K destination, String name, int weight) 
	{
		directConnections.put(from, destination);
		directConnections.put(destination, from);
		
		F�rbindelse f1 = new F�rbindelse(from, destination, name, weight);
		F�rbindelse f2 = new F�rbindelse(destination, from, name, weight);
		
		v�gar.add(f1);
		v�gar.add(f2);
	}
	
	public List<K> getEdgesFrom(K node)
	{
		List <K> fromV�gar = new ArrayList<K>();
		for(F�rbindelse<K> f : v�gar)
		{
			if(f.getFrom().equals(node))
			{
				fromV�gar.add(f.getDestination());
			}
		}
		return fromV�gar;
	}
	
	
	public List findAnyPath(K from, K destination)
	{
		List<F�rbindelse<K>> via = new ArrayList<F�rbindelse<K>>();
		HashSet<K> bes�kta = new HashSet<K>();
		bes�kta.add(from);
		K whereFrom = from;
		
		if(pathExists(from, destination))
		{
			while(!whereFrom.equals(destination))
			{
			List <K> fromV�gar = getEdgesFrom(whereFrom);
				for(K k : fromV�gar)
				{
					if(!bes�kta.contains(k))
					{
					if(k.equals(destination))	//Om den angr�nsande noden �r destinationen loopas f�rbindelser igenom, r�tt f�rbindelse hittas och loopen avslutas
					{
						for(F�rbindelse f : v�gar)
						{
							if(f.getFrom().equals(whereFrom) && f.getDestination().equals(destination))
							{
								via.add(f);
								return via;

							}
						}
					}

					else if(pathExists(k, destination) && !k.equals(from) && !bes�kta.contains(k)) //Om en v�g finns mellan noden och destinationen samt att noden inte �r from-noden
					{
						bes�kta.add(k);
						for(F�rbindelse f : v�gar)
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
						bes�kta.add(k);
					}
				}
				}
			}
		}
		else
		{
			System.out.println("Ingen v�g finns!");
			return null;
		}
		return via;


		
	}


		
		
	public void dfs(K where, HashSet<K> bes�kta) 
	{
		bes�kta.add(where);
		for (F�rbindelse<K> f : v�gar)
		{
			K to = f.getDestination();
			if (!bes�kta.contains(to)) 
			{
				dfs(to, bes�kta);
			}
		}
	}
	
	
	public boolean pathExists(K from, K destination) 
	{
		HashSet<K> bes�kta = new HashSet<K>();
		dfs(from, bes�kta);
		return bes�kta.contains(destination);
	}



	public F�rbindelse getEdgesBetween(K from, K destination) {
		for(F�rbindelse f : v�gar)
		{
			if(f.getFrom().equals(from) && f.getDestination().equals(destination))
			{
				return f;
			}
		}
		return null;
		
	}





}
