package logic;

import java.util.ArrayList;
import java.util.List;
import character.Item;

public class Share {

	private static Share instance = new Share();
	private boolean[] gunAvailable;
	private List<Item> inventory;
	
	private Share(){
		inventory = new ArrayList<Item>();
		gunAvailable = new boolean[4];
	}
	
	public static Share getInstance(){
		return instance;
	}
	
	public void resetGun(){
		for(int i = 0; i < 4; i++)
			gunAvailable[i] = false;
	}
		
	public void resetItem(){
		inventory.clear();
	}
	
	///////////////////////////////////////
	public synchronized void addItem(Item item){
		boolean copy = false;
		for(Item checkItem : inventory){
			if(checkItem.getClass() == item.getClass()){
				checkItem.increaseQuantity();
				copy = true;
				break;
			}
		}
		if(!copy)
			inventory.add(item);
	}
	
	public boolean hasGun(int index){
		if(index < 0 || index > 3) return false;
		return gunAvailable[index];
	}
	
	//////////////////////////////////
	public void unlockGun(int index){
		if(index < 0 || index > 3) return ;
		gunAvailable[index] = true;
	}
	
	public List<Item> getItemList(){
		return inventory;
	}

}
