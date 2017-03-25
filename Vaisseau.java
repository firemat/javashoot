
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Rectangle;

public class Vaisseau extends Objet{
  private int tempsTouche;
	public Vaisseau (int ax, int ay,String chemin, int difficulte){
		super(ax,ay,2,0);  
    tempsTouche=0;            
		try {
             	image= ImageIO.read(new File(chemin));    //Lecture de l'image
            } 
        catch(Exception err)                                       //Si on échoue, on arrête le programme et on affiche des informations dans la console
            {
            	System.out.println("Image introuvable !");            
            	System.exit(-1);                                        //Code d'erreur spécifique à l'impossibilité de lire l'image
            }   
        h= image.getHeight(null);       
        l= image.getWidth(null);      
        BoxObjet = new Rectangle(x,y,l-5,h-5); 
    switch(difficulte){
      case 1:
        life=3;
      break;

      case 2:
        life=2;
      break;

      case 3:
        life=1;
      break;
    }
	}
	

	public boolean Collision(Objet O,int temps) {
    if (BoxObjet.intersects(O.BoxObjet)) {
      this.Touche(temps);
    }
        return BoxObjet.intersects(O.BoxObjet); 
    }
	public void move(){
		if (x<=0){          
        	x=0;
    	}
    if (x>=800){       
     		x=800;
      }
    if (y<=0) {
     	y=0;
    }
   	if (y>=600-h) {
   		y=600-h;
   	}
		BoxObjet.setLocation(x,y);    
	}
  public void Touche(int temps){
    if(temps-tempsTouche>1){
      life--;
      tempsTouche=temps;
    }
    if (life<1) {
      try{
          image=ImageIO.read(new File("./img/testex.png"));
      }
      catch(Exception err){
        System.out.println("Image introuvable !");            
        System.exit(-1);                          //Code d'erreur spécifique à l'impossibilité de lire l'image
      }
    }
  }
}

