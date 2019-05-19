package logic;

import java.awt.Dimension;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import render.CookingScreen;
import render.HuntingGround;
import render.Resource;
import render.TitleScreen;

@SuppressWarnings("serial")
public class GameScreen extends JFrame{
	
	private static GameScreen instance = new GameScreen();
	
	private JComponent currentScreen;
	
	private TitleScreen titleScreen;
	private HuntingGround huntingGround;
	private CookingScreen cookingScreen;
	
	private GameScreen(){
		titleScreen = TitleScreen.getInstance();
		huntingGround = HuntingGround.getInstance();
		cookingScreen = CookingScreen.getInstance();
		
		setTitle("Food Hunter");
		setPreferredSize(new Dimension(1280,800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		currentScreen = new JPanel();
		add(currentScreen);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private void changeScreen(JComponent screen){
		remove(currentScreen);
		Utility.reset();
		currentScreen = screen;
		add(currentScreen);
	}
	
	public void goToCookingScreen(){
		changeScreen(cookingScreen);
		cookingScreen.requestFocus();
		Resource.playBGM(2);
		validate();
		repaint();
	}
	
	public void goToHuntingGround(){
		changeScreen(huntingGround);
		GameManager.getInstance().startGame();
		huntingGround.requestFocus();
		Resource.playBGM(1);
		validate();
		repaint();
	}
	
	public void backToTitle(){
		changeScreen(titleScreen);
		titleScreen.requestFocus();
		Resource.playBGM(0);
		validate();
		repaint();
	}

	public static GameScreen getInstance() {
		return instance;
	}
	
}
