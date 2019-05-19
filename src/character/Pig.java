package character;

import logic.Utility;
import render.HuntingGround;
import render.IRender;

public abstract class Pig extends MovableEntity implements IRender{

	protected int hp;
	
	protected Player player;
	
	protected int walkFrame;
	protected final int DELAY_PER_FRAME = 8;
	protected int walkCounter = 0;
	
	public Pig(Player player) {
		super(0, 0, 32, 1, 1);
		// TODO Auto-generated constructor stub
		double x = 0,y = 0;
		int spawnDirection = Utility.random(0, 3);
		if(spawnDirection == HuntingGround.NORTH){
			x = Utility.random(0, HuntingGround.WIDTH);
			y = 0;
		}
		else if(spawnDirection == HuntingGround.WEST){
			x = 0;
			y = Utility.random(0, HuntingGround.HEIGHT);
		}
		else if(spawnDirection == HuntingGround.SOUTH){
			x = Utility.random(0, HuntingGround.WIDTH);
			y = HuntingGround.HEIGHT;
		}
		else if(spawnDirection == HuntingGround.EAST){
			x = HuntingGround.WIDTH;
			y = Utility.random(0, HuntingGround.HEIGHT); 
		}
		this.x = x;
		this.y = y;
		this.player = player;
		HuntingGround.getInstance().spawn(this);
	}
	
	public void getHit(int damage){
		hp -= damage;
	}
	
	protected abstract void dropItem();
	
	protected void updateAnimation(){
		if(walkCounter >= DELAY_PER_FRAME){
			walkFrame++;
			if(walkFrame > 1)
				walkFrame = 0;
			walkCounter = 0;
		}
		else
			walkCounter++;
	}
	
	@Override
	protected void move() {
		// TODO Auto-generated method stub
		if(player.x < x){
			x -= dx;
			faceDirection = 0;
		}
		else if(player.x > x){
			x += dx;
			faceDirection = 1;
		}
		if(player.y < y)
			y -= dy;
		else if(player.y > y)
			y += dy;
	}
	
	protected void collideWithPlayer() {
		if(Math.hypot(player.x - x, player.y - y) <= player.radius + radius){
			player.getHit(1);
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(visible){
			move();
			updateAnimation();
			collideWithPlayer();
			if(hp <= 0) {
				visible = false;
				dropItem();
			}
		}
	}

}
