import java.sql.Connection;
import java.sql.DriverManager;


public class Connect {

	public Connect() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	// @SuppressWarnings("unused")
	@SuppressWarnings("unused")
	public static void main(String[] args) { 
		//ConnexionBd c = new ConnexionBd();
		//c.obtenirConnexion();
		    try {
		      Class.forName("org.postgresql.Driver");
		      System.out.println("Driver O.K.");

		      String url = "jdbc:postgresql://localhost:5433/ChatStri";
		      String user = "postgres";
		      String password = "Bah.19";
		     
		      Connection con = DriverManager.getConnection(url, user, password);
		      System.out.println("Connexion effective !");
		     
		         
		    } catch (Exception e) {
		      e.printStackTrace();
		    }     
		  }

}
