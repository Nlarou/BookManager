package singleton;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

/**
 * @author Nathaniel Larouche
 * Classe Singleton dont la fonction est de nous servir de pont de connection unique entre notre application et la base de donnée
 */
public class Singleton {
	private static Connection conn = null;
	private static ResourceBundle config;
	private Singleton() {
		try {
			config = ResourceBundle.getBundle("properties.Config");
			Class.forName(config.getString("driver"));
			conn= DriverManager.getConnection(config.getString("url"),config.getString("login"),config.getString("password"));
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	static public Connection getInstance() {
		if(conn == null)
			new Singleton();
		
		return conn;
	}
}
