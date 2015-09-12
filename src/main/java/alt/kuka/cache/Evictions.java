package alt.kuka.cache;

/**
 * 
 * @author Albert Shift
 *
 */

public class Evictions {

	public static Eviction lru() {
		return new Eviction().lru();
	}

	public static Eviction lfu() {
		return new Eviction().lfu();
	}

	public static Eviction maxItems(Integer maxItems) {
		return new Eviction().maxItems(maxItems);
	}
	
}
