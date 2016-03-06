package ca.carleton.comp3004.project.GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
 
public class Image extends Component {
           
    BufferedImage actionCards[] = new BufferedImage[17];
    BufferedImage simpleCards[] = new BufferedImage[19];
    BufferedImage backgroundBorder;//=  ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/background.jpg"));

    //(BufferedImage)ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/background.jpg"));
    // = new BufferedImage();
    
    //backgroundBorder = ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/background.jpg"));
 
    public Image() {
    	for(int i = 0; i<17; i++) {
    		try {
    			//actionCards[i] = ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/" + "actionCards" + i + ".jpeg"));
    			actionCards[i] = ImageIO.read(new File("images/actionCards" + i + ".jpeg"));
    			actionCards[i] = resize(actionCards[i],100,160);
    		} catch (IOException e) {
    		}
    	}
    	for(int i = 0; i<19; i++) {
    		try {
    			//simpleCards[i] = ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/" + "simpleCards" + i + ".jpeg"));
    			simpleCards[i] = ImageIO.read(new File("images/simpleCards" + i + ".jpeg"));
    			simpleCards[i] = resize(simpleCards[i],100,160);
    		} catch (IOException e) {
    		}
    	}
    	
		try {
	    	backgroundBorder = ImageIO.read(new File("images/Background/background.jpeg"));
		} catch (IOException e) {
			System.out.println("Background missing");
		}
 
    }
 
    public BufferedImage getActionCards(int x) {
        return actionCards[x];
    }
    
    public BufferedImage getSimpleCards(int x) {
        return simpleCards[x];
    }
    
    public BufferedImage getCards(int x) {
    	if (x < 17 && x >= 0) {
    		return actionCards[x];
    	} else if (x > 16 && x < 35) {
    		x = x - 17;
    		return simpleCards[x];
    	} else {
    		return null;
    	}
    }
    
    public BufferedImage getBackgroundPic() {
    	return backgroundBorder;
    }
    
    public BufferedImage getBackgroundPic(int x, int y) {
    	return resize(backgroundBorder,x,y);
    }
    
    public static BufferedImage resize(BufferedImage img, int newW, int newH) { 
        java.awt.Image tmp = img.getScaledInstance(newW, newH, java.awt.Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
    

    
}