package character;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import logic.Share;
import render.HuntingGround;
import render.IRender;

public abstract class Item extends Entity implements IRender{

	private Player player;
	protected int quantity;
	
	protected BufferedImage westImage;
	protected BufferedImage cookingImage;
	
	protected double floatingPoint;
	protected double direction;
	
	public Item(double x, double y, Player player) {
		super(x, y, 20);
		// TODO Auto-generated constructor stub
		this.player = player;
		quantity = 1;
		HuntingGround.numberOfItem++;
		HuntingGround.getInstance().spawn(this);
		floatingPoint = 0;
		direction = 0.2;
	}
	
	protected void keepByPlayer() {
		if(Math.hypot(player.x - x, player.y - y) <= player.radius + radius){
			Share.getInstance().addItem(this);
			HuntingGround.numberOfItem--;
			visible = false;
		}
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		if(visible){
			keepByPlayer();
			floatingPoint += direction;
			if(floatingPoint >= 5 || floatingPoint <= -5)
				direction = -direction;
		}
	}
	
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		//shadow
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		g2d.setColor(Color.BLACK);
		g2d.fillOval(getIntX() - radius / 2, getIntY() + radius, radius, radius / 2);
		g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
	}
	
	protected int getIntFloating(){
		return (int) floatingPoint;
	}

	public void increaseQuantity(){
		quantity++;
	}
	
	public void decreaseQuantity(){
		quantity--;
	}
	
	public void setQuantity(int a){
		this.quantity = a;
	}
	
	public int getQuantity(){
		return quantity;
	}
	
	public BufferedImage getWestImage(){
		return westImage;
	}
	public BufferedImage getCookingImage(){
		return cookingImage;
	}
}
