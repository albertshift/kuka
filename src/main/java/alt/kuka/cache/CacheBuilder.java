package alt.kuka.cache;

import alt.kuka.internal.cache.LFUCacheImpl;
import alt.kuka.internal.cache.LRUCacheImpl;

/**
 * 
 * @author Albert Shift
 *
 */

public class CacheBuilder {

	private String cacheName;

	private Eviction eviction;
	private Expiration expiration;

	private boolean persistent;
	private DiskStore diskStore;

	private Replication replication;
	private Partition partition;

	public CacheBuilder() {
	}

	public CacheBuilder(String cacheName) {
		withName(cacheName);
	}

	public CacheBuilder withName(String cacheName) {
		this.cacheName = cacheName;
		return this;
	}

	public CacheBuilder local() {
		return this;
	}

	public CacheBuilder persistent() {
		return persistent(true);
	}

	public CacheBuilder persistent(boolean flag) {
		this.persistent = flag;
		return this;
	}

	public CacheBuilder replicated(Replication replication) {
		this.replication = replication;
		return this;
	}

	public CacheBuilder partitioned(Partition partition) {
		this.partition = partition;
		return this;
	}

	public CacheBuilder useEviction(Eviction eviction) {
		this.eviction = eviction;
		return this;
	}

	public CacheBuilder useExpiration(Expiration expiration) {
		this.expiration = expiration;
		return this;
	}

	public CacheBuilder useDiskStore(DiskStore diskStore) {
		this.diskStore = diskStore;
		return this;
	}

	public <K, V> Cache<K, V> build() {

		if (eviction != null) {

			EvictionType evictionType = eviction.getEvictionType();
			if (evictionType != null) {

				if (evictionType == EvictionType.LFU) {
					return new LFUCacheImpl<K, V>();
				}
				if (evictionType == EvictionType.LRU) {
					return new LRUCacheImpl<K, V>();
				}

			}
		}

		throw new IllegalArgumentException("unsupported configuration");
	}

}
