package org.mcforge.vhpontes;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class listCommand {
	public MySQL mySQL;

	public void list(CommandSender sender) {

		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return;
		}

		mySQL = new MySQL(AutoViP.logger, "[AutoViP]", AutoViP.cfg_mysql_host,
				"3306", AutoViP.cfg_mysql_db, AutoViP.cfg_mysql_user,
				AutoViP.cfg_mysql_password);
		try {
			mySQL.open();
		} catch (Exception e) {
			AutoViP.logger.info("AutoViP" + e.getMessage());
			player.sendMessage(ChatColor.RED + "AutoViP" + e.getMessage());
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
