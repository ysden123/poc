/**
 * Created by Yuriy Stul 16 May 2018
 */
package com.stulsoft.poc.prometheus.pushapp1;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Yuriy Stul
 *
 */
public class Utils {
	/**
	 * Converts a date to double.
	 * <p>
	 * Result is number (double) which represents date in the format HHmm
	 * </p>
	 * 
	 * @param date
	 *            the date to convert
	 * @return the number (double) which represents date in the format
	 *         HHmm
	 */
	public static double timeToMinutes(Date date) {
//		SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
//		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
		return Double.parseDouble(sdf.format(date));
	}
}
