package org.mcforge.vhpontes.commands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.AutoViP;
import org.mcforge.vhpontes.utils.DatabaseUtils;

public class InfoCommand extends AutoViP {

	private String infoPlayer;

	public boolean info(CommandSender sender, String arqplayer) {
		Player player = (Player) sender;

		if (!(sender instanceof Player)) {
			return false;
		}

		DatabaseUtils DB = new DatabaseUtils();
		if (DB.checkConnection()) {
			try {
				if (arqplayer.isEmpty()) {
					infoPlayer = player.getName();
				} else {
					infoPlayer = arqplayer.toString();
				}
				String mySQLstring = "SELECT Player, Ativacao, Meses, Desativacao, Ativo FROM Vips WHERE Player='"
						+ infoPlayer + "'";
				ResultSet rs = DB.mySQL.query(mySQLstring);
				rs.last();
				if (rs.getRow() > 0) {
					if (rs.getBoolean("Ativo")) {
						SimpleDateFormat formato = new SimpleDateFormat(
								"dd/MM/yyyy");
						Date d_atv = rs.getDate("Ativacao");
						String data_formatada = formato.format(d_atv);

						long tempo_consulta = System.currentTimeMillis();
						long meses = rs.getLong("Meses");
						long datatime_ativacao = d_atv.getTime();
						// long tempo_contratado = ((1000 * 60 * 60 * 24 * 30) *
						// meses);
						long tempo_contratado = 2592000000L * meses;

						long tempo_vip = datatime_ativacao + tempo_contratado;
						long diff = tempo_vip - tempo_consulta;
						long vip_days = (diff / (1000L * 60L * 60L * 24L)) + 1;

						player.sendMessage(ChatColor.YELLOW + infoPlayer
								+ ChatColor.GREEN
								+ ", seu ViP foi ativado no dia "
								+ ChatColor.YELLOW + data_formatada
								+ ChatColor.GREEN + " voce ainda tem "
								+ ChatColor.YELLOW + vip_days + " dias "
								+ ChatColor.GREEN + " de conta ViP restantes!");
						player.sendMessage(ChatColor.GREEN
								+ "Meses contratados:  " + ChatColor.YELLOW
								+ meses);
						if (vip_days < 5) {
							player.sendMessage(ChatColor.RED
									+ "Atencao! Sua conta ViP acaba em "
									+ vip_days
									+ " dias. "
									+ ChatColor.GREEN
									+ "Adquira uma novo ViP Code em nosso site: http://mcforge.org");
						}
					}
				} else {
					player.sendMessage(ChatColor.YELLOW + infoPlayer
							+ ChatColor.RED
							+ ", nao tem nenhuma conta ViP Ativa!");
				}
				rs.close();
			} catch (SQLException e) {
				player.sendMessage(ChatColor.RED + "AutoViP SQLException: "
						+ e.getMessage());
			}
		} else {
			player.sendMessage(ChatColor.RED + "AutoViP No connection!");
		}

		return false;
	}
}
