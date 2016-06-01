package Test;

import gestionErreur.ErreurDeConnexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author bah
 */

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
        
    }
    
   
   public  boolean isValidUser(String username, String password,String nombd,String user,String password1,String port) {
        boolean connecter=false;
           
        
        try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:";
            url+=port+"/"+nombd;
          
            
           
            
   
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
        
        
      }
    } catch (SQLException e) {
            e.printStackTrace();
            
            
        }
        return false;
  }
 public String recupererNomAdmin(String nombd,String user,String password1,String port)
{
       
    try {

            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:";
                    url+=port+"/"+nombd;
        
    
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
      
    } catch (SQLException e) {
            e.printStackTrace();
            
            
        }
 
    return "Administrateur";

}
  public boolean existeDeja(String username,String nombd,String user,String password1,String port)
    {
     boolean connecter=false;
           
        
        try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:";
            url+=port+"/"+nombd;
          
            
           
            
   
      Connection connexion = DriverManager.getConnection(url, user, password1);
      System.out.println("Connexion effective !");
      Statement instruction = connexion.createStatement();
      ResultSet resultat = instruction.executeQuery("SELECT login FROM utilisateur");
      
      while (resultat.next()) {
           
          
         
        if(resultat.getString("login").compareTo(username)==0)
        {
           
             
             
            connecter=true;
            resultat.close();
            return connecter;
        }
       
        
      }
      resultat.close();
    } catch (SQLException e) {
            e.printStackTrace();
            
            
        }
        
        return false;   
        
        
     
    }
    

       


        
    
}

