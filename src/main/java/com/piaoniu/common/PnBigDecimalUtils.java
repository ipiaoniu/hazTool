package com.piaoniu.common;

import java.math.BigDecimal;
import java.math.RoundingMode;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * 基于一般性的假设：
 *
 * 1. 所有金额取两位小数
 * 2. 通过四舍五入来截取
 *
 * 所以写了这个工具类，避免BigDecimal用错了之类的
 *
 * @author code4crafter@gmail.com
 *         Date: 15/9/28
 *         Time: 下午6:37
 */
@Slf4j
public class PnBigDecimalUtils {

	/**
	 * 返回2位小数
	 * @param value
	 * @return
	 */
	public static BigDecimal newBigDecimal(String value){
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
	}

	public static boolean greaterThanZero(BigDecimal number) {
		if (number == null) {
			return false;
		}
		return number.compareTo(ZERO) > 0;
	}

	public static boolean lessThanZero(BigDecimal number) {
		return number.compareTo(ZERO) < 0;
	}

	public static boolean isZero(BigDecimal number) {
		return number.compareTo(ZERO) == 0;
	}

	/**
	 * 返回2位小数
	 * @param value
	 * @return
	 */
	public static BigDecimal newBigDecimal(double value){
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
	}

	public static BigDecimal opposite(BigDecimal value){
		return PnBigDecimalUtils.newBigDecimal(0).subtract(value).setScale(2,
				BigDecimal.ROUND_HALF_UP);
	}

	public static BigDecimal newBigDecimal(int value){
		return new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
	}

	public static BigDecimal normalize(BigDecimal value){
		return value.setScale(2, BigDecimal.ROUND_HALF_UP);
	}
	public static boolean equal(BigDecimal b1, BigDecimal b2){
		if (b1 == null){
			return b2 == null;
		}
		return b1.compareTo(b2) == 0;
	}

	public static final BigDecimal MINIMAL_PRICE = PnBigDecimalUtils.newBigDecimal(0.01);

	public static final BigDecimal ZERO = PnBigDecimalUtils.newBigDecimal(0);

	public static final BigDecimal ONE = PnBigDecimalUtils.newBigDecimal("1");

	public static BigDecimal getGeoBigDecimal(String string) {
		return new BigDecimal(string).setScale(6, RoundingMode.FLOOR);
	}

	public static BigDecimal max(BigDecimal m1, BigDecimal m2) {
		if (m1.compareTo(m2) >= 0) {
			return m1;
		} else {
			return m2;
		}
	}

	public static BigDecimal min(BigDecimal m1, BigDecimal m2) {
		if (m1.compareTo(m2) < 0) {
			return m1;
		} else {
			return m2;
		}
	}

}
