package dao;

/**
 * @author Nathaniel Larouche
 *	Fabrique d'instance qui va nous retourner l'instance d�sir� d�pendamment de la valeur entr�e en parametre
 */
public class FrabriqueDao {
	public final static int LIVRE_DAO_DB = 1;
	public final static int LIVRE_DAO_FILE = 2; 
	
	public static livreDao CreateLivreDao(int value) {
		
		if(value==LIVRE_DAO_DB) {
			return new livreDaoImplDB();
		}
		else {
			return new livreDaoImplFile();
		}
	}

}
