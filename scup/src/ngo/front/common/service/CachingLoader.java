package ngo.front.common.service;

import java.util.Map;

/**
 * the class implements this interface hold the implementation of loading the specified caching entry (by key)
 * from the storage somewhere, e.g. database. 
 * @author Administrator
 */
public interface CachingLoader {
	public Map<String, String> loadDBMapObject(String key);
}
