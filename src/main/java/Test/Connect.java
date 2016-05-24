package Test;

import gestionErreur.ErreurDeConnexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Connect {
  
   private int etat;
    public Connect() {
        
        // TODO Auto-generated constructor stub
        
    }
    public void setEtat(int etat)
    {
        this.etat=etat;
    }
    public int getEtat()
    {
        return etat;
    }
    
    

    /**
     * @param args
     */
    // @SuppressWarnings("unused")
   
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
    
    

   public  boolean isValidUser(String username, String password,String nombd,String user,String password1) {
        boolean connecter=false;
           
        
        try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5433/"+nombd;
          
            
           
            
   
          Connection connexion = DriverManager.getConnection(url, user, password1);
         System.out.println("Connexion effective !");
      Statement instruction = connexion.createStatement();
      ResultSet resultat = instruction.executeQuery("SELECT login,mdp,type FROM utilisateur");
      
      while (resultat.next()) {
           
          
         
        if(resultat.getString("login").compareTo(username)==0 && resultat.getString("mdp").compareTo(password)==0 )
        {
           
             if(resultat.getString("type")!=null)
             {
                 setEtat(1);
             }
             else 
             {
                 setEtat(0);
             }
             
            connecter=true;
            resultat.close();
            return connecter;
        }
        else 
        {
            System.out.println("j'ai pas encore trouvé");
            
        }
        
      }
    } catch (Exception e) {
            e.printStackTrace();
            
            
        }
        return false;
  }
 public String recupererNomAdmin(String nombd,String user,String password1)
{
       
    try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5433/"+nombd;
        
    
          Connection connexion = DriverManager.getConnection(url, user, password1);
         System.out.println("Connexion effective !");
      Statement instruction = connexion.createStatement();
      ResultSet resultat = instruction.executeQuery("SELECT login,type FROM utilisateur");
      
      while (resultat.next()) {
             
         String nomAdmin=resultat.getString("login");
          if(resultat.getString("type")!=null)
          {
              resultat.close();
              return nomAdmin;
           }
 
          
 
        
        
        
      }
      
    } catch (Exception e) {
            e.printStackTrace();
            
            
        }
 
return "Administrateur";

}
       


        
    
    }

