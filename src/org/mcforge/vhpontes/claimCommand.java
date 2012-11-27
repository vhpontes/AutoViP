package org.mcforge.vhpontes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class claimCommand {
	// Bukkit.getServer().broadcastMessage(cfg_getvip_message);
	public MySQL mySQL;

	ConfigFile configYML = new ConfigFile();

	public boolean claim(CommandSender sender, String code) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		// player.sendMessage(ChatColor.RED + "CODE ARG2: " + code.toString());

		String cfg_mysql_host = configYML.getCustomConfig().getString(
				"general.mysql.host");
		String cfg_mysql_db = configYML.getCustomConfig().getString(
				"general.mysql.db");
		String cfg_mysql_user = configYML.getCustomConfig().getString(
				"general.mysql.user");
		String cfg_mysql_password = configYML.getCustomConfig().getString(
				"general.mysql.password");
		String cfg_getvip_message = configYML.getCustomConfig().getString(
				"general.getViP.message");

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
					player.setOp(true);

					int cfg_getvip_money = configYML.getCustomConfig().getInt(
							"general.getViP.money");
					int cfg_getvip_xp = configYML.getCustomConfig().getInt(
							"general.getViP.xp");
					String cfg_ViPgroup = configYML.getCustomConfig()
							.getString("general.ViPgroup");

					List<String> itemlist = configYML.getCustomConfig()
							.getStringList("general.Items");

					List<String> commandslist = configYML.getCustomConfig()
							.getStringList("general.Commands");

					for (String item : itemlist) {
						player.performCommand("give " + player.getName() + " "
								+ item);
					}

					for (String command : commandslist) {
						command = command.replaceAll("<player>",
								player.getName());
						command = command.replaceAll("<group>", cfg_ViPgroup);
						player.performCommand(command);
					}

					/* If set give Money to player in claim */
					if (cfg_getvip_money > 0) {
						player.performCommand("eco give " + player.getName()
								+ " " + cfg_getvip_money);
					}

					/* If set give XP to player in claim */
					if (cfg_getvip_xp > 0) {
						player.performCommand("exp give " + player.getName()
								+ " " + cfg_getvip_xp);
					}

					try {
						Date dt = new java.util.Date();
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String currentTime = sdf.format(dt);
						String mySQLstring1 = "UPDATE Codes SET Used=1, Player='"
								+ player.getName()
								+ "', Date='"
								+ currentTime
								+ "' WHERE Code='" + code.toString() + "'";
						ResultSet rs1 = this.mySQL.query(mySQLstring1);
						rs1.close();
					} catch (SQLException e) {
						player.sendMessage(ChatColor.RED
								+ "AutoViP SQLException: " + e.getMessage());
					}
					try {
						Date dt = new java.util.Date();
						java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
								"yyyy-MM-dd HH:mm:ss");
						String currentTime = sdf.format(dt);
						String mySQLstring2 = "UPDATE Vips SET Ativo=1 Player='"
								+ player.getName()
								+ "', Ativacao='"
								+ currentTime
								+ "' WHERE Code_used='"
								+ code.toString() + "'";
						ResultSet rs2 = this.mySQL.query(mySQLstring2);
						rs2.close();
					} catch (SQLException e) {
						player.sendMessage(ChatColor.RED
								+ "AutoViP SQLException: " + e.getMessage());
					}
					player.setOp(false);
					player.sendMessage(ChatColor.GREEN + cfg_getvip_message);
				}
				rs.close();
			}
		} catch (SQLException e) {
			player.sendMessage(ChatColor.RED + "AutoViP SQLException: "
					+ e.getMessage());
		}
		return false;
	}
}