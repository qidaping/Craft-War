import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

import sun.awt.AWTAccessor.KeyEventAccessor;


public class Games extends JFrame{
	 World w = new World();
	
	public Games() {
		this.setSize(320, 480+30);
		this.setTitle("飞机大战");
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);//禁止重置大小
		
		this.add(w);//添加场景
		this.setVisible(true);
		
		Thread t = new Thread(w);
		t.start();
		//添加鼠标监听器
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				switch(w.status){
				case World.START:
					w.status = World.RUNNING;
					break;
				
				case World.RUNNING:
			  		break;
					
				case World.GAME_OVER:
					w.restart();
					w.status = World.START;
			  		break;
				}
			}
		});
		//添加键盘监听器

		this.addKeyListener(new KeyAdapter(){
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT:
					w.plane.left =true;
					break;
				case KeyEvent.VK_UP:
					w.plane.up = true;
					break;
				case KeyEvent.VK_RIGHT:
					w.plane.right = true;
					break;
				case KeyEvent.VK_DOWN:
					w.plane.down=true;
					break;
				case KeyEvent.VK_CONTROL:
					w.shoot = true;
					break;
				}
			}
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()){
				case KeyEvent.VK_LEFT:
					w.plane.left =false;
					break;
				case KeyEvent.VK_UP:
					w.plane.up = false;
					break;
				case KeyEvent.VK_RIGHT:
					w.plane.right = false;
					break;
				case KeyEvent.VK_DOWN:
					w.plane.down=false;
					break;
				}
			}
		});
		
	}

	
	public static void main(String[] args) {
		Games games = new Games();
		

	}

}
