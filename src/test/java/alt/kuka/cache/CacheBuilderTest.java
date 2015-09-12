package alt.kuka.cache;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import alt.kuka.cache.Cache;
import alt.kuka.cache.Caches;
import alt.kuka.cache.DiskStores;
import alt.kuka.cache.Evictions;
import alt.kuka.cache.Expirations;

/**
 * Cache Builder Tests
 * 
 * @author Albert Shift
 *
 */

public class CacheBuilderTest {

	@Test
	public void test() {

		Cache<Long, String> cache = Caches.newCache()
				.useEviction(Evictions.lru())
				.useExpiration(Expirations.ttl(5, TimeUnit.DAYS))
				.local()
				.persistent()
				.useDiskStore(DiskStores.memoryMappedStore().addFile("test.mmf", Integer.MAX_VALUE))
				.build();
		
	}
	
}
