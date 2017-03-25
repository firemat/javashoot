
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public abstract class VagueEnnemis {  
	public boolean vaincue;
    public int lifeTraget;
    //public Ennemis[] tabEnnemis;
    public ArrayList<Ennemis> tabEnnemis;
    protected int ecranX, ecranY; 
	public BufferedImage image;		//Image représentant l'objet que l'on dessinera par la suite
	public Rectangle BoxObjet;		//Objet représentant un rectangle qui englobe l'image de l'objet afin de détecter les collisions
    								//Sert quand notre objet translate indépendament des autes en y pour savoir quels qens et limites imposer à son mouvement
    // Constructeur principal
    public VagueEnnemis(){
    }
    // Class abstract pour renvoyer à la méthode spécifique à chauqe objets pour les faire bouger
    public abstract void move();
}
