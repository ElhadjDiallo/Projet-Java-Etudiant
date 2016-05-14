/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContenuSalonetClient;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 *
 * @author elhadj
 */
public class TableSalon {
    private ArrayList<Salon> table;
    public TableSalon()
    {
        table=new ArrayList<Salon>();
    }
    
    public void ajouterUnSalon(Salon salon)
    {
       table.add(salon);
    }
    public ArrayList<Salon> getTableSalon()
    {
        return table;
    }
    
   
    
    
    
    
}
