/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ContenuSalonetClient;

/**
 *
 * @author elhadj
 * Stocke un client la clé tel que present dans la base de donné et le nom du salon dans lequel il se trouve 
 */
public class FormeClientBd {
     private   Client leclient;
     private  int cleduClient;
     private  String nomSalon;
    public FormeClientBd(String nomClient,int cle,ClientEtat etat,String nomSalon)
    {
        this.cleduClient=cle;
        this.nomSalon=nomSalon;
        leclient=new Client(nomClient);
        leclient.setClientEtat(etat);
                
    }
    public int retournerCle()
    {
        return cleduClient;
    }
    public Client retournerClient()
    {
        return leclient;
    }
    public String retournerSalon()
    {
        return nomSalon;
    }
    
  
   
}
