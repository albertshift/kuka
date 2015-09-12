package alt.kuka.cache;

import alt.kuka.client.ConnectionPool;
import alt.kuka.distributed.DistributedSystem;

/**
 * 
 * @author Albert Shift
 *
 */

public class Replication {

	private ConnectionPool connectionPool;
	private DistributedSystem distributedSystem;

	public Replication() {
	}

	public Replication client(ConnectionPool connectionPool) {
		this.connectionPool = connectionPool;
		return this;
	}

	public Replication member(DistributedSystem distributedSystem) {
		this.distributedSystem = distributedSystem;
		return this;
	}

}
