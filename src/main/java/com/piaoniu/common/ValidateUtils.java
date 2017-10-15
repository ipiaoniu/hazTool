package com.piaoniu.common;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/3/17
 *         Time: 上午10:01
 */
public abstract class ValidateUtils {

	public static void assertTrue(boolean assertion, String errorMsg) {
		if (!assertion) {
			throw new RuntimeException(errorMsg);
		}
	}
}
