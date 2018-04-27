/**
 * 
 */
package com.deloitte.writer.impl;

import java.util.List;

import com.deloitte.model.Activity;
import com.deloitte.model.Team;
import com.deloitte.writer.ActivityWriter;

/**
 * @author ssamal
 *
 */
public class ActivityConsoleWriterImpl implements ActivityWriter {

	@Override
	public void write(List<Team> pTeams) {
		for (Team team : pTeams) {

			System.out.println(System.lineSeparator() + "Team " + team.toString());

			for (Activity activity : team.getActivities()) {

				System.out.println(new StringBuilder().append(activity.toString()).toString());
			}
		}
	}

}
