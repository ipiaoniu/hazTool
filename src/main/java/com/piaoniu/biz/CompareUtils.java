package com.piaoniu.biz;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/8/10
 *         Time: 下午1:55
 */
public abstract class CompareUtils {

	public static <T extends Comparable<T>> T max(T a, T b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		if (a.compareTo(b) < 0) {
			return b;
		} else {
			return a;
		}
	}

	public static <T extends Comparable<T>> T min(T a, T b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		if (a.compareTo(b) < 0) {
			return a;
		} else {
			return b;
		}
	}
}
