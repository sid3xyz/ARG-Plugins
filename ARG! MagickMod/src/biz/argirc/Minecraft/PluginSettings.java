package biz.argirc.Minecraft;

public class PluginSettings {
	public int				startingbalance;
	public String			credit;
	public int				creeperValue;
	public int				spiderValue;
	public int				zombieValue;
	public int				skelValue;
	public int				multi;
	public int				deathpenalty;
	private final String	propertiesFile;

	public PluginSettings(String propertiesFile) {
		this.propertiesFile = propertiesFile;
		PluginProperties properties = new PluginProperties(propertiesFile);
		properties.load();

		startingbalance = properties.getInteger("Starting-Balance", 1000);
		credit = properties.getString("Currancy", "Tacos");
		creeperValue = properties.getInteger("creeper", 1);
		spiderValue = properties.getInteger("spider", 1);
		zombieValue = properties.getInteger("zombie", 1);
		skelValue = properties.getInteger("skel", 1);
		multi = properties.getInteger("multi", 5);
		deathpenalty = properties.getInteger("death", -75);
		properties.save("***MAGICKMOD CONFIG***");
	}

	public void load() {

		PluginProperties properties = new PluginProperties(propertiesFile);
		properties.load();
		startingbalance = properties.getInteger("Starting-Balance", 1000);
		credit = properties.getString("Currancy", "Tacos");
		creeperValue = properties.getInteger("creeper", 1);
		spiderValue = properties.getInteger("spider", 1);
		zombieValue = properties.getInteger("zombie", 1);
		skelValue = properties.getInteger("skel", 1);
		multi = properties.getInteger("multi", 5);
		deathpenalty = properties.getInteger("death", -75);
		properties.save("***MAGICKMOD CONFIG***");
	}

}
