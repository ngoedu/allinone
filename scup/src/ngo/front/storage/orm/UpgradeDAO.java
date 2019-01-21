package ngo.front.storage.orm;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import ngo.front.storage.entity.Course;
import ngo.front.storage.entity.UpgradeConf;
import ngo.front.storage.entity.UpgradePack;
import ngo.front.storage.entity.UpgradeResource;

@Repository
public class UpgradeDAO {

	@Autowired
	private SqlSessionTemplate sqlSession;

	//PACK
	public UpgradePack getUpgradePackObject() {
		UpgradePack pack =  sqlSession.selectOne("ngo.front.storage.orm.UpgradeMapper.getUpgradePack");
		
		pack.setUpgradeConf(getUpgradeConfObject());	
		pack.setDllResrouces(getUpgradeDllsObject());
		pack.setJarResources(getUpgradeJarsObject());
		
		return pack;
	}
	
	public int updatePackObject(UpgradePack pack) {
		return sqlSession.update("ngo.front.storage.orm.UpgradeMapper.updateUpgradePack", pack);		
	}
	
	public int insertPackObject(UpgradePack pack) {
		return sqlSession.insert("ngo.front.storage.orm.UpgradeMapper.insertUpgradePack", pack);		
	}
	
	//CONF
	public UpgradeConf getUpgradeConfObject() {
		UpgradeConf conf = sqlSession.selectOne("ngo.front.storage.orm.UpgradeMapper.getUpgradeConf");
		return conf;
	}
	
	//JARS
	public List<UpgradeResource> getUpgradeJarsObject() {
		List<UpgradeResource> jars = sqlSession.selectList("ngo.front.storage.orm.UpgradeMapper.getUpgradeJars");
		return jars;
	}

	
	//DLLS
	public List<UpgradeResource> getUpgradeDllsObject() {
		List<UpgradeResource> dlls = sqlSession.selectList("ngo.front.storage.orm.UpgradeMapper.getUpgradeDlls");
		return dlls;
	}
}
