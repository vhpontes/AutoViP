package org.mcforge.vhpontes;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcforge.vhpontes.utils.ConfigFileUtils;
import org.mcforge.vhpontes.utils.DatabaseUtils;

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
	public static boolean cfg_mysql_enabled;
	public static String cfg_mysql_host;
	public static String cfg_mysql_database;
	public static String cfg_mysql_username;
	public static String cfg_mysql_password;
	public static List<String> config_commandslist = new ArrayList<String>();

	public String AUTO_VIP_TAG = "[AutoViP] - ";
	public static String AUTO_VIP_TAG1 = "[AutoViP] ";

	@Override
	public void onDisable() {
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(AUTO_VIP_TAG1 + pdfFile.getName() + " is now disabled.");
	}

	@SuppressWarnings({ "unused" })
	@Override
	public void onEnable() {
		PluginManager pm = this.getServer().getPluginManager();
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(AUTO_VIP_TAG1 + pdfFile.getName() + " version "
				+ pdfFile.getVersion() + " is enabled.");

		ConfigFileUtils configYML = new ConfigFileUtils();

		DatabaseUtils DB = new DatabaseUtils();
		DB.checkConnection();

		commandsExecutor = new Commands(this);
		getCommand("av").setExecutor(commandsExecutor);

	}

	public void reloadAutoViP() {
		debug = getConfig().getBoolean("settings.debug", false);
		debugMsg("Reloading config file");
		this.reloadConfig();
		logger.info(AUTO_VIP_TAG1 + "Reloaded config file");
	}

	public void debugMsg(String msg) {
		if (debug)
			logger.info(AUTO_VIP_TAG1 + "Debug: " + msg);
	}

}
