package org.mcforge.vhpontes.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigFileUtils extends JavaPlugin {
	private FileConfiguration customConfig = null;
	private File customConfigFile = null;

	public int cfg_getvip_money, cfg_getvip_xp;
	public String cfg_getvip_message, cfg_ViPgroup;
	public boolean cfg_mysql_enabled;
	public String cfg_mysql_host, cfg_mysql_database, cfg_mysql_username,
			cfg_mysql_password;
	public List<String> config_commandslist = new ArrayList<String>();

	public void reloadCustomConfig() {
		if (customConfigFile == null) {
			customConfigFile = new File("plugins" + File.separator + "AutoViP"
					+ File.separator, "config.yml");
			customConfigFile.mkdir();
		}
		customConfig = YamlConfiguration.loadConfiguration(customConfigFile);

		if (!customConfig.contains("general.plugin.language")) {
			customConfig.set("general.plugin.language", "en");
		}
		if (!customConfig.contains("general.mysql.enabled")) {
			customConfig.set("general.mysql.enabled", false);
		}
		if (!customConfig.contains("general.mysql.host")) {
			customConfig.set("general.mysql.host", "localhost");
		}
		if (!customConfig.contains("general.mysql.database")) {
			customConfig.set("general.mysql.database", "");
		}
		if (!customConfig.contains("general.mysql.username")) {
			customConfig.set("general.mysql.username", "");
		}
		if (!customConfig.contains("general.mysql.password")) {
			customConfig.set("general.mysql.password", "");
		}
		if (!customConfig.contains("general.getViP.money")) {
			customConfig.set("general.getViP.money", 0);
		}
		if (!customConfig.contains("general.getViP.xp")) {
			customConfig.set("general.getViP.xp", 0);
		}
		if (!customConfig.contains("general.getViP.message")) {
			customConfig.set("general.getViP.message",
					"The player has come to ViP Group");
		}
		if (!customConfig.contains("general.getViP.endmessage")) {
			customConfig.set("general.getViP.message",
					"The player has come to Default Group");
		}
		if (!customConfig.contains("general.ViPgroup")) {
			customConfig.set("general.ViPgroup", "Vip");
		}
		if (!customConfig.contains("general.Defaultgroup")) {
			customConfig.set("general.Defaultgroup", "Default");
		}
		if (!customConfig.contains("general.Items")) {
			String[] listOfStrings = { "<item id or name> <amount>",
					"<item id or name> <amount>" };
			customConfig.set("general.Items", Arrays.asList(listOfStrings));
		}
		if (!customConfig.contains("general.Commands")) {
			String[] listOfStrings = { "example command 1",
					"example command 2", "example command 3" };
			customConfig.set("general.Commands", Arrays.asList(listOfStrings));
		}
		if (!customConfig.contains("general.EndCommands")) {
			String[] listOfStrings = { "example command 1", "example command 2" };
			customConfig.set("general.EndCommands",
					Arrays.asList(listOfStrings));
		}
		saveCustomConfig();
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
