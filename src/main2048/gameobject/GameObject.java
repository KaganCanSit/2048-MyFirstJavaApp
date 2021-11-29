package main2048.gameobject;

import java.util.Random;

import main2048.Main;
import main2048.game.Game;
import main2048.graphics.Renderer;
import main2048.graphics.Sprite;

public class GameObject {
	public double x, y;
	public int width, height;
	public Sprite sprite;			//Sprite Class'i Icerisinden Bir Nesne Olusturuyoruz.
	public int value, speed = 10;
	public boolean moving = false, remove = false, hasMoved = false;
	
	Random rand = new Random();
	
	public GameObject(double x, double y) 
	{
		this.x = x;
		this.y = y;
		this.value = (rand.nextBoolean() ? 2 : 4);	//2 ve 4 Degerleri Icerisinden Birini Rastgele Olarak Value Degeri Icerisine Atiyoruz
		createSprite();								//Cikan Ve Olusan Degerlere Gore Ekranda Kare Alan Olusmasini Ve Rengini Sprite Class'i Ile Sagliyoruz.
		this.width = sprite.width;
		this.height = sprite.height;
	}
	
	//Deger Ile Beraber Renk Ve Nesne Olusumu.
	public void createSprite() {
		int[] colorCode= {0xefe5db, 0xece0c8, 0xf1b078, 0xEB8C52, 0xF57C5F, 0xEC563D, 0xF2D86A, 0xECC750, 0xE5BF2D, 0xE2B913, 0xEDC22E, 0x5DDB92, 0xEC4D58};
		switch(this.value)
		{
			case 2:  	MinimalSprite(colorCode[0]); 	break;
			case 4:  	MinimalSprite(colorCode[1]); 	break;
			case 8:  	MinimalSprite(colorCode[2]); 	break;
			case 16: 	MinimalSprite(colorCode[3]); 	break;
			case 32: 	MinimalSprite(colorCode[4]); 	break;
			case 64: 	MinimalSprite(colorCode[5]);	break;
			case 128:	MinimalSprite(colorCode[6]);	break;
			case 256:	MinimalSprite(colorCode[7]);	break;
			case 512:	MinimalSprite(colorCode[8]);	break;
			case 1024:	MinimalSprite(colorCode[9]);	break;
			case 2048: 	MinimalSprite(colorCode[10]);	break;
			case 4096:	MinimalSprite(colorCode[11]);	break;
			case 8192:	MinimalSprite(colorCode[12]);	break;
		}
	}	
	public void MinimalSprite(int colorCode) {
		this.sprite = new Sprite(100,100,colorCode);
	}
	
	//Ekran Sinirlari Icerisinde Kal. Sinirlar Icerisinde Ise Hareket Edebilirsin.
	public boolean canMove() 
	{	
		if(x < 0 || x + width > Main.WIDTH || y < 0 || y + height > Main.HEIGHT)
			return false;
		
		//Degerlerin Kontrolu 2/2 - 4/4 - 8/8
		for(int i = 0; i < Game.objects.size(); i++) 
		{
			GameObject o = Game.objects.get(i);
			if(x + width > o.x && x < o.x + o.width && y + height > o.y && y < o.y + o.height && value != o.value) return false;
		}
		return true;
	}
	
	
	public void update() 
	{
		if(Game.moving) 
		{
			if(!hasMoved) hasMoved = true;
			if(canMove()) moving = true;
			//x-y Duzleminde Hareket. "Dir" Degerlerini game Class'indan Aliyoruz.
			if(moving) 
			{
				if(Game.dir == 0) x -= speed;
				if(Game.dir == 1) x += speed;
				if(Game.dir == 2) y -= speed;
				if(Game.dir == 3) y += speed;
			}
			if(!canMove()) 
			{
				moving = false;
				x = Math.round(x / 100) * 100;
				y = Math.round(y / 100) * 100;
			}				
		}
	}
	
	//Olusan Kare Alan Ve Ayarlarini Renderer Class'i Yardimiyla Ekrana Basiyoruz.
	public void render()
	{
		Renderer.renderSprite(sprite, (int) x, (int) y);
	}
}