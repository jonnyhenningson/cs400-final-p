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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
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
			challengers = TournamentBracket.readFile(args[0]); // TODO args[0] eventually
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
		pane.setPadding(new Insets(10));
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
				
				TextField score2 = new TextField();
				score2.setMaxHeight(20); 
				score2.setMaxWidth(90);
				score2.setPromptText("Input Score");
				
				HBox hbox1 = new HBox(10);
                hbox1.getChildren().addAll(name1, score1);
                
                HBox hbox2 = new HBox(10);
                hbox2.getChildren().addAll(name2, score2);
                
                // adds new HBoxes to the list of all HBoxes (in order)
                allHBoxes.add(hbox1);
                allHBoxes.add(hbox2);
                
				Button button = new Button("Submit Scores");
				button.setAlignment(Pos.CENTER);
                button.setMinWidth(175);
                button.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
				button.setOnAction(new EventHandler<ActionEvent>() 
				{
				    // lambda expression used to create an instance of EventHandler
					@Override
					public void handle(ActionEvent event)
					{
						if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
						{
							System.out.println("Not all teams are present yet"); // this is just a print statement
							return; // if one of the teams in the match is not actually a team, it won't do anything.
						}
						// tries to parse scores, if it can't it won't do anything
						try 
						{   
						    // if team1 has higher score, they advance
							if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
							{
//								System.out.println("Team " + name1.getText() + " won.");
								advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
							} 
							// if team2 has higher score, they advance
							else 
							{
//								System.out.println("Team " + name2.getText() + " won.");
								advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
							}
						}
						// case when the score could not be parsed
						catch(NumberFormatException e) 
						{
							button.setText("Please Resubmit Scores");
							return;
						}
					}
				});
				
				VBox vbox = new VBox(10);
				vbox.setPadding(new Insets(7));
				vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
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
					
					TextField score1 = new TextField();
					score1.setMaxHeight(20); 
					score1.setMaxWidth(90);
					score1.setPromptText("Input Score");
					
					TextField score2 = new TextField();
					score2.setMaxHeight(20); 
					score2.setMaxWidth(90);
					score2.setPromptText("Input Score");
					
					HBox hbox1 = new HBox(10);
                    hbox1.getChildren().addAll(name1, score1);
                    
                    HBox hbox2 = new HBox(10);
                    hbox2.getChildren().addAll(name2, score2);
                    
                    allHBoxes.add(hbox1);
                    allHBoxes.add(hbox2);
                    
					Button button = new Button("Submit Scores");
					button.setAlignment(Pos.CENTER);
	                button.setMinWidth(175);
	                button.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
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
									advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
								} 
								// if team2 has higher score, they advance
								else 
								{
									advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
								}
							}
							// case when the score could not be parsed
							catch(NumberFormatException e) 
							{
								button.setText("Please Resubmit Scores");
								return;
							}}
					});
					
					VBox vbox = new VBox(10);
					vbox.setPadding(new Insets(7));
					vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                    vbox.getChildren().addAll(hbox1, button, hbox2);
                    
                    GridPane.setConstraints(vbox, i + 1, j);
                    pane.getChildren().add(vbox);
				}
				n = n * 2;
			}
		}

		// button to display champions
		VBox vbox = new VBox(10);
		
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
			// this means it was a championship match
			else if(boxID >= 28)
			{
			    generateChampions(boxID);			
			    return;
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
			// this means it was a championship match
			else if(boxID >= 12)
			{
			    generateChampions(boxID);
				return;
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
			// this means it was a championship match
			else if (boxID >= 4)
			{
			    generateChampions(boxID);
				return; 
			}
		} 
		// case for when there are 2 challengers
		else if (chalNum == 2) 
		{
		    // first place is in the HBox found at the index of boxID in allHBoxes
            String first = ((Label) allHBoxes.get(boxID).getChildren().get(0)).getText();
            String second="";
            
            // if the last HBox in allHBoxes isn't the same as first, then it is second
            if(!((Label) allHBoxes.get(allHBoxes.size()-1).getChildren().get(0)).getText().equals(first))
            {
                second = ((Label) allHBoxes.get(allHBoxes.size()-1).getChildren().get(0)).getText();
            }
            // otherwise, second is the second to last HBox in allHBoxes
            else
            {
                second = ((Label) allHBoxes.get(allHBoxes.size()-2).getChildren().get(0)).getText();
            }
            
            // there are only two teams, so there is no third place team
            String third = "No third place team";

            displayChampions(first,second,third);
            return; 
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
	
	/**
	 * Called from the advanceVictor method when the boxID indicates that it was a championship match. 
	 * This method determines the names of the challengers that took first, second, and third place and 
	 * assigns them to the respective fields.
	 * 
	 * @param boxID the location of the match that took place
	 */
	private void generateChampions(int boxID)
	{
	    // first place is in the HBox found at the index of boxID in allHBoxes
        String first = ((Label) allHBoxes.get(boxID).getChildren().get(0)).getText();
        String second="";
        String third="";
        
        // if the last HBox in allHBoxes isn't the same as first, then it is second
        if(!((Label) allHBoxes.get(allHBoxes.size()-1).getChildren().get(0)).getText().equals(first))
        {
            second = ((Label) allHBoxes.get(allHBoxes.size()-1).getChildren().get(0)).getText();
        }
        // otherwise, second is the second to last HBox in allHBoxes
        else
        {
            second = ((Label) allHBoxes.get(allHBoxes.size()-2).getChildren().get(0)).getText();
        }
        
        // stores all of the HBoxes that could be the third place finisher
        ArrayList<HBox> thirdOptions = new ArrayList<HBox>();
        // iterates over the final four round to search for the challenger that took third
        for(int n = 6; n > 2; n--)
        {
            // if the HBox is already either first or second, it cannot be third
            if(((Label) allHBoxes.get(allHBoxes.size()-n).getChildren().get(0)).getText().equals(first) ||
               ((Label) allHBoxes.get(allHBoxes.size()-n).getChildren().get(0)).getText().equals(second))
            {
                continue;
            }
            // all other HBoxes could potentially be the third place finisher
            else
            {
                thirdOptions.add(allHBoxes.get(allHBoxes.size()-n));
            }
        }
        // checks which third place option scored the most points in the final four round  
        // and assigns the highest scoring challenger to be the third place finisher
        if(Integer.parseInt((((TextField) thirdOptions.get(0).getChildren().get(1)).getText())) > 
           Integer.parseInt((((TextField) thirdOptions.get(1).getChildren().get(1)).getText())))
        {
            third = ((Label) thirdOptions.get(0).getChildren().get(0)).getText();
        }
        else
        {
            third = ((Label) thirdOptions.get(1).getChildren().get(0)).getText();
        }
        displayChampions(first,second,third);
        return;
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
	private void displayChampions(String first, String second, String third) 
	{
		Label champion = new Label();
//        champion.setAlignment(Pos.CENTER);
        champion.setMinWidth(150);
		champion.setText("Tournament Champion: ");
		
		Label championName = new Label();
        championName.setAlignment(Pos.CENTER);
		championName.setMinWidth(100);		
		championName.setText(first);
	
		Label secondPlace = new Label();
//        secondPlace.setAlignment(Pos.CENTER);
        secondPlace.setMinWidth(150);
		secondPlace.setText("Second Place: ");
		
		Label secondName = new Label();
        secondName.setAlignment(Pos.CENTER);
		secondName.setMinWidth(100);		
		secondName.setText(second);
		
		Label thirdPlace = new Label();
//        thirdPlace.setAlignment(Pos.CENTER);
        thirdPlace.setMinWidth(150);
		thirdPlace.setText("Third Place: ");
		
		Label thirdName = new Label();
        thirdName.setAlignment(Pos.CENTER);
		thirdName.setMinWidth(100);		
		thirdName.setText(third);
		
		HBox number1 = new HBox(10);
		number1.getChildren().addAll(champion, championName);
		
		HBox number2 = new HBox(10);
        number2.getChildren().addAll(secondPlace, secondName);
		
        HBox number3 = new HBox(10);
        number3.getChildren().addAll(thirdPlace, thirdName);
        
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(7));
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.getChildren().addAll(number1, number2, number3);
		
        GridPane.setConstraints(vbox, (((int)(Math.log(chalNum)/Math.log(2))) + 1), 0);
        pane.getChildren().add(vbox);
        
		theStage.show();
	}
}
