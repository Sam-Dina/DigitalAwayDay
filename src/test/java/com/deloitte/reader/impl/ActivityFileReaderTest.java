/**
 * 
 */
package com.deloitte.reader.impl;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

/**
 * @author ssamal
 *
 */
public class ActivityFileReaderTest {

	private String mName;

	@Before
	public void doBefore() {
		mName = "activities.txt";
	}

	@Test
	public void testRead() throws IOException {

		ActivityFileReaderImpl activityFileReaderImpl = new ActivityFileReaderImpl(mName);

		String content = activityFileReaderImpl.read();

		assertNotNull(content);

	}

	@Test(expected = IOException.class)
	public void testReadException() throws IOException {

		ActivityFileReaderImpl activityFileReaderImpl = new ActivityFileReaderImpl("");

		String content = activityFileReaderImpl.read();

		assertNotNull(content);

	}

}
