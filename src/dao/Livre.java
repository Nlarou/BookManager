package dao;

import java.io.Serializable;

/**
 * @author Nathaniel Larouche
 * Objet livre qui doit avoir un id, titre, auteur, prix et des pages.
 */
public class Livre implements Serializable {

	private static final long serialVersionUID = 8L;
	private int id;
	private String titre;
	private String auteur;
	private double prix;
	private int page;
	
	public Livre() {
		
	}
	public Livre(int id, String titre, String auteur, int page, float prix) {
		this.id=id;
		this.titre=titre;
		this.auteur=auteur;
		this.page=page;
		this.prix=prix;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getAuteur() {
		return auteur;
	}
	public void setAuteur(String auteur) {
		this.auteur = auteur;
	}
	public double getPrix() {
		return prix;
	}
	public void setPrix(double prix) {
		this.prix = prix;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	@Override
	public String toString() {
		return "Livre [id=" + id + ", titre=" + titre + ", auteur=" + auteur + ", prix=" + prix + ", page=" + page
				+ "]";
	}
	
}
