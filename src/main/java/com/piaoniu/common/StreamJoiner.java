package com.piaoniu.common;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/5/10
 *         Time: 下午1:05
 */
public class StreamJoiner<TypeA,TypeB,Key> {

	private List<TypeA> listA;

	private Function<List<Key>, List<TypeB>> getterB;

	private Function<TypeA,Key> keyGetterA;

	private Function<TypeB,Key> keyGetterB;

	private Map<Key,TypeB> mapB;

	public static <TypeA, TypeB, Key> Stream<Pair<TypeA, TypeB>> join(Stream<TypeA> listA,
			Function<List<Key>, List<TypeB>> getterB,On<TypeA, TypeB, Key> on) {
		StreamJoiner<TypeA, TypeB, Key> joiner = new StreamJoiner<>();
		joiner.listA = listA.collect(Collectors.toList());
		joiner.getterB = getterB;
		joiner.keyGetterA = on.getKeyGetterA();
		joiner.keyGetterB = on.getKeyGetterB();
		List<Pair<TypeA, TypeB>> pairs = joiner.merge((typeA, typeB) -> ImmutablePair.of(typeA, typeB));
		return pairs.stream();
	}

	public <R> List<R> merge(BiFunction<TypeA, TypeB, R> merger) {
		calcMap();
		return listA.stream().map((a)->merger.apply(a,mapB.get(keyGetterA.apply(a)))).collect(Collectors.toList());
	}

	private void calcMap() {
		mapB = getterB.apply(listA.stream().map(keyGetterA).collect(Collectors.toList())).stream()
				.collect(Collectors.toMap(keyGetterB, Function.identity()));
	}

	@AllArgsConstructor
	static class On<TypeRaw, TypeB, Key> {

		private final Function<TypeRaw, Key> keyGetterA;

		private final Function<TypeB, Key> keyGetterB;

		public Function<TypeRaw, Key> getKeyGetterA() {
			return keyGetterA;
		}

		public Function<TypeB, Key> getKeyGetterB() {
			return keyGetterB;
		}
	}

	public static <TypeRaw, TypeB, Key> StreamJoiner.On<TypeRaw, TypeB, Key> on(Function<TypeRaw, Key> keyGetterA,
			Function<TypeB, Key> keyGetterB) {
		return new StreamJoiner.On<>(keyGetterA, keyGetterB);
	}

	public static void main(String[] args) {

		List<Integer> listA = Lists.newArrayList(1, 2, 3);
		Stream<Pair<Integer, Integer>> join = StreamJoiner
				.join(listA.stream(), (ids) -> ids, on(Function.identity(), Function.identity()));
		System.out.println(join.collect(Collectors.toList()));
	}

}
