import java.util.List;

public class TournamentBracket {

	public static void Main(String[] args) {
		List<Challenger> challengers=readFile(args[0]);
		TournamentBracket bracket=new TournamentBracket(challengers);
	}
	
	
	private int numOfChallengers=0; //Total number of challengers. Nessesary?
	private List<Challenger> chals; //List of all challengers present for tournament
	
	public TournamentBracket(List<Challenger> challengers) {
		chals=challengers;
	}

	
	
	
	
	
	
	
	public static List<Challenger> readFile(String fileName) {
		//Heyo copied from last assignment
	    // get stream
		Stream<String> dictData = Files.lines(Paths.get(filepath)); 
		// remove all empty strings
		dictData = dictData.filter(x -> x != null && !x.trim().equals("")); 
		// all words upper case and trimmed
		dictData = dictData.map(String::trim); 
		
		
		return dictData;
	}
}