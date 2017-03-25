import java.util.ArrayList;
public class Vague3 extends VagueEnnemis{
	public Vague3 (int limFenetreX,int limFenetreY){
		vaincue=false;
		ecranX=limFenetreX;
		ecranY=limFenetreY;
		/*tabEnnemis=new Ennemis[7];
		tabEnnemis[0]=new Ennemis(ecranX,(int)(ecranY*0.2),10,0,"./img/ennemi2.png");
		tabEnnemis[1]=new Ennemis(ecranX+50,(int)(ecranY*0.3),10,0,"./img/ennemi2.png");
		tabEnnemis[2]=new Ennemis(ecranX+100,(int)(ecranY*0.4),10,0,"./img/ennemi2.png");
		tabEnnemis[3]=new Ennemis(ecranX+150,(int)(ecranY*0.5),10,0,"./img/ennemi2.png");
		tabEnnemis[4]=new Ennemis(ecranX+100,(int)(ecranY*0.6),10,0,"./img/ennemi2.png");
		tabEnnemis[5]=new Ennemis(ecranX+50,(int)(ecranY*0.7),10,0,"./img/ennemi2.png");
		tabEnnemis[6]=new Ennemis(ecranX,(int)(ecranY*0.8),10,0,"./img/ennemi2.png");*/
		tabEnnemis=new ArrayList<Ennemis>(7);
		tabEnnemis.add(0,new Ennemis(ecranX,(int)(ecranY*0.2),10,0,"./img/ennemi2.png"));
		tabEnnemis.add(1,new Ennemis(ecranX+50,(int)(ecranY*0.3),10,0,"./img/ennemi2.png"));
		tabEnnemis.add(2,new Ennemis(ecranX+100,(int)(ecranY*0.4),10,0,"./img/ennemi2.png"));
		tabEnnemis.add(3,new Ennemis(ecranX+150,(int)(ecranY*0.5),10,0,"./img/ennemi2.png"));
		tabEnnemis.add(4,new Ennemis(ecranX+100,(int)(ecranY*0.6),10,0,"./img/ennemi2.png"));
		tabEnnemis.add(5,new Ennemis(ecranX+50,(int)(ecranY*0.7),10,0,"./img/ennemi2.png"));
		tabEnnemis.add(6,new Ennemis(ecranX,(int)(ecranY*0.8),10,0,"./img/ennemi2.png"));
	}
	public void move(){
		if(!vaincue){
			/*int v=0;
			if (v!=tabEnnemis.length) {
				for (int i=0;i<tabEnnemis.length;i++) {
					if (tabEnnemis[i].intacte) {
						tabEnnemis[i].move();
					}else{
						v++;
					//System.out.println("valeur v "+v+" bool "+(v==tabEnnemis.length)+" ennemi posi "+i);
					}
				}
			}
			if (v==tabEnnemis.length){
				vaincue=true;
			}*/
			int v=tabEnnemis.size();
			for (int i=0;i<v;i++) {
				if (tabEnnemis.get(i).intacte) {
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
