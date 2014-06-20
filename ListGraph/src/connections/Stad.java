package connections;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import java.awt.Dimension;


public class Stad extends JComponent
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String namn;
	private int xkoordinat;
	private int ykoordinat;
	private boolean isSelected;
	
	public Stad(String namn, int xkoordinat, int ykoordinat)
	{
		this.namn = namn;
		this.xkoordinat = xkoordinat-5;
		this.ykoordinat = ykoordinat-5;
		
		setBounds(this.xkoordinat, this.ykoordinat, 20, 20);
		Dimension size = new Dimension(20,20);
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
		
		isSelected = false;
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

	public String getNamn()
	{
		return namn;
	}
	
	public int getxkoordinat()
	{
		return xkoordinat;
	}

	public int getykoordinat()
	{
		return ykoordinat;
	}

	public void setSelected(Boolean b)
	{	
		isSelected = !isSelected;
		repaint();
	}
	
	public boolean isSelected()
	{
		return isSelected;
	}
	

	public String toString()
	{
		return namn;
	}
	

}