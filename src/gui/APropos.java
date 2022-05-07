package gui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.Font;
import java.awt.Color;

/**
 * @author Nathaniel Larouche
 * Classe APropos qui herite du JPanel qui va nous afficher les informations relatives au propriétaire du logiciel
 */
public class APropos extends JPanel {
	private ResourceBundle textLabels;
	/**
	 * Constructeur pour la page APropos
	 */
	public APropos() {
		setBackground(new Color(0, 255, 255));
		//Detecte le changement de langue lorsque tout les labels/boutons/menu prennent la nouvelle langue.
		//Cela ce manifeste par la modification du border donc ici on s'en sert pour récupérer la langue.
		addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if(evt.getPropertyName().equals("border"))
					getLanguage();
				
			}
		});
		
		getLanguage();
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblTitre = new JLabel(textLabels.getString("aProposTitle"));
		lblTitre.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitre.setName("aProposTitle");
		GridBagConstraints gbc_lblTitre = new GridBagConstraints();
		gbc_lblTitre.gridwidth = 10;
		gbc_lblTitre.fill = GridBagConstraints.VERTICAL;
		gbc_lblTitre.insets = new Insets(0, 0, 5, 5);
		gbc_lblTitre.gridx = 1;
		gbc_lblTitre.gridy = 1;
		add(lblTitre, gbc_lblTitre);
		
		JLabel lblDesc = new JLabel(textLabels.getString("AProposDesc"));
		lblDesc.setName("AProposDesc");
		GridBagConstraints gbc_lblDesc = new GridBagConstraints();
		gbc_lblDesc.fill = GridBagConstraints.VERTICAL;
		gbc_lblDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblDesc.gridx = 5;
		gbc_lblDesc.gridy = 2;
		add(lblDesc, gbc_lblDesc);
		
	}
	
	// Fonction qui va chercher la langue utilisé dans la classe de l'InterfacePrincipale
	public void getLanguage() {
		textLabels = InterfacePrincipale.getResourceBundle(InterfacePrincipale.ResourceLabel);
		if(textLabels==null) {
			textLabels=ResourceBundle.getBundle("properties.Labels", Locale.getDefault());
		}
	}

}
