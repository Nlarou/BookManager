package dao;

import java.util.List;
/**
 * @author Nathaniel Larouche
 * Interface livreDao qui va servir de modele pour nos interaction avec les multiples support de stockage.
 */
public interface livreDao {
	public void add(Livre l) throws Exception;
	public void delete(Livre l) throws Exception;
	public void update(Livre l) throws Exception;
	public Livre findById(int id) throws Exception;
	public List<Livre> findAll() throws Exception;
}

