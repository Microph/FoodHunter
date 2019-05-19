package character.bullet;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import character.Bullet;
import character.Entity;
import character.Pig;
import render.HuntingGround;
import render.IRender;
import render.Resource;

public class ShotgunBullet extends Bullet implements IRender{

	private static final int SHORT_RANGE_DELAY = 50;
	protected int delayCounter;
	
	public ShotgunBullet(double x, double y, double theta) {
		super(x, y, theta, 6);
		// TODO Auto-generated constructor stub
		delayCounter = 0;
	}
	
	@Override
	protected synchronized void collideWithPig() {
		// TODO Auto-generated method stub
		for(Entity entity : HuntingGround.getInstance().getObjectOnScreen()){
			if(entity instanceof Pig){
				if(Math.hypot(entity.getX() - x, entity.getY() - y) <= entity.getRadius() + radius){
					((Pig)entity).getHit(2);
					visible = false;
					break;
				}
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(delayCounter == SHORT_RANGE_DELAY) visible = false;
		super.update();
		delayCounter++;
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		AffineTransform at = new AffineTransform();
		at.rotate(theta);
		at.translate(-5, -5);
		AffineTransformOp op = new AffineTransformOp(at,AffineTransformOp.TYPE_BICUBIC);
		g2d.drawImage(Resource.getImage(10), op, getIntX(), getIntY());
	}

}
