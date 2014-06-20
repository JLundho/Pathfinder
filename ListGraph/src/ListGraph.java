import java.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.xml.transform.Source;


public class ListGraph extends JFrame {
	
	private Map<Stad, List<Förbindelse>> nodes = new HashMap<Stad, List<Förbindelse>>();	//Skapar och deklarerar HashMapen
	private JFileChooser jfc = new JFileChooser("C:\\");
	
	private List<Förbindelse> fromList = new ArrayList<Förbindelse>();						//Lagrar alla from-noder(to-noder ingår även)
	private List<Förbindelse> toList = new ArrayList<Förbindelse>();						//Lagrar alla to-noder(from-noder ingår även)
	private List<Förbindelse> vägar = new ArrayList<Förbindelse>();
	
	private ArrayList<String> nodNamnLista = new ArrayList<String>();
	
	
	private BackgroundPanel background;

	private JTextArea visaFörbindelseFält = new JTextArea();									//Textruta där förbindelser från en nod visas
	
	
	int xkoordinat;																			//X-koordinat för musklick
	int ykoordinat;																			//Y-koordinat för musklick

	JMenu[] arkivMenu = new JMenu[2];														//Array av menyer med 2 platser för arkiv
	JMenuItem[] opsMenu = new JMenuItem[7];													//Array av menyer med 7 platser för Operationer(uppfyller samma sak som knappar)
	JButton[] buttons = new JButton[5];

	
	
	
	//Konstruktor och grafiskt gränssnitt
	ListGraph() 
	{
		super("Nodmonstret");

		//JMenuBar - Ursprungliga knappar 'som läggs till i menybaren
		JMenuBar mb = new JMenuBar();
		arkivMenu[0] = new JMenu("Arkiv");
		arkivMenu[1] = new JMenu("Operationer");
		setJMenuBar(mb);
		for (JMenu j : arkivMenu){
			mb.add(j);
		}
		
		//Subknapparna till "Arkiv"
		JMenuItem[] arkiv = new JMenuItem[1];		
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());
		
		JMenuItem[] operationer = new JMenuItem[1];	
		
		for (JMenuItem i : arkiv)	
		{
			arkivMenu[0].add(i);
		}
		
		
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());
		
		
		JPanel north = new JPanel(); 									
		north.setLayout(new FlowLayout());										
		

		buttons[0] = new JButton("Hitta väg");
		buttons[0].addActionListener(new HittaVägKnappLyss());
		buttons[1] = new JButton("Visa förbindelse");
		buttons[1].addActionListener(new visaFörbindelseKnappLyss());
		buttons[2] = new JButton("Ny nod");
		buttons[2].addActionListener(new NyNodKnappLyss());
		buttons[3] = new JButton("Ny förbindelse");
		buttons[3].addActionListener(new NyFörbindelseKnappLyss());
		buttons[4] = new JButton("Ändra förbindelse");
		

		for (JButton b : buttons)	
		{
			north.add(b, BorderLayout.NORTH);
			b.setEnabled(false);
		}
		
		add(north, BorderLayout.NORTH);


		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	} 
	
	class NyFörbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyFörbindelseKnappAve) {

			
			if(s1 != null && s2 != null)
			{
			nyFörbindelseForm nff = new nyFörbindelseForm();

			
			
	
			int nySvar = JOptionPane.showConfirmDialog(null, nff,
					"Ny förbindelse", JOptionPane.OK_CANCEL_OPTION); // Dialogfönster
																		// som
																		// skapar
																		// ny
																		// deltagare
			
			for (;;) {
				
				{
				if (nySvar == JOptionPane.OK_OPTION) 
				{

					
					
					String fardmedel = nff.fardmedelsFalt.getText();
					String extra = "";
					try 
					{
						int tidInt = Integer.parseInt(nff.tidFalt.getText());;
						if(fardmedel.isEmpty() || (fardmedel.matches(".*\\d.*")))// Kontrollerar om namnfält är tomt
						{
							JOptionPane.showMessageDialog(null, "Färdmedelsfältet är tomt eller innehåller siffror!");
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							background.removeMouseListener(nkl);
							break;
						}
						else
						{
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						connect(s1, s2, tidInt, nff.fardmedelsFalt.getText());
						background.removeMouseListener(nkl);
						break;
						}
					}

					catch (NumberFormatException e) 
					{
						if(nff.tidFalt.getText().equals(""))
						{
							extra += "och även tomt!";
							setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						}
				
						JOptionPane.showMessageDialog(null, "Tidfältet är felaktigt inskrivet " +extra);
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						background.removeMouseListener(nkl);
						break;
					}
					
				} 
				else 
				{
					
					setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
					background.removeMouseListener(nkl);
					break;
					
				}
			}
			}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Två noder måste väljas!");
				
			}
		}
	}
	
	
	class visaFörbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyFörbindelseKnappAve)
		{
			visaFörbindelseForm vff = new visaFörbindelseForm() ;

			
			if(PathExists(s1, s2))
			{
			GetConnectionBetween(s1, s2);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Ingen förbindelse finns!");
			}
		}
	}


	class nyFilKnappLyss implements ActionListener 
	{
		public void actionPerformed(ActionEvent e) 
		{
			int resultat = jfc.showOpenDialog(null);
			
			if (resultat == JFileChooser.APPROVE_OPTION)
			{		
				for (JButton b : buttons)	
				{
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

	
	class NyNodKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyNodKnappAve) 
		{
			for (JButton b : buttons)	
			{
				b.setEnabled(false);
			}
			background.addMouseListener(CenterLyss);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}

	}
	
	class HittaVägKnappLyss implements ActionListener{
		public void actionPerformed(ActionEvent hittaVägAve)
		{
			try{
				visaFörbindelseForm vff = new visaFörbindelseForm() ;
				
				GetConnectionBetween(s1, s2);
				GetAnyPath(s1, s2);
				
				}
				catch(NoSuchElementException nsee)
				{
					JOptionPane.showMessageDialog(null, "Två noder måste väljas!");
				}
			}

		
	}
	
	public List<Förbindelse> GetAnyPath(Stad from, Stad to)
	{
		Map<Stad, Stad> via = new HashMap<Stad, Stad>();
		Set<Stad> besökta = new HashSet<Stad>();
		
		if(PathExists(from, to))
		{
		for(Förbindelse f : fromList)
		{
			dfs2(f.getFrom(), f.getTo(), besökta, via);
			GetConnectionBetween(f.getFrom(), f.getTo());
		}
		
		System.out.println(""+via);
		
		List<Förbindelse> vägen = new ArrayList<Förbindelse>();
		
		Stad where = to;
		return vägen;
		}
		else
		{
			System.out.println("Ingen väg finns!");
			return null;
		}
		
		
	}
	
	
	private Förbindelse GetConnectionBetween(Stad from, Stad to)
	{
		for(Förbindelse f : vägar)
		{
				if(f.getFrom() == from && f.getTo() == to)
					{
					for(int results = 0; results < 1; results++)
					{
					if(PathExists(f.getTo(), to))
					{
					System.out.println(f);
					}
					}
					return f;
				
				}

		}
			
			return null;
	}
	private void connect(Stad from, Stad to, int tid, String namn) {


		if (s1 != null && s2 != null)	//Verifierar att båda noderna har städer. Kontroll sker även vid musklicket.
		{
				//Skapar en förbindelse FRÅN from TILL to

				Förbindelse f1 = new Förbindelse(from, to, tid, namn);
				fromList.add(f1);
				background.add(f1);
				
				//Skapar en förbindelse FRÅN to TILL from
				Förbindelse f2 = new Förbindelse(to, from, tid, namn);
				toList.add(f2);
				background.add(f2);
				
				vägar.add(f1);
				vägar.add(f2);

				System.out.println(s1+" är nu sammanbunden med "+s2);
				
				validate();
				repaint();
		}
		else
		{
			System.out.println("Två noder har ej valts!");
		}
		
		
	
	}

	// Metod som hittar alla förbindelser från en angedd stad
	private void getEdgesFrom(Stad from) 
	{
		fromList = nodes.get(from);
		
		String str = "";
		for (Map.Entry<Stad, List<Förbindelse>> me : nodes.entrySet()) 
		{
			List<Förbindelse> förbindelser = me.getValue();
			if(me.getKey().equals(from))
			{
				str += förbindelser + "\n";
			}
			
		}
		System.out.println(str);
		visaFörbindelseFält.setText(str);
	}

	private boolean PathExists(Stad from, Stad to) {
		Set<Stad> besökta = new HashSet<Stad>();
		besökta.add(from);
		
		toList = nodes.get(to);	//Hämtar noden "to" från alla noder som har en förbindelse(de är oriktade)

		for (Förbindelse f : vägar) // Loopar genom alla objekt av "Förbindelse"
		{ // Listan "vägar" är alla förbindelser som leder från "from"-objektet.
			if (!besökta.contains(f.getTo())) // Har objektet(förbindelsen)
												// inte besökts förut körs en
												// djupsökning
			{
				dfs(f.getTo(), besökta); // Kör en instans av metoden
											// djupsökning, samt skickar vidare
			} // förbindelseobjektets destination, samt listan av beesökta noder
		}
		return besökta.contains(to);
	}
	
	
	
	
	
	private void dfs(Stad where, Set<Stad> besökta) {
		besökta.add(where); // Adderar noden som precis besöktes till den
							// besökta listan
		for (Förbindelse f : nodes.get(where)) // Loopar genom alla förbindelser
												// från staden som besöks(granne
												// till from)
		{
			if (!besökta.contains(f.getTo())) // Om staden ej besökts
												// förut(finns inte i listan
												// besökta)
			{ // utförs samma sökning av förbindelser som gjordes på from,
				// staden läggs sedan till i besökta.
				dfs(f.getTo(), besökta);
			}
		}
	}
	
	private void dfs2(Stad where, Stad fromwhere, Set<Stad> besökta, Map<Stad, Stad> via)
	{
		besökta.add(where);
		via.put(where, fromwhere);

		for(Förbindelse f : nodes.get(where))
		{
			if(!besökta.contains(f.getTo()))
			{
				dfs2(f.getTo(), fromwhere, besökta, via);
			}
			
		}
		
		
		
	}
	

	

	
	class visaFörbindelseForm extends JPanel
	{
		visaFörbindelseForm() 
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Visar förbindelser från "+s1));
			
			JPanel rad2 = new JPanel();
			rad2.add(visaFörbindelseFält);
			
			GetConnectionBetween(s1, s2);
			
			add(rad1);
			add(rad2);
		}	
	}

	class nyNodForm extends JPanel {
		JTextField nyNodFalt; // Fältet där "från"-förbindelsen anges, oriktad

		nyNodForm() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Nodens namn:"));
			nyNodFalt = new JTextField(10);
			rad1.add(nyNodFalt);
			add(rad1);
		}
	}

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



	

	
	private CenterLyss CenterLyss = new CenterLyss(); //MouseClickListener som adderas till bakgrunden
	class CenterLyss extends MouseAdapter
	{
		@Override
		public void mouseClicked(MouseEvent me) 
		{
			for (JButton b : buttons)	
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
							
							Stad newNode = new Stad(nnf.nyNodFalt.getText(), xkoordinat, ykoordinat);
							if(nodNamn.isEmpty() || (nodNamn.matches(".*\\d.*") || nodNamnLista.contains(newNode.getNamn())))// Kontrollerar om namnfält är tomt
							{
								JOptionPane.showMessageDialog(null, "Nodnamn är tomt eller innehåller siffror!");
								setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								background.removeMouseListener(nkl);
								break;
							}
							
							if(nodNamnLista.contains(newNode.getNamn()))
								{
								System.out.println("Noden finns redan!");
								background.removeMouseListener(nkl);
								break;
								}
							 vägar = new ArrayList<Förbindelse>();
							
							
							newNode.addMouseListener(nkl);
							newNode.setBounds(xkoordinat, ykoordinat, 200, 50);
							background.add(newNode);

							background.removeMouseListener(CenterLyss);
							setCursor(Cursor.getDefaultCursor());
							nodes.put(newNode, new ArrayList<Förbindelse>()); 
							
							nodNamnLista.add(nnf.nyNodFalt.getText());
							
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
	private Stad s1 = null, s2 = null;
	
	NodKlickLyss nkl = new NodKlickLyss();	//MouseClickListener som adderas till varje StadCirkel
	class NodKlickLyss extends MouseAdapter	//överskuggar "onödiga" metoder från MouseListener.
	{
		@Override
		public void mouseClicked(MouseEvent mev) 
		{
			Stad newNode = (Stad)mev.getSource();
			
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
			

			repaint();
		}
			else
			{
	
			}
		}
		
		
	}
	

}
