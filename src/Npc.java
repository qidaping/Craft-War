import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;


public class Npc {
	int x;
	int y;
	int w;
	int h;
	int xstep=0;
	int ystep=5;
	BufferedImage img;
	BufferedImage [] imgs = new BufferedImage[4];
	int index=0;
	boolean hit = false;//击中
	boolean destroy = false;//销毁
	int hitCount = 0;
	Random r = new Random();
	int randomNum;
	int time;
    public Npc(int type ) {
    	try {
			img = ImageIO.read(getClass().getResource("imgs/"+type+".png"));
			for(int i =0;i <imgs.length;i++) {
				imgs[i]= ImageIO.read(getClass().getResource("imgs/bomb_"+i+".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = img.getWidth();
		h = img.getHeight();
		y=-h;
		randomNum = r.nextInt(50)+50;
		time = 0;
    }
    public void paint(Graphics g){
    	if(destroy)
    		return;
    	g.drawImage(img,x,y,null);
    
    	if(hit){
    		img = imgs[index++];
    		index = hitCount++/2%5;
    	}
    	if(index>=imgs.length){
    		destroy = true;
    	}
    }
    public void move(Vector<Npc> npcs,Vector<NpcBullet> nbs, Plane plane){
    	if(destroy){
    		npcs.remove(this);
    		World.Score++;
    		return;
    	}
    	int x1 = plane.x;
    	int y1 = plane.y;
    	int w1 = plane.w;
    	int h1 = plane.h;
    	if(x>x1-w && x<x1+w1){
			if(y>y1-h && y<y1+h1){
				//通州飞机被击中
				hit = true;
				plane.hitcount=10;
			}
        }
    	time++;
    	if(time % randomNum==0){
    		NpcBullet nb = new NpcBullet(this,plane);
    		nbs.add(nb);
    		time=0;
    		randomNum = r.nextInt(50)+50;
    	}
    	
    	x+=xstep;
    	y+=ystep;
    	if(y>300){
    		ystep=-1;
    		xstep=-xstep;
    	}
    	if(y<-250){
    		npcs.remove(this);
    	}
    }
    
}

