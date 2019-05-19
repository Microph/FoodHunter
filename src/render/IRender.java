package render;

import java.awt.Graphics2D;

public interface IRender {

	public void draw(Graphics2D g2d);
	public boolean isVisible();
	public int getZ();
}
