package ContenuSalonetClient;

import javax.swing.ImageIcon;

/**
 * 
 * 
 * @author elhadj
 * pour pouvoir stocker dans la jlist à droite un client et un salon 
 */
public  class SalonClient {
	
	private String nom;
        /*pour quand il s'agit d'un client pour mentionner s'il est connecté ou pas */
        private ImageIcon icon;
	public SalonClient (String nom)
	{
	 this.setNom(nom);      
	 
	}


	public String getNom() {
		return nom;
	}





	public void setNom(String nom) {
		this.nom = nom;
	}
        @Override
    public String toString()
    {
    	return getNom();
    }

	
	
	

}
