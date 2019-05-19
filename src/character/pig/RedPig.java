package character.pig;

import java.awt.Graphics2D;

import character.Pig;
import character.Player;
import character.item.Liver;
import character.item.RedPork;
import character.item.Sausage;
import character.item.Tomato;
import logic.Utility;
import render.IRender;
import render.Resource;

public class RedPig extends Pig implements IRender{
	
	public RedPig(Player player){
		super(player);
		hp = 3;
	}

	@Override
	protected void dropItem() {
		// TODO Auto-generated method stub
		if(Utility.chance(20)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new RedPork(x,y,player);
				}
				
			}).start();;
		}
		else if(Utility.chance(25)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Sausage(x, y, player);
				}
				
			}).start();;
		}
		else if(Utility.chance(30)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Liver(x, y, player);
				}
				
			}).start();;
		}		
		else if(Utility.chance(35)){
			new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					new Tomato(x, y, player);
				}
				
			}).start();;
		}
	}
	
	//Render
	@Override
	public void draw(Graphics2D g2d) {
		// TODO Auto-generated method stub
		g2d.drawImage(Resource.getImage(2).getSubimage(80 * (walkFrame + 2 * faceDirection), 0, 80, 80), null, getIntX() - 40, getIntY() - 40);
	}

}
