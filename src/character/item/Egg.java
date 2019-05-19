package character.item;

import java.awt.Graphics2D;

import character.Item;
import character.Player;
import render.IRender;
import render.Resource;

public class Egg extends Item implements IRender{

	public Egg(double x, double y, Player player) {
		super(x, y, player);
		// TODO Auto-generated constructor stub
		westImage = Resource.getImage(39);
		cookingImage = Resource.getImage(40);
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		g2d.drawImage(Resource.getImage(15), null, getIntX() - radius, getIntY() - radius + getIntFloating());
	}
	

}
