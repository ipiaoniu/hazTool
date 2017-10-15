package com.piaoniu.common;

import lombok.Data;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/1/17
 *         Time: 下午2:52
 */
@Data
public class WeightedItem<T> {

	private int weight;

	private T item;

	public WeightedItem(int weight, T item) {
		this.weight = weight;
		this.item = item;
	}
}
