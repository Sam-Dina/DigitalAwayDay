/**
 * 
 */
package com.deloitte.writer.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.deloitte.model.Activity;
import com.deloitte.model.Team;

/**
 * @author ssamal
 *
 */
public class ActivityFileWriterImplTest {
	private List<Team> mTeams;

	private List<Activity> mActivities;

	private String mOutput;

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
	public void testWriter() throws IOException {
		ActivityFileWriterImpl activityFileWriter = new ActivityFileWriterImpl("output.txt");
		activityFileWriter.write(mTeams);

		assertEquals(mOutput, new String(Files.readAllBytes(Paths.get("output.txt")), StandardCharsets.UTF_8));
	}

	@Test(expected = IOException.class)
	public void testWriterException() throws IOException {
		ActivityFileWriterImpl activityFileWriter = new ActivityFileWriterImpl("");
		activityFileWriter.write(mTeams);
	}
}
