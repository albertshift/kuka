package alt.kuka.cache;

/**
 *
 * @author Albert Shift
 *
 */
public class DiskStore {

	public DiskStore() {
	}

	public DiskStore useMemoryMappedFiles() {
		return this;
	}

	public DiskStore addFile(String filePath, long maxSize) {
		return this;
	}

}
