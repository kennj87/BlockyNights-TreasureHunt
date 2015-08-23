package randomloot.blockynights;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
	Main main;
	ChestSpawn chestspawn;
	public Commands(Main main, ChestSpawn chestspawn) { 
		this.main = main;
		this.chestspawn = chestspawn;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		if (player.isOp()) {
			if (args.length == 1 && args[0].equalsIgnoreCase("setspawn")) {
				Location loc = player.getLocation(); 
				int x = loc.getBlockX();
				int y = loc.getBlockY();
				int z = loc.getBlockZ();
				String world = loc.getWorld().getName();
				int amount;
				if (main.getConfig().getString("spawn") != null) {
					amount = main.getConfig().getConfigurationSection("spawn").getKeys(false).size()+1;
				} else {
					amount = 1;
				}
				main.getConfig().set("spawn."+amount+".x", x);
				main.getConfig().set("spawn."+amount+".y", y);
				main.getConfig().set("spawn."+amount+".z", z);
				main.getConfig().set("spawn."+amount+".world", world);
				sender.sendMessage("spawnset " + amount);
				main.saveConfig();
			}	
			if (args.length == 1 && args[0].equalsIgnoreCase("test")) {
				player.sendMessage("Spawning chest");
				chestspawn.spawnChest();
			}
			if (args.length == 4 && args[0].equalsIgnoreCase("setsign")) {
				int x = Integer.parseInt(args[1]);
				int y = Integer.parseInt(args[2]);
				int z = Integer.parseInt(args[3]);
				main.getConfig().set("sign.x", x);
				main.getConfig().set("sign.y", y);
				main.getConfig().set("sign.z", z);
				main.getConfig().set("sign.world", player.getWorld().getName());
				main.saveConfig();
				player.sendMessage("Sign set");
			}
			if (args.length == 4 && args[0].equalsIgnoreCase("additem")) {
				player.sendMessage("Adding item");
				int amount;
				if (main.getConfig().getString("item."+args[1]) != null) {
					amount = main.getConfig().getConfigurationSection("item."+args[1]).getKeys(false).size()+1;
				} else {
					amount = 1;
				}
				main.getConfig().set("item."+args[1]+"."+amount+".item", args[2]);
				main.getConfig().set("item."+args[1]+"."+amount+".stacks", args[3]);
				main.saveConfig();
			}
			if (args.length == 5 && args[0].equalsIgnoreCase("edititem") && args[3].equalsIgnoreCase("amount")) {
				player.sendMessage("Setting amount");
				int amount = Integer.parseInt(args[2]);
				main.getConfig().set("item."+args[1]+"."+amount+".amount", Integer.parseInt(args[4]));
				main.saveConfig();
			}
			if (args.length == 6 && args[0].equalsIgnoreCase("edititem") && args[3].equalsIgnoreCase("enchant")) {
				player.sendMessage("Enchanting item");
				int amount = Integer.parseInt(args[2]);
				int enchantamount;
				if (main.getConfig().getConfigurationSection("item."+args[1]+"."+amount+".enchant") != null) {
					enchantamount = main.getConfig().getConfigurationSection("item."+args[1]+"."+amount+".enchant").getKeys(false).size()+1;
				} else {
					enchantamount = 1;
				}
				main.getConfig().set("item."+args[1]+"."+amount+".enchant."+enchantamount+".type", args[4]);
				main.getConfig().set("item."+args[1]+"."+amount+".enchant."+enchantamount+".level", Integer.parseInt(args[5]));
				main.saveConfig();
			}
		}
		return true;
	}
}
