/**
 * 
 */
package com.deloitte.reader;

import java.io.IOException;

/**
 * @author ssamal
 *
 */
public interface ItemReader<U> {
	U read() throws IOException;
}
