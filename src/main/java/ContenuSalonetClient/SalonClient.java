package ContenuSalonetClient;

import javax.swing.ImageIcon;

public class SalonClient {
	
	private String nom;
	
	ImageIcon icon;
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
    public String toString()
    {
    	return getNom();
    }

	
	
	

}
