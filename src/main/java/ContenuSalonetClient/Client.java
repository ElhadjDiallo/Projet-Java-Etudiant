
package ContenuSalonetClient;

import javax.swing.ImageIcon;


/**
 * 
 * @author elhadj
 */
public class Client extends SalonClient{
       
	
	private String nomclient;
        /*mettre en ligne ou hors ligne */
    private ClientEtat clientEtat;
	public Client(String nomClient)
	{
	    super(nomClient);
            this.nomclient=nomClient;
	    clientEtat=new ClientEtat(nomClient, new ImageIcon(getClass().getResource("/fr/stri/tchat/pasconnecte.gif")));
		
	}
	public ImageIcon etatclient()
	{
	
	  return	clientEtat.getIcon();
	}
	public void ajouterEtatClient(ImageIcon icon)
	{
	setClientEtat(new ClientEtat(getNomclient(), icon));
	}
	public String getNomclient() {
		return nomclient;
	}

	public void setNomclient(String nomclient) {
		this.nomclient = nomclient;
	}
	public String toString()
	{
		return getNomclient();
	}
	public ClientEtat getClientEtat() {
		return clientEtat;
	}
	public void setClientEtat(ClientEtat clientEtat) {
		this.clientEtat = clientEtat;
	}
	

}
