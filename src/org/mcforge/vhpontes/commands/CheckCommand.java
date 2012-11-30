package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.AutoViP;
import org.mcforge.vhpontes.utils.ConfigFileUtils;
import org.mcforge.vhpontes.utils.DatabaseUtils;

public class CheckCommand extends AutoViP {

	ConfigFileUtils configYML = new ConfigFileUtils();

	public boolean check(CommandSender sender, String code) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		DatabaseUtils DB = new DatabaseUtils();
		if (DB.checkConnection()) {

			try {
				String mySQLstring = "SELECT Code, Used, Player, Date FROM Codes WHERE Code='"
						+ code.toString() + "'";
				ResultSet rs = DB.mySQL.query(mySQLstring);
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
		}
		return false;
	}

}
