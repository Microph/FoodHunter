package character.pig;

import java.awt.Graphics2D;

import character.Pig;
import character.Player;
import character.item.Broccoli;
import character.item.Corn;
import character.item.Cucumber;
import character.item.Egg;
import character.item.FriedChicken;
import character.item.Lettuce;
import logic.Utility;
import render.HuntingGround;
import render.IRender;
import render.Resource;

public class ChickenPig extends Pig implements IRender{
	
	public ChickenPig(Player player) {
		super(player);
		// TODO Auto-generated constructor stub
		dx = 6 * Math.cos(Math.atan2(player.getY() - y, player.getX() - x));
		dy = 6 * Math.sin(Math.atan2(player.getY() - y, player.getX() - x));
		if(dx >= 0) faceDirection = 1;
		else faceDirection = 0;
		hp = 2;
	}

	@Override
	protected void move() {
		// TODO Auto-generated method stub
		x += dx;
		y += dy;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
		if(x < -50 || x > HuntingGround.WIDTH + 50 || y < - 50 || y > HuntingGround.HEIGHT + 50)
			visible = false;
	}

	@Override
	protected void dropItem() {
		// TODO Auto-generated method stub
		if(Utility.chance(15)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new FriedChicken(x, y, player);
				}
				
			}).start();;
		}
		else if(Utility.chance(20)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Egg(x, y, player);
				}
				
			}).start();;
		}
		else if(Utility.chance(25)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Corn(x, y, player);
				}
				
			}).start();;
		}
		else if(Utility.chance(30)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Broccoli(x, y, player);
				}
				
			}).start();;
		}
		else if(Utility.chance(35)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Cucumber(x, y, player);
				}
				
			}).start();;
		}
		else if(Utility.chance(40)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Lettuce(x, y, player);
				}
				
			}).start();;
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(Resource.getImage(3).getSubimage(80 * (walkFrame + 2 * faceDirection), 0, 80, 80), null, getIntX() - 40, getIntY() - 40);
	}

}
