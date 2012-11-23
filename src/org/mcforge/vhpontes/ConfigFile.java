package org.mcforge.vhpontes;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFile extends JavaPlugin {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	public void reloadCustomConfig() {
		if (customConfigFile == null) {
			customConfigFile = new File("plugins" + File.separator + "AutoViP"
					+ File.separator, "config.yml");
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

		// Look for defaults in the jar
		// InputStream defConfigStream = getResource("config.yml");
		// if (defConfigStream != null) {
		// YamlConfiguration defConfig = YamlConfiguration
		// .loadConfiguration(defConfigStream);
		// customConfig.setDefaults(defConfig);
		// }
	}

	public FileConfiguration getCustomConfig() {
		if (customConfig == null) {
			reloadCustomConfig();
		}
		return customConfig;
	}

	public void saveCustomConfig() {
		if (customConfig == null || customConfigFile == null) {
			return;
		}
		try {
			customConfig.save(customConfigFile);
		} catch (IOException ex) {
			Logger.getLogger(JavaPlugin.class.getName()).log(Level.SEVERE,
					"Could not save config to " + customConfigFile, ex);
		}
	}

}
