package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.AutoViP;
import org.mcforge.vhpontes.utils.ConfigFileUtils;

public class CheckCommand extends AutoViP {
	public MySQL mySQL;

	ConfigFileUtils configYML = new ConfigFileUtils();

	public boolean check(CommandSender sender, String code) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		String cfg_mysql_host = configYML.getCustomConfig().getString(
				"general.mysql.host");
		String cfg_mysql_db = configYML.getCustomConfig().getString(
				"general.mysql.database");
		String cfg_mysql_user = configYML.getCustomConfig().getString(
				"general.mysql.username");
		String cfg_mysql_password = configYML.getCustomConfig().getString(
				"general.mysql.password");

		mySQL = new MySQL(AutoViP.logger, "[AutoViP]", cfg_mysql_host, "3306",
				cfg_mysql_db, cfg_mysql_user, cfg_mysql_password);

		try {
			mySQL.open();
		} catch (Exception e) {
			AutoViP.logger.info("AutoViP" + e.getMessage());
			player.sendMessage(ChatColor.RED + "AutoViP" + e.getMessage());
		}
		try {
			String mySQLstring = "SELECT Code, Used, Player, Date FROM Codes WHERE Code='"
					+ code.toString() + "'";
			ResultSet rs = this.mySQL.query(mySQLstring);
			rs.last();
			if (rs.getRow() > 0) {
				if (rs.getBoolean("Used")) {
					player.sendMessage(ChatColor.RED + "Code "
							+ ChatColor.YELLOW + rs.getString("Code")
							+ ChatColor.RED + " used by " + ChatColor.WHITE
							+ rs.getString("Player") + " in "
							+ rs.getString("Date"));
				} else {
					player.sendMessage(ChatColor.GREEN + "Code "
							+ ChatColor.YELLOW + rs.getString("Code")
							+ ChatColor.GREEN + " not used yet.");
				}
			}
			rs.close();
		} catch (SQLException e) {
			player.sendMessage(ChatColor.RED + "AutoViP SQLException: "
					+ e.getMessage());
		}
		return false;
	}

}
