/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.stri.tchat;

import ContenuSalonetClient.Client;
import ContenuSalonetClient.Salon;
import Test.Connexion;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *https://github.com/ElhadjDiallo/Projet-Java-Etudiant.git
 * @author elhadj
 */
public class IhmSalon extends javax.swing.JPanel {

    /**
     * Creates new form IhmSalon
     */
    private ListeduContenuDunSalonIhm listModelIhm;
    private String gestionaffichage;
    public IhmSalon(Salon salon) {
       
       listModelIhm=new ListeduContenuDunSalonIhm(salon.getNom());
        initComponents();
        listModelIhm.ajouter(salon);
        this.setName(salon.getNom());
       listContenuSalonIhmCellrendere();
       //this.gestionaffichage="normal";
       
        
    }
    
    
    public void afficher(String texte,String selection)
    {
        
       
        
     StyledDocument document = new DefaultStyledDocument();
    SimpleAttributeSet attributes = new SimpleAttributeSet();
   // attributes = new SimpleAttributeSet();
    if(selection.compareTo("gras")==0)
    {
    attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
    
  
    }
    else if(selection.compareTo("italique")==0)
    {
    attributes.addAttribute(StyleConstants.CharacterConstants.Italic, Boolean.TRUE);    
    }
    else if(selection.compareTo("normal")==0)
    {
        Font f = new Font("Courier", Font.BOLD, 50);
        attributes.addAttribute(f, Boolean.TRUE);
    }
    else 
    {
        Font f = new Font("Courier", Font.BOLD, 50);
        attributes.addAttribute(f, Boolean.TRUE);
    }
    try {
      document.insertString(document.getLength(),texte, attributes);
       
        affichageSalonClient.setDocument(document);
        affichageSalonClient.setCaretPosition(document.getLength());
    } catch (BadLocationException badLocationException) {

       badLocationException.printStackTrace();
    }
      
      // affichageSalonClient.setText(texte);
    }
    public String getNom()
    {
        return this.getName();
    }
    
    public void listContenuSalonIhmCellrendere()
    {
       jlistIhm.setCellRenderer(new ListCellRenderer<Client>() {

           @Override
           public Component getListCellRendererComponent(JList<? extends Client> list, Client value, int index, boolean isSelected, boolean cellHasFocus) {
               JLabel resultat=new JLabel();
               resultat.setText(value.toString());
               resultat.setIcon(value.etatclient());
             
               
             
               if(isSelected)
               {
                   
                   
                   resultat.setBackground(Color.CYAN);
                 
                   
               }
               else 
               {
                   resultat.setBackground(list.getBackground());
               }
               
               resultat.setEnabled(list.isEnabled());
        
              resultat.setOpaque(true);

               return resultat;
               
           }
       });
        
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jlistIhm = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        affichageSalonClient = new javax.swing.JTextPane();

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setBackground(new java.awt.Color(240, 240, 188));

        jScrollPane4.setHorizontalScrollBar(null);

        jlistIhm.setBackground(new java.awt.Color(221, 213, 236));
        jlistIhm.setModel(listModelIhm);
        jlistIhm.setLayoutOrientation(javax.swing.JList.VERTICAL_WRAP);
        jlistIhm.setVisibleRowCount(-1);
        jScrollPane4.setViewportView(jlistIhm);

        affichageSalonClient.setEditable(false);
        affichageSalonClient.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                affichageSalonClientMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                affichageSalonClientMouseExited(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                affichageSalonClientMousePressed(evt);
            }
        });
        jScrollPane2.setViewportView(affichageSalonClient);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 438, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(69, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(13, 13, 13)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(163, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 73, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(18, 18, 18)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(332, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void affichageSalonClientMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_affichageSalonClientMouseExited
        // TODO add your handling code here:
        this.gestionaffichage="toucher";
        
    }//GEN-LAST:event_affichageSalonClientMouseExited

    private void affichageSalonClientMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_affichageSalonClientMousePressed
        // TODO add your handling code here:
       
        
    }//GEN-LAST:event_affichageSalonClientMousePressed

    private void affichageSalonClientMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_affichageSalonClientMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_affichageSalonClientMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextPane affichageSalonClient;
    private javax.swing.JList jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JList jlistIhm;
    // End of variables declaration//GEN-END:variables
}
