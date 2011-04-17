/*
 * 
 * 
 * Borrowed from Meldanor
 * 
 */

package biz.argirc.Minecraft;

import static biz.argirc.Minecraft.FileUtils.FILE_SEPARATOR;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.ConsoleCommandSender;

public class BackupTask implements Runnable {

	private Server		server		= null;
	private final int	MAX_BACKUPS;

	private String		backupName	= "ARG-LandBackup";

	public BackupTask(Server server) {
		this.server = server;

		MAX_BACKUPS = 3;
	}

	@Override
	public void run() {

		backup();

	}

	protected void backup() {

		// the messages
		String startBackupMessage = "Starting world Backup";
		System.out.println(startBackupMessage);
		server.broadcastMessage(startBackupMessage);
		ConsoleCommandSender ccs = new ConsoleCommandSender(server);
		server.dispatchCommand(ccs, "save-all");
		server.dispatchCommand(ccs, "save-off");
		// the Player Position are getting stored
		server.savePlayers();

		try {

			World world = server.getWorld("world");
			String backupDir = "backups".concat(FILE_SEPARATOR).concat(world.getName());
			// save every information from the RAM into the HDD
			world.save();
			// make a temporary dir of the world
			FileUtils.copyDirectory(new File(world.getName()), new File(backupDir));
			// zip the temporary dir
			String targetName = world.getName();
			String targetDir = "backups".concat(FILE_SEPARATOR);

			if (backupName != null) {
				targetName = backupName;
				targetDir = targetDir.concat(FILE_SEPARATOR);
			}
			FileUtils.zipDirectory(backupDir, targetDir.concat(targetName).concat(getDate()));
			// delete the temporary dir
			FileUtils.deleteDirectory(new File(backupDir));

		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		// enable the world save
		server.dispatchCommand(ccs, "save-on");
		// the messages
		String completedBackupMessage = "Backups Complete";
		server.broadcastMessage(completedBackupMessage);
		System.out.println(completedBackupMessage);
		// check whether there are old backups to delete
		deleteOldBackups();
		backupName = null;

	}

	private String getDate() {
		StringBuilder sBuilder = new StringBuilder();
		Calendar cal = Calendar.getInstance();
		sBuilder.append(cal.get(Calendar.DAY_OF_MONTH));

		int month = cal.get(Calendar.MONTH) + 1;
		if (month < 10) sBuilder.append("0");
		sBuilder.append(month);

		sBuilder.append(cal.get(Calendar.YEAR));
		sBuilder.append("-");

		int hours = cal.get(Calendar.HOUR_OF_DAY);
		if (hours < 10) sBuilder.append("0");
		sBuilder.append(hours);
		int minutes = cal.get(Calendar.MINUTE);
		if (minutes < 10) sBuilder.append("0");
		sBuilder.append(minutes);
		int seconds = cal.get(Calendar.SECOND);
		if (seconds < 10) sBuilder.append("0");
		sBuilder.append(seconds);
		return sBuilder.toString();
	}

	private void deleteOldBackups() {
		try {
			//
			File backupDir = new File("backups");
			// get every zip file in the backup Dir
			File[] tempArray = backupDir.listFiles();
			// when are more backups existing as allowed as to store
			if (tempArray.length > MAX_BACKUPS) {
				System.out.println("Delete old backups");

				// Store the to delete backups in a list
				ArrayList<File> backups = new ArrayList<File>(tempArray.length);
				// For this add all backups in the list and remove later the
				// newest ones
				backups.addAll(Arrays.asList(tempArray));

				// the current index of the newest backup
				int maxModifiedIndex;
				// the current time of the newest backup
				long maxModified;

				// remove all newest backups from the list to delete
				for (int i = 0; i < MAX_BACKUPS; ++i) {
					maxModifiedIndex = 0;
					maxModified = backups.get(0).lastModified();
					for (int j = 1; j < backups.size(); ++j) {
						File currentFile = backups.get(j);
						if (currentFile.lastModified() > maxModified) {
							maxModified = currentFile.lastModified();
							maxModifiedIndex = j;
						}
					}
					backups.remove(maxModifiedIndex);
				}
				// this are the oldest backups, so delete them
				for (File backupToDelete : backups)
					backupToDelete.delete();
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

	public void setBackupName(String backupName) {
		this.backupName = backupName;
	}
}