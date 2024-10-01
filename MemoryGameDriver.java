package gui;

import javax.swing.SwingUtilities;

public class MemoryGameDriver {
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(
			    new Runnable(){
			        public void run(){
			        	
			        	//MyJFrame j = new MyJFrame();
			        	
			        	
			        	
			        	
			        	MemoryGameFrame f = new MemoryGameFrame();
			        }
			    });
	}

}
