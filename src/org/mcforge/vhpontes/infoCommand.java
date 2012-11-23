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

	ConfigFile configYML = new ConfigFile();

	public boolean info(CommandSender sender) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		String cfg_mysql_host = configYML.getCustomConfig().getString(
				"general.mysql.host");
		String cfg_mysql_db = configYML.getCustomConfig().getString(
				"general.mysql.db");
		String cfg_mysql_user = configYML.getCustomConfig().getString(
				"general.mysql.user");
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
			String mySQLstring = "SELECT Player, Ativacao, Meses, Desativacao, Ativo FROM Vips WHERE Player='"
					+ player.getName() + "'";
			// player.sendMessage(ChatColor.RED + "SQL>" + mySQLstring);
			ResultSet rs = this.mySQL.query(mySQLstring);
			rs.last();
			if (rs.getRow() > 0) {
				if (rs.getBoolean("Ativo")) {

					Date d_atv = rs.getDate("Ativacao");
					long tempo_consulta = System.currentTimeMillis();
					long datatime_ativacao = d_atv.getTime();
					long tempo_contratado = (1000 * 60 * 60 * 24 * 30 * rs
							.getInt("Meses"));
					long tempo_vip = datatime_ativacao + tempo_contratado;
					long diff = tempo_consulta - tempo_vip;
					long vip_days = diff / (1000 * 60 * 60 * 24);
					long contratado_vip_days = tempo_vip
							/ (1000 * 60 * 60 * 24);

					player.sendMessage(ChatColor.GREEN + "ViP Ativo desde "
							+ ChatColor.YELLOW + rs.getDate("Ativacao")
							+ ChatColor.GREEN + " sua conta ViP acaba em "
							+ ChatColor.RED + vip_days
							+ " dias de um total de " + contratado_vip_days
							+ "dias.");
				} else {
					player.sendMessage(ChatColor.RED + player.getName()
							+ ", voce nao tem nenhuma conta ViP Ativa!");
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
