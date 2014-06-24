package graphs;

import javax.swing.JComponent;


public class Nod extends JComponent implements Comparable<Nod>{

	private String nodNamn;	

	public Nod(String nodNamn)
	{
		this.nodNamn = nodNamn;	
	}
	
	public String getNamn(){
		return nodNamn;
	}
	
	public String toString(){
		return nodNamn;
	}

	public int compareTo(Nod l) {
		return nodNamn.compareTo(l.getName());
	}
	
}
