import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Observable;
import javafx.application.Application;
import javafx.collections.FXCollections;

public class Main extends Application {
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
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Tournament Bracket");
		primaryStage.show();

		
	}
}
