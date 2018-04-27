/**
 * 
 */
package com.deloitte.controller.impl;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

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
public class ActivityControllerImplTest {

	private ActivityReader mActivityFileReader;

	private ActivityReader mEmptyActivityReader;

	private ActivityProcessor mActivityProcessor;

	private ActivityWriter mActivityConsoleWriter;

	private ActivityWriter mActivityFileWriter;

	@Before
	public void doBefore() {
		mActivityFileReader = new ActivityFileReaderImpl("activities.txt");
		mEmptyActivityReader = new ActivityFileReaderImpl("empty.txt");
		mActivityProcessor = new ActivityProcessorImpl();
		mActivityConsoleWriter = new ActivityConsoleWriterImpl();
		mActivityFileWriter = new ActivityFileWriterImpl("output.txt");
	}

	@Test
	public void testConsoleWriter() throws IOException {
		ActivityControllerImpl consoleController = new ActivityControllerImpl(mActivityFileReader, mActivityProcessor,
				mActivityConsoleWriter);
		consoleController.execute();
	}

	@Test
	public void testFileWriter() throws IOException {
		ActivityControllerImpl fileController = new ActivityControllerImpl(mActivityFileReader, mActivityProcessor,
				mActivityFileWriter);
		fileController.execute();
	}

	@Test(expected = IOException.class)
	public void testConsoleWriterIOException() throws IOException {
		ActivityControllerImpl consoleController = new ActivityControllerImpl(mEmptyActivityReader, mActivityProcessor,
				mActivityConsoleWriter);
		consoleController.execute();
	}

	@Test(expected = IOException.class)
	public void testFileWriterIOException() throws IOException {
		ActivityControllerImpl fileController = new ActivityControllerImpl(mEmptyActivityReader, mActivityProcessor,
				mActivityFileWriter);
		fileController.execute();
	}

}
