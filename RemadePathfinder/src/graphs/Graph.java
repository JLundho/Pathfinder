package graphs;

import java.util.List;


public interface Graph<K> {
	
	public void add(K node);
	public void connect(K from, K destination, String name, int weight);
	public List getEdgesFrom(K node);
	public Förbindelse<K> getEdgesBetween(K from, K destination);
	public List findAnyPath(K where, K destination);
	public boolean pathExists(K from, K destination);
	
	

}
