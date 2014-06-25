package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import graphs.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener; 



public class RemadePathFinder extends JFrame{
	
	JMenu[] Menu = new JMenu[3]; 
	JButton[] northButtons = new JButton[5]; 
	JMenuItem[] arkivItems = new JMenuItem[5];
	JMenuItem[] operationItems = new JMenuItem[5];
	private BackgroundPanel background;
				
	Graph<Nod> g = new ListGraph<Nod>();

	
	int xkoordinat;																			//X-koordinat för musklick
	int ykoordinat;	
	
	public RemadePathFinder()
	{
		super("PathFinder Remastered");	
		
		JMenuBar mb = new JMenuBar();
		setJMenuBar(mb);
		
		JPanel north = new JPanel();
		north.setLayout(new FlowLayout());
		
		Menu[0] = new JMenu("Arkiv");
		Menu[1] = new JMenu("Operationer");
		Menu[2] = new JMenu("Om");

		String[] miButtonNames = {"Hitta väg", "Visa förbindelse", "Ny nod", "Ny förbindelse", "Ändra förbindelse"}; 
		String[] arkivNames = {"Nytt", "Öppna", "Spara", "Spara som", "Avsluta"}; 
		

		//Adderar miButtonNames till menybaren
		for (int i = 0; i < arkivNames.length; i++) {  
			arkivItems[i] = new JMenuItem(arkivNames[i]); 
			Menu[0].add(arkivItems[i]);
		}
		arkivItems[0].addActionListener(new nyFilKnappLyss());
		
		
		
		//Adderar operationNames till menybaren
		for (int i = 0; i < miButtonNames.length; i++) {  
			operationItems[i] = new JMenuItem(miButtonNames[i]); 
			Menu[1].add(operationItems[i]);
		}
		operationItems[0].addActionListener(new HittaVägKnappLyss());
		operationItems[1].addActionListener(new VisaFörbindelseKnappLyss());
		operationItems[2].addActionListener(new NyNodKnappLyss());
		operationItems[3].addActionListener(new NyFörbindelseKnappLyss());
		
		for (int i = 0; i < northButtons.length; i++) {  
			northButtons[i] = new JButton(miButtonNames[i]);
			north.add(northButtons[i], BorderLayout.NORTH);
		}
		northButtons[0].addActionListener(new HittaVägKnappLyss());
		northButtons[1].addActionListener(new VisaFörbindelseKnappLyss());
		northButtons[2].addActionListener(new NyNodKnappLyss());
		northButtons[3].addActionListener(new NyFörbindelseKnappLyss());
		
		for (JMenu j : Menu) {
			mb.add(j);
		}
		//Menu[2].addMenuListener(new omKnappLyss());
		
		
		add(north, BorderLayout.NORTH);
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		
	}
	
			
	// BUTTON LISTENERS ************************************************************
	// BUTTON LISTENERS ************************************************************
	// BUTTON LISTENERS ************************************************************
	// BUTTON LISTENERS ************************************************************
	
	class NyNodKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyNodKnappAve) 
		{
			
			background.addMouseListener(CenterLyss);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));

		}

	}
	
	class NyFörbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyFörbindelseKnappAve) {

				nyFörbindelseForm nff = new nyFörbindelseForm();
				int nySvar = JOptionPane.showConfirmDialog(null, nff,"Ny förbindelse", JOptionPane.OK_CANCEL_OPTION); 
				for (;;) {
					{
						if (nySvar == JOptionPane.OK_OPTION) 
						{

							String fardmedel = nff.fardmedelsFalt.getText();
							try {
								int tidInt = Integer.parseInt(nff.tidFalt.getText());
								if (fardmedel.isEmpty() || (fardmedel.matches(".*\\d.*")))
								{
									JOptionPane.showMessageDialog(null,"Färdmedelsfältet är tomt eller innehåller siffror!");
									break;
									
								} else 
								{
									g.connect(s1, s2, fardmedel, tidInt);
									break;
								}
							}

							catch (NumberFormatException e) {
								if (nff.tidFalt.getText().equals("")) {
									//extra += "och även tomt!";

								}
								break;
							}

						} else {

							break;

						}
					}
				}
			}
		}
	
	class VisaFörbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent VisaKnappAve) 
		{
		g.getEdgesBetween(s1, s2);
		}
	}
	
	class HittaVägKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent HittaVägKnappAve) 
		{
		g.pathExists(s1, s2);
		}
	}
	

	
	class omKnappLyss implements MenuListener {
		public void menuSelected(MenuEvent e) 
		{
			JOptionPane.showMessageDialog(null, "Mitt namn är Jonas Lundholm och det här är ett program som är till för\n"
					+ "att ladda kartor, sätta ut punkter på kartan och skapa förbindelser mellan punkterna\n"
					+ "på kartan. Uppskattar du programmet, hittar buggar eller vill lämna åsikter\n"
					+ "finns jag tillgänglig på: \nemail: jonas.l.lundholm@gmail.com", "Om", JOptionPane.INFORMATION_MESSAGE);	
		}

		public void menuCanceled(MenuEvent arg0) {
			//Empty method because Java doesn't allow me to remove it
		}

		public void menuDeselected(MenuEvent arg0) {
			//Empty method because Java doesn't allow me to remove it
		}
	}
	
	
	// FILES ************************************************************
	// FILES ************************************************************
	// FILES ************************************************************
	// FILES ************************************************************
	
	private JFileChooser jfc = new JFileChooser("C:\\");
	
	
	class nyFilKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int resultat = jfc.showOpenDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION) {
				for (JButton b : northButtons) {
					b.setEnabled(true);
				}

				File f = jfc.getSelectedFile();
				String path = f.getAbsolutePath();

				background = new BackgroundPanel(path);

				add(background, BorderLayout.CENTER);
				validate();
				repaint();
				pack();
			}
		}
	}

	// MOUSE LISTENERS ************************************************************
	// MOUSE LISTENERS ************************************************************
	// MOUSE LISTENERS ************************************************************
	// MOUSE LISTENERS ************************************************************
	
	
	
	CenterLyss CenterLyss = new CenterLyss(); //MouseClickListener som adderas till bakgrunden
	class CenterLyss extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent me) 
		{
			for (JButton b : northButtons)	
			{
				b.setEnabled(true);
			}
			xkoordinat = me.getX()-9;
			ykoordinat = me.getY()-9;


			nyNodForm nnf = new nyNodForm();
			
				try
				{
					for (;;) 
					{
						int nySvar = JOptionPane.showConfirmDialog(null, nnf, "Ny nod",
						JOptionPane.OK_CANCEL_OPTION); 							// Dialogfönster som skapar
																				// ny deltagare
						if (nySvar == JOptionPane.OK_OPTION) 
						{
							String nodNamn = nnf.nyNodFalt.getText();

							Nod newNode = new Nod(nnf.nyNodFalt.getText(), xkoordinat, ykoordinat);
							if(nodNamn.isEmpty() || (nodNamn.matches(".*\\d.*")))
							{
								JOptionPane.showMessageDialog(null, "Nodnamn är tomt eller innehåller siffror!");
								setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								background.removeMouseListener(nkl);
								break;
							}

							newNode.addMouseListener(nkl);
							newNode.setBounds(xkoordinat, ykoordinat, 200, 50);
							background.add(newNode);

							background.removeMouseListener(CenterLyss);
							setCursor(Cursor.getDefaultCursor());
							g.add(newNode);
					
							validate();
							repaint();

							System.out.println("X: "+(newNode.getxkoordinat())+", Y: "+newNode.getykoordinat());

							break;
						} 
						else 
						{
							background.removeMouseListener(CenterLyss);
							background.setCursor(Cursor.getDefaultCursor());
							System.out.println("Noden har ej adderats!");

							break;
						}
					}
				}
				catch(IndexOutOfBoundsException e)
				{

				}
		}
	}
	
	
	NodKlickLyss nkl = new NodKlickLyss();	

	private Nod s1 = null, s2 = null;

	class NodKlickLyss extends MouseAdapter	//överskuggar "onödiga" metoder från MouseListener.
	{
		@Override
		public void mouseClicked(MouseEvent mev) 
		{
			Nod newNode = (Nod)mev.getSource();

													//Om båda är tomma adderas noden till den första minnesplatsen
			if(mev.getClickCount() < 2)
				{
					if (s1 == null && newNode != s2)
					{
						s1 = newNode;
						System.out.println("s1 = "+s1);
						newNode.setSelected(true);
					}
					else if (s2 == null && newNode != s1) 	// Om första minnesplatsen är full och 
					{										//nya noden inte redan är där adderas den till andra
						s2 = newNode;
						System.out.println("s2 = "+s2);
						newNode.setSelected(true);
					}
					else if (s1 == newNode)					//Om första minnesplatsen redan innehar värdet avmarkeras den
					{
						s1 = null;
						System.out.println("s1 är öppen!");
						newNode.setSelected(false);
					} 
					else if (s2 == newNode)					//Om andra minnesplatsen redan innehar värdet avmarkeras den
					{
						s2 = null;
						System.out.println("s2 är öppen");
						newNode.setSelected(false);
					}

					else if (s1 != null && s2 != null && newNode != s1 || newNode != s2)					//Om andra minnesplatsen redan innehar värdet avmarkeras den
					{
						System.out.println("Båda noderna är valda!");
					}
					validate();
					repaint();
				}
			else {
				System.out.println("Två noder har ej valts!");
			}

		}
	}

	

	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	
	class nyFörbindelseForm extends JPanel {


		JTextField tidFalt;
		JTextField fardmedelsFalt;

		nyFörbindelseForm() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel forbindelserad = new JPanel();
			JPanel rad1 = new JPanel();
			JPanel rad2 = new JPanel();

			forbindelserad.add(new JLabel("Lägg till ny förbindelse"));

			rad1.add(new JLabel("Tid: "));
			tidFalt = new JTextField(10);
			rad1.add(tidFalt);

			rad2.add(new JLabel("Färdmedel: "));
			fardmedelsFalt = new JTextField(10);
			rad2.add(fardmedelsFalt);

			add(forbindelserad);
			add(rad1);
			add(rad2);
		}
	}
	
	class nyNodForm extends JPanel {
		private JTextField nyNodFalt; // Fältet där "från"-förbindelsen anges, oriktad
		
		public nyNodForm() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Nodens namn:"));
			nyNodFalt = new JTextField(10);
			rad1.add(nyNodFalt);
			add(rad1);
		}
		
		public String getName(){
			return nyNodFalt.getText();
		}
		
	}
	

	public static void main(String[] args) {
		new RemadePathFinder();
	}

}


