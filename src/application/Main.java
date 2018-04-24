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

public class Main extends Application {
	
	static ObservableList<String> names = FXCollections.observableArrayList();
//	private static TournamentBracket bracket;
	
	public static void main(String[] args) {
//		List<Challenger> challengers;
//		try {
//			challengers = TournamentBracket.readFile(args[0]);
//		} catch (Exception e) {
//			System.out.println("Invalid file path.");
//			e.printStackTrace();
//			return;
//		}
//		bracket=new TournamentBracket(challengers);
		names.addAll("Challenger1", "Challenger2","Challenger3","Challenger4","Challenger5","Challenger6","Challenger7","Challenger8","Challenger9","Challenger10","Challenger11","Challenger12","Challenger13","Challenger14","Challenger15","Challenger16");
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
//		List<Challenger> chals=bracket.getChals();
		primaryStage.setTitle("Tournament Bracket");
		
		GridPane pane = new GridPane();
		Scene scene = new Scene(pane, 2000, 1000, Color.DARKGRAY);
		
		for(int i = 0; i < names.size() / 2; i++) {
			
			Label name1 = new Label();
			name1.setAlignment(Pos.CENTER);
			name1.setMinWidth(100);
			name1.setText(names.get(i));
			Label name2 = new Label();
			name2.setAlignment(Pos.CENTER);
			name2.setMinWidth(100);
			name2.setText(names.get(15 - i));
			Label blank = new Label();
			blank.setText(null);
			
			TextField score1 = new TextField();
			score1.setMaxHeight(20); 
			score1.setMaxWidth(100);
			score1.setPromptText("Input Score");
			score1.setFocusTraversable(false);
			TextField score2 = new TextField();
			score2.setMaxHeight(20); 
			score2.setMaxWidth(100);
			score2.setPromptText("Input Score");
			score2.setFocusTraversable(false);
			
			Button button = new Button("Submit");
			
			HBox hbox1 = new HBox(10);
			hbox1.getChildren().add(name1);
			hbox1.getChildren().add(score1);
			HBox hbox2 = new HBox(10);
			hbox2.getChildren().add(name2);
			hbox2.getChildren().add(score2);
			hbox2.getChildren().add(button);
			
			VBox vbox = new VBox(10);
			vbox.getChildren().add(hbox1);
			vbox.getChildren().add(hbox2);
			vbox.getChildren().add(blank);
			
			pane.add(vbox, 0, i);
			
		}
		
		int n = 4;
		for(int i = 0; i < Math.log(names.size()); i++) {
			
			for(int j = 0; j < names.size() / n; j++) {
				
				Label name1 = new Label();
				name1.setAlignment(Pos.CENTER);
				name1.setMinWidth(60);
				name1.setText("TBD");
				Label name2 = new Label();
				name2.setAlignment(Pos.CENTER);
				name2.setMinWidth(60);
				name2.setText("TBD");
				Label blank = new Label();
				blank.setText(null);
				
				TextField score1 = new TextField();
				score1.setMaxHeight(20); 
				score1.setMaxWidth(100);
				score1.setPromptText("Input Score");
				score1.setFocusTraversable(false);
				TextField score2 = new TextField();
				score2.setMaxHeight(20); 
				score2.setMaxWidth(100);
				score2.setPromptText("Input Score");
				score2.setFocusTraversable(false);
				
				Button button = new Button("Submit");
				
				HBox hbox1 = new HBox(10);
				hbox1.getChildren().add(name1);
				hbox1.getChildren().add(score1);
				HBox hbox2 = new HBox(10);
				hbox2.getChildren().add(name2);
				hbox2.getChildren().add(score2);
				hbox2.getChildren().add(button);
				
	            VBox vbox = new VBox(10);
				
//				if(j == 0) {
//					Label upperBlank = new Label();
//					upperBlank.setText(null);
//					upperBlank.setMinHeight((i + 2) * 120);
//					vbox.getChildren().add(upperBlank);
//				}
				
				vbox.getChildren().add(hbox1);
				vbox.getChildren().add(hbox2);
				vbox.getChildren().add(blank);
				
				pane.add(vbox, i + 1, j);

			}
			n = n * 2;
		}
		
        Label champion = new Label();
        champion.setAlignment(Pos.CENTER);
        champion.setMinWidth(250);
		champion.setText(" Tournament Champion ");
		Label blank = new Label();
		blank.setAlignment(Pos.CENTER);
		blank.setMinWidth(35);
		
		TextField championName = new TextField();
		championName.setMaxHeight(20); 
		championName.setMaxWidth(250);
		championName.setPromptText("Champion's Name");
		championName.setFocusTraversable(false);
		
		HBox hbox = new HBox(10);
		hbox.getChildren().add(blank);
		hbox.getChildren().add(championName);
		VBox vbox = new VBox(10);
		vbox.getChildren().add(champion);
		vbox.getChildren().add(hbox);
		pane.add(vbox, (int) Math.log(names.size()) + 2, 0);
		
		
		

		
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
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}
}
