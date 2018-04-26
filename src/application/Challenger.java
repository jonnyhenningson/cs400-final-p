package application;

public class Challenger implements Comparable<Challenger> 
{
	private int seed = -1;
	private String name = null;

	public Challenger(String name, int seed) 
	{
		this.seed = seed;
		this.name = name;
	}

	public int getSeed()
	{
		return seed;
	}

	public void setSeed(int seed) 
	{
		this.seed = seed;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

	
	@Override
	public int compareTo(Challenger c) 
	{
		return (seed > c.getSeed()) ? 1 : ((seed == c.getSeed()) ? 0 : -1);
	}
}
