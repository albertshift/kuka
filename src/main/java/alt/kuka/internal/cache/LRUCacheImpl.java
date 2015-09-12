package alt.kuka.internal.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import alt.kuka.cache.Cache;
import alt.kuka.internal.concurrent.DualBlockingLinkedList;

/**
 * 
 * @author Albert Shift
 *
 * @param <K>
 * @param <V>
 */

public class LRUCacheImpl<K, V> implements Cache<K, V> {

	private final int UNITS = Integer.getInteger("cachemod.lru.units", 1000);
	private final int CONCURRENT = Integer.getInteger("cachemod.lru.concurrent", 16);

	private ConcurrentHashMap<K, LRUEntry<K, V>> localMap;

	private DualBlockingLinkedList<V> list = new DualBlockingLinkedList<V>();

	public static class LRUEntry<K, V> extends DualBlockingLinkedList.Entry<V> {

		private final K key;

		public LRUEntry(K key, V initValue) {
			super(initValue);
			this.key = key;
		}

		public K getKey() {
			return key;
		}

	}

	public LRUCacheImpl() {
		this.localMap = new ConcurrentHashMap<K, LRUEntry<K, V>>(UNITS, 0.75f, CONCURRENT);
	}

	@Override
	public V get(Object key) {
		LRUEntry<K, V> entry = localMap.get(key);
		if (entry != null) {
			list.touch(entry);
			return entry.getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V cacheEntry) {
		LRUEntry<K, V> newEntry = new LRUEntry<K, V>(key, cacheEntry);
		LRUEntry<K, V> prevEntry = localMap.putIfAbsent(key, newEntry);
		V prevValue = null;
		if (prevEntry != null) {
			prevValue = prevEntry.setValue(cacheEntry);
			list.touch(prevEntry);
		} else {
			list.add(newEntry);
			evict();
		}
		return prevValue;
	}

	public void evict() {
		while (list.size() > UNITS) {
			LRUEntry<K, V> head = (LRUEntry<K, V>) list.first();
			if (head != null) {
				localMap.remove(head.getKey());
			}
		}
	}

	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return localMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return localMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return list.containsValue(value);
	}

	@Override
	public V remove(Object key) {
		LRUEntry<K, V> entry = localMap.remove(key);
		if (entry != null) {
			list.remove(entry);
		}
		return entry.getValue();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void clear() {
		localMap.clear();
		list.clear();
	}

	@Override
	public Set<K> keySet() {
		return localMap.keySet();
	}

	@Override
	public Collection<V> values() {
		return list.values();
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		throw new RuntimeException("not supported");
	}

}