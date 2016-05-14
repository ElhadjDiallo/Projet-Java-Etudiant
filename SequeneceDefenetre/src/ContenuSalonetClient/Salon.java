package ContenuSalonetClient;

import java.util.ArrayList;

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
