package alt.kuka.cache;

/**
 * 
 * @author Albert Shift
 *
 */
public class Caches {

	public static CacheBuilder newCache() {
		return new CacheBuilder();
	}

	public static CacheBuilder newNamedCache(String cacheName) {
		return new CacheBuilder(cacheName);
	}

}
