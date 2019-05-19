package logic;

public class Utility {

	private static int zCounter;
	
	private static boolean[] keyPressed;
	private static boolean mouseLeftClicked;
	private static boolean mouseLeftTriggered;
	private static double mouseX, mouseY;
	static{
		zCounter = 1;
		keyPressed = new boolean[256];
		mouseLeftClicked = false;
		mouseLeftTriggered = false;
	}
	
	public static boolean getKeyPressed(int key){
		if(key < 0 || key > 255) return false;
		return keyPressed[key];
	}
	
	public static void setKeyPressed(int key,boolean pressed){
		if(key < 0 || key > 255) return ;
		keyPressed[key] = pressed;
	}
	
	public static boolean getMouseLeftClicked(){
		return mouseLeftClicked;
	}
	
	public static void setMouseLeftClicked(boolean pressed){
		mouseLeftClicked = pressed;
	}
	
	public static boolean getMouseLeftTriggered(){
		return mouseLeftTriggered;
	}
	
	public static void setMouseLeftTriggered(boolean pressed){
		mouseLeftTriggered = pressed;
	}
	
	public static void postUpdate(){
		mouseLeftTriggered = false;
	}
	
	public static void reset(){
		for(int i = 0; i < 255; i++){
			keyPressed[i] = false;
		}
		mouseLeftTriggered = false;
		mouseLeftClicked = false;
		zCounter = 1;
	}
	
	public static int getMouseX(){
		return (int) mouseX;
	}
	
	public static void setMouseX(double x){
		mouseX = x;
	}
	
	public static int getMouseY(){
		return (int) mouseY;
	}
	
	public static void setMouseY(double y){
		mouseY = y;
	}
	
	public static int generateZ(){
		return ++zCounter;
	}
	
	//[start,stop]
	public static int random(int start,int stop){
		return (int) (start + (Math.random() * (stop - start + 1)));
	}
	
	//chance%
	public static boolean chance(int chance){
		if(chance >= random(1, 100)) return true;
		return false;
	}
}
