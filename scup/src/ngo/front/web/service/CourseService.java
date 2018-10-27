package ngo.front.web.service;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ngo.front.common.service.LocalCache;
import ngo.front.storage.entity.Course;
import ngo.front.storage.entity.Resource;
import ngo.front.storage.orm.CourseDAO;

@Service
public class CourseService implements LocalCache.CachingLoader{
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final String REG_KEY = "course_meta";

	
	@Autowired
	private LocalCache localCache;
	
	@Autowired
	private CourseDAO courseDAO;
	
	
	@PostConstruct
	public void init()
	{
		localCache.register(REG_KEY, this);
		logger.info("CourseService registered as caching loader for ["+REG_KEY+"]");				
	}
	
	public String getCourseMetaInfo()
	{
    	return (String)localCache.getObject(REG_KEY);	
	}
	

	@Override
	public Object loadCacheObject(String key)  {
		if (key.equals(REG_KEY))
		{
			Resource resource = new Resource(courseDAO.getMetaInfo());
			localCache.entryVerUp(key, resource.getVersion());

			logger.info("key ["+key+"] loaded from database");			
			return (Object)courseDAO.getMetaInfo().getMd5();	
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
