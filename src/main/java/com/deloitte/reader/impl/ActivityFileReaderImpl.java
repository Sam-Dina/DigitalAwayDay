/**
 * 
 */
package com.deloitte.reader.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.deloitte.reader.ActivityReader;

/**
 * @author ssamal
 *
 */
public class ActivityFileReaderImpl implements ActivityReader {

	private String mName;

	public ActivityFileReaderImpl(String pName) {
		setName(pName);
	}

	@Override
	public String read() throws IOException {
		return new String(Files.readAllBytes(Paths.get(getName())), StandardCharsets.UTF_8);
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

}
