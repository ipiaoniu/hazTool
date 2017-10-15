package com.piaoniu.common;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FilteredKeyCollector<T, K, V> implements Collector<T, Map<K, V>, Map<K, V>> {

	private final Function<? super T, ? extends K> keyMapper;
	private final Function<? super T, ? extends V> valueMapper;
	private final Predicate<K> keyFilter;
	private final EnumSet<Characteristics> characteristics;

	public FilteredKeyCollector(Function<? super T, ? extends K> keyMapper,
			Function<? super T, ? extends V> valueMapper, Predicate<K> keyFilter) {

		this.keyMapper = keyMapper;
		this.valueMapper = valueMapper;
		this.keyFilter = keyFilter;
		this.characteristics = EnumSet.of(Characteristics.IDENTITY_FINISH);
	}

	@Override
	public Supplier<Map<K, V>> supplier() {
		return HashMap<K, V>::new;
	}

	@Override
	public BiConsumer<Map<K, V>, T> accumulator() {
		return (map, t) -> {
			K key = keyMapper.apply(t);
			if (keyFilter.test(key)) {
				map.put(key, valueMapper.apply(t));
			}
		};
	}

	@Override
	public BinaryOperator<Map<K, V>> combiner() {
		return (map1, map2) -> {
			map1.putAll(map2);
			return map1;
		};
	}

	@Override
	public Function<Map<K, V>, Map<K, V>> finisher() {
		return m -> m;
	}

	@Override
	public Set<Characteristics> characteristics() {
		return characteristics;
	}

}
