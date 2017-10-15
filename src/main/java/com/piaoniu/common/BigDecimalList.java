package com.piaoniu.common;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Stephen Cai on 2017-08-08 09:56.
 */
public class BigDecimalList extends ArrayList<BigDecimal> {
	public BigDecimalList(Collection<? extends BigDecimal> b) {super(b);}
	public BigDecimalList() {}
	public static BigDecimalList of(List<BigDecimal> b){
		return new BigDecimalList(b);
	}
}
