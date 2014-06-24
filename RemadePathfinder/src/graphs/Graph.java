package graphs;


public interface Graph<K> {
	
	public void add(K node);
	public void connect(K from, K to, String name, int weight);
	public void displayConnections();
	
	
	//public String toString();
	

}
