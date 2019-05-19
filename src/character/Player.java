package character;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;

import character.bullet.PistolBullet;
import character.bullet.RifleBullet;
import character.bullet.Rocket;
import character.bullet.ShotgunBullet;
import logic.Share;
import logic.Utility;
import render.HuntingGround;
import render.IRender;
import render.Resource;

public class Player extends MovableEntity implements IRender{
	
	private static Player instance = new Player();
	
	private final int INVICIBLE_TIME = 50;
	private int invicibleCounter;
	
	private int gunType;
	private Point[][] gunArrange;
	private final int[] SHOOT_DELAY = new int[]{
			5,25,25,15
	};
	private int shootCounter;
	
	private int walkFrame;
	private int walkDirection;
	private final int DELAY_PER_FRAME = 8;
	private int walkCounter = 0;
	
	protected int hp;
	protected boolean moving;
	
	private Player(){
		super(HuntingGround.WIDTH/2, HuntingGround.HEIGHT/2, 30, 2, 2);
		hp = 5;
		z = Integer.MAX_VALUE;
		
		gunType = 0;
		gunArrange = new Point[][]{
			{new Point(70,-12), new Point(-70,-12)},
			{new Point(50,-25), new Point(-50,-25)},
			{new Point(60,-20), new Point(-60,-20)},
			{new Point(160,-18),new Point(-160,-18)}
			
		};
		walkFrame = 0;
		walkDirection = 1;
		HuntingGround.getInstance().spawn(this);
	}
	
	protected void changeGun(){
		if(Utility.getKeyPressed(KeyEvent.VK_1))
			gunType = 0;
		else if(Utility.getKeyPressed(KeyEvent.VK_2) && Share.getInstance().hasGun(1))
			gunType = 1;
		else if(Utility.getKeyPressed(KeyEvent.VK_3) && Share.getInstance().hasGun(2))
			gunType = 3;
		else if(Utility.getKeyPressed(KeyEvent.VK_4) && Share.getInstance().hasGun(3)) //swap 3 & 2 for balance issue
			gunType = 2;
	}

	protected void shoot(){
	    if(Utility.getMouseLeftTriggered() && shootCounter >= SHOOT_DELAY[gunType]){
	      new Thread(new Runnable(){

	        @Override
	        public void run() {
	          // TODO Auto-generated method stub
	          double zheta = Math.atan2(Utility.getMouseY() - y - gunArrange[gunType][faceDirection].getY(), Utility.getMouseX() - x - gunArrange[gunType][faceDirection].getX());
	          if((Math.abs(zheta) < Math.PI / 2 && faceDirection == 0) || (Math.abs(zheta) > Math.PI / 2  && faceDirection == 1)){
	            switch(gunType){
	            case 0:
	              Resource.playSound(6);
	              new PistolBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta);
	              break;
	            case 1:
	              new Rocket(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta,Utility.getMouseX(),Utility.getMouseY());
	              moving = false;
	              break;
	            case 2:
	              Resource.playSound(8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta + Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta - Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta + Math.PI / 8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),zheta - Math.PI / 8);
	              break;
	            case 3:
	              new RifleBullet(Utility.getMouseX(),Utility.getMouseY());
	              moving = false;
	              break;
	            }
	          }
	          
	          else if(faceDirection == 0 && Math.abs(zheta) > Math.PI / 2 && zheta < Math.PI && zheta<0){
	            switch(gunType){
	            case 0:
	              Resource.playSound(6);
	              new PistolBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2);
	              break;
	            case 1:
	              new Rocket(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2,Utility.getMouseX()+(getX()-Utility.getMouseX()),Utility.getMouseY());
	              moving = false;
	              break;
	            case 2:
	              Resource.playSound(8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 + Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 - Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 + Math.PI / 8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 - Math.PI / 8);
	              break;
	            case 3:
	              new RifleBullet(Utility.getMouseX(),Utility.getMouseY());
	              moving = false;
	              break;
	            }
	          }
	          
	          else if(faceDirection == 0){
	            switch(gunType){
	            case 0:
	              Resource.playSound(6);
	              new PistolBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2);
	              break;
	            case 1:
	              new Rocket(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2,Utility.getMouseX()+(getX()-Utility.getMouseX()),Utility.getMouseY());
	              moving = false;
	              break;
	            case 2:
	              Resource.playSound(8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 + Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 - Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 + Math.PI / 8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 - Math.PI / 8);
	              break;
	            case 3:
	              new RifleBullet(Utility.getMouseX(),Utility.getMouseY());
	              moving = false;
	              break;
	            }
	          }
	          
	          else if(zheta<0){
	            switch(gunType){
	            case 0:
	              Resource.playSound(6);
	              new PistolBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2);
	              break;
	            case 1:
	              new Rocket(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2,Utility.getMouseX()-(Utility.getMouseX()-getX()),Utility.getMouseY());
	              moving = false;
	              break;
	            case 2:
	              Resource.playSound(8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 + Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 - Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 + Math.PI / 8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),-Math.PI /2 - Math.PI / 8);
	              break;
	            case 3:
	              new RifleBullet(Utility.getMouseX(),Utility.getMouseY());
	              moving = false;  
	              break;
	            }
	          }
	          
	          else{
	            switch(gunType){
	            case 0:
	              Resource.playSound(6);
	              new PistolBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2);
	              break;
	            case 1:
	              new Rocket(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2,Utility.getMouseX()-(Utility.getMouseX()-getX()),Utility.getMouseY());
	              moving = false;
	              break;
	            case 2:
	              Resource.playSound(8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 + Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 - Math.PI / 16);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 + Math.PI / 8);
	              new ShotgunBullet(x + gunArrange[gunType][faceDirection].getX(),y + gunArrange[gunType][faceDirection].getY(),Math.PI /2 - Math.PI / 8);
	              break;
	            case 3:
	              new RifleBullet(Utility.getMouseX(),Utility.getMouseY());
	              moving = false;
	              break;
	            }
	          }
	          
	          shootCounter = 0;
	        }
	      }).start();
	    }
	  }
	
	
	@Override
	protected void move() {
		// TODO Auto-generated method stub
		if(moving){
			if(Utility.getKeyPressed(KeyEvent.VK_S) && Utility.getKeyPressed(KeyEvent.VK_D)){
				x += dx;
				y += dy;
				faceDirection = 0;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_D) && Utility.getKeyPressed(KeyEvent.VK_W)){
				x += dx;
				y -= dy;
				faceDirection = 0;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_W) && Utility.getKeyPressed(KeyEvent.VK_A)){
				x -= dx;
				y -= dy;
				faceDirection = 1;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_A) && Utility.getKeyPressed(KeyEvent.VK_S)){
				x -= dx;
				y += dy;
				faceDirection = 1;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_S)){
				y += dy * 2;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_D)){
				x += dx * 2;
				faceDirection = 0;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_W)){
				y -= dy * 2;
				walkCounter++;
			}
			else if(Utility.getKeyPressed(KeyEvent.VK_A)){
				x -= dx * 2;
				faceDirection = 1;
				walkCounter++;
			}
			if(walkCounter >= DELAY_PER_FRAME){
				walkFrame += walkDirection;
				if(walkFrame == 2 || walkFrame == 0)
					walkDirection = -walkDirection;
				walkCounter = 0;
			}
		}
		else {
			if(shootCounter == SHOOT_DELAY[gunType])
				moving = true;
		}
		
		if(x + radius > HuntingGround.WIDTH)
			x = HuntingGround.WIDTH - radius;
		else if(x - radius < 0)
			x = radius;
		if(y + radius> HuntingGround.HEIGHT)
			y = HuntingGround.HEIGHT - radius;
		else if(y - radius < 0)
			y = radius;
		
	}
	
	public void getHit(int dmg){
		if(invicibleCounter == 0){
			hp -= dmg;
			invicibleCounter = INVICIBLE_TIME;
			dx = 4;
			dy = 4;
		}
	}
	
	public void heal(int value){
		hp += value;
		if(hp > 5) hp = 5;
	}
	
	public void setToDefault(){
		x = HuntingGround.WIDTH / 2;
		y = HuntingGround.HEIGHT / 2;
		gunType = 0;
		shootCounter = 0;
		walkFrame = 0;
		walkDirection = 1;
		walkCounter = 0;
		moving = true;
		invicibleCounter = 0;
		dx = 2;
		dy = 2;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(visible){
			move();
			shoot();
			changeGun();
			shootCounter++;
			if(invicibleCounter > 0){
				invicibleCounter--;
				if(invicibleCounter == 0){
					dx = 2;
					dy = 2;
				}
			}
		}
	}

	public int getHp() {
		return hp;
	}

	public static Player getInstance() {
		return instance;
	}

	//Render
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(Resource.getImage(1).getSubimage(200 * (walkFrame + 3 * faceDirection), 0, 200, 200), null, getIntX() - 100, getIntY() - 130);
		switch(gunType){
		case 0: 
			g2d.drawImage(Resource.getImage(5).getSubimage(50 * faceDirection, 0, 50, 30), null, getIntX() + 20 - 90 * faceDirection, getIntY() - 25);
			break;
		case 1:
			g2d.drawImage(Resource.getImage(7).getSubimage(100 * faceDirection, 0, 100, 50), null, getIntX() - 50, getIntY() - 50);
			break;
		case 2:
			g2d.drawImage(Resource.getImage(9).getSubimage(110 * faceDirection, 0, 110, 25), null, getIntX() - 45 - 20 * faceDirection, getIntY() - 25);
			break;
		case 3:
			g2d.drawImage(Resource.getImage(11).getSubimage(200 * faceDirection, 0, 200, 50), null, getIntX() - 35 - 130 * faceDirection, getIntY() - 40);
		}
	}

}
