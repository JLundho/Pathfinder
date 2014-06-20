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
	
	private Map<Stad, List<F�rbindelse>> nodes = new HashMap<Stad, List<F�rbindelse>>();	//Skapar och deklarerar HashMapen
	private JFileChooser jfc = new JFileChooser("C:\\");
	
	private List<F�rbindelse> fromList = new ArrayList<F�rbindelse>();						//Lagrar alla from-noder(to-noder ing�r �ven)
	private List<F�rbindelse> toList = new ArrayList<F�rbindelse>();						//Lagrar alla to-noder(from-noder ing�r �ven)
	private List<F�rbindelse> v�gar = new ArrayList<F�rbindelse>();
	
	private ArrayList<String> nodNamnLista = new ArrayList<String>();
	
	
	private BackgroundPanel background;

	private JTextArea visaF�rbindelseF�lt = new JTextArea();									//Textruta d�r f�rbindelser fr�n en nod visas
	
	
	int xkoordinat;																			//X-koordinat f�r musklick
	int ykoordinat;																			//Y-koordinat f�r musklick

	JMenu[] arkivMenu = new JMenu[2];														//Array av menyer med 2 platser f�r arkiv
	JMenuItem[] opsMenu = new JMenuItem[7];													//Array av menyer med 7 platser f�r Operationer(uppfyller samma sak som knappar)
	JButton[] buttons = new JButton[5];

	
	
	
	//Konstruktor och grafiskt gr�nssnitt
	ListGraph() 
	{
		super("Nodmonstret");

		//JMenuBar - Ursprungliga knappar 'som l�ggs till i menybaren
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
		

		buttons[0] = new JButton("Hitta v�g");
		buttons[0].addActionListener(new HittaV�gKnappLyss());
		buttons[1] = new JButton("Visa f�rbindelse");
		buttons[1].addActionListener(new visaF�rbindelseKnappLyss());
		buttons[2] = new JButton("Ny nod");
		buttons[2].addActionListener(new NyNodKnappLyss());
		buttons[3] = new JButton("Ny f�rbindelse");
		buttons[3].addActionListener(new NyF�rbindelseKnappLyss());
		buttons[4] = new JButton("�ndra f�rbindelse");
		

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
	
	class NyF�rbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyF�rbindelseKnappAve) {

			
			if(s1 != null && s2 != null)
			{
			nyF�rbindelseForm nff = new nyF�rbindelseForm();

			
			
	
			int nySvar = JOptionPane.showConfirmDialog(null, nff,
					"Ny f�rbindelse", JOptionPane.OK_CANCEL_OPTION); // Dialogf�nster
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
						if(fardmedel.isEmpty() || (fardmedel.matches(".*\\d.*")))// Kontrollerar om namnf�lt �r tomt
						{
							JOptionPane.showMessageDialog(null, "F�rdmedelsf�ltet �r tomt eller inneh�ller siffror!");
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
							extra += "och �ven tomt!";
							setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
						}
				
						JOptionPane.showMessageDialog(null, "Tidf�ltet �r felaktigt inskrivet " +extra);
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
				JOptionPane.showMessageDialog(null, "Tv� noder m�ste v�ljas!");
				
			}
		}
	}
	
	
	class visaF�rbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyF�rbindelseKnappAve)
		{
			visaF�rbindelseForm vff = new visaF�rbindelseForm() ;

			
			if(PathExists(s1, s2))
			{
			GetConnectionBetween(s1, s2);
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Ingen f�rbindelse finns!");
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
	
	class HittaV�gKnappLyss implements ActionListener{
		public void actionPerformed(ActionEvent hittaV�gAve)
		{
			try{
				visaF�rbindelseForm vff = new visaF�rbindelseForm() ;
				
				GetConnectionBetween(s1, s2);
				GetAnyPath(s1, s2);
				
				}
				catch(NoSuchElementException nsee)
				{
					JOptionPane.showMessageDialog(null, "Tv� noder m�ste v�ljas!");
				}
			}

		
	}
	
	public List<F�rbindelse> GetAnyPath(Stad from, Stad to)
	{
		Map<Stad, Stad> via = new HashMap<Stad, Stad>();
		Set<Stad> bes�kta = new HashSet<Stad>();
		
		if(PathExists(from, to))
		{
		for(F�rbindelse f : fromList)
		{
			dfs2(f.getFrom(), f.getTo(), bes�kta, via);
			GetConnectionBetween(f.getFrom(), f.getTo());
		}
		
		System.out.println(""+via);
		
		List<F�rbindelse> v�gen = new ArrayList<F�rbindelse>();
		
		Stad where = to;
		return v�gen;
		}
		else
		{
			System.out.println("Ingen v�g finns!");
			return null;
		}
		
		
	}
	
	
	private F�rbindelse GetConnectionBetween(Stad from, Stad to)
	{
		for(F�rbindelse f : v�gar)
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


		if (s1 != null && s2 != null)	//Verifierar att b�da noderna har st�der. Kontroll sker �ven vid musklicket.
		{
				//Skapar en f�rbindelse FR�N from TILL to

				F�rbindelse f1 = new F�rbindelse(from, to, tid, namn);
				fromList.add(f1);
				background.add(f1);
				
				//Skapar en f�rbindelse FR�N to TILL from
				F�rbindelse f2 = new F�rbindelse(to, from, tid, namn);
				toList.add(f2);
				background.add(f2);
				
				v�gar.add(f1);
				v�gar.add(f2);

				System.out.println(s1+" �r nu sammanbunden med "+s2);
				
				validate();
				repaint();
		}
		else
		{
			System.out.println("Tv� noder har ej valts!");
		}
		
		
	
	}

	// Metod som hittar alla f�rbindelser fr�n en angedd stad
	private void getEdgesFrom(Stad from) 
	{
		fromList = nodes.get(from);
		
		String str = "";
		for (Map.Entry<Stad, List<F�rbindelse>> me : nodes.entrySet()) 
		{
			List<F�rbindelse> f�rbindelser = me.getValue();
			if(me.getKey().equals(from))
			{
				str += f�rbindelser + "\n";
			}
			
		}
		System.out.println(str);
		visaF�rbindelseF�lt.setText(str);
	}

	private boolean PathExists(Stad from, Stad to) {
		Set<Stad> bes�kta = new HashSet<Stad>();
		bes�kta.add(from);
		
		toList = nodes.get(to);	//H�mtar noden "to" fr�n alla noder som har en f�rbindelse(de �r oriktade)

		for (F�rbindelse f : v�gar) // Loopar genom alla objekt av "F�rbindelse"
		{ // Listan "v�gar" �r alla f�rbindelser som leder fr�n "from"-objektet.
			if (!bes�kta.contains(f.getTo())) // Har objektet(f�rbindelsen)
												// inte bes�kts f�rut k�rs en
												// djups�kning
			{
				dfs(f.getTo(), bes�kta); // K�r en instans av metoden
											// djups�kning, samt skickar vidare
			} // f�rbindelseobjektets destination, samt listan av bees�kta noder
		}
		return bes�kta.contains(to);
	}
	
	
	
	
	
	private void dfs(Stad where, Set<Stad> bes�kta) {
		bes�kta.add(where); // Adderar noden som precis bes�ktes till den
							// bes�kta listan
		for (F�rbindelse f : nodes.get(where)) // Loopar genom alla f�rbindelser
												// fr�n staden som bes�ks(granne
												// till from)
		{
			if (!bes�kta.contains(f.getTo())) // Om staden ej bes�kts
												// f�rut(finns inte i listan
												// bes�kta)
			{ // utf�rs samma s�kning av f�rbindelser som gjordes p� from,
				// staden l�ggs sedan till i bes�kta.
				dfs(f.getTo(), bes�kta);
			}
		}
	}
	
	private void dfs2(Stad where, Stad fromwhere, Set<Stad> bes�kta, Map<Stad, Stad> via)
	{
		bes�kta.add(where);
		via.put(where, fromwhere);

		for(F�rbindelse f : nodes.get(where))
		{
			if(!bes�kta.contains(f.getTo()))
			{
				dfs2(f.getTo(), fromwhere, bes�kta, via);
			}
			
		}
		
		
		
	}
	

	

	
	class visaF�rbindelseForm extends JPanel
	{
		visaF�rbindelseForm() 
		{
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Visar f�rbindelser fr�n "+s1));
			
			JPanel rad2 = new JPanel();
			rad2.add(visaF�rbindelseF�lt);
			
			GetConnectionBetween(s1, s2);
			
			add(rad1);
			add(rad2);
		}	
	}

	class nyNodForm extends JPanel {
		JTextField nyNodFalt; // F�ltet d�r "fr�n"-f�rbindelsen anges, oriktad

		nyNodForm() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Nodens namn:"));
			nyNodFalt = new JTextField(10);
			rad1.add(nyNodFalt);
			add(rad1);
		}
	}

	class nyF�rbindelseForm extends JPanel {


		JTextField tidFalt;
		JTextField fardmedelsFalt;

		nyF�rbindelseForm() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			
			
			JPanel forbindelserad = new JPanel();
			JPanel rad1 = new JPanel();
			JPanel rad2 = new JPanel();

			forbindelserad.add(new JLabel("L�gg till ny f�rbindelse"));

			rad1.add(new JLabel("Tid: "));
			tidFalt = new JTextField(10);
			rad1.add(tidFalt);

			rad2.add(new JLabel("F�rdmedel: "));
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
						JOptionPane.OK_CANCEL_OPTION); 							// Dialogf�nster som skapar
																				// ny deltagare
						if (nySvar == JOptionPane.OK_OPTION) 
						{
							
							String nodNamn = nnf.nyNodFalt.getText();
							
							Stad newNode = new Stad(nnf.nyNodFalt.getText(), xkoordinat, ykoordinat);
							if(nodNamn.isEmpty() || (nodNamn.matches(".*\\d.*") || nodNamnLista.contains(newNode.getNamn())))// Kontrollerar om namnf�lt �r tomt
							{
								JOptionPane.showMessageDialog(null, "Nodnamn �r tomt eller inneh�ller siffror!");
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
							 v�gar = new ArrayList<F�rbindelse>();
							
							
							newNode.addMouseListener(nkl);
							newNode.setBounds(xkoordinat, ykoordinat, 200, 50);
							background.add(newNode);

							background.removeMouseListener(CenterLyss);
							setCursor(Cursor.getDefaultCursor());
							nodes.put(newNode, new ArrayList<F�rbindelse>()); 
							
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
	class NodKlickLyss extends MouseAdapter	//�verskuggar "on�diga" metoder fr�n MouseListener.
	{
		@Override
		public void mouseClicked(MouseEvent mev) 
		{
			Stad newNode = (Stad)mev.getSource();
			
			//Om b�da �r tomma adderas noden till den f�rsta minnesplatsen
			if(mev.getClickCount() < 2)
			{
			if (s1 == null && newNode != s2)
			{
				s1 = newNode;
				System.out.println("s1 = "+s1);
				newNode.setSelected(true);
			}
			else if (s2 == null && newNode != s1) 	// Om f�rsta minnesplatsen �r full och 
			{										//nya noden inte redan �r d�r adderas den till andra
				s2 = newNode;
				System.out.println("s2 = "+s2);
				newNode.setSelected(true);
			}
			else if (s1 == newNode)					//Om f�rsta minnesplatsen redan innehar v�rdet avmarkeras den
			{
				s1 = null;
				System.out.println("s1 �r �ppen!");
				newNode.setSelected(false);
			} 
			else if (s2 == newNode)					//Om andra minnesplatsen redan innehar v�rdet avmarkeras den
			{
				s2 = null;
				System.out.println("s2 �r �ppen");
				newNode.setSelected(false);
			}
			
			else if (s1 != null && s2 != null && newNode != s1 || newNode != s2)					//Om andra minnesplatsen redan innehar v�rdet avmarkeras den
			{
				System.out.println("B�da noderna �r valda!");
			}
			

			repaint();
		}
			else
			{
	
			}
		}
		
		
	}
	

}
