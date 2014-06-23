package graphs;

import java.util.*;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class ListGraph<N> extends Graphs implements Graph<N> {
	Multimap<N, Set<Förbindelse<N>>> nodes = ArrayListMultimap.create();

	public ListGraph(){
		nodes = ArrayListMultimap.create();
	}
	
	public void	add(N node) {
		nodes.put(node, new HashSet<Förbindelse<N>>());
	}
	
	public List<N> getNodes(){
		return new ArrayList<N>(nodes.keySet());
	}
	
	

	public void connect(N from, N to, String name, int weight) {
		// TODO Auto-generated method stub
		
	}
	
	public String toString(){
		String s = "";
		for(N n : nodes.keySet()){
			System.out.println(n+" har adderats i HashMapen");
		}
		return s;
	}

	
	

}
