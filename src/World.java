import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Vector;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

//JPanel new Runnable
		
public class World extends JPanel implements Runnable {
	Map map = new Map();//创建地图
	Plane plane = new Plane();//创建飞机
	//绘制
	Vector<Bullet>  bullets = new Vector<Bullet>();
	int BulletCount = 0;
	int BulletStep = 8;
	
	Vector<NpcBullet> nbs = new Vector<NpcBullet>();

    Vector<Npc> npcs = new Vector<Npc>();
    int npcCount = 0;
    int npcStep = 50;
    
    
    public static int Score;
    //随机数
    Random r = new Random();
    
    public static final int START=0;
    public static final int RUNNING=1;
    public static final int GAME_OVER=2;
    public int status;//记录状态
    BufferedImage start;
    BufferedImage game_over;
	 boolean shoot = false;
	public World() {
		try {
			start = ImageIO.read(getClass().getResource("imgs/start.png"));
			game_over = ImageIO.read(getClass().getResource("imgs/gameover.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	status=0;
    }
    
	public void paint(Graphics g) {
		map.paint(g);
		//绘制血槽
		g.setColor(Color.RED);
		g.drawRect(10,10, 103,15);
		int value = 100-plane.hitcount*10;
		if(value<0){
			value=0;
			//status = GAME_OVER;
		}
		
		g.fillRect(12,12,value,12);
		//绘制分数
		g.setColor(Color.white);
		Font f = new Font(Font.SANS_SERIF,Font.ITALIC|Font.BOLD,20);
		g.setFont(f);
		g.drawString(Score*100+"分",220,30);
		if(plane.over){
			status = GAME_OVER;
		    String  old = writeFile();
		    g.drawString("本次得分："+Score+"00",100,100);
		    g.drawString("历史最高分:"+old+"00",100,130);
		}
		switch(status){
		case START:
			plane.paint(g);
			g.drawImage(start,160-start.getWidth()/2, 200,this);
			break;
		case RUNNING:
			if(Score<301){
				plane.level=Score/100+1;
			}
			plane.paint(g);
			for(int i =0;i <bullets.size();i++) {
				bullets.get(i).paint(g);
			}
			for(int i =0; i <npcs.size();i++) {
				npcs.get(i).paint(g);
			}
			for(int i =0;i <nbs.size();i++){
				nbs.get(i).paint(g);
			}
			break;
		case GAME_OVER:
			g.drawImage(game_over,160-game_over.getWidth()/2, 200,this);
			break;
		}

		
	}
	
	//添加未实现的方法  多线程
	@Override
	public void run() {
		while(true){
			//想要循环执行的代码写这里
			map.move();	
			switch(status){
			case START:
				break;
			case RUNNING:
				plane.move();
				if(shoot)
					addBullet();
				for(int i =0;i <bullets.size();i++) {
					bullets.get(i).move(bullets,npcs);
				}
				addNpc();
				for( int i =0; i <npcs.size();i++){
					npcs.get(i).move(npcs,nbs,plane);
				}
				for(int i =0;i <nbs.size();i++){
					nbs.get(i).move(plane,nbs);
				}
				
				break;
			case GAME_OVER:
				break;
			} 
			try{
				Thread.sleep(1000/60);
			}catch(InterruptedException e){
				e.printStackTrace();
			}	
			repaint();
			

		}
		
	}
	void addBullet(){
		BulletCount++;
		if(BulletCount % BulletStep==0){
			if(plane.level==1){
		        Bullet b =  new Bullet();
		        b.x = plane.x+plane.w/2-b.w/2;
		        b.y = plane.y-b.h;
		        bullets.add(b);
		        BulletCount = 0;
			}
		    if(plane.level==2){
			    Bullet b =  new Bullet();
			    b.x = plane.x+plane.w/2-b.w/2;
			    b.y = plane.y-b.h;
			    bullets.add(b);
			    
			    b =  new Bullet();
			    b.x = plane.x+plane.w/2-b.w/2;
			    b.y = plane.y-b.h;
			    bullets.add(b);
			    BulletCount = 0;
		    }
		    if(plane.level==3){
			    Bullet b =  new Bullet();
			    b.x = plane.x+plane.w/2-b.w/2;
			    b.y = plane.y-b.h;
			    bullets.add(b);
			    
			    b =  new Bullet();
			    b.x = plane.x+plane.w/2-b.w/2;
			    b.y = plane.y-b.h;
			    b.xstep-=1;
			    bullets.add(b);
			    
			    b =  new Bullet();
			    b.x = plane.x+plane.w/2-b.w/2;
			    b.y = plane.y-b.h;
			    b.xstep+=1;
			    bullets.add(b);
			    BulletCount = 0;
		    }
	   }
    }
	void addNpc(){
		npcCount++;
		if(npcCount % npcStep == 0) {
			int type = r.nextInt(8);
			int direction = r.nextInt(3);
			for(int i=0;i<4;i++){
				Npc n = new Npc(type);
				switch(direction){
				case 0://左侧
					n.x = -n.w-(50*i);
					n.y = -n.h-(50*i);
					n.xstep = 3;
					n.ystep = 3;
					
					break;
				case 1://中间
					n.x =  320/2-(150+n.w)/2+(50*i);
					n.y = -n.h;
					n.ystep = 3;
					break;
				case 2://右侧
					n.x = 320+(50*i);
					n.y = -n.h-(50*i);
					n.xstep = -3;
					n.ystep = 3;
					break;
				}
			    npcs.add(n);
			}
			npcCount = 0;
		}
	}
	//初始化数据
	void restart() {
		plane.hitcount = 0;
		plane.x = 320/2-plane.w/2;
		plane.y = 300;
		npcs.clear();
		nbs.clear();
		bullets.clear();
		Score = 0;
		plane.over = false;
		plane.level = 1;
	}
	String writeFile(){
		String str = null;
		File f = new File("d://score.txt");
		if(!f.exists()){
				try {
					f.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			str = br.readLine();
			br.close();
			fr.close();
			if(str==null){
				str = Score+"";
			}else{
				int _old = Integer.parseInt(str);
				str = String.valueOf(_old>Score?_old:Score);
			}
			
			FileWriter fw = new FileWriter(f); 
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(str);
			bw.close();
			fw.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	    return str;
	}
}
