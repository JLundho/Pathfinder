package mainpackage;

import graphs.Förbindelse;
import graphs.Graph;
import graphs.ListGraph;
import graphs.Nod;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;





import java.util.HashSet;
import java.util.Set;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener; 

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class RemadePathFinder<N> extends JFrame{
	
	JMenu[] Menu = new JMenu[3]; 
	JButton[] northButtons = new JButton[5]; 
	JMenuItem[] arkivItems = new JMenuItem[5];
	JMenuItem[] operationItems = new JMenuItem[5];
	private BackgroundPanel background;
	Multimap<N, Set<Förbindelse<N>>> nodes = ArrayListMultimap.create();
	
	
	private Graph<Nod> g;
	
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
		operationItems[2].addActionListener(new NyNodKnappLyss());
		
		
		for (int i = 0; i < northButtons.length; i++) {  
			northButtons[i] = new JButton(miButtonNames[i]);
			north.add(northButtons[i], BorderLayout.NORTH);
		}
		northButtons[2].addActionListener(new NyNodKnappLyss());
		
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
			Nod newNode = new Nod(nnf.getName());
			
			try {
				for (;;) {
					int nySvar = JOptionPane.showConfirmDialog(RemadePathFinder.this, nnf,"Ny nod", JOptionPane.OK_CANCEL_OPTION);											
						if (nySvar == JOptionPane.OK_OPTION) {
						
						fadd((N) newNode);
						
						
						
						//g.add(newNode);
						
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
	
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************

	// METHODS ************************************************************
	// METHODS ************************************************************
	// METHODS ************************************************************
	// METHODS ************************************************************
	
	public void	fadd(N node) {
		nodes.put(node, new HashSet<Förbindelse<N>>());
		System.out.println("Yes!");
	}
	
	
	
	public static void main(String[] args) {
		new RemadePathFinder();
		
	}



}
