package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.EOFException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import CustomException.NegatifIdException;
import CustomException.SameIdException;
import dao.FrabriqueDao;
import dao.Livre;
import dao.livreDao;
import dao.livreDaoImplDB;
import java.awt.Color;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

/**
 * @author Nathaniel Larouche
 * Classe AjoutLivre qui derivent de JPanel dont l'utilité est d'ajouter un livre dans un des supports de stockage.
 *  
 */
public class AjoutLivre extends JPanel {

	private JFrame frame;
	private JTextField tfId;
	private JTextField tfTitre;
	private JTextField tfAuteur;
	private JTextField tfNbPage;
	private JTextField tfPrix;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.getContentPane().add(new AjoutLivre(),BorderLayout.CENTER);
		f.setBounds(100, 100, 539, 300);
		f.setSize(800, 500);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
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
	
	/**
	 * Constructeur par défaut, il contiens tout les éléments visuels et ainsi que les interactions avec celui-ci
	 */
	public AjoutLivre() {
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
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] {36, 131, 117, 0, 0};
		gbl_panel.rowHeights = new int[] {36, 36, 36, 36, 36, 36, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		this.setLayout(gbl_panel);
		
		//Le titre
		JLabel lblAjoutDunLivre = new JLabel(textLabels.getString("lblAjoutDunLivre"));
		lblAjoutDunLivre.setName("lblAjoutDunLivre");
		lblAjoutDunLivre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		GridBagConstraints gbc_lblAjoutDunLivre = new GridBagConstraints();
		gbc_lblAjoutDunLivre.gridwidth = 4;
		gbc_lblAjoutDunLivre.insets = new Insets(0, 0, 5, 5);
		gbc_lblAjoutDunLivre.gridx = 0;
		gbc_lblAjoutDunLivre.gridy = 0;
		this.add(lblAjoutDunLivre, gbc_lblAjoutDunLivre);
		
		JLabel lblId = new JLabel(textLabels.getString("lblId"));
		lblId.setBackground(new Color(64, 224, 208));
		lblId.setName("lblId");
		lblId.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 1;
		gbc_lblId.gridy = 1;
		this.add(lblId, gbc_lblId);
		
		tfId = new JTextField();
		GridBagConstraints gbc_tfId = new GridBagConstraints();
		gbc_tfId.insets = new Insets(0, 0, 5, 5);
		gbc_tfId.fill = GridBagConstraints.BOTH;
		gbc_tfId.gridx = 2;
		gbc_tfId.gridy = 1;
		this.add(tfId, gbc_tfId);
		tfId.setColumns(10);
		
		JLabel lblTitre = new JLabel(textLabels.getString("lblTitre"));
		lblTitre.setBackground(new Color(64, 224, 208));
		lblTitre.setName("lblTitre");
		lblTitre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 1;
		gbc_lblTitre.gridy = 2;
		this.add(lblTitre, gbc_lblTitre);
		
		tfTitre = new JTextField();
		GridBagConstraints gbc_tfTitre = new GridBagConstraints();
		gbc_tfTitre.insets = new Insets(0, 0, 5, 5);
		gbc_tfTitre.fill = GridBagConstraints.BOTH;
		gbc_tfTitre.gridx = 2;
		gbc_tfTitre.gridy = 2;
		this.add(tfTitre, gbc_tfTitre);
		tfTitre.setColumns(10);
		
		JLabel lblAuteur = new JLabel(textLabels.getString("lblAuteur"));
		lblAuteur.setBackground(new Color(64, 224, 208));
		lblAuteur.setName("lblAuteur");
		lblAuteur.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		GridBagConstraints gbc_lblAuteur = new GridBagConstraints();
		gbc_lblAuteur.insets = new Insets(0, 0, 5, 5);
		gbc_lblAuteur.gridx = 1;
		gbc_lblAuteur.gridy = 3;
		this.add(lblAuteur, gbc_lblAuteur);
		
		tfAuteur = new JTextField();
		GridBagConstraints gbc_tfAuteur = new GridBagConstraints();
		gbc_tfAuteur.insets = new Insets(0, 0, 5, 5);
		gbc_tfAuteur.fill = GridBagConstraints.BOTH;
		gbc_tfAuteur.gridx = 2;
		gbc_tfAuteur.gridy = 3;
		this.add(tfAuteur, gbc_tfAuteur);
		tfAuteur.setColumns(10);
		
		JLabel lblPage = new JLabel(textLabels.getString("lblPage"));
		lblPage.setBackground(new Color(64, 224, 208));
		lblPage.setName("lblPage");
		lblPage.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		GridBagConstraints gbc_lblPage = new GridBagConstraints();
		gbc_lblPage.insets = new Insets(0, 0, 5, 5);
		gbc_lblPage.gridx = 1;
		gbc_lblPage.gridy = 4;
		this.add(lblPage, gbc_lblPage);
		
		tfNbPage = new JTextField();
		GridBagConstraints gbc_tfNbPage = new GridBagConstraints();
		gbc_tfNbPage.insets = new Insets(0, 0, 5, 5);
		gbc_tfNbPage.fill = GridBagConstraints.BOTH;
		gbc_tfNbPage.gridx = 2;
		gbc_tfNbPage.gridy = 4;
		this.add(tfNbPage, gbc_tfNbPage);
		tfNbPage.setColumns(10);
		
		JLabel lblPrix = new JLabel(textLabels.getString("lblPrix"));
		lblPrix.setBackground(new Color(64, 224, 208));
		lblPrix.setName("lblPrix");
		lblPrix.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		GridBagConstraints gbc_lblPrix = new GridBagConstraints();
		gbc_lblPrix.insets = new Insets(0, 0, 5, 5);
		gbc_lblPrix.gridx = 1;
		gbc_lblPrix.gridy = 5;
		this.add(lblPrix, gbc_lblPrix);
		
		tfPrix = new JTextField();
		GridBagConstraints gbc_tfPrix = new GridBagConstraints();
		gbc_tfPrix.fill = GridBagConstraints.BOTH;
		gbc_tfPrix.insets = new Insets(0, 0, 5, 5);
		gbc_tfPrix.gridx = 2;
		gbc_tfPrix.gridy = 5;
		this.add(tfPrix, gbc_tfPrix);
		tfPrix.setColumns(10);
		
		JButton btnAjouter = new JButton(textBoutons.getString("btnAjouter"));
		btnAjouter.setAutoscrolls(true);
		btnAjouter.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnAjouter.setForeground(new Color(255, 255, 255));
		btnAjouter.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		btnAjouter.setBackground(new Color(0, 102, 51));
		btnAjouter.setName("btnAjouter");
		btnAjouter.setPreferredSize(new Dimension(82, 23));
		
		/*
		 * L'appuie sur le bouton ajouter permet de prendre les informations dans les textfields et de les
		 * stocker dans un des supports de stockage voulu 
		 */
		btnAjouter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					Livre l = new Livre();
					l.setId(Integer.parseInt(tfId.getText()));
					l.setTitre(tfTitre.getText());
					l.setAuteur(tfAuteur.getText());
					l.setPrix(Double.parseDouble(tfPrix.getText()));
					l.setPage(Integer.parseInt(tfNbPage.getText()));
					//Appel de la fabriqueDao afin de savoir quel implementation
					livreDao livreDao = FrabriqueDao.CreateLivreDao(InterfacePrincipale.getImplementationDao()); 
					livreDao.add(l);
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreAjouter"));
				}
				catch(NumberFormatException exp) {
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneErreurParser"));
				}
				catch(SQLIntegrityConstraintViolationException sqlError) {
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreIdUtiliser"));
				}
				catch(SameIdException sameid) {
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneLivreIdUtiliser"));
					System.out.println(sameid);
				}
				catch(NegatifIdException NIE) {
					JOptionPane.showMessageDialog(frame,textLabels.getString("JOptionPaneIllegalID"));
				}
				catch(Exception exception) {
					exception.printStackTrace();
				}
				
			}
		});
		
		JButton btnAnnuler = new JButton(textBoutons.getString("btnAnnuler"));
		btnAnnuler.setAutoscrolls(true);
		btnAnnuler.setBorder(new LineBorder(new Color(0, 0, 0), 2, true));
		btnAnnuler.setForeground(new Color(255, 255, 255));
		btnAnnuler.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		btnAnnuler.setBackground(new Color(0, 102, 51));
		btnAnnuler.setName("btnAnnuler");
		
		//Vide tout les textfields lors de l'appui d'annuler.
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfId.setText("");
				tfTitre.setText("");
				tfAuteur.setText("");
				tfPrix.setText("");
				tfNbPage.setText("");
			}
		});
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.fill = GridBagConstraints.BOTH;
		gbc_btnAnnuler.insets = new Insets(0, 0, 0, 5);
		gbc_btnAnnuler.gridx = 1;
		gbc_btnAnnuler.gridy = 6;
		add(btnAnnuler, gbc_btnAnnuler);
		GridBagConstraints gbc_btnAjouter = new GridBagConstraints();
		gbc_btnAjouter.insets = new Insets(0, 0, 0, 5);
		gbc_btnAjouter.fill = GridBagConstraints.BOTH;
		gbc_btnAjouter.gridx = 2;
		gbc_btnAjouter.gridy = 6;
		add(btnAjouter, gbc_btnAjouter);
	}


}
