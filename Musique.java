
import java.io.*;
import javax.sound.sampled.*;
 
 
public class Musique extends Thread{ //Utilisation d'un thread pour l'éxécution en parallèle
    String chemin;
    AudioInputStream audioInputStream = null;
    SourceDataLine line;
    boolean stop;
    int bytePause;
    public Musique(String fichier){
        chemin="./img/"+fichier;
        stop=false;
    }
    public void run(){
        stop=false;
        File fichier = new File(chemin); //Choix du fichier audio (attention forcément un .wav)
        try {
        @SuppressWarnings("unused")
		AudioFileFormat format = AudioSystem.getAudioFileFormat(fichier); //vérification que les formats sont supportés
        } catch (UnsupportedAudioFileException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
         
        try {
            audioInputStream = AudioSystem.getAudioInputStream(fichier); //vérification que les streams sont suportés
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
         //Obtenir les informations liées au fichier audio
         AudioFormat audioFormat = audioInputStream.getFormat();
         DataLine.Info info = new DataLine.Info(SourceDataLine.class,audioFormat);
        //Amorce de la lecture et extraction de la première trame audio
         try {
             line = (SourceDataLine) AudioSystem.getLine(info); //Permet de lire les valeurs de la musique
                        
             } catch (LineUnavailableException e) {
               e.printStackTrace();
               return;
             }
          
        try {
                line.open(audioFormat);
        } catch (LineUnavailableException e) {
                    e.printStackTrace();
                    return;
        }
        line.start();
        /*Décompose la trame en un tableau de 10 octects, 
        lit la trame morceau par morceau via le tableau 
        puis stocke les valeurs de la trame audio suivante dans un tableau*/
        try {  
            byte bytes[] = new byte[1024]; 
            int bytesRead=0;
            while (((bytesRead = audioInputStream.read(bytes, 0, bytes.length))!=-1)&&(!stop)){
                 line.write(bytes, 0, bytesRead);
                }
            audioInputStream.close();
        } catch (IOException io) {
            io.printStackTrace();
            return;
        }
    }
    public void arret(){
        stop=true;
        return;
    }
}
