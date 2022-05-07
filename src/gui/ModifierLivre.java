package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JTextField;

import dao.FrabriqueDao;
import dao.Livre;
import dao.livreDao;
import dao.livreDaoImplDB;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

/**
 * @author Nathaniel Larouche
 * Classe ModifierLivre héritant d'un JPanel qui permet de modifier les informations dans l'un des supports de stockage.
 */
public class ModifierLivre extends JPanel {

	private JFrame frame;
	private JTextField tfTitre;
	private JTextField tfAuteur;
	private JTextField tfPrix;
	private JTextField tfNbPage;
	private JTextField tfId;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.getContentPane().add(new ModifierLivre(), BorderLayout.CENTER);
		f.setBounds(100, 100, 539, 300);
		f.setSize(800, 500);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
	}
	/**
	 * Constructeur par défaut, il contiens tout les éléments visuels et ainsi que les interactions avec celui-ci
	 */
	public ModifierLivre() {
		//Detecte le changement de langue lorsque tout les labels/boutons/menu prennent la nouvelle langue.
		//Cela ce manifeste par la modification du border donc ici on s'en sert pour récupérer la langue.
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border"))
					getLanguage();
			}
		});
		
		/*
		 * Initialization de tout les composants visuels
		 */
		getLanguage();
		setPreferredSize(new Dimension(800, 500));
		setMinimumSize(new Dimension(200, 100));
		this.setSize(500, 300);
		this.setBackground(new Color(0, 204, 204));
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{92, 112, 89, 136, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 26, 26, 26, 26, 26, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblModifierTitre = new JLabel(textLabels.getString("lblModifierTitre"));
		lblModifierTitre.setName("lblModifierTitre");
		lblModifierTitre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		GridBagConstraints gbc_lblModifierTitre = new GridBagConstraints();
		gbc_lblModifierTitre.gridheight = 2;
		gbc_lblModifierTitre.gridwidth = 4;
		gbc_lblModifierTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblModifierTitre.gridx = 0;
		gbc_lblModifierTitre.gridy = 0;
		add(lblModifierTitre, gbc_lblModifierTitre);
		
		JLabel lblId = new JLabel(textLabels.getString("lblId"));
		lblId.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblId.setName("lblId");
		lblId.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.fill = GridBagConstraints.BOTH;
		gbc_lblId.anchor = GridBagConstraints.EAST;
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 2;
		add(lblId, gbc_lblId);
		
		JButton btnRechercher = new JButton(textBoutons.getString("btnRechercher"));
		btnRechercher.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnRechercher.setForeground(Color.WHITE);
		btnRechercher.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		btnRechercher.setName("btnRechercher");
		btnRechercher.setBackground(new Color(0, 102, 51));
		
		/* Va chercher le livre d'apres l'id du textfield dans un des supports de stockage et remplit les textfields
		 * avec les informations qui correspond.
		 */
		btnRechercher.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Livre l = new Livre();
					l.setId(Integer.parseInt(tfId.getText()));
					livreDao livreDao = FrabriqueDao.CreateLivreDao(InterfacePrincipale.getImplementationDao());
					if(livreDao.findById(l.getId()) != null) {
						l = livreDao.findById(l.getId());
						tfTitre.setText(l.getTitre());
						tfAuteur.setText(l.getAuteur());
						tfNbPage.setText(String.valueOf(l.getPage()));
						tfPrix.setText(String.valueOf(l.getPrix()));
					}
					else {
						JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreIdExistePas"));
					}
				}
				catch(NumberFormatException pars) {
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneErreurParser"));
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
			}
		});
		
		tfId = new JTextField();
		tfId.setForeground(new Color(0, 0, 0));
		tfId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_tfId = new GridBagConstraints();
		gbc_tfId.anchor = GridBagConstraints.NORTH;
		gbc_tfId.insets = new Insets(0, 0, 5, 5);
		gbc_tfId.fill = GridBagConstraints.BOTH;
		gbc_tfId.gridx = 2;
		gbc_tfId.gridy = 2;
		add(tfId, gbc_tfId);
		tfId.setColumns(10);
		GridBagConstraints gbc_btnRechercher = new GridBagConstraints();
		gbc_btnRechercher.fill = GridBagConstraints.BOTH;
		gbc_btnRechercher.insets = new Insets(0, 0, 5, 0);
		gbc_btnRechercher.gridx = 3;
		gbc_btnRechercher.gridy = 2;
		add(btnRechercher, gbc_btnRechercher);
		
		JLabel lblTitre = new JLabel(textLabels.getString("lblTitre"));
		lblTitre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblTitre.setName("lblTitre");
		lblTitre.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.fill = GridBagConstraints.BOTH;
		gbc_lblTitre.anchor = GridBagConstraints.EAST;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 0;
		gbc_lblTitre.gridy = 3;
		add(lblTitre, gbc_lblTitre);
		
		tfTitre = new JTextField();
		GridBagConstraints gbc_tfTitre = new GridBagConstraints();
		gbc_tfTitre.insets = new Insets(0, 0, 5, 0);
		gbc_tfTitre.fill = GridBagConstraints.BOTH;
		gbc_tfTitre.gridx = 3;
		gbc_tfTitre.gridy = 3;
		add(tfTitre, gbc_tfTitre);
		tfTitre.setColumns(10);
		
		JLabel lblAuteur = new JLabel(textLabels.getString("lblAuteur"));
		lblAuteur.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblAuteur.setName("lblAuteur");
		lblAuteur.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblAuteur = new GridBagConstraints();
		gbc_lblAuteur.fill = GridBagConstraints.BOTH;
		gbc_lblAuteur.anchor = GridBagConstraints.EAST;
		gbc_lblAuteur.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuteur.gridx = 0;
		gbc_lblAuteur.gridy = 4;
		add(lblAuteur, gbc_lblAuteur);
		
		tfAuteur = new JTextField();
		GridBagConstraints gbc_tfAuteur = new GridBagConstraints();
		gbc_tfAuteur.insets = new Insets(0, 0, 5, 0);
		gbc_tfAuteur.fill = GridBagConstraints.BOTH;
		gbc_tfAuteur.gridx = 3;
		gbc_tfAuteur.gridy = 4;
		add(tfAuteur, gbc_tfAuteur);
		tfAuteur.setColumns(10);
		
		JLabel lblPrix = new JLabel(textLabels.getString("lblPrix"));
		lblPrix.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblPrix.setName("lblPrix");
		lblPrix.setHorizontalAlignment(SwingConstants.RIGHT);
		GridBagConstraints gbc_lblPrix = new GridBagConstraints();
		gbc_lblPrix.fill = GridBagConstraints.BOTH;
		gbc_lblPrix.anchor = GridBagConstraints.EAST;
		gbc_lblPrix.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrix.gridx = 0;
		gbc_lblPrix.gridy = 5;
		add(lblPrix, gbc_lblPrix);
		
		tfPrix = new JTextField();
		GridBagConstraints gbc_tfPrix = new GridBagConstraints();
		gbc_tfPrix.insets = new Insets(0, 0, 5, 0);
		gbc_tfPrix.fill = GridBagConstraints.BOTH;
		gbc_tfPrix.gridx = 3;
		gbc_tfPrix.gridy = 5;
		add(tfPrix, gbc_tfPrix);
		tfPrix.setColumns(10);
		
		JLabel lblPage = new JLabel(textLabels.getString("lblPage"));
		lblPage.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblPage.setName("lblPage");
		GridBagConstraints gbc_lblPage = new GridBagConstraints();
		gbc_lblPage.anchor = GridBagConstraints.EAST;
		gbc_lblPage.insets = new Insets(0, 0, 5, 5);
		gbc_lblPage.gridx = 0;
		gbc_lblPage.gridy = 6;
		add(lblPage, gbc_lblPage);
		
		tfNbPage = new JTextField();
		GridBagConstraints gbc_tfNbPage = new GridBagConstraints();
		gbc_tfNbPage.insets = new Insets(0, 0, 5, 0);
		gbc_tfNbPage.fill = GridBagConstraints.BOTH;
		gbc_tfNbPage.gridx = 3;
		gbc_tfNbPage.gridy = 6;
		add(tfNbPage, gbc_tfNbPage);
		tfNbPage.setColumns(10);
		
		JButton btnAnnuler = new JButton(textBoutons.getString("btnAnnuler"));
		btnAnnuler.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnAnnuler.setForeground(Color.WHITE);
		btnAnnuler.setBackground(new Color(0, 102, 51));
		btnAnnuler.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		btnAnnuler.setName("btnAnnuler");
		//Vide les champs des textfields
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfId.setText("");
				tfTitre.setText("");
				tfAuteur.setText("");
				tfNbPage.setText("");
				tfPrix.setText("");
			}
		});
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.fill = GridBagConstraints.BOTH;
		gbc_btnAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_btnAnnuler.gridx = 1;
		gbc_btnAnnuler.gridy = 7;
		add(btnAnnuler, gbc_btnAnnuler);
		
		JButton btnModifier = new JButton(textBoutons.getString("btnModifier"));
		btnModifier.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnModifier.setForeground(Color.WHITE);
		btnModifier.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		btnModifier.setBackground(new Color(0, 102, 51));
		btnModifier.setName("btnModifier");
		/*
		 * Modifie les informations du livre selectionné
		 */
		btnModifier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Livre l = new Livre();
					l.setId(Integer.parseInt(tfId.getText()));
					livreDao livreDao = FrabriqueDao.CreateLivreDao(InterfacePrincipale.getImplementationDao());
					if(livreDao.findById(l.getId()) != null) {
						l.setTitre(tfTitre.getText());
						l.setAuteur(tfAuteur.getText());
						l.setPage(Integer.parseInt(tfNbPage.getText()));
						l.setPrix(Double.parseDouble(tfPrix.getText()));
						livreDao.update(l);
						JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreModifier"));
					}
					else {
						JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreIdExistePas"));
					}
				}
				catch(NumberFormatException pars) {
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneErreurParser"));
				}
				catch(Exception exc) {
					exc.printStackTrace();
				}
				
			}
		});
		GridBagConstraints gbc_btnModifier = new GridBagConstraints();
		gbc_btnModifier.fill = GridBagConstraints.BOTH;
		gbc_btnModifier.insets = new Insets(0, 0, 0, 5);
		gbc_btnModifier.gridx = 2;
		gbc_btnModifier.gridy = 7;
		add(btnModifier, gbc_btnModifier);
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
