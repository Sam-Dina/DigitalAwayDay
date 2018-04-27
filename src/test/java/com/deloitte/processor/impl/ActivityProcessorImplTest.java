/**
 * 
 */
package com.deloitte.processor.impl;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;

import com.deloitte.model.Activity;
import com.deloitte.model.Team;

/**
 * @author ssamal
 *
 */
public class ActivityProcessorImplTest {

	private int mTeams;

	private int mSprint;

	private String mContent;

	private LocalTime mEventTime;

	private LocalTime mEventEndTime;

	private Activity mDefaultActivity;

	private List<Activity> mActivities;

	private List<Activity> mNoTimeActivities;

	@Before
	public void doBefore() {
		mActivities = new ArrayList<>();
		Activity a1 = new Activity("A1", "30", LocalTime.of(9, 0), LocalTime.of(9, 30));
		mActivities.add(a1);
		Activity a2 = new Activity("A2", "15", LocalTime.of(9, 15), LocalTime.of(9, 30));
		mActivities.add(a2);
		Activity a3 = new Activity("A3", "60", LocalTime.of(9, 30), LocalTime.of(10, 30));
		mActivities.add(a3);
		Activity a4 = new Activity("A4", "30", LocalTime.of(10, 0), LocalTime.of(10, 30));
		mActivities.add(a4);
		Activity a5 = new Activity("A5", "45", LocalTime.of(12, 30), LocalTime.of(13, 15));
		mActivities.add(a5);
		Activity a6 = new Activity("A6", "30", LocalTime.of(15, 0), LocalTime.of(15, 30));
		mActivities.add(a6);

		mNoTimeActivities = new ArrayList<>();
		Activity na1 = new Activity("NA1", "30", null, null);
		mNoTimeActivities.add(na1);
		Activity na2 = new Activity("NA2", "15", null, null);
		mNoTimeActivities.add(na2);
		Activity na3 = new Activity("NA3", "60", null, null);
		mNoTimeActivities.add(na3);
		Activity na4 = new Activity("NA4", "sprint", null, null);
		mNoTimeActivities.add(na4);
		Activity na5 = new Activity("NA5", "45", null, null);
		mNoTimeActivities.add(na5);
		Activity na6 = new Activity("NA6", "30", null, null);
		mNoTimeActivities.add(na6);

		mTeams = 4;
		mSprint = 30;
		mEventTime = LocalTime.of(11, 0);
		mEventEndTime = LocalTime.of(14, 0);
		mContent = new StringBuilder().append("Duck Herding 60min").toString();
		mDefaultActivity = new Activity("Lunch Break", "30Min", LocalTime.of(12, 0), LocalTime.of(12, 30));
	}

	@Test
	public void testDefaultsNotNull() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl();

		assertNotNull(activityProcessor.getTeams());
		assertNotNull(activityProcessor.getSprint());
		assertNotNull(activityProcessor.getDefaultActivity());
		assertNotNull(activityProcessor.getEventTime());
		assertNotNull(activityProcessor.getEventEndTime());
	}

	@Test
	public void testGreedyAlgorithm() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl();
		List<Activity> selectedActivity = activityProcessor.applyGreedyAlgorithm(mActivities);

		assertEquals(4, selectedActivity.size());
		assertEquals(mActivities.get(0), selectedActivity.get(0));
		assertEquals(mActivities.get(4), selectedActivity.get(2));
	}

	@Test
	public void testGreedyAlgorithm_ActivityTime() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl();
		List<Activity> selectedActivity = activityProcessor.applyGreedyAlgorithm(mActivities);

		assertTrue(selectedActivity.get(1).getStartTime().isAfter(selectedActivity.get(0).getEndTime()));
		assertTrue(selectedActivity.get(2).getStartTime().isAfter(selectedActivity.get(1).getEndTime()));
		assertTrue(selectedActivity.get(3).getStartTime().isAfter(selectedActivity.get(2).getEndTime()));
	}

	@Test
	public void testTeamSize() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl(mSprint, mTeams, mEventTime, mEventEndTime,
				mDefaultActivity);
		List<Team> teams = activityProcessor.processTeam(mActivities);

		assertEquals(4, teams.size());
	}

	@Test
	public void testDefaultActivityInAllTeams() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl(mSprint, mTeams, mEventTime, mEventEndTime,
				mDefaultActivity);
		List<Team> teams = activityProcessor.processTeam(mActivities);

		assertTrue(teams.get(0).getActivities().contains(mDefaultActivity));
		assertTrue(teams.get(1).getActivities().contains(mDefaultActivity));
		assertTrue(teams.get(2).getActivities().contains(mDefaultActivity));
		assertTrue(teams.get(3).getActivities().contains(mDefaultActivity));
	}

	@Test
	public void testProcessTime() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl(mSprint, mTeams, mEventTime, mEventEndTime,
				mDefaultActivity);

		List<Activity> activities = activityProcessor.processActivityTime(mNoTimeActivities);

		assertThat(activities,
				IsIterableContainingInAnyOrder.containsInAnyOrder(
						new Activity("NA1", "30", LocalTime.of(11, 00), LocalTime.of(11, 30)),
						new Activity("NA2", "15", LocalTime.of(11, 30), LocalTime.of(11, 45)),
						new Activity("NA3", "60", LocalTime.of(12, 30), LocalTime.of(13, 30)),
						new Activity("NA4", "sprint", LocalTime.of(13, 30), LocalTime.of(14, 00)),
						new Activity("NA5", "45", LocalTime.of(11, 00), LocalTime.of(11, 45)),
						new Activity("NA6", "30", LocalTime.of(12, 30), LocalTime.of(13, 00))));
	}

	@Test
	public void testProcessTimeCheck_EventTime() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl(mSprint, mTeams, mEventTime, mEventEndTime,
				mDefaultActivity);

		LocalTime localTime = activityProcessor.processActivityTimeCheck(LocalTime.of(13, 30), LocalTime.of(13, 45),
				"45");

		assertEquals(LocalTime.of(11, 0), localTime);
	}

	@Test
	public void testProcessTimeCheck_LunchTime() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl(mSprint, mTeams, mEventTime, mEventEndTime,
				mDefaultActivity);

		LocalTime localTime = activityProcessor.processActivityTimeCheck(LocalTime.of(11, 30), LocalTime.of(11, 45),
				"60");

		assertEquals(LocalTime.of(12, 30), localTime);

	}

	@Test
	public void testProcess() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl();

		List<Team> teams = activityProcessor.process(mContent);

		assertThat(teams.get(0).getActivities(),
				hasItems(new Activity("Duck Herding", "60min", LocalTime.of(9, 0), LocalTime.of(10, 0)),
						new Activity("Lunch Break", "60min", LocalTime.of(12, 0), LocalTime.of(13, 0))));

		assertThat(teams.get(1).getActivities(),
				hasItems(new Activity("Duck Herding", "60min", LocalTime.of(9, 0), LocalTime.of(10, 0)),
						new Activity("Lunch Break", "60min", LocalTime.of(12, 0), LocalTime.of(13, 0))));

	}

	@Test
	public void testMatcher_Activity() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl();
		String minute = activityProcessor.matcher("Duck Herding 60min",
				Pattern.compile("([0-9]{2}min|sprint)+$", Pattern.CASE_INSENSITIVE));
		String sprint = activityProcessor.matcher("Duck Herding sprint",
				Pattern.compile("([0-9]{2}min|sprint)+$", Pattern.CASE_INSENSITIVE));

		assertEquals("60min", minute);
		assertEquals("sprint", sprint);
	}

	@Test
	public void testMatcher_Time() {
		ActivityProcessorImpl activityProcessor = new ActivityProcessorImpl();
		String minute = activityProcessor.matcher("Duck Herding 60min",
				Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE));
		String sprint = activityProcessor.matcher("Duck Herding sprint",
				Pattern.compile("\\d+", Pattern.CASE_INSENSITIVE));

		assertEquals("60", minute);
		assertEquals("", sprint);
	}
}
