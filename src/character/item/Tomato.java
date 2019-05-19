package character.item;

import java.awt.Graphics2D;

import character.Item;
import character.Player;
import render.IRender;
import render.Resource;

public class Tomato extends Item implements IRender{

	public Tomato(double x, double y, Player player) {
		super(x, y, player);
		// TODO Auto-generated constructor stub
		westImage = Resource.getImage(51);
		cookingImage = Resource.getImage(52);
	}

	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		super.draw(g2d);
		g2d.drawImage(Resource.getImage(29), null, getIntX() - radius, getIntY() - radius + getIntFloating());
	}
}
