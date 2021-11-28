package main2048.graphics;

//Bu Class Olusturulan Her Bir Kare Alan Icin Siyah Bir Dis Cerceve Ekler.
public class Sprite {

	public int width, height;
	public int[] pixels;
	
	public Sprite(int width, int height, int color) 
	{
		this.width = width;
		this.height = height;
		this.pixels = new int[width * height];
		
		//Kare Alanlar Icin Olcut Ve Renk Atamasi Yapildiktan Sonra Gorsel Olarak Islenmesi - GameObject Class'i Ile Bagintili
		for(int y = 0; y < height; y++) 
			for(int x = 0; x < width; x++) 
			{	
				pixels[x + y * width] = color;		
				if(x % 100 < 3 || x % 100 > 97 || y % 100 < 3 || y % 100 > 97) {
					pixels[x + y * width] = 0x000000;		//Her Bir Sayi Ve Karesi Icin Cerceve
				}
			}
	}
}