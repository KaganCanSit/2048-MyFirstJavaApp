package main2048.game;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import main2048.Main;
import main2048.gameobject.GameObject;
import main2048.graphics.Renderer;
import main2048.input.Keyboard;

public class Game {

	public static List<GameObject> objects;
	
	public static boolean moving = false, hasMoved = true, somethingIsMoving = false;
	public static int dir = 0;
	
	private Random rand = new Random();
	
	public Game() {
		init();
	}

	public void init() {
		objects = new ArrayList<GameObject>();
		moving = false;
		hasMoved = true;
		somethingIsMoving = false;
		spawn();
	}

	public void update() 
	{
		if(Keyboard.keyUp(KeyEvent.VK_R))
			init();
		
		for(int i = 0; i < objects.size(); i++)
			objects.get(i).update();
		
		checkForValueIncrease();		
		movingLogic();
	}
	
	//Degerlerin Islenmesi Ve Kontrolu
	private void checkForValueIncrease() 
	{
		for(int i = 0; i < objects.size(); i++) 
			for(int j = 0; j < objects.size(); j++)
			{
				if(i == j) continue;
				if(objects.get(i).x == objects.get(j).x && objects.get(i).y == objects.get(j).y && !objects.get(i).remove && !objects.get(j).remove) {
					objects.get(j).remove = true;
					objects.get(i).value *= 2;			//i ve j Objelerinin Degerlerini Carpar(value*2=4 / value*4=8 gibi)
					objects.get(i).createSprite();		//Yeni Olusan Deger Ile Birlikte Gorunumunu Degistiriyoruz. Bu Islemi Gerceklesmezsek Her Zaman Ayni Renk Ile Kalir.
				}
			}
		//Sayisal Deger Degisimi Gerceklestikten Sonra Alttakini Remove Et
		for(int i = 0; i < objects.size(); i++)
			if(objects.get(i).remove) objects.remove(i);

		System.out.println(objects.size());
	}

	//Tahtaya Hamle Basina Yeni Kutu Ekleme
	private void spawn() 
	{
		if(objects.size() == 16) return;
		
		boolean available = false;
		int x = 0, y = 0;
		while(!available) 
		{
			//Yeni Kutunun Tahta Uzerindeki Rastgele Konumu 
			x = rand.nextInt(4);
			y = rand.nextInt(4);
			boolean isAvailable = true;
			
			//Yeni Gelecek Olan Kutunun Diger Kutular Ile Kontrolu (Eger Varse = false / Uretme)
			for(int i = 0 ; i < objects.size(); i++)
				if(objects.get(i).x / 100 == x && objects.get(i).y / 100 == y) 
					isAvailable = false;
			
			if(isAvailable) 
				available = true;
		}
		objects.add(new GameObject(x * 100, y * 100));
	}

	private void movingLogic() 
	{
		somethingIsMoving = false;
		for(int i = 0; i < objects.size(); i++)
			if(objects.get(i).moving)
				somethingIsMoving = true;
		
		if(!somethingIsMoving) 
		{
			moving = false;
			for(int i = 0; i < objects.size(); i++)
				objects.get(i).hasMoved = false;
		}
		if(!moving && hasMoved)
		{
			spawn();
			hasMoved = false;
		}
		if(!moving && !hasMoved) 
		{
			if(Keyboard.keyDown(KeyEvent.VK_A)) 
				KeyboardFunction(true,true,0);
			else if(Keyboard.keyDown(KeyEvent.VK_D)) 
				KeyboardFunction(true,true,1);
			else if(Keyboard.keyDown(KeyEvent.VK_W))
				KeyboardFunction(true,true,2);
			else if(Keyboard.keyDown(KeyEvent.VK_S)) 
				KeyboardFunction(true,true,3);
		}
	}
	
	public void KeyboardFunction(boolean d1, boolean d2, int d3)
	{
		hasMoved = d1;
		moving = d2;
		dir = d3;
	}
	
	public void render() 
	{
		Renderer.renderBackground(); 					//>Arkaplanin Yenilenmesi Ve Olusturulmasi
		for(int i = 0; i < objects.size(); i++)
			objects.get(i).render();					//Objelerin Olusturulmasi
		for(int i = 0 ; i < Main.pixels.length; i++)
			Main.pixels[i] = Renderer.pixels[i];
	}
	
	//Yazi Degerlerinin Basimi Icin Ayarlar
	public void renderText(Graphics2D g) 
	{
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setFont(new Font("Verdana", 0, 80));
		g.setColor(Color.BLACK);
		
		for(int i = 0; i < objects.size(); i++) 
		{
			GameObject o = objects.get(i);
			String s = o.value + "";
			int sw = (int) (g.getFontMetrics().stringWidth(s) / 2 / Main.scale);
			g.drawString(s, (int) (o.x + o.width / 2 - sw) * Main.scale, (int) (o.y + o.height / 2 + 18) * Main.scale);
		}		
	}	
}