import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*; 

import javax.imageio.ImageIO;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;


public class Accueil extends JFrame implements ActionListener{
	//Création de notre fenêtre et de nos boutons
		private JFrame fenetre = new JFrame();        
		private final int HAUTEUR_FENETRE_ACCUEIL=720;                       
		private final int LARGEUR_FENETRE_ACCUEIL=1024; 
		//private JMenuBar menuBar = new JMenuBar();
		private JMenu menu = new JMenu("Options");
		private JMenuItem menuItem = new JMenuItem("A propos");
		private JMenuItem menuItem2 = new JMenuItem("Purger (fichier txt)");
		private JMenuItem menuItem3 = new JMenuItem("Garder 5 scores (fichier txt)");
		private JMenuItem menuItem4 = new JMenuItem("Quitter");

		private JButton bouton1 = new JButton("Can I play, daddy ?");
		private JButton bouton2 = new JButton("Don't hurt me !");
		private JButton bouton3 = new JButton("Bring 'em on !");
		private JButton boutonRegles = new JButton("R\u00e8gles du jeu");
	    //Création de notre case à cocher, on précise qu'elle est cochée par défaut
	    private JCheckBox son = new JCheckBox("Activer le son",false);
	    private JCheckBox bruitages = new JCheckBox("Activer les bruitages",false);
	    private JCheckBox monde1 = new JCheckBox("Choisir le monde 1 ?",true);
	    private JCheckBox monde2 = new JCheckBox("Choisir le monde 2 ?",false);
	    private JCheckBox monde3 = new JCheckBox("Choisir le monde 3 ?",false);

	    private boolean selectMonde1,selectMonde2,selectMonde3;
	    //Création de note Jpanel auquel nous greffrons les élèments
		private FondAcceuil panel = new FondAcceuil();
		private MenuAccueil menuBar= new MenuAccueil();
	    //Déclarations des différentes variables et champ à remplir pour le joueur
	    private JTextField champPseudo = new JTextField ("Pseudo");    //On crée un champ pré-remplit avec un pseudo
	    private JLabel texte;
	    private Font police;
	    private ImageIcon icone=new ImageIcon("./img/life.png");      //Définit l'icone
	    private String pseudo;
	    private String pseudoFichier[];
	    private int scoreFichier[];
	    private int i,v,d,countAffichageScore,numMonde;
	    private AudioClip sonAccueil;
	    private boolean problemeLecture=false;
		
		public Accueil (){
			selectMonde1=monde1.isSelected();
			selectMonde2=monde2.isSelected();
			selectMonde3=monde3.isSelected();
			
			/*Toolkit T=Toolkit.getDefaultToolkit();	
			Image image1 = T.getImage("./img/cursorAccueil.gif");
			Cursor c1 = T.createCustomCursor(image1, new Point(5,5), "img");*/
			fenetre.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

			pseudo="";                                      //On initialise le string
	        //Définition de notre fenêtre
			fenetre.setTitle("Accueil - Javashoot");               //Titre
			fenetre.setSize(LARGEUR_FENETRE_ACCUEIL,HAUTEUR_FENETRE_ACCUEIL);                               //Taille
			fenetre.setResizable(false);                            //Interdit le redimensionnement
			fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Ferme le programme avec la fermeture de la fenetre
			icone=new ImageIcon("./img/life.png");
			fenetre.setIconImage(icone.getImage());        //Indique l'icone à utiliser pour la fenetre
			try {
      			//InputStream is = DemoFonts.class.getResourceAsStream("./COMPUTER.TTF");
      			police = Font.createFont(Font.TRUETYPE_FONT, new File("./img/Diablo.TTF"));
      			police = police.deriveFont(14f);
    		} catch (Exception ex) {
      			ex.printStackTrace();
      			System.err.println("impossinle de charger la police");
      			police=new Font("Times New Roman", Font.BOLD, 20); 
    		}
			
    		menu.setBackground(Color.BLACK);
    		menu.setFont(police);
    		menu.setForeground(Color.red); 
    		menuItem.setFont(police);
    		menuItem2.setFont(police);
    		menuItem3.setFont(police);
    		menuItem4.setFont(police);
    		menu.setFont(police);
    		police = police.deriveFont(16f);
			menu.add(menuItem);
			menu.add(menuItem2);
			menu.add(menuItem3);
			menu.addSeparator();
			menu.add(menuItem4);
			
    		menuBar.setBackground(Color.BLACK);
			menuBar.add(menu);


			fenetre.setJMenuBar(menuBar);

	        //Définition de notre Layout pour positionner nos élèments
			panel.setLayout(null);                      //On indique le layout a utiliser par dessus notre fond
	        panel.setBounds(0,0,LARGEUR_FENETRE_ACCUEIL,HAUTEUR_FENETRE_ACCUEIL);
			//On définit les differents composants de la fenêtre
	        //Coordonnées du texte dans la grille du layout 
	        texte=new JLabel("Saissisez un");
	        texte.setFont(police);                     //On applique les caractéristiques précedement définies sur notre texte
	        texte.setBounds(60,25,150,20);             //On centre notre texte au sein de sa cellule 
	        texte.setForeground(Color.white);   
	        panel.add(texte);                          //Ajout de notre texte positionée et définit sur notre Layout

	        texte=new JLabel("pseudonyme");
	        texte.setFont(police);                     //On applique les caractéristiques précedement définies sur notre texte
	        texte.setBounds(60,50,150,20);             //On centre notre texte au sein de sa cellule 
	        texte.setForeground(Color.white);   
	        panel.add(texte);                          //Ajout de notre texte positionée et définit sur notre Layout

	        //Ajout du JTextField champPseudo pour permettre la saisie du String pseudo
	        champPseudo.setBounds(225,30,175,40); 
	        panel.add(champPseudo);


	        texte=new JLabel("Choisissez votre");
	        texte.setFont(police); 
	        texte.setForeground(Color.white);                    
	        texte.setBounds(450,25,300,20);             
	        panel.add(texte);                          

	        texte=new JLabel(" monde -----\u2265");
	        texte.setFont(police);    
	        texte.setForeground(Color.white);                 
	        texte.setBounds(475,50,300,20);             
	        panel.add(texte);   
	        
	        texte=new JLabel("Niveau d\u00e9sir\u00e9");
	        texte.setFont(police);
	        texte.setForeground(Color.red);   
	        texte.setBounds(465,160,150,20);             //On centre notre texte au sein de sa cellule                                    //Définit le nombre de cellule à fusionner pour former la cellule contenant notre objet
	        panel.add(texte);  
			
	        //Ajout des boutons
			bouton1.setBackground(new Color(137,230,139));    //Couleur de fond de notre bouton
			bouton1.setForeground(Color.white);               //Couleur de notre bouton lorsqu'on appuie sur celui-ci
			bouton1.setBounds(435,200,200,50); 
			panel.add(bouton1);
			
			bouton2.setBackground(new Color(226,156,23));
			bouton2.setForeground(Color.white);
			bouton2.setBounds(435,275,200,50); 
			panel.add(bouton2);
			
			bouton3.setBackground(new Color(239,31,0));
			bouton3.setForeground(Color.white);
			bouton3.setBounds(435,350,200,50); 
			panel.add(bouton3);

			texte=new JLabel("Options");
	        texte.setFont(police);
	        texte.setForeground(Color.red);   
	        texte.setBounds(500,475,200,50);             //On centre notre texte au sein de sa cellule                                    //Définit le nombre de cellule à fusionner pour former la cellule contenant notre objet
	        panel.add(texte);  
	        //Ajout de la case à cocher pour le son
	        son.setBounds(450,510,200,25); 
	        son.setOpaque(false);
	        son.setForeground(Color.white);
	        panel.add(son);

	        bruitages.setBounds(450,535,200,25); 
	        bruitages.setOpaque(false);
	        bruitages.setForeground(Color.white);
	        panel.add(bruitages);

	        monde1.setBounds(760,180,200,50); 
	        monde1.setOpaque(false);
	        monde1.setForeground(Color.white);
	        panel.add(monde1);

	        monde2.setBounds(760,400,200,50); 
	        monde2.setOpaque(false);
	        monde2.setForeground(Color.white);
	        panel.add(monde2);

	        monde3.setBounds(760,630,200,50); 
	        monde3.setOpaque(false);
	        monde3.setForeground(Color.white);
	        panel.add(monde3);
			
			boutonRegles.setBackground(new Color (188,238,243));
			boutonRegles.setForeground(Color.black);
	        boutonRegles.setFont(police);
			boutonRegles.setBounds(435,600,200,50); 
			panel.add(boutonRegles);
			
			lectureFichier(); //On lit le fichier des scores
			if (!problemeLecture) {
				triBulle();       //On tri les donnèes obtenues pour les avoir dans l'ordre à l'aide du tri à bulle
	        	countAffichageScore=0;
	        	//Affichage des meilleurs scores et des pseudos enregistrées
	        	if (pseudoFichier.length>0) {   //On s'assure qu'il y ait bien au moins un score enregistré
	            	//Pseudo du meilleur joueur
	            	police=police.deriveFont(24f);
	            	for (int w=pseudoFichier.length-1;(w>=0)&&(w>pseudoFichier.length-6);w--) {
	                	texte=new JLabel(""+pseudoFichier[w]);
	                	texte.setFont(police);
	                	texte.setForeground(Color.red);
	                	texte.setBounds(60,260+countAffichageScore*90,140,20); 
	                	texte.setHorizontalAlignment(JLabel.LEFT);
	                	panel.add(texte);
	                	//Score du meilleur joueur
	                	texte=new JLabel(""+scoreFichier[w]);
	                	texte.setFont(police);
	                	texte.setForeground(Color.red);
	                	texte.setBounds(225,260+countAffichageScore*90,150,20); 
	                	texte.setHorizontalAlignment(JLabel.RIGHT);
	                	panel.add(texte);
	                	countAffichageScore++;
	            	}
	        	}
	        	if (scoreFichier.length>15) {
	        		viderFichier(false);
	        	}
			}
	        //On relie l'actionListener à nos boutons pour qu'il les écoute
			bouton1.addActionListener(this);
			bouton2.addActionListener(this);
			bouton3.addActionListener(this);
			boutonRegles.addActionListener(this);
			menuItem.addActionListener(this);
			menuItem2.addActionListener(this);
			menuItem3.addActionListener(this);
			menuItem4.addActionListener(this);
			son.addActionListener(this);
			monde1.addActionListener(this);
			monde2.addActionListener(this);
			monde3.addActionListener(this);

	        //On greffe nos élèments correctement placés sur notre JPanel contenant notre fond 
	        //puis on greffe ce JPanel sur notre JFrame
			fenetre.getContentPane().add(panel);

	        //On rend visible notre fenêtre entièrement finie
	        fenetre.setLocationRelativeTo(null);
			fenetre.setVisible(true);
			
			////////////////////////////////////////////////// TEST Lecture
			try {
                    // creation d'un audioclip
                    sonAccueil=Applet.newAudioClip(new URL("file:./img/keihou.wav"));
                } catch (Exception e) {
      				System.out.println(e);
    		}
		}
		//Méthode de suppression des scores du fichier
		private void viderFichier(boolean purge){

			
			try{
				FileWriter fw = new FileWriter(new File("Score.txt"));
				if (!purge) {
					//On définit le buffer pour écrire
					BufferedWriter bufferEcrire = new BufferedWriter (fw);
					//On positionne le curseur sur une nouvelle ligne
					bufferEcrire.newLine();
					//On se positionne au début de la ligne vierge
					PrintWriter ecrire = new PrintWriter(bufferEcrire);
					int tailleMax=Math.min(scoreFichier.length,pseudoFichier.length);
					//Pas plus de cinq écriture
					for (int x=0;x<tailleMax||x<5;x++) {
						//On écrit puis on saute une ligne
						ecrire.println(""+pseudoFichier[x]);
						//On écrit en laissant le curseur en fin de ligne
						ecrire.println(""+scoreFichier[x]);
					}
					//On ferme le fichier en écriture
					ecrire.close();
				} 
    		}
    		//Si on échoue durant l'écriture, on stoppe le jeu avec une erreur spécifique
			catch(IOException el) 
			{
            	System.out.println("Impossible d'écrire le score !");            
            	System.exit(-2);    
            }
		}
	    //Méthode permettant de lire le fichier des scores
		private void lectureFichier(){
	        //Tentative de lecture du fichier
			try
			{	
	    		File fichier=new File("Score.txt");                   //On définit le fichier que nous allons lire
	    		FileReader lecteur=new FileReader(fichier);           //On rend exploitable le fichier pour qu'il puisse être lu par le buffer
	    		BufferedReader bufferLire=new BufferedReader(lecteur);//On définit le buffer permettant de lire le fichier
	 			i=0;                                                  //Variable interne des boucles while permettant d'alterner l'écritures des scores et des pseudos
	 			v=0;                                                  //Variable définisant une position commune pour le joueur et son score dans les 2 tableaux
	            d=0;                                                  //Variable définisant la longueur des tableaux à creer et qui correspond au nombre de joueurs enregistrés
	            try
	            {
	                String ligne=bufferLire.readLine();               //On lit la première ligne du fichier
	                while (ligne!=null)                               //On continue la lecture tant que celle ci ne rencontre pas une ligne vide correspondant à la fin du fichier texte 
	                {
	                    ligne=bufferLire.readLine();                  //On lit la ligne suivante du fichier
	                    if (i%2!=0) {                                 //A chaque rencontre avec la ligne contenant le score, on incrémente d
	                        d++;                                      //On augmente de un le nombre de joueur présent dans le fichier
	                    }
	                    i++;
	                }
	                //On termine la lecture en fermant le fichier
	                bufferLire.close();
	                lecteur.close();
	            }
	            //Si on ne peut pas lire, on arrete le programme
	            catch (IOException exception)
	            {
	                System.out.println("impossible de lister les scores !");
	            }
	            //Définition de nos tableaux
	            scoreFichier=new int[d];
	            pseudoFichier=new String[d];
	            i=0;    //Réinitialisation de i pour la lecture suivante
	            //On recommence une lecture du fichier depuis le début
	            lecteur=new FileReader(fichier);
	            bufferLire=new BufferedReader(lecteur);
	    		try
	    		{
	        		String ligne=bufferLire.readLine();
	        		while (pseudoFichier.length>(i/2))                //On lit jusqu'a ce qu'on ait lu entièrement le fichier et donc remplit les tableaux
	        		{
	            		ligne=bufferLire.readLine();
	            		if (i%2==0) {                                 //Si i est paire, on est sur une ligne contenant un pseudo
	            			pseudoFichier[v]=ligne;                   //On copie le String dans notre tableau
	            		}else{                                        //Si i est impaire, on est sur une ligne contenant un score
	            			scoreFichier[v]=Integer.parseInt(ligne);  //On copie le score convertie en entier dans notre tableau
	            			v++;                                      //On passe au joueur suivant
	            		}
	            		i++;
	        		}
	        		bufferLire.close();
	        		lecteur.close();
	    		}
	            //Si on ne peut pas lire, on arrete le programme
	    		catch (IOException exception)
	    		{
	        		System.out.println("Impossible de lire le score !");
	    		}
			}
	        //Si on ne peut pas trouver le fichier, on arrete le programme
			catch (FileNotFoundException exception)
			{
	    		System.out.println ("Le fichier n'a pas été trouvé ");
	    		FileWriter fw = new FileWriter(new File("Score.txt"));
	    		System.out.println ("Le fichier a été crée");
	    		problemeLecture=true;
			}
			finally{
				return;
			}
		}
	    //Méthode pour trier en ordre croissant les pseudos et les scores dans leurs tableaux respectifs
		private void triBulle(){
	        int contenuInt=0;
	        String contenuString="";
	        int nbElementsRestant=scoreFichier.length-1;            //On place le nombre d'éléments à trier dans une variable
	        while (nbElementsRestant>0) {                           //Tant qu'il reste des éléments à trier, on continue
	            for(int j=0 ; j<nbElementsRestant ; j++){           //On parcourt la partie non triée du tableau en entier
	                if(scoreFichier[j]>scoreFichier[j+1]){          //Si l'élement suivant est inférieur alors on échange notre élément avec le suivant
	                    contenuInt=scoreFichier[j];                 //On stocke la valeur de la case actuelle dans une variable tampon
	                    scoreFichier[j]=scoreFichier[j+1];          //On copie la valeur suivante dans le tableau dans la case actuelle
	                    scoreFichier[j+1]=contenuInt;               //On remplace la valeur contenue dans la case suivante par celle anciennement contenue dans la case actuelle
	                    //On effectue l'opération réciproque sur le tableau des pseudos afin de conserver le lien entre le pseudo et le score qui ui est associé
	                    contenuString=pseudoFichier[j];
	                    pseudoFichier[j]=pseudoFichier[j+1];
	                    pseudoFichier[j+1]=contenuString;
	                }
	            }
	            nbElementsRestant--;                                //On a trié un élément donc on décremente ce qui permet de ne pas vérifier les éléménts déja triés
	        }
		}
	public void actionPerformed (ActionEvent e){
		if (son.isSelected()) {
			// lecture en boucle de l'audiocgetAudioClip
            sonAccueil.loop();
		}else{
			sonAccueil.stop();
		}
		if (selectMonde1) {
			if (monde2.isSelected()) {
				monde1.setSelected(false);
				monde3.setSelected(false);
			}else if (monde3.isSelected()) {
				monde1.setSelected(false);
				monde2.setSelected(false);
			}
		}else if (selectMonde2) {
			if (monde1.isSelected()) {
				monde2.setSelected(false);
				monde3.setSelected(false);
			}else if (monde3.isSelected()) {
				monde1.setSelected(false);
				monde2.setSelected(false);
			}
		}else if (selectMonde3) {
			if (monde1.isSelected()) {
				monde2.setSelected(false);
				monde3.setSelected(false);
			}else if (monde2.isSelected()) {
				monde1.setSelected(false);
				monde3.setSelected(false);
			}
		}
		
		selectMonde1=monde1.isSelected();
		selectMonde2=monde2.isSelected();
		selectMonde3=monde3.isSelected();

		if (selectMonde1) {
			numMonde=1;
		}else if (selectMonde2) {
			numMonde=2;
		}else if (selectMonde3) {
			numMonde=3;
		}

        //Si le bouton appuié est celui de l'aide
		if (e.getSource()==boutonRegles) {
		 	new JOptionPane();
			Object[] texte={                                   //On définit le texte à afficher
				"R\u00e8gles du jeu",
				"Le but du jeu est de d\u00e9truire le plus d'ennemis.",
				"Pour tirer, utilise soit la barre espace soit le clique gauche.",
				"Utilise les fl\u00e8ches directionelles pour bouger.",
				"Lorsque tu sors la souris de la fen\u00eatre, le jeu se met en pause.",
			};
            //On affiche la pop-up et on lui donne un titre et un type
			JOptionPane.showMessageDialog(null,
            texte, "Regles du Jeu",
            JOptionPane.INFORMATION_MESSAGE,new ImageIcon("./img/life.png")); 
         //////////TEST//////////////
         /*
        	JLabel iconLabel = new JLabel(new ImageIcon("./img/life.png"));
        	JPanel iconPanel = new JPanel(new GridBagLayout());
        	iconPanel.add(iconLabel);
        	JPanel textPanel= new JPanel(new GridLayout(0, 1));
        	textPanel.add(new JLabel("                               R\u00e8gles du jeu"));
			textPanel.add(new JLabel("     Le but du jeu est de d\u00e9truire le plus d'ennemis."));
			textPanel.add(new JLabel("     Pour tirer, utilise soit la barre espace soit le clique gauche."));
			textPanel.add(new JLabel("     Utilise les fl\u00e8ches directionelles pour bouger."));
			textPanel.add(new JLabel("     Lorsque tu sors la souris de la fen\u00eatre, le jeu se met en pause."));
        	JPanel mainPanel = new JPanel(new BorderLayout());
        	mainPanel.add(textPanel);
        	mainPanel.add(iconPanel, BorderLayout.WEST);
        	JOptionPane.showMessageDialog(null, mainPanel, "R\u00e8gle du jeu", JOptionPane.PLAIN_MESSAGE);
		 */
		 }else if (e.getSource()==bouton1) {
		 	sonAccueil.stop();
		 	pseudo=champPseudo.getText();
            pseudo=enleverNonAlphanumerique(pseudo);
		 	//fenetre.setVisible(false);
		 	
		 	try {
		 			if (bruitages.isSelected()) {
		 				// creation d'un audioclip
                    	sonAccueil=Applet.newAudioClip(new URL("file:./img/daidageki-kai2.wav"));
                    	// lecture en boucle de l'audiocgetAudioClip
                    	sonAccueil.play();
                    	Thread.sleep(2000);
		 			}
                    new Shoot(pseudo,son.isSelected(),bruitages.isSelected(),1,numMonde);
			 		fenetre.dispose();
                } catch (Exception e1) {
      				System.out.println(e1);
    		}
		 }else if (e.getSource()==bouton2) {
		 	sonAccueil.stop();
			pseudo=champPseudo.getText();
	        pseudo=enleverNonAlphanumerique(pseudo);

			try {
				if (bruitages.isSelected()) {
					// creation d'un audioclip
                    sonAccueil=Applet.newAudioClip(new URL("file:./img/doon3.wav"));
                  	// lecture en boucle de l'audiocgetAudioClip
                  	sonAccueil.play();
                  	Thread.sleep(2000);
				}
                  	new Shoot(pseudo,son.isSelected(),bruitages.isSelected(),2,numMonde);
			 		fenetre.dispose();
            } catch (Exception e2) {
      			System.out.println(e2);
    		}
		 }else if (e.getSource()==bouton3) {
		 		sonAccueil.stop();
			 	pseudo=champPseudo.getText();
	            pseudo=enleverNonAlphanumerique(pseudo);
			 	try {
			 		if (bruitages.isSelected()) {
			 			// creation d'un audioclip
                    	sonAccueil=Applet.newAudioClip(new URL("file:./img/SE194.wav"));
                    	// lecture en boucle de l'audiocgetAudioClip
                    	sonAccueil.play();
                  		Thread.sleep(2000);
			 		}
                    new Shoot(pseudo,son.isSelected(),bruitages.isSelected(),3,numMonde);
			 		fenetre.dispose();
                } catch (Exception e3) {
      				System.out.println(e3);
    			}
		 }else if (e.getSource()==menuItem) {
			 	new JOptionPane();
				Object[] texte={                                   //On définit le texte à afficher
					"A propos",
					"Ce jeu ?????",
					"Parce que le pastafarisme et raptor jesus en a decide ainsi",
					"Le code ainsi ecrit tel une bible par des moines (kof kof insaliens) reclus chez eux",
					"permettra d'apporter moult amusement a la galerie",
				};
	            //On affiche la pop-up et on lui donne un titre et un type
				JOptionPane.showMessageDialog(null, texte, "(╯°□°）╯︵ ┻━┻", JOptionPane.INFORMATION_MESSAGE,new ImageIcon("./img/testShip.png")); 

		 }else if (e.getSource()==menuItem2){
		 	viderFichier(true);
		 }else if (e.getSource()==menuItem3&&(!problemeLecture&&pseudoFichier.length>5)){
		 	viderFichier(false);
		 }else if (e.getSource()==menuItem4){
		 	//Fermeture normale
		 	System.exit(0); 
		 }
	}

	//Méthode pour supprimer les caractères spéciaux du pseudo entré
    private static String enleverNonAlphanumerique(String a){
        String resultat="";                 //String permettant de receuillir le mot final
        int q=0;
        for(q=0;q<a.length();q++){          //On parcourt le String envoyé
            if(((a.charAt(q)>=97)&&(a.charAt(q)<=122))||((a.charAt(q)>=65)&&(a.charAt(q)<=90))||((a.charAt(q)>=48)&&(a.charAt(q)<=57))){
                resultat=resultat+a.charAt(q);  //Si le caractère est bon, on l'ajoute au mot final
            }
            if ((a.charAt(q)==' ')) {       //On prend en compte les espaces pour ne pas les enlever
                resultat=resultat+" ";
            }
        }
        return resultat;
    }

	public static void main(String [] Args){
		Accueil affichageFenetre= new Accueil();
	}
}
