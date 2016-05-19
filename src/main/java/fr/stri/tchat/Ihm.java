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
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
   
    /**
     * Creates new form Ihm
     */
    
    
    public Ihm(String nomUtilisateur,String nomAdmin) {
        
        listmodel = new ListSalonClient();
        tableIhm=new ArrayList<IhmSalon>();
      
         initComponents();
         gererSaisie();
         listSalonClientCellRenderer();
         this.nomUtilisateur.setText(nomUtilisateur);
         this.jLabel1.setText(nomAdmin);
         this.inciseNomClient.setText(nomUtilisateur.substring(0, 3));
        
         
          mettreAjour();
        javax.swing.Timer timer1 = new javax.swing.Timer(3000, new ActionListener() {

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
         try {
//      Class.forName("org.postgresql.Driver");
            System.out.println("Driver O.K.");

            String url = "jdbc:postgresql://localhost:5433/javaSTRI";
            String user = "postgres";
            String password1="diallo";

           this.connexion = DriverManager.getConnection(url, user, password1);
            System.out.println("Connexion effective !");
          
         
     
    } catch (Exception e) {
             System.err.println("Erreur de connexion");
        }
       
     
         
     }
   
    private void vider()
    {
        if(onglets.getTabCount()!=0)
        {
            onglets.removeAll();
        }
           
    }
   
    
     private void maj() {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
    public void mettreAjour()
    {
        /* Requête Utilisateur d'un salon  
 */
        /*requête pour recuper un salon */
      /*  ;*/
        
         
        
         
        
      
        
        
      
               
        /*
          
        Timer timer =new Timer();       
        TimerTask Task=new TimerTask() {

            @Override
            public void run() {
          */ 
        int indiceDuClient;
        String online=new String();
        HashMap<Integer,FormeClientBd> tabclient= new HashMap<Integer,FormeClientBd>();
        ArrayList<String>temp=new ArrayList<>();
        tablesalon=new TableSalon();
    int    indiceCourant=onglets.getSelectedIndex();
    connexionAlabase();
               try {
           
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery("Select nom_salon from salon");
            
          while (resultat.next()) {
              
              
              temp.add(resultat.getString("nom_salon"));
            
              
          }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
            
            int i=0;  
         try {
             for(String lesalon:temp)
             {
             
             String requetestatus="select  online_status,utilisateur.id_membre,login from enligne ,salon";
             requetestatus+=",utilisateur where enligne.id_membre=utilisateur.id_membre and ";
             requetestatus+="salon.nom_salon="+"'"+lesalon+"' and salon.id_salon=enligne.id_salon";
             
              System.out.println("la requete est "+requetestatus);
             ClientEtat enligne=new ClientEtat("default", new ImageIcon(getClass().getResource("/fr/stri/tchat/ico.gif")));
             ClientEtat horsligne=new ClientEtat("default", new ImageIcon(getClass().getResource("/fr/stri/tchat/pasconnecte.gif")));
            
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requetestatus);
            
            while (resultat.next())
            {
                
                
              indiceDuClient=Integer.parseInt(resultat.getString("id_membre"));
  
               online=resultat.getString("online_status");
               
              
                
                if(online.compareTo("Hors-ligne")==0)
                {
                   
                  
                FormeClientBd utiliseClient=new FormeClientBd(resultat.getString("login"), indiceDuClient, horsligne, lesalon);    
                tabclient.put(i,utiliseClient);
                    
                }
                if(online.compareTo("En ligne")==0)
                {
                     
                 FormeClientBd utiliseClient=new FormeClientBd(resultat.getString("login"), indiceDuClient,enligne, lesalon);
                  
                  tabclient.put(i, utiliseClient);
                          
                }
                
                i++;
                
            }
             
             }    
             
        } catch (Exception e) {
             e.printStackTrace();
        }
         
                System.err.println("le nombre d'element est "+tabclient.size()); 
     
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
                /* if(tabclient.get(cle).retournerSalon().compareTo(lessalon)==0)
                 {
                     System.out.println("bien vu");
                     salon.ajouterClientDansleSalon(tabclient.get(cle).retournerClient());
                 }*/
                 list=salon.findClient(tabclient, lessalon);
                 if(list.isEmpty()==false)
                 {
                     for(Client cl:list)
                     {
                         System.out.println("les clients sont "+cl.getNomclient());
                         salon.ajouterClientDansleSalon(cl);
                     }
                    
                 }
               
               
                    
              }
            tablesalon.ajouterUnSalon(salon);
           
           
            }
             
        } catch (Exception e) {
            e.printStackTrace();
        }
        
         
                
                
                
                
           listmodel.viderLaliste();
           vider();
          for(Salon salon2:tablesalon.getTableSalon())   
           {
            IhmSalon salonAjouter=new IhmSalon(salon2);
            recupererContenuSalon(salonAjouter);
           tableIhm.add(salonAjouter);
           onglets.addTab(salonAjouter.getName(), salonAjouter);
           listmodel.addSalon(salon2);
           salon2.ajouterLesClientDanslaJlistSalonClient(listmodel);
                               
            }
             onglets.setSelectedIndex(indiceCourant);
             
          
            /*   
           
      
            }
        };
        timer.scheduleAtFixedRate(Task,0, 30*1000);*/
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
                   f.setBackground(Color.CYAN);
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        panelClient = new javax.swing.JPanel();
        nomUtilisateur = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        inciseNomClient = new javax.swing.JLabel();
        jSplitPane3 = new javax.swing.JSplitPane();
        jSplitPane4 = new javax.swing.JSplitPane();
        onglets = new javax.swing.JTabbedPane();
        EntreeClavier = new javax.swing.JPanel();
        envoyerMessage = new javax.swing.JButton();
        scrollpaneSaisie = new javax.swing.JScrollPane();
        zoneDeSaisie = new javax.swing.JTextArea();
        jRadioButton4 = new javax.swing.JRadioButton();
        jRadioButton5 = new javax.swing.JRadioButton();
        jRadioButton6 = new javax.swing.JRadioButton();
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

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setDividerSize(0);
        jSplitPane2.setResizeWeight(0.01);

        jPanel5.setToolTipText("");

        jLabel2.setFont(new java.awt.Font("TlwgTypewriter", 2, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(54, 36, 209));
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/strilogo_opt(1).png"))); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jSplitPane2.setLeftComponent(jPanel5);

        nomUtilisateur.setFont(new java.awt.Font("Ubuntu", 0, 24)); // NOI18N
        nomUtilisateur.setForeground(new java.awt.Color(33, 35, 230));
        nomUtilisateur.setText("NomClient");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "En ligne", "Hors ligne", " " }));

        inciseNomClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/fr/stri/tchat/cl.jpg"))); // NOI18N
        inciseNomClient.setText("CL");
        inciseNomClient.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelClientLayout = new javax.swing.GroupLayout(panelClient);
        panelClient.setLayout(panelClientLayout);
        panelClientLayout.setHorizontalGroup(
            panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelClientLayout.createSequentialGroup()
                .addContainerGap(221, Short.MAX_VALUE)
                .addComponent(nomUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(inciseNomClient)
                .addGap(2, 2, 2)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        panelClientLayout.setVerticalGroup(
            panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelClientLayout.createSequentialGroup()
                .addContainerGap(37, Short.MAX_VALUE)
                .addGroup(panelClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(inciseNomClient)
                    .addComponent(nomUtilisateur, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43))
        );

        jSplitPane2.setRightComponent(panelClient);

        jSplitPane1.setTopComponent(jSplitPane2);

        jSplitPane3.setDividerLocation(600);
        jSplitPane3.setDividerSize(0);
        jSplitPane3.setResizeWeight(0.9);

        jSplitPane4.setDividerLocation(400);
        jSplitPane4.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane4.setResizeWeight(0.9);

        onglets.setName("En ligne"); // NOI18N
        onglets.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                ongletsCaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
        });
        jSplitPane4.setTopComponent(onglets);
        onglets.getAccessibleContext().setAccessibleName("Salon1");

        envoyerMessage.setText("Envoyer");
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

        buttonGroup2.add(jRadioButton4);
        jRadioButton4.setText("Normal");

        buttonGroup2.add(jRadioButton5);
        jRadioButton5.setText("Italique");

        buttonGroup2.add(jRadioButton6);
        jRadioButton6.setText("Gras");

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
                .addComponent(jRadioButton4)
                .addGap(62, 62, 62)
                .addComponent(jRadioButton5)
                .addGap(61, 61, 61)
                .addComponent(jRadioButton6))
        );
        EntreeClavierLayout.setVerticalGroup(
            EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EntreeClavierLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton4)
                    .addGroup(EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jRadioButton6)
                        .addComponent(jRadioButton5)))
                .addGroup(EntreeClavierLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EntreeClavierLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(scrollpaneSaisie, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EntreeClavierLayout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(envoyerMessage)))
                .addContainerGap(170, Short.MAX_VALUE))
        );

        jSplitPane4.setRightComponent(EntreeClavier);

        jSplitPane3.setLeftComponent(jSplitPane4);

        jLabel1.setText("Administrateur");

        jButton2.setText("Ajouter Salon");

        jButton3.setText("Supprimer Salon");

        jButton4.setText("Ajouter Membre");

        jButton5.setText("Supprimer Membre");

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
                .addContainerGap(75, Short.MAX_VALUE))
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
            .addComponent(jSplitPane1, javax.swing.GroupLayout.Alignment.TRAILING)
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
            
            
        } catch (Exception e) {
              System.err.println("Erreur sur Id Salon");
        }
          
         requetemesSalon="select login from utilisateur,envoyermess"; 
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
             
         
          }
            
            
        } catch (Exception e) {
            System.out.println("Erreur sur les logins");
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
         messages+=lislogin.get(i);
         messages+="   ";
         messages+=mes;
         messages+="\n";
         i++;
                  
        }
        ihmSalon.afficher(messages);
          
        
       
   
     
       
   }
    private void envoyerMessageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_envoyerMessageActionPerformed
        // TODO add your handling code here:
         
        
        boolean verif=false;
        String idsalon = new String();
        String idmembre =new String();
        String login=new String();
        int indiceDuSalon=0;
        int nbligne = 0;
        ArrayList<String> lislogin=new ArrayList<>();
        ArrayList <String>listMessag=new ArrayList<>();
        
        String texte=new String();
       texte=zoneDeSaisie.getText();
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
        System.err.println("la requete "+requeteRecupereEtatUTilisateur);
        
        try {
            Statement instruction = connexion.createStatement();
            ResultSet resultat = instruction.executeQuery(requeteRecupereEtatUTilisateur);
            while(resultat.next())
            {
                
                System.out.println(resultat.getString("online_status")); 
              
                  verif=true;
              
              
            }
       
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(verif)
        {
         String requeteInsertion="INSERT INTO envoyermess VALUES";
                 requeteInsertion+="("+""+idsalon+","+idmembre+","+"CURRENT_TIMESTAMP"+",'"+texte+"')";
                 
      
        try {
            
         Statement instruction = connexion.createStatement();
         ResultSet resultat = instruction.executeQuery(requeteInsertion);
            resultat.close();
            
        } catch (Exception e) {
            
        }
        }
       
       String requetemesSalon;
       requetemesSalon="select login from utilisateur,envoyermess"; 
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
        
            
        /*requête nombre de ligne   select Count(*) from salon;*/
        /* type de requête INSERT INTO envoyermess VALUES (numeroligne, idsalon,idcompte,'heure','message');*/
        /*select id_membre from utilisateur 
           where login='DIALLO';
           Select id_salon from salon 
            where nom_salon='SALON1';*/
        /*select message from envoyermess 
                      where id_salon='1';*/
        /*select login from utilisateur,envoyermess 
         where id_salon='1' and utilisateur.id_membre=envoyermess.id_membre;*/
        int i=0;
        String messages = new String();
        for(String mes:listMessag)
        {
         messages+=lislogin.get(i);
         messages+="   ";
          messages+=mes;
          messages+="\n";
          i++;
                  
        }
        tableIhm.get(indiceDuSalon).afficher(messages);
        onglets.remove(indiceDuSalon);
        
       // IhmSalon componentAt = (IhmSalon) onglets.getComponentAt(0);
       // componentAt.listContenuSalonIhmCellrendere();
        
        onglets.add(tableIhm.get(indiceDuSalon),indiceDuSalon);
        onglets.setSelectedIndex(indiceDuSalon);
        
        //mettreAjour();
        
        
   
        
       
    }//GEN-LAST:event_envoyerMessageActionPerformed

    private void deconnexionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deconnexionActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_deconnexionActionPerformed

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
              //  new Ihm().setVisible(true);
             //  Ihm ihm = new Ihm("Client","Administrateur");
            //   ihm.ajouterIhmSalon();
             
               // ihm.setVisible(true);
                    
                  
                   
                     
      
            }
        });
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EntreeClavier;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JMenu choixDeconnexion;
    private javax.swing.JMenuItem deconnexion;
    private javax.swing.JButton envoyerMessage;
    private javax.swing.JLabel inciseNomClient;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JRadioButton jRadioButton5;
    private javax.swing.JRadioButton jRadioButton6;
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
