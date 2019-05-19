package character.bullet;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import character.Bullet;
import character.Entity;
import character.Pig;
import character.Player;
import render.HuntingGround;
import render.IRender;
import render.Resource;

public class Rocket extends Bullet implements IRender{

	protected double landingX,landingY;
	protected int detonateRadius;
	
	protected boolean inDetonating;
	protected int frameCounter;
	
	public Rocket(double x, double y, double theta, double landingX, double landingY) {
		super(x, y, theta, 5);
		// TODO Auto-generated constructor stub
		dx = speed * Math.cos(Math.atan2(landingY - y, landingX - x));
		dy = speed * Math.sin(Math.atan2(landingY - y, landingX - x));
		radius = 30;
		detonateRadius = 75;
		this.landingX = landingX;
		this.landingY = landingY;
		inDetonating = false;
		frameCounter = 0;
	}
	
	protected synchronized void detonate(){
		for(Entity entity : HuntingGround.getInstance().getObjectOnScreen()){
			if(entity instanceof Pig){
				if(Math.hypot(entity.getX() - landingX, entity.getY() - landingY) <= entity.getRadius() + detonateRadius){
					((Pig)entity).getHit(3);
				}
			}
			else if(entity instanceof Player){
				if(Math.hypot(entity.getX() - landingX, entity.getY() - landingY) <= entity.getRadius() + detonateRadius){
					((Player)entity).getHit(2);
				}
			}
		}
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(!inDetonating && visible){
			move();
			if(Math.hypot(landingX - x,landingY -y) <= radius){
				Resource.playSound(7);
				detonate();
				inDetonating = true;
			}
				
		}
		else if(visible){
			frameCounter++;
			if(frameCounter == 8)
				visible = false;
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		AffineTransform at = new AffineTransform();
		at.rotate(theta);
		at.translate(-30, -30);
		AffineTransformOp op = new AffineTransformOp(at,AffineTransformOp.TYPE_BICUBIC);
		if(inDetonating)
			g2d.drawImage(Resource.getImage(34).getSubimage(150 * frameCounter, 0, 150, 150), null, (int)landingX - detonateRadius, (int)landingY - detonateRadius);
		else
			g2d.drawImage(Resource.getImage(8), op, getIntX(), getIntY());
	}

}
