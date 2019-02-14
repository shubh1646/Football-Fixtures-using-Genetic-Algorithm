
package main.java.fixtures;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import main.java.helper.Team;

/**
 * The class contains the data structures which stores all the data related to fixtures.
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */
public class FootballData 
{

	public static List<Team> teams = new ArrayList<Team>();
	public static List<String> locations = new ArrayList<String>();
	public static List<Date> dates = new ArrayList<Date>();
	
	/**
	 * @return the teams
	 */
	public static List<Team> getTeams() {
		return teams;
	}
	/**
	 * @return the locations
	 */
	public static List<String> getLocations() {
		return locations;
	}
	/**
	 * @return the dates
	 */
	public static List<Date> getDates() {
		return dates;
	}

}