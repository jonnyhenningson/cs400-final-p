/////////////////////////////// ALL ASSIGNMENTS INCLUDE THIS SECTION ///////////////////////////////
//
// Title:                   Tournament Bracket
// Due Date:                May 3rd, 2018
// Submission Files:        Challenger.java, GUIInterface.java, TournamentBracket.java, teamList.txt 
// 
// Course:                  CS400 Spring 2018
// Other Sources:           None
// Known Bugs:              None
//
// Authors:                 Jonny Henningson, Zhengda (Jerry) Han, Tyler Henning
// Emails:                  jhenningson@wisc.edu, zhan66@wisc.edu, thenning2@wisc.edu
//
////////////////////////////////////////////////////////////////////////////////////////////////////

package application;

/**
 * Represents a challenger object in the tournament bracket
 * 
 * @author Jonny Henningson, Tyler Henning, Jerry Han
 *
 */
public class Challenger implements Comparable<Challenger> 
{
    // stores the Challenger's seed in the tournament
	private int seed;
	// stores the name of the team
	private String name;

	/**
	 * Constructor used to initialize a new Challenger object
	 * 
	 * @param name is the name of the challenger
	 * @param seed is the seed of the challenger at the start of the tournament
	 */
	public Challenger(String name, int seed) 
	{
		this.seed = seed;
		this.name = name;
	}

	/**
	 * Getter method used to retrieve the seed of a Challenger object
	 * 
	 * @return the value in the seed field of a Challenger
	 */
	public int getSeed()
	{
		return seed;
	}

	/**
	 * Getter method used to retrieve the name of a Challenger object
	 * 
	 * @return the String held in the name field of a Challenger
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Used to compare two Challenger objects based on their seed
	 * 
	 * @param c is the Challenger object whose seed is being compared to this Challenger's seed
	 * @return 1 if this Challenger's seed is greater than c's seed, 0 if the two seeds are equal, 
	 *         -1 if c's seed is greater than this Challenger's seed
	 */
	@Override
	public int compareTo(Challenger c) 
	{
		return (seed > c.getSeed()) ? 1 : ((seed == c.getSeed()) ? 0 : -1);
	}
}
