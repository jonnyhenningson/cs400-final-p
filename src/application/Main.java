/////////////////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION ///////////////////////////////
//
// Title:                   Tournament Bracket
// Due Date:                May 3rd, 2018
// Submission Files:        Challenger.java, Main.java, TournamentBracket.java, teamList.txt 
// 
// Course:                  CS400 Spring 2018
// Other Sources:           None
// Known Bugs:              None
//
// Authors:                 Jonny Henningson, Zhengda (Jerry) Han, Tyler Henning, 
//                          Penghai Wei, Jichen Zhang
// Emails:                  jhenningson@wisc.edu, zhan66@wisc.edu, thenning2@wisc.edu,
//                          pwei25@wisc.edu, JICHEN EMAIL HERE
//
////////////////////////////////////////////////////////////////////////////////////////////////////

package application;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Main class that sets up the Tournament Bracket GUI and runs the program
 * 
 * @author Jonny Henningson, Tyler Henning, Jerry Han
 */
public class Main extends Application 
{
    // the TournamentBracket object that holds the list of challengers
	private static TournamentBracket bracket;
	// the Stage object that the GUI shows up in
	private Stage theStage;
	// the GridPane object that holds all of the elements in the Stage
	private GridPane pane;
	// stores the number of challengers
	private int chalNum = 0;
	// list of all challengers, used to find where to advance and find third place finisher
	private ArrayList<HBox> allHBoxes = new ArrayList<HBox>(16);
	
	
	/** Main method that runs the program, taking in the test file, parsing it for elements,
	 *  and launching the GUI
	 * 
	 * @param args first element should be the name of a file containing a list of teams
	 */
	public static void main(String[] args) 
	{
	    // stores the list of Challengers in the bracket
		List<Challenger> challengers;
		
		// tries to read in the teams from the teams file
		try 
		{
			challengers = TournamentBracket.readFile("teamList.txt");//args[0]); // teamList
		} 
		catch (Exception e) 
		{
			System.out.println("Invalid file path.");
			e.printStackTrace();
			return;
		}
		
		// creates a new TournamentBracket object using the list of Challengers
		bracket = new TournamentBracket(challengers);
		// creates the GUIInterface object
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
		    // label used to store the message displayed to the user in this case
            Label message = new Label();
            message.setAlignment(Pos.CENTER);
            message.setMinWidth(75);
            
            // if there are 0 challengers, display that no teams were entered
            if(chalNum == 0) 
            {
                message.setText("No Teams, No Champion.");
            } 
            // if there is only 1 challenger, display that that one team won
            else 
            { 
                message.setText("Tournament Champion: " + chals.get(0).getName());
            }
            
            // adds the message to the pane
            GridPane.setConstraints(message, 0, 0);
            pane.getChildren().add(message);
        }
		// checks for an even number of teams
        else if(chalNum % 2 != 0)
        {
            // used to store the message displayed to the user in this case
            Label message = new Label();
            message.setText("ERROR: The number of challengers must be a power of 2.");
            message.setAlignment(Pos.CENTER);
            message.setMinWidth(75);
            
            // adds the message to the pane
            GridPane.setConstraints(message, 0, 0);
            pane.getChildren().add(message);
        }	
		// case where there is more than 1 challenger
		else
		{
		    // creates the first round of matches, with chalNum teams facing off
			for(int i = 0; i < chalNum / 2; i++) 
			{ 
			    // creates Labels to display the names of the teams facing off
				Label name1 = new Label();
				name1.setAlignment(Pos.CENTER);
				name1.setMinWidth(75);
				name1.setText(chals.get(i).getName());
				Label name2 = new Label();
				name2.setAlignment(Pos.CENTER);
				name2.setMinWidth(75);
				name2.setText(chals.get(chalNum - i-1).getName());
				
				// creates TextFields to allow the user to enter each team's score
				TextField score1 = new TextField();
				score1.setMaxHeight(20); 
				score1.setMaxWidth(90);
				score1.setPromptText("Input Score");
				TextField score2 = new TextField();
				score2.setMaxHeight(20); 
				score2.setMaxWidth(90);
				score2.setPromptText("Input Score");
				
				// places each team's name and score field next to each other in an HBox 
				HBox hbox1 = new HBox(10);
                hbox1.getChildren().addAll(name1, score1);
                HBox hbox2 = new HBox(10);
                hbox2.getChildren().addAll(name2, score2);
     
                // adds new HBoxes to the list of all HBoxes (in order)
                allHBoxes.add(hbox1);
                allHBoxes.add(hbox2);
                
                // creates a button that allows the user to submit the scores for the round
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
					    // checks to make sure that the scores being submitted are for a valid game
						if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
						{
							button.setText("Please Submit Scores Later");
						}
						else
						{
						    // stores the scores entered by the user
						    String stringScore1 = score1.getText();
						    String stringScore2 = score2.getText();
						    
						    // checks to ensure that two scores were entered
						    if(stringScore1.isEmpty() || stringScore2.isEmpty())
						    {
						        button.setText("Please Submit Two Scores");
						    }
    						
						    // tries to parse the entered scores
    						try 
    						{   
    						    // stores the parsed scores
    						    int team1Score = Integer.parseInt(stringScore1);
    						    int team2Score = Integer.parseInt(stringScore2);
    						    
    						    // scores must be positive
    						    if(team1Score < 0 || team2Score < 0)
    						    {
    						        button.setText("Please Resubmit Scores");
    						    }
    						    // ties are not allowed in a tournament bracket
    						    else if(team1Score == team2Score)
    						    {
    						        button.setText("Please Resubmit Scores");
    						    }
    						    // if name1 has higher score, they advance to the next round
    						    else if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
    							{
    								advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
    								button.setText("Scores Submitted");
    								button.setDisable(true);
    							} 
    							// otherwise name2 advances to the next round
    							else 
    							{
    								advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
    								button.setText("Scores Submitted");
    								button.setDisable(true);
    							}
    						}
    						// case when the score could not be parsed
                            catch(NumberFormatException e) 
                            {
                                button.setText("Please Resubmit Scores");
                            }
						}
					}
				});
				
				// creates a VBox to hold both team's HBox and the submit button
				VBox vbox = new VBox(10);
				vbox.setPadding(new Insets(7));
				vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                vbox.getChildren().addAll(hbox1, button, hbox2);
                
                // adds the vbox to the pane at the proper location
                GridPane.setConstraints(vbox, 0, i);
                pane.getChildren().add(vbox);
			}
			
			int n = 4;
			// sets up all of the remaining rounds of matches
			for(int i = 0; i < Math.log(chalNum); i++) 
			{
				for(int j = 0; j < chalNum / n; j++) 
				{
				    // creates Labels to display the names of the teams facing off
					Label name1 = new Label();
					name1.setAlignment(Pos.CENTER);
					name1.setMinWidth(75);
					name1.setText("TBD");
					Label name2 = new Label();
					name2.setAlignment(Pos.CENTER);
					name2.setMinWidth(75);
					name2.setText("TBD");
					
					// creates TextFields to allow the user to enter each team's score
					TextField score1 = new TextField();
					score1.setMaxHeight(20); 
					score1.setMaxWidth(90);
					score1.setPromptText("Input Score");
					TextField score2 = new TextField();
					score2.setMaxHeight(20); 
					score2.setMaxWidth(90);
					score2.setPromptText("Input Score");
					
					// places each team's name and score field next to each other in an HBox 
					HBox hbox1 = new HBox(10);
                    hbox1.getChildren().addAll(name1, score1);
                    HBox hbox2 = new HBox(10);
                    hbox2.getChildren().addAll(name2, score2);
                    
                    // adds new HBoxes to the list of all HBoxes (in order)
                    allHBoxes.add(hbox1);
                    allHBoxes.add(hbox2);
                    
                    // creates a button that allows the user to submit the scores for the round
					Button button = new Button("Submit Scores");
					button.setAlignment(Pos.CENTER);
	                button.setMinWidth(175);
	                button.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
					button.setOnAction(new EventHandler<ActionEvent>() 
					{
					    // lambda expression used to create an instance of EvenHandler
						@Override
						public void handle(ActionEvent event)
						{
						    // checks to make sure that the scores being submitted are for a valid game
	                        if(name1.getText().equals("TBD") || name2.getText().equals("TBD")) 
	                        {
	                            button.setText("Please Submit Scores Later");
	                        }
	                        else
	                        {
	                            // stores the score entered by the user
	                            String stringScore1 = score1.getText();
	                            String stringScore2 = score2.getText();
	                            
	                            // checks to ensure that two scores were entered
	                            if(stringScore1.isEmpty() || stringScore2.isEmpty())
	                            {
	                                button.setText("Please Submit Two Scores");
	                            }
	                            
	                            // tries to parse the entered scores
	                            try 
	                            {   
	                                // stores the parsed scores
	                                int team1Score = Integer.parseInt(stringScore1);
	                                int team2Score = Integer.parseInt(stringScore2);
	        
	                                // scores must be positive
	                                if(team1Score < 0 || team2Score < 0)
	                                {
	                                    button.setText("Please Resubmit Scores");
	                                }
	                                // ties are not allowed in a tournament bracket
	                                else if(team1Score == team2Score)
	                                {
	                                    button.setText("Please Resubmit Scores");
	                                }
	                                // if name1 has higher score, they advance to the next round
	                                else if(Integer.parseInt(score1.getText()) > Integer.parseInt(score2.getText())) 
	                                {
	                                    advanceVictor(name1.getText(), allHBoxes.indexOf(hbox1));
	                                    button.setText("Scores Submitted");
	                                    button.setDisable(true);
	                                } 
	                                // otherwise name2 advances to the next round
	                                else 
	                                {
	                                    advanceVictor(name2.getText(), allHBoxes.indexOf(hbox2));
	                                    button.setText("Scores Submitted");
	                                    button.setDisable(true);
	                                }
	                            }
	                            // case when the score could not be parsed
	                            catch(NumberFormatException e) 
	                            {
	                                button.setText("Please Resubmit Scores");
	                            }
	                        }
						}
						    
					});
					
					// creates a VBox to hold both team's HBox and the submit button
					VBox vbox = new VBox(10);
					vbox.setPadding(new Insets(7));
					vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
                    vbox.getChildren().addAll(hbox1, button, hbox2);
                    
                    // adds the vbox to the pane at the proper location
                    GridPane.setConstraints(vbox, i + 1, j);
                    pane.getChildren().add(vbox);
				}
				n = n * 2;
			}
		}
	
		theStage.setScene(scene);
		theStage.show();
	}
	
	/** Identifies the location in the pane where the winner of a given match moves to, and changes
	 *  the "TBD" label to reflect the winner moving on
	 * 
	 * @param winner name of the team who won a match
	 * @param boxID location of the match that took place
	 */
	private void advanceVictor(String winner, int boxID) 
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
            String second= "";
         // there are only two teams, so there is no third place team
            String third = "No third place team";
            
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

            displayChampions(first, second, third);
            return; 
		}
		else 
		{
			// this should never occur
		}
		
		Label l = (Label) allHBoxes.get(boxID).getChildren().get(0);
		l.setText(winner); 
	}
	
	/**
	 * Called from the advanceVictor method when the boxID indicates that it was a championship match- 
	 * This method determines the names of the challengers that took first, second, and third place and 
	 * then calls the displayChampions method
	 * 
	 * @param boxID the location of the match that took place
	 */
	private void generateChampions(int boxID)
	{
	    // first place is in the HBox found at the index of boxID in allHBoxes
        String first = ((Label) allHBoxes.get(boxID).getChildren().get(0)).getText();
        String second = "";
        String third = "";
        
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
        
        displayChampions(first, second, third);
        return;
	}
	
	/** 
	 * Displays the names of the teams that took first, second, and third in the tournament
	 * 
	 * @param first is the team that took first place
	 * @param second is the team that took second place
	 * @param third is the team that took third place
	 */
	private void displayChampions(String first, String second, String third) 
	{
	    // creates Labels to display the name of the tournament champion
		Label champion = new Label();
        champion.setMinWidth(150);
		champion.setText("Tournament Champion: ");
		Label championName = new Label();
        championName.setAlignment(Pos.CENTER);
		championName.setMinWidth(100);		
		championName.setText(first);
		
		// creates Labels to display the name of the team that took second
		Label secondPlace = new Label();
        secondPlace.setMinWidth(150);
		secondPlace.setText("Second Place: ");
		Label secondName = new Label();
        secondName.setAlignment(Pos.CENTER);
		secondName.setMinWidth(100);		
		secondName.setText(second);
		
		// creates Labels to display the name of the team that took second
		Label thirdPlace = new Label();
        thirdPlace.setMinWidth(150);
		thirdPlace.setText("Third Place: ");
		Label thirdName = new Label();
        thirdName.setAlignment(Pos.CENTER);
		thirdName.setMinWidth(100);		
		thirdName.setText(third);
		
		// creates HBoxes to store the two labels displaying the results of the tournament
		HBox number1 = new HBox(10);
		number1.getChildren().addAll(champion, championName);
		HBox number2 = new HBox(10);
        number2.getChildren().addAll(secondPlace, secondName);
        HBox number3 = new HBox(10);
        number3.getChildren().addAll(thirdPlace, thirdName);
        
        // creates a VBox to store the HBoxes containing the top three teams
		VBox vbox = new VBox(10);
		vbox.setPadding(new Insets(7));
        vbox.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
        vbox.getChildren().addAll(number1, number2, number3);
		
        // adds the VBox containing the results of the tournament to the pane
        GridPane.setConstraints(vbox, (((int)(Math.log(chalNum)/Math.log(2))) + 1), 0);
        pane.getChildren().add(vbox);
        
		theStage.show();
	}
}
