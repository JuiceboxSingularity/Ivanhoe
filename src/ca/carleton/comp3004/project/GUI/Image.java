package ca.carleton.comp3004.project.GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;

import ca.carleton.comp3004.project.gameobjects.Card;
 
public class Image extends Component {
           
    //BufferedImage actionCards[] = new BufferedImage[17];
    //BufferedImage simpleCards[] = new BufferedImage[19];
	BufferedImage[] cards = new BufferedImage[36]; //17+19
	String[] cardNames = new String[36];
	String[] part;
	int cardCount;
    BufferedImage backgroundBorder;//=  ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/background.jpg"));

    //(BufferedImage)ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/background.jpg"));
    // = new BufferedImage();
    
    //backgroundBorder = ImageIO.read(new File("C:/Users/Inar/Desktop/Classes/COMP3004/images/background.jpg"));
 
    public Image() {
    	File folder = new File("images2");
    	File[] listOfFiles = folder.listFiles();
    	cardCount = 0;
    	for (int i = 0; i < listOfFiles.length; i++) {
    		if (listOfFiles[i].isFile()) {
    			try {
					cards[cardCount] = ImageIO.read(listOfFiles[i]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			cards[cardCount] = resize(cards[cardCount],100,160);
    			cardNames[cardCount] = listOfFiles[i].getName();
    			cardCount++;
    		} else if (listOfFiles[i].isDirectory()) {
    			//System.out.println("Directory " + listOfFiles[i].getName());
    		}
    	}
    	
		try {
	    	backgroundBorder = ImageIO.read(new File("images2/Background/background.jpeg"));
		} catch (IOException e) {
			System.out.println("Background missing");
		}
 
    }
     
    public BufferedImage getCards(Card card) {
    	String name;
    	//System.out.println("IN WE GO" + card);
    	if (card.getCardType() != Card.CardType.Action){
    		//System.out.println("IN AGAIN");
    		name = card.getCardName() + card.getCardValue();
    		name = name.toLowerCase();
    		for (int x = 0; x<cardCount;x++){
        		part = cardNames[x].split("\\.");
        		//System.out.println(name + " " + part[0]);
        		if (part[0].equals(name)){
        			//System.out.println("FOUND");
        			return cards[x];
        		}
        	}
    		System.out.println("ERROR: CARD NOT FOUND");
    	} else {
    		name = "action"+card.getCardName();
    		name = name.toLowerCase();
    		for (int x = 0; x<cardCount;x++){
        		part = cardNames[x].split("\\.");
        		//System.out.println(name + " " + part[0]);
        		part[0] = part[0].toLowerCase();
        		if (part[0].equals(name)){
        			//System.out.println("FOUND");
        			return cards[x];
        		}
        	}
    		System.out.println("ERROR: CARD NOT FOUND");
    	}
    	return null;
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