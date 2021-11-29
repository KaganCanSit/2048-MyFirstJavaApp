package main2048.graphics;

//Bu Class Olusturulan Her Bir Kare Alan Icin Gorsel Olarak Isler ve Bir Dis Cerceve Ekler.
public class Sprite {

	public int width, height;
	public int[] pixels;
	
	public Sprite(int width, int height, int color) 	//GameObject Class'i Ile Bagintili
	{
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		for(int y = 0; y < height; y++) 
			for(int x = 0; x < width; x++) 
			{	
				pixels[x + y * width] = color;		//Objelerin Gorsel Olarak Olusturulmasi		
				if(x % 100 < 2 || x % 100 > 98 || y % 100 < 2 || y % 100 > 98) {
					pixels[x + y * width] = 0x2C3E50;		//Objelerin Cercevelerinin Olusturulmasi
				}
			}
	}
}