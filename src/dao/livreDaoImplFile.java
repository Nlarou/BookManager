/**
 * 
 */
package dao;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import CustomException.*;

/**
 * @author Nathaniel Larouche
 * Implementation dans un fichier, il prend comme modele l'interface livreDao
 */
public class livreDaoImplFile implements livreDao{
	private String nomFichier="Livre_Stored.txt";
	/* (non-Javadoc)
	 * @see dao.livreDao#add(dao.Livre)
	 */
	@Override
	public void add(Livre l) throws Exception {
		ObjectOutputStream objectOutStream;
		File fichierDest = new File(nomFichier);
		List<Livre> listeLivre = new ArrayList<Livre>();
		
		if(l.getId()< 0) {
			throw new NegatifIdException();
		}
		/*Debut de la lecture, on utilisent la fonction findAll afin de vérifier si l'id ce trouvent 
		*déja dans la liste de livre a l'interieuredu fichier. Si elle si trouvent on lance une 
		*Exception qui va avertir la classe AjoutLivre que l'id s'y trouvent.
		*/
		if(!fichierDest.exists()) {
			fichierDest.createNewFile();
		}
		else {
			listeLivre.addAll(findAll());
			for(Livre livre : listeLivre) {
				if(l.getId() == livre.getId()) {
					throw new SameIdException();
				}
			}
		}
		//Ouverture de l'ecriture
		objectOutStream = new ObjectOutputStream(new FileOutputStream(fichierDest));
		listeLivre.add(l);
		//Tri de la liste en fonction de l'ip du plus petit au plus grand
		 Collections.sort(listeLivre, new Comparator<Livre>() {
			@Override
			public int compare(Livre o1, Livre o2) {
				return o1.getId() > o2.getId() ? 1 : o1.getId() < o2.getId() ? -1 : 0;
			}
			 
		 });
		//Ecriture dans le fichier.
		objectOutStream.writeObject(listeLivre);
		//Fermeture.
		objectOutStream.close();
	}

	/* (non-Javadoc)
	 * @see dao.livreDao#delete(dao.Livre)
	 */
	@Override
	public void delete(Livre l) throws Exception {
		ObjectOutputStream objectOutStream;
		List<Livre> listeLivre = new ArrayList<Livre>();
		//On recupere la liste de livre dans le fichier
		listeLivre.addAll(findAll());
		//Utilisation d'un iterateur afin de supprimmer l'element
		Iterator<Livre> listeLivreIterateur = listeLivre.iterator();
		while(listeLivreIterateur.hasNext()) {
			if(listeLivreIterateur.next().getId()==l.getId()) {
				objectOutStream = new ObjectOutputStream(new FileOutputStream(new File(nomFichier)));
				listeLivreIterateur.remove();
				objectOutStream.writeObject(listeLivre);
				objectOutStream.close();
			}
		}
	}

	/* (non-Javadoc)
	 * @see dao.livreDao#update(dao.Livre)
	 */
	@Override
	public void update(Livre l) throws Exception {
		ObjectOutputStream objectOutStream;
		List<Livre> listeLivre = new ArrayList<Livre>();
		listeLivre=findAll();
		for(Livre livre : listeLivre) {
			if(livre.getId()==l.getId()) {
				livre.setTitre(l.getTitre());
				livre.setAuteur(l.getAuteur());
				livre.setPage(l.getPage());
				livre.setPrix(l.getPrix());
				objectOutStream = new ObjectOutputStream(new FileOutputStream(new File(nomFichier)));
				objectOutStream.writeObject(listeLivre);
				objectOutStream.close();
			}
		}
		
		

	}

	/* (non-Javadoc)
	 * @see dao.livreDao#findById(int)
	 */
	@Override
	public Livre findById(int id) throws Exception {
		List<Livre> listeLivre = new ArrayList<Livre>();
		listeLivre=findAll();
		for(Livre livre: listeLivre) {
			if(livre.getId()==id) {
				return livre;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see dao.livreDao#findAll()
	 */
	@Override
	public List<Livre> findAll() throws Exception {
		//Ouverture de la lecture
		FileInputStream FileInStream =new FileInputStream(new File(nomFichier));
		ObjectInputStream objectInStream = new ObjectInputStream(FileInStream);
		List<Livre> listeLivre = new ArrayList<Livre>();
		//Lecture du fichier retournant la liste d'object qui est ensuite stocker dans la liste.
		listeLivre= (List<Livre>) objectInStream.readObject();
		//Fermeture de la lecture du fichier.
		objectInStream.close();
		FileInStream.close();
		
		return listeLivre;
	}
}
