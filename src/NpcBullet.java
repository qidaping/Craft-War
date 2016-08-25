import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;


public class NpcBullet {
	int x,y,w,h;
	int step=5;
	double sin,cos;
	BufferedImage img;
	public NpcBullet(Npc npc,Plane plane) {
		try {
			img = ImageIO.read(getClass().getResource("imgs/npcbullet.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = img.getWidth();
		h = img.getHeight();
		x = npc.x+npc.w/2-w/2;
		y = npc.y+npc.h;
		int x1 = plane.x+plane.w/2;
		int y1 = plane.y+plane.h/2;
		int dx = x1-x;
		int dy = y1-y;
		double dLine = Math.sqrt(dx*dx+dy*dy);
		cos = dx/dLine;
		sin = dy/dLine;
	}
	public void paint(Graphics g) {
		g.drawImage(img, x, y, null);
	}
	public void move(Plane plane,Vector<NpcBullet> nbs) {
		x = (int) (x+step*cos);
		y = (int) (y+step*sin);
		//碰撞检测
		int x1 = plane.x;
		int y1 = plane.y;
		int w1 = plane.w;
		int h1 = plane.h;
		if(x>x1-w && x<x1+w1){
			if(y>y1-h && y<y1+h1){
				//通州飞机被击中
				plane.hit = true;
				plane.hitcount++;
				//移除子弹
				nbs.remove(this);
			}
		}
		if(x<-50 || y<-50 || x>370 || y>550){
			nbs.remove(this);
		}
	}
}
