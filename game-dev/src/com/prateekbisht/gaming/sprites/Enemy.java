package com.prateekbisht.gaming.sprites;

import javax.swing.ImageIcon;

public class Enemy extends Sprite{
	
	public Enemy(int x, int speed) {
		w = 150;
		h = 150;
		this.x = x;
		this.speed = speed;
		y = 50;
		image = new ImageIcon(Player.class.getResource("spider.gif"));
	}
	
	public void move() {
		if(y > 820) {
			y = 0;
		}
		y = y + speed;
	}
	
	public void resetPosition() {
	    setX(x);
	    setY(y);
	}
	
}
