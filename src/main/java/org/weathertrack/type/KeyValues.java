package org.weathertrack.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KeyValues<K, V> extends KeyValuesPair<K, V> {
	private final Map<K, List<V>> map = new HashMap<>();

	@Override
	public void put(K key, V value) {
		map.computeIfAbsent(key, k -> new ArrayList<>()).add(value);
	}

	@Override
	public List<V> get(K key) {
		return map.getOrDefault(key, Collections.emptyList());
	}

	@Override
	public List<K> getKeys() {
		return new ArrayList<>(map.keySet());
	}

	@Override
	public boolean removeValue(K key, V value) {
		if (!map.containsKey(key)) {
			return false;
		}

		List<V> values = map.get(key);
		boolean removed = values.remove(value);
		if (values.isEmpty()) {
			map.remove(key);
		}
		return removed;
	}

	@Override
	public boolean removeKey(K key) {
		return map.remove(key) != null;
	}

	@Override
	public boolean containsKey(K key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsEntry(K key, V value) {
		List<V> values = map.get(key);
		return values != null && values.contains(value);
	}
}
