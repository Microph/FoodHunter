package exception;

import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class SameFileExistsException extends Exception{

	public SameFileExistsException(){
		super();
	}

	@Override
	public void printStackTrace() {
		JOptionPane.showConfirmDialog(null, "Unable to save the screenshot.\nThere is already a file named \"MyMealScreenShot.png\" at your desktop.\nConsider moving it to somewhere else first.", "Warning", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE);
	}
	
}