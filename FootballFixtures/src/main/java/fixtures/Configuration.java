package main.java.fixtures;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import main.java.helper.Team;

/**
 * The class contains the data initialise methods which input data to the data structures
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */
public class Configuration {
	
	
	public static void initializeData()
	{
		addTeamNames();
		addLocations();
		addDates();
	}
	
	private static void addDates() {
		
		Date date;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd hh:mm", Locale.ENGLISH).parse("2018-08-10 17:00");

	        int numberOfMatches = ((FootballData.getTeams().size()) * (FootballData.getTeams().size()-1))/2 * Constants.NUMBER_OF_ROUNDS;
	        
	        for(int i = 0; i < numberOfMatches; i++)
	        {
	        	FootballData.dates.add(date);
	        	
	        	// Convert Date to a Calendar
		        Calendar cal = Calendar.getInstance();
		        cal.setTime(date);
		        
		        // mutate the value
		        cal.add(Calendar.DATE, 2);

		        // convert back to Date
		        date = cal.getTime();	
	        }
	        
		} catch (ParseException e) {
			e.printStackTrace();
		}

        
	}

	public static void addTeamNames()
	{
		FootballData.teams.add(new Team("Manchester City","Etihad stadium"));
		FootballData.teams.add(new Team("Liverpool","Anfield"));
		FootballData.teams.add(new Team("Tottenham Hotspur","Wembley stadium"));
		FootballData.teams.add(new Team("Chelsea","Stamford stadium"));
		/*FootballData.teams.add(new Team("Arsenal","Emirates stadium"));
		FootballData.teams.add(new Team("Everton","Goodison park"));
		FootballData.teams.add(new Team("Manchester United", "Old Trafford"));
		FootballData.teams.add(new Team("Watford","Vicarage Road"));*/
	}
	
	public static void addLocations(){
		FootballData.locations.add("Etihad stadium");
		FootballData.locations.add("Anfield");
		FootballData.locations.add("Wembley stadium");
		FootballData.locations.add("Stamford stadium");
		/*FootballData.locations.add("Emirates stadium");
		FootballData.locations.add("Goodison park");
		FootballData.locations.add("Old Trafford");
		FootballData.locations.add("Vicarage Road");*/
	}
	
	
}
