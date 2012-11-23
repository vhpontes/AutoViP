package org.mcforge.vhpontes;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	public final AutoViP plugin;
	private listCommand listExecutor;
	private checkCommand checkExecutor;
	private claimCommand claimExecutor;
	private endvipCommand endvipExecutor;
	private infoCommand infoExecutor;

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
					if (player.hasPermission("autovip.admin.reload")) {
						plugin.reloadAutoViP();
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - Config File Reloaded");
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("list")) {
					if (player.hasPermission("autovip.admin.list")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - ViP Codes");
						listExecutor = new listCommand();
						listExecutor.list(player);
						return true;
					}
				}
				if (args[0].equalsIgnoreCase("info")) {
					if (player.hasPermission("autovip.user.info")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - ViP Account Info");
						infoExecutor = new infoCommand();
						infoExecutor.info(player);
						return true;

					}
				}
				if (args[0].equalsIgnoreCase("claim")) {
					if (player.hasPermission("autovip.user.claim")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - Correct use: /av claim <VIP_CODE>");
					}
				}
				if (args[0].equalsIgnoreCase("check")) {
					if (player.hasPermission("autovip.user.check")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - Correct use: /av check <VIP_CODE>");
					}
				}
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("claim")) {
					if (player.hasPermission("autovip.user.claim")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - ViP Code Claim: "
								+ ChatColor.WHITE + args[1].toString());
						claimExecutor = new claimCommand();
						claimExecutor.claim(player, args[1].toString());
					}
				}
				if (args[0].equalsIgnoreCase("check")) {
					if (player.hasPermission("autovip.user.check")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - ViP Code Check: "
								+ ChatColor.WHITE + args[1].toString());
						checkExecutor = new checkCommand();
						checkExecutor.check(player, args[1].toString());
					}
				}
				if (args[0].equalsIgnoreCase("endvip")) {
					if (player.hasPermission("autovip.admin.endvip")) {
						player.sendMessage(ChatColor.GREEN
								+ "[AutoViP] - End ViP Player: "
								+ ChatColor.WHITE + args[1].toString());
						endvipExecutor = new endvipCommand();
						endvipExecutor.endvip(player, args[1].toString());
					}
				}
			}
		} else {
			player.sendMessage(ChatColor.GREEN + "--------- AutoViP "
					+ plugin.getDescription().getVersion().toString()
					+ " Commands Help ---------");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " reload" + ChatColor.WHITE + " -> Reload config file.");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " list " + ChatColor.WHITE + " -> List all ViP Codes.");
			player.sendMessage(ChatColor.YELLOW + "/" + label.toString()
					+ " claim <VIP_CODE>" + ChatColor.WHITE
					+ " -> Claim a ViP Code.");
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
