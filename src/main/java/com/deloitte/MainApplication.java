/**
 * 
 */
package com.deloitte;

import java.io.IOException;

import com.deloitte.controller.impl.ActivityControllerImpl;
import com.deloitte.processor.ActivityProcessor;
import com.deloitte.processor.impl.ActivityProcessorImpl;
import com.deloitte.reader.ActivityReader;
import com.deloitte.reader.impl.ActivityFileReaderImpl;
import com.deloitte.writer.ActivityWriter;
import com.deloitte.writer.impl.ActivityConsoleWriterImpl;
import com.deloitte.writer.impl.ActivityFileWriterImpl;

/**
 * @author ssamal
 *
 */
public class MainApplication {

	private static final String INPUT = "activities.txt";

	private static final String OUTPUT = "output.txt";

	private ActivityReader mActivityFileReader;

	private ActivityProcessor mActivityProcessor;

	private ActivityWriter mActivityConsoleWriter;

	private ActivityWriter mActivityFileWriter;

	/**
	 * @param args
	 */
	public static void main(String[] pArgs) {
		try {
			if (pArgs.length == 1) {
				ActivityControllerImpl consoleController = new ActivityControllerImpl(
						new ActivityFileReaderImpl(pArgs[0]), new ActivityProcessorImpl(),
						new ActivityConsoleWriterImpl());
				consoleController.execute();

				return;
			}

			String input = INPUT;
			String output = OUTPUT;

			if (pArgs.length == 2) {
				input = pArgs[0];
				output = pArgs[1];
			}

			ActivityControllerImpl consoleController = new ActivityControllerImpl(new ActivityFileReaderImpl(input),
					new ActivityProcessorImpl(), new ActivityConsoleWriterImpl());
			consoleController.execute();

			ActivityControllerImpl fileController = new ActivityControllerImpl(new ActivityFileReaderImpl(input),
					new ActivityProcessorImpl(), new ActivityFileWriterImpl(output));
			fileController.execute();

		} catch (IOException e) {
			// TODO Handle Exception
			e.printStackTrace();
		}
	}

	/**
	 * @return the mActivityFileReader
	 */
	public ActivityReader getActivityFileReader() {
		return mActivityFileReader;
	}

	/**
	 * @param mActivityFileReader
	 *            the mActivityFileReader to set
	 */
	public void setActivityFileReader(ActivityReader mActivityFileReader) {
		this.mActivityFileReader = mActivityFileReader;
	}

	/**
	 * @return the mActivityProcessor
	 */
	public ActivityProcessor getActivityProcessor() {
		return mActivityProcessor;
	}

	/**
	 * @param mActivityProcessor
	 *            the mActivityProcessor to set
	 */
	public void setActivityProcessor(ActivityProcessor mActivityProcessor) {
		this.mActivityProcessor = mActivityProcessor;
	}

	/**
	 * @return the mActivityConsoleWriter
	 */
	public ActivityWriter getActivityConsoleWriter() {
		return mActivityConsoleWriter;
	}

	/**
	 * @param mActivityConsoleWriter
	 *            the mActivityConsoleWriter to set
	 */
	public void setActivityConsoleWriter(ActivityWriter mActivityConsoleWriter) {
		this.mActivityConsoleWriter = mActivityConsoleWriter;
	}

	/**
	 * @return the mActivityFileWriter
	 */
	public ActivityWriter getActivityFileWriter() {
		return mActivityFileWriter;
	}

	/**
	 * @param mActivityFileWriter
	 *            the mActivityFileWriter to set
	 */
	public void setActivityFileWriter(ActivityWriter mActivityFileWriter) {
		this.mActivityFileWriter = mActivityFileWriter;
	}

}
