package character;

import render.HuntingGround;
import render.IRender;

public abstract class Bullet extends MovableEntity implements IRender{

	protected double theta;
	protected double speed;
	
	public Bullet(double x, double y, double theta,int speed) {
		super(x, y, 5, speed * Math.cos(theta), speed * Math.sin(theta));
		// TODO Auto-generated constructor stub
		this.speed = speed;
		this.theta = theta;
		HuntingGround.getInstance().spawn(this);
	}

	protected synchronized void collideWithPig(){
		for(Entity entity : HuntingGround.getInstance().getObjectOnScreen()){
			if(entity instanceof Pig){
				if(Math.hypot(entity.x - x, entity.y - y) <= entity.radius + radius){
					((Pig)entity).getHit(1);
					visible = false;
					break;
				}
			}
		}
	}
	
	@Override
	protected void move() {
		// TODO Auto-generated method stub
		if(x < 0 || x > HuntingGround.WIDTH || y < 0 || y > HuntingGround.HEIGHT) visible = false;
		x += dx;
		y += dy;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(visible){
			move();
			collideWithPig();
		}
	}

}
