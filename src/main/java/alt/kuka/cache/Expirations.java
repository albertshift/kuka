package alt.kuka.cache;

import java.util.concurrent.TimeUnit;

/**
 * 
 * @author Albert Shift
 *
 */
public class Expirations {

	public static Expiration ttl(Integer timeToLiveSeconds) {
		return new Expiration().ttl(timeToLiveSeconds);
	}

	public static Expiration ttl(long timeToLive, TimeUnit timeUnit) {
		return new Expiration().ttl((int) timeUnit.toSeconds(timeToLive));
	}


}
