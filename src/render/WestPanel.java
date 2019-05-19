package render;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import character.Item;
import character.Player;
import logic.GameScreen;
import logic.Share;
import logic.StatManager;

@SuppressWarnings("serial")
public class WestPanel extends JPanel{
	public static ArrayList<ToppingDisplay> toppingDisplayList = new ArrayList<ToppingDisplay>();
	public static JPanel toppingDisplayCollectionPanel = new JPanel();
	public static JLabel stat, reset, serve, rotate, fliph, flipv;
	
	public WestPanel(){
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setOpaque(false);
		
		toppingDisplayCollectionPanel.setLayout(new BoxLayout(toppingDisplayCollectionPanel, BoxLayout.Y_AXIS));
		JScrollPane westScroll = new JScrollPane(toppingDisplayCollectionPanel);
		westScroll.setPreferredSize(new Dimension(400, 650));
		westScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		add(westScroll);
		
		JPanel buttonPanel = new JPanel(new FlowLayout());
		buttonPanel.setPreferredSize(new Dimension(400, 150));
		buttonPanel.setOpaque(false);
		
		reset = new JLabel();
		reset.setIcon(new ImageIcon(Resource.getImage(66).getSubimage(0, 0, 100, 100)));
		reset.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(1);
				int choice = JOptionPane.showConfirmDialog(null, "Reset your meal?", "Reset?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(choice != JOptionPane.OK_OPTION)
					return;
				for(ToppingDisplay a: toppingDisplayList){
					a.item.setQuantity(a.max);
					a.amountLabel.setText(a.item.getQuantity() +"/"+ a.max);
					if(a.max>0)
						a.setVisible(true);
				}
				CookingScreen.clearTopRice();
				Resource.playSound(2);
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				reset.setIcon(new ImageIcon(Resource.getImage(66).getSubimage(106, 0, 100, 100)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				reset.setIcon(new ImageIcon(Resource.getImage(66).getSubimage(0, 0, 100, 100)));
			}
		});
		
		stat = new JLabel();
		stat.setIcon(new ImageIcon(Resource.getImage(65).getSubimage(0, 0, 100, 100)));
		stat.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(1);
				JPanel statPanel = new JPanel(new GridBagLayout());
				GridBagConstraints q = new GridBagConstraints();
				q.gridx = 0; q.gridy = 0;
				statPanel.add(new JLabel("Current Health: "), q);
				JPanel health = new JPanel();
				for(int l=0; l < Player.getInstance().getHp(); l++)
					health.add(new JLabel(new ImageIcon(Resource.getImage(0))));
				q.gridx = 1;
				statPanel.add(health,q);
				q.gridx = 0; q.gridy = 1;
				statPanel.add(new JLabel("Gun(s) Available: "), q);
				q.gridx = 1; q.gridy = 1; q.anchor = GridBagConstraints.LINE_START;
				statPanel.add(new JLabel("(Toggle Key: Number Button 1)", new ImageIcon(Resource.getImage(5).getSubimage(0, 0, Resource.getImage(5).getWidth()/2, Resource.getImage(5).getHeight())), JLabel.LEFT),q);
				q.gridy ++;
				if(Share.getInstance().hasGun(1)){
					statPanel.add(new JLabel("(Toggle Key: Number Button 2)", new ImageIcon(Resource.getImage(7).getSubimage(0, 0, Resource.getImage(7).getWidth()/2, Resource.getImage(7).getHeight())), JLabel.LEFT),q);
					q.gridy ++;
				}
				if(Share.getInstance().hasGun(2)){
					statPanel.add(new JLabel("(Toggle Key: Number Button 3)", new ImageIcon(Resource.getImage(11).getSubimage(0, 0, Resource.getImage(11).getWidth()/2, Resource.getImage(11).getHeight())), JLabel.LEFT),q);
					q.gridy ++;
				}
				if(Share.getInstance().hasGun(3))
					statPanel.add(new JLabel("(Toggle Key: Number Button 4)", new ImageIcon(Resource.getImage(9).getSubimage(0, 0, Resource.getImage(9).getWidth()/2, Resource.getImage(9).getHeight())), JLabel.LEFT),q);
				
				JOptionPane.showConfirmDialog(null, statPanel, "Your Status", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				stat.setIcon(new ImageIcon(Resource.getImage(65).getSubimage(103, 0, 100, 100)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				stat.setIcon(new ImageIcon(Resource.getImage(65).getSubimage(0, 0, 100, 100)));
			}
		});
		
		serve = new JLabel();
		serve.setIcon(new ImageIcon(Resource.getImage(67).getSubimage(0, 0, 100, 100)));
		serve.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stubs
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(1);
				int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to serve and eat this?", "Serve it?", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if(choice == JOptionPane.OK_OPTION){
				CookingScreen.clearTopRice();
				CookingScreen.setBG(71);
				StatManager.evaluate();
				for(int l=Share.getInstance().getItemList().size()-1; l>=0;l--){
					if(Share.getInstance().getItemList().get(l).getQuantity()<=0)
						Share.getInstance().getItemList().remove(l);
				}
				WestPanel.clearDisplay();
				serve.setIcon(new ImageIcon(Resource.getImage(67).getSubimage(0, 0, 100, 100)));
				GameScreen.getInstance().goToHuntingGround();
				}
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				serve.setIcon(new ImageIcon(Resource.getImage(67).getSubimage(102, 0, 100, 100)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				serve.setIcon(new ImageIcon(Resource.getImage(67).getSubimage(0, 0, 100, 100)));
			}
		});
		
		rotate = new JLabel();
		rotate.setIcon(new ImageIcon(Resource.getImage(68).getSubimage(0, 0, 100, 100)));
		rotate.setVisible(false);
		rotate.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(2);
				BufferedImage img = DragScreen.instance.img;
				int w = img.getWidth();
		        int h = img.getHeight();
		        BufferedImage rotatedImage = new BufferedImage(h, w, img.getType());
		        Graphics2D g = rotatedImage.createGraphics();
		        AffineTransform xform = new AffineTransform();
		        xform.translate(0.5*h, 0.5*w);
		        xform.rotate(Math.PI/2);
		        xform.translate(-0.5*w, -0.5*h);
		        g.drawImage(img, xform, null);
		        g.dispose();
		        
		        DragScreen.instance.imgLabel.setSize(rotatedImage.getWidth(), rotatedImage.getHeight());
		        DragScreen.instance.imgLabel.setIcon(new ImageIcon(rotatedImage));
		        DragScreen.instance.img = rotatedImage;
		        
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				rotate.setIcon(new ImageIcon(Resource.getImage(68).getSubimage(103, 0, 100, 100)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				rotate.setIcon(new ImageIcon(Resource.getImage(68).getSubimage(0, 0, 100, 100)));
			}
		});
		
		fliph = new JLabel();
		fliph.setIcon(new ImageIcon(Resource.getImage(69).getSubimage(0, 0, 100, 100)));
		fliph.setVisible(false);
		fliph.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(2);
				BufferedImage img = DragScreen.instance.img;
				int w = img.getWidth();
		        int h = img.getHeight();
		        BufferedImage flippedImage = new BufferedImage(w, h, img.getType());
		        Graphics2D g = flippedImage.createGraphics();
		        g.drawImage(img, 0, 0, w, h, w, 0, 0, h, null);
		        g.dispose();
		        
		        DragScreen.instance.imgLabel.setIcon(new ImageIcon(flippedImage));
		        DragScreen.instance.img = flippedImage;
		        DragScreen.instance.imgLabel.setLocation(440-DragScreen.instance.img.getWidth()/2, 0);
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				fliph.setIcon(new ImageIcon(Resource.getImage(69).getSubimage(100, 0, 100, 100)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				fliph.setIcon(new ImageIcon(Resource.getImage(69).getSubimage(0, 0, 100, 100)));
			}
		});
		
		
		flipv = new JLabel();
		flipv.setIcon(new ImageIcon(Resource.getImage(70).getSubimage(0, 0, 100, 100)));
		flipv.setVisible(false);
		flipv.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(2);
				BufferedImage img = DragScreen.instance.img;
				int w = img.getWidth();
		        int h = img.getHeight();
		        BufferedImage flippedImage = new BufferedImage(w, h, img.getType());
		        Graphics2D g = flippedImage.createGraphics();
		        g.drawImage(img, 0, 0, w, h, 0, h, w, 0, null);
		        g.dispose();
		        
		        DragScreen.instance.imgLabel.setIcon(new ImageIcon(flippedImage));
		        DragScreen.instance.img = flippedImage;
		        DragScreen.instance.imgLabel.setLocation(440-DragScreen.instance.img.getWidth()/2, 0);
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				flipv.setIcon(new ImageIcon(Resource.getImage(70).getSubimage(100, 0, 100, 100)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				flipv.setIcon(new ImageIcon(Resource.getImage(70).getSubimage(0, 0, 100, 100)));
			}
		});
		
		buttonPanel.add(reset);
		buttonPanel.add(stat);
		buttonPanel.add(serve);
		buttonPanel.add(rotate);
		buttonPanel.add(fliph);
		buttonPanel.add(flipv);
		add(buttonPanel);
	}
	
	public static void toggleButtons(){
		stat.setVisible(!stat.isVisible());
		reset.setVisible(!reset.isVisible());
		serve.setVisible(!serve.isVisible());
		rotate.setVisible(!rotate.isVisible());
		fliph.setVisible(!fliph.isVisible());
		flipv.setVisible(!flipv.isVisible());
	}
	
	public static void clearDisplay(){
		toppingDisplayCollectionPanel.removeAll();
		for(int l=toppingDisplayList.size()-1; l>=0;l--)
			toppingDisplayList.remove(l);
		
		toppingDisplayCollectionPanel.validate();
	}
	
	public static void loadDisplay(){
		for(Item a: Share.getInstance().getItemList()){
			ToppingDisplay b = new ToppingDisplay(a);
			toppingDisplayList.add(b);
			toppingDisplayCollectionPanel.add(b);
		}
		
		toppingDisplayCollectionPanel.validate();
		toppingDisplayCollectionPanel.repaint();
	}
	
}
