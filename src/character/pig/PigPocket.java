package character.pig;

import java.awt.Graphics2D;

import character.Entity;
import character.Item;
import character.Pig;
import character.Player;
import character.item.Duck;
import character.item.Fish;
import character.item.Shrimp;
import logic.Utility;
import render.HuntingGround;
import render.IRender;
import render.Resource;

public class PigPocket extends Pig implements IRender{

	protected Item item;
	protected double startX,startY;
	protected boolean success;
	
	public PigPocket(Player player) {
		super(player);
		hp = 1;
		startX = x;
		startY = y;
		dx = 8;
		dy = 8;
		item = null;
		// TODO Auto-generated constructor stub
		for(Entity entity : HuntingGround.getInstance().getObjectOnScreen()){
			if(entity instanceof Item){
				item = (Item) entity;
				break;
			}
		}
		if(item == null) visible = false;
		success = false;
	}

	@Override
	protected void move() {
		// TODO Auto-generated method stub
		if(success){
			if(startX < x - dx){
				x -= dx;
				faceDirection = 0;
			}
			else if(startX > x + dx){
				x += dx;
				faceDirection = 1;
			}
			if(startY < y - dy)
				y -= dy;
			else if(startY > y + dy)
				y += dy;
			if(Math.hypot(startX - x, startY - y) <= radius) visible = false;
		}
		else{
			if(item.getX() < x - dx){
				x -= dx;
				faceDirection = 0;
			}
			else if(item.getX() > x + dx){
				x += dx;
				faceDirection = 1;
			}
			if(item.getY() < y - dy)
				y -= dy;
			else if(item.getY() > y + dy)
				y += dy;
		}
	}

	protected void snatchItem() {
		// TODO Auto-generated method stub
		if(!success){
			if(Math.hypot(item.getX() - x, item.getY() - y) <= item.getRadius() + radius){
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						HuntingGround.numberOfItem--;
						HuntingGround.getInstance().getObjectOnScreen().remove(item);
						success = true;
					}
					
				}).start();
			}
		}
	}

	
	
	@Override
	protected void updateAnimation() {
		// TODO Auto-generated method stub
		walkCounter++;
		super.updateAnimation();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(visible){
			move();
			updateAnimation();
			snatchItem();
			if(hp <= 0) {
				visible = false;
				dropItem();
			}
		}
	}

	@Override
	protected void dropItem() {
		// TODO Auto-generated method stub
		if(Utility.chance(20)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Shrimp(x,y,player);
				}
				
			}).start();;
		}
		else if(Utility.chance(25)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Fish(x,y,player);
				}
				
			}).start();;
		}
		else if(Utility.chance(30)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Duck(x,y,player);
				}
				
			}).start();;
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(Resource.getImage(4).getSubimage(80 * (walkFrame + 2 * faceDirection), 0, 80, 80), null, getIntX() - 40, getIntY() - 40);
	}

}
