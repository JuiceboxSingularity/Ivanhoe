package ca.carleton.comp3004.project.GUI;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(System.getProperty("user.dir"));
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {          
            	ArrayList temphand = new ArrayList();
            	temphand.add(0);
            	temphand.add(1);
            	temphand.add(3);
            	temphand.add(5);            	
            	temphand.add(6);
            	temphand.add(7);            	
            	temphand.add(8);
            	temphand.add(9);
            	temphand.add(11);
            	temphand.add(12);
                Model model = new Model(temphand,temphand,temphand,temphand,temphand,temphand);
                model.setPlayerTokens();
    			model.addPlayerSpecials("Shield");
    			model.addPlayerSpecials("Stunned");
    			model.addPlayerSpecials("Ivanhoe");
                View view = new View(model); 
               // Controller controller = new Controller(model,view);
                //controller.control();
            }
        });  
	}

}
