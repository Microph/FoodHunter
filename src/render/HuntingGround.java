package render;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.swing.JComponent;
import character.Entity;
import character.Player;
import logic.GameManager;
import logic.GameScreen;
import logic.Utility;

@SuppressWarnings("serial")
public class HuntingGround extends JComponent{

	private static HuntingGround instance = new HuntingGround();
	
	private static final int TIMER = 1500;
	private int timeCounter;
	
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	
	public static final int NORTH = 0;
	public static final int WEST = 1;
	public static final int SOUTH = 2;
	public static final int EAST = 3;
	
	private List<Entity> objectOnScreen;
	
	public static int numberOfItem;
	
	private HuntingGround(){
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		setDoubleBuffered(true);
		setFocusable(true);
		
		addKeyListener(new KeyListener(){

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				Utility.setKeyPressed(e.getKeyCode(), true);
				
				if(GameManager.getInstance().isPaused() && timeCounter > 0){
					if(Utility.getKeyPressed(KeyEvent.VK_ENTER)){
						GameManager.getInstance().setPaused(false);
						synchronized(Utility.class){
							Utility.class.notifyAll();
						}
					}
					else if(Utility.getKeyPressed(KeyEvent.VK_ESCAPE)){
						GameScreen.getInstance().backToTitle();
						GameManager.getInstance().gameOver();
						GameManager.getInstance().setPaused(false);
					}
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				Utility.setKeyPressed(e.getKeyCode(), false);
			}
			
		});
		
		addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				Utility.setMouseLeftClicked(true);
				Utility.setMouseLeftTriggered(true);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				Utility.setMouseLeftClicked(false);
				Utility.setMouseLeftTriggered(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!GameManager.getInstance().isPaused() && timeCounter > 0 && Player.getInstance().getHp() > 0){
					GameManager.getInstance().setPaused(true);
				}
			}
			
		});
		
		addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				try{
					Point p = getMousePosition();
					Utility.setMouseX(p.getX());
					Utility.setMouseY(p.getY());
				}
				catch (NullPointerException r2d2){
				}
			}
			
		});
		
		objectOnScreen = new ArrayList<Entity>();
		numberOfItem = 0;
	}
	
	

	public synchronized List<Entity> getObjectOnScreen() {
		return objectOnScreen;
	}

	public synchronized void spawn(Entity object){
		objectOnScreen.add(object);
		
		objectOnScreen.sort(new Comparator<IRender>(){

			@Override
			public int compare(IRender o1, IRender o2) {
				if(o1.getZ() > o2.getZ()) return 1;
				return -1;
			}
			
		});
	}
	
	public synchronized void updateObjectOnScreen(){
		
		for(int i = objectOnScreen.size() - 1; i >= 0; i--){
			if(!objectOnScreen.get(i).isVisible()) objectOnScreen.remove(i);
		}
		for(Entity entity : objectOnScreen){
			entity.update();
		}
		timeCounter--;
	}
	
	public void reset(){
		if(GameManager.SPAWN_TIME > 30)
			GameManager.SPAWN_TIME -= 5;
		timeCounter = TIMER;
		objectOnScreen.clear();
		numberOfItem = 0;
		Player.getInstance().setToDefault();
		spawn(Player.getInstance());
	}
	
	public int getTimeCounter() {
		return timeCounter;
	}

	public static HuntingGround getInstance(){
		return instance;
	}
	
	//Render
	@Override
	protected synchronized void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Resource.getImage(22), null, 0, 0);
		
		for(Entity entity : objectOnScreen){
			entity.draw(g2d);
		}
		
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Tahoma",Font.BOLD,30));
		g2d.drawString(timeCounter / 50 + "", HuntingGround.WIDTH / 2, 50);
		g2d.drawImage(Resource.getImage(21), null, HuntingGround.WIDTH / 2 + 40, 20);
		
		for(int i = 0; i < Player.getInstance().getHp(); i++){
			g2d.drawImage(Resource.getImage(0), null, 20 + 64 * i, 10);
		}
		
		if(timeCounter <= 0){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g2d.drawImage(Resource.getImage(19), null, HuntingGround.WIDTH / 2 - 191, HuntingGround.HEIGHT / 2 - 62);
		}
		else if(Player.getInstance().getHp() <= 0){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g2d.drawImage(Resource.getImage(20), null, HuntingGround.WIDTH / 2 - 113, HuntingGround.HEIGHT / 2 - 20);
		}
		else if(GameManager.getInstance().isPaused()){
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, WIDTH, HEIGHT);
			g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
			g2d.drawImage(Resource.getImage(18), null, HuntingGround.WIDTH / 2 - 160, HuntingGround.HEIGHT / 2 - 125);
		}
			
	}
	
}
