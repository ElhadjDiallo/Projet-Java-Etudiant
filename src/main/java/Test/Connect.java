package Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            String password = "xxx";

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion effective !");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static boolean isValidUser(String username, String password) {
        boolean tmp = false;
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5432/projet_java";
            String userdb = "postgres";
            String passworddb = "root";

            Connection conn = DriverManager.getConnection(url, userdb, passworddb);
            System.out.println("Connexion effective !");
            String sql = "SELECT * FROM Utilisateur where Login='"+username+"' and password='"+password+"'";
            Statement stmt = conn.createStatement();
         //   stmt.setString(1, username);
          //  stmt.setString(2, password);
      ResultSet rs = stmt.executeQuery(sql);
      //STEP 5: Extract data from result set
      while(rs.next()){
         //Retrieve by column name
         
         tmp=true;
      }
      rs.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            tmp=false;
        }
        return tmp;
    }
}
