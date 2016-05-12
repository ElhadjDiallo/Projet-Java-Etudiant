/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.sql.*;

/**
 *
 * @author bah
 */// Source du programme - lecture de la table < Utilisateur > de la base de données < ChatStri > 



public class LectureTable {
  public static void main(String[] args) {
    try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5433/ChatStri";
            String user = "postgres";
            String password = "xxxxx";

            Connection connexion = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion effective !");
      Statement instruction = connexion.createStatement();
      ResultSet résultat = instruction.executeQuery("SELECT * FROM utilisateur");
      while (résultat.next()) {
        System.out.println("---------------------------------");
        System.out.println("id_membre : "+ résultat.getString("id_membre"));
        System.out.println("Login : "+ résultat.getString("login"));
        System.out.println("Mot de passe : "+ résultat.getString("mdp"));
      }
    } catch (Exception e) {
            e.printStackTrace();
        }
  }
}