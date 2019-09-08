import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {

	public Main(){
JFrame fr= new JFrame("Snake");
		ImageIcon img= new ImageIcon(getClass().getResource("images/IconSnake.jpg"));
		fr.setIconImage(img.getImage());
	//	fr.setIconImage(Toolkit.getDefaultToolkit().getImage("/snakeIcon.ico"));
		fr.setBounds(100, 5, 1025, 725);
		fr.setBackground(Color.RED);
		fr.setResizable(false);
		fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GamePlay gp= new GamePlay();
		fr.add(gp);
		fr.setVisible(true);
	}
	public static void main(String[] args) throws Exception {
		new Main();
	}

}
