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
       /* 
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5433/javaSTRI";
            String user = "postgres";
            String password = "root";

            Connection con = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion effective !");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }
    
    

    static boolean isValidUser(String username, String password) {
        boolean connecter=false;
        
        try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5433/javaSTRI";
            String user = "postgres";
            String password1="diallo";

            Connection connexion = DriverManager.getConnection(url, user, password1);
            System.out.println("Connexion effective !");
      Statement instruction = connexion.createStatement();
      ResultSet resultat = instruction.executeQuery("SELECT login,mdp FROM utilisateur");
      
      while (resultat.next()) {
             
        //System.out.println("id_membre : "+ résultat.getString("id_membre"));
        if(resultat.getString("login").compareTo(username)==0 && resultat.getString("mdp").compareTo(password)==0 )
        {
            connecter=true;
            return connecter;
        }
        else 
        {
            System.out.println("ERReur");
        }
        
      }
    } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
  }
        
    
    }
       


