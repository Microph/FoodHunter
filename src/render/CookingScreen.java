package render;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;
import exception.SameFileExistsException;
import logic.Share;

@SuppressWarnings("serial")
public class CookingScreen extends JLayeredPane{
	private static CookingScreen instance = new CookingScreen();
	private static JLabel capture, help, bg;

	public static ArrayList<JButton> onTopRiceImages = new ArrayList<JButton>();
	public static int i=3;
	
	private CookingScreen(){
		setPreferredSize(new Dimension(1280, 800));
		bg = new JLabel(new ImageIcon(Resource.getImage(61)));
		bg.setLayout(new FlowLayout(FlowLayout.LEFT));
		bg.setBounds(0,0,1280,800);
		add(bg, new Integer(0));
		WestPanel west = new WestPanel();
		west.setBounds(0,0,400,800);
		add(west, new Integer(Integer.MAX_VALUE-1));
		
		capture = new JLabel(); //capture
		capture.setBounds(1280-170, 0, 80, 80);
		capture.setIcon(new ImageIcon(Resource.getImage(63).getSubimage(0, 0, 80, 80)));
		add(capture,new Integer(Integer.MAX_VALUE-1));
		capture.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(1);
				capture.setVisible(false);
				help.setVisible(false);
				try {
					saveFile();
				} catch (SameFileExistsException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e2){
					JOptionPane.showConfirmDialog(null, "Unable to save the screenshot.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
				}
		        capture.setVisible(true);
				help.setVisible(true);
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				capture.setIcon(new ImageIcon(Resource.getImage(63).getSubimage(85, 0, 80, 80)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				capture.setIcon(new ImageIcon(Resource.getImage(63).getSubimage(0, 0, 80, 80)));
			}
		});
		
		help = new JLabel();
		help.setBounds(1280-90, 0, 80, 80);
		help.setIcon(new ImageIcon(Resource.getImage(64).getSubimage(0, 0, 80, 80)));
		add(help,new Integer(Integer.MAX_VALUE-1));
		help.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				if(!SwingUtilities.isLeftMouseButton(e))
					return;
				Resource.playSound(1);
				JOptionPane.showMessageDialog(null,null,"Cooking Tips!",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(Resource.getImage(62)));
			}
			
			@Override
			public void mouseEntered(MouseEvent e){
				help.setIcon(new ImageIcon(Resource.getImage(64).getSubimage(85, 0, 80, 80)));
				Resource.playSound(0);
			}
			
			@Override
			public void mouseExited(MouseEvent e){
				help.setIcon(new ImageIcon(Resource.getImage(64).getSubimage(0, 0, 80, 80)));
			}
		});
	}
	
	public void addToBowl(JLabel draggedImage, ToppingDisplay sender, int x, int y, int w, int h){
		JButton img;
		try {
			img = new JButton(draggedImage.getIcon());
		} catch (Exception e) {
			img = null;
		}
		
		img.setBorderPainted(false);
		img.setContentAreaFilled(false);
		img.setOpaque(false);
		img.setBounds(x+400 -img.getBounds().width/2, y -img.getBounds().height/2, w, h);
		img.setLocation(x+400 -img.getBounds().width/2, y -img.getBounds().height/2);
		instance.add(img, new Integer(i)); i++;
		onTopRiceImages.add(img);
		final JButton imgfinal = img;
		img.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Resource.playSound(3);
				instance.remove(imgfinal);
				Share.getInstance().addItem(sender.item);
				sender.setVisible(true);
				sender.amountLabel.setText(sender.item.getQuantity() +"/"+ sender.max);
				instance.repaint();
				
			}
		});
				
	}
	
	public static void setBG(int index){
		bg.setIcon(new ImageIcon(Resource.getImage(index)));
		CookingScreen.getInstance().repaint();
	}
	
	public static void clearTopRice(){
		for(JButton a: CookingScreen.onTopRiceImages)
			CookingScreen.getInstance().remove(a);
		CookingScreen.getInstance().repaint();
	}
	
	public void saveFile() throws SameFileExistsException, IOException{
	    BufferedImage screenshot = new BufferedImage(1280, 800, BufferedImage.TYPE_INT_ARGB);
	    CookingScreen.instance.paint(screenshot.getGraphics());
        File outputfile = new File(System.getProperty("user.home") + "/Desktop/" + "MyMealScreenShot.png");
       if(outputfile.exists())
    	   throw new SameFileExistsException();
        ImageIO.write(screenshot.getSubimage(400, 0, 880, 800), "png", outputfile);
        Resource.playSound(4);
        JOptionPane.showConfirmDialog(null, "Finish Capturing. \nyou can find the screenshot named \"MyMealScreenShot.png\" at your desktop.", "Capture!", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static CookingScreen getInstance() {
		return instance;
	}
	
}
