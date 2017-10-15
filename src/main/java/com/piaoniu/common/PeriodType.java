package com.piaoniu.common;


/**
 * @author code4crafter@gmail.com
 *         Date: 16/6/22
 *         Time: 下午12:01
 */
public enum PeriodType {
	DAILY(1),
	WEEKLY(2),
	MONTHLY(3),
	QUARTER(4),
	YEARLY(5);

	private final int code;

	PeriodType(int code) { this.code = code; }

	public int getValue() {
		return code;
	}

	public static PeriodType fromValue(int value){
		for (PeriodType periodType : PeriodType.values()) {
			if (periodType.getValue()==value){
				return periodType;
			}
		}
		throw new RuntimeException("该类型暂不支持");
	}

	public boolean is(int value){
		return getValue() == value;
	}
}
