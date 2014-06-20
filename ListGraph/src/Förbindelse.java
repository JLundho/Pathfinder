import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

public class F�rbindelse extends JComponent
{
	private Stad from;
	private Stad to;
	private int tid;
	private String namn;
	private int fromNodeX;	//F�rsta punkten i f�rbindelsen som linjen ska g� fr�n
	private int fromNodeY;	//Andra punkten i f�rbindelsen som linjen ska g� fr�n
	private int toNodeX;
	private int toNodeY;
	
	private int x, y, height, width;

	
	public F�rbindelse(Stad from, Stad to, int tid, String namn)
	{
		this.from = from;
		this.to = to;
		this.tid = tid;
		this.namn = namn;

		fromNodeX = from.getxkoordinat();
		fromNodeY = from.getykoordinat();
		toNodeX = to.getxkoordinat();
		toNodeY = to.getykoordinat();
		

		if(fromNodeX >= toNodeX)	//Om noden �r �t "fel" h�ll(icke dragen fr�n v�nster till h�ger) byts positionen p� de tv�
		{

			x = toNodeX;
			width = fromNodeX-toNodeX+10;	//Ursprungligen b�rjar strecket fr�n from-koordinaten och ritar strecket
			
			if(fromNodeY <= toNodeY)
			{
				x = fromNodeX;
			}
		}
		else
		{
			x = fromNodeX;
			width = toNodeX-fromNodeX+10;
			
			if(fromNodeY >= toNodeY)
			{
				height -= (toNodeY-fromNodeY);
			}
		}
		if(fromNodeY >= toNodeY)
		{

			y = toNodeY;
			height = fromNodeY-toNodeY+10;
			
			if(fromNodeX <= toNodeX)
			{
				y = fromNodeY;
			}
			
		}
		else
		{
			y = fromNodeY;
			height = toNodeY-fromNodeY+10;
			
			if(fromNodeX >= toNodeX)
			{
				x = fromNodeX;
			}

		}
		
		setBounds(x, y, width, height);
		Dimension size = new Dimension(width, height);
	    setPreferredSize(size);
	    setMinimumSize(size);
	    setMaximumSize(size);
	    }
		
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
			g.drawLine(0+10, 0+10, width, height);
			g.setColor(Color.BLACK);
	}
	
	public Stad getFrom()
	{
		return from;
	}

	public Stad getTo()
	{
		return to;
	}
	
	public int getTid()
	{
		return tid;
	}
	
	public void setTid(int tid) 
	{
		this.tid = tid;
	}
	
	public String getNamn() 
	{
		return namn;
	}
	
	public void setFardMedel(String namn) 
	{
		this.namn = namn;
	}

	public String toString()
	{
		return "Du kan �ka fr�n "+from+" till "+to+" med "+namn+" och det tar "+tid+" minuter";	
	}
}
