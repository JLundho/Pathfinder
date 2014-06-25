package graphs;

import javax.swing.JComponent;

public class Förbindelse<K> extends JComponent {

	private K from;
	private K destination;
	private int weight;
	private String name;
	/*
	private int fromNodeX;	//Första punkten i förbindelsen som linjen ska gå från
	private int fromNodeY;	//Andra punkten i förbindelsen som linjen ska gå från
	private int toNodeX;
	private int toNodeY;
	*/
	

	public Förbindelse(K from, K destination, String name, int weight) {
		this.from = from;
		this.destination = destination;
		this.weight = weight;
		this.name = name;
	}
	
	public K getDestination()
	{
		return destination;
	}
	
	public K getFrom()
	{
		return from;
	}
	public int getWeight()
	{
		return weight;
	}


	public String getName() 
	{
		return name;
	}
	
	public String toString() 
	{
		return from+" är förbunden med "+destination;
	}


	
}
