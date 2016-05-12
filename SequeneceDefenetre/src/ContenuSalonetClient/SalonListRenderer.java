package ContenuSalonetClient;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;


public  class SalonListRenderer extends JLabel implements ListCellRenderer<SalonClient>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Color selectCouleur = Color.green;
  @SuppressWarnings("unused")
private SalonClient client;
  
	@Override
	public Component getListCellRendererComponent(JList<? extends SalonClient> list, SalonClient value, int index,
			boolean isSelected, boolean cellHasFocus) {
		// TODO Auto-generated method stub
			client = (SalonClient) value;
	           setText(value.toString());
		 
		 if (isSelected) {
			 
	         setBackground(list.getSelectionBackground());
       	         setForeground(list.getSelectionForeground());
	         

	      }
	      else {
	         setBackground(list.getBackground());
	         setForeground(list.getForeground());
	      }
		 list.setBackground(new Color(220, 220, 220));
		 if(value instanceof Salon)
			{
				setBackground(Color.green);
			
			}
		 if(value instanceof Client)
		 {
			 
			
			setForeground(Color.blue);
		}
		 
	
	  
	      setEnabled(list.isEnabled());
	      setFont(list.getFont());
	      setOpaque(true);
	  
	      return this;
	  

	}
	

}

