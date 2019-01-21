package ngo.front.web.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ngo.front.common.service.CachingLoader;
import ngo.front.common.service.JSonService;
import ngo.front.common.service.JedisCache;
import ngo.front.common.service.LocalCache;
import ngo.front.storage.entity.Course;
import ngo.front.storage.entity.Resource;
import ngo.front.storage.entity.UpgradePack;
import ngo.front.storage.orm.CourseDAO;
import ngo.front.storage.orm.UpgradeDAO;

@Service
public class UpgradeService implements CachingLoader{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final String REG_KEY = "reg_upgpack";

	@Autowired
	private JedisCache jedisCache;
	
	@Autowired
	private UpgradeDAO upgradeDAO;
	
	@Autowired
	private JSonService jsonService;
	
	
	@PostConstruct
	public void init()
	{
		jedisCache.register(REG_KEY, this);
		logger.info("UpgradeService registered as caching loader for ["+REG_KEY+"]");				
	}
	
	
	public String getUpgradePackJson()
	{
    	Map<String,String> pack = jedisCache.getHMValue(REG_KEY, "upgpack");	
    	return pack.get("packjson");
	}
	
	public String getUpgradePackVer()
	{
    	Map<String,String> pack = jedisCache.getHMValue(REG_KEY, "upgpack");	
    	return pack.get("packver");
	}


	public String getUpgradePackCmkey() {
    	Map<String,String> pack = jedisCache.getHMValue(REG_KEY, "upgpack");	
    	return pack.get("cmetaKey");
	}

	/**
	 * load resource from database
	 * @param key
	 * @return
	 */
	@Override
	public Map<String, String> loadDBMapObject(String key)  {
		if (key.equals(REG_KEY))
		{
			UpgradePack pack = upgradeDAO.getUpgradePackObject();		
			logger.info("UpgradePack ["+key+"] loaded from database");
			
			Map<String, String> hmap = new HashMap<String, String>();
			hmap.put("packver", pack.getPackVer());
			hmap.put("packjson", jsonService.toJson(pack));
			hmap.put("cmetaKey", pack.getCmetaKey());
			return hmap;	
		}
		return null;
	}

	public int updateUpgradePackObject(UpgradePack pack) {
		if (pack == null)
			throw new IllegalStateException("upgrade pack can not be null for updating");
		return upgradeDAO.updatePackObject(pack);
	}
	
	public int addUpgradePackObject(UpgradePack pack) {
		if (pack == null)
			throw new IllegalStateException("upgrade pack can not be null for adding");
		return upgradeDAO.insertPackObject(pack);
	}

}
