package ContenuSalonetClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;

public class Salon extends SalonClient {
	private String nomSalon;
 
        @SuppressWarnings("FieldMayBeFinal")
        private ArrayList<Client>listeDesClientduSalon;
      
   
        @SuppressWarnings("Convert2Diamond")
	public Salon(String nomSalon) {
		super(nomSalon);
		this.nomSalon=nomSalon;
                listeDesClientduSalon=new ArrayList<Client>();
		
		
	
        }
        
        public void recupererUnClient(String nom)
        {
            Client clientModifier;
            int i=0;
            for(Client client :listeDesClientduSalon)
            {
                if(client.getNom().compareTo(nom)==0)
                {
                   // System.out.println("client"+client);
                   //listeDesClientduSalon.remove(client);
                  
                  
                }
                i++;
            }
        }
        
        public ArrayList<Client> findClient(HashMap<Integer,FormeClientBd>boite,String nomSalon)
        {
            
            ArrayList<Client>retour=new ArrayList<>();
           
          Iterator it=boite.keySet().iterator();
          while(it.hasNext())
          {
               Object key=it.next();
              Object value=boite.get(key);
           if(((FormeClientBd)value).retournerSalon().compareTo(nomSalon)==0)
           {
               
               
                retour.add(((FormeClientBd)value).retournerClient());  
                
            
               
           }
          }
            
        
          
             
          
          return retour;
            
        }
        public ArrayList<Client> getlisteDesClientduSalon()
        {
            return listeDesClientduSalon;
        }
        /*fonction qui permet d'ajouter des client dans un salon */
        public void ajouterClientDansleSalon(Client client)
        {
            
          if(!listeDesClientduSalon.contains(client))
          {
            listeDesClientduSalon.add(client);
  
          }
         }
        public void ajouterLesClientDanslaJlistSalonClient(ListSalonClient list)
        {
           
            for(Client client:listeDesClientduSalon)
            {
                list.addSalon(client);
            }
        }
        
	public String getNomSalon() {
		return nomSalon;
	}
	public void setNomSalon(String nomSalon) {
		this.nomSalon = nomSalon;
	}
        @SuppressWarnings("override")
	public String toString()
	{
		return getNomSalon();
	}
	
  
	
     

}
