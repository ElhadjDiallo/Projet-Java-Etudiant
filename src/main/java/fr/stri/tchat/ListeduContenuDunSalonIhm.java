/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.stri.tchat;

import ContenuSalonetClient.Client;
import ContenuSalonetClient.Salon;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author elhadj
 * 
 */

public class ListeduContenuDunSalonIhm extends AbstractListModel<Client>  {
   
     private ArrayList<Client>listecontenusalonIhm;

     public ListeduContenuDunSalonIhm(String nomSalon)
     {
         listecontenusalonIhm=new ArrayList<Client>();
         
     }
    
    
    @Override
    public int getSize() {
      return listecontenusalonIhm.size();
    }

    @Override
    public Client getElementAt(int index) {
        return listecontenusalonIhm.get(index);
    }
    public void ajouter(Salon salon)
    {
        for(Client cl:salon.getlisteDesClientduSalon())
        {
            listecontenusalonIhm.add(cl);
        }
        fireContentsChanged(this, 0, listecontenusalonIhm.size());
    }

    

}
