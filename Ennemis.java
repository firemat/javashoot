
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Ennemis extends Objet{
	public Ennemis (int ax, int ay, int vx, int vy,String chemin){
		super(ax,ay,vx,vy);           
    life=1;
		try {
             	image= ImageIO.read(new File(chemin));    //Lecture de l'image
            } 
        catch(Exception err)                              //Si on échoue, on arrête le programme et on affiche des informations dans la console
            {
            	System.out.println("Image introuvable !");            
            	System.exit(-1);                          //Code d'erreur spécifique à l'impossibilité de lire l'image
            }   
        h= image.getHeight(null);       
        l= image.getWidth(null);        
        BoxObjet = new Rectangle(x,y,l,h); 
	}

	public boolean Collision(Objet O) {
		if (BoxObjet.intersects(O.BoxObjet)) {
      //life--;
      //if (life<1) {
        intacte=false;
      //}
		}
        return BoxObjet.intersects(O.BoxObjet); 
    }

	public void move(){
		if (intacte) {
			x=x-this.vitessex;
			y=y-this.vitessey;
    		if (x<l){        
       			x=700+l;
       		}
       		if (y<h) {
       			y=h;
       		}else if(y>500-h){
       			y=500-h;
       		}
			BoxObjet.setLocation(x,y);   
		}
	}
	public void specialMove(){
		if (intacte) {
			x=x+this.vitessex;
			y=y+this.vitessey;
			if (x<-l){        
       			intacte=false;
       		}else if(x>600+l){
       			intacte=false;
       		}

       		if (y<-h) {
       			intacte=false;
       		}else if(y>500+h){
       			intacte=false;
       		}
			BoxObjet.setLocation(x,y);   
		}
	}
}
