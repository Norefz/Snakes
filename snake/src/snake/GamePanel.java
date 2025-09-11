/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package snake;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.PrintWriter;
import javax.swing.*;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author erlan
 */
// audio support



public class GamePanel extends JPanel implements ActionListener{
                   static final int SCREEN_WIDTH = 600;
                   static final int SCREEN_HEIGHT = 600;
                   static final int UNIT_SIZE  = 25;
                   static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
	static final int DELAY = 75;
	final int x[] = new int [GAME_UNITS];
	final int y[] = new int [GAME_UNITS];
	int bodyParts = 6;
	int applesEaten;
	int appleX;
	int appleY;
	int highscore = 0;
	char direction ='R';
	boolean running=false;
//	Image
	Image backgroundimage;
	Image gameOverImage;
//	Sound
	SoundPlayer bgm  = new SoundPlayer();
	SoundPlayer sfx = new SoundPlayer();
	SoundPlayer OverGame = new SoundPlayer();
//	boolean gameOver = false;
	Timer timer;
	Random random;
	
	GamePanel() {
		random = new Random();
		this.setPreferredSize(new  Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
//		this.setBackground(Color.red);
                                         backgroundimage = new ImageIcon(getClass().getResource("/Pictures/letsgoo.jpg")).getImage();
		this.setFocusable(true);
		this.addKeyListener( new myKeyAdapter());
		startGame();
	}
                 public void  startGame(){
		newApple();
		running = true;
		timer = new Timer(DELAY, this);
		timer.start();
		//BGM
                                         bgm.play("/sound/Dababy .wav",true);
		OverGame.stop();
//		gameOver = false;
                                        repaint();
		loadHighScore();
			 
		

		 }
		 private void loadHighScore() {
        try (Scanner sc = new Scanner(new File("highscore.txt"))) {
            if (sc.hasNextInt()) {
                highscore = sc.nextInt();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		if (running) {
			  g.drawImage(backgroundimage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
			draw(g);
			
		}
		else{
                            		  gameOver(g);
	                     g.drawImage(gameOverImage, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, this);
		 g.setColor(Color.red);
		g.setFont( new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics = getFontMetrics(getFont());
		g.drawString("OSHIMAI",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/4-5, SCREEN_HEIGHT/4+30);
		g.setFont( new Font("Ink Free",Font.BOLD,35));
		g.drawString("Press Start Space To Reset",(SCREEN_WIDTH - metrics.stringWidth("Game Over"))/7, SCREEN_HEIGHT*2/3);
		    g.drawString("Highest Score: " +  highscore, (SCREEN_WIDTH - metrics.stringWidth("Highest Score : " + highscore))/SCREEN_HEIGHT*3/3,g.getFont().getSize());
			 
		}
		
	}
	public void draw(Graphics g){
//		GRID UNIT 
                                  if (running) {
			for (int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++) {
//			g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
//			g.drawLine(0, i*UNIT_SIZE,SCREEN_WIDTH,  i*UNIT_SIZE);
			
			g.setColor(Color.yellow);
		                   g.setFont( new Font("Ink Free",Font.BOLD,55));
			FontMetrics metrics = getFontMetrics(g.getFont());
                                                         g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());
		       
                                       
                                                  
		}
//		APPLE COLOR AND BODDY
		g.setColor(Color.RED);
		g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);
//                                         g.drawRoundRect(appleX, appleY, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE, UNIT_SIZE);
//                                          SNake Body
                                           for (int i = 0; i < bodyParts; i++) {				
	                              if (i ==0) {		   
				           g.setColor(Color.green);
		                                               g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				}
				      else{
					      g.setColor(new Color(45,180,0 ));
					      g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
					      g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
				      }
			
		}
					  
		} else{
	                     gameOver(g);
			
			  
		   }
		
	}
	public  void move(){
		for (int i = bodyParts; i>0;  i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		switch(direction){
			case 'U':
				y[0] = y[0] - UNIT_SIZE;
				break;
			case 'D':
				y[0] = y[0] + UNIT_SIZE;
				break;
			case 'L':
				x[0] = x[0] - UNIT_SIZE;
				break;
			case 'R':
				x[0] = x[0] + UNIT_SIZE;
				break;
		}
	}
	public void newApple(){
		appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
		appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
		
		
	}
	public  void checkApple(){
		if ((x[0] == appleX)&&(y[0]== appleY)) {
			bodyParts++;
			applesEaten++;
			newApple();
			  sfx.play("/sound/clash.wav",false);
	}
		}
	public void checkCollision(){
		//check if head collidised with boddy
		for (int i = bodyParts; i < 0 ; i--) {
			if ((x[0] == x[i])&&(y[0] == y[i])) {
				running = false;
			}
		}
		//check if head touch left border
		if (x[0] < 0) {
			running = false;
		}
		// check if head touch right border
		if (x[0] > SCREEN_WIDTH) {
			running = false;
		}
		// check if head touch top border
		if (y[0] < 0) {
			running = false;
		}
		// check if head touch bottom border
		if (y[0] > SCREEN_HEIGHT) {
			running = false;
		}
		if (!running) {
		timer.stop();
		}

	}
	public void  gameOver(Graphics g){
		//Game OVER TEXT
		 bgm.stop();
		  gameOverImage = new ImageIcon(getClass().getResource("/Pictures/images.jpg")).getImage();
//		  sound
		  OverGame.play("/sound/sad.wav",false);     
//		gameOver = true;
                                            if (applesEaten > highscore) {
			highscore = applesEaten;
			HighScoreSave();
		}
		
	}
	private  void HighScoreSave(){
		try(PrintWriter out = new PrintWriter("highscore.txt") ){
	                      out.println(highscore);
                             } 
                        catch(Exception Error){
	Error.printStackTrace();
                       }

	}
	public void GameReset(){
		applesEaten=0;
		bodyParts = 6;
		direction = 'R';
		running = true;
		newApple();
		
		repaint();
		startGame();
		    for (int i = 0; i < bodyParts; i++) {
        x[i] = 50 - i * UNIT_SIZE;
        y[i] = 50;
    }
//		    Timer Delay
		if (timer != null) {
                            timer.stop();
                                      }
		timer = new Timer(DELAY, this);
		timer.start();
 
	}
	
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (running) {
			move();
			checkApple();
			checkCollision();
			
		}
		repaint();
	}
	public class myKeyAdapter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e){
			if (!running  && e.getKeyCode() == KeyEvent.VK_SPACE) {
				GameReset();
				
				
			}
			switch(e.getKeyCode()){
//				Arrow Keys
				case KeyEvent.VK_LEFT:
					if (direction != 'R') {
					    direction = 'L';
					}
					break;
				case KeyEvent.VK_RIGHT:
					if (direction != 'L') {
					    direction = 'R';
					}
					
					break;	
				case KeyEvent.VK_UP:
					if (direction != 'D') {
					    direction = 'U';
					}
					break;
				case KeyEvent.VK_DOWN:
					if (direction != 'U') {
					    direction = 'D';
					}
					break;
//					WASD
				case KeyEvent.VK_W:
					if (direction !='D') {
						direction = 'U';
					}
					break;
					case KeyEvent.VK_D:
					if (direction != 'L') {
					    direction = 'R';
					}
					break;
					case KeyEvent.VK_A:
					if (direction != 'R') {
					    direction = 'L';
					}
					break;
					case KeyEvent.VK_S:
					if (direction != 'U') {
					    direction = 'D';
					}
					break;
					
			}

		}
	}
		
	
	
	
}
