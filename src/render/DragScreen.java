package render;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import character.Item;

@SuppressWarnings("serial")
public class DragScreen extends JPanel{
	public static DragScreen instance;
	public static Item tempTopping;
	public BufferedImage img;
	public JLabel imgLabel;
	
	public DragScreen(ToppingDisplay sender){
		setBounds(400,0,880,800);
		setOpaque(false);
		
		try {
			img = sender.item.getCookingImage();
			imgLabel = new JLabel(new ImageIcon(img));
			
		} catch (Exception e) {
			imgLabel = null;
		}
		add(imgLabel);
		
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				Resource.playSound(3);
					if(sender.item.getQuantity() > 0 && SwingUtilities.isLeftMouseButton(e)){
						CookingScreen.getInstance().addToBowl(imgLabel, sender, e.getX(), e.getY(), imgLabel.getBounds().width, imgLabel.getBounds().height);
						sender.item.decreaseQuantity();
						sender.amountLabel.setText(sender.item.getQuantity() +"/"+ sender.max);
						}
					
					else{
						tempTopping = null;
						CookingScreen.getInstance().remove(DragScreen.instance);
						sender.isToppingHeld = false;
						WestPanel.toggleButtons();
						
					}
								
					if(sender.item.getQuantity() <= 0){
						tempTopping = null;
						CookingScreen.getInstance().remove(DragScreen.instance);
						sender.isToppingHeld = false;
						WestPanel.toggleButtons();
						sender.setVisible(false); //to be remove after click serve
					}
					CookingScreen.getInstance().repaint();
			}
		});
		
		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				imgLabel.setBounds(e.getX()-imgLabel.getWidth()/2, e.getY()-imgLabel.getHeight()/2, imgLabel.getWidth(), imgLabel.getHeight());
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
				imgLabel.setBounds(e.getX()-imgLabel.getWidth()/2, e.getY()-imgLabel.getHeight()/2, imgLabel.getWidth(), imgLabel.getHeight());
			}
		});
		
		requestFocus();
	}
}
