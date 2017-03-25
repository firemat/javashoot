import java.util.ArrayList;
public class Vague4 extends VagueEnnemis{
	private int ecartX, decalLigne; 
	public Vague4 (int limFenetreX,int limFenetreY){
		vaincue=false;
		ecranX=limFenetreX;
		ecranY=limFenetreY/8;
		ecartX=200;
		decalLigne=100;
		/*tabEnnemis=new Ennemis[8];
		tabEnnemis[0]=new Ennemis(ecranX+ecartX,ecranY,6,0,"./img/ennemi2.png");
		tabEnnemis[1]=new Ennemis(ecranX+ecartX+decalLigne,ecranY*7,6,0,"./img/ennemi2.png");
		tabEnnemis[2]=new Ennemis(ecranX+ecartX*2,ecranY,6,0,"./img/ennemi2.png");
		tabEnnemis[3]=new Ennemis(ecranX+ecartX*2+decalLigne,ecranY*7,6,0,"./img/ennemi2.png");
		tabEnnemis[4]=new Ennemis(ecranX+ecartX*3,ecranY,6,0,"./img/ennemi2.png");
		tabEnnemis[5]=new Ennemis(ecranX+ecartX*3+decalLigne,ecranY*7,6,0,"./img/ennemi2.png");
		tabEnnemis[6]=new Ennemis(ecranX+ecartX*4,ecranY,6,0,"./img/ennemi2.png");
		tabEnnemis[7]=new Ennemis(ecranX+ecartX*4+decalLigne,ecranY*7,6,0,"./img/ennemi2.png");*/
		tabEnnemis=new ArrayList<Ennemis>(8);
		tabEnnemis.add(0,new Ennemis(ecranX+ecartX,ecranY,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(1,new Ennemis(ecranX+ecartX+decalLigne,ecranY*7,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(2,new Ennemis(ecranX+ecartX*2,ecranY,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(3,new Ennemis(ecranX+ecartX*2+decalLigne,ecranY*7,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(4,new Ennemis(ecranX+ecartX*3,ecranY,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(5,new Ennemis(ecranX+ecartX*3+decalLigne,ecranY*7,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(6,new Ennemis(ecranX+ecartX*4,ecranY,6,0,"./img/ennemi2.png"));
		tabEnnemis.add(7,new Ennemis(ecranX+ecartX*4+decalLigne,ecranY*7,6,0,"./img/ennemi2.png"));
	}
	public void move(){
		if(!vaincue){
			/*int v=0;
			//equilibre avec une amplitude de 1.5*la vitesse en x
			for (int i=0;i<tabEnnemis.length;i++) {
				if (tabEnnemis[i].intacte) {
					if (i%2==0) {
						//(1.5*v(x))*sin(4PI*g) avec g entre 0 et 1 (represente le pourcentage de la longeur parcourue sur l'ecran)
						tabEnnemis[i].vitessey=(int)(9*Math.sin((4*Math.PI)*(tabEnnemis[i].x)/ecranX));
					}else{
						tabEnnemis[i].vitessey=(int)(9*Math.cos((4*Math.PI)*(tabEnnemis[i].x)/ecranX));
					}
					tabEnnemis[i].move();
				}else{
					v++;
				}
			}
			if (v==tabEnnemis.length) {
				vaincue=true;
			}*/
			int v=tabEnnemis.size();
			for (int i=0;i<v;i++) {
				if (tabEnnemis.get(i).intacte) {
					if (i%2==0) {
						//(1.5*v(x))*sin(4PI*g) avec g entre 0 et 1 (represente le pourcentage de la longeur parcourue sur l'ecran)
						tabEnnemis.get(i).vitessey=(int)(9*Math.sin((4*Math.PI)*(tabEnnemis.get(i).x)/ecranX));
					}else{
						tabEnnemis.get(i).vitessey=(int)(9*Math.cos((4*Math.PI)*(tabEnnemis.get(i).x)/ecranX));
					}
					tabEnnemis.get(i).move();
				}else{
					tabEnnemis.remove(i);
					v--;
					i--;
					//System.out.println("valeur v "+v+" bool "+(v==tabEnnemis.length)+" ennemi posi "+i);
				}
			}
			if (tabEnnemis.isEmpty()){
				vaincue=true;
			}
		}
	}
}
