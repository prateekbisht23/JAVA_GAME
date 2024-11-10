package com.prateekbisht.gaming.sprites;

import javax.swing.ImageIcon;

public class Player extends Sprite{
	
	public Player() {
		w = 200;
		h = 200;
		x = 60;
		y = 450;
		image = new ImageIcon(Player.class.getResource("player.gif"));
	}
	
	public void move() {
		x = x + speed;
	}
	
	public boolean outOfScreen() {
		return x > 1300;
	}
	
	public void resetPosition() {
	    setX(60);
	    setY(450);
	}

	
}
