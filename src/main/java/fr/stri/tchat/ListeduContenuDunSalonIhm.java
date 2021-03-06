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
//bonjour

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
    public int position(String nom)
    {
        for(Client client:listecontenusalonIhm)
        {
            if(client.getNom().compareTo(nom)==0)
            {
                return listecontenusalonIhm.indexOf(client);
            }
        }
         return -1;
    }

    @Override
    public Client getElementAt(int index) {
        return listecontenusalonIhm.get(index);
    }
    public void retirerElemen(int index)
    {
        listecontenusalonIhm.remove(index);
    }
    public void ajouter(Salon salon)
    {
        for(Client cl:salon.getlisteDesClientduSalon())
        {
            listecontenusalonIhm.add(cl);
        }
        fireContentsChanged(this, 0, listecontenusalonIhm.size());
    }
    public void viderlaliste()
    {
       
        listecontenusalonIhm.removeAll(listecontenusalonIhm);
    }

    

}
