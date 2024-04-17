package org.weathertrack.type;

import java.util.List;

public abstract class KeyValuesPair<K, V> {
	public abstract void put(K key, V value);

	public abstract List<V> get(K key);

	public abstract List<K> getKeys();

	public abstract boolean removeValue(K key, V value);

	public abstract boolean removeKey(K key);

	public abstract boolean containsKey(K key);

	public abstract boolean containsEntry(K key, V value);
}
