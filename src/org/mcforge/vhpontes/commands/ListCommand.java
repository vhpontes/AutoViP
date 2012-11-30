package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.AutoViP;
import org.mcforge.vhpontes.utils.DatabaseUtils;

public class ListCommand extends AutoViP {

	public void list(CommandSender sender) {

		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return;
		}

		DatabaseUtils DB = new DatabaseUtils();
		if (DB.checkConnection()) {
			try {
				ResultSet rs = DB.mySQL
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
}
