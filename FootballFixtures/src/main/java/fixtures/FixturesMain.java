
package main.java.fixtures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import main.java.helper.Match;

/**
 * The class contains the main class from which the fixtures are generated if
 * all the constraints are met
 * 
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */

public class FixturesMain {

	static double startTime;
	static double timeInNano = 0.0;
	static long timeInMilliToGetSolution = 0;

	public static void main(String[] args) {

		Configuration.initializeData();

		startTime = System.nanoTime();

		Population population = runAlgorithm(0, Constants.POPULATION_SIZE);

		evaluateAlgorithm(population);

	}

	private static Population runAlgorithm(int from, int to) {
		int size = to - from;

		if (size < Constants.MAX_COLONY_SIZE) {

			return new Population(size);

		} else {

			int mid = from + ((to - from) / 2);

			CompletableFuture<Population> colony_1 = generatePopulation(from, mid);
			CompletableFuture<Population> colony_2 = generatePopulation(mid, to);

			CompletableFuture<Population> combineColonies = colony_1.thenCombine(colony_2,
					(xs1, xs2) -> new Population(xs1.getChromosomes(), xs2.getChromosomes()));

			combineColonies.whenComplete((population, throwable) -> {
				if (throwable != null) {
					System.out.println("Exception throw in thread: " + throwable.getMessage());
					throwable.printStackTrace();
				}

			});

			CompletableFuture.allOf(combineColonies).join();
			try {
				return combineColonies.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
			return null;
		}
	}

	/**
	 * Function creates a new thread for given colony size
	 * 
	 * @param from
	 *            starting index of colony
	 * @param to
	 *            last index of colony
	 * @return Created thread fo type CompletableFuture
	 */
	private static CompletableFuture<Population> generatePopulation(int from, int to) {
		return CompletableFuture.supplyAsync(() -> {
			return runAlgorithm(from, to);
		});
	}

	private static void evaluateAlgorithm(Population population) {

		Population nextGen;

		int maxGeneration = Constants.MAX_GENERATION;

		do {

			nextGen = GeneticAlgorithm.runGeneticAlgorithm(population);
			maxGeneration--;

			if (GeneticAlgorithm.getFlag() == true) {
				break;
			}
			population = nextGen;
		} while (maxGeneration >= 0);

		Chromosome[] temp = population.getChromosomes();

		boolean flag = false;

		for (int i = 0; i < temp.length; i++) {

			if (temp[i].getFitness() == 1.0) {
				flag = true;

				displaySchedule(temp[i], true);

				break;
			}
		}

		if (!flag) {
			
			double maxFitness = -1;
			int position = 0;
			// display chromosome with highest fitness
			for (int i = 0; i < temp.length; i++) {
				if (temp[i].getFitness() > maxFitness) {
					maxFitness = temp[i].getFitness();
					position = i;
				}
			}
			displaySchedule(temp[position], false);

		}

		System.out.println();
		System.out.println("timeInMilli_To_GetSolution:" + timeInMilliToGetSolution);

		System.out.println("Done implementing GA");

	}

	private static void displaySchedule(Chromosome chromosome, boolean bestSol) {

		timeInNano = (System.nanoTime() - startTime);
		timeInMilliToGetSolution = TimeUnit.MILLISECONDS.convert((long) timeInNano, TimeUnit.NANOSECONDS);
		// sort schedule by date
		List<Match> al = new ArrayList<Match>(Arrays.asList(chromosome.getMatches()));
		Collections.sort(al, new Comparator<Match>() {
			public int compare(Match m1, Match m2) {
				return m1.getMatchDate().compareTo(m2.getMatchDate());
			}
		});
		
		if(bestSol){
			System.out.println("Best Schedule: ");
			System.out.println("Fitness: " + chromosome.getFitness());
			
		} else {
			System.out.println("Optimal Solution: ");
		} 

		System.out.println("Date\t\t\t\t|\tHome Team\t\t\t|\tOpponent Team\t\t|\tLocation");
		for (Match m : al) {
			System.out.println(m.getMatchDate() + "\t|\t" + m.getTeamA() + "\t\t\t|\t" + m.getTeamB() + "\t\t|\t"
					+ m.getMatchLocation());
		}

	}

}
