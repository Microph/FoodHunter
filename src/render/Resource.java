package render;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Resource {
	
	private static ClassLoader loader = Resource.class.getClassLoader();
	private static List<AudioClip> sounds = new ArrayList<AudioClip>();
	private static List<BufferedImage> images = new ArrayList<BufferedImage>();
	private static List<AudioClip> bgm = new ArrayList<AudioClip>();
	private static List<ImageIcon> icon = new ArrayList<ImageIcon>();
	
	static{
		images.add(loadImage("heart"));
		images.add(loadImage("player"));	
		images.add(loadImage("redPig"));
		images.add(loadImage("chickenPig"));
		images.add(loadImage("pigPocket"));
		
		images.add(loadImage("pistol"));
		images.add(loadImage("pistolBullet"));
		images.add(loadImage("bazooka"));
		images.add(loadImage("rocket"));
		images.add(loadImage("shotgun"));
		
		images.add(loadImage("shotgunBullet"));
		images.add(loadImage("rifle"));
		images.add(loadImage("mark"));	
		images.add(loadImage("redPork"));
		images.add(loadImage("friedChicken"));
		
		images.add(loadImage("egg"));
		images.add(loadImage("dumpling"));
		images.add(loadImage("tempura"));
		images.add(loadImage("paused"));
		images.add(loadImage("timeup"));
		
		images.add(loadImage("gameover"));
		images.add(loadImage("clock"));
		images.add(loadImage("bg"));
		images.add(loadImage("title"));
		images.add(loadImage("broccoli"));
		
		images.add(loadImage("corn"));
		images.add(loadImage("cucumber"));
		images.add(loadImage("fish"));
		images.add(loadImage("sausage"));
		images.add(loadImage("tomato"));
		
		images.add(loadImage("shrimp"));
		images.add(loadImage("liver"));
		images.add(loadImage("duck"));
		images.add(loadImage("lettuce"));
		images.add(loadImage("bomb"));
		
		images.add(loadImage("pork_b"));
		images.add(loadImage("pork_icon"));
		images.add(loadImage("chicken_b"));
		images.add(loadImage("chicken_icon"));
		images.add(loadImage("egg_b"));
		
		images.add(loadImage("egg_icon"));
		images.add(loadImage("broccoli_b"));
		images.add(loadImage("broccoli_icon"));
		images.add(loadImage("corn_b"));
		images.add(loadImage("corn_icon"));
		
		images.add(loadImage("cucumber_b"));
		images.add(loadImage("cucumber_icon"));
		images.add(loadImage("fish_b"));
		images.add(loadImage("fish_icon"));
		images.add(loadImage("sausage_b"));
		
		images.add(loadImage("sausage_icon"));
		images.add(loadImage("tomato_b"));
		images.add(loadImage("tomato_icon"));
		images.add(loadImage("shrimp_b"));
		images.add(loadImage("shrimp_icon"));
		
		images.add(loadImage("liver_b"));
		images.add(loadImage("liver_icon"));
		images.add(loadImage("duck_b"));
		images.add(loadImage("duck_icon"));
		images.add(loadImage("lettuce_b"));
		
		images.add(loadImage("lettuce_icon"));//60
		
		images.add(loadImage("plain")); //my cooking game
		images.add(loadImage("instructions"));
		images.add(loadImage("save"));
		images.add(loadImage("information"));
		images.add(loadImage("stat"));
		images.add(loadImage("reset"));
		images.add(loadImage("yes"));
		images.add(loadImage("rotate"));//68
		images.add(loadImage("lr"));
		images.add(loadImage("ud"));
		images.add(loadImage("eaten")); //71
		
		bgm.add(loadSound("ThailandFloatingMarket"));
		bgm.add(loadSound("EastofHenesys"));
		bgm.add(loadSound("cookingtime"));
		
		sounds.add(loadSound("mouseon")); //0
		sounds.add(loadSound("click"));
		sounds.add(loadSound("click2"));
		sounds.add(loadSound("pop"));
		sounds.add(loadSound("capture"));
		sounds.add(loadSound("magical"));
		sounds.add(loadSound("pew")); //6
		sounds.add(loadSound("explode"));
		sounds.add(loadSound("blast"));
		sounds.add(loadSound("headshot"));
		sounds.add(loadSound("clock"));
		sounds.add(loadSound("mario"));
		
		
		icon.add(new ImageIcon(loadImage("start")));
		icon.add(new ImageIcon(loadImage("start_mo")));
		icon.add(new ImageIcon(loadImage("start_mc")));
		icon.add(new ImageIcon(loadImage("h2p")));
		icon.add(new ImageIcon(loadImage("h2p_mo")));
		icon.add(new ImageIcon(loadImage("h2p_mc")));
		icon.add(new ImageIcon(loadImage("h2p_popup")));
	}
	
	private static AudioClip loadSound(String name){
			try {
				return Applet.newAudioClip(loader.getResource("res/" + name + ".wav").toURI().toURL());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
	}
	
	public static void playSound(int index){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				sounds.get(index).play();
			}
			
		}).start();
	}
	
	public static void playBGM(int index){
		new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				stopBGM();
				bgm.get(index).loop();
			}
			
		}).start();
	}
	
	public static void stopBGM(){
		for(AudioClip clip : bgm){
			clip.stop();
		}
	}
	
	private static BufferedImage loadImage(String name){
		try {
			return ImageIO.read(loader.getResource("res/" + name + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static BufferedImage getImage(int index){
		return images.get(index);
	}
	
	public static ImageIcon getIcon(int index){
		return icon.get(index);
	}
}
