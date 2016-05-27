/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.stri.tchat;

import ContenuSalonetClient.Client;
import ContenuSalonetClient.ClientEtat;
import ContenuSalonetClient.FormeClientBd;
import ContenuSalonetClient.ListSalonClient;
import ContenuSalonetClient.Salon;
import ContenuSalonetClient.SalonClient;
import ContenuSalonetClient.TableSalon;
import Test.AjouterMembre;
import Test.Connexion;
import Test.CreerSalons;
import Test.MsgPrive;
import Test.RetirerMembres;
import Test.Supprimer_Salons;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

/**
 *
 * @author elhadj
 */
public class Ihm extends javax.swing.JFrame {

   
    /*Contenu de l'Ihm de la partie creation du salon avec la barre de clients 
    horizontal*/
    private Salon salon;
    private ListSalonClient listmodel;
    private IhmSalon ihmSalon;
    private TableSalon tablesalon;
    private ArrayList<IhmSalon>tableIhm;
    private Connection connexion;
    private String style;
    private int  indiceCourant;/* indice du placement du panel salon sur l'affichage*/
    private  javax.swing.Timer timer1;
    /**
     * Creates new form Ihm
     */
    
    
    public Ihm(String nomUtilisateur,String nomAdmin) {
        
        listmodel = new ListSalonClient();
        tableIhm=new ArrayList<IhmSalon>();
      
         initComponents();
         connexionAlabase();
         gererSaisie();
         listSalonClientCellRenderer();
         this.nomUtilisateur.setText(nomUtilisateur);
         this.jLabel1.setText(nomAdmin);
         this.inciseNomClient.setText(nomUtilisateur.substring(0, 2));
         boutonGras.setName("gras");
         boutonItalique.setName("italique");
         boutonNormal.setName("normale");
         this.style="normal";
         
          mettreAjour();
        timer1 = new javax.swing.Timer(2000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
           //
                // mise a jour
                mettreAjour();
            }

                   });
        
        timer1.start();
       
        
    }
     public void gererSaisie()
     {
         zoneDeSaisie.setWrapStyleWord(true);
         zoneDeSaisie.setLineWrap(true);
     }
     
     public void connexionAlabase()
     {
         Connexion con =new Connexion();
         try {
//      Class.forName("org.postgresql.Driver");
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
   /***
    * 
    * permet de vider tout le contenu du jtabpane onglets
    */
    private void vider()
    {
        if(onglets.getTabCount()!=0)
        {
            onglets.removeAll();
        }
           
    }
   
    /**
     * 
     * 
     */
    
    public void mettreAjour()
    {
    
       int indiceDuClient;
        String online=new String();
        HashMap<Integer,FormeClientBd> tabclient= new HashMap<Integer,FormeClientBd>();
        ArrayList<String>temp=new ArrayList<>();
          tablesalon=new TableSalon();
         indiceCourant=onglets.getSelectedIndex();
         
       
    /***
     * Recupère le nom  du salon  
     */
    
    
         try {
           
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery("Select nom_salon from salon");
            
             while (resultat.next()) {
              
              
              temp.add(resultat.getString("nom_salon"));
            
              
             
              
          }
            
        } catch (SQLException e) {
           
                   System.out.println("Erreur là");
        }
            
            int i=0;  
            /**
             * Récupère l'id de l'utilisateur son nom et son etat connecté ou pas 
             */
         try {
             for(String lesalon:temp)
             {
             
             String requetestatus="select  online_status,utilisateur.id_membre,login from enligne ,salon";
             requetestatus+=",utilisateur where enligne.id_membre=utilisateur.id_membre and ";
             requetestatus+="salon.nom_salon="+"'"+lesalon+"' and salon.id_salon=enligne.id_salon";
             
             
             ClientEtat enligne=new ClientEtat("default", new ImageIcon(getClass().getResource("/fr/stri/tchat/ico.gif")));
             ClientEtat horsligne=new ClientEtat("default", new ImageIcon(getClass().getResource("/fr/stri/tchat/pasconnecte.gif")));
            
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requetestatus);
            
            while (resultat.next())
            {
                
                
              indiceDuClient=Integer.parseInt(resultat.getString("id_membre"));
  
               online=resultat.getString("online_status");
               
              /**
               * 
               * je sauvegarde un client dans une table pour memoriser sa clé dans la table 
               */
                
                if(online.compareTo("Hors-ligne")==0)
                {
                   
                  
                FormeClientBd utiliseClient=new FormeClientBd(resultat.getString("login"), indiceDuClient, horsligne, lesalon);    
                tabclient.put(i,utiliseClient);
                    
                }
                else
                {
                    
                     
                 FormeClientBd utiliseClient=new FormeClientBd(resultat.getString("login"), indiceDuClient,enligne, lesalon);
                 tabclient.put(i, utiliseClient);
                          
                }
                
                i++;
                
            }
             resultat.close();
             }    
             
        } catch (Exception e) {
             e.printStackTrace();
        }
         
               
     
        ArrayList<Client>list=new ArrayList<>();
        try {
            for(String lessalon:temp)
            {
            
            String  requete="Select login,utilisateur.id_membre from utilisateur,salonutilisateur,salon ";
           requete+="where nom_salon=";
           requete+="'"+lessalon+"'";
           requete+=" and  salon.id_salon=salonutilisateur.id_salon and utilisateur.id_membre=salonutilisateur.id_membre"; 
              
           salon = new Salon(lessalon);
           
            Statement instruction = connexion.createStatement();
           ResultSet  requeteClientDuSalon=instruction.executeQuery(requete);
           
           while(requeteClientDuSalon.next())
              {
                  int cle=Integer.parseInt(requeteClientDuSalon.getString("id_membre"));
           /*
                  on stocke les clients dans une arrayList ordonné par leur clé 
                  */    
                 list=salon.findClient(tabclient, lessalon);
                 if(list.isEmpty()==false)
                 {
                     for(Client cl:list)
                     {
                       
                         salon.ajouterClientDansleSalon(cl);
                     }
                    
                 }
               
               
                    
              }
            tablesalon.ajouterUnSalon(salon);
           
           requeteClientDuSalon.close();
            }
            
             
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
         
                /**
                 * pour savoir qu'il y' a plus de salon dans le panel
                 */
                
                
           if(tablesalon.getTableSalon().size()==0)
           {
               indiceCourant=-1;
              listmodel.formater();
              listSalonClientCellRenderer();
              
           }
           
           vider();
           listmodel.viderLaliste();
          
          for(Salon salon2:tablesalon.getTableSalon())   
           {
               
           IhmSalon salonAjouter=new IhmSalon(salon2);
           recupererContenuSalon(salonAjouter);
           tableIhm.add(salonAjouter);
           onglets.addTab(salonAjouter.getName(), salonAjouter);
           listmodel.addSalon(salon2);
           salon2.ajouterLesClientDanslaJlistSalonClient(listmodel);
                               
            }
          boolean existe=true;
          try {
              
              onglets.getComponentAt(indiceCourant);
            
        } catch (Exception e) {
            existe=false;
        }
           
                if(existe && indiceCourant!=-1)
                {
                 onglets.setSelectedIndex(indiceCourant);       
                }
                else if(!existe && indiceCourant >=1)
                {
                    indiceCourant--;
                    onglets.setSelectedIndex(indiceCourant);
                    
                }
             
                
                     
                 
             
       
    }
    
    
    public void  listSalonClientCellRenderer()
    {
       jlistSalonClient.setCellRenderer(new ListCellRenderer<SalonClient>() {

     

         
            @Override
            public Component getListCellRendererComponent(JList<? extends SalonClient> list, SalonClient value, int index, boolean isSelected, boolean cellHasFocus) {
           JLabel f = new JLabel();
           SalonClient salonClient;
           salonClient=(SalonClient)value;
           f.setText(value.getNom());
           list.setBackground(new Color(220, 220, 220));
           if(isSelected)
           {
               if(value instanceof Salon)
               {
               f.setForeground(Color.red);    
               }
               else 
               {    
                   MsgPrive ms=new MsgPrive(nomUtilisateur.getText(),value.getNom());
                   ms.setVisible(true);
                   f.setBackground(Color.CYAN);
                   list.clearSelection();
               }
             
                   
            
               
           }
           else 
           {
              f.setBackground(list.getBackground());
              f.setForeground(list.getForeground());
           }
           
         if(value instanceof Salon)
             {
              f.setBackground(Color.green);   
             }
               else
               {
                f.setForeground(Color.blue); 
                //message Prive
                
                 }
           

             
         
        f.setEnabled(list.isEnabled());
         f.setFont(list.getFont());
        f.setOpaque(true);

            
            return f;
                
                
            }
        });
   
        
    
    }
    /*Cette methode prend une @Salon en argument et met tous les objets contenus dans le salon 
    dans la modèle liste 
    */
    
    public void mettreAjourLalisteSalonClient(Salon salon)
    {
        listmodel.addSalon(salon);
        salon.ajouterLesClientDanslaJlistSalonClient(listmodel);
    }
    /*ajout de la partie graphique du Salon*/
    
    public void ajouterIhmSalon()
    {
        onglets.addTab(ihmSalon.getName(),ihmSalon);
       
    }
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane4 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        jMenuItem1 = new javax.swing.JMenuItem();
        groupeBouton = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelClient = new javax.swing.JPanel();
        nomUtilisateur = new javax.swing.JLabel();
        etatC = new javax.swing.JComboBox();
        inciseNomClient = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane4 = new javax.swing.JSplitPane();
        onglets = new javax.swing.JTabbedPane();
        EntreeClavier = new javax.swing.JPanel();
        envoyerMessage = new javax.swing.JButton();
        scrollpaneSaisie = new javax.swing.JScrollPane();
        zoneDeSaisie = new javax.swing.JTextArea();
        boutonNormal = new javax.swing.JRadioButton();
        boutonItalique = new javax.swing.JRadioButton();
        boutonGras = new javax.swing.JRadioButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jlistSalonClient = new javax.swing.JList();
        jMenuBar1 = new javax.swing.JMenuBar();
        choixDeconnexion = new javax.swing.JMenu();
        deconnexion = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane4.setViewportView(jTextArea2);

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(130);
        jSplitPane1.setDividerSize(0);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setResizeWeight(1.0E-10);

        jSplitPane2.setDividerLocation(550);
        jSplitPane2.setDividerSize(0);
        jSplitPane2.setResizeWeight(0.01);

        jPanel5.setBackground(new java.awt.Color(240, 240, 188));
        jPanel5.setToolTipText("");

        jLabel2.setBackground(new java.awt.Color(240, 240, 188));
        jLabel2.setFont(new java.awt.Font("TlwgTypewriter", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(70, 209, 7));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/strilogo_opt(1).png"))); // NOI18N
        jLabel2.setText("CHAT STRI");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 520, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel5);

        panelClient.setBackground(new java.awt.Color(240, 240, 188));
        panelClient.setToolTipText("");

        nomUtilisateur.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        nomUtilisateur.setForeground(new java.awt.Color(33, 35, 230));
        nomUtilisateur.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/ico.gif"))); // NOI18N
        nomUtilisateur.setText("NomClient");

        etatC.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "En ligne", "Hors ligne", " " }));
        etatC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                etatCActionPerformed(evt);
            }
        });

        inciseNomClient.setFont(new java.awt.Font("URW Chancery L", 1, 24)); // NOI18N
        inciseNomClient.setIcon(new javax.swing.ImageIcon("/home/elhadj/NetBeansProjects/projet-update/Projet-Java-Etudiant/src/main/resources/fr/stri/tchat/cl.jpg")); // NOI18N
        inciseNomClient.setText("CL");
        inciseNomClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelClientLayout = new javax.swing.GroupLayout(panelClient);
        panelClient.setLayout(panelClientLayout);
        panelClientLayout.setHorizontalGroup(
            panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientLayout.createSequentialGroup()
                .addContainerGap(49, Short.MAX_VALUE)
                .addGroup(panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelClientLayout.createSequentialGroup()
                        .addComponent(inciseNomClient)
                        .addGap(15, 15, 15)
                        .addComponent(etatC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(nomUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        panelClientLayout.setVerticalGroup(
            panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientLayout.createSequentialGroup()
                .addComponent(nomUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(etatC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inciseNomClient))
                .addGap(49, 49, 49))
        );

        jSplitPane2.setRightComponent(panelClient);

        jSplitPane1.setTopComponent(jSplitPane2);

        jSplitPane3.setDividerLocation(600);
        jSplitPane3.setDividerSize(0);
        jSplitPane3.setResizeWeight(0.9);

        jSplitPane4.setDividerLocation(400);
        jSplitPane4.setDividerSize(1);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(0.9);

        onglets.setBackground(new java.awt.Color(240, 240, 188));
        onglets.setName("En ligne"); // NOI18N
        onglets.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                ongletsCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        onglets.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                ongletsKeyPressed(evt);
            }
        });
        jSplitPane4.setTopComponent(onglets);
        onglets.getAccessibleContext().setAccessibleName("Salon1");

        EntreeClavier.setBackground(new java.awt.Color(240, 240, 188));

        envoyerMessage.setText("Envoyer");
        envoyerMessage.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        envoyerMessage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                envoyerMessageActionPerformed(evt);
            }
        });

        scrollpaneSaisie.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollpaneSaisie.setFocusable(false);
        scrollpaneSaisie.setHorizontalScrollBar(null);

        zoneDeSaisie.setColumns(20);
        zoneDeSaisie.setRows(5);
        scrollpaneSaisie.setViewportView(zoneDeSaisie);

        groupeBouton.add(boutonNormal);
        boutonNormal.setText("Normal");
        boutonNormal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonNormalActionPerformed(evt);
            }
        });

        groupeBouton.add(boutonItalique);
        boutonItalique.setText("Italique");
        boutonItalique.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonItaliqueActionPerformed(evt);
            }
        });

        groupeBouton.add(boutonGras);
        boutonGras.setText("Gras");
        boutonGras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boutonGrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout EntreeClavierLayout = new javax.swing.GroupLayout(EntreeClavier);
        EntreeClavier.setLayout(EntreeClavierLayout);
        EntreeClavierLayout.setHorizontalGroup(
            EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntreeClavierLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(scrollpaneSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(envoyerMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(EntreeClavierLayout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addComponent(boutonNormal)
                .addGap(62, 62, 62)
                .addComponent(boutonItalique)
                .addGap(61, 61, 61)
                .addComponent(boutonGras))
        );
        EntreeClavierLayout.setVerticalGroup(
            EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntreeClavierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boutonNormal)
                    .addGroup(EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boutonGras)
                        .addComponent(boutonItalique)))
                .addGap(18, 18, 18)
                .addGroup(EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollpaneSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(EntreeClavierLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addComponent(envoyerMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
        );

        jSplitPane4.setRightComponent(EntreeClavier);

        jSplitPane3.setLeftComponent(jSplitPane4);

        jPanel4.setBackground(new java.awt.Color(240, 240, 188));

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(16, 204, 36));
        jLabel1.setText("Administrateur");

        jButton2.setText("Ajouter Salon");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Supprimer Salon");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("Ajouter Membre");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("Supprimer Membre");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jlistSalonClient.setBackground(new java.awt.Color(231, 229, 239));
        jlistSalonClient.setModel(listmodel);
        jScrollPane2.setViewportView(jlistSalonClient);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jSplitPane3.setRightComponent(jPanel4);

        jSplitPane1.setRightComponent(jSplitPane3);

        choixDeconnexion.setText("File");

        deconnexion.setText("Deconnexion");
        deconnexion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deconnexionActionPerformed(evt);
            }
        });
        choixDeconnexion.add(deconnexion);

        jMenuBar1.add(choixDeconnexion);

        jMenu1.setText("Aide");

        jMenuItem3.setText("A propos");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ongletsCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_ongletsCaretPositionChanged
       // TODO add your handling code here:
    }//GEN-LAST:event_ongletsCaretPositionChanged
   public void recupererContenuSalon(IhmSalon ihmSalon)
   {
       
        String idsalon = new String();
        String idmembre =new String();
        String login=new String();
        int indiceDuSalon=0;
        ArrayList<String>date=new ArrayList<>();
        ArrayList<String> lislogin=new ArrayList<>();
        ArrayList <String>listMessag=new ArrayList<>();
          String requeteIdSalon="Select id_salon from salon where nom_salon";
         requeteIdSalon+=" ="+"'"+ihmSalon.getNom()+"'";
         String requetemesSalon;
          
         requetemesSalon="select login from utilisateur,envoyermess"; 
         requetemesSalon+=" where id_salon=";
         requetemesSalon+="'"+idsalon+"'"+" and utilisateur.id_membre=envoyermess.id_membre";
         String requeteMessage;
          
          try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requeteIdSalon);
         
            while (resultat.next()) 
            {
                   
            idsalon=resultat.getString("id_salon");
             
         
          }
            
            
        } catch (SQLException e) {
              System.err.println("Erreur sur Id Salon");
        }
          
         requetemesSalon="select login,message_time from utilisateur,envoyermess"; 
         requetemesSalon+=" where id_salon=";
         requetemesSalon+="'"+idsalon+"'"+" and utilisateur.id_membre=envoyermess.id_membre";
      
          try
          {
          
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requetemesSalon);
           
         while (resultat.next()) 
            {
                   
            
                login=resultat.getString("login");      
                lislogin.add(login);
                date.add(resultat.getString("message_time").substring(0, 19));
             
         
          }
            
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
         requeteMessage="select message from envoyermess  where id_salon=";
         requeteMessage+="'"+idsalon+"'";
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requeteMessage);
         while (resultat.next()) 
            {
                   
                
               listMessag.add(resultat.getString("message")); 
              
             
         
          }
            
            
        } catch (Exception e) {
            System.out.println("Erreur sur les messages ");
        }
        
       int i=0;
        String messages = new String();
        for(String mes:listMessag)
        {
         mes=mes.replace("\"", "'");
         messages+=date.get(i);
         messages+="    ";
         messages+=lislogin.get(i);
         messages+="   ";
         messages+=mes;
         messages+="\n";
    
         
         i++;
                  
        }
       
      
         ihmSalon.afficher(messages,style);
         
        
       
   
     
       
   }
    private void envoyerMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envoyerMessageActionPerformed
        // TODO add your handling code here:
         
        
        boolean verif=false;
        String idsalon = new String();
        String idmembre =new String();
        String login=new String();
        int indiceDuSalon=0;
     
        ArrayList<String> lislogin=new ArrayList<>();
        ArrayList <String>listMessag=new ArrayList<>();
        ArrayList<String>date=new ArrayList<>();
        String texte=new String();
       texte=zoneDeSaisie.getText();
       zoneDeSaisie.setText("");
       indiceDuSalon=onglets.getSelectedIndex();
      
        
        String requeteIdSalon="Select id_salon from salon where nom_salon";
               requeteIdSalon+=" ="+"'"+tableIhm.get(indiceDuSalon).getName()+"'";
        String idcompte="select id_membre from utilisateur where login";
        idcompte+=" ='"+this.nomUtilisateur.getText()+"'";
        
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requeteIdSalon);
         
            while (resultat.next()) 
            {
                   
            idsalon=resultat.getString("id_salon"); 
         
          }
            
            
        } catch (Exception e) {
        }
         
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(idcompte);
         while (resultat.next()) 
            {
                   
            
             idmembre=resultat.getString("id_membre");
         
          }
            
            
            
        } catch (Exception e) {
        }
        
        String requeteRecupereEtatUTilisateur="select  online_status from enligne where   enligne.id_membre="+idmembre+" "
            + "and enligne.id_salon="+idsalon+"";
     
        
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteRecupereEtatUTilisateur);
            System.err.println("la requete est "+requeteRecupereEtatUTilisateur);
            while(resultat.next())
            {
                
                if(resultat.getString("online_status").compareTo("Hors-ligne")!=0)
              
                  verif=true;
              
              
            }
       
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(verif)
        {
          
            texte=texte.replace("'", "\"");
           
         String requeteInsertion="INSERT INTO envoyermess VALUES";
                 requeteInsertion+="("+""+idsalon+","+idmembre+","+"CURRENT_TIMESTAMP"+",'"+texte+"')";
                 System.out.println("la requete "+requeteInsertion);
      
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requeteInsertion);
            resultat.close();
            
        } catch (Exception e) {
            
        }
        }
        /*
       
       String requetemesSalon;
       requetemesSalon="select login,message_time from utilisateur,envoyermess"; 
       requetemesSalon+=" where id_salon=";
       requetemesSalon+="'"+idsalon+"'"+" and utilisateur.id_membre=envoyermess.id_membre";
        
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requetemesSalon);
           
         while (resultat.next()) 
            {
                   
            
                login=resultat.getString("login"); 
                
                lislogin.add(login);
             
         
          }
            
            
        } catch (Exception e) {
        }
        String requeteMessage="select message from envoyermess  where id_salon=";
        requeteMessage+="'"+idsalon+"'";
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requeteMessage);
         while (resultat.next()) 
            {
                   
                
               listMessag.add(resultat.getString("message")); 
             
         
          }
            
            
        } catch (Exception e) {
        }
         try
          {
          
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requetemesSalon);
           
         while (resultat.next()) 
            {
                   
            
                login=resultat.getString("login");      
                lislogin.add(login);
                date.add(resultat.getString("message_time").substring(0, 19));
             
         
          }
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
            
           int i=0;
        String messages = new String();
        for(String mes:listMessag)
        {
          mes=mes.replace("\"", "'");
         messages+=date.get(i);
         messages+="    ";
         messages+=lislogin.get(i);
         messages+="   ";
         messages+=mes;
         messages+="\n";
    
         
            i++;
                  
        }
        
         
        tableIhm.get(indiceDuSalon).afficher(messages,style);
        onglets.remove(indiceDuSalon);
        
       
        
        onglets.add(tableIhm.get(indiceDuSalon),indiceDuSalon);
        onglets.setSelectedIndex(indiceDuSalon);
        
       
        
        */
   
        
       
    }//GEN-LAST:event_envoyerMessageActionPerformed

    private void deconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deconnexionActionPerformed
        try {
            // TODO add your handling code here:
            connexion.close();
        } catch (SQLException ex) {
            Logger.getLogger(Ihm.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.dispose();
        timer1.stop();
        
        
 
    }//GEN-LAST:event_deconnexionActionPerformed

    private void boutonGrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonGrasActionPerformed
        // TODO add your handling code here:
               if(boutonGras.isSelected())
          {
              
              this.style="gras";
          }
          
    
    }//GEN-LAST:event_boutonGrasActionPerformed

    private void boutonItaliqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonItaliqueActionPerformed
        // TODO add your handling code here:
        if(boutonItalique.isSelected())
        {
            this.style="italique";
        }
    }//GEN-LAST:event_boutonItaliqueActionPerformed

    private void boutonNormalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boutonNormalActionPerformed
        // TODO add your handling code here:
        if(boutonNormal.isSelected())
        {
            this.style="normale";
        }
    }//GEN-LAST:event_boutonNormalActionPerformed

    private void ongletsKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ongletsKeyPressed
        // TODO add your handling code here:
     
    }//GEN-LAST:event_ongletsKeyPressed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        CreerSalons salons=new CreerSalons();
        salons.setLocationRelativeTo(null);
        salons.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void etatCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_etatCActionPerformed
        // TODO add your handling code here:
        int indice=0;
        String idmembre=new String();
        String idsalon=new String();
        String online_status=new String();
        String nomSalon=new String();
        String login=new String();
        String requete=new String ();
        
        if( etatC.getSelectedIndex()==0)
        {
           indice=onglets.getSelectedIndex();
           nomSalon=((IhmSalon) onglets.getComponentAt(indice)).getNom();
           login=nomUtilisateur.getText();
           requete="select id_membre,id_salon from utilisateur,salon";
           requete+=" where nom_salon="+"'";
           requete+=nomSalon+"'"+" and login="+"'"+login+"'";
           
        try
        {
          Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requete);
         while (resultat.next()) 
            {
                   
            
             idmembre=resultat.getString("id_membre");
             idsalon=resultat.getString("id_salon");
          }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
          String requeteRecupereEtatUTilisateur="select  online_status from enligne where   enligne.id_membre="+idmembre+" "
            + "and enligne.id_salon="+idsalon+"";
     
        
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteRecupereEtatUTilisateur);
            while(resultat.next())
            {
                
               online_status=resultat.getString("online_status");
              
               
              
              
            }
       
                   
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         String requeteUpdate="UPDATE enligne SET online_status = REPLACE(";
         requeteUpdate+="online_status"+","+"'"+online_status+"'"+",'En ligne')";
         requeteUpdate+="where enligne.id_salon="+"'"+idsalon+"'"+"and enligne.id_membre="+"'"+idmembre+"'";
            try {
                Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteUpdate);
            
                
            } catch (SQLException e) {
            }
             
            
           
        }
        else 
        {
              indice=onglets.getSelectedIndex();
           nomSalon=((IhmSalon) onglets.getComponentAt(indice)).getNom();
           login=nomUtilisateur.getText();
           requete="select id_membre,id_salon from utilisateur,salon";
           requete+=" where nom_salon="+"'";
           requete+=nomSalon+"'"+" and login="+"'"+login+"'";
           
        try
        {
          Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requete);
         while (resultat.next()) 
            {
                   
            
             idmembre=resultat.getString("id_membre");
             idsalon=resultat.getString("id_salon");
          }
            
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
          String requeteRecupereEtatUTilisateur="select  online_status from enligne where   enligne.id_membre="+idmembre+" "
            + "and enligne.id_salon="+idsalon+"";
     
        
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteRecupereEtatUTilisateur);
            while(resultat.next())
            {
                
               online_status=resultat.getString("online_status");
              
               
              
              
            }
       
                   
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         String requeteUpdate="UPDATE enligne SET online_status = REPLACE(";
         requeteUpdate+="online_status"+","+"'"+online_status+"'"+",'Hors-ligne')";
         requeteUpdate+="where enligne.id_salon="+"'"+idsalon+"'"+"and enligne.id_membre="+"'"+idmembre+"'";
            try {
                Statement instruction = connexion.createStatement();
                 instruction.executeUpdate(requeteUpdate);
            
                
            } catch (SQLException e) {
               e.printStackTrace();
            }
             
            
           
  
         
        }
        
    }//GEN-LAST:event_etatCActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        
        Supprimer_Salons supsalon=new Supprimer_Salons();
        supsalon.setLocationRelativeTo(null);
        supsalon.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
      AjouterMembre ajout=new AjouterMembre();
        ajout.setLocationRelativeTo(null);
        ajout.setVisible(true);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        indiceCourant--;
        RetirerMembres retirer=new RetirerMembres();
        retirer.setLocationRelativeTo(null);
        retirer.setVisible(true);
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

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
            java.util.logging.Logger.getLogger(Ihm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Ihm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Ihm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Ihm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
       
        /* ListSalonClient listsalonclient=new ListSalonClient();
         listsalonclient.ajouter(salon);
         */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
               //new Ihm().setVisible(true);
              // Ihm ihm = new Ihm("Client","Administrateur");
               //ihm.ajouterIhmSalon();
             
                //ihm.setVisible(true);
                    
                  
                   
                     
      
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EntreeClavier;
    private javax.swing.JRadioButton boutonGras;
    private javax.swing.JRadioButton boutonItalique;
    private javax.swing.JRadioButton boutonNormal;
    private javax.swing.JMenu choixDeconnexion;
    private javax.swing.JMenuItem deconnexion;
    private javax.swing.JButton envoyerMessage;
    private javax.swing.JComboBox etatC;
    private javax.swing.ButtonGroup groupeBouton;
    private javax.swing.JLabel inciseNomClient;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JSplitPane jSplitPane4;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JList jlistSalonClient;
    private javax.swing.JLabel nomUtilisateur;
    private javax.swing.JTabbedPane onglets;
    private javax.swing.JPanel panelClient;
    private javax.swing.JScrollPane scrollpaneSaisie;
    private javax.swing.JTextArea zoneDeSaisie;
    // End of variables declaration//GEN-END:variables

    
}
