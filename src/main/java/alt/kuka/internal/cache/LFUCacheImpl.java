package alt.kuka.internal.cache;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import alt.kuka.cache.Cache;
import alt.kuka.internal.concurrent.DualBlockingSequenceLinkedList;

/**
 * 
 * @author Albert Shift
 *
 * @param <K>
 * @param <V>
 */

public class LFUCacheImpl<K, V> implements Cache<K, V> {

	private final int UNITS = Integer.getInteger("cachemod.lfu.units", 1000);
	private final int CONCURRENT = Integer.getInteger("cachemod.lfu.concurrent", 16);

	private ConcurrentHashMap<K, LFUEntry<K, V>> localMap;

	private DualBlockingSequenceLinkedList<V> list = new DualBlockingSequenceLinkedList<V>();

	public static class LFUEntry<K, V> extends DualBlockingSequenceLinkedList.Entry<V> {

		private final K key;

		public LFUEntry(K key, V initValue) {
			super(initValue);
			this.key = key;
		}

		public K getKey() {
			return key;
		}

	}

	public LFUCacheImpl() {
		this.localMap = new ConcurrentHashMap<K, LFUEntry<K, V>>(UNITS, 0.75f, CONCURRENT);
	}

	@Override
	public V get(Object key) {
		LFUEntry<K, V> entry = localMap.get(key);
		if (entry != null) {
			list.touch(entry);
			return entry.getValue();
		}
		return null;
	}

	@Override
	public V put(K key, V cacheEntry) {
		LFUEntry<K, V> newEntry = new LFUEntry<K, V>(key, cacheEntry);
		LFUEntry<K, V> prevEntry = localMap.putIfAbsent(key, newEntry);
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
			LFUEntry<K, V> entry = (LFUEntry<K, V>) list.first();
			if (entry != null) {
				localMap.remove(entry.getKey());
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
		LFUEntry<K, V> entry = localMap.remove(key);
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
