package ngo.front.storage.entity;

import java.util.List;

public class UpgradePack {
	private String packVer;
	private String cmetaKey;
	private String cmetaPath;
	
	private UpgradeConf upgradeConf;
	
	private List<UpgradeResource> dllResrouces;
	private List<UpgradeResource> jarResources;
	
	public UpgradePack(String cmkey, String cmurl) {
		this.cmetaKey = cmkey;
		this.cmetaPath = cmurl;
	}
	public String getPackVer() {
		return packVer;
	}
	public void setPackVer(String packVer) {
		this.packVer = packVer;
	}
	public String getCmetaKey() {
		return cmetaKey;
	}
	
	public String getCmetaPath() {
		return cmetaPath;
	}
	public void setCmetaPath(String cmetaPath) {
		this.cmetaPath = cmetaPath;
	}
	public void setCmetaKey(String cmetaKey) {
		this.cmetaKey = cmetaKey;
	}
	public UpgradeConf getUpgradeConf() {
		return upgradeConf;
	}
	public void setUpgradeConf(UpgradeConf upgradeConf) {
		this.upgradeConf = upgradeConf;
	}
	public List<UpgradeResource> getDllResrouces() {
		return dllResrouces;
	}
	public void setDllResrouces(List<UpgradeResource> dllResrouces) {
		this.dllResrouces = dllResrouces;
	}
	public List<UpgradeResource> getJarResources() {
		return jarResources;
	}
	public void setJarResources(List<UpgradeResource> jarResources) {
		this.jarResources = jarResources;
	}
	
	
	
}


