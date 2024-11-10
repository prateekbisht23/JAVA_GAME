package com.prateekbisht.gaming;

import javax.swing.JFrame;

public class GameFrame extends JFrame {

	public GameFrame(){
		Board board = new Board();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Game Dev In Java");
		setResizable(false);
		setSize(1400,800);
		setLocationRelativeTo(null);
		add(board);
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new GameFrame();
		

	}

}
