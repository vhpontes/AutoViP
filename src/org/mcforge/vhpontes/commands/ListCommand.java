package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.AutoViP;
import org.mcforge.vhpontes.utils.ConfigFileUtils;

public class ListCommand extends AutoViP {
	public MySQL mySQL;

	ConfigFileUtils configYML = new ConfigFileUtils();

	public void list(CommandSender sender) {

		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return;
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
		}

		try {
			ResultSet rs = this.mySQL
					.query("SELECT Code FROM Codes WHERE Used = 0");

			while (rs.next()) {
				player.sendMessage(ChatColor.WHITE + rs.getString("Code"));
			}
			rs.close();
		} catch (SQLException e) {
			player.sendMessage(ChatColor.RED + "AutoViP SQLException: "
					+ e.getMessage());
		}
		return;
	}

}
