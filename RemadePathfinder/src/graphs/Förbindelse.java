package graphs;

import javax.swing.JComponent;

public class F�rbindelse<K> extends JComponent {

	private K from;
	private K destination;
	private int weight;
	private String name;
	/*
	private int fromNodeX;	//F�rsta punkten i f�rbindelsen som linjen ska g� fr�n
	private int fromNodeY;	//Andra punkten i f�rbindelsen som linjen ska g� fr�n
	private int toNodeX;
	private int toNodeY;
	*/
	

	public F�rbindelse(K from, K destination, String name, int weight) {
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
		return from+" �r f�rbunden med "+destination;
	}


	
}
