package character.bullet;

import java.awt.Graphics2D;

import character.Bullet;
import character.Entity;
import character.Pig;
import render.HuntingGround;
import render.IRender;
import render.Resource;

public class RifleBullet extends Bullet implements IRender{

	protected Pig pig;
	
	private static final int KILL_DELAY = 15;
	protected int delayCounter;
	
	public RifleBullet(int x,int y) {
		super(0, 0, 10, 0);
		pig = null;
		// TODO Auto-generated constructor stub
		for(Entity entity : HuntingGround.getInstance().getObjectOnScreen()){
			if(entity instanceof Pig){
				if(Math.hypot(entity.getX() - x, entity.getY() - y) <= entity.getRadius()){
					pig = (Pig) entity;
					break;
				}
			}
		}
		if(pig == null) visible = false;
		delayCounter = 0;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(visible){
			if(delayCounter == KILL_DELAY){
				Resource.playSound(9);
				pig.getHit(9001);
				visible = false;
			}
			delayCounter++;
		}
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		if(visible){
			g2d.drawImage(Resource.getImage(12), null, pig.getIntX() - 20, pig.getIntY() - 20);
		}
	}

}
