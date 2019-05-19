package logic;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import character.Item;
import character.Player;
import character.item.*;
import render.Resource;
import render.ToppingDisplay;
import render.WestPanel;

public class StatManager {
  
  public static void evaluate(){
	int bazooka = 0, shotgun = 0, rifle = 0, heal = 0, q = 0;
    for(ToppingDisplay a: WestPanel.toppingDisplayList){
      q = a.max-a.item.getQuantity();
      Item item = a.item;
      
      if(item instanceof Liver) bazooka += q;
      if(item instanceof RedPork) bazooka += q;
      if(item instanceof Sausage) bazooka += q;
      if(item instanceof Tomato) bazooka += q;
      if(item instanceof Broccoli) rifle += q;
      if(item instanceof Corn) rifle += q;
      if(item instanceof Cucumber) rifle += q;
      if(item instanceof Lettuce) rifle += q;
      if(item instanceof FriedChicken) rifle += q;
      if(item instanceof Egg) shotgun += q;
      if(item instanceof Shrimp) shotgun += q;
      if(item instanceof Duck) shotgun += q;
      if(item instanceof Fish) shotgun += q;
    }
  		
    	boolean hasReward = false;
        JPanel rewardPanel = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0; g.gridy = 0;
		
		heal = bazooka+shotgun+rifle;
	    if(heal >= 15 && Player.getInstance().getHp() < 5){
	    	hasReward = true;
	    	rewardPanel.add(new JLabel("Heal: "), g);
	    	JPanel health = new JPanel();
	    	int before = Player.getInstance().getHp();
	    	Player.getInstance().heal(heal/15);
	    	for(int l=0; l <  Player.getInstance().getHp() - before; l++)
				health.add(new JLabel(new ImageIcon(Resource.getImage(0))));
			g.gridx = 1;
			rewardPanel.add(health,g);
			g.gridx = 0; g.gridy = 1;
	    }
	    
	    if(bazooka >= 9 && !Share.getInstance().hasGun(1)){
	    	hasReward = true;
	        Share.getInstance().unlockGun(1);
			rewardPanel.add(new JLabel("Unlock: "), g);
			g.gridx = 1; g.anchor = GridBagConstraints.LINE_START;
			rewardPanel.add(new JLabel("(Toggle Key: Number Button 2)", new ImageIcon(Resource.getImage(7).getSubimage(0, 0, Resource.getImage(7).getWidth()/2, Resource.getImage(7).getHeight())), JLabel.LEFT),g);
		}
	    if(rifle >= 9 && Share.getInstance().hasGun(1) && !Share.getInstance().hasGun(2)){
	    	hasReward = true;
	        Share.getInstance().unlockGun(2);
			rewardPanel.add(new JLabel("Unlock: "), g);
			g.gridx = 1; g.anchor = GridBagConstraints.LINE_START;
			rewardPanel.add(new JLabel("(Toggle Key: Number Button 3)", new ImageIcon(Resource.getImage(11).getSubimage(0, 0, Resource.getImage(11).getWidth()/2, Resource.getImage(11).getHeight())), JLabel.LEFT),g);
		}
	    if(shotgun >= 9 && Share.getInstance().hasGun(1) && Share.getInstance().hasGun(2) && !Share.getInstance().hasGun(3)){
	    	hasReward = true;
	        Share.getInstance().unlockGun(3);
			rewardPanel.add(new JLabel("Unlock: "), g);
			g.gridx = 1; g.anchor = GridBagConstraints.LINE_START;
			rewardPanel.add(new JLabel("(Toggle Key: Number Button 4)", new ImageIcon(Resource.getImage(9).getSubimage(0, 0, Resource.getImage(9).getWidth()/2, Resource.getImage(9).getHeight())), JLabel.LEFT),g);
		}
	    
	    if(hasReward){
			Resource.playSound(5);
	    	JOptionPane.showConfirmDialog(null, rewardPanel, "What a Great Meal!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
	    }
		else
	    	JOptionPane.showConfirmDialog(null, "You Get Nothing...", "What an Ordinary Meal!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        
        JPanel statPanel = new JPanel(new GridBagLayout());
		g.gridx = 0; g.gridy = 0;
		statPanel.add(new JLabel("Current Health: "), g);
		JPanel health1 = new JPanel();
		for(int l=0; l < Player.getInstance().getHp(); l++)
			health1.add(new JLabel(new ImageIcon(Resource.getImage(0))));
		g.gridx = 1;
		statPanel.add(health1,g);
		g.gridx = 0; g.gridy = 1;
		statPanel.add(new JLabel("Gun(s) Owned: "), g);
		g.gridx = 1; g.gridy = 1; g.anchor = GridBagConstraints.LINE_START;
		statPanel.add(new JLabel("(Toggle Key: Number Button 1)", new ImageIcon(Resource.getImage(5).getSubimage(0, 0, Resource.getImage(5).getWidth()/2, Resource.getImage(5).getHeight())), JLabel.LEFT),g);
		g.gridy ++;
		if(Share.getInstance().hasGun(1)){
			statPanel.add(new JLabel("(Toggle Key: Number Button 2)", new ImageIcon(Resource.getImage(7).getSubimage(0, 0, Resource.getImage(7).getWidth()/2, Resource.getImage(7).getHeight())), JLabel.LEFT),g);
			g.gridy ++;
		}
		if(Share.getInstance().hasGun(2)){
			statPanel.add(new JLabel("(Toggle Key: Number Button 3)", new ImageIcon(Resource.getImage(11).getSubimage(0, 0, Resource.getImage(11).getWidth()/2, Resource.getImage(11).getHeight())), JLabel.LEFT),g);
			g.gridy ++;
		}
		if(Share.getInstance().hasGun(3))
			statPanel.add(new JLabel("(Toggle Key: Number Button 4)", new ImageIcon(Resource.getImage(9).getSubimage(0, 0, Resource.getImage(9).getWidth()/2, Resource.getImage(9).getHeight())), JLabel.LEFT),g);
		
		JOptionPane.showConfirmDialog(null, statPanel, "Your Status", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
		
    bazooka=0;
    shotgun=0;
    rifle=0;
    heal=0;
    GameManager.SERVE_COUNT++;
  }
}
