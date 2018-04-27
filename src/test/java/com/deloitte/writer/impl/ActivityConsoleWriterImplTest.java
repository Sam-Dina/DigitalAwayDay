/**
 * 
 */
package com.deloitte.writer.impl;

import static org.junit.Assert.assertEquals;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;

import com.deloitte.model.Activity;
import com.deloitte.model.Team;

/**
 * @author ssamal
 *
 */
public class ActivityConsoleWriterImplTest {

	private List<Team> mTeams;

	private List<Activity> mActivities;

	private String mOutput;

	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Before
	public void doBefore() {
		mActivities = new ArrayList<>();

		Activity a1 = new Activity("A1", "30", LocalTime.of(9, 0), LocalTime.of(9, 30));
		mActivities.add(a1);

		mTeams = new ArrayList<>();

		Team t1 = new Team("1");
		t1.setActivities(mActivities);
		mTeams.add(t1);

		Team t2 = new Team("2");
		t2.setActivities(mActivities);
		mTeams.add(t2);

		mOutput = new StringBuilder().append(System.lineSeparator()).append("Team 1 : ").append(System.lineSeparator())
				.append("09:00 AM : A1 30").append(System.lineSeparator()).append(System.lineSeparator())
				.append("Team 2 : ").append(System.lineSeparator()).append("09:00 AM : A1 30")
				.append(System.lineSeparator()).toString();
	}

	@Test
	public void testWriter() {
		ActivityConsoleWriterImpl activityConsoleWriter = new ActivityConsoleWriterImpl();
		activityConsoleWriter.write(mTeams);

		assertEquals(mOutput, systemOutRule.getLog());
	}

}
