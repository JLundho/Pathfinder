package mainpackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

import graphs.*;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener; 

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;



public class RemadePathFinder extends JFrame{
	
	JMenu[] Menu = new JMenu[3]; 
	JButton[] northButtons = new JButton[5]; 
	JMenuItem[] arkivItems = new JMenuItem[5];
	JMenuItem[] operationItems = new JMenuItem[5];
	private BackgroundPanel background;
				
	Graph g = new ListGraph();
	
	private Nod s1, s2;	
	
	Multimap<Nod, Nod> fromConnections = ArrayListMultimap.create();
	Multimap<Nod, Nod> toConnections = ArrayListMultimap.create();
	Multimap<Nod, Set<F�rbindelse<Nod>>> nodes = ArrayListMultimap.create();
	
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

		String[] miButtonNames = {"Hitta v�g", "Visa f�rbindelse", "Ny nod", "Ny f�rbindelse", "�ndra f�rbindelse"}; 
		String[] arkivNames = {"Nytt", "�ppna", "Spara", "Spara som", "Avsluta"}; 
		

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
		operationItems[1].addActionListener(new VisaF�rbindelseKnappLyss());
		operationItems[2].addActionListener(new NyNodKnappLyss());
		operationItems[3].addActionListener(new NyNodKnappLyss());
		
		for (int i = 0; i < northButtons.length; i++) {  
			northButtons[i] = new JButton(miButtonNames[i]);
			north.add(northButtons[i], BorderLayout.NORTH);
		}
		northButtons[1].addActionListener(new VisaF�rbindelseKnappLyss());
		northButtons[2].addActionListener(new NyNodKnappLyss());
		northButtons[3].addActionListener(new NyF�rbindelseKnappLyss());
		//northButtons[3].addActionListener(new NyF�rbindelseKnappLyss());
		
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
		public void actionPerformed(ActionEvent nyNodKnappAve) {
			nyNodForm nnf = new nyNodForm();

			
			try {
				for (;;) {
					int nySvar = JOptionPane.showConfirmDialog(RemadePathFinder.this, nnf,"Ny nod", JOptionPane.OK_CANCEL_OPTION);											
						Nod newNode = new Nod(nnf.getName());
						if (nySvar == JOptionPane.OK_OPTION) {
						g.add(newNode);
						break;
					} else {

						System.out.println("Noden har ej adderats!");
						break;
					}
				}
			} catch (IndexOutOfBoundsException e) {

			}

		}

	}
	
	class NyF�rbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyF�rbindelseKnappAve) {

				nyF�rbindelseForm nff = new nyF�rbindelseForm();
				int nySvar = JOptionPane.showConfirmDialog(null, nff,"Ny f�rbindelse", JOptionPane.OK_CANCEL_OPTION); 
				for (;;) {
					{
						if (nySvar == JOptionPane.OK_OPTION) 
						{

							String fardmedel = nff.fardmedelsFalt.getText();
							String extra = "";
							try {
								int tidInt = Integer.parseInt(nff.tidFalt.getText());
								if (fardmedel.isEmpty() || (fardmedel.matches(".*\\d.*")))
								{
									JOptionPane.showMessageDialog(null,"F�rdmedelsf�ltet �r tomt eller inneh�ller siffror!");

									break;
									
								} else 
								{
									g.connect(s1, s2, fardmedel, tidInt);
									System.out.println(s1+" och "+s2+" �r nu sammanbundna");
									break;
								}
							}

							catch (NumberFormatException e) {
								if (nff.tidFalt.getText().equals("")) {
									extra += "och �ven tomt!";

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
	
	class VisaF�rbindelseKnappLyss implements ActionListener {
	public void actionPerformed(ActionEvent VisaKnappAve) {
		g.displayConnections();
	}
	}
	
	class omKnappLyss implements MenuListener {
		public void menuSelected(MenuEvent e) 
		{
			JOptionPane.showMessageDialog(null, "Mitt namn �r Jonas Lundholm och det h�r �r ett program som �r till f�r\n"
					+ "att ladda kartor, s�tta ut punkter p� kartan och skapa f�rbindelser mellan punkterna\n"
					+ "p� kartan. Uppskattar du programmet, hittar buggar eller vill l�mna �sikter\n"
					+ "finns jag tillg�nglig p�: \nemail: jonas.l.lundholm@gmail.com", "Om", JOptionPane.INFORMATION_MESSAGE);	
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
	
	class BackgroundPanel extends JPanel 
	{
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
	}
	
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
	

	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	
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
	
	class nyNodForm extends JPanel {
		private JTextField nyNodFalt; // F�ltet d�r "fr�n"-f�rbindelsen anges, oriktad
		
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
	
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************

	// METHODS ************************************************************
	// METHODS ************************************************************
	// METHODS ************************************************************
	// METHODS ************************************************************
	


	public static void main(String[] args) {
		new RemadePathFinder();
	}



}
