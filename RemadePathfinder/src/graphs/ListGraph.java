package graphs;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


public class ListGraph<K> extends Graphs implements Graph<K> {
	Multimap<K, List<Förbindelse>> nodes;
	
	public ListGraph(){
		nodes  = ArrayListMultimap.create();
	}
	
	public void	add(K node) {
		nodes.put(node, new ArrayList<Förbindelse>());
		System.out.println(node+" har adderats!");
	}
	
	public void connect(K from, K to, String name, int weight) {
		
	}
	public void displayConnections() {
		for (K key : nodes.keys()) 
		{
		System.out.print(key+"\n");
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
