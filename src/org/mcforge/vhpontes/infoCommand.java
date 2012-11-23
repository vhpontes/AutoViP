package org.mcforge.vhpontes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import lib.PatPeter.SQLibrary.MySQL;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class infoCommand {
	public MySQL mySQL;

	public boolean info(CommandSender sender) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
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
			String mySQLstring = "SELECT Player, Ativacao, Meses, Desativacao, Ativo FROM Vips WHERE Player='"
					+ player.getName() + "'";
			player.sendMessage(ChatColor.RED + "SQL>" + mySQLstring);
			ResultSet rs = this.mySQL.query(mySQLstring);
			rs.last();
			if (rs.getRow() > 0) {
				if (rs.getBoolean("Ativo")) {

					// DateFormat dateFormat = new
					// SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
					Date d_atv = rs.getDate("Ativacao");
					long total_vipsd = d_atv.getTime()
							+ (1000 * 60 * 60 * 24 * 30 * rs.getInt("Meses"));
					long diff = total_vipsd - d_atv.getTime();
					long vip_days = diff / (1000 * 60 * 60 * 24);

					player.sendMessage(ChatColor.GREEN + "ViP Ativo desde "
							+ ChatColor.YELLOW + rs.getDate("Ativacao")
							+ ChatColor.GREEN + " sua conta ViP acaba em "
							+ ChatColor.RED + vip_days + " dias.");
				} else {
					player.sendMessage(ChatColor.RED
							+ "Voce nao tem uma conta ViP Ativa!");
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
