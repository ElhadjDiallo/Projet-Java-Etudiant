/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygraphicalapp;

/**
 *
 * @author 21507165
 */
public class Personne {

    private String nom;
    private String prenom;

    public Personne(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom + " - " + prenom;
    }
}
