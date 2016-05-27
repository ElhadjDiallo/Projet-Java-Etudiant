/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Test;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


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
     */
   
    private Connection connexion; 
    private String nomClient;
    
    public MsgPrive(final String nomClt1, final String nomClt2) {
        initComponents();
        this.nomClient1 = nomClt1;
        this.nomClient2 = nomClt2;
        name.setText(nomClt1);
        
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

            String url = "jdbc:postgresql://localhost:5433/";
            url+=con.getnombd();
            
            

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
       // String nomClient = new String();
        ArrayList <String>listMessag=new ArrayList<>();

        //afficher.setText(texte);
       String requeteIdmembre1="Select id_membre from utilisateur where login='"+nomClient1+"'";
       // String requeteIdmembre1="Select id_membre from utilisateur where login='bah'";
        
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteIdmembre1);

            while (resultat.next())
            {

                idmembre1=resultat.getInt("id_membre");
                System.out.println(idmembre1);
            }
            instruction.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

         String requeteidmembre2="select id_membre from utilisateur where login='"+nomClient2+"'";
        //String requeteidmembre2="Select id_membre from utilisateur where login='diallo'";
   
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteidmembre2);

            while (resultat.next())
            {

                idmembre2=resultat.getInt("id_membre");
                System.out.println(idmembre2);

            }
            instruction.close();

        } catch (Exception e) {
            e.printStackTrace();

        }
        /*recuperer message */
       String reqrecupmsg="Select message from messageprive where (id_membre1="+idmembre1+"and id_membre2 ="+idmembre2+")";
        //String reqrecupmsg="Select message from messageprive where idmembre ";
       
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(reqrecupmsg);

            while (resultat.next())
            {

                String recup = resultat.getString("message");
                System.out.println(recup);
                listMessag.add(recup);
               
            }
            instruction.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        /*------------------*/
        String reqrecuplogin= "Select login from utilisateur where id_membre='"+idmembre1+"'";
        //String reqrecupmsg="Select message from messageprive where idmembre ";
      
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(reqrecuplogin);
              while (resultat.next()){
                login = resultat.getString("login");
                System.out.println(login);
               
              }
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*recup temps*/
         String reqrecuptemps="Select message_time from messageprive where id_membre1='"+idmembre1+"'";
        //String reqrecupmsg="Select message from messageprive where idmembre ";
      
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(reqrecuptemps);
              while (resultat.next()){
                time = resultat.getString("message_time");
                System.out.println(time);
              }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        /*------------------*/
        String text=new String();
        for(String mes:listMessag)
        {
            text+="["+login+"]";
            text+=mes;
            text+="\t"+time+"";
            text+="\n";
        }
        afficher.setText(text);
        
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

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(240, 240, 190));

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
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/strilogo_opt(1).png"))); // NOI18N

        jScrollPane2.setViewportView(afficher);

        name.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(Normal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Gras)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Italique))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(137, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(name))
                            .addComponent(scrollpaneSaisie))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(bt_ajouter)
                        .addGap(42, 42, 42))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(46, 46, 46)
                        .addComponent(name)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 110, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Normal)
                            .addComponent(Gras)
                            .addComponent(Italique))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scrollpaneSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(bt_ajouter, javax.swing.GroupLayout.Alignment.TRAILING))
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
        //afficher.setText(texte);
       String requeteIdmembre1="Select id_membre from utilisateur where login='"+nomClient1+"'";
       // String requeteIdmembre1="Select id_membre from utilisateur where login='bah'";
        connexionAlabase();
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteIdmembre1);

            while (resultat.next())
            {

                idmembre1=resultat.getInt("id_membre");
                System.out.println(idmembre1);
            }

        } catch (Exception e) {
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
        String requeteInsertion="INSERT INTO messageprive VALUES";
        requeteInsertion+="("+idmembre1+","+idmembre2+","+"CURRENT_TIMESTAMP"+",'"+texte+"')";
 
        try {
            Statement instruction = connexion.createStatement();
            instruction.executeUpdate(requeteInsertion);
          

        } catch (Exception e) {
            e.printStackTrace();

        }
      
        /*MsgPrive client  = new MsgPrive(nomClient);
        client.setVisible(true);
        client.jTextArea1.setText(texte);*/

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
        /* Set the Nimbus look and feel */
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
              // MsgPrive c = new MsgPrive("bah", "diallo");
             // c.setVisible(true);
            }
       
                });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton Gras;
    private javax.swing.JRadioButton Italique;
    private javax.swing.JRadioButton Normal;
    private javax.swing.JTextPane afficher;
    private javax.swing.JButton bt_ajouter;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel name;
    private javax.swing.JScrollPane scrollpaneSaisie;
    private javax.swing.JTextArea zoneDeSaisie;
    // End of variables declaration//GEN-END:variables
}
