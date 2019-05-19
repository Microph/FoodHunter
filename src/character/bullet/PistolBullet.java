package character.bullet;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;

import character.Bullet;
import render.IRender;
import render.Resource;

public class PistolBullet extends Bullet implements IRender{

	public PistolBullet(double x, double y, double theta) {
		super(x, y, theta, 10);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		AffineTransform at = new AffineTransform();
		at.rotate(theta);
		at.translate(-15, -5);
		AffineTransformOp op = new AffineTransformOp(at,AffineTransformOp.TYPE_BICUBIC);
		g2d.drawImage(Resource.getImage(6), op, getIntX(), getIntY());
	}

}
