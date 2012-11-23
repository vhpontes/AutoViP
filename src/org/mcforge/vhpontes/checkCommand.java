package org.mcforge.vhpontes;

import java.sql.ResultSet;
import java.sql.SQLException;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class checkCommand implements CommandExecutor {
	public MySQL mySQL;

	public boolean check(CommandSender sender, String code) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		// player.sendMessage(ChatColor.RED + "CODE ARG2: " + code.toString());

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
			String mySQLstring = "SELECT Code, Used, Player, Date FROM Codes WHERE Code='"
					+ code.toString() + "'";
			// player.sendMessage(ChatColor.RED + "SQL>" + mySQLstring);
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

	@Override
	public boolean onCommand(CommandSender arg0, Command arg1, String arg2,
			String[] arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}
