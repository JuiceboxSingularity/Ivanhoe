package ca.carleton.comp3004.project.GUI;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import ca.carleton.comp3004.project.gameobjects.Card.CardColor;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {          
            	ArrayList temphand = new ArrayList();
                Model model = new Model(temphand,temphand,temphand,temphand,temphand,temphand);
                //model.setPlayerTokens();
    			//model.addPlayerSpecials("Shield");
    			//model.addPlayerSpecials("Stunned");
    			//model.addPlayerSpecials("Ivanhoe");
                View view = new View(model); 
               // CardColor r = view.chooseColor();
               // Controller controller = new Controller(model,view);
                //controller.control();
            }
        });  
	}
}
