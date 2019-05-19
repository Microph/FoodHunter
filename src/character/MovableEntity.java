package character;

import logic.Utility;
import render.IRender;

public abstract class MovableEntity extends Entity implements IRender{

	protected double dx,dy;
	protected int faceDirection;

	public MovableEntity(double x, double y, int radius, double dx, double dy) {
		super(x, y, radius);
		// TODO Auto-generated constructor stub
		this.dx = dx;
		this.dy = dy;
		faceDirection = 0;
		z = Utility.generateZ();
	}
	
	protected abstract void move();

}
