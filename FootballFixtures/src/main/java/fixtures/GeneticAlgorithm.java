package main.java.fixtures;

/**
 * The class contains the Genetic Algorithm which contains methods for selection, cross-over and mutation
 * 
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import main.java.helper.Match;

public class GeneticAlgorithm 
{
	private static Random random = new Random();
	private static boolean flag = false; 
	
	/**
	 * @return the flag
	 */
	public static boolean getFlag() {
		return flag;
	}

	/**
	 * @param flag the flag to set
	 */
	public static void setFlag(boolean flag) {
		GeneticAlgorithm.flag = flag;
	}

	public static Population runGeneticAlgorithm(Population initialPopulation)
	{
		/**
		 * Sort the initial population on the basis of fitness factor
		 */
		sortPopulation(initialPopulation);
		
		Chromosome[] chromosomes = initialPopulation.getChromosomes();
		
		for(int i = 0; i < chromosomes.length; i++)
		{
			double fitness = chromosomes[i].getFitness();

			if (fitness > 0.900 && fitness <= 1.000)
			{
				flag = true;
				return initialPopulation;
			}
		}
		
		
		/**
		 * find first parent by using k-way tournament selection  
		 */
		Chromosome parent_1 = k_wayParentSelection(chromosomes);		
		
		/**
		 * find second parent by using k-way tournament selection  
		 */
		Chromosome parent_2 = k_wayParentSelection(chromosomes);
		
		while (true)
		{
			if (parent_1 != parent_2)
				break;
			parent_2 = k_wayParentSelection(chromosomes);
		}
		
		Chromosome[] childrens = crossOver(parent_1, parent_2);
		
		Chromosome child_1 = mutate(childrens[0]);
		Chromosome child_2 = mutate(childrens[1]);
		
		Chromosome fitestChromosome_1 = pushChromosomeBackInPop(parent_1, child_1);
		Chromosome fitestChromosome_2 = pushChromosomeBackInPop(parent_2, child_2);
		
		int minVal = Constants.ELITE_FACTOR + 1;

		int firstPosition =  random.nextInt(chromosomes.length-minVal) + minVal;
		chromosomes[firstPosition] =fitestChromosome_1;
		
		int secondPosition =  random.nextInt(chromosomes.length-minVal) + minVal;
		chromosomes[secondPosition] =fitestChromosome_2;
						
		Population newPopulation = new Population(chromosomes,chromosomes.length);
		return newPopulation;
	}
	
	public static Chromosome[] crossOver(Chromosome p1, Chromosome p2) 
	{	
		Match[] parentOneMatches = p1.getMatches();
		ArrayList<Match> parentOneMatchesAL = new ArrayList<Match>();
		for (int i=0;i<parentOneMatches.length;i++)
		{
			parentOneMatchesAL.add(parentOneMatches[i]);
		}
		
		
		Match[] parentTwoMatches = p2.getMatches();
		ArrayList<Match> parentTwoMatchesAL = new ArrayList<Match>();
		for (int i=0;i<parentTwoMatches.length;i++)
		{
			parentTwoMatchesAL.add(parentTwoMatches[i]);
		}
		
		int minimumIndex = 0;
		int maximumIndex = p1.size() -1;
		int rand =random.nextInt(maximumIndex);
		int crossOverPoint =  rand + minimumIndex;		
		
		//child 1
		ArrayList<Match> childOneMatchesAL = new ArrayList<Match>();
		for (int i=0;i<crossOverPoint;i++)
		{
			childOneMatchesAL.add(parentOneMatchesAL.get(i));
		}
		for (int i=crossOverPoint;i<parentTwoMatchesAL.size();i++)
		{
			childOneMatchesAL.add(parentTwoMatchesAL.get(i));
		}
		Match[] childOne = new Match[childOneMatchesAL.size()];
		for (int i=0;i<childOneMatchesAL.size();i++)
		{
			childOne[i] = childOneMatchesAL.get(i);
		}
		Chromosome child1 = new Chromosome(childOne);
		
		
		//child 2
		ArrayList<Match> childTwoMatchesAL = new ArrayList<Match>();
		for (int i=0;i<crossOverPoint;i++)
		{
			childTwoMatchesAL.add(parentTwoMatchesAL.get(i));
		}
		for (int i=crossOverPoint;i<parentOneMatchesAL.size();i++)
		{
			childTwoMatchesAL.add(parentOneMatchesAL.get(i));
		}
		Match[] childTwo = new Match[childTwoMatchesAL.size()];
		for (int i=0;i<childTwoMatchesAL.size();i++)
		{
			childTwo[i] = childTwoMatchesAL.get(i);
		}
		Chromosome child2 = new Chromosome(childTwo);
		
		Chromosome[] setOfChildren = new Chromosome[]{child1, child2};
		return setOfChildren;
		
	}
	
	public static Chromosome mutate (Chromosome c)
	{
		int maximumIndex = c.size() ;
		int numberOfMatchesToChange =  (int) (Constants.MUTATION_FACTOR * (maximumIndex) );
		Match[] matchesPlayed = c.getMatches();
		
		Match[] newMatches = new Match[matchesPlayed.length];
		System.arraycopy(matchesPlayed, 0, newMatches, 0, matchesPlayed.length);
		Chromosome mutatedChromosome = new Chromosome();
		
		while (numberOfMatchesToChange !=0)
		{
			int positionToChange = random.nextInt(c.size());
			Match matchToChange = new Match(FootballData.getTeams().get(random.nextInt(FootballData.getTeams().size())),
            		FootballData.getTeams().get(random.nextInt(FootballData.getTeams().size())),
            		FootballData.getDates().get(random.nextInt(FootballData.getDates().size())), 
            		FootballData.getLocations().get(random.nextInt(FootballData.getLocations().size()))) ;
			newMatches[positionToChange] = matchToChange; 
			 numberOfMatchesToChange --;
		}
		
		mutatedChromosome.setMatches(newMatches);
		return mutatedChromosome;
	}  
	
	/**
	 * The following function select a parent from the population
	 * @param population sorted by it's fitness value
	 * @return chromosome with highest fitness
	 */
	public static Chromosome k_wayParentSelection(Chromosome[] chromosomes)
	{
		
		List<Chromosome> listOfChromosomes = new ArrayList<Chromosome>();
		
		/*** after sorting population in decreasing order first ELITE_FACTOR value chromosomes 
		will not pick for random selection ***/
		
		int minVal = Constants.ELITE_FACTOR + 1;
		int position = 0;
		
		HashMap<Integer, Chromosome> tournaments = new HashMap<Integer, Chromosome>();
		
		for (int i=0; i< Constants.K_FACTOR ;i++)
		{
			//System.out.println("length:" +chromosomes.length);
			position =  random.nextInt(chromosomes.length - minVal) + minVal;
			tournaments.put(position, chromosomes[position]);
		}
		
		for (Entry<Integer, Chromosome> entry : tournaments.entrySet())
			listOfChromosomes.add(entry.getValue());
		
		knuthShuffleParentList(listOfChromosomes);
		
		Collections.sort(listOfChromosomes, new Comparator<Chromosome>() {
            @Override
            public int compare(Chromosome o1, Chromosome o2) {
                return o1.compareTo(o2);
            }
        });
		
		return listOfChromosomes.get(0);
	}
	
	//added new method for deciding between child and parent chromosome
	public static Chromosome pushChromosomeBackInPop (Chromosome parentChromo, Chromosome childChromo)
	{
		if (Math.random() <= Constants.CROSSOVER_RATE)
			return childChromo;
		else 
			return parentChromo;	
	}

	public static void sortPopulation(Population initialPopulation) 
	{
		Arrays.sort(initialPopulation.getChromosomes(), new Comparator<Chromosome>() 
		{
	        public int compare(Chromosome o1, Chromosome o2) {
	            return o1.compareTo(o2);
	        }
	    });			
	}
	
	public static void knuthShuffleParentList(List<Chromosome> kSelectedChromosomes) 
	{ 
		for (int i=0; i < kSelectedChromosomes.size() ; i++)
		{
			int r = random.nextInt(i+1);
			swapChromosmes(kSelectedChromosomes, i, r);
		}
					
	}
	
	private static void swapChromosmes(List<Chromosome> al, int firstPos, int secondPos){
		Collections.swap(al, firstPos, secondPos);
	}
	
	
}

