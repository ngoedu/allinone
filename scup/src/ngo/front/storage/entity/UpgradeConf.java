package ngo.front.storage.entity;

public class UpgradeConf {
	private String upgradeSite;
	private String dllJarUpgradeSite;
	private int upgradeInterval;
	public String getUpgradeSite() {
		return upgradeSite;
	}
	public void setUpgradeSite(String upgradeSite) {
		this.upgradeSite = upgradeSite;
	}
	public String getDllJarUpgradeSite() {
		return dllJarUpgradeSite;
	}
	public void setDllJarUpgradeSite(String dllJarUpgradeSite) {
		this.dllJarUpgradeSite = dllJarUpgradeSite;
	}
	public int getUpgradeInterval() {
		return upgradeInterval;
	}
	public void setUpgradeInterval(int upgradeInterval) {
		this.upgradeInterval = upgradeInterval;
	}
	
	
}
