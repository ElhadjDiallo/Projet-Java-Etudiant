package ContenuSalonetClient;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class SalonListRenderer extends JLabel implements ListCellRenderer<SalonClient> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    Color selectCouleur = Color.green;
    @SuppressWarnings("unused")

    private SalonClient client;
    /* qui permet de gerer les évènements au niveau de la list */

    @Override
    public Component getListCellRendererComponent(JList<? extends SalonClient> maliste, SalonClient value, int index,
            boolean isSelected, boolean cellHasFocus) {
        // TODO Auto-generated method stub
        client = (SalonClient) value;
        setText(value.toString());

        if (isSelected) {

            setBackground(maliste.getSelectionBackground());
            setForeground(maliste.getSelectionForeground());

        } else {
            setBackground(maliste.getBackground());
            setForeground(maliste.getForeground());
        }
        maliste.setBackground(new Color(220, 220, 220));
        if (value instanceof Salon) {
            setBackground(Color.green);

        }
        if (value instanceof Client) {

            setForeground(Color.blue);
        }

        setEnabled(maliste.isEnabled());
        setFont(maliste.getFont());
        setOpaque(true);

        return this;

    }

}
