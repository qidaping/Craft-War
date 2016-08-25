import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;



public class Map {
	int x;
	int y;
	BufferedImage   img;//保存图片变量
	//构造器
	public Map() {
		x=0;
		y=0;
		try {
			img = ImageIO.read(getClass().getResource("imgs/bg.gif"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
		}
	}
	//paint 
	public void paint(Graphics  g){
		g.drawImage(img,x,y-img.getHeight(),null);
		g.drawImage(img,x,y,null);
	}

	//move()
	public void move(){
		y++;
		if(y>img.getHeight())
			y=0;
	}

}
