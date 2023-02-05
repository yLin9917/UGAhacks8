package uga.hack.mavenproject;

import javafx.application.Application;


public class Driver {
	
    public static void main(String[] args) {
    
    	try {
			Application.launch(App.class, args);
		} catch (UnsupportedOperationException e) {
			System.out.println(e);
			System.err.println("err occurs...");
			System.exit(1);
		} // try
    }

}
