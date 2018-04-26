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

public class GUIInterface extends Application 
{
	private static TournamentBracket bracket;
	private Stage theStage;
	private GridPane pane;
	private int chalNum = 0;
	private ArrayList<HBox> allHBoxes = new ArrayList<HBox>(16);
	
	public static void main(String[] args) 
	{
		List<Challenger> challengers;
		
		try 
		{
			challengers = TournamentBracket.readFile("test.txt");
		} 
		catch (Exception e) 
		{
			System.out.println("Invalid file path.");
			e.printStackTrace();
			return;
		}
		
		bracket = new TournamentBracket(challengers);
		launch(args);
		//GUI.displayChampions(challengers.get(0), challengers.get(1), challengers.get(2));
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		theStage = primaryStage;
		theStage.setTitle("Tournament Bracket");
		pane = new GridPane();
		Scene scene = new Scene(pane, 2000, 1000, Color.DARKGRAY);
		
		List<Challenger> chals = bracket.getChals();
		chalNum = chals.size();
		
		if(chalNum <= 1) 
		{
			Label champion = new Label();
	        champion.setAlignment(Pos.CENTER);
	        champion.setMinWidth(250);
			champion.setText(" Tournament Champion ");
			
			Label championName = new Label();
	        champion.setAlignment(Pos.CENTER);
			championName.setMinWidth(250);
			
			if(chalNum == 0) 
			{
				championName.setText("No teams entered.");
				
			} 
			else 
			{
				championName.setText(chals.get(0).getName());
			}
			
			VBox vbox = new VBox(10);
			vbox.getChildren().add(champion);
			vbox.getChildren().add(championName);
			pane.add(vbox, 0, 0);
		}		
		else if(chalNum > 1) 
		{
			for(int i = 0; i < chalNum / 2; i++) 
			{
				Label name1 = new Label();
				name1.setAlignment(Pos.CENTER);
				name1.setMinWidth(100);
				name1.setText(chals.get(i).getName());
				
				Label name2 = new Label();
				name2.setAlignment(Pos.CENTER);
				name2.setMinWidth(100);
				name2.setText(chals.get(chalNum - i-1).getName());
				
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
				
				allHBoxes.add(hbox1);
				allHBoxes.add(hbox2);
				
				VBox vbox = new VBox(10);
				vbox.getChildren().add(hbox1);
				vbox.getChildren().add(hbox2);
				vbox.getChildren().add(blank);
				
				button.setOnAction(new EventHandler<ActionEvent>() 
				{
					@Override
					public void handle(ActionEvent event) 
					{
						System.out.println(score1.getText() + " " + score2.getText());
						if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
						{
							System.out.println("Not all teams are present yet");
							return;
						}
						try 
						{
							if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
							{
								System.out.println("Team " + name1.getText() + " won.");
								advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
							} 
							else 
							{
								System.out.println("Team " + name2.getText() + " won.");
								advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
							}
						}
						catch(NumberFormatException e) 
						{
							System.out.println("Invalid input.");
							//button.setText("Invalid score");
							return;
						}
					}
				});
			
				pane.add(vbox, 0, i);
			}
			
			int n = 4;
			
			for(int i = 0; i < Math.log(chalNum); i++) 
			{
				for(int j = 0; j < chalNum / n; j++) 
				{
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
					
					allHBoxes.add(hbox1);
					allHBoxes.add(hbox2);
					
					button.setOnAction(new EventHandler<ActionEvent>() 
					{
						@Override
						public void handle(ActionEvent event) 
						{
							System.out.println(score1.getText()+ " " + score2.getText());
							//I think we need some way to access the 'next' game.
							if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
							{
								System.out.println("Not all teams are present yet");
								return;
							}
							try 
							{
								if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
								{
									System.out.println("Team " + name1.getText() + " won.");
									advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
								} 
								else 
								{
									System.out.println("Team "+ name2.getText() + " won.");
									advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
								}
							} 
							catch(NumberFormatException e) 
							{
								System.out.println("Invalid input.");
								//button.setText("Invalid score");
								return;
							}
						}
					});
					
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
		}

		VBox vbox = new VBox(10);
		Button finalButton = new Button("Show Champions");
		
		finalButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{
				displayChampions(chals.get(0), chals.get(1), chals.get(2));
			}
		});
		
		pane.add(finalButton, chalNum + 3, 0);
		
		if(chalNum != 0)
			pane.add(vbox, (int) Math.log(chalNum) + 2, 0);
	
		theStage.setScene(scene);
		theStage.show();
	}
	
	/** Identifies where the winner of a given match goes on to, and changes
	 * the label to reflect it.
	 * @param winner Name of team who won the match.
	 * @param boxID Location of match that took place.
	 */
	public void advanceVictor(String winner, int boxID) 
	{
		if(chalNum == 16) 
		{
			if(boxID < 16) {
				boxID /= 2;
				boxID += 16;
			}
			else if(boxID < 24) 
			{
				boxID -= 16;
				boxID /= 2;
				boxID += 24;
			}
			else if(boxID < 28) 
			{
				boxID -= 24;
				boxID /= 2;
				boxID += 28;
			}
			else if(boxID >= 28)
			{
				return;
			}
		}
		else if(chalNum == 8) 
		{
			if(boxID < 8) 
			{
				boxID /= 2;
				boxID += 8;
			}
			else if(boxID < 12) 
			{
				boxID -= 8;
				boxID /= 2;
				boxID += 12;
			}
			else if(boxID >= 12)
			{
				return;
			}
		} 
		else if (chalNum == 4) 
		{
			if(boxID < 4) 
			{
				boxID /= 2;
				boxID += 4;
			} 
			else if (boxID >= 4)
			{
				return;
			}
		} 
		else if (chalNum == 2) 
		{
			return;
		}
		
		Label l = (Label) allHBoxes.get(boxID).getChildren().get(0);//I don't like casts, but it works.
		l.setText(winner);// I could change it to <Label> not <HBox> to fix it actually, but
		// it might be useful later so I wont now
	}
	
	
	public void displayChampions(Challenger champ, Challenger secondC, Challenger thirdC) {
		Label champion = new Label();
        champion.setAlignment(Pos.CENTER);
        champion.setMinWidth(250);
		champion.setText(" Tournament Champion ");
		
		Label championName = new Label();
        champion.setAlignment(Pos.CENTER);
		championName.setMaxWidth(250);		
		championName.setText(champ.getName());
	
		Label second = new Label();
        second.setAlignment(Pos.CENTER);
        second.setMinWidth(250);
		second.setText(" Second Place ");
		
		Label secondName = new Label();
        second.setAlignment(Pos.CENTER);
		secondName.setMaxWidth(250);		
		secondName.setText(secondC.getName());
		
		Label third = new Label();
        third.setAlignment(Pos.CENTER);
        third.setMinWidth(250);
		third.setText(" Third Place ");
		
		Label thirdName = new Label();
        third.setAlignment(Pos.CENTER);
		thirdName.setMaxWidth(250);		
		thirdName.setText(thirdC.getName());
		
		VBox vbox = new VBox(10);
		vbox.getChildren().add(champion);
		vbox.getChildren().add(championName);
		vbox.getChildren().add(second);
		vbox.getChildren().add(secondName);
		vbox.getChildren().add(third);
		vbox.getChildren().add(thirdName);
		
		pane.add(vbox, chalNum + 3, 2);
		System.out.println("Added");
		theStage.show();
	}
}
