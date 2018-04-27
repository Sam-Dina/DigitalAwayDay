/**
 * 
 */
package com.deloitte.processor.impl;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.deloitte.model.Activity;
import com.deloitte.model.Team;
import com.deloitte.processor.ActivityProcessor;

/**
 * @author ssamal
 *
 */
public class ActivityProcessorImpl implements ActivityProcessor {

	private int mSprint;

	private int mTeams;

	private LocalTime mEventTime;

	private LocalTime mEventEndTime;

	private Activity mDefaultActivity;

	public ActivityProcessorImpl() {
		setSprint(15);
		setTeams(2);
		setEventTime(LocalTime.of(9, 0));
		setEventEndTime(LocalTime.of(17, 0));
		setDefaultActivity(new Activity("Lunch Break", "60min", LocalTime.of(12, 0), LocalTime.of(13, 0)));
	}

	public ActivityProcessorImpl(int pSprint, int pTeamSize, LocalTime pEventTime, LocalTime pEventEndTime,
			Activity pDefaultActivity) {
		setSprint(pSprint);
		setTeams(pTeamSize);
		setEventTime(pEventTime);
		setEventEndTime(pEventEndTime);
		setDefaultActivity(pDefaultActivity);
	}

	@Override
	public List<Team> process(String pContent) {

		// Process and Apply Time to the activity
		List<Activity> activities = processActivityTime(processActivityData(pContent));

		// Apply Activity Selection Greedy Algorithm
		List<Team> teams = processTeam(activities);

		return teams;
	}

	protected List<Activity> processActivityData(String pContent) {
		List<Activity> activities = new ArrayList<>();

		try (Scanner scanner = new Scanner(pContent)) {
			while (scanner.hasNextLine()) {
				String activity = scanner.nextLine();

				String activityDuration = matcher(activity,
						Pattern.compile("([0-9]{2}min|sprint)+$", Pattern.CASE_INSENSITIVE));

				/*
				 * TODO - Replace all occurrence of the match ignoring the case - Substitution.
				 * String activityName = line.replaceAll("(?i)" + activityDuration, "");
				 */

				String activityName = activity.substring(0, activity.lastIndexOf(activityDuration)).trim();

				activities.add(new Activity(activityName, activityDuration));
			}
		}
		return activities;
	}

	protected List<Activity> processActivityTime(List<Activity> pActivities) {

		LocalTime activityStartTime = getEventTime();
		LocalTime activityEndTime = getEventTime();

		for (Activity activity : pActivities) {

			String duration = matcher(activity.getDuration(), Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE));

			LocalTime correctedTime = processActivityTimeCheck(activityStartTime, activityEndTime, duration);
			if (null != correctedTime) {
				activityStartTime = correctedTime;
				activityEndTime = correctedTime;
			}

			// Set the start time to end time of the previous activity
			activityStartTime = LocalTime.of(activityEndTime.getHour(), activityEndTime.getMinute());
			activity.setStartTime(activityStartTime);

			// Set the end time by increasing the duration
			activityEndTime = activityEndTime.plusMinutes(duration == "" ? getSprint() : Integer.parseInt(duration));
			activity.setEndTime(activityEndTime);
		}

		return pActivities;
	}

	protected LocalTime processActivityTimeCheck(LocalTime pActivityStartTime, LocalTime pActivityEndTime,
			String pDuration) {

		LocalTime localTime = null;

		LocalTime timeCheck = LocalTime.of(pActivityEndTime.getHour(), pActivityEndTime.getMinute());
		timeCheck = timeCheck.plusMinutes(pDuration == "" ? getSprint() : Integer.parseInt(pDuration));

		// Check Event End Time
		if (timeCheck.isAfter(getEventEndTime())) {
			localTime = getEventTime();
		}

		// Check Default Activity Time
		if ((timeCheck.isAfter(getDefaultActivity().getStartTime())
				&& timeCheck.isBefore(getDefaultActivity().getEndTime()))
				|| (getDefaultActivity().getEndTime().isAfter(pActivityStartTime)
						&& getDefaultActivity().getEndTime().isBefore(timeCheck))) {

			localTime = getDefaultActivity().getEndTime();
		}

		return localTime;
	}

	protected String matcher(String pContent, Pattern pPattern) {
		String content = "";
		Matcher matcher = pPattern.matcher(pContent);
		if (matcher.find()) {
			content = matcher.group();
		}
		return content;
	}

	protected List<Team> processTeam(List<Activity> pActivities) {

		List<Team> teams = new ArrayList<>();

		for (int i = 0; i < getTeams(); i++) {
			Team team = new Team(String.valueOf(i + 1));

			List<Activity> selectedActivities = applyGreedyAlgorithm(selectRamdomActivity(pActivities));

			// Add the default activity and sort the list
			selectedActivities.add(getDefaultActivity());
			selectedActivities.sort((Activity a1, Activity a2) -> a1.getEndTime().compareTo(a2.getEndTime()));

			team.setActivities(selectedActivities);

			teams.add(team);
		}

		return teams;
	}

	protected List<Activity> selectRamdomActivity(List<Activity> pActivities) {
		Random randomGen = new Random();
		List<Activity> activities = new ArrayList<>();
		for (int i = 0; i < pActivities.size(); i++) {
			activities.add(pActivities.get(randomGen.nextInt(pActivities.size())));
		}
		return activities;
	}

	protected List<Activity> applyGreedyAlgorithm(List<Activity> pActivities) {

		pActivities.sort((Activity a1, Activity a2) -> a1.getEndTime().compareTo(a2.getEndTime()));

		List<Activity> selectedActivities = new ArrayList<>();

		LocalTime localTime = LocalTime.of(0, 0);
		for (Activity activity : pActivities) {
			if (activity.getStartTime().isAfter(localTime)) {
				selectedActivities.add(activity);
				localTime = LocalTime.of(activity.getEndTime().getHour(), activity.getEndTime().getMinute());
			}
		}
		return selectedActivities;
	}

	/**
	 * @return the mSprint
	 */
	public int getSprint() {
		return mSprint;
	}

	/**
	 * @param pSprint
	 *            the mSprint to set
	 */
	public void setSprint(int pSprint) {
		this.mSprint = pSprint;
	}

	/**
	 * @return the mTeams
	 */
	public int getTeams() {
		return mTeams;
	}

	/**
	 * @param pTeams
	 *            the mTeams to set
	 */
	public void setTeams(int pTeams) {
		this.mTeams = pTeams;
	}

	/**
	 * @return the mEventTime
	 */
	public LocalTime getEventTime() {
		return mEventTime;
	}

	/**
	 * @param pEventTime
	 *            the mEventTime to set
	 */
	public void setEventTime(LocalTime pEventTime) {
		this.mEventTime = pEventTime;
	}

	/**
	 * @return the mEventEndTime
	 */
	public LocalTime getEventEndTime() {
		return mEventEndTime;
	}

	/**
	 * @param pEventEndTime
	 *            the mEventEndTime to set
	 */
	public void setEventEndTime(LocalTime pEventEndTime) {
		this.mEventEndTime = pEventEndTime;
	}

	/**
	 * @return the mDefaultActivity
	 */
	public Activity getDefaultActivity() {
		return mDefaultActivity;
	}

	/**
	 * @param pDefaultActivity
	 *            the mDefaultActivity to set
	 */
	public void setDefaultActivity(Activity pDefaultActivity) {
		this.mDefaultActivity = pDefaultActivity;
	}

}
