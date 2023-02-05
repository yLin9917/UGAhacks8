package uga.hack.mavenproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application{

	Label title = new Label("Please enter the full address below: ");
	String riskScore;
	String[] ans;
	Label result = new Label("The purpose of this application is to \ncheck the Crime and Education (Schools) \nscore through the full address");
	Stage stage;
	Scene scene;
	Button button = new Button("SCORE");
	VBox vbox = new VBox();
	TextField tf;
	
	public App() {
		this.stage = null;
		vbox.setPrefHeight(350);
		vbox.setPrefWidth(300);
		
	}
	
	@Override
	public void init() {
		result.setTextFill(Color.web("#0076a3"));

		tf = new TextField("Ex: 870 Gaines School Rd, Athens, GA 30605");
		tf.setMaxWidth(250);
		tf.setPadding(new Insets(10, 0, 10, 0));

		vbox.setSpacing(40);
		vbox.getChildren().addAll(title, tf, button, result);
		vbox.setAlignment(Pos.CENTER);
		scene = new Scene(vbox);
		buttonAction(button);
	}
	
	@Override
	public void start(Stage stage) {
		
		this.stage = stage;
		
		this.stage.setScene(scene);
		this.stage.setTitle("Hacks");
		this.stage.sizeToScene();
		this.stage.show();
		
	}
	
	private void buttonAction(Button button) {
		
		SchoolByAddress sba = new SchoolByAddress();
		Risks r = new Risks();
		button.setOnAction(e -> {
			ans = sba.generateSchool(tf.getText());
			riskScore = r.risk(tf.getText());
			r.risk(tf.getText());
			if (ans != null ) {
				double total = 0;
				for (String i : ans) {
					total += Double.parseDouble(i);
				}
				result.setText("Overall School ranking within 1200 Meter: TOP " + Math.round((total / ans.length * 100.00) / 100.00) + "%" + "\n\n" +
						"The location's riskScore is " + riskScore + "\n"+ "(Mention: the lower Score, the safer" + " you get!)");
			}
		});
		
	}

}
