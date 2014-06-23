package graphs;

import java.awt.Component;
import java.io.Serializable;
import java.util.*;

public interface Graph<N> extends Serializable {
	
	public void add(N node);
	public void connect(N from, N to, String name, int weight);
	public String toString();
	public List<N> getNodes();
	

}
