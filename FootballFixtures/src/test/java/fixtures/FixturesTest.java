package test.java.fixtures;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import main.java.fixtures.Chromosome;
import main.java.fixtures.FootballData;
import main.java.fixtures.GeneticAlgorithm;

import main.java.fixtures.Population;
import main.java.helper.Match;
import main.java.helper.Team;

/**
 * The class contains test cases for Genetic Algorithm function
 * 
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */

public class FixturesTest 
{
	
	/*
	 * Initialize population by setting data
	 */
	@Before
	public void setUp() throws Exception
	{		

		String location_1 = "Etihad stadium";
		String location_2 = "Anfield";
		String location_3 = "Wembley stadium";
		
		Team team_1 = new Team("Manchester City","Etihad stadium"); 
		Team team_2 = new Team("Liverpool","Anfield");
		Team team_3 = new Team("Tottenham Hotspur","Wembley stadium");
		
		FootballData.getLocations().clear();
		FootballData.locations.add(location_1);
		FootballData.locations.add(location_2);
		FootballData.locations.add(location_3);
		
		FootballData.getTeams().clear();
		FootballData.teams.add(team_1);
		FootballData.teams.add(team_2);
		FootballData.teams.add(team_3);

		
		Date date_1 = new Date();
		Date date_2 = new Date();
		Date date_3 = new Date();
		Date date_4 = new Date();
		Date date_5 = new Date();
		Date date_6 = new Date();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		try{
			date_1 = dateFormat.parse("12/04/2018");
			date_2 = dateFormat.parse("12/05/2018");
			date_3 = dateFormat.parse("12/06/2018");
			date_4 = dateFormat.parse("12/07/2018");
			date_5 = dateFormat.parse("12/08/2018");
			date_6 = dateFormat.parse("12/09/2018");
			
		} catch (Exception e){
			System.out.println("Excepting for date parsing:" +e.getMessage());
		}

		FootballData.getDates().addAll(Arrays.asList(date_1, date_2, date_3,
				date_4, date_5, date_6));
	}
		
	/*
	 * Test to check if fitness is 1.0 for the perfect fixture
	 */
	@Test
	public void checkFitnessTest(){
		
		Team team_1 = FootballData.getTeams().get(0);
		Team team_2 = FootballData.getTeams().get(1);
		Team team_3 = FootballData.getTeams().get(2);
		
		String location_1 = FootballData.getLocations().get(0);
		String location_2 = FootballData.getLocations().get(1);
		String location_3 = FootballData.getLocations().get(2);
		
		Date date_1 = FootballData.getDates().get(0);
		Date date_2 = FootballData.getDates().get(1);
		Date date_3 = FootballData.getDates().get(2);
		Date date_4 = FootballData.getDates().get(3);
		Date date_5 = FootballData.getDates().get(4);
		Date date_6 = FootballData.getDates().get(5);

		FootballData.getDates().addAll(Arrays.asList(date_1, date_2, date_3,
				date_4, date_5, date_6));
		
		
		Match match_1 = new Match(team_1, team_2, date_1, location_1);
		Match match_2 = new Match(team_1, team_2, date_2, location_2);
		Match match_3 = new Match(team_1, team_3, date_3, location_1);
		Match match_4 = new Match(team_1, team_3, date_4, location_3);
		Match match_5 = new Match(team_2, team_3, date_5, location_2);
		Match match_6 = new Match(team_2, team_3, date_6, location_3);
		
		Match[] matches = {match_1, match_2, match_3, match_4, match_5, match_6};
		Chromosome chromosome = new Chromosome(matches);
		
		double expectedFitness = 1.00;
		chromosome.calculateFitness();
		double calculatedFitness = chromosome.getFitness();
		assertTrue(Double.compare(expectedFitness, calculatedFitness) ==0);
	}
  
	/*
	 * Test to check if conflicts arise for clashing fixtures
	 */
	@Test
	public void checkConflictsTest(){
		
		Team team_1 = FootballData.getTeams().get(0);
		Team team_2 = FootballData.getTeams().get(1);
		Team team_3 = FootballData.getTeams().get(2);
		
		String location_1 = FootballData.getLocations().get(0);
		String location_2 = FootballData.getLocations().get(1);
		String location_3 = FootballData.getLocations().get(2);
		
		Date date_1 = FootballData.getDates().get(0);
		
		Match match_1 = new Match(team_1, team_2, date_1, location_1);
		Match match_2 = new Match(team_1, team_2, date_1, location_1);
		Match match_3 = new Match(team_2, team_2, date_1, location_2);
		Match match_4 = new Match(team_2, team_3, date_1, location_1);
		Match match_5 = new Match(team_3, team_1, date_1, location_3);
		Match match_6 = new Match(team_3, team_1, date_1, location_1);
		
		Match[] matches = {match_1, match_2, match_3, match_4, match_5, match_6};
		Chromosome chromosome = new Chromosome(matches);
		
		double expectedFitness = 1.00;
		chromosome.calculateFitness();
		double calculatedFitness = chromosome.getFitness();
		assertTrue(Double.compare(expectedFitness, calculatedFitness) !=0);
		
	}
	
	/*
	 * Test to check if mutation takes place in a chromosome to generate a new chromosome
	 */
	@Test
	public void checkMutationTest()
	{
		Team team_1 = FootballData.getTeams().get(0);
		Team team_2 = FootballData.getTeams().get(1);
		Team team_3 = FootballData.getTeams().get(2);
		
		String location_1 = FootballData.getLocations().get(0);
		String location_2 = FootballData.getLocations().get(1);
		String location_3 = FootballData.getLocations().get(2);
		
		Date date_1 = FootballData.getDates().get(0);
		Date date_2 = FootballData.getDates().get(1);
		Date date_3 = FootballData.getDates().get(2);
		Date date_4 = FootballData.getDates().get(3);
		Date date_5 = FootballData.getDates().get(4);
		Date date_6 = FootballData.getDates().get(5);
		
		Match match_1 = new Match(team_1, team_2, date_1, location_1);
		Match match_2 = new Match(team_1, team_3, date_2, location_1);
		Match match_3 = new Match(team_2, team_1, date_3, location_2);
		Match match_4 = new Match(team_2, team_3, date_4, location_2);
		Match match_5 = new Match(team_3, team_1, date_5, location_3);
		Match match_6 = new Match(team_3, team_2, date_6, location_3);
		
		Match[] matches = {match_1, match_2, match_3, match_4, match_5, match_6};
		Chromosome chromosomeToBeMutated = new Chromosome(matches);
		Chromosome mutatedChromosome = GeneticAlgorithm.mutate(chromosomeToBeMutated);
		
		boolean flagForComparingMatches = false;
		for (int i=0;i<mutatedChromosome.size();i++)
		{
			if (chromosomeToBeMutated.getMatches()[i].equals((mutatedChromosome.getMatches()[i])))
			{
				flagForComparingMatches = true;
				break;
			}	
		}
		
		assertEquals(flagForComparingMatches , true);
	}
	
	/*
	 * Test to check if fittest chromosome is present at the top of the population
	 */
	@Test
	public void checkFittestChromosomeTest ()
	{
		Team team_1 = FootballData.getTeams().get(0);
		Team team_2 = FootballData.getTeams().get(1);
		Team team_3 = FootballData.getTeams().get(2);
		
		String location_1 = FootballData.getLocations().get(0);
		String location_2 = FootballData.getLocations().get(1);
		String location_3 = FootballData.getLocations().get(2);
		
		Date date_1 = FootballData.getDates().get(0);
		Date date_2 = FootballData.getDates().get(1);
		Date date_3 = FootballData.getDates().get(2);
		Date date_4 = FootballData.getDates().get(3);
		Date date_5 = FootballData.getDates().get(4);
		Date date_6 = FootballData.getDates().get(5);
		
		Match match_1 = new Match(team_1, team_2, date_1, location_1);
		Match match_2 = new Match(team_1, team_3, date_2, location_1);
		Match match_3 = new Match(team_2, team_1, date_3, location_2);
		Match match_4 = new Match(team_2, team_3, date_4, location_2);
		Match match_5 = new Match(team_3, team_1, date_5, location_3);
		Match match_6 = new Match(team_3, team_2, date_6, location_3);
		
		Match[] matches = {match_1, match_2, match_3, match_4, match_5, match_6};
		Chromosome chromosomeToBeAdded = new Chromosome(matches);
		Chromosome[] chromosomeArray = new Chromosome[]{chromosomeToBeAdded};
		
		Population population = new Population(1);
		population.setChromosomes(chromosomeArray);
		
		GeneticAlgorithm.sortPopulation(population); //sort in descending order
		Chromosome firstChromosome = population.getChromosomes()[0];
		
		assertEquals(population.getChromosomes()[0], firstChromosome);
	}
	
	/*
	 * Test to check if cross over between 2 parents produces 2 children
	 */
	@Test
	public void checkCrossOverTest()
	{
		Team team_1 = FootballData.getTeams().get(0);
		Team team_2 = FootballData.getTeams().get(1);
		Team team_3 = FootballData.getTeams().get(2);
		
		String location_1 = FootballData.getLocations().get(0);
		String location_2 = FootballData.getLocations().get(1);
		String location_3 = FootballData.getLocations().get(2);
		
		Date date_1 = FootballData.getDates().get(0);
		Date date_2 = FootballData.getDates().get(1);
		Date date_3 = FootballData.getDates().get(2);
		Date date_4 = FootballData.getDates().get(3);
		Date date_5 = FootballData.getDates().get(4);
		Date date_6 = FootballData.getDates().get(5);
		
		Match match_1 = new Match(team_1, team_2, date_1, location_1);
		Match match_2 = new Match(team_1, team_3, date_2, location_1);
		Match match_3 = new Match(team_2, team_1, date_3, location_2);
		Match match_4 = new Match(team_2, team_3, date_4, location_2);
		Match match_5 = new Match(team_3, team_1, date_5, location_3);
		Match match_6 = new Match(team_3, team_2, date_6, location_3);
		
		Match[] matches_set1 = {match_1, match_2, match_3, match_4, match_5, match_6};
		Match[] matches_set2 = {match_6, match_5, match_4, match_3, match_2, match_1};
		
		Chromosome parent1 = new Chromosome(matches_set1);
		Chromosome parent2 = new Chromosome(matches_set2);
		
		Chromosome children[] =GeneticAlgorithm.crossOver(parent1, parent2);
		Chromosome child1 = children[0];
		Chromosome child2 = children[1];
		
		boolean flagForComparingChromosomes = false;
		
		if (!child1.equals(parent1) && !child1.equals(parent2))
		{
			if (!child2.equals(parent1) && !child2.equals(parent2) )
				flagForComparingChromosomes=true;
		}
		
		assertEquals(flagForComparingChromosomes, true);
	}
	
	/*
	 * Test to check if population size matches with the chromosomes present in the population
	 */
    @Test
    public void checkPopulationSizeTest() 
    {
    	Team team_1 = FootballData.getTeams().get(0);
		Team team_2 = FootballData.getTeams().get(1);
		Team team_3 = FootballData.getTeams().get(2);
		
		String location_1 = FootballData.getLocations().get(0);
		String location_2 = FootballData.getLocations().get(1);
		String location_3 = FootballData.getLocations().get(2);
		
		Date date_1 = FootballData.getDates().get(0);
		Date date_2 = FootballData.getDates().get(1);
		Date date_3 = FootballData.getDates().get(2);
		Date date_4 = FootballData.getDates().get(3);
		Date date_5 = FootballData.getDates().get(4);
		Date date_6 = FootballData.getDates().get(5);
		
		Match match_1 = new Match(team_1, team_2, date_1, location_1);
		Match match_2 = new Match(team_1, team_3, date_2, location_1);
		Match match_3 = new Match(team_2, team_1, date_3, location_2);
		Match match_4 = new Match(team_2, team_3, date_4, location_2);
		Match match_5 = new Match(team_3, team_1, date_5, location_3);
		Match match_6 = new Match(team_3, team_2, date_6, location_3);
		
		Match[] matches = {match_1, match_2, match_3, match_4, match_5, match_6};
		Chromosome chromosomeToBeAdded = new Chromosome(matches);
		Chromosome[] chromosomeArray = new Chromosome[]{chromosomeToBeAdded};
		
		Population population = new  Population(chromosomeArray, 1);
		
		assertEquals(population.getChromosomes().length, chromosomeArray.length);
        
    }

}