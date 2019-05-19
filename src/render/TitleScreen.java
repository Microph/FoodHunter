package render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import logic.GameScreen;
import logic.Utility;

@SuppressWarnings("serial")
public class TitleScreen extends JPanel{
	
	private static TitleScreen instance = new TitleScreen();
	
	private TitleScreen(){
		setPreferredSize(new Dimension(1280,800));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel start = new JLabel(Resource.getIcon(0));
		start.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				start.setIcon(Resource.getIcon(2));
				try{
					Point p = getMousePosition();
					Utility.setMouseX(p.getX());
					Utility.setMouseY(p.getY());
				}
				catch (NullPointerException r2d2){
				}
				new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(300);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						GameScreen.getInstance().goToHuntingGround();
					}
					
				}).start();
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				start.setIcon(Resource.getIcon(1));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				start.setIcon(Resource.getIcon(1));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				start.setIcon(Resource.getIcon(0));
			}
			
		});
		c.gridy = 0;
		c.insets = new Insets(350,0,0,0);
		add(start,c);
		
		JLabel how2play = new JLabel(Resource.getIcon(3));
		how2play.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				how2play.setIcon(Resource.getIcon(5));
				JOptionPane.showMessageDialog(null, Resource.getIcon(6), "How to play", JOptionPane.PLAIN_MESSAGE);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				how2play.setIcon(Resource.getIcon(4));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				how2play.setIcon(Resource.getIcon(4));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				how2play.setIcon(Resource.getIcon(3));
			}
			
		});
		c.gridy = 2;
		c.insets = new Insets(50,0,0,0);
		add(how2play,c);
		
		setFocusable(true);
		setVisible(true);
	}

	public static TitleScreen getInstance() {
		return instance;
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(Resource.getImage(23), null, 0, 0);
	}
	
	
}
