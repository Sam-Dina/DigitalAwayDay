/**
 * 
 */
package com.deloitte.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author ssamal
 *
 */
public class Activity {

	private String mName;

	private String mDuration;

	private LocalTime mStartTime;

	private LocalTime mEndTime;

	public Activity(String pName, String pDuration) {
		setName(pName);
		setDuration(pDuration);
	}

	/**
	 * 
	 * @param pName
	 * @param pDuration
	 * @param pStartTime
	 * @param pEndTime
	 */
	public Activity(String pName, String pDuration, LocalTime pStartTime, LocalTime pEndTime) {
		setName(pName);
		setDuration(pDuration);
		setStartTime(pStartTime);
		setEndTime(pEndTime);
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

	/**
	 * @return the mDuration
	 */
	public String getDuration() {
		return mDuration;
	}

	/**
	 * @param pDuration
	 *            the mDuration to set
	 */
	public void setDuration(String pDuration) {
		this.mDuration = pDuration;
	}

	/**
	 * @return the mStartTime
	 */
	public LocalTime getStartTime() {
		return mStartTime;
	}

	/**
	 * @param pStartTime
	 *            the mStartTime to set
	 */
	public void setStartTime(LocalTime pStartTime) {
		this.mStartTime = pStartTime;
	}

	/**
	 * @return the mEndTime
	 */
	public LocalTime getEndTime() {
		return mEndTime;
	}

	/**
	 * @param pEndTime
	 *            the mEndTime to set
	 */
	public void setEndTime(LocalTime pEndTime) {
		this.mEndTime = pEndTime;
	}

	@Override
	public String toString() {
		return new StringBuilder().append(this.mStartTime.format(DateTimeFormatter.ofPattern("hh:mm a"))).append(" : ")
				.append(this.mName).append(" ").append(this.mDuration).toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((mDuration == null) ? 0 : mDuration.hashCode());
		result = prime * result + ((mEndTime == null) ? 0 : mEndTime.hashCode());
		result = prime * result + ((mName == null) ? 0 : mName.hashCode());
		result = prime * result + ((mStartTime == null) ? 0 : mStartTime.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Activity other = (Activity) obj;
		if (mDuration == null) {
			if (other.mDuration != null)
				return false;
		} else if (!mDuration.equals(other.mDuration))
			return false;
		if (mEndTime == null) {
			if (other.mEndTime != null)
				return false;
		} else if (!mEndTime.equals(other.mEndTime))
			return false;
		if (mName == null) {
			if (other.mName != null)
				return false;
		} else if (!mName.equals(other.mName))
			return false;
		if (mStartTime == null) {
			if (other.mStartTime != null)
				return false;
		} else if (!mStartTime.equals(other.mStartTime))
			return false;
		return true;
	}

}
