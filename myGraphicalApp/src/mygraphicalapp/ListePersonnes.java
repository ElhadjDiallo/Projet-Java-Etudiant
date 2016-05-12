/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygraphicalapp;

import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author 21507165
 */
public class ListePersonnes extends AbstractListModel<Personne> {//on utilise AbstractListModel<Personnes> afin que notre Jlist prélève ses éléments dans cette classe

    private ArrayList<Personne> maListe;

    public ListePersonnes() {
        maListe = new ArrayList<Personne>();
    }

    public void add(Personne p) {
        maListe.add(p);
        fireContentsChanged(this, 0, maListe.size());//lorsqu'on rajoute une personne à la liste, on lance une analyse sur le contenu de la liste, du premier au dernier élement afin de prendre en compte le changement de taille de la liste
    }

    @Override
    public int getSize() {
        return maListe.size();
    }

    @Override
    public Personne getElementAt(int index) {
        return maListe.get(index);
    }
}
