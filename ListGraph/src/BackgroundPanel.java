import javax.swing.*;

import java.awt.Dimension;
import java.awt.Graphics;


public class BackgroundPanel extends JPanel {

	private ImageIcon background;
	
	public BackgroundPanel(String path)
	{
		background = new ImageIcon(path);
		setLayout(null);
		Dimension size = new Dimension(background.getIconWidth(),background.getIconHeight());
		setPreferredSize(size);
		setMaximumSize(size);
		setMinimumSize(size);
	}
	
			
	protected void paintComponent(Graphics g) 
	{								
		super.paintComponent(g);
		g.drawImage(background.getImage(), 0, 0, this);
	}
	
//Line2D.double i BackgroundPanel klassen
//FooBar torsdag handledning PROG2 maila Jozef - Kolla om handledare kan redovisa. 
}
