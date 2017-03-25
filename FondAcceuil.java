
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import java.awt.Color;
//import java.awt.AlphaComposite;

/*
FondAcceuil: class héritant de JPanel et définissant un objet JPanel sur lequel nous greffrons des composants
			 Permet d'afficher le fond de la fenêtre d'accueil
*/
public class FondAcceuil extends JPanel {
	//Méthode pour dessiner le fond de notre fenêtre d'accueil sur notre JPanel
    Image img,img1,img2,img3,img4;
    public FondAcceuil(){
      //Lecture de l'image.
      try {
          img = ImageIO.read(new File("./img/img3.jpg"));           //Tentative de lecture de l'image.
          img1 = ImageIO.read(new File("./img/img4.jpg")); 
          img2 = ImageIO.read(new File("./img/fond1.png")); 
          img3 = ImageIO.read(new File("./img/img2.jpg")); 
          img4 = ImageIO.read(new File("./img/scoreboard.png")); 
        } catch (IOException e) {
          e.printStackTrace();                                                  //Si la lecture est impossible, on ignore.
      }
    }
  	protected void paintComponent(Graphics g){                
  		//Lecture de l'image.
      if ((img!=null)&&(img1!=null)&&(img2!=null)&&(img3!=null)&&(img4!=null)) {
          g.drawImage(img,0,0,this.getWidth(), this.getHeight(), this);     //Si cela réussit, on dessine notre image sur notre JPanel.
          g.drawImage(img1,670,10,330,210,this);
          g.drawImage(img2,670,230,330,210,this);
          g.drawImage(img3,670,450,330,220,this);
          g.drawImage(img4,50,100,350,575,this);
          int alpha = 127; // 50% transparent
          Color blancTransparent = new Color(255,255,255,alpha);
          g.setColor(blancTransparent);
          g.fillRect(670,190,330,30);
          g.fillRect(670,410,330,30);
          g.fillRect(670,640,330,30);
      }
  	}
}
