import java.io.File;
import javax.imageio.ImageIO;
import java.awt.Rectangle;
import java.util.ArrayList;

public class SuperEnnemi extends Objet{
    private int tempsTouche;
	public ArrayList<Tir> missile;
	public ArrayList<Ennemis> tabEnnemis;
	public SuperEnnemi (int ax, int ay, int vx, int vy,int vie,String chemin){
		super(ax,ay,vx,vy);            
		tempsTouche=0; 
		life=vie;
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
        missile=new ArrayList<Tir>(10);
		tabEnnemis=new ArrayList<Ennemis>(16);

	}

	public boolean Collision(Objet O,int temps) {
		if (BoxObjet.intersects(O.BoxObjet)) {
			this.Touche(temps);
		}
        return BoxObjet.intersects(O.BoxObjet); 
    }

    public void Touche(int temps){
    	if(temps-tempsTouche>10){
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

    public void move(){
    	move(false,false);
    }
	public void move(boolean specialAttack, boolean apparitionBoss){
		if (apparitionBoss) {
			x=x-this.vitessex;
			if (x>700) {
				return;
			}
		}else if (!specialAttack&&!apparitionBoss) {
			y=y-this.vitessey;
    		if (y<=0){        
       			y=0;
       			this.vitessey=-this.vitessey;
       		}else if(y>=600){
       			y=600;
       			this.vitessey=-this.vitessey;
       		}
		}else{
			x=x-this.vitessex;
			if (x<=400) {
				x=400;
				this.vitessex=-this.vitessex;
			}else if (x>=700) {
				x=700;
				this.vitessex=-this.vitessex;
			}
		}
		
		BoxObjet.setLocation(x,y);    
	}
	public void tirSpecial(int jx,int jy){
		int vpx=-10;
		int vpy=0;
		double pente=10*(this.y-jy)/(this.x-jx);
		vpy=(int)(vpx*pente)/10;
		missile.add(new Tir(this.x,this.y,vpx,vpy,"./img/tir.png"));
	}
	public void generationVague(int radius, int vitesse, double angleSeparation){
		tabEnnemis.add(0,new Ennemis(x+(int)(radius*Math.cos(0)),y+(int)(radius*Math.sin(0)),vitesse,0,"./img/ennemi2.png"));
		for (int i=1;i<(int)((2*Math.PI)/angleSeparation);i++) {
			tabEnnemis.add(i,new Ennemis(x+(int)(radius*Math.cos(i*angleSeparation)),y+(int)(radius*Math.sin(i*angleSeparation)),(int)(10*vitesse*Math.cos(i*angleSeparation))/10,(int)(10*vitesse*Math.sin(i*angleSeparation))/10,"./img/ennemi2.png"));
		}
	}
}
