package com.prateekbisht.gaming.sprites;

import java.awt.Graphics;

import javax.swing.ImageIcon;

public abstract class Sprite {
	int speed;
	public int getSpeed() {
		return speed;
	}
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	int x;
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	
	int y;
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	int w;
	public int getW() {
		return w;
	}
	public void setW(int w) {
		this.w = w;
	}
	
	int h;
	public int getH() {
		return h;
	}
	public void setH(int h) {
		this.h = h;
	}
	
	ImageIcon image;
	
	public void draw(Graphics pen){
		pen.drawImage(image.getImage(), x, y, w, h, null);
	}
	
}
