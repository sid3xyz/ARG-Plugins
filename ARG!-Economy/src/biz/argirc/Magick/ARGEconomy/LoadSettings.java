package biz.argirc.Magick.ARGEconomy;

// this file loads plugin settings
public class LoadSettings {
	static int		startingbalance;
	static String	credit;
	static int		creeperValue;
	static int		spiderValue;
	static int		zombieValue;
	static int		skelValue;
	static int 		multi;
	static int 		deathpenalty;
	public static void loadStore() {
		String propertiesFile = ARGEcon.maindirectory + "mainConfig.properties";

		PluginProperties properties = new PluginProperties(propertiesFile);
		properties.load();

		startingbalance = properties.getInteger("Starting-Balance", 1000);
		credit = properties.getString("Currancy", "Tacos");

		properties.save("***MAGICKTACOS CONFIG***");

	}

	public static void loadMobValues() {
		String propertiesFile = ARGEcon.maindirectory + "mainConfig.properties";

		PluginProperties properties = new PluginProperties(propertiesFile);
		properties.load();

		credit = properties.getString("Currancy", "Tacos");
		creeperValue = properties.getInteger("creeper", 1);
		spiderValue = properties.getInteger("spider", 1);
		zombieValue = properties.getInteger("zombie", 1);
		skelValue = properties.getInteger("skel", 1);
		multi = properties.getInteger("multi", 5);
		deathpenalty =  properties.getInteger("death", -75);
		properties.save("***MAGICKTACOS CONFIG***");

	}
	public static void getDeathPenalty() {
		String propertiesFile = ARGEcon.maindirectory + "mainConfig.properties";
		PluginProperties properties = new PluginProperties(propertiesFile);
		properties.load();
		deathpenalty =  properties.getInteger("death", -75);
		credit = properties.getString("Currancy", "Tacos");
		properties.save("***MAGICKTACOS CONFIG***");

	}

}
