package alt.kuka.client;

/**
 * 
 * @author Albert Shift
 *
 */

public class ConnectionPools {

	public static ConnectionPool nettyBased() {
		return new ConnectionPool();
	}
	
}
