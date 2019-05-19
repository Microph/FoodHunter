package character;

import render.IRender;

public abstract class Entity implements IRender{
	
	protected double x,y;
	protected int z;
	protected int radius;
	protected boolean visible;
	
	public Entity(double x,double y, int radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
		visible = true;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public int getRadius() {
		return radius;
	}

	public int getIntX(){
		return (int) x;
	}
	
	public int getIntY(){
		return (int) y;
	}
	
	public abstract void update();

	@Override
	public boolean isVisible() {
		// TODO Auto-generated method stub
		return visible;
	}

	@Override
	public int getZ() {
		// TODO Auto-generated method stub
		return z;
	}
}
