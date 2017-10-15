package com.piaoniu.common;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author code4crafter@gmail.com
 *         Date: 17/5/10
 *         Time: 下午1:05
 */
public class ListJoiner<TypeA,TypeB,Key> {

	private List<TypeA> listA;

	private Function<List<Key>, List<TypeB>> getterB;

	private Function<List<Key>, Map<Key, TypeB>> mapGetterB;

	private Function<TypeA,Key> keyGetterA;

	private Function<TypeB,Key> keyGetterB;

	private Map<Key,TypeB> mapB;

	public static <TypeA, TypeB, Key> List<Pair<TypeA, TypeB>> join(List<TypeA> listA,
			Function<List<Key>, List<TypeB>> getterB,BiOn<TypeA, TypeB, Key> on) {
		ListJoiner<TypeA, TypeB, Key> joiner = new ListJoiner<>();
		joiner.listA = listA;
		joiner.getterB = getterB;
		joiner.keyGetterA = on.getKeyGetterA();
		joiner.keyGetterB = on.getKeyGetterB();
		return joiner.merge((typeA, typeB) -> ImmutablePair.of(typeA, typeB));
	}

	public static <TypeA, TypeB, Key> List<Pair<TypeA, TypeB>> join(List<TypeA> listA,
			List<TypeB> listB,BiOn<TypeA, TypeB, Key> on) {
		ListJoiner<TypeA, TypeB, Key> joiner = new ListJoiner<>();
		joiner.listA = listA;
		joiner.getterB = (ids) -> listB;
		joiner.keyGetterA = on.getKeyGetterA();
		joiner.keyGetterB = on.getKeyGetterB();
		return joiner.merge((typeA, typeB) -> ImmutablePair.of(typeA, typeB));
	}

	public static <TypeA, TypeB, Key> List<Pair<TypeA, TypeB>> join(List<TypeA> listA,
			Function<List<Key>, Map<Key, TypeB>> getterB, SingleOn<TypeA, Key> on) {
		ListJoiner<TypeA, TypeB, Key> joiner = new ListJoiner<>();
		joiner.listA = listA;
		joiner.keyGetterA = on.getKeyGetterA();
		joiner.mapGetterB = getterB;
		return joiner.merge((typeA, typeB) -> ImmutablePair.of(typeA, typeB));
	}

	public <R> List<R> merge(BiFunction<TypeA, TypeB, R> merger) {
		calcMap();
		return listA.stream().map((a)->merger.apply(a,mapB.get(keyGetterA.apply(a)))).collect(Collectors.toList());
	}

	private void calcMap() {
		List<Key> keys = listA.stream().map(keyGetterA).filter(key -> key != null).distinct()
				.collect(Collectors.toList());
		if (keys.isEmpty()){
			mapB = Collections.emptyMap();
			return;
		}
		if (mapGetterB != null) {
			mapB = mapGetterB.apply(keys);
		} else {
			mapB = getterB.apply(keys).stream()
					.collect(Collectors.toMap(keyGetterB, Function.identity()));
		}
	}

	@AllArgsConstructor
	static class BiOn<TypeRaw, TypeB, Key> {

		private final Function<TypeRaw, Key> keyGetterA;

		private final Function<TypeB, Key> keyGetterB;

		public Function<TypeRaw, Key> getKeyGetterA() {
			return keyGetterA;
		}

		public Function<TypeB, Key> getKeyGetterB() {
			return keyGetterB;
		}
	}

	@AllArgsConstructor
	static class SingleOn<TypeRaw, Key> {

		private final Function<TypeRaw, Key> keyGetterA;

		public Function<TypeRaw, Key> getKeyGetterA() {
			return keyGetterA;
		}

	}

	public static <TypeRaw, TypeB, Key> BiOn<TypeRaw, TypeB, Key> on(Function<TypeRaw, Key> keyGetterA,
			Function<TypeB, Key> keyGetterB) {
		return new BiOn<>(keyGetterA, keyGetterB);
	}

	public static <TypeRaw, Key> SingleOn<TypeRaw, Key> on(Function<TypeRaw, Key> keyGetterA) {
		return new SingleOn<>(keyGetterA);
	}

}
