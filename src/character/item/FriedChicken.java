package character.item;

import java.awt.Graphics2D;

import character.Item;
import character.Player;
import render.IRender;
import render.Resource;

public class FriedChicken extends Item implements IRender{

	public FriedChicken(double x, double y, Player player) {
		super(x, y, player);
		// TODO Auto-generated constructor stub
		westImage = Resource.getImage(37);
		cookingImage = Resource.getImage(38);
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		g2d.drawImage(Resource.getImage(14), null, getIntX() - radius, getIntY() - radius + getIntFloating());
	}

	
}
