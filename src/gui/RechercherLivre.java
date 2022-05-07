package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.FrabriqueDao;
import dao.Livre;
import dao.livreDao;
import dao.livreDaoImplDB;
import javax.swing.border.LineBorder;

/**
 * @author Nathaniel Larouche
 *Classe RechercherLivre qui derivent de JPanel dont l'utilité est de rechercher un livre dans un des supports de stockage.
 */
public class RechercherLivre extends JPanel {

	private JFrame frame;
	private JTextField tfAuteur;
	private JTextField tfTitre;
	private JTable tblRecherche;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	/**
	 * Lance l'interface si exécuter individuellement de l'interface principale.
	 */
	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.getContentPane().add(new RechercherLivre(), BorderLayout.CENTER);
		f.setBounds(100, 100, 539, 300);
		f.setSize(800, 500);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
	}
	/*
	 * Constructeur par défaut, il contiens tout les éléments visuels et ainsi que les interactions avec celui-ci
	 */
	public RechercherLivre() {
		//Detecte le changement de langue lorsque tout les labels/boutons/menu prennent la nouvelle langue.
		//Cela ce manifeste par la modification du border donc ici on s'en sert pour récupérer la langue.
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border"))
					getLanguage();
			}
		});
		
		getLanguage();
		setPreferredSize(new Dimension(800, 500));
		setMinimumSize(new Dimension(200, 100));
		this.setSize(500, 300);
		this.setBackground(new Color(0, 204, 204));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 42, 38, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0};
		gridBagLayout.rowWeights = new double[]{1.0, 0.0, 0.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		tblRecherche = new JTable();
		tblRecherche.setGridColor(new Color(0, 128, 128));
		tblRecherche.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		tblRecherche.setBackground(new Color(0, 255, 255));
		tblRecherche.setEnabled(false);
		TableModele modele = new TableModele();
		JScrollPane scrollPane = new JScrollPane(tblRecherche);
		scrollPane.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.gridwidth = 8;
		gbc_scrollPane.gridheight = 3;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 3;
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		add(scrollPane, gbc_scrollPane);
		
		
		
		
		JLabel lblRechercherUnLivre = new JLabel(textLabels.getString("lblRechercherUnLivre"));
		lblRechercherUnLivre.setName("lblRechercherUnLivre");
		lblRechercherUnLivre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		GridBagConstraints gbc_lblRechercherUnLivre = new GridBagConstraints();
		gbc_lblRechercherUnLivre.gridwidth = 8;
		gbc_lblRechercherUnLivre.insets = new Insets(0, 0, 5, 5);
		gbc_lblRechercherUnLivre.gridx = 0;
		gbc_lblRechercherUnLivre.gridy = 0;
		add(lblRechercherUnLivre, gbc_lblRechercherUnLivre);
		
		JLabel lblTitre = new JLabel(textLabels.getString("lblTitre"));
		lblTitre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblTitre.setName("lblTitre");
		lblTitre.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.fill = GridBagConstraints.BOTH;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 1;
		add(lblTitre, gbc_lblTitre);
		
		tfTitre = new JTextField();
		tfTitre.setToolTipText("/FindAll");
		GridBagConstraints gbc_tfTitre = new GridBagConstraints();
		gbc_tfTitre.gridwidth = 3;
		gbc_tfTitre.insets = new Insets(0, 0, 5, 5);
		gbc_tfTitre.fill = GridBagConstraints.BOTH;
		gbc_tfTitre.gridx = 3;
		gbc_tfTitre.gridy = 1;
		add(tfTitre, gbc_tfTitre);
		tfTitre.setColumns(10);
		
		JButton btnRechercher = new JButton(textBoutons.getString("btnRechercher"));
		btnRechercher.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnRechercher.setForeground(Color.WHITE);
		btnRechercher.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		btnRechercher.setName("btnRechercher");
		btnRechercher.setBackground(new Color(0, 102, 51));
		/*
		 * Recherche sur l'un des supports de stockage tout les livres stocker qui contients 
		 * le titre et le nom entrée dans les champs. Si il trouvent il va l'afficher
		 * dans le tableau
		 */
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				List<Livre> listeLivreSelect=new ArrayList<Livre>();
				Boolean deuxElement=false;
				try {
					livreDao livreDao = FrabriqueDao.CreateLivreDao(InterfacePrincipale.getImplementationDao());
					List<Livre> listeTousLivre=new ArrayList<Livre>();
					listeTousLivre= livreDao.findAll();
					//Lorsque les deux textfields ne sont pas vide, on va dont aller chercher les deux element.
					if(!tfAuteur.getText().isEmpty() && !tfTitre.getText().isEmpty()) {
						deuxElement = true;
					}
					//La commande /FindAll sur les textfield permet d'afficher tout les livres de la db
					if(tfAuteur.getText().toString().equals("/FindAll")||
							tfTitre.getText().toString().equals("/FindAll")) {
						listeLivreSelect = listeTousLivre;
					}
					else {
						for(Livre l:listeTousLivre) {
							//Cherche le resultat par rapport au deux textfield
							if((containsIgnoreCase(l.getAuteur(),tfAuteur.getText())&& !tfAuteur.getText().isEmpty() )&&
									(containsIgnoreCase(l.getTitre(),tfTitre.getText())&& !tfTitre.getText().isEmpty())) {
								listeLivreSelect.add(l);
							}
							//Va ajouter soit l'auteur ou le titre
							else if(!deuxElement) {
								if(containsIgnoreCase(l.getAuteur(),tfAuteur.getText())&& !tfAuteur.getText().isEmpty()) {
									listeLivreSelect.add(l);
								}
								else if(containsIgnoreCase(l.getTitre(),tfTitre.getText()) && !tfTitre.getText().isEmpty()) {
									listeLivreSelect.add(l);
								}
							}
						}
					}
					if(listeLivreSelect.size() != 0) {
						//Defini le model
						modele.setTableModele(convert(listeLivreSelect),new String[]{textLabels.getString("lblId"),textLabels.getString("lblTitre")
								,textLabels.getString("lblAuteur"),textLabels.getString("lblPage"),textLabels.getString("lblPrix")});
						tblRecherche.setModel(modele);
						tblRecherche.revalidate();
						scrollPane.revalidate();
						updateUI();
					}
					else {
						JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreNonTrouver"));
					}
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnRechercher = new GridBagConstraints();
		gbc_btnRechercher.fill = GridBagConstraints.BOTH;
		gbc_btnRechercher.gridheight = 2;
		gbc_btnRechercher.gridwidth = 2;
		gbc_btnRechercher.insets = new Insets(0, 0, 5, 0);
		gbc_btnRechercher.gridx = 6;
		gbc_btnRechercher.gridy = 1;
		add(btnRechercher, gbc_btnRechercher);
		
		JLabel lblAuteur = new JLabel(textLabels.getString("lblAuteur"));
		lblAuteur.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblAuteur.setName("lblAuteur");
		lblAuteur.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblAuteur = new GridBagConstraints();
		gbc_lblAuteur.fill = GridBagConstraints.BOTH;
		gbc_lblAuteur.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuteur.gridx = 0;
		gbc_lblAuteur.gridy = 2;
		add(lblAuteur, gbc_lblAuteur);
		
		tfAuteur = new JTextField();
		tfAuteur.setToolTipText("/FindAll");
		GridBagConstraints gbc_tfAuteur = new GridBagConstraints();
		gbc_tfAuteur.gridwidth = 3;
		gbc_tfAuteur.insets = new Insets(0, 0, 5, 5);
		gbc_tfAuteur.fill = GridBagConstraints.BOTH;
		gbc_tfAuteur.gridx = 3;
		gbc_tfAuteur.gridy = 2;
		add(tfAuteur, gbc_tfAuteur);
		tfAuteur.setColumns(10);
		
	}
	/**
	 * Permet de convertir une liste en un tableau d'objet afin d'etre utilisé par un JTable. 
	 * @param liste
	 * @return Un tableau d'objet
	 */
	private Object[][] convert(List<Livre> liste ) {
		Object[][] tab =new Object[liste.size()][5];
		
		for(int i = 0; i<liste.size(); i++) {
			tab[i][0] = liste.get(i).getId();
			tab[i][1] = liste.get(i).getTitre();
			tab[i][2] = liste.get(i).getAuteur();
			tab[i][3] = liste.get(i).getPage();
			tab[i][4] = liste.get(i).getPrix();
		}
		
		return tab;
	}
	
	/**
	 * Permet de voir si un string contient un autre string tout en faisant du ignoreCase
	 * @param strSource
	 * @param strRechercher
	 * @return un boolean, TRUE si il contient strRechercher et FALSE si non.
	 */
	public static boolean containsIgnoreCase(String strSource, String strRechercher)     {
	    if(strSource == null || strRechercher == null)
	    	return false;

	    final int length = strRechercher.length();
	    if (length == 0)
	        return true;

	    for (int i = strSource.length() - length; i >= 0; i--) {
	        if (strSource.regionMatches(true, i, strRechercher, 0, length))
	            return true;
	    }
	    return false;
	}
	// Fonction qui va chercher la langue utilisé dans la classe de l'InterfacePrincipale
	public void getLanguage() {
		textLabels = InterfacePrincipale.getResourceBundle(InterfacePrincipale.ResourceLabel);
		textBoutons = InterfacePrincipale.getResourceBundle(InterfacePrincipale.ResourceBouton);
		if(textLabels==null) {
			textLabels=ResourceBundle.getBundle("properties.Labels", Locale.getDefault());
		}
		if(textBoutons==null) {
			textBoutons=ResourceBundle.getBundle("properties.Boutons", Locale.getDefault());
		}
	}

}
