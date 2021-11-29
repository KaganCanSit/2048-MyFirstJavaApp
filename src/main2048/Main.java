package main2048;

import java.awt.Canvas;			//Cizim Yapilabilir, Girdi Girilebilir Ekran Icin Kullaniyoruz.
import java.awt.Dimension;		//Olceklendirme Ve Icerik Icin Kullaniyoruz.

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.Graphics2D;

import main2048.game.Game;
import main2048.input.Keyboard;

public class Main extends Canvas implements Runnable{
	
	public static final int WIDTH = 400, HEIGHT = 400;	//Form Olcutleri
	public static float scale = 2.0f;					//Olcek
	
	public JFrame frame;
	public JLabel label;
	public Thread thread;
	public Keyboard key;
	public Game game;
	public boolean running = false;
	
	public static BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public static int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	
	
	//Oyun Formunun Olceklendirmesi Ve Frame, Oyun, Klavye Takibi Icin Diger Class'larden Nesne Ureterek Forma Dahil Etme.
	public Main() 
	{
		setPreferredSize(new Dimension((int) (WIDTH*scale+300), (int) (HEIGHT*scale)));
		frame = new JFrame();
		game = new Game();
		key = new Keyboard();
		addKeyListener(key);
	}
	
	public void start() 
	{
		running = true;
		thread = new Thread(this, "loopThread");
		thread.start();
	}
		
	//Ekran Yenileme(FPS)
	public void run() 
	{
		long lastTimeInNanoSeconds = System.nanoTime();
		long timer = System.currentTimeMillis();
		double nanoSecondsPerUpdate = 1000000000.0 / 60.0;
		double updatesToPerform = 0.0;
		int frames = 0;
		int updates = 0;
		requestFocus();
		
		while(running) 
		{
			long currentTimeInNanoSeconds = System.nanoTime();
			updatesToPerform += (currentTimeInNanoSeconds - lastTimeInNanoSeconds) / nanoSecondsPerUpdate;
			if(updatesToPerform >= 1) 
			{
				update();
				updates++;
				updatesToPerform--;
			}
			lastTimeInNanoSeconds = currentTimeInNanoSeconds;
			
			render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000)
				frame.setTitle("2048" + updates + "updates, " + frames + "frames");
				updates =0;
				frames=0;
				timer +=1000;
		}
	}
	
	public void update()
	{
		game.update();
		key.update();
	}
	
	
	public void render() 
	{
		BufferStrategy bs = getBufferStrategy();
		if(bs == null)
		{
			createBufferStrategy(3);
			return;
		}
		
		game.render();																		//Oyun alaninin render islemi
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		g.drawImage(image, 0, 0, (int) (WIDTH*scale) , (int)(HEIGHT*scale),null);			//Oyun alaninin Render ederken olceklendirmesi
		game.renderText(g);																	//Ekran uzerinde sayilari render et(Goster)
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Main m = new Main();									//Main Icin Frame'i Nesnesini Olusturuyoruz.
		ImageIcon img = new ImageIcon("D:\\Java_Eclipse_Workspace\\2048Game\\icon\\JFrameIcon.png");
		m.frame.setIconImage(img.getImage());					//JFrame Icon
		
		JLabel label = new JLabel("<html>2048 GAME RULE<br><br>"
				+ "- Different values cannot be added together. Values can be summed when the samevalues are superimposed.<br><br>"
				+ "- The game is single player.<br><br>"
				+ "- The playing field consists of a 4 by 4 squarearea.<br><br>"
				+ "- The game ends when there is no value tomove and no value to collect.<br><br>"
				+ "- You can direct the playing field with the w, a,s, d keys on the keyboard.<br><br>"
				+ "- Press r to restart the game.<br><br>"
				+ "- As a result of each move, new values areadded to the playing field. These values canbe 2 or 4.<br><br>"
				+ "HAVE FUN!<br>SEE YOU LATER...</html>",SwingConstants.CENTER);
		label.setBounds(820, 30, 260, 400);
	    m.frame.add(label);
		label.setVisible(true);

		m.frame.setResizable(false);							
		m.frame.setTitle("2048 Game");							
		m.frame.add(m);											//Form bilesenlerini Frame'e dahil et.
		m.frame.pack();											//Alt bilesen iceriklerine gore boyutlandirma ile pencereyi ayarla.
		m.frame.setVisible(true);								
		m.frame.setLocationRelativeTo(null);					//Null ile temel ayar ile ekranin ortasinda goruntule
		m.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		m.frame.setAlwaysOnTop(true);							//Form her zaman en ustte kalsin.
		m.start();
	}	
}