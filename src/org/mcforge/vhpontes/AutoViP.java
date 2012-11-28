package org.mcforge.vhpontes;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AutoViP extends JavaPlugin {
	public static AutoViP plugin;
	public final static Logger logger = Logger.getLogger("Minecraft");
	protected Commands commandsExecutor;

	public boolean debug;
	public FileConfiguration config;
	public static int cfg_getvip_money;
	public static int cfg_getvip_xp;
	public static String cfg_getvip_message;
	public static String cfg_ViPgroup;
	public static boolean cfg_mysql_enable;
	public static String cfg_mysql_host;
	public static String cfg_mysql_db;
	public static String cfg_mysql_user;
	public static String cfg_mysql_password;
	public static List<String> config_commandslist = new ArrayList<String>();

	public String AUTO_VIP_TAG = "[AutoViP] - ";

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " is now disabled.");
	}

	@SuppressWarnings({ "unused" })
	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " version " + pdfFile.getVersion()
				+ " is enabled.");

		try {
			config = getConfig();

			File AutoViP = new File("plugins" + File.separator + "AutoViP"
					+ File.separator + "config.yml");
			AutoViP.mkdir();

			if (!config.contains("general.language")) {
				config.set("general.language", "en");
			}
			if (!config.contains("general.mysql.enable")) {
				config.set("general.mysql.enable", false);
			}
			if (!config.contains("general.mysql.host")) {
				config.set("general.mysql.host", "localhost");
			}
			if (!config.contains("general.mysql.database")) {
				config.set("general.mysql.database", "");
			}
			if (!config.contains("general.mysql.username")) {
				config.set("general.mysql.username", "");
			}
			if (!config.contains("general.mysql.password")) {
				config.set("general.mysql.password", "");
			}
			if (!config.contains("general.getViP.money")) {
				config.set("general.getViP.money", 0);
			}
			if (!config.contains("general.getViP.xp")) {
				config.set("general.getViP.xp", 0);
			}
			if (!config.contains("general.getViP.message")) {
				config.set("general.getViP.message",
						"The player has come to ViP Group");
			}
			if (!config.contains("general.getViP.endmessage")) {
				config.set("general.getViP.message",
						"The player has come to Default Group");
			}
			if (!config.contains("general.ViPgroup")) {
				config.set("general.ViPgroup", "Vip");
			}
			if (!config.contains("general.Defaultgroup")) {
				config.set("general.ViPgroup", "Default");
			}
			if (!config.contains("general.Items")) {
				String[] listOfStrings = { "<item id or name> <amount>",
						"<item id or name> <amount>" };
				this.getConfig().set("general.Items",
						Arrays.asList(listOfStrings));
			}
			if (!config.contains("general.Commands")) {
				String[] listOfStrings = { "example command 1",
						"example command 2", "example command 3" };
				this.getConfig().set("general.Commands",
						Arrays.asList(listOfStrings));
			}
			if (!config.contains("general.EndCommands")) {
				String[] listOfStrings = { "example command 1",
						"example command 2" };
				this.getConfig().set("general.EndCommands",
						Arrays.asList(listOfStrings));
			}
			saveConfig();
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		/*
		 * cfg_mysql_enable = config.getBoolean("general.mysql.enable");
		 * cfg_getvip_money = config.getInt("general.getViP.money");
		 * cfg_getvip_xp = config.getInt("general.getViP.xp");
		 * cfg_getvip_message = config.getString("general.getViP.message");
		 * cfg_ViPgroup = config.getString("general.ViPgroup");
		 * 
		 * if (cfg_mysql_enable) { cfg_mysql_host =
		 * config.getString("general.mysql.host"); cfg_mysql_db =
		 * config.getString("general.mysql.db"); cfg_mysql_user =
		 * config.getString("general.mysql.username"); cfg_mysql_password =
		 * config.getString("general.mysql.password"); }
		 */

		// this.config_commandslist =
		// AutoViP.this.getConfig().getStringList("general.Commands");

		commandsExecutor = new Commands(this);
		getCommand("av").setExecutor(commandsExecutor);

	}

	public void reloadAutoViP() {
		debug = getConfig().getBoolean("settings.debug", false);
		debugMsg("Reloading config file");
		this.reloadConfig();
		logger.info("Reloaded config file");
	}

	public void debugMsg(String msg) {
		if (debug)
			logger.info("[AutoViP] debug: " + msg);
	}

}
