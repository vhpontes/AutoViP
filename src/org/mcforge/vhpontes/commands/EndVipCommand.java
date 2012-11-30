package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.utils.ConfigFileUtils;
import org.mcforge.vhpontes.utils.DatabaseUtils;

public class EndVipCommand {

	ConfigFileUtils configYML = new ConfigFileUtils();

	public boolean endvip(CommandSender sender, String vipplayer) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		DatabaseUtils DB = new DatabaseUtils();
		if (DB.checkConnection()) {

			if (player.isOp()) {
				String cfg_getvip_endmessage = configYML.getCustomConfig()
						.getString("general.getViP.message");

				String cfg_Defaultgroup = configYML.getCustomConfig()
						.getString("general.Defaultgroup");

				List<String> endcommandslist = configYML.getCustomConfig()
						.getStringList("general.EndCommands");

				for (String command : endcommandslist) {
					command = command.replaceAll("<player>",
							vipplayer.toString());
					command = command.replaceAll("<group>", cfg_Defaultgroup);
					player.performCommand(command);
				}

				try {
					Date dt = new java.util.Date();
					java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
					String currentTime = sdf.format(dt);
					String mySQLstring2 = "UPDATE Vips SET Ativo=0, Desativacao='"
							+ currentTime
							+ "' WHERE Player='"
							+ vipplayer.toString() + "'";
					ResultSet rs2 = DB.mySQL.query(mySQLstring2);
					rs2.close();
				} catch (SQLException e) {
					player.sendMessage(ChatColor.RED + "AutoViP SQLException: "
							+ e.getMessage());
				}
				player.performCommand("mail send "
						+ vipplayer.toString()
						+ " Seu periodo de com conta ViP encerrou. Renove em nosso site http://mcforge.org");

				player.sendMessage(ChatColor.GREEN + cfg_getvip_endmessage);
			}
		}
		return false;
	}
}