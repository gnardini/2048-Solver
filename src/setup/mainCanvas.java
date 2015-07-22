package setup;

import game.Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.Timer;

public class mainCanvas extends Canvas implements KeyListener, myConstants{

	
	public static final int SIZE=4;
	private static final long serialVersionUID = 1L;
	private boolean repaintInProgress = false;
	private int[][] board;
	private Game ga;
	private Map<Integer,Image> c;
	
	mainCanvas(JFrame pane) {
		setIgnoreRepaint(true);
		addKeyListener(this);
		
		board=new int[SIZE][SIZE];
		for(int i=0 ; i<SIZE ; i++)
			for(int j=0 ; j<SIZE ; j++)
				board[i][j]=0;
		ga=new Game(board);
		ga.start();
		
		c=new HashMap<Integer,Image>();
		initColor();
		mainChrono chrono = new mainChrono(this);
		new Timer(16, chrono).start();
	}
	
	public void myRepaint() {
		if(repaintInProgress)
			return;
		repaintInProgress = true;
		BufferStrategy strategy = getBufferStrategy();
		Graphics g = strategy.getDrawGraphics();
		g.setColor(new Color(215,215,215));
		g.fillRect(0, 0, 25+10*3+ESCALA*4, 60+10*3+ESCALA*4);
		g.setColor(Color.WHITE);
		for(int i=0 ; i<4; i++)
			for(int j=0 ; j<4; j++){
				g.fillRect(10+10*i+ESCALA*i,25+10*j+ESCALA*j , ESCALA, ESCALA);
			}
		g.setColor(Color.BLACK);
		g.drawString("Points: " + ga.getPoints() + "   Highscore: " + ga.getHighScore(), 50, 15);
		g.drawString("Games Played: " + ga.getTotal() + "   Max: " + ga.getmaxTile(), 350, 15);
		for(int i=0 ; i<4; i++)
			for(int j=0 ; j<4; j++)
				if(board[i][j]!=0){
					g.drawImage(c.get(board[i][j]),10+10*i+i*ESCALA, 25+10*j+j*ESCALA,null);
				}	
		
		strategy.show();
		Toolkit.getDefaultToolkit().sync();
		repaintInProgress = false;
	}
	
	
	@Override
	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		if(key==KeyEvent.VK_DOWN) ga.b.moveDown();
		else if(key==KeyEvent.VK_UP) ga.b.moveUp();
		else if(key==KeyEvent.VK_RIGHT) ga.b.moveRight();
		else if(key==KeyEvent.VK_LEFT) ga.b.moveLeft();
		else if(key==KeyEvent.VK_Z) ga.moveOrdered();
		
		if(key==KeyEvent.VK_SPACE && ga.isOver()) ga.resetGame();
	}

	private void initColor(){
		try{
		c.put(2,loadImage("images/2.png"));
		c.put(4,loadImage("images/4.png"));
		c.put(8,loadImage("images/8.png"));
		c.put(16,loadImage("images/16.png"));
		c.put(32,loadImage("images/32.png"));
		c.put(64,loadImage("images/64.png"));
		c.put(128,loadImage("images/128.png"));
		c.put(256,loadImage("images/256.png"));
		c.put(512,loadImage("images/512.png"));
		c.put(1024,loadImage("images/1024.png"));
		c.put(2048,loadImage("images/2048.png"));
		c.put(4096,loadImage("images/red hair.png"));
		}catch(IOException e){}
	}
	
	public static Image loadImage(String fileName) throws IOException {
		InputStream stream = ClassLoader.getSystemResourceAsStream(fileName);
		if (stream == null) {	
			return ImageIO.read(new File(fileName)); 
		} else {
			return ImageIO.read(stream);
		}
	}
	
	@Override public void keyReleased(KeyEvent e) {}
	@Override public void keyTyped(KeyEvent e) {}
}

