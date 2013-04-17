package at.aau.reversi.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class Draw_Game_Field extends JPanel{
	public Draw_Game_Field() {
		
	}

	
	 protected void paintComponent( Graphics g ) // Zeichnet das Feld
	  {
	    super.paintComponent( g );
	
	    
	    g.drawRect(0, 0, 399, 399);
	    
	    int corx = 0;
	    int cory = 0;
	    int cory1 = 0;
	    int corx1 = 0;
	    
	    
	    for (int i = 0; i<=7; i++){
	    	
	    	cory= cory+50;
	    	cory1 = cory1+50;
	    
	    	g.drawLine(0, cory, 399, cory1);
 		
	    }
	    for (int i = 0; i<=7; i++){
	    	 
	    	corx= corx+50;
	    	corx1 = corx1+50;
	    	
	    	g.drawLine(corx, 0, corx1, 399);
	    	
	    	    	
	    }
	
	}
	 public  void Gamefield_update(Graphics g) {										// Updatet das Feld
			
	      Image white =  Toolkit.getDefaultToolkit().getImage("/Reversie/white.png");
	      Image black =  Toolkit.getDefaultToolkit().getImage("/Reversie/black.png");
		
		for (int posx = 0; posx < 7; posx++) {
			 
		       for (int posy = 0 ; posy < 7; posy++) {
		    	   
		    	   		Object[][] gamefield = null;
		    	   		
						if ( gamefield[posx][posy].equals ("white") ){
							
							g.drawImage(white, 50 * posx, 50 * posy, this);
							
	
						}else
						if ( gamefield[posx][posy].equals ("black") ){
							
							g.drawImage(black, 50 * posx, 50 * posy, this);
							
						}else
							
						if ( gamefield[posx][posy].equals ("empty") ){
							
							
							
						}
						
				repaint();		
		       }
		}
	}

}
