package com.piaoniu.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 和List\<String>没区别,只是为了mybatis在数据转换的时候好识别
 * @author code4crafter@gmail.com
 *         Date: 16/5/5
 *         Time: 上午10:52
 */
public class IntList extends ArrayList<Integer> {

	public IntList(Collection<? extends Integer> c) {
		super(c);
	}

	public IntList() {
	}

	public static IntList of(List<Integer> a){
		return new IntList(a);
	}
}
