
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Tir extends Objet{
	public Tir (int ax, int ay, int vx, int vy,String chemin){
		super(ax,ay,vx,vy); 
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
			intacte=false;
		}
        return BoxObjet.intersects(O.BoxObjet); 
    }

	public void move(){
		if(intacte){
			x=x+this.vitessex;
    		if (x>=800){        
       			x=0;
       			intacte=false;
       			this.vitessex=0;
       		}
			BoxObjet.setLocation(x,y);     
		}
	}

  public void moveSpecial(){
    if(intacte){
      x=x+this.vitessex;
      y=y+this.vitessey;
        if (x<=0){        
            x=-10;
            intacte=false;
            this.vitessex=0;
            this.vitessey=0;
        }else if (x>=900) {
            x=910;
            intacte=false;
            this.vitessex=0;
            this.vitessey=0;
        }
        if (y<=0) {
            y=-10;
            intacte=false;
            this.vitessex=0;
            this.vitessey=0;
        }else if (y>=700) {
            y=710;
            intacte=false;
            this.vitessex=0;
            this.vitessey=0;
        }
      BoxObjet.setLocation(x,y);     
    }
  }

}
