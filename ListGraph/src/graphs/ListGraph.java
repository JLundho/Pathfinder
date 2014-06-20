package graphs;

import java.awt.*;

import javax.swing.*;

import java.util.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import connections.F�rbindelse;
import connections.Stad;

public class ListGraph extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	// README ************************************************************
	// README ************************************************************
	// README ************************************************************
	// README ************************************************************
	
	// Below I have declared the global elements who will be used in multiple
	// methods and classes. If an element outside of the function/class is ONLY used within it
	// it will be declared above the appropriate code-section
	
	private Map<Stad, List<F�rbindelse>> nodes = new HashMap<Stad, List<F�rbindelse>>();
	private List<F�rbindelse> v�gar = new ArrayList<F�rbindelse>();
	private BackgroundPanel background;
	
	private Stad s1 = null, s2 = null;	//Variables to declare which nodes are currently selected

	JMenu[] arkivMenu = new JMenu[2]; 
	JMenuItem[] opsMenu = new JMenuItem[7]; 
	JButton[] buttons = new JButton[5];

	public ListGraph() {
		super("Nodmonstret");	

		JMenuBar mb = new JMenuBar();
		arkivMenu[0] = new JMenu("Arkiv");
		arkivMenu[1] = new JMenu("Operationer");
		setJMenuBar(mb);
		for (JMenu j : arkivMenu) {
			mb.add(j);
		}


		JMenuItem[] arkiv = new JMenuItem[1];
		arkiv[0] = new JMenuItem("Ny");
		arkiv[0].addActionListener(new nyFilKnappLyss());

		
		//JMenuItem[] operationer = new JMenuItem[1];

		for (JMenuItem i : arkiv) {
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

		for (JButton b : buttons) {
			north.add(b, BorderLayout.NORTH);
			b.setEnabled(false);
		}

		add(north, BorderLayout.NORTH);

		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);

	}
	
	// OPERATIONS // ************************************************************
	// OPERATIONS // ************************************************************
	// OPERATIONS // ************************************************************
	// OPERATIONS // ************************************************************


	// BUTTON LISTENERS ************************************************************
	// BUTTON LISTENERS ************************************************************
	// BUTTON LISTENERS ************************************************************
	// BUTTON LISTENERS ************************************************************

	class NyF�rbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyF�rbindelseKnappAve) {

			if (s1 != null && s2 != null) {
				nyF�rbindelseForm nff = new nyF�rbindelseForm();
				int nySvar = JOptionPane.showConfirmDialog(null, nff,
						
						"Ny f�rbindelse", JOptionPane.OK_CANCEL_OPTION); 
				for (;;) {
					{
						if (nySvar == JOptionPane.OK_OPTION) {

							String fardmedel = nff.fardmedelsFalt.getText();
							String extra = "";
							try {
								int tidInt = Integer.parseInt(nff.tidFalt.getText());
								if (fardmedel.isEmpty()
										|| (fardmedel.matches(".*\\d.*")))
								{
									JOptionPane.showMessageDialog(null,"F�rdmedelsf�ltet �r tomt eller inneh�ller siffror!");
									setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
									background.removeMouseListener(nkl);
									break;
									
								} else {
									setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
									connect(s1, s2, tidInt,nff.fardmedelsFalt.getText());
									background.removeMouseListener(nkl);
									break;
								}
							}

							catch (NumberFormatException e) {
								if (nff.tidFalt.getText().equals("")) {
									extra += "och �ven tomt!";
									setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
								}

								JOptionPane.showMessageDialog(null,"Tidf�ltet �r felaktigt inskrivet "+ extra);
								setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
								background.removeMouseListener(nkl);
								break;
							}

						} else {

							setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
							background.removeMouseListener(nkl);
							break;

						}
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Tv� noder m�ste v�ljas!");

			}
		}
	}

	class visaF�rbindelseKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyF�rbindelseKnappAve) {


			if (PathExists(s1, s2)) {
				GetConnectionBetween(s1, s2);
			} else {
				JOptionPane.showMessageDialog(null, "Ingen f�rbindelse finns!");
			}
		}
	}
	
	
	private ArrayList<String> nodNamnLista = new ArrayList<String>();
	
	class NyNodKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent nyNodKnappAve) {
			for (JButton b : buttons) {
				b.setEnabled(false);
			}
			background.addMouseListener(CenterLyss);
			setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		}

	}

	class HittaV�gKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent hittaV�gAve) {
			try {

				GetConnectionBetween(s1, s2);
				GetAnyPath(s1, s2);

			} catch (NoSuchElementException nsee) {
				JOptionPane.showMessageDialog(null, "Tv� noder m�ste v�ljas!");
			}
		}

	}

	// MOUSE LISTENERS ************************************************************
	// MOUSE LISTENERS ************************************************************
	// MOUSE LISTENERS ************************************************************
	// MOUSE LISTENERS ************************************************************

	int xkoordinat; // X-koordinat f�r musklick
	int ykoordinat; // Y-koordinat f�r musklick

	private CenterLyss CenterLyss = new CenterLyss(); // MouseClickListener som
														// adderas till
														// bakgrunden

	class CenterLyss extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent me) {
			for (JButton b : buttons) {
				b.setEnabled(true);
			}
			xkoordinat = me.getX() - 9;
			ykoordinat = me.getY() - 9;

			nyNodForm nnf = new nyNodForm();
			try {
				for (;;) {
					int nySvar = JOptionPane.showConfirmDialog(null, nnf,"Ny nod", JOptionPane.OK_CANCEL_OPTION);
																		
					if (nySvar == JOptionPane.OK_OPTION) {

						String nodNamn = nnf.nyNodFalt.getText();

						Stad newNode = new Stad(nnf.nyNodFalt.getText(),
								xkoordinat, ykoordinat);
						if (nodNamn.isEmpty() || (nodNamn.matches(".*\\d.*") || nodNamnLista.contains(newNode.getNamn())))
						{
							JOptionPane.showMessageDialog(null,	"Nodnamn �r tomt eller inneh�ller siffror!");
							setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
							background.removeMouseListener(nkl);
							break;
						}

						if (nodNamnLista.contains(newNode.getNamn())) {
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

						System.out.println("X: " + (newNode.getxkoordinat())
								+ ", Y: " + newNode.getykoordinat());

						break;
					} else {
						background.removeMouseListener(CenterLyss);
						background.setCursor(Cursor.getDefaultCursor());
						System.out.println("Noden har ej adderats!");

						break;
					}
				}
			} catch (IndexOutOfBoundsException e) {

			}
		}
	}


	NodKlickLyss nkl = new NodKlickLyss(); 
	
	class NodKlickLyss extends MouseAdapter
											
	{
		@Override
		public void mouseClicked(MouseEvent mev) {
			Stad newNode = (Stad) mev.getSource();

			if (mev.getClickCount() < 2) {
				if (s1 == null && newNode != s2) {
					s1 = newNode;
					System.out.println("s1 = " + s1);
					newNode.setSelected(true);
				} else if (s2 == null && newNode != s1)
				{ 
					s2 = newNode;
					System.out.println("s2 = " + s2);
					newNode.setSelected(true);
				} else if (s1 == newNode)
				{
					s1 = null;
					System.out.println("s1 �r �ppen!");
					newNode.setSelected(false);
				} else if (s2 == newNode) 
				{
					s2 = null;
					System.out.println("s2 �r �ppen");
					newNode.setSelected(false);
				}

				else if (s1 != null && s2 != null && newNode != s1
						|| newNode != s2) 
				{
					System.out.println("B�da noderna �r valda!");
				}

				repaint();
			} 
		}
	}

	// BUTTON FUNCTIONALITY ************************************************************
	// BUTTON FUNCTIONALITY ************************************************************
	// BUTTON FUNCTIONALITY ************************************************************
	// BUTTON FUNCTIONALITY ************************************************************

	private List<F�rbindelse> fromList = new ArrayList<F�rbindelse>(); 
	private List<F�rbindelse> toList = new ArrayList<F�rbindelse>(); 
	
	private void connect(Stad from, Stad to, int tid, String namn) {
		if (s1 != null && s2 != null) 
		{
			// Skapar en f�rbindelse FR�N from TILL to

			F�rbindelse f1 = new F�rbindelse(from, to, tid, namn);
			fromList.add(f1);
			background.add(f1);

			// Skapar en f�rbindelse FR�N to TILL from
			F�rbindelse f2 = new F�rbindelse(to, from, tid, namn);
			toList.add(f2);
			background.add(f2);

			v�gar.add(f1);
			v�gar.add(f2);

			System.out.println(s1 + " �r nu sammanbunden med " + s2);

			validate();
			repaint();
		} else {
			System.out.println("Tv� noder har ej valts!");
		}

	}

	public List<F�rbindelse> GetAnyPath(Stad from, Stad to) {
		Map<Stad, Stad> via = new HashMap<Stad, Stad>();
		Set<Stad> bes�kta = new HashSet<Stad>();

		if (PathExists(from, to)) {
			for (F�rbindelse f : fromList) {
				dfs2(f.getFrom(), f.getTo(), bes�kta, via);
				GetConnectionBetween(f.getFrom(), f.getTo());
			}

			System.out.println("" + via);
			List<F�rbindelse> v�gen = new ArrayList<F�rbindelse>();
			return v�gen;
		} else {
			System.out.println("Ingen v�g finns!");
			return null;
		}
	}

	private F�rbindelse GetConnectionBetween(Stad from, Stad to) {
		for (F�rbindelse f : v�gar) {
			if (f.getFrom() == from && f.getTo() == to) {
				for (int results = 0; results < 1; results++) {
					if (PathExists(f.getTo(), to)) {
						System.out.println(f);
					}
				}
				return f;
			}
		}

		return null;
	}

	private boolean PathExists(Stad from, Stad to) {
		Set<Stad> bes�kta = new HashSet<Stad>();
		bes�kta.add(from);

		toList = nodes.get(to); 
		
		for (F�rbindelse f : v�gar)
		{ 
			if (!bes�kta.contains(f.getTo()))
			{
				dfs(f.getTo(), bes�kta);
			} 
		}
		return bes�kta.contains(to);
	}

	private void dfs(Stad where, Set<Stad> bes�kta) {
		bes�kta.add(where); 
		for (F�rbindelse f : nodes.get(where)) 
		{
			if (!bes�kta.contains(f.getTo())) 
			{ 
				dfs(f.getTo(), bes�kta);
			}
		}
	}

	private void dfs2(Stad where, Stad fromwhere, Set<Stad> bes�kta,
			Map<Stad, Stad> via) {

		bes�kta.add(where);
		via.put(where, fromwhere);

		for (F�rbindelse f : nodes.get(where)) {
			if (!bes�kta.contains(f.getTo())) {
				dfs2(f.getTo(), fromwhere, bes�kta, via);
			}
		}
	}

	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	// FORMS ************************************************************
	
	private JTextArea visaF�rbindelseF�lt = new JTextArea();
	
	class visaF�rbindelseForm extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		visaF�rbindelseForm() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

			JPanel rad1 = new JPanel();
			rad1.add(new JLabel("Visar f�rbindelser fr�n " + s1));

			JPanel rad2 = new JPanel();
			rad2.add(visaF�rbindelseF�lt);

			GetConnectionBetween(s1, s2);

			add(rad1);
			add(rad2);
		}
	}

	
	class nyNodForm extends JPanel {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

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
	
	// FILES ************************************************************
	// FILES ************************************************************
	// FILES ************************************************************
	// FILES ************************************************************
	
	public class BackgroundPanel extends JPanel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
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
	
	
	private JFileChooser jfc = new JFileChooser("C:\\");
	
	class nyFilKnappLyss implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int resultat = jfc.showOpenDialog(null);

			if (resultat == JFileChooser.APPROVE_OPTION) {
				for (JButton b : buttons) {
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
	
	public static void main(String[] args) {
		new ListGraph();
	}


}
