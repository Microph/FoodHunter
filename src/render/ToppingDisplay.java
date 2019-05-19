package render;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import character.Item;

@SuppressWarnings("serial")
public class ToppingDisplay extends JPanel{
	public Item item;
	public boolean isToppingHeld = false;
	public JLabel amountLabel;
	public int max;
	
	public ToppingDisplay(Item item){
		this.item = item;
		setPreferredSize(new Dimension(395,200));
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton imgButton = new JButton(new ImageIcon(item.getWestImage()));
		
		imgButton.setContentAreaFilled(false);
		imgButton.setPreferredSize(new Dimension(395, 150));
		imgButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(imgButton);
		
		imgButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				if(DragScreen.tempTopping == null && item.getQuantity()>0 && SwingUtilities.isLeftMouseButton(e)){
							// TODO Auto-generated method stub
							isToppingHeld = true;
							WestPanel.toggleButtons();
							DragScreen.tempTopping = item;
							DragScreen.instance = new DragScreen(ToppingDisplay.this);
							CookingScreen.getInstance().add(DragScreen.instance, new Integer(Integer.MAX_VALUE));
				}
				else if(item.getQuantity()>0 && SwingUtilities.isLeftMouseButton(e)){
					// TODO Auto-generated method stub
					for(ToppingDisplay a: WestPanel.toppingDisplayList){
						if(a.isToppingHeld && !a.equals(ToppingDisplay.this)){
							a.isToppingHeld = false;
							isToppingHeld = true;
							DragScreen.tempTopping = item;
							CookingScreen.getInstance().remove(DragScreen.instance);
							DragScreen.instance = new DragScreen(ToppingDisplay.this);
							CookingScreen.getInstance().add(DragScreen.instance, new Integer(Integer.MAX_VALUE));
							break;
						}
						else if(a.isToppingHeld && a.equals(ToppingDisplay.this)){
							isToppingHeld = false;
							WestPanel.toggleButtons();
							DragScreen.tempTopping = null;
							CookingScreen.getInstance().remove(DragScreen.instance);
							break;
						}
					}
				}
				else if(DragScreen.tempTopping != null && SwingUtilities.isRightMouseButton(e)){
					DragScreen.tempTopping = null;
					CookingScreen.getInstance().remove(DragScreen.instance);
					for(ToppingDisplay sender: WestPanel.toppingDisplayList)
						sender.isToppingHeld = false;
					WestPanel.toggleButtons();
				}
				else
					return;
				CookingScreen.getInstance().repaint();
				Resource.playSound(3);
			}
		});
		
		this.max = item.getQuantity();
		amountLabel = new JLabel(item.getQuantity() +"/"+ max);
		amountLabel.setFont(new Font("Serif", Font.BOLD, 25));
		amountLabel.setPreferredSize(new Dimension(100, 50));
		amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		add(amountLabel);
	}
}
