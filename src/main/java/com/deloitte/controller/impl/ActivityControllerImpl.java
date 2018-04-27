/**
 * 
 */
package com.deloitte.controller.impl;

import java.io.IOException;

import com.deloitte.controller.Controller;
import com.deloitte.processor.ActivityProcessor;
import com.deloitte.reader.ActivityReader;
import com.deloitte.writer.ActivityWriter;

/**
 * @author ssamal
 *
 */
public class ActivityControllerImpl implements Controller {

	private ActivityReader mActivityReader;

	private ActivityWriter mActivityWriter;

	private ActivityProcessor mActivityProcessor;

	public ActivityControllerImpl(ActivityReader pActivityReader, ActivityProcessor pActivityProcessor,
			ActivityWriter pActivityWriter) {
		setActivityReader(pActivityReader);
		setActivityProcessor(pActivityProcessor);
		setActivityWriter(pActivityWriter);
	}

	@Override
	public void execute() throws IOException {
		getActivityWriter().write(getActivityProcessor().process(getActivityReader().read()));
	}

	/**
	 * @return the mActivityReader
	 */
	public ActivityReader getActivityReader() {
		return mActivityReader;
	}

	/**
	 * @param mActivityReader
	 *            the mActivityReader to set
	 */
	public void setActivityReader(ActivityReader mActivityReader) {
		this.mActivityReader = mActivityReader;
	}

	/**
	 * @return the mActivityWriter
	 */
	public ActivityWriter getActivityWriter() {
		return mActivityWriter;
	}

	/**
	 * @param mActivityWriter
	 *            the mActivityWriter to set
	 */
	public void setActivityWriter(ActivityWriter mActivityWriter) {
		this.mActivityWriter = mActivityWriter;
	}

	/**
	 * @return the mActivityProcessor
	 */
	public ActivityProcessor getActivityProcessor() {
		return mActivityProcessor;
	}

	/**
	 * @param pActivityProcessor
	 *            the mActivityProcessor to set
	 */
	public void setActivityProcessor(ActivityProcessor pActivityProcessor) {
		this.mActivityProcessor = pActivityProcessor;
	}

}
