package logic;

import javax.swing.JOptionPane;

import character.Player;
import character.pig.ChickenPig;
import character.pig.PigPocket;
import character.pig.RedPig;
import render.CookingScreen;
import render.HuntingGround;
import render.Resource;
import render.WestPanel;

public class GameManager {
	
	private static GameManager instance = new GameManager();
	
	public static int SERVE_COUNT = 0;
	public static int SPAWN_TIME = 80; //default = 80
	public int spawnCounter = 0;

	Thread renderThread;
	Thread logicThread;
	
	Player player;
	
	private boolean paused;

	private GameManager(){
		player = Player.getInstance();
		
		renderThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					HuntingGround.getInstance().repaint();
					if(HuntingGround.getInstance().getTimeCounter() <= 0){
						synchronized(Utility.class){
							try {
								Utility.class.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				}
			}
			
		});
		
		logicThread = new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(true){
					try {
						Thread.sleep(20);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					if(spawnCounter >= SPAWN_TIME){
						new RedPig(player);
						if(Utility.chance(40))
							new ChickenPig(player);
						if(HuntingGround.numberOfItem >= 3){
							if(Utility.chance(40))
								new PigPocket(player);
						}
						spawnCounter = 0;
					}
					else
						spawnCounter++;
					HuntingGround.getInstance().updateObjectOnScreen();
					if(HuntingGround.getInstance().getTimeCounter() <= 0){
						try {
							Resource.stopBGM();
							Resource.playSound(10);
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						Thread t = new Thread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								CookingScreen.setBG(61);
								WestPanel.loadDisplay();
							}
						});
						t.start();
						try {
							t.join();
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						GameScreen.getInstance().goToCookingScreen();
						
						synchronized(Utility.class){
							try {
								Utility.class.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else if(player.getHp() <= 0){
						try {
							Resource.stopBGM();
							Resource.playSound(11);
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						JOptionPane.showConfirmDialog(null, "You lived to serve " +SERVE_COUNT+ " plate(s) so far.", "Game Over", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
						SERVE_COUNT = 0;
						GameScreen.getInstance().backToTitle();
						GameManager.getInstance().gameOver();
						synchronized(Utility.class){
							try {
								Utility.class.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else if(paused){
						synchronized(Utility.class){
							try {
								Utility.class.wait();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					Utility.postUpdate();
				}
			}
			
		});
		
		renderThread.start();
		logicThread.start();
	}
	
	public void startGame(){
		HuntingGround.getInstance().reset();
		synchronized(Utility.class){
			Utility.class.notifyAll();
		}
	}
	
	public void gameOver(){
		player.heal(9001);
		Share.getInstance().resetGun();
		Share.getInstance().resetItem();
		SPAWN_TIME = 80;
	}
	
	public static GameManager getInstance(){
		return instance;
	}

	public boolean isPaused() {
		return paused;
	}

	public void setPaused(boolean paused) {
		this.paused = paused;
	}
}
