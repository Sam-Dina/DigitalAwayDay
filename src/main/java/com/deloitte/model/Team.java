/**
 * 
 */
package com.deloitte.model;

import java.util.List;

/**
 * @author ssamal
 *
 */
public class Team {

	private String mName;

	private List<Activity> mActivities;

	public Team(String pName) {
		setName(pName);
	}

	public Team(String pName, List<Activity> pActivities) {
		setName(pName);
		setActivities(pActivities);
	}

	/**
	 * @return the mName
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param mName
	 *            the mName to set
	 */
	public void setName(String mName) {
		this.mName = mName;
	}

	/**
	 * @return the mActivities
	 */
	public List<Activity> getActivities() {
		return mActivities;
	}

	/**
	 * @param mActivities
	 *            the mActivities to set
	 */
	public void setActivities(List<Activity> mActivities) {
		this.mActivities = mActivities;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.mName).append(" : ").toString();
	}
}
