package randomloot.blockynights;

import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class Listeners implements Listener {

	ChestSpawn chestspawn;
	Main main;
	public Listeners(Main main, ChestSpawn chestspawn) { 
		this.main = main;
		this.chestspawn = chestspawn;
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	 public void onInventoryOpenEvent(InventoryOpenEvent event){
		if (ChestSpawn.chestloc.get("loc").equals(event.getPlayer().getTargetBlock((Set<Material>) null, 10).getLocation())) {
			if (ChestSpawn.chest.get("claimed").equalsIgnoreCase("no") || ChestSpawn.chest.get("claimee").equals(event.getPlayer().getName())) {
				if (event.isCancelled()) { event.setCancelled(false); }
			}
		} 
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChest(PlayerInteractEvent event) {
			if (event.getAction() == Action.RIGHT_CLICK_BLOCK && event.getClickedBlock().getType() == Material.CHEST) {
				if (ChestSpawn.chestloc.get("loc").equals(event.getClickedBlock().getLocation())) {
					if (ChestSpawn.chest.get("claimed").equalsIgnoreCase("no") || ChestSpawn.chest.get("claimee").equals(event.getPlayer().getName())) {
						String player = event.getPlayer().getName();
						String rarity = rarityAnnounc(ChestSpawn.chest.get("rarity"));
						if (event.isCancelled()) { event.setCancelled(false); }
						if (ChestSpawn.chest.get("claimed").equalsIgnoreCase("no")) {
							Bukkit.getServer().broadcastMessage("§b"+player+"§3 claimed the "+rarity+"§3 Chest!");
							ChestSpawn.chest.put("claimed", "yes");
							ChestSpawn.chest.put("claimee", event.getPlayer().getName());
							updateSign(event.getPlayer().getWorld().getName());
						}
					} else { 
						event.setCancelled(true);
						event.getPlayer().sendMessage("§3Sorry this chest is already claimed by §b"+ChestSpawn.chest.get("claimee"));
						}
				}
			}
	}
	
	public void updateSign(String world) {
		double signx = main.getConfig().getDouble("sign.x");
		double signy = main.getConfig().getDouble("sign.y");
		double signz = main.getConfig().getDouble("sign.z");
		Location signloc = new Location(Bukkit.getWorld(world),signx,signy,signz);
		Block sign = Bukkit.getServer().getWorld(world).getBlockAt(signloc);
		final Sign updatesign = (Sign) sign.getState();
		updatesign.setLine(0, "§bNo chest");
		updatesign.setLine(1, "§bright now");
		updatesign.setLine(2, "§3Last chest found");
		updatesign.setLine(3, "§3By:§5 "+ChestSpawn.chest.get("claimee"));
		updatesign.update();
		updatesign.update();
	}
	public String rarityAnnounc(String rare) {
		if (rare.equalsIgnoreCase("common")) {
			return "§fCommon§r";
		}
		if (rare.equalsIgnoreCase("uncommon")) {
			return "§aUncommon§r";
		}
		if (rare.equalsIgnoreCase("rare")) {
			return "§9Rare§r";
		}
		if (rare.equalsIgnoreCase("epic")) {
			return "§5Epic§r";
		}
		if (rare.equalsIgnoreCase("legendary")) {
			return "§6Legendary§r";
		}
		if (rare.equalsIgnoreCase("artifact")) {
			return "§cArtifact§r";
		}
		return rare;
	}
}
