package ContenuSalonetClient;

import javax.swing.ImageIcon;
/**
 * 
 * @author elhadj
 * pour mettre à un client une icone 
 */
public class ClientEtat {
	
	private String value;
	private ImageIcon icon;
	public ClientEtat(String value, ImageIcon icon)
	{
              this.setValue(value);
	     this.setIcon(icon);
	 
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	public String toString()
	{
		String s="";
		s+=""+getValue();
		return s;
	}

}
