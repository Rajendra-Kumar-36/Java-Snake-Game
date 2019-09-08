import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener, ActionListener{
	int xLength[]= new int[800];
	int yLength[]= new int[800];
	int xEnemy[]= {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525
			                  ,550,575,600,625,650,675,700,725,750,775,800,825,850,875,900,925,950,975};
	int yEnemy[]= {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,475,500,525
                              ,550,575,600,625,650};
	private Random rd= new Random();
	private int xposE=rd.nextInt(39),yposE=rd.nextInt(24 );
	private int lengthOfSnake=3;
	private Timer timer;
	private int delay=150;
	private int move=0;
	private boolean left,right,up,down,gameover;
	private ImageIcon leftMouth,rightMouth,upMouth,downMouth,snakeBody,snakeIcon,enemy;
    private int score=0, level=1;
    AudioClip audioHit, audioGameover;
  	public GamePlay(){
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		timer= new Timer(delay,this);
		timer.start();
		  try{
			  URL url= getClass().getResource("hit2.wav");
				audioHit= Applet.newAudioClip(url);
				URL url2= getClass().getResource("Gameover.wav");
				audioGameover= Applet.newAudioClip(url2);
			}catch(Exception e){
				e.printStackTrace();
			}

	}
	
	
	public void paint(Graphics g){
		if(move==0){
			xLength[0]=100;
			xLength[1]=75;
			xLength[2]=50;
			yLength[0]=100;
			yLength[1]=100;
			yLength[2]=100;
		}
		// header
		g.setColor(Color.WHITE);
		g.drawRect(24, 5, 977, 55);
		g.setColor(Color.decode("#054989"));
		g.fillRect(25, 6, 975, 54);
		//snake icon
		snakeIcon= new ImageIcon(getClass().getResource("images/snaketitle.jpg"));
		snakeIcon.paintIcon(this, g, 6+60, 6);
		//game screen
		g.setColor(Color.WHITE);
		g.drawRect(24, 74, 977, 601);
		g.setColor(Color.BLACK);
		g.fillRect(25, 75, 975, 600);
		//draw score
		g.setColor(Color.WHITE);
		g.setFont(new Font("Comic sans MS", Font.PLAIN, 15));
		g.drawString("Score : "+score, 890, 28);
		g.drawString("Level  : "+level, 890, 48);
	
		rightMouth= new ImageIcon(getClass().getResource("images/rightmouth.png"));
		rightMouth.paintIcon(this, g, xLength[0], yLength[0]);
		
		for(int a=0;a<lengthOfSnake;a++){
			if(a==0&& left){
				leftMouth= new ImageIcon(getClass().getResource("images/leftmouth.png"));
				leftMouth.paintIcon(this, g, xLength[a], yLength[a]);
//				g.setColor(Color.WHITE);
//				g.fillRect( xLength[a], yLength[a],20,20);
			}
			if(a==0&&right){
			rightMouth.paintIcon(this, g, xLength[a], yLength[a]);
//			g.setColor(Color.WHITE);
//			g.fillRect( xLength[a], yLength[a],20,20);
			}
			if(a==0&& up){
				upMouth= new ImageIcon(getClass().getResource("images/upmouth.png"));
				upMouth.paintIcon(this, g, xLength[a], yLength[a]);
//				g.setColor(Color.WHITE);
//				g.fillRect( xLength[a], yLength[a],20,20);
			}
			if(a==0&& down){
				downMouth= new ImageIcon(getClass().getResource("images/downmouth.png"));
				downMouth.paintIcon(this, g, xLength[a], yLength[a]);
//				g.setColor(Color.WHITE);
//				g.fillRect( xLength[a], yLength[a],20,20);
			}
			if(a!=0){
				snakeBody= new ImageIcon(getClass().getResource("images/snakeimage.png"));
				snakeBody.paintIcon(this, g, xLength[a], yLength[a]);
		//		g.setColor(Color.GREEN);
		//		g.fillRect(xLength[a], yLength[a],20,20);
			}
		}
		
		enemy= new ImageIcon(getClass().getResource("images/frog2.png"));
		if(xEnemy[xposE]==xLength[0] && yEnemy[yposE]==yLength[0]){
			audioHit.play();
			score++;
			level=1+score/10;
	//		delay=delay-level*10;
	//		timer.setDelay(delay);
			lengthOfSnake++;
		 xposE= rd.nextInt(38);
		 yposE= rd.nextInt(23);
		}
		enemy.paintIcon(this, g, xEnemy[xposE], yEnemy[yposE]);
//		g.setColor(Color.RED);
//		g.fillRect( xEnemy[xposE], yEnemy[yposE],20,20);
		
		// check for gameover
		for(int i=1;i<lengthOfSnake;i++){
			if(xLength[i]==xLength[0] && yLength[i]==yLength[0]){
		//		right=left=up=down=false;
				audioGameover.play();
				timer.stop();
				gameover=true;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Arial", Font.BOLD, 50));
				g.drawString("Gameover !", 350, 350);
				// show score
				g.setColor(Color.YELLOW);
				g.setFont(new Font("Comic sans MS", Font.BOLD, 30));
				g.drawString("Your score is : "+score, 350, 400);
				g.setColor(Color.RED);
				g.setFont(new Font("Arial", Font.BOLD, 20));
				g.drawString("Press space for restart !", 750, 640);
				g.drawString("Press Esc for exit !", 750, 665);
				break;
			}
		}
		
		// In starting
		if(move==0){
			try {
				File f= new File(getClass().getResource("images/wallpaper.jpg").toURI());
				BufferedImage wall= ImageIO.read(f);
				g.drawImage(wall, 25, 75,975,600, this);
			} catch (Exception e) {
				e.printStackTrace();
			}
//			ImageIcon wall= new ImageIcon(getClass().getResource("wallpaper.jpg"));
//			wall.paintIcon(this, g,20, 20);
		
			g.setColor(Color.YELLOW);
			g.drawRect(248, 273, 604, 144);
			g.setColor(Color.RED);
			g.drawRect(250, 275, 600, 140);
			g.setColor(Color.BLUE);
			g.setFont(new Font("Cooper Black", Font.BOLD, 40));
			g.drawString("Press Enter to start game !", 260, 350);
			
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		timer.start();
		if(right){
			for(int i=lengthOfSnake;i>=0;i--){
				if(xLength[i]>=975)
					xLength[i]=0;
				if(i==0)
					xLength[i]=xLength[i]+25;
				else
				xLength[i]=xLength[i-1];
			}for(int i=lengthOfSnake-1;i>=0;i--){
				yLength[i+1]=yLength[i];
			}
			
			repaint();
			}
			else if(left){
				for(int i=lengthOfSnake;i>=0;i--){
					if(xLength[i]==25)
						xLength[i]=1000;
					if(i==0)
						xLength[i]=xLength[i]-25;
					else
					xLength[i]=xLength[i-1];
				}for(int i=lengthOfSnake-1;i>=0;i--){
					yLength[1+i]=yLength[i];
				}
				
				repaint();
				}
				else if(down){
					for(int i=lengthOfSnake;i>=0;i--){
					if(yLength[i]==650)
						yLength[i]=50;
					if(i==0)
						yLength[i]=yLength[i]+25;
					else
					yLength[i]=yLength[i-1];
				}for(int i=lengthOfSnake-1;i>=0;i--){
					xLength[i+1]=xLength[i];
				}
				
				repaint();
				}
					else if(up){
						for(int i=lengthOfSnake;i>=0;i--){
							if(yLength[i]==75)
								yLength[i]=675;
							if(i==0)
								yLength[i]=yLength[i]-25;
							else
							yLength[i]=yLength[i-1];
						}for(int i=lengthOfSnake-1;i>=0;i--){
							xLength[i+1]=xLength[i];
						}
						
						repaint();
		}
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int k=e.getKeyCode();
		if(k==KeyEvent.VK_RIGHT || k==KeyEvent.VK_6){
			move++;
			if(!left)
			right=true;
			up=false;
			down=false;
		}
		else if(k==KeyEvent.VK_LEFT || k==KeyEvent.VK_4){
			move++;
			if(!right)
			left=true;
			up=false;
			down=false;
		}
		else if(k==KeyEvent.VK_UP || k==KeyEvent.VK_8){
			move++;
			if(!down)
			up=true;
			right=false;
			left=false;
		}
		else if(k==KeyEvent.VK_DOWN || k==KeyEvent.VK_2){
			move++;
			if(!up)
			down=true;
			right=false;
			left=false;
		}
		else if(k==KeyEvent.VK_ESCAPE){
			timer.stop();
			System.exit(0);
		}
		else if(k==KeyEvent.VK_SPACE){
			if(!gameover){
				move=0;
			}
			gameover=false;
			audioGameover.stop();
			score=0;
			level=1;
			lengthOfSnake=3;
//			right=true;
			timer.start();
			repaint();
		}
		else if(k==KeyEvent.VK_ENTER && move==0){
			right=true;
			move++;
			repaint();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
