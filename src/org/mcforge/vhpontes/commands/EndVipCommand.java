package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.AutoViP;
import org.mcforge.vhpontes.utils.ConfigFileUtils;

public class EndVipCommand {
	public MySQL mySQL;

	ConfigFileUtils configYML = new ConfigFileUtils();

	public boolean endvip(CommandSender sender, String vipplayer) {
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
		String cfg_getvip_endmessage = configYML.getCustomConfig().getString(
				"general.getViP.endmessage");

		mySQL = new MySQL(AutoViP.logger, "[AutoViP]", cfg_mysql_host, "3306",
				cfg_mysql_db, cfg_mysql_user, cfg_mysql_password);
		try {
			mySQL.open();
		} catch (Exception e) {
			AutoViP.logger.info("AutoViP" + e.getMessage());
			player.sendMessage(ChatColor.RED + "AutoViP" + e.getMessage());
		}

		if (player.isOp()) {
			String cfg_Defaultgroup = configYML.getCustomConfig().getString(
					"general.Defaultgroup");

			List<String> endcommandslist = configYML.getCustomConfig()
					.getStringList("general.EndCommands");

			for (String command : endcommandslist) {
				command = command.replaceAll("<player>", vipplayer.toString());
				command = command.replaceAll("<group>", cfg_Defaultgroup);
				player.performCommand(command);
			}

			try {
				Date dt = new java.util.Date();
				java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String currentTime = sdf.format(dt);
				String mySQLstring2 = "UPDATE Vips SET Ativo=0, Desativacao='"
						+ currentTime + "' WHERE Player='"
						+ vipplayer.toString() + "'";
				ResultSet rs2 = this.mySQL.query(mySQLstring2);
				rs2.close();
			} catch (SQLException e) {
				player.sendMessage(ChatColor.RED + "AutoViP SQLException: "
						+ e.getMessage());
			}
			player.performCommand("mail send "
					+ vipplayer.toString()
					+ " Seu periodo de com conta ViP encerrou. Renove em nosso site http://mcforge.org");
		}
		player.sendMessage(ChatColor.GREEN + cfg_getvip_endmessage);
		return false;
	}
}