package alt.kuka.cache;

import alt.kuka.client.ConnectionPool;
import alt.kuka.distributed.DistributedSystem;

/**
 * 
 * @author Albert Shift
 *
 */

public class Partition {

	private ConnectionPool connectionPool;
	private DistributedSystem distributedSystem;

	public Partition() {
	}

	public Partition client(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		return this;
	}

	public Partition member(DistributedSystem distributedSystem) {
		this.distributedSystem = distributedSystem;
		return this;
	}

	public Partition useNearCache(Cache nearCache) {
		return this;
	}

}
