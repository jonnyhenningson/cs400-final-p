package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Scanner;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Main {
	
//	static ObservableList<String> names = FXCollections.observableArrayList();
	private static TournamentBracket bracket;
	
	public static void main(String[] args) {
		List<Challenger> challengers;
		try {
			challengers = TournamentBracket.readFile(args[0]);
		} catch (Exception e) {
			System.out.println("Invalid file path.");
			e.printStackTrace();
			return;
		}
		bracket=new TournamentBracket(challengers);
//		names.addAll("Challenger1", "Challenger2","Challenger3","Challenger4","Challenger5","Challenger6","Challenger7","Challenger8","Challenger9","Challenger10","Challenger11","Challenger12","Challenger13","Challenger14","Challenger15","Challenger16");
//		launch(args);
	}

//	@Override
//	public void start(Stage primaryStage) throws Exception {
//		List<Challenger> chals=bracket.getChals();
//		primaryStage.setTitle("Tournament Bracket");
		

		
		
		

		
//		ArrayList<HBox> boxes=new ArrayList<HBox>();
		
//		for(Challenger c:chals) {
//			HBox box1=new HBox(6.0);
//			Label nameLabel = new Label();
////			nameLabel.setAlignment(Pos.CENTER);
////			nameLabel.setMinHeight(25);
//			nameLabel.setText(c.getName());
//			box1.getChildren().add(nameLabel);
//			boxes.add(box1);
//		}
//		
		
//		vbox.getChildren().addAll(boxes);
//		primaryStage.setScene(scene);
//		primaryStage.show();

		
//	}
}
