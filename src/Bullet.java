import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;


public class Bullet {

	int x;
	int y;
	int w;
	int h;
	int xstep = 0;
	BufferedImage img;
	
	public  Bullet() {
		try {
			img = ImageIO.read(getClass().getResource("imgs/bullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = img.getWidth();
		h = img.getHeight();
	}
	public void paint(Graphics g) {
		g.drawImage(img,x,y,null);
	}
	public void move(Vector<Bullet>  bullets,Vector<Npc> npcs){
		y-=8;
		x+=xstep;
		if(y<-h){
			bullets.remove(this);
		}
		for(int i=0;i<npcs.size();i++){
			int x1 = npcs.get(i).x;
			int y1 = npcs.get(i).y;
			int w1 = npcs.get(i).w;
			int h1 = npcs.get(i).h;
			
			if(x>x1-w && x< x1+w1) {
				if(y<y1+h1 && y>y1-h){
					//移除子弹
					bullets.remove(this);
					//npc被击中状态
					npcs.get(i).hit = true;
					//积分系统
				}
			}
		}
		
			
	}
	
}
