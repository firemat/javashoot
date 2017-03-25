/*
Version 2.0 - 20 mars 2017 a 09h29
Fin implementation boss
Verification du code a faire
Virer les librairies inutiles a faire
Commenter le code a faire
introduire bonus ?
Possibilite de modifier serieusement les jmenu constatee
Penser à redimensionner les images pour le jeu
 */
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.ArrayList;
import java.awt.Cursor;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer; 

public class Shoot extends JFrame implements ActionListener,MouseListener,KeyListener{
	
	private Vaisseau joueur;
	private SuperEnnemi boss;
	//private Tir tirJoueur[];
	private ArrayList<Tir> listTirPlayer;
	private boolean victoire,defaite;
	private final int LARGEUR_FENETRE = 800;
    private final int HAUTEUR_FENETRE = 600;
    private final int NB_TIR = 15;
    private String titre;
	private int vitessex_joueur;
	private int vitessey_joueur;
	private int tempsAffichageMin, tempsAffichageSec;
	private int vagueCourante;
	private int timeLimitTot,timeLimitMin,timeLimitSec;
	private boolean tempsClignote;

	private Musique sonVagues;
	private Musique sonBoss;
	private boolean arriveeBoss;
	private boolean presenceBoss;
	private boolean specialAttack;
	private boolean sonArriveBossJoue=false;
	private boolean sonTempsLimiteJoue=false;

    private ImageIcon icone;
	private Rectangle ecran;
	private Image fond;
	//Timer
    private Timer tempsJeu;
    private boolean pause;
    private int temps;
	//Score
	private PrintWriter ecrire;
	private float score_joueur;
	private String pseudo;
    //Buffer
	private BufferedImage ArrierePlan;
	private Image affichageVie;
	private Graphics buffer;
	private Font police;
	private Accueil accueil;
	private JOptionPane jop;
	private boolean sonActif;
	private boolean bruitagesActif;

	//Bruitages
	private AudioClip explosion;
	private AudioClip tempsLimite;
	private AudioClip jingleDefaite;
	private AudioClip jingleVictoire;
	private AudioClip apparitionBoss;
    VagueEnnemis vagues[];

	public Shoot(String pseudo, boolean activerSon, boolean activerBruitages, int difficulte, int choixFond){
		sonActif=activerSon;
		bruitagesActif=activerBruitages;
		this.pseudo=pseudo;
		arriveeBoss=false;
		presenceBoss=false;
		specialAttack=false;
		pause=false;												//Le jeu n'est pas en pause
		victoire=false;
		defaite=false;
		score_joueur=0;												//On initialise le score
		vitessey_joueur=10;											//vitesse de déplacement du joueur
		vitessex_joueur=15;
		temps=0;
		tempsAffichageMin=0;
		tempsAffichageSec=0;
		listTirPlayer=new ArrayList<Tir>(NB_TIR);
		titre="Jamais 203";										//On initialise le titre de la fenêtre
		//On définit notre fenêtre
		this.setTitle(titre);										
		this.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		this.setLayout(null);
		this.setSize(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		icone = new ImageIcon("./img/tir.png");             //Définit l'icone
		this.setIconImage(icone.getImage());                 //Indique l'icone à utiliser pour la fenêtre
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		ecran=new Rectangle(0,0,getSize().width,getSize().height); 	//Création d'un rectangle couvrant l'intérieur complet de la fenêtre
		joueur=new Vaisseau(0,HAUTEUR_FENETRE/2,"./img/testShip.png",difficulte);
		vagues=new VagueEnnemis[2];
		vagues[0]=new Vague4(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		vagues[1]=new Vague3(LARGEUR_FENETRE,HAUTEUR_FENETRE);
		boss=new SuperEnnemi(LARGEUR_FENETRE+100,HAUTEUR_FENETRE/2,4,10,5,"./img/ennemi1.png");
		if (sonActif) {
			boss.x=LARGEUR_FENETRE+500;
		}
		
		//Lecture de l'image
		Toolkit T=Toolkit.getDefaultToolkit();	
		switch(choixFond){
			case 1:
				fond=T.getImage("./img/img4.jpg");
			break;
			case 2:
				fond=T.getImage("./img/fond1.png");
			break;
			case 3:
				fond=T.getImage("./img/img2.jpg");
			break;
			default:
				fond=T.getImage("./img/fond1.png");
			break;
		}
		affichageVie=T.getImage("./img/life.png");	
		ArrierePlan = new BufferedImage(LARGEUR_FENETRE,HAUTEUR_FENETRE,BufferedImage.TYPE_INT_RGB);	//Définition de l'arrière plan où nous imprimerons nos images
		buffer=ArrierePlan.getGraphics();					
		police=new Font("Times New Roman", Font.BOLD, 20); 									//Définition du buffer pour dessiner sur l'arrière plan
		
		//Implémentation des écouteurs à la fenêtre
		this.addMouseListener(this);
		this.addKeyListener(this);
		tempsJeu=new Timer(50,this);
		//millisec a chaque execution du timer
		timeLimitMin=1;
		timeLimitSec=30;
		timeLimitTot=(timeLimitMin*60+timeLimitSec)*20*tempsJeu.getInitialDelay();
		tempsClignote=false;
		vagueCourante=0;
		if (activerSon) {
			//Lancement du Son
			sonVagues=new Musique("rougoku.wav");
			sonVagues.start();
			sonBoss=new Musique("Corruption.wav");
		}
			//Implémentation bruitage
			try {
                // creation d'un audioclip
                jingleVictoire=Applet.newAudioClip(new URL("file:./img/BGM02.wav"));
                jingleDefaite=Applet.newAudioClip(new URL("file:./img/Birth_and_death.wav"));
                apparitionBoss=Applet.newAudioClip(new URL("file:./img/HIMERARETAKYOUSOU.wav"));
                // lecture avec loop, play et arret avec stop
            } catch (Exception e) {
      			System.out.println(e);
    		}
    		if (bruitagesActif) {
    			try{
                	explosion=Applet.newAudioClip(new URL("file:./img/shoot2.wav"));
                	tempsLimite=Applet.newAudioClip(new URL("file:./img/clock_ticking.wav"));
    			} catch (Exception e) {
      				System.out.println(e);
    			}
    		}
		tempsJeu.start();
	}

	public void gestionVague(){

		if (vagues[vagueCourante].vaincue&&(vagueCourante+1<vagues.length)) {
			vagueCourante++;
		}
		if(vagues[vagueCourante].vaincue&&(vagueCourante+1>=vagues.length)){
			arriveeBoss=true;
			if (sonActif) {
				sonVagues.stop();

			}
			return;
		}

		//System.out.println("appel vague en cours bool= "+vagues[vagueCourante].vaincue+" nb "+vagueCourante+ " victoire "+victoire);
		if (!vagues[vagueCourante].vaincue) {
			vagues[vagueCourante].move();
			//Test collission
			for (int i=0;vagues[vagueCourante].tabEnnemis.size()>i;i++ ) {
				if (vagues[vagueCourante].tabEnnemis.get(i)!=null) {
					for (int v=0;v<listTirPlayer.size();v++) {
						if (listTirPlayer.get(v)!=null&&vagues[vagueCourante].tabEnnemis.get(i).intacte) {
							if(vagues[vagueCourante].tabEnnemis.get(i).Collision(listTirPlayer.get(v))){
								score_joueur=score_joueur+100;
								if (bruitagesActif) {
									explosion.play();
								}
							}
						}
					}	
				}
			}
		}
		
	}
	public void gestionVaisseau(){
		if (joueur.intacte) {
			joueur.move();
			if (!presenceBoss) {
				for (int i=0;vagues[vagueCourante].tabEnnemis.size()>i;i++ ) {
					if (vagues[vagueCourante].tabEnnemis.get(i)!=null) {
						if (vagues[vagueCourante].tabEnnemis.get(i).intacte) {
							if (joueur.Collision(vagues[vagueCourante].tabEnnemis.get(i),temps)) {
								vagues[vagueCourante].tabEnnemis.get(i).intacte=false;
								if (bruitagesActif) {
									explosion.play();
								}
								if (joueur.life<1) {
									/*try{
										if (bruitagesActif) {
											explosion.play();
										}
                    					Thread.sleep(2000);
                    				}catch (Exception finJeu) {
                    					System.out.println(finJeu);
                    				}*/
									tempsJeu.stop();
									defaite=true;
								}
							}	
						}/*else{
							vagues[vagueCourante].tabEnnemis.remove(i);
						}*/
					}
				}
			}else{
				if (boss.tabEnnemis.size()>0) {
					for (int i=0;boss.tabEnnemis.size()>i;i++ ) {
						if (boss.tabEnnemis.get(i)!=null) {
							if (boss.tabEnnemis.get(i).intacte) {
								if (joueur.Collision(boss.tabEnnemis.get(i),temps)) {
									boss.tabEnnemis.get(i).intacte=false;
									if (bruitagesActif) {
										explosion.play();
									}
									if (joueur.life<1) {
										/*try{
											if (bruitagesActif) {
												explosion.play();
											}
                    						Thread.sleep(2000);
                    					}catch (Exception finJeu) {
                    						System.out.println(finJeu);
                    					}*/
										tempsJeu.stop();
										defaite=true;
									}
								}	
							}/*else{
								vagues[vagueCourante].tabEnnemis.remove(i);
							}*/
						}
					}
				}
				if (boss.missile.size()>0) {
					for (int v=0;v<boss.missile.size();v++) {
						if (boss.missile.get(v).intacte) {
							if (joueur.Collision(boss.missile.get(v),temps)) {
								boss.missile.get(v).intacte=false;
								if (bruitagesActif) {
									explosion.play();
								}
								if (joueur.life<1) {
									/*try{
										if (bruitagesActif) {
											explosion.play();
										}
                    					Thread.sleep(2000);
               						}catch (Exception finJeu) {
                   						System.out.println(finJeu);
                    				}*/
									tempsJeu.stop();
									defaite=true;
								}
							}
						}/*else{
							boss.missile.remove(v);
						}*/
					}
				}
			}
		}
	}
	public void gestionTir(){
		for (int v=0;v<listTirPlayer.size();v++) {
			if (listTirPlayer.get(v)!=null) {
				if (listTirPlayer.get(v).intacte) {
					listTirPlayer.get(v).move();
					if (!presenceBoss) {
						for (int i=0;vagues[vagueCourante].tabEnnemis.size()>i;i++ ) {
							if (vagues[vagueCourante].tabEnnemis.get(i)!=null) {
								if(listTirPlayer.get(v).Collision(vagues[vagueCourante].tabEnnemis.get(i))){
									vagues[vagueCourante].tabEnnemis.get(i).intacte=false;
									score_joueur=score_joueur+100;
									if (bruitagesActif) {
										explosion.play();
									}
								}
							}
						}
					}else if(presenceBoss){
						if (boss.tabEnnemis.size()>0) {
							for (int i=0;boss.tabEnnemis.size()>i;i++ ) {
								if (boss.tabEnnemis.get(i)!=null) {
									if(boss.tabEnnemis.get(i).intacte){
										if(listTirPlayer.get(v).Collision(boss.tabEnnemis.get(i))){
											boss.tabEnnemis.get(i).intacte=false;
											score_joueur=score_joueur+300;
											if (bruitagesActif) {
												explosion.play();
											}
										}
									}else{
										boss.tabEnnemis.remove(i);
									}
									
								}
							}
						}
						if (boss.Collision(listTirPlayer.get(v),temps)) {
							score_joueur=score_joueur+1000;
							listTirPlayer.get(v).intacte=false;
							System.out.println("touche reste "+boss.life);
							if (bruitagesActif) {
								explosion.play();
							}
							if (boss.life<1) {
								/*try{
									if (bruitagesActif) {
										explosion.play();
									}
                    				Thread.sleep(2000);
                    			}catch (Exception finJeu) {
                    				System.out.println(finJeu);
                    			}*/
								tempsJeu.stop();
								victoire=true;
							}
						}
					}
				}else{
					listTirPlayer.remove(v);
				}
			}
		}
	}
	public void gestionTirBoss(){
		if (presenceBoss) {
			for (int c=0;c<boss.missile.size();c++) {
				if (boss.missile!=null) {
					if (boss.missile.get(c).intacte) {
						boss.missile.get(c).moveSpecial();
					}
				}
			}
			
			if (temps%50==0) {
				boss.tirSpecial(joueur.x,joueur.y);
			}
			if (((boss.y>(HAUTEUR_FENETRE/2)-5)&&(boss.y<(HAUTEUR_FENETRE/2)+5))) {
				
				if (temps%25>=10&&!specialAttack) {
					specialAttack=true;
					System.out.println("s "+boss.vitessex+" "+temps%75+" "+boss.x);
					boss.x=boss.x-boss.vitessex;
				}
				if (specialAttack&&boss.x==400) {
					boss.generationVague(30,5,Math.PI/(4+boss.life));
				}
				else if (specialAttack&&boss.x==700) {
					specialAttack=false;
				}
			}
			
			for (int s=0;s<boss.tabEnnemis.size();s++) {
				if (boss.tabEnnemis.get(s)!=null) {
					boss.tabEnnemis.get(s).specialMove();
				}
			}

			
			//boss.generationVague(10,5,Math.PI/6);
		}
	}
	public void gestionBoss(){
		if (arriveeBoss) {
			if (sonActif&&!sonArriveBossJoue) {
				apparitionBoss.play();
				sonArriveBossJoue=true;
			}
			boss.move(false,true);
			//System.out.println(boss.x+"  "+boss.y);
			if (boss.x<=700) {
				arriveeBoss=false;
				if (sonActif) {
					apparitionBoss.stop();
                	sonBoss.start();
                	try{
						Thread.sleep(4000);
                	}catch (Exception transitionBoss) {
                    	System.out.println(transitionBoss);
                	}
				}
				presenceBoss=true;
			}
		}
		if (presenceBoss) {
			boss.move(specialAttack,false);
		}
	}

	public void paint(Graphics g) {
		if (fond!=null) {
			buffer.drawImage(fond,0,0,this);						//On dessine le fond via le buffer sur notre arrière-plan
		}
		if (joueur!=null) {
			buffer.drawImage(joueur.image,joueur.x,joueur.y,this);	//On dessine le vaisseau via le buffer sur notre arrière-plan
		}
		
		for (int c=0;c<listTirPlayer.size();c++) {
			if (listTirPlayer.get(c)!=null) {
				if(listTirPlayer.get(c).x!=0 && listTirPlayer.get(c).intacte){
					buffer.drawImage(listTirPlayer.get(c).image,listTirPlayer.get(c).x,listTirPlayer.get(c).y,this);	
				}	
			}	
		}
		if (!presenceBoss&&!arriveeBoss) {
			for (int v=0;v<vagues[vagueCourante].tabEnnemis.size();v++) {
				if (vagues[vagueCourante].tabEnnemis.get(v)!=null && vagues[vagueCourante].tabEnnemis.get(v).intacte) {
					buffer.drawImage(vagues[vagueCourante].tabEnnemis.get(v).image,vagues[vagueCourante].tabEnnemis.get(v).x,vagues[vagueCourante].tabEnnemis.get(v).y,this);
				}
			}
		}else{
			if (boss!=null) {
				buffer.drawImage(boss.image,boss.x,boss.y,this);
				if (boss.missile!=null) {
					for (int f=0;f<boss.missile.size();f++) {
						if (boss.missile.get(f).intacte) {
							buffer.drawImage(boss.missile.get(f).image,boss.missile.get(f).x,boss.missile.get(f).y,this);	
						}
					}
				}
				for (int v=0;v<boss.tabEnnemis.size();v++) {
					if (boss.tabEnnemis.get(v)!=null && boss.tabEnnemis.get(v).intacte) {
						buffer.drawImage(boss.tabEnnemis.get(v).image,boss.tabEnnemis.get(v).x,boss.tabEnnemis.get(v).y,this);
					}
				}
			}
		}
		
		if (affichageVie!=null) {

			switch(joueur.life){
				case 1:
					buffer.drawImage(affichageVie,20,HAUTEUR_FENETRE-60,this);
				break;
				case 2:
					buffer.drawImage(affichageVie,20,HAUTEUR_FENETRE-60,this);
					buffer.drawImage(affichageVie,80,HAUTEUR_FENETRE-60,this);
				break;
				case 3:
					buffer.drawImage(affichageVie,20,HAUTEUR_FENETRE-60,this);
					buffer.drawImage(affichageVie,80,HAUTEUR_FENETRE-60,this);
					buffer.drawImage(affichageVie,140,HAUTEUR_FENETRE-60,this);
				break;
			}
			buffer.setColor(Color.white);
			police = police.deriveFont(20.0f);
			buffer.setFont(police);
			buffer.drawString("Vies restantes",40,HAUTEUR_FENETRE-80);
			buffer.drawString("Temps restant",LARGEUR_FENETRE-180,HAUTEUR_FENETRE-80);
			police = police.deriveFont(28.0f);
			buffer.setFont(police);
			if (tempsClignote) {
				buffer.setColor(Color.red);
			}else{
				buffer.setColor(Color.white);
			}
			if (tempsAffichageSec<10) {
				buffer.drawString(tempsAffichageMin+" : 0"+tempsAffichageSec,LARGEUR_FENETRE-150,HAUTEUR_FENETRE-40);
			}else{
				buffer.drawString(tempsAffichageMin+" : "+tempsAffichageSec,LARGEUR_FENETRE-150,HAUTEUR_FENETRE-40);
			}
		}
		g.drawImage(ArrierePlan,0,0,this);

	}

	@SuppressWarnings("deprecation")
	public void actionPerformed(ActionEvent e){	
		titre="Jamais 203"+" | Nom du joueur : "+pseudo+" | score : "+score_joueur;		
		setTitle(titre);
		score_joueur=score_joueur+(int)(temps/75);
		temps++;

		tempsAffichageSec=((timeLimitTot-(tempsJeu.getDelay()*temps))/1000);
		tempsAffichageMin=(int)((tempsAffichageSec-(tempsAffichageSec%60))/60);
		tempsAffichageSec=(int)(tempsAffichageSec-(tempsAffichageMin*60));
		if ((tempsAffichageMin==1)&&(tempsAffichageSec<2)&&bruitagesActif&&!sonTempsLimiteJoue) {
			tempsLimite.play();
			sonTempsLimiteJoue=true;
		}
		if ((tempsAffichageMin<1)&&((tempsAffichageSec%2)==0)) {
			tempsClignote=true;
		}else if ((tempsAffichageMin<=1)&&((tempsAffichageSec%2)!=0)) {
			tempsClignote=false;
		}
		if ((tempsAffichageMin<=0)&&(tempsAffichageSec<=0)) {
			defaite=true;
		}
		//if (!arriveeBoss) {
			gestionVaisseau();
			gestionTir();
		//}
		if (!presenceBoss&&!arriveeBoss) {
			gestionVague();
		}else{
			gestionTirBoss();
			gestionBoss();
		}
		repaint();
		if (victoire||defaite) {
			tempsJeu.stop();
			if(sonActif&&victoire){
				if (presenceBoss) {
					sonBoss.stop();
				}else{
					sonVagues.stop();
				}
				jingleVictoire.loop();
			}else if(sonActif&&defaite){
				if (presenceBoss) {
					sonBoss.stop();
				}else{
					sonVagues.stop();
				}
				jingleDefaite.loop();
			}
			if (victoire) {
				titre=titre+" GAGNE";
				setTitle(titre);
				jop = new JOptionPane();
				ImageIcon ImgGagne = new ImageIcon("./img/gagne.jpg");			//On définit l'image à afficher
				JOptionPane.showMessageDialog(null,"", "Victoire ! Score: "+score_joueur,JOptionPane.INFORMATION_MESSAGE, ImgGagne);
			}else{
				titre=titre+" PERDU";
				setTitle(titre);
				jop = new JOptionPane();
				ImageIcon ImgDefaite = new ImageIcon("./img/perdu.jpg");			//On définit l'image à afficher
				JOptionPane.showMessageDialog(null,"", "Défaite! Score: "+score_joueur,JOptionPane.INFORMATION_MESSAGE, ImgDefaite);
			}
			
			
			//Ecriture du score
			//Tentative d'écriture
			try{
				//On ouvre le fichier (on met true pour signifier qu'on veut écrire à la suite du fichier)
				FileWriter fw = new FileWriter("Score.txt", true);
				//On définit le buffer pour écrire
				BufferedWriter bufferEcrire = new BufferedWriter (fw);
				//On positionne le curseur sur une nouvelle ligne
				bufferEcrire.newLine();
				//On se positionne au début de la ligne vierge
				ecrire = new PrintWriter(bufferEcrire);
				//On écrit puis on saute une ligne
				ecrire.println(""+pseudo);
				//On écrit en laissant le curseur en fin de ligne
				ecrire.print(""+(int)score_joueur);
				//On ferme le fichier en écriture
				ecrire.close(); 
    		}
    		//Si on échoue durant l'écriture, on stoppe le jeu avec une erreur spécifique
			catch(IOException el) 
			{
            	System.out.println("Impossible d'écrire le score !");            
            	System.exit(-2);    
            }
			this.dispose();
			if (sonActif&&victoire) {
				jingleVictoire.stop();
			}else if(sonActif&&defaite){
				jingleDefaite.stop();
			}
            accueil = new Accueil();
		}
	}
			//Méthodes pour MouseEvent, elles gèrent : La mise en pause du jeu selon la position du curseur, la jouabilité via la souris 
	public void mouseClicked(MouseEvent e){		//Si on clique sur un bouton de la souris, on fait juste bouger
		if (listTirPlayer.size()<NB_TIR) {
			listTirPlayer.add(new Tir(joueur.x+joueur.l,joueur.y,10,0,"./img/tir2.png"));
		}
	}
	public void mousePressed(MouseEvent e){		//Si on presse un bouton de la souris
		
	}
	public void mouseReleased(MouseEvent e){	//Si on relâche le bouton de la souris
		
	}
	public void mouseExited(MouseEvent e){ 		//Si le curseur sort de la fenêtre, le jeu se met en pause
    	if (!victoire&&!defaite) {
    		tempsJeu.stop();
    		pause=true;
    		if (bruitagesActif) {
    			if (arriveeBoss) {
    				apparitionBoss.stop();
    			}
    		}
    	}
    }
    public void mouseEntered(MouseEvent e){		//Si le curseur rentre dans la fenêtre, le jeu se remet en marche
    	if (!victoire&&!defaite) {
    		tempsJeu.start();
    		pause=false;
    		if (bruitagesActif) {
    			if (arriveeBoss) {
    				apparitionBoss.play();
    			}
    		}
    	}
    }


    public void keyTyped(KeyEvent e){
	}
	public void keyPressed(KeyEvent e){
		//Selon la touche appuyé
		switch(e.getKeyCode()){
			case KeyEvent.VK_SPACE:				//En appuyant sur la touche espace
				if (listTirPlayer.size()<NB_TIR) {
					listTirPlayer.add(new Tir(joueur.x+joueur.l,joueur.y,10,0,"./img/tir.png"));
				}
			break;
			/*case KeyEvent.VK_F10:
				*/

			case KeyEvent.VK_ESCAPE:			//En appuyant sur la touche echap, on fait sauter l'oiseau
				System.exit(0);
			break;

			case KeyEvent.VK_ENTER:				//En appuyant sur la touche enter, on met le jeu soit en pause (s'il ne l'est pas), soit on le remet en marche (s'il était déjà en pause)
				if (pause&&!victoire&&!defaite) {
					tempsJeu.start();
					pause=false;
				}else{
					tempsJeu.stop();
					pause=true;
				}

			case KeyEvent.VK_UP:
				joueur.y=joueur.y-vitessey_joueur;
			break;

			case KeyEvent.VK_DOWN:
				joueur.y=joueur.y+vitessey_joueur;
			break;

			case KeyEvent.VK_LEFT:
				joueur.x=joueur.x-vitessex_joueur;
			break;

			case KeyEvent.VK_RIGHT:
				joueur.x=joueur.x+vitessex_joueur;
			break;
		}
	}
	public void keyReleased(KeyEvent e){
	}
}
