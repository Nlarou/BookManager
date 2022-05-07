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
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.FrabriqueDao;
import dao.Livre;
import dao.livreDao;
import javax.swing.border.LineBorder;
/**
 * @author Nathaniel Larouche
 * Classe SupprimerLivre qui derivent de JPanel dont l'utilité est de supprimer un livre dans un des supports de stockage.
 */
public class SupprimerLivre extends JPanel {
	private JTextField tfId;
	private ResourceBundle textBoutons;
	private ResourceBundle textLabels;

	public static void main(String[] args) {
		JFrame f=new JFrame();
		f.getContentPane().add(new SupprimerLivre(), BorderLayout.CENTER);
		f.setBounds(100, 100, 539, 300);
		f.setSize(800, 500);
		f.setVisible(true);
		f.pack();
		f.setDefaultCloseOperation(f.DISPOSE_ON_CLOSE);
	}
	/*
	 * Constructeur par défaut, il contiens tout les éléments visuels et ainsi que les interactions avec celui-ci
	 */
	/**
	 * 
	 */
	public SupprimerLivre() {
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
		gridBagLayout.columnWidths = new int[]{52, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblSuppressionDunLivre = new JLabel(textLabels.getString("lblSuppressionDunLivre"));
		lblSuppressionDunLivre.setName("lblSuppressionDunLivre");
		lblSuppressionDunLivre.setHorizontalAlignment(SwingConstants.CENTER);
		lblSuppressionDunLivre.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 16));
		GridBagConstraints gbc_lblSuppressionDunLivre = new GridBagConstraints();
		gbc_lblSuppressionDunLivre.gridwidth = 3;
		gbc_lblSuppressionDunLivre.fill = GridBagConstraints.BOTH;
		gbc_lblSuppressionDunLivre.insets = new Insets(0, 0, 5, 5);
		gbc_lblSuppressionDunLivre.gridx = 0;
		gbc_lblSuppressionDunLivre.gridy = 0;
		add(lblSuppressionDunLivre, gbc_lblSuppressionDunLivre);
		
		JLabel lblId = new JLabel(textLabels.getString("lblId"));
		lblId.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblId.setName("lblId");
		GridBagConstraints gbc_lblId = new GridBagConstraints();
		gbc_lblId.insets = new Insets(0, 0, 5, 5);
		gbc_lblId.gridx = 0;
		gbc_lblId.gridy = 3;
		add(lblId, gbc_lblId);
		
		tfId = new JTextField();
		GridBagConstraints gbc_tfId = new GridBagConstraints();
		gbc_tfId.insets = new Insets(0, 0, 5, 5);
		gbc_tfId.fill = GridBagConstraints.BOTH;
		gbc_tfId.gridx = 1;
		gbc_tfId.gridy = 3;
		add(tfId, gbc_tfId);
		tfId.setColumns(10);
		
		
		JLabel lblStatus = new JLabel(textLabels.getString("lblStatus"));
		lblStatus.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 11));
		lblStatus.setName("lblStatus");
		GridBagConstraints gbc_lblStatus = new GridBagConstraints();
		gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
		gbc_lblStatus.gridx = 0;
		gbc_lblStatus.gridy = 5;
		add(lblStatus, gbc_lblStatus);
		
		JButton btnSupprimer = new JButton(textBoutons.getString("btnSupprimer"));
		btnSupprimer.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnSupprimer.setForeground(Color.WHITE);
		btnSupprimer.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		btnSupprimer.setName("btnSupprimer");
		btnSupprimer.setBackground(new Color(0, 102, 51));
		/*
		 * Va supprimer le livre possedant l'id entrée par l'utilisateur
		 */
		btnSupprimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Livre l = new Livre();
					l.setId(Integer.parseInt(tfId.getText()));
					livreDao livreDao = FrabriqueDao.CreateLivreDao(InterfacePrincipale.getImplementationDao());
					if(InterfacePrincipale.getImplementationDao()==FrabriqueDao.LIVRE_DAO_DB) {
						if(livreDao.findById(l.getId()) != null ) {
							livreDao.delete(l);
							lblStatus.setText(textLabels.getString("lblStatusn1"));
						}
						else {
							lblStatus.setText(textLabels.getString("lblStatusn2"));
						}
					}
					else {
						livreDao.delete(l);
						lblStatus.setText(textLabels.getString("lblStatusn1"));
					}
				}
				catch(NumberFormatException pars) {
				}
				catch(Exception exc) {
					lblStatus.setText(textLabels.getString("lblStatusn2"));
					exc.printStackTrace();
				}
			}
		});
		GridBagConstraints gbc_btnSupprimer = new GridBagConstraints();
		gbc_btnSupprimer.fill = GridBagConstraints.BOTH;
		gbc_btnSupprimer.insets = new Insets(0, 0, 5, 0);
		gbc_btnSupprimer.gridx = 2;
		gbc_btnSupprimer.gridy = 3;
		add(btnSupprimer, gbc_btnSupprimer);
		
		JButton btnAnnuler = new JButton(textBoutons.getString("btnAnnuler"));
		btnAnnuler.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		btnAnnuler.setForeground(Color.WHITE);
		btnAnnuler.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 12));
		btnAnnuler.setName("btnAnnuler");
		btnAnnuler.setBackground(new Color(0, 102, 51));
		btnAnnuler.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tfId.setText("");
			}
		});
		GridBagConstraints gbc_btnAnnuler = new GridBagConstraints();
		gbc_btnAnnuler.fill = GridBagConstraints.BOTH;
		gbc_btnAnnuler.insets = new Insets(0, 0, 5, 5);
		gbc_btnAnnuler.gridx = 1;
		gbc_btnAnnuler.gridy = 7;
		add(btnAnnuler, gbc_btnAnnuler);
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
