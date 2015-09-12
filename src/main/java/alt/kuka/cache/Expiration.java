package alt.kuka.cache;

/**
 * 
 * @author Albert Shift
 *
 */
public class Expiration {

	private Integer timeToLiveSeconds;

	public Expiration() {
	}
	
	public Expiration ttl(Integer timeToLiveSeconds) {
		this.timeToLiveSeconds = timeToLiveSeconds;
		return this;
	}

	public Integer getTimeToLiveSeconds() {
		return timeToLiveSeconds;
	}
	
}
