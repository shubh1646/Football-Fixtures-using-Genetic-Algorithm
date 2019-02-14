package main.java.helper;

import java.util.Date;

/**
 * The class contains the layout of a Gene. A match is a fixture which forms a schedule (chromosome).
 * The match contains both teams, match date and the location.
 * 
 * @author Aditi Jalkote, Mitali Salvi, Shubham Sharma
 */
public class Match 
{
	private Team teamA;
	private Team teamB;
	private Date matchDate;
	private String matchLocation;
	
	/**
	 * @param teamA
	 * @param teamB
	 * @param matchDate
	 * @param matchLocation
	 */
	public Match(Team teamA, Team teamB, Date matchDate, String matchLocation) {
		super();
		this.teamA = teamA;
		this.teamB = teamB;
		this.matchDate = matchDate;
		this.matchLocation = matchLocation;
	}

	/**
	 * @return the teamA
	 */
	public Team getTeamA() {
		return teamA;
	}

	/**
	 * @param teamA the teamA to set
	 */
	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	/**
	 * @return the teamB
	 */
	public Team getTeamB() {
		return teamB;
	}

	/**
	 * @param teamB the teamB to set
	 */
	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	/**
	 * @return the matchDate
	 */
	public Date getMatchDate() {
		return matchDate;
	}

	/**
	 * @param matchDate the matchDate to set
	 */
	public void setMatchDate(Date matchDate) {
		this.matchDate = matchDate;
	}

	/**
	 * @return the matchLocation
	 */
	public String getMatchLocation() {
		return matchLocation;
	}

	/**
	 * @param matchLocation the matchLocation to set
	 */
	public void setMatchLocation(String matchLocation) {
		this.matchLocation = matchLocation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Match: " + teamA.toString() + " vs " + teamB.toString() + " on " + matchDate + "  @ "
				+ matchLocation;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((matchDate == null) ? 0 : matchDate.hashCode());
		result = prime * result + ((matchLocation == null) ? 0 : matchLocation.hashCode());
		result = prime * result + ((teamA == null) ? 0 : teamA.hashCode());
		result = prime * result + ((teamB == null) ? 0 : teamB.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Match other = (Match) obj;
		if (matchDate == null) {
			if (other.matchDate != null)
				return false;
		} else if (!matchDate.equals(other.matchDate))
			return false;
		if (matchLocation == null) {
			if (other.matchLocation != null)
				return false;
		} else if (!matchLocation.equals(other.matchLocation))
			return false;
		if (teamA == null) {
			if (other.teamA != null)
				return false;
		} else if (!teamA.equals(other.teamA))
			return false;
		if (teamB == null) {
			if (other.teamB != null)
				return false;
		} else if (!teamB.equals(other.teamB))
			return false;
		return true;
	}
	
	
	

	
	
	
	
	
	
	


	
	

}
