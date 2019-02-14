
package main.java.fixtures;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import main.java.helper.Match;
import main.java.helper.Team;

/**
 * The class contains functions related to a chromosom including fitness calculation.
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */
public class Chromosome implements Comparable<Chromosome> {
	private double fitness = 0.00;
	private Match[] matches;

	public Chromosome() {
		int totalNumberOfMatches = ((FootballData.getTeams().size()) * (FootballData.getTeams().size() - 1)) / 2 * Constants.NUMBER_OF_ROUNDS;
		matches = new Match[totalNumberOfMatches];
		generateChromosome();
		calculateFitness();
	}

	public Chromosome(Match[] matches) {
		setMatches(matches);
		calculateFitness();
	}

	private void generateChromosome() {
		Random random = new Random();
		for (int i = 0; i < size(); i++) {
			Team randomTeam = FootballData.getTeams().get(random.nextInt(FootballData.getTeams().size()));
			matches[i] = new Match(randomTeam,
					FootballData.getTeams().get(random.nextInt(FootballData.getTeams().size())),
					FootballData.getDates().get(random.nextInt(FootballData.getDates().size())), randomTeam.getHomeStadium());
		}
	}

	public int size() {
		return matches.length;
	}

	public void calculateFitness() {

		 int conflicts = 0;
		 HashMap<Team, Integer> matchesPlayed = new HashMap<Team, Integer>();
		
		 HashMap<String, ArrayList<Team>> hashMapOfTeamAndOpponentCount = new HashMap<String, ArrayList<Team>>();
		 for (Team t :FootballData.getTeams()) {
			 hashMapOfTeamAndOpponentCount.put(t.getTeamName(), new ArrayList<Team>());
		 }
		// Java 8
		// Map<String, List<Student>> studlistGrouped = studlist.stream().collect(Collectors.groupingBy(w -> w.stud_location));
		
		
		 //Each location (home ground) will have matches of all team which have played there
		 HashMap <String , HashMap<Team, Integer>> locationCount = new HashMap<String , HashMap<Team, Integer>>();
		 for (String loc:FootballData.getLocations()) {
			 locationCount.put(loc, new HashMap<Team,Integer>());
		 }
		
		
		 //A team can't play 2 matches on the same day
		 HashMap<Team, ArrayList<Date>> sameDateChecker = new HashMap<Team,
		 ArrayList<Date>>();
		 for (Team t:FootballData.getTeams())
		 sameDateChecker.put(t, new ArrayList<Date>());
	
		 //iterating through each gene
		 for (int i=0; i<size(); i++)
		 {
			 Match matchSchedule = matches[i];
		
			 //Calculate total matches played by each team
			 Team teamA = matchSchedule.getTeamA();
			 if(matchesPlayed.containsKey(teamA)) {
				 matchesPlayed.put(teamA, matchesPlayed.get(teamA) + 1);
			 }
			 else{
				 matchesPlayed.put(teamA, 1);
			 }
			
			 Team teamB = matchSchedule.getTeamB();
			 if(matchesPlayed.containsKey(teamB)) {
				 matchesPlayed.put(teamB, matchesPlayed.get(teamB) + 1);
			 }
			 else{
				 matchesPlayed.put(teamB, 1);
			 }
		
		
		 //Storing opponents of each team
		 for (Entry<String, ArrayList<Team>> entry : hashMapOfTeamAndOpponentCount.entrySet()) 
		 {
			 String team = entry.getKey();
			 if (team.equals(teamA.getTeamName())) {
				 ArrayList<Team> al = hashMapOfTeamAndOpponentCount.get(team);
				 al.add(teamB);
			 }
			
			 if (team.equals(teamB.getTeamName()) && !(teamA.equals(teamB)) ){
				 ArrayList<Team> al = hashMapOfTeamAndOpponentCount.get(team);
				 al.add(teamA);
			 }
		 }
		
		
		 //1. Team will not play with itself
		 if(teamA == teamB){
			 conflicts++;
		 }
		
		 //4. Two matches cannot take place on the same day and same location
		 for (int j=i+1; j< size() && i< size()-1 ; j++)
		 {
			 if (matchSchedule.getMatchDate().compareTo(matches[j].getMatchDate())==0)
			 {
				 if (matchSchedule.getMatchLocation().equalsIgnoreCase(matches[j].getMatchLocation())) {
					 conflicts++;
				 }
			 }
		 }
		
		 //Each team will play one match at home and one match at opponent ground
		 //equivalent to each location will have 2 home ground matches and each other team will play one match each at that ground
		 String currentLocation = matchSchedule.getMatchLocation();
		
		 HashMap<Team,Integer> locHashMap =locationCount.get(currentLocation);
		 if (currentLocation.equals(teamA.getHomeStadium()) ||currentLocation.equals(teamB.getHomeStadium()))
		 {
			 if (locHashMap.containsKey(teamA))
				 	locHashMap.put(teamA, locHashMap.get(teamA) + 1);
			 else
				 	locHashMap.put(teamA, 1);
		
			 if (locHashMap.containsKey(teamB) && !(teamA.equals(teamB)) )
				 locHashMap.put(teamB, locHashMap.get(teamB) + 1);
			 else
				 locHashMap.put(teamB, 1);
		 }
		 else
			 conflicts++;
		
		 ArrayList<Date> dateForTeamA = sameDateChecker.get(teamA);
		 dateForTeamA.add(matchSchedule.getMatchDate());
		 sameDateChecker.put(teamA, dateForTeamA);
		
		 if (!teamA.getTeamName().equals(teamB.getTeamName()))
		 {
			 ArrayList<Date> dateForTeamB = sameDateChecker.get(teamB);
			 dateForTeamB.add(matchSchedule.getMatchDate());
			 sameDateChecker.put(teamB, dateForTeamB);
		 }

		 
		 }
		 
		
		 for (Entry<Team, ArrayList<Date>> entry : sameDateChecker.entrySet())
		 {
			 boolean flag = containsDuplicates(entry.getValue());
			 if (flag) {
				 conflicts++;
			 }
		
		 }
		
		for (Entry<String, HashMap<Team, Integer>> entry : locationCount.entrySet()) 
		{
			String currentLoc = entry.getKey();
			HashMap<Team,Integer> locHashMap = locationCount.get(currentLoc);
			for (Entry<Team ,Integer> temp : locHashMap.entrySet())
			{
				if (temp.getKey().getHomeStadium().equals(currentLoc)) {
					if (temp.getValue() != (FootballData.getTeams().size() - 1)){
						conflicts++;
					}
				}
			}
		 }
		
		 //Each team will play - (NUMBER OF TEAM - 1) * NUMBER OF ROUNDS matches
		 for (Entry<Team, Integer> entry : matchesPlayed.entrySet())
		 {
			 int numberOfMatches = entry.getValue();
			 if (numberOfMatches != ((FootballData.getTeams().size() - 1) * Constants.NUMBER_OF_ROUNDS))
			 {
				 conflicts++;
				 break;
			 }
		 }
		
		
		 //Each team will play 2 (number of rounds) against every other team
		 for (Entry<String, ArrayList<Team>> entry : hashMapOfTeamAndOpponentCount.entrySet())
		 {
		 ArrayList<Team> temp = entry.getValue();
		 String team = entry.getKey();
		 HashMap <String, Integer> countOfMatchesPlayed = new HashMap<>();
		 for (Team t :temp)
		 {
			 if(countOfMatchesPlayed.containsKey(t.getTeamName()))
			 {
				 countOfMatchesPlayed.put(t.getTeamName(),countOfMatchesPlayed.get(t.getTeamName()) + 1);
			 }
			 else{
				 countOfMatchesPlayed.put(t.getTeamName(), 1);
			 }
		 }
		 for (Entry<String, Integer> single : countOfMatchesPlayed.entrySet())
		 {
			 String opponentTeam = single.getKey();
			 int numberOfMatchesBetween = single.getValue();
			 if (team.equals(opponentTeam)) {
			 }
			 else
			 {
				 if (numberOfMatchesBetween != Constants.NUMBER_OF_ROUNDS){
					 conflicts++;
				 }
			 }
		 }
		
		 //Checking if this team has played matches against EACH other team in the league
		 HashMap <String, Integer> copyOfCountOfMatchesPlayed = new
		 HashMap<>(countOfMatchesPlayed);
		 int counterOfOpponentTeam = 0;
		 for (Entry<String, Integer> single :
		 copyOfCountOfMatchesPlayed.entrySet())
		 {
		 String opponentTeam = single.getKey();
		 if (!team.equals(opponentTeam)) {
		 counterOfOpponentTeam ++;
		 }
		 }
		 if (counterOfOpponentTeam != FootballData.getTeams().size() -1) {
		 conflicts++;
		 }
		 }
		
		 double fitness = 1/(double)(1+conflicts);
		 fitness = BigDecimal.valueOf(fitness).setScale(3, RoundingMode.HALF_UP).doubleValue();
		 setFitness(fitness);
		 //System.out.println("fitness::"+fitness);

	}

	/**
	 * @return the fitness
	 */
	public double getFitness() {
		return fitness;
	}

	/**
	 * @param fitness
	 *            the fitness to set
	 */
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	public ArrayList<Team> removeDuplicates(ArrayList<Team> list) {
		Set<Team> hs = new HashSet<Team>();
		hs.addAll(list);
		list.clear();
		list.addAll(hs);
		return list;
	}

	public boolean containsDuplicates(ArrayList<Date> list) {
		Set<Date> set = new HashSet<Date>(list);
		boolean flag = false;
		if (set.size() < list.size()) {
			/* There are duplicates */
			flag = true;
		}
		return flag;
	}

	/**
	 * @return the matches
	 */
	public Match[] getMatches() {
		return matches;
	}

	/**
	 * @param matches
	 *            the matches to set
	 */
	public void setMatches(Match[] matches) {
		this.matches = matches;
	}

	@Override
	public int compareTo(Chromosome o) {
		return Double.compare(o.getFitness(), this.getFitness());
	}

}
