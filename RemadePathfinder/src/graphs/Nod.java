package graphs;

import javax.swing.JComponent;

import java.awt.Color;
import java.awt.Graphics;

public class Nod extends JComponent implements Comparable<Nod>{

	private String nodNamn;
	private int xkoordinat;
	private int ykoordinat;
	private boolean isSelected;

	public Nod(String nodNamn, int xkoordinat, int ykoordinat)
	{
		this.nodNamn = nodNamn;	
		this.xkoordinat = xkoordinat;	
		this.ykoordinat = ykoordinat;	
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);

		if(isSelected)
		{
			g.setColor(Color.RED);
			g.fillOval(0, 0, 20, 20);
		}
		else
		{
			g.setColor(Color.BLUE);
			g.fillOval(0, 0, 20,20 );
		}

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
	
	public void setSelected(Boolean b)
	{	
		isSelected = !isSelected;
		repaint();
	}
	
	
	public int getxkoordinat()
	{
		return xkoordinat;
	}

	public int getykoordinat()
	{
		return ykoordinat;
	}
}
