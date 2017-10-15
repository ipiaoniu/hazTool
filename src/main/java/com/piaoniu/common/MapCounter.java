package com.piaoniu.common;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author code4crafter@gmail.com
 *         Date: 16/11/13
 *         Time: 上午8:23
 */
public class MapCounter<T> {

	public static final AtomicInteger ZERO_COUNTER = new AtomicInteger();
	private Map<T, AtomicInteger> map = new TreeMap<T, AtomicInteger>();

	public int incr(T key){
		AtomicInteger counter = map.get(key);
		if (counter == null) {
			counter = new AtomicInteger();
			map.putIfAbsent(key, counter);
		}
		return counter.incrementAndGet();
	}

	public int get(T key){
		return map.getOrDefault(key, ZERO_COUNTER).get();
	}

	public List<GeneralPair<T,Integer>> getCounts(){
		return map.entrySet().stream().map((entry) -> new GeneralPair<T,Integer>(entry.getKey(), entry.getValue().get()))
				.sorted((o1, o2) -> o2.getRight().compareTo(o1.getRight())).collect(Collectors.toList());
	}
}
