package ContenuSalonetClient;

public class Salon extends SalonClient {
	private String nomSalon;
	public Salon(String nomSalon) {
		super(nomSalon);
		this.nomSalon=nomSalon;
		
		
	}
	public String getNomSalon() {
		return nomSalon;
	}
	public void setNomSalon(String nomSalon) {
		this.nomSalon = nomSalon;
	}
	public String toString()
	{
		return getNomSalon();
	}
	
  
	
     

}
