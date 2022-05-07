package dao;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import singleton.Singleton;
public class livreDaoImplDB implements livreDao {
	private Connection con= Singleton.getInstance();

	@Override
	public void add(Livre l) throws Exception {
		String req="INSERT INTO livre(id,titre,auteur,page,prix) VALUE("+l.getId()+",\""+l.getTitre()+"\",\""+
				l.getAuteur()+"\","+l.getPage()+","+l.getPrix()+")";
		
		con.createStatement().executeUpdate(req);
		
	}

	@Override
	public void delete(Livre l) throws Exception {
		String req="DELETE livre FROM livre WHERE id="+l.getId();
		con.createStatement().executeUpdate(req);
	}

	@Override
	public void update(Livre l) throws Exception {
		String req="UPDATE livre SET titre=\""+l.getTitre()+"\",auteur=\""+l.getAuteur()+"\",PAGE="+l.getPage()
		+",prix="+l.getPrix()+" WHERE id="+l.getId()+"";
		con.createStatement().executeUpdate(req);
	}

	@Override
	public Livre findById(int id) throws Exception {
		String req="SELECT * FROM livre WHERE id="+id;
		ResultSet rs=con.createStatement().executeQuery(req);
		if(rs.next()) {
			Livre l=new Livre();
			l.setId(id);
			l.setTitre(rs.getString("titre"));
			l.setAuteur(rs.getString("auteur"));
			l.setPage(rs.getInt("page"));
			l.setPrix(rs.getDouble("prix"));
			return l;
		}
		return null;
	}

	@Override
	public List<Livre> findAll() throws Exception {
		String req="SELECT * FROM livre";
		ResultSet rs=con.createStatement().executeQuery(req);
		List<Livre> liste=new ArrayList<Livre>();
		while(rs.next()) {
			Livre l=new Livre();
			l.setId(rs.getInt("id"));
			l.setTitre(rs.getString("titre"));
			l.setAuteur(rs.getString("auteur"));
			l.setPage(rs.getInt("page"));
			l.setPrix(rs.getDouble("prix"));
			liste.add(l);
		}
		return liste;
	}

}
