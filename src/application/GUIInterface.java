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
import javafx.scene.control.ScrollPane;
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

/**
 * @author Henning and others (put name here, unless it doesn't matter)
 *
 */
public class GUIInterface extends Application 
{
    // TournamentBracket that holds list of challengers
	private static TournamentBracket bracket;
	// Stage that the GUI shows up on
	private Stage theStage;
	// GridPane that holds all of the elements in the Stage
	private GridPane pane;
	// number of challengers
	private int chalNum = 0;
	// list of all challengers, used to find where to advance and find third place finisher
	private ArrayList<HBox> allHBoxes = new ArrayList<HBox>(16);
	
	
	/** Main method that runs the program, taking in the test file, parsing it for elements,
	 * and launching the GUI.
	 * @param args First element should be the name of the test file.
	 */
	public static void main(String[] args) 
	{
	    // stores the list of Challengers in the bracket
		List<Challenger> challengers;
		
		try 
		{
		    // get information of teams from test file.
			challengers = TournamentBracket.readFile("test.txt"); // TODO args[0] eventually
		} 
		catch (Exception e) 
		{
			System.out.println("Invalid file path.");
			e.printStackTrace();
			return;
		}
		// create a new TournamentBracket object using the list of Challengers
		bracket = new TournamentBracket(challengers);
		// creates the GUIInterface object.
		launch(args);
		
		//GUI.displayChampions(challengers.get(0), challengers.get(1), challengers.get(2));
	}
	
	/** Method ran by the Application constructor that builds the GUI page
	 * 
	 * @see javafx.application.Application#start(javafx.stage.Stage)
	 * @param primaryStage the primary stage things are displayed in
	 */
	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		// initializes theStage field
		theStage = primaryStage;
		theStage.setTitle("Tournament Bracket");
		// initializes the pane field
		pane = new GridPane();
		pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(10);
        pane.setHgap(10);
		// creates a new scene containing pane
		Scene scene = new Scene(pane, 2000, 1000, Color.DARKGRAY);
		
		// utilizes a ScrollPane object to make the GUI scrollable
		VBox root = new VBox();
        ScrollPane scroll = new ScrollPane();
        scroll.setContent(pane);
        root.getChildren().addAll(scroll);
        scene.setRoot(root);
		
        // stores a list of the challengers in the bracket
		List<Challenger> chals = bracket.getChals();
		// stores the number of challengers in the bracket
		chalNum = chals.size();
		
		// case where there are 0 or 1 challengers
		if(chalNum <= 1) 
        {
            Label champion = new Label();
            champion.setAlignment(Pos.CENTER);
            champion.setMinWidth(75);
            
            // if there are 0 challengers, display that no teams were entered
            if(chalNum == 0) 
            {
                champion.setText("No Teams, No Champion.");
            } 
            // if there is only 1 challenger, display that that one team won
            else 
            { 
                champion.setText("Tournament Champion: " + chals.get(0).getName());
            }
            GridPane.setConstraints(champion, 0, 0);
            pane.getChildren().add(champion);
        }
		// checks for an even number of teams
        else if(chalNum % 2 != 0)
        {
            Label unevenTeams = new Label();
            unevenTeams.setText("ERROR: The number of challengers must be a power of 2.");
            unevenTeams.setAlignment(Pos.CENTER);
            unevenTeams.setMinWidth(75);
            GridPane.setConstraints(unevenTeams, 0, 0);
            pane.getChildren().add(unevenTeams);
        }	
		// case where there are more than 1 challengers
		else
		{
		    // creates the first line of matches, with chalNum teams facing off
			for(int i = 0; i < chalNum / 2; i++) 
			{ 
				Label name1 = new Label();
				name1.setAlignment(Pos.CENTER);
				name1.setMinWidth(75);
				name1.setText(chals.get(i).getName());
				
				Label name2 = new Label();
				name2.setAlignment(Pos.CENTER);
				name2.setMinWidth(75);
				name2.setText(chals.get(chalNum - i-1).getName());
				
				TextField score1 = new TextField();
				score1.setMaxHeight(20); 
				score1.setMaxWidth(90);
				score1.setPromptText("Input Score");
//				score1.setFocusTraversable(false);
				
				TextField score2 = new TextField();
				score2.setMaxHeight(20); 
				score2.setMaxWidth(90);
				score2.setPromptText("Input Score");
//				score2.setFocusTraversable(false);
				
				HBox hbox1 = new HBox(10);
                hbox1.getChildren().addAll(name1, score1);
                
                HBox hbox2 = new HBox(10);
                hbox2.getChildren().addAll(name2, score2);
                
                // adds new HBoxes to the list of all HBoxes (in order)
                allHBoxes.add(hbox1);
                allHBoxes.add(hbox2);
                
				Button button = new Button("Submit");
				button.setAlignment(Pos.CENTER);
                button.setMinWidth(75);
				button.setOnAction(new EventHandler<ActionEvent>() 
				{
				    // lambda expression used to create an instance of EvenHandler
					@Override
					public void handle(ActionEvent event)
					{
						//System.out.println(score1.getText() + " " + score2.getText());
						if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
						{
							System.out.println("Not all teams are present yet"); // this is just a print statement
							return;// If one of the teams in the match is not actually a team, it won't do anything.
						}
						// tries to parse scores, if it can't it won't do anything
						try 
						{   
						    // if team1 has higher score, they advance
							if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
							{
								System.out.println("Team " + name1.getText() + " won.");
								advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
							} 
							// if team2 has higher score, they advance
							else 
							{
								System.out.println("Team " + name2.getText() + " won.");
								advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
							}
						}
						// case when the score could not be parsed
						catch(NumberFormatException e) 
						{
							System.out.println("Invalid input.");
							// button.setText("Invalid score");
							return;
						}
					}
				});
				
				VBox vbox = new VBox(10);
                vbox.getChildren().addAll(hbox1, button, hbox2);
                
                GridPane.setConstraints(vbox, 0, i);
                pane.getChildren().add(vbox);
			}
			
			int n = 4;
			
			// sets up all the remaining rows of matches
			for(int i = 0; i < Math.log(chalNum); i++) 
			{
				for(int j = 0; j < chalNum / n; j++) 
				{
					Label name1 = new Label();
					name1.setAlignment(Pos.CENTER);
					name1.setMinWidth(75);
					name1.setText("TBD");
					
					Label name2 = new Label();
					name2.setAlignment(Pos.CENTER);
					name2.setMinWidth(75);
					name2.setText("TBD");
					
//					Label blank = new Label();
//					blank.setText(null);
					
					TextField score1 = new TextField();
					score1.setMaxHeight(20); 
					score1.setMaxWidth(90);
					score1.setPromptText("Input Score");
//					score1.setFocusTraversable(false);
					
					TextField score2 = new TextField();
					score2.setMaxHeight(20); 
					score2.setMaxWidth(90);
					score2.setPromptText("Input Score");
//					score2.setFocusTraversable(false);
					
					HBox hbox1 = new HBox(10);
                    hbox1.getChildren().addAll(name1, score1);
                    
                    HBox hbox2 = new HBox(10);
                    hbox2.getChildren().addAll(name2, score2);
                    
                    allHBoxes.add(hbox1);
                    allHBoxes.add(hbox2);
                    
					Button button = new Button("Submit");
					button.setAlignment(Pos.CENTER);
	                button.setMinWidth(75);
					button.setOnAction(new EventHandler<ActionEvent>() 
					{
					    // lambda expression used to create an instance of EvenHandler
						@Override
						public void handle(ActionEvent event) // Do we need to annotate the top of this?
						{
							//System.out.println(score1.getText() + " " + score2.getText());
							if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
							{
								System.out.println("Not all teams are present yet");//this is just a print statement
								return;//If one of the teams in the match is not actually a team, it won't do anything.
							}
							// tries to parse scores, if it can't it won't do anything
							try 
							{
							    // if team1 has higher score, they advance
								if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
								{
									System.out.println("Team " + name1.getText() + " won.");
									advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
								} 
								// if team2 has higher score, they advance
								else 
								{
									System.out.println("Team " + name2.getText() + " won.");
									advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
								}
							}
							// case when the score could not be parsed
							catch(NumberFormatException e) 
							{
								System.out.println("Invalid input.");
								//button.setText("Invalid score");
								return;
							}}
					});
					
					VBox vbox = new VBox(10);
                    vbox.getChildren().addAll(hbox1, button, hbox2);
                    
                    GridPane.setConstraints(vbox, i + 1, j);
                    pane.getChildren().add(vbox);
					
	//				if(j == 0) {
	//					Label upperBlank = new Label();
	//					upperBlank.setText(null);
	//					upperBlank.setMinHeight((i + 2) * 120);
	//					vbox.getChildren().add(upperBlank);
	//				}
//					
//					vbox.getChildren().add(hbox1);
//					vbox.getChildren().add(hbox2);
//					vbox.getChildren().add(blank);
//					
//					pane.add(vbox, i + 1, j);
				}
				n = n * 2;
			}
		}

		// proof of concept button to show champions. NOT IMPLIMENTED, JUST AN EXAMPLE.
		VBox vbox = new VBox(10);
		Button finalButton = new Button("Show Champions");
		
		finalButton.setOnAction(new EventHandler<ActionEvent>() 
		{
			@Override
			public void handle(ActionEvent event) 
			{    
			    //currently just displays first 3 teams, which is why it crashes.
				displayChampions(chals.get(0), chals.get(1), chals.get(2));
			}
		});
		
		pane.add(finalButton, chalNum + 3, 0);
		
		if(chalNum != 0)
        {
            pane.add(vbox, (int) Math.log(chalNum) + 2, 0);
        }
	
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
	  // case for when there are 16 challengers
		if(chalNum == 16) 
		{
		    // calculations to find where to advance
			if(boxID < 16) 
			{
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
				return; //THIS MEANS IT WAS CHAMPIONSHIP MATCH; NOT IMPLIMENTED
			}
		}
		// case for when there are 8 challengers
		else if(chalNum == 8) 
		{
		    // calculations to find where to advance
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
				return; //THIS MEANS IT WAS CHAMPIONSHIP MATCH; NOT IMPLIMENTED
			}
		} 
		// case for when there are 4 challengers
		else if (chalNum == 4) 
		{
		    // calculations to find where to advance
			if(boxID < 4) 
			{
				boxID /= 2;
				boxID += 4;
			} 
			else if (boxID >= 4)
			{
				return; //THIS MEANS IT WAS CHAMPIONSHIP MATCH; NOT IMPLIMENTED
			}
		} 
		// case for when there are 2 challengers
		else if (chalNum == 2) 
		{
			return; //THIS MEANS IT WAS CHAMPIONSHIP MATCH; NOT IMPLIMENTED
		}
		else 
		{
			// this should never occur
		}
		
		Label l = (Label) allHBoxes.get(boxID).getChildren().get(0); // I don't like casts, but it works.
		l.setText(winner); // I could change it to <Label> not <HBox> to fix it actually, but
		// it might be useful later so I wont now
		
		// CHANGE TO .get(1) TO GET THE CORRESPONDING TEXT BOX
		// like this probably, haven't actually tested
		// TextField t= (TextField) allHBoxes.get(boxID).getChildren().get(1);
	}
	
	
	/** Half-implemented method for displaying champions. Mostly a proof of concept,
	 * but usable.
	 * 
	 * !Note that it might be a good idea to change it so that the input is just Strings,
	 * rather than entire Challenger objects.!
	 * 
	 * @param champ Winning team
	 * @param secondC Second place team
	 * @param thirdC Third place team
	 */
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
