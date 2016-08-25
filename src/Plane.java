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
	boolean left,up,right,down;//��������
	int index=0;//����
	int level=1;//1234
	boolean hit = false;
	int hitcount=0;//�ۼƱ����еĴ���
	int flas = 0;
	boolean over = false;
	public Plane(){
		//����ͼƬ
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
		img = imgs[index];//��ȡһ��ͼƬ
		w = img.getWidth();//��ȡ��
		h = img.getHeight();//��ȡ��
		x = 320/2-w/2;//����x���괰���м�
		y = 300;//����Y����
	}
	public void paint(Graphics g){
		if(over)//��ǰ�ɻ����ڱ�ը���������Բ����л��ƣ�
			return;
		g.drawImage(img,x,y,null);
		if(hitcount>=10){//�жϻ��д�����������
			flas++;//������������ͼƬ
			img = imgs2[index=flas/3%4];//�任ͼƬ
			if(flas>11) {//����ͼƬ�������
				flas=0;
				over = true;//��ը����
				img = imgs[0];//Ϊ��һ�ο�ʼ��ʼ��ͼƬ
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