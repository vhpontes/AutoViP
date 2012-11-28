package org.mcforge.vhpontes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.mcforge.vhpontes.commands.CheckCommand;
import org.mcforge.vhpontes.commands.EndVipCommand;
import org.mcforge.vhpontes.commands.InfoCommand;
import org.mcforge.vhpontes.commands.ListCommand;
import org.mcforge.vhpontes.commands.UseCommand;

public class Commands implements CommandExecutor {
	public final AutoViP plugin;
	private ListCommand listExecutor;
	private CheckCommand checkExecutor;
	private UseCommand useExecutor;
	private EndVipCommand endvipExecutor;
	private InfoCommand infoExecutor;

	public Commands(AutoViP plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {

		if (!(sender instanceof Player)) {
			return false;
		}
		Player player = (Player) sender;

		plugin.debugMsg("Command: " + label.toString());

		if (args.length > 0) {
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					if (player.hasPermission("autovip.av.reload")) {
						plugin.reloadAutoViP();
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "Config File Reloaded");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("list")) {
					if (player.hasPermission("autovip.av.list")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "ViP Codes:");
						listExecutor = new ListCommand();
						listExecutor.list(player);
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("info")) {
					if (player.hasPermission("autovip.av.info")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "ViP Account Info:");
						infoExecutor = new InfoCommand();
						infoExecutor.info(player, "");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("use")) {
					if (player.hasPermission("autovip.av.use")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG
								+ "Correct use: /av use <VIP_CODE>");
					}
				}
				if (args[0].equalsIgnoreCase("check")) {
					if (player.hasPermission("autovip.av.check")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG
								+ "Correct use: /av check <VIP_CODE>");
					}
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("use")) {
					if (player.hasPermission("autovip.av.use")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "ViP Code Use: "
								+ ChatColor.WHITE + args[1].toString());
						useExecutor = new UseCommand();
						useExecutor.use(player, args[1].toString());
					}
				}
				if (args[0].equalsIgnoreCase("check")) {
					if (player.hasPermission("autovip.av.check")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "ViP Code Check: "
								+ ChatColor.WHITE + args[1].toString());
						checkExecutor = new CheckCommand();
						checkExecutor.check(player, args[1].toString());
					}
				}
				if (args[0].equalsIgnoreCase("info")) {
					if (player.hasPermission("autovip.av.info")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "ViP Account Info:");
						infoExecutor = new InfoCommand();
						infoExecutor.info(player, args[1].toString());
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("endvip")) {
					if (player.hasPermission("autovip.av.endvip")) {
						player.sendMessage(ChatColor.DARK_AQUA
								+ plugin.AUTO_VIP_TAG + "End ViP Player: "
								+ ChatColor.WHITE + args[1].toString());
						endvipExecutor = new EndVipCommand();
						endvipExecutor.endvip(player, args[1].toString());
					}
				}
			}
		} else {
			player.sendMessage(ChatColor.DARK_AQUA + "--------- AutoViP "
					+ plugin.getDescription().getVersion().toString()
					+ " Commands Help ---------");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " reload" + ChatColor.WHITE + " -> Reload config file.");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " list " + ChatColor.WHITE + " -> List all ViP Codes.");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " use <VIP_CODE>" + ChatColor.WHITE
					+ " -> Use a ViP Code.");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " check <VIP_CODE>" + ChatColor.WHITE
					+ " -> Check a ViP Code.");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " info" + ChatColor.WHITE
					+ " -> Display ViP account info of player.");
			return true;
		}
		return false;
	}
}
