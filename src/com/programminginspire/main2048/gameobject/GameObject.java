package com.programminginspire.main2048.gameobject;

import java.util.Random;

import com.programminginspire.main2048.Main;
import com.programminginspire.main2048.game.Game;
import com.programminginspire.main2048.graphics.Renderer;
import com.programminginspire.main2048.graphics.Sprite;

public class GameObject {

	public double x, y;
	public int width, height;
	public Sprite sprite;			//Sprite Class'i Icerisinden Bir Nesne Olusturuyoruz.
	public int value, speed = 8;
	public boolean moving = false, remove = false, hasMoved = false;
	
	Random rand = new Random();
	
	public GameObject(double x, double y) {
		this.x = x;
		this.y = y;
		this.value = (rand.nextBoolean() ? 2 : 4);	//2 ve 4 Degerleri Icerisinden Birini Rastegele Olarak Value Degeri Icerisine Atiyoruz
		createSprite();								//Cikan ve olusan degerlere gore ekranda kare alan olusmasi ve rengini Sprite Class'i ile sagliyoruz.
		this.width = sprite.width;
		this.height = sprite.height;
	}
	
	//Deger ile beraber renk ve nesne olusumu.
	public void createSprite() {
		switch(this.value)
		{
			case 2:  	this.sprite = new Sprite(100, 100, 0xefe5db); 	break;
			case 4:  	this.sprite = new Sprite(100, 100, 0xece0c8); 	break;
			case 8:  	this.sprite = new Sprite(100, 100, 0xf1b078); 	break;
			case 16: 	this.sprite = new Sprite(100, 100, 0xEB8C52); 	break;
			case 32: 	this.sprite = new Sprite(100, 100, 0xF57C5F); 	break;
			case 64: 	this.sprite = new Sprite(100, 100, 0xEC563D);	break;
			case 128:	this.sprite = new Sprite(100, 100, 0xF2D86A);	break;
			case 256:	this.sprite = new Sprite(100, 100, 0xECC750);	break;
			case 512:	this.sprite = new Sprite(100, 100, 0xE5BF2D);	break;
			case 1024:	this.sprite = new Sprite(100, 100, 0xE2B913);	break;
			case 2048: 	this.sprite = new Sprite(100, 100, 0xEDC22E);	break;
			case 4096:	this.sprite = new Sprite(100, 100, 0x5DDB92);	break;
			case 8192:	this.sprite = new Sprite(100, 100, 0xEC4D58);	break;
		}
	}

	//Ekran sinirlari icerisinde kal. Degilse hamle gerceklestir. (Hareket edebilirsin.)
	public boolean canMove() {
		if(x < 0 || x + width > Main.WIDTH || y < 0 || y + height > Main.HEIGHT)
			return false;
		for(int i = 0; i < Game.objects.size(); i++) 
		{
			GameObject o = Game.objects.get(i);
			if(this == o) continue;
			if(x + width > o.x && x < o.x + o.width && y + height > o.y && y < o.y + o.height && value != o.value)
				return false;
		}
		return true;
	}
	
	
	public void update() {
		if(Game.moving) {
			if(!hasMoved) hasMoved = true;
			if(canMove()) moving = true;		
			if(moving) {
				if(Game.dir == 0) x -= speed;
				if(Game.dir == 1) x += speed;
				if(Game.dir == 2) y -= speed;
				if(Game.dir == 3) y += speed;
			}
			if(!canMove()) {
				moving = false;
				x = Math.round(x / 100) * 100;
				y = Math.round(y / 100) * 100;
			}
		}	
	}
	
	public void render() {
		Renderer.renderSprite(sprite, (int) x, (int) y);
	}
}
