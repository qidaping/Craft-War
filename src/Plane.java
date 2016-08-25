import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Plane {
	int x;
	int y;
	int w;
	int h;
	BufferedImage [] imgs = new BufferedImage[3];
	BufferedImage [] imgs2 = new BufferedImage[4];
	BufferedImage img;
	boolean left,up,right,down;//上下左右
	int index=0;//索引
	int level=1;//1234
	boolean hit = false;
	int hitcount=0;//累计被击中的次数
	int flas = 0;
	boolean over = false;
	public Plane(){
		//加载图片
		try {
			for(int i =0;i <imgs.length;i++){
				imgs[i]=ImageIO.read(getClass().getResource("imgs/plane"+i+".png"));
			}
			for(int i =0;i <imgs2.length;i++) {
				imgs2[i]= ImageIO.read(getClass().getResource("imgs/bomb_"+i+".png"));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		img = imgs[index];//获取一张图片
		w = img.getWidth();//获取宽
		h = img.getHeight();//获取高
		x = 320/2-w/2;//设置x坐标窗体中间
		y = 300;//设置Y坐标
	}
	public void paint(Graphics g){
		if(over)//当前飞机处于爆炸结束，所以不进行绘制，
			return;
		g.drawImage(img,x,y,null);
		if(hitcount>=10){//判断击中次数满足销毁
			flas++;//计数播放销毁图片
			img = imgs2[index=flas/3%4];//变换图片
			if(flas>11) {//满足图片播放完毕
				flas=0;
				over = true;//爆炸结束
				img = imgs[0];//为下一次开始初始化图片
				return;
			}
		}
		if(hit){
			flas++;
			img = imgs[index = flas/3%2];
			if(flas>11){
				hit = false;
				flas=0;
				index=0;
			}
		}
		
	}
	public void move(){
		if(left)
		    x-=10;
		if(up)
			y-=10;
		if(right)
			x+=10;
		if(down)
			y+=10;
		if(x<0)
			x=0;
		if(y<0)
			y=0;
		if(x>320-w)
			x=320-w;
		if(y>480-h)
			y=480-h;
   }
}