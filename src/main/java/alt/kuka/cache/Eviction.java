package alt.kuka.cache;

/**
 * 
 * @author Albert Shift
 *
 */

public class Eviction {

	private EvictionType evictionType;
	private Integer maxItems;
	
	public Eviction() {
	}
	
	public Eviction lru() {
		this.evictionType = EvictionType.LRU;
		return this;
	}

	public Eviction lfu() {
		this.evictionType = EvictionType.LFU;
		return this;
	}
	
	public Eviction maxItems(Integer maxItems) {
		this.maxItems = maxItems;
		return this;
	}

	public EvictionType getEvictionType() {
		return evictionType;
	}

	public Integer getMaxItems() {
		return maxItems;
	}
	
}
