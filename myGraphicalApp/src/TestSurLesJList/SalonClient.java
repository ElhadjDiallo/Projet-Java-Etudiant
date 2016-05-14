package TestSurLesJList;

import javax.swing.ImageIcon;

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
