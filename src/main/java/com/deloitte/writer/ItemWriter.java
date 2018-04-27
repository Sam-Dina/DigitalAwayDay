/**
 * 
 */
package com.deloitte.writer;

import java.io.IOException;

/**
 * @author ssamal
 *
 */
public interface ItemWriter<V> {
	void write(V v) throws IOException;
}
