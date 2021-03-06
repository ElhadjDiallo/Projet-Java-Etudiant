/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContenuSalonetClient;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author elhadj
 */
/*liste qui contient un Salon et un client pour pouvoir afficher les clients contenu dans un salon*/
public class ListSalonClient extends AbstractListModel<SalonClient> {

    private List<SalonClient> ls;

    public ListSalonClient() {
        ls = new ArrayList<>();
    }
     
    @Override
    public int getSize() {
        return ls.size();
    }

    @Override
    public SalonClient getElementAt(int index) {
        return ls.get(index);
    }

    public void viderLaliste()
    {
        if(!ls.isEmpty())
        {
            ls.removeAll(ls);
        }
    }
    public void formater()
    {
        ls.clear();
    }
    public void addSalon(SalonClient salon) {
        ls.add(salon);
        fireContentsChanged(this, 0, ls.size());
    }

}
