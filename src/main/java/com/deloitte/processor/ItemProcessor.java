/**
 * 
 */
package com.deloitte.processor;

/**
 * @author ssamal
 *
 */
public interface ItemProcessor<U, V> {

	V process(U u);

}
