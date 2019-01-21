package ngo.front.common.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Service
@Scope("singleton")
public class JedisCache {

	private final static Map<String, CachingLoader> REGISTRA_MAP = new HashMap<String, CachingLoader>();
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final JedisPool pool;
	
	static  {
		
		JedisPoolConfig config = new JedisPoolConfig(); 
		config.setMaxIdle(8); 
		config.setMaxTotal(18); 
		pool = new JedisPool(config, "192.168.0.13", 6379, 20000); 
	}

	public void register(String regKey, CachingLoader loader) {
		this.REGISTRA_MAP.put(regKey, loader);
	}
	
	public Map<String,String> getHMValue(String regKey, String key) {
		Jedis jedis = pool.getResource();
		Map<String, String> mvalue = null;
		try {
			mvalue = jedis.hgetAll(key);
			if (mvalue == null || mvalue.isEmpty()) {
				CachingLoader cl = REGISTRA_MAP.get(regKey);
				mvalue= (Map<String, String>) cl.loadDBMapObject(regKey);
				jedis.hmset(key, mvalue);
				return mvalue;
			}
		} finally {
		    if (jedis != null) 
		        jedis.close();
		}
		return mvalue;
	}
	
	
	
}
