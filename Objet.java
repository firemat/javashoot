
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class Objet {  
	public int x,y,h,l;				//Position en, en y, la hauteur de l'image, la largeur de l'image
    public int vitessex;
    public int vitessey;
	public boolean intacte;
    public int life;
	public BufferedImage image;		//Image représentant l'objet que l'on dessinera par la suite
	public Rectangle BoxObjet;		//Objet représentant un rectangle qui englobe l'image de l'objet afin de détecter les collisions
    								//Sert quand notre objet translate indépendament des autes en y pour savoir quels qens et limites imposer à son mouvement
    // Constructeur principal
    public Objet(int ax, int ay, int vx, int vy){
        x=ax;
        y=ay;
        intacte=true; 
        vitessex=vx;
        vitessey=vy;
    }
    // Class abstract pour renvoyer à la méthode spécifique à chauqe objets pour les faire bouger
    public abstract void move();
}
