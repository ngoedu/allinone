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
import ngo.front.storage.orm.CourseDAO;

@Service
public class CourseService implements CachingLoader{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final String REG_KEY = "course";

	
	@Autowired
	private JedisCache jedisCache;
	
	@Autowired
	private CourseDAO courseDAO;
	
	@Autowired
	private JSonService jsonService;
	
	
	@PostConstruct
	public void init()
	{
		jedisCache.register(REG_KEY, this);
		logger.info("CourseService registered as caching loader for ["+REG_KEY+"]");				
	}
	
	public String getCourseMetaKey()
	{
    	Map<String,String> course = jedisCache.getHMValue(REG_KEY, "course");	
    	return course.get("metaKey");
	}
	
	public String getCourseMetaFileUrl()
	{
		Map<String,String> course = jedisCache.getHMValue(REG_KEY, "course");	
    	return course.get("fileUrl");
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
			Course course = courseDAO.getCourseMetaObject();		
			logger.info("key ["+key+"] loaded from database");
			
			Map<String, String> hmap = new HashMap<String, String>();
			hmap.put("metaKey", course.getMd5());
			hmap.put("fileUrl", course.getUrl());
			
			return hmap;	
		}
		return null;
	}

	public int updateCourseObject(Course course) {
		if (course == null)
			throw new IllegalStateException("course can not be null for updating");
		return courseDAO.update(course);
	}
	
	public int addCourseObject(Course course) {
		if (course == null)
			throw new IllegalStateException("course can not be null for adding");
		return courseDAO.insert(course);
	}
}
