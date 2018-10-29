package ngo.front.storage.orm;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ngo.front.storage.entity.Course;

@Repository
public class CourseDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	
	public Course getCourseMetaObject() {
		Course course =  sqlSession.selectOne("ngo.front.storage.orm.CourseMapper.getCourseMeta");
		return course;
	}
	
	public int update(Course bom) {
		return sqlSession.update("ngo.front.storage.orm.CourseMapper.update", bom);		
	}
	
	public int insert(Course bom) {
		return sqlSession.insert("ngo.front.storage.orm.CourseMapper.insert", bom);		
	}
}
