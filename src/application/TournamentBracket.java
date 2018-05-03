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
//                          Wei Penghai, Jichen Zhang
// Emails:                  jhenningson@wisc.edu, zhan66@wisc.edu, thenning2@wisc.edu,
//                          WEI EMAIL HERE, JICHEN EMAIL HERE
//
////////////////////////////////////////////////////////////////////////////////////////////////////

package application;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a Tournament Bracket containing a given number of Challengers (Max 16)
 * 
 * @author Jonny Henningson, Tyler Henning, Jerry Han
 */
public class TournamentBracket 
{
    // stores a list of all Challengers present for the tournament
	private List<Challenger> chals; 
	
	/**
	 * Constructor used to initialize a new TournamentBracket object
	 * 
	 * @param challengers is the list of Challenger objects to be included in the bracket
	 */
	public TournamentBracket(List<Challenger> challengers) 
	{
		chals = challengers;
		Collections.sort(chals);
	}
	
	/**
	 * Getter method used to retrieve the list of Challengers in the bracket
	 * 
	 * @return the list of Challengers stored in the chals field
	 */
	public List<Challenger> getChals() 
	{
		return chals;
	}

	/** 
	 * Used to read a file containing a list of the teams in the tournament bracket
	 * 
	 * @param fileName is the path of the file to be read
	 * @return list is the list of all of the challengers read in from the file
	 * @throws IOException if fileName is an invalid path
	 */
	public static List<Challenger> readFile(String fileName) throws IOException 
	{
	    // get stream
		Stream<String> chalData = Files.lines(Paths.get(fileName)); 
		// remove all empty strings
		chalData = chalData.filter(x -> x != null && !x.trim().equals("")); 
		// converts stream to a String list of Challengers
		List<String> challengers = chalData.collect(Collectors.toList());
		chalData.close(); 
		
		// stores the list of Challenger objects that will eventually be returned
		ArrayList<Challenger> retrn = new ArrayList<Challenger>();
		
		// creates a new Challenger object for each team read  
		// in from the file and adds it to the retrn list
		for(int i = 0; i < challengers.size(); i++)
		{
		    retrn.add(new Challenger(challengers.get(i).trim(), i)); 
		}
		
		return retrn;
	}
}
