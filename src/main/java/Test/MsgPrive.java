/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;


/**
 *
 * @author bah
 */
public class MsgPrive extends javax.swing.JFrame {
    private String style;
    private String nomClient1;
    private String nomClient2;
    private  javax.swing.Timer timer1;

    /**
     * Creates new form MsgPrive
     * 
     */
   
    private Connection connexion; 
    private String nomClient;
    
    public MsgPrive(final String nomClt1, final String nomClt2) {
        initComponents();
        this.nomClient1 = nomClt1;
        this.nomClient2 = nomClt2;
        
        this.name.setText(nomClt1);
        this.destinataire.setText(nomClt2);
         this.style="normal";    
         this.inciseNomClient.setText(nomClt1.substring(0,2));
         gererSaisie();
        connexionAlabase();
         timer1 = new javax.swing.Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           //
                // mise a jour
                recupererMessage(nomClient1, nomClient2);
               
            }

                   });
        
        timer1.start();
        
    }
   public void connexionAlabase()
     {
         Connexion con =new Connexion();
         try {

            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:";
             url+=con.getport();
             url+="/"+con.getnombd();
            
            
            

           this.connexion = DriverManager.getConnection(url,con.getuser(), con.getpassword());
            System.out.println("Connexion effective !");
          
         
     
    } catch (SQLException e) {
             System.err.println("Erreur de connexion");
        }
       
      
   
 }   
        
    
    
    
    
public void recupererMessage(String nomClient1, String nomClient2){
        Integer idmembre1 = 0 ;
        Integer idmembre2 = 0 ;
        String login = null;
        String time = null;
       ArrayList<String> listid = new ArrayList<>();
        ArrayList <String>listMessag=new ArrayList<>();

      
       String requeteIdmembre1="Select id_membre from utilisateur where login='"+nomClient1+"'";
     
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteIdmembre1);

            while (resultat.next())
            {

                idmembre1=resultat.getInt("id_membre");
              
            }
            instruction.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

         String requeteidmembre2="select id_membre from utilisateur where login='"+nomClient2+"'";
       
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteidmembre2);

            while (resultat.next())
            {

                idmembre2=resultat.getInt("id_membre");
               

            }
            

        } catch (SQLException e) {
            e.printStackTrace();

        }
        /*recuperer message */
       String reqrecupmsg="Select id_membre1 ,message from messageprive where ( (id_membre1="+idmembre1+"and id_membre2 ="+idmembre2+") or (id_membre1="+idmembre2+"and id_membre2 ="+idmembre1+"))";
       
        //String reqrecupmsg="Select message from messageprive where idmembre ";
       
       String id=new String();
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(reqrecupmsg);
             
            while (resultat.next())
            {

                String recup = resultat.getString("message");
              
                listMessag.add(recup);
                id=resultat.getString("id_membre1");
             
               listid.add(id);
            }
            

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        /*recup temps*/
         String reqrecuptemps="Select message_time from messageprive where id_membre1='"+idmembre1+"'";
     
      
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(reqrecuptemps);
              while (resultat.next()){
                time = resultat.getString("message_time");
               
                 time=time.substring(0, 10);   
              }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        
        /*------------------*/
        String text=new String();
        int i=0;
        for(String mes:listMessag)
        {
             mes=mes.replaceAll("\"","'" );
            text+=""+time+"      ";
            id=listid.get(i);
            if(Integer.parseInt(id)==idmembre1)
            {
               
            text+="["+name.getText()+" ] ";    
            }
            else 
            {
               
             text+="["+destinataire.getText()+"]   ";
            }
            
            
            text+="  "+mes;
            
            text+="\n";
            i++;
        }
       
        affichage(text,this.style);
        
}
        public void gererSaisie()
     {
         zoneDeSaisie.setWrapStyleWord(true);
         zoneDeSaisie.setLineWrap(true);
     }

public void affichage(String texte,String selection)
    {
        
       
        
     StyledDocument document = new DefaultStyledDocument();
    SimpleAttributeSet attributes = new SimpleAttributeSet();
    attributes = new SimpleAttributeSet();
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
       
        afficher.setDocument(document);
        afficher.setCaretPosition(document.getLength());
    } catch (BadLocationException badLocationException) {

       badLocationException.printStackTrace();
    }
      
      // affichageSalonClient.setText(texte);
    }
  
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        scrollpaneSaisie = new javax.swing.JScrollPane();
        zoneDeSaisie = new javax.swing.JTextArea();
        bt_ajouter = new javax.swing.JButton();
        Normal = new javax.swing.JRadioButton();
        Gras = new javax.swing.JRadioButton();
        Italique = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        afficher = new javax.swing.JTextPane();
        name = new javax.swing.JLabel();
        inciseNomClient = new javax.swing.JLabel();
        destinataire = new javax.swing.JLabel();

        setBackground(new java.awt.Color(240, 240, 188));

        scrollpaneSaisie.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpaneSaisie.setFocusable(false);
        scrollpaneSaisie.setHorizontalScrollBar(null);

        zoneDeSaisie.setColumns(20);
        zoneDeSaisie.setRows(5);
        scrollpaneSaisie.setViewportView(zoneDeSaisie);

        bt_ajouter.setText("Envoyer");
        bt_ajouter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_ajouterActionPerformed(evt);
            }
        });

        buttonGroup1.add(Normal);
        Normal.setText("Normal");
        Normal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NormalActionPerformed(evt);
            }
        });

        buttonGroup1.add(Gras);
        Gras.setText("Gras");
        Gras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GrasActionPerformed(evt);
            }
        });

        buttonGroup1.add(Italique);
        Italique.setText("Italique");
        Italique.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItaliqueActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("TlwgTypewriter", 2, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(54, 36, 209));
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/strilogo.png"))); // NOI18N

        afficher.setEditable(false);
        jScrollPane2.setViewportView(afficher);

        name.setFont(new java.awt.Font("Ubuntu", 0, 18)); // NOI18N
        name.setForeground(new java.awt.Color(33, 37, 230));
        name.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/ico.gif"))); // NOI18N
        name.setText("CLIENT ");

        inciseNomClient.setFont(new java.awt.Font("URW Chancery L", 1, 24)); // NOI18N
        inciseNomClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/cl.jpg"))); // NOI18N
        inciseNomClient.setText("CL");
        inciseNomClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        destinataire.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        destinataire.setForeground(new java.awt.Color(27, 211, 28));
        destinataire.setText("          DESTINATAIRE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(scrollpaneSaisie)
                        .addGap(18, 18, 18)
                        .addComponent(bt_ajouter))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(Normal)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Gras)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(Italique)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 207, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane2)
                                        .addGap(27, 27, 27)
                                        .addComponent(inciseNomClient))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(destinataire, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(name)))))
                        .addGap(27, 27, 27))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(destinataire, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 21, 21)
                        .addComponent(name))
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(inciseNomClient)
                        .addGap(0, 269, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Normal)
                    .addComponent(Gras)
                    .addComponent(Italique))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpaneSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_ajouter))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void bt_ajouterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_ajouterActionPerformed
        // TODO add your handling code here: ici envoyer un message au client
        Integer idmembre1 = 0 ;
        Integer idmembre2 = 0 ;
        //String nomClient = new String();
        ArrayList <String>listMessag=new ArrayList<>();

        String texte=new String();
        texte=zoneDeSaisie.getText();
       zoneDeSaisie.setText("");
       String requeteIdmembre1="Select id_membre from utilisateur where login='"+nomClient1+"'";
    
        connexionAlabase();
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteIdmembre1);

            while (resultat.next())
            {

                idmembre1=resultat.getInt("id_membre");
                System.out.println(idmembre1);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

         String requeteidmembre2="select id_membre from utilisateur where login='"+nomClient2+"'";
        //String requeteidmembre2="Select id_membre from utilisateur where login='diallo'";
         connexionAlabase();
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteidmembre2);

            while (resultat.next())
            {

                idmembre2=resultat.getInt("id_membre");
                System.out.println(idmembre2);

            }

        } catch (Exception e) {
            e.printStackTrace();

        }

        /*requete envoie message*/
        texte=texte.replace("'","\"");
        String requeteInsertion="INSERT INTO messageprive VALUES";
        requeteInsertion+="("+idmembre1+","+idmembre2+","+"CURRENT_TIMESTAMP"+",'"+texte+"')";
 
        try {
            Statement instruction = connexion.createStatement();
            instruction.executeUpdate(requeteInsertion);
          

        } catch (Exception e) {
            e.printStackTrace();

        }
      
       

    }//GEN-LAST:event_bt_ajouterActionPerformed

    private void NormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NormalActionPerformed
        // TODO add your handling code here:
        if(Normal.isSelected())
        {
            this.style="normale";
        }
    }//GEN-LAST:event_NormalActionPerformed

    private void GrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GrasActionPerformed
        // TODO add your handling code here:
         if(Gras.isSelected())
          {
              
              this.style="gras";
          }
          
        
    }//GEN-LAST:event_GrasActionPerformed

    private void ItaliqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItaliqueActionPerformed
        // TODO add your handling code here:
        if(Italique.isSelected())
        {
            this.style="italique";
            
        }
    }//GEN-LAST:event_ItaliqueActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
      /*  Set the Nimbus look and feel*/ 
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MsgPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MsgPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MsgPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MsgPrive.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

      //  Create and display the form 
     /*   java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               MsgPrive c = new MsgPrive("DIALLO", "BAH");
              c.setVisible(true);
               MsgPrive d = new MsgPrive("BAH", "DIALLO");
              d.setVisible(true);
              
            }
       
                });*/
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Gras;
    private javax.swing.JRadioButton Italique;
    private javax.swing.JRadioButton Normal;
    private javax.swing.JTextPane afficher;
    private javax.swing.JButton bt_ajouter;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel destinataire;
    private javax.swing.JLabel inciseNomClient;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel name;
    private javax.swing.JScrollPane scrollpaneSaisie;
    private javax.swing.JTextArea zoneDeSaisie;
    // End of variables declaration//GEN-END:variables
}

