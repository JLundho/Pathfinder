package graphs;


public interface Graph<K> {
	
	public void add(K node);
	public void connect(K from, K destination, String name, int weight);
	public void getEdgesFrom(K node);
	public void getEdgesBetween(K from, K destination);
	public boolean pathExists(K from, K destination);
	public void displayConnections();
	
	
	//public String toString();
	

}
