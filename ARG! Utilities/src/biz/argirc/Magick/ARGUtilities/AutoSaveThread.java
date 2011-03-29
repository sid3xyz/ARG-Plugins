package biz.argirc.Magick.ARGUtilities;

import java.util.Date;
import java.util.logging.Logger;

import org.bukkit.ChatColor;

public class AutoSaveThread extends Thread {

	protected final Logger log = Logger.getLogger("Minecraft");
	private boolean run = true;
	private ARGUtilities plugin = null;

	private Date lastSave = null;

	
	AutoSaveThread(ARGUtilities plugin) {
		this.plugin = plugin;
		
	}

	// Allows for the thread to naturally exit if value is false
	public void setRun(boolean run) {
		this.run = run;
	}

	public Date getLastSave() {
		return lastSave;
	}

	// The code to run...weee
    public void run() {
    
    	System.out.println("AutoSaveThread Started: Interval is 900 seconds");
    	//log.info(String.format("[%s] AutoSaveThread Started: Interval is %s seconds", plugin.getDescription().getName(), config.varInterval));
    	while(run) {
    		// Do our Sleep stuff!
			for (int i = 0; i < 900; i++) {
				try {
					if(!run) {
						return;
					}
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.info("Could not sleep!");
				}
			}
			// Save the worlds
			int saved = 0;
			saved += plugin.saveWorlds();
			System.out.println("Saved Worlds");
			//log.info(String.format("[%s] Saved %d Worlds", plugin.getDescription().getName(), saved));
			lastSave = new Date();
			plugin.getServer().broadcastMessage(String.format("%s%s", ChatColor.BLUE, "World and Player data saved"));	
		}
    }

}
