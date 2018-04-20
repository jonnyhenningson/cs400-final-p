import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;




public class TournamentBracket {
	
	private int numOfChallengers=0; //Total number of challengers. Nessesary?
	private List<Challenger> chals; //List of all challengers present for tournament
	
	public TournamentBracket(List<Challenger> challengers) {
		chals=challengers;
		Collections.sort(chals);
		numOfChallengers=chals.size();
	}
	
	public List<Challenger> getChals() {
		return chals;
	}


	/** Currently expects information to be in format Name:Seed, !although I expect it to change.!
	 * (Doing so should not be hard.)
	 * Reads the information on a file and returns a list of Challenger objects
	 * with information as shown on the file.
	 * 
	 * @param fileName path to the file to be read.
	 * @return List of all challengers.
	 * @throws IOException if fileName is invalid path.
	 */
	public static List<Challenger> readFile(String fileName) throws IOException {
		//Heyo copied from last assignment
	    // get stream
		Stream<String> chalData = Files.lines(Paths.get(fileName)); 
		// remove all empty strings
		chalData = chalData.filter(x -> x != null && !x.trim().equals("")); 
		// all words upper case and trimmed
		List<String> challengers = chalData.collect(Collectors.toList());
		chalData.close(); //Yes, it is closed. Screw you eclipse.
		
		ArrayList<Challenger> retrn=new ArrayList<Challenger>();
		String[] f;//Get arrays to store information
		for(String info:challengers) {
			f=info.split(":");//Separate input into name and seed.
			retrn.add(new Challenger(f[0].trim(),Integer.parseInt(f[1].trim())));
		}
		
		for(Challenger c:retrn) {//Test for correct output
			System.out.println(c.getName()+" "+c.getSeed());
		}
		return retrn;
	}


}