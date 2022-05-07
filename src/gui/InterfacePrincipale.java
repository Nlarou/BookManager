package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.MenuElement;

import dao.FrabriqueDao;

/**
 * @author Nathaniel Larouche
 * @date 2019-03-12
 * Interface graphique qui permet de lire et ecrire des données de livre soit dans une base de donnée ou
 * dans un fichier. L'interface nous donne l'option d'ajouté, supprimer,modifier et rechercher des livres.
 * Il nous donne l'option de changement de langue soit en francais ou en anglais.
 */
public class InterfacePrincipale extends JFrame {

	
	private static ResourceBundle textMenus;
	private static ResourceBundle textBoutons;
	private static ResourceBundle textLabels;
	public final static int ResourceMenu = 1;
	public final static int ResourceBouton = 2;
	public final static int ResourceLabel = 3;
	private Locale currentLocale = Locale.getDefault(); 
	private final Locale francais =  new Locale("fr","CA");
	private final Locale anglais = new Locale("en","CA");
	
	private static int implementationDao = 1;
	
	private JFrame frmLivreManager;
	private JMenuBar menuBar;
	private JMenu mFichier;
	private JMenuItem mItemSaveFichier;
	private JMenuItem mItemSaveDb;
	private JMenuItem mItemPropos;
	private JMenuItem mItemQuitter;
	private JMenu mFonction;
	private JMenuItem mItemAjout;
	private JMenuItem mItemSuppr;
	private JMenuItem mItemModif;
	private JMenuItem mItemRecherche;
	private JMenu mAide;
	private JMenuItem mItemAideAjt;
	private JMenuItem mItemAideModif;
	private JMenuItem mItemAideSupp;
	private JMenuItem mItemRech;
	private JMenu mLangue;
	private JMenuItem mItemFr;
	private JMenuItem mItemEng;
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InterfacePrincipale window = new InterfacePrincipale();
					window.frmLivreManager.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Créee l'application.
	 */
	public InterfacePrincipale() {
		initialize();
	}

	/**
	 * Initialise le contenu du JFrame.
	 */
	private void initialize() {
		textMenus =ResourceBundle.getBundle("properties.Menus", currentLocale);
		textBoutons = ResourceBundle.getBundle("properties.Boutons", currentLocale);
		textLabels = ResourceBundle.getBundle("properties.Labels", currentLocale);
		
		frmLivreManager = new JFrame();
		frmLivreManager.setForeground(new Color(255, 255, 255));
		frmLivreManager.setBackground(new Color(255, 255, 255));
		frmLivreManager.setName("MainFrame");
		frmLivreManager.setIconImage(Toolkit.getDefaultToolkit().getImage(InterfacePrincipale.class.getResource("/gui/Img/iconLivre60px.png")));
		frmLivreManager.setTitle(textLabels.getString("ModeDB") );
		frmLivreManager.setBounds(100, 100, 539, 300);
		frmLivreManager.setSize(800, 500);
		frmLivreManager.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLivreManager.getContentPane().setLayout(new BorderLayout(0, 0));
		
		
		menuBar = new JMenuBar();
		frmLivreManager.getContentPane().add(menuBar, BorderLayout.NORTH);
		menuBar.setAlignmentY(Component.CENTER_ALIGNMENT);
		menuBar.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		menuBar.setForeground(SystemColor.desktop);
		menuBar.setBackground(Color.LIGHT_GRAY);
		
		JPanel panel = new JPanel();
		frmLivreManager.getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		
		mFichier = new JMenu(textMenus.getString("mFichier"));
		mFichier.setName("mFichier");

		mFichier.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/IconFichierFer.png")));
		menuBar.add(mFichier);
		
		//Met l'application en mode sauvegarde dans un fichier
		mItemSaveFichier = new JMenuItem(textMenus.getString("mItemSaveFichier"));
		mItemSaveFichier.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				implementationDao=FrabriqueDao.LIVRE_DAO_FILE;
				frmLivreManager.setTitle(textLabels.getString("ModeFile") );
			}
		});
		mItemSaveFichier.setName("mItemSaveFichier");
		mItemSaveFichier.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/SaveIcon.png")));
		mFichier.add(mItemSaveFichier);
		
		//Met l'application en mode sauvegarde dans une base de donnee
		mItemSaveDb = new JMenuItem(textMenus.getString("mItemSaveDb"));
		mItemSaveDb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				implementationDao=FrabriqueDao.LIVRE_DAO_DB;
				frmLivreManager.setTitle(textLabels.getString("ModeDB") );
			}
		});
		mItemSaveDb.setName("mItemSaveDb");
		mItemSaveDb.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/DatabaseIcon.png")));
		mFichier.add(mItemSaveDb);
		
		//Nous affiche la page APropos
		mItemPropos = new JMenuItem(textMenus.getString("mItemPropos"));
		mItemPropos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Component c : frmLivreManager.getContentPane().getComponents() ) {
					if(c instanceof JPanel) {
						frmLivreManager.remove(c);
					}
				}
				frmLivreManager.getContentPane().add(new APropos(),BorderLayout.CENTER);
				frmLivreManager.validate();
			}
		});
		mItemPropos.setName("mItemPropos");
		mItemPropos.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/IconInfo.png")));
		mFichier.add(mItemPropos);
		
		//Fait quitter l'application
		mItemQuitter = new JMenuItem(textMenus.getString("mItemQuitter"));
		mItemQuitter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmLivreManager.dispose();
			}
		});
		mItemQuitter.setName("mItemQuitter");
		mItemQuitter.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/QuitterIcon.png")));
		mFichier.add(mItemQuitter);
		
		mFonction = new JMenu(textMenus.getString("mFonction"));
		mFonction.setName("mFonction");
		mFonction.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/iconEtagere20px.png")));
		menuBar.add(mFonction);
		
		//Nous affiche la page d'ajout
		mItemAjout = new JMenuItem(textMenus.getString("mItemAjout"));
		mItemAjout.setName("mItemAjout");
		mItemAjout.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/AjoutIcon.png")));
		mItemAjout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Component c : frmLivreManager.getContentPane().getComponents() ) {
					if(c instanceof JPanel) {
						frmLivreManager.remove(c);
					}
				}
				frmLivreManager.getContentPane().add(new AjoutLivre(),BorderLayout.CENTER);
				frmLivreManager.validate();
			}
		});
		mFonction.add(mItemAjout);
		
		//Nous affiche la page Supprimer
		mItemSuppr = new JMenuItem(textMenus.getString("mItemSuppr"));
		mItemSuppr.setName("mItemSuppr");
		mItemSuppr.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/IconSupprimer.png")));
		mItemSuppr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Component c : frmLivreManager.getContentPane().getComponents() ) {
					if(c instanceof JPanel) {
						frmLivreManager.remove(c);
					}
				}
				frmLivreManager.getContentPane().add(new SupprimerLivre(),BorderLayout.CENTER);
				frmLivreManager.validate();
			}
		});
		mFonction.add(mItemSuppr);
		
		//Nous affiche la page de modification
		mItemModif = new JMenuItem(textMenus.getString("mItemModif"));
		mItemModif.setName("mItemModif");
		mItemModif.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/IconModifier.png")));
		mItemModif.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Component c : frmLivreManager.getContentPane().getComponents() ) {
					if(c instanceof JPanel) {
						frmLivreManager.remove(c);
					}
				}
				frmLivreManager.getContentPane().add(new ModifierLivre(),BorderLayout.CENTER);
				frmLivreManager.validate();
			}
		});
		mFonction.add(mItemModif);
		
		//Nous affiche la page de recherche
		mItemRecherche = new JMenuItem(textMenus.getString("mItemRecherche"));
		mItemRecherche.setName("mItemRecherche");
		mItemRecherche.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/IconRecherche.png")));
		mItemRecherche.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(Component c : frmLivreManager.getContentPane().getComponents() ) {
					if(c instanceof JPanel) {
						frmLivreManager.remove(c);
					}
				}
				frmLivreManager.getContentPane().add(new RechercherLivre(),BorderLayout.CENTER);
				frmLivreManager.validate();
			}
		});
		mFonction.add(mItemRecherche);
		
		mAide = new JMenu(textMenus.getString("mAide"));
		mAide.setName("mAide");
		mAide.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/AideIcon.png")));
		menuBar.add(mAide);
		
		mItemAideAjt = new JMenuItem(textMenus.getString("mItemAideAjt"));
		mItemAideAjt.setName("mItemAideAjt");
		mItemAideAjt.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/AideIcon.png")));
		mAide.add(mItemAideAjt);
		
		mItemAideSupp = new JMenuItem(textMenus.getString("mItemAideSupp"));
		mItemAideSupp.setName("mItemAideSupp");
		mItemAideSupp.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/AideIcon.png")));
		mAide.add(mItemAideSupp);
		
		mItemAideModif = new JMenuItem(textMenus.getString("mItemAideModif"));
		mItemAideModif.setName("mItemAideModif");
		mItemAideModif.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/AideIcon.png")));
		mAide.add(mItemAideModif);
		
		mItemRech = new JMenuItem(textMenus.getString("mItemRech"));
		mItemRech.setName("mItemRech");
		mItemRech.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/AideIcon.png")));
		mAide.add(mItemRech);
		
		mLangue = new JMenu(textMenus.getString("mLangue"));
		mLangue.setName("mLangue");
		mLangue.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/IconLangue.png")));
		menuBar.add(mLangue);
		
		//Change la langue en francais
		mItemFr = new JMenuItem(textMenus.getString("mItemFr"));
		mItemFr.setName("mItemFr");
		mItemFr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLocale = francais;
				textMenus = ResourceBundle.getBundle("properties.Menus", currentLocale);
				textBoutons = ResourceBundle.getBundle("properties.Boutons", currentLocale);
				textLabels = ResourceBundle.getBundle("properties.Labels", currentLocale);
				updateLangage();
			}
		});
		mItemFr.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/FrenchFlag.png")));
		mLangue.add(mItemFr);
		
		//Change la langue en anglais
		mItemEng = new JMenuItem(textMenus.getString("mItemEng"));
		mItemEng.setName("mItemEng");
		mItemEng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currentLocale = anglais;
				textMenus = ResourceBundle.getBundle("properties.Menus", currentLocale);
				textBoutons = ResourceBundle.getBundle("properties.Boutons", currentLocale);
				textLabels = ResourceBundle.getBundle("properties.Labels", currentLocale);
				updateLangage();
			}
		});
		mItemEng.setIcon(new ImageIcon(InterfacePrincipale.class.getResource("/gui/Img/EnglishFlag.png")));
		mLangue.add(mItemEng);

	}
	
	/**
	 * Permet de donner quel est le dao présentement utilisé
	 * @return implementationDao
	 */
	public static int getImplementationDao() {
		return implementationDao;
	}
	
	/**
	 * Fonction qui permet de de retourner quel ressourceBundle est présentement utilisé pour les
	 * labels,boutons et menus
	 * @param i
	 * @return ResourceBundle
	 */
	public static ResourceBundle getResourceBundle(int i) {
		if(i==ResourceMenu) {
			return textMenus;
		}
		if(i==ResourceBouton) {
			return textBoutons;
		}
		if(i==ResourceLabel) {
			return textLabels;
		}
		return null;
	}
	
	/**
	 * Fonction qui permet dynamiquement de changer le language de chaque composant disposant de texte
	 * dans l'interface graphique en fonction des ressourceBundle utilisé a chaqu'un.
	 */
	private void updateLangage() {
		if(implementationDao==FrabriqueDao.LIVRE_DAO_DB) {
			frmLivreManager.setTitle(textLabels.getString("ModeDB") );
		}
		else {
			frmLivreManager.setTitle(textLabels.getString("ModeFile") );
		}
		//Iteration pour chaqu'un des composants du frame
		for(Component composantPrincipal: frmLivreManager.getContentPane().getComponents()) {
			//Iteration pour chaqu'un des composants soit d'un JPanel ou d'un JMenuBar pour la grande partit de l'application
			for(Component sousComposant : getAllChild(composantPrincipal)) {
				if(sousComposant instanceof JLabel) {
					((JLabel)sousComposant).setText(textLabels.getString(((JLabel)sousComposant).getName()));
				}
				if(sousComposant instanceof JButton) {
					((JButton)sousComposant).setText(textBoutons.getString(((JButton)sousComposant).getName()));
				}
				if(sousComposant instanceof JMenu) {
					((JMenu)sousComposant).setText(textMenus.getString(((JMenu)sousComposant).getName()));
					for(MenuElement popMenu: ((JMenu) sousComposant).getSubElements()) {
						for(MenuElement mItem: ((JPopupMenu) popMenu).getSubElements()) {
							if(mItem instanceof JMenuItem) {
								((JMenuItem)mItem).setText(textMenus.getString(((JMenuItem)mItem).getName()));
							}
						}
					}
				}
				
				if(sousComposant instanceof JScrollPane) {
					//Va faire l'iteration des composants du JScrollPane qui se compose majoritairement de ViewPort dont
					// l'un ce trouvent le JTable que l'on cherche
					for(Component sousComposantJScrollPane : getAllChild(sousComposant)) {
						for(Component composantJTabel : getAllChild(sousComposantJScrollPane)) {
							//Fait la mise a jour de la langue du JTable
							if(composantJTabel instanceof JTable) {
								TableModele modele;
								if(((JTable)composantJTabel).getModel() instanceof TableModele ) {
									modele = new TableModele((TableModele) ((JTable)composantJTabel).getModel());
									modele.setHeaders(new String[]{textLabels.getString("lblId"),textLabels.getString("lblTitre")
											,textLabels.getString("lblAuteur"),textLabels.getString("lblPage"),textLabels.getString("lblPrix")});
									((JTable)composantJTabel).setModel(modele);
									((JTable)composantJTabel).revalidate();
								}
							}
						}
					}
					((JScrollPane)sousComposant).revalidate();
				}
			}
			if(composantPrincipal instanceof JPanel ) {
				((JPanel)composantPrincipal).updateUI();
			}
		}
	}
	
	/**
	 * Fonction qui retourne un liste de child d'un composant.
	 * @param c
	 * @return Liste de composant fille
	 */
	private static Component[] getAllChild(Component c) {
		Component[] components = ((Container) c).getComponents();
		return components;
	}
}
