/**
 * 
 */
package com.deloitte.writer.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.deloitte.model.Activity;
import com.deloitte.model.Team;
import com.deloitte.writer.ActivityWriter;

/**
 * @author ssamal
 *
 */
public class ActivityFileWriterImpl implements ActivityWriter {

	private String mName;

	public ActivityFileWriterImpl(String pName) {
		setName(pName);
	}

	@Override
	public void write(List<Team> pTeams) throws IOException {

		StringBuilder output = new StringBuilder();

		for (Team team : pTeams) {

			output.append(System.lineSeparator()).append("Team ").append(team.toString())
					.append(System.lineSeparator());

			for (Activity activity : team.getActivities()) {
				output.append(activity.toString()).append(System.lineSeparator());
			}
		}

		Files.write(Paths.get(getName()), output.toString().getBytes(), StandardOpenOption.CREATE,
				StandardOpenOption.TRUNCATE_EXISTING);

	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param pName
	 *            the mName to set
	 */
	public void setName(String pName) {
		this.mName = pName;
	}

}
