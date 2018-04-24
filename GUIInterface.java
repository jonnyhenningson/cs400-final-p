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

public class GUIInterface extends Application {
	private static TournamentBracket bracket;
	
	public static void main(String[] args) {
		List<Challenger> challengers;
		try {
			challengers = TournamentBracket.readFile("test.txt");
		} catch (Exception e) {
			System.out.println("Invalid file path.");
			e.printStackTrace();
			return;
		}
		bracket=new TournamentBracket(challengers);
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		List<Challenger> chals=bracket.getChals();
		primaryStage.setTitle("Tournament Bracket");
		VBox vbox = new VBox(30.0);
		Scene scene = new Scene(vbox, 800, 600, Color.DARKGRAY);
		
		ArrayList<HBox> boxes=new ArrayList<HBox>();
		for(Challenger c:chals) {
			HBox box1=new HBox(6.0);
			Label nameLabel = new Label();
//			nameLabel.setAlignment(Pos.CENTER);
//			nameLabel.setMinHeight(25);
			nameLabel.setText(c.getName());
			//saved for later use
//			TextField scoreInput = new TextField();
//			scoreInput.setMaxHeight(20);
//			scoreInput.setMaxWidth(200);
//			scoreInput.setPromptText("Enter the score");
//			scoreInput.setFocusTraversable(false);
			Button button = new Button();
			button.setText("Modify the information of " + nameLabel.getText());
			button.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e){
			       if(button.getText() == "")
			           button.setText("");
			    }});
			box1.getChildren().addAll(nameLabel, button);
			boxes.add(box1);
		}
		
		
		vbox.getChildren().addAll(boxes);
		
		
		//Pop-up Box
		HBox box2 = new HBox(6.0);
		Label nameLabel = new Label();
		nameLabel.setText(chals.get(0).getName());
		TextField scoreInput = new TextField();
		scoreInput.setMaxHeight(20);
		scoreInput.setMaxWidth(200);
		scoreInput.setPromptText("Enter the score");
        scoreInput.setFocusTraversable(false);
        Button button = new Button();
        button.setText("Submit");
        button.setOnAction(new EventHandler<ActionEvent>() {public void handle(ActionEvent e){
               if(button.getText() == "")
                   button.setText("");
            }});
        box2.getChildren().addAll(nameLabel, scoreInput, button);
        vbox.getChildren().add(box2);
       
       
        
		
		primaryStage.setScene(scene);
		primaryStage.show();

		
	}
}
