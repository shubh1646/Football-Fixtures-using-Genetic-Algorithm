/**
 * 
 */
package main.java.fixtures;

/**
 * The class contains the genetic algorithm parameters which are used to run the algorithm
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */
public class Constants 
{
	public static final int POPULATION_SIZE = 100000;
	
	public static final int NUMBER_OF_ROUNDS = 2; 
	
	public static final int ELITE_FACTOR = (int)(0.04 * POPULATION_SIZE);
	
	public static final int K_FACTOR = 100; 
	
	public static final double MUTATION_FACTOR = 0.8;
	
	public static final double CROSSOVER_RATE = 0.8;
	
	public static final int MAX_GENERATION = 100;

	public static final int MAX_COLONY_SIZE = 500;
}
