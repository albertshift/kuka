package alt.kuka.cache;

/**
 * 
 * @author Albert Shift
 *
 */
public class DiskStores {

	public static DiskStore memoryMappedStore() {
		return new DiskStore().useMemoryMappedFiles();
	}
	
}
