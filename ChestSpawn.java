package randomloot.blockynights;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestSpawn {
	
	static public Map<String, String> chest = new HashMap<String, String>();
	static public Map<String, Location> chestloc = new HashMap<String, Location>();
	
	Main main;
	LootHandler loot;
	Listeners listeners;
	public ChestSpawn(Main main, LootHandler loot,Listeners listeners) { 
		this.main = main;
		this.loot = loot;
		this.listeners = listeners;
	}
	
	public void spawnChest() {
		Random random = new Random();
		int low = 1;
		int high = main.getConfig().getConfigurationSection("spawn").getKeys(false).size()+1;
		int number = random.nextInt(high-low) + low;
		double x = main.getConfig().getDouble("spawn."+number+".x");
		double y = main.getConfig().getDouble("spawn."+number+".y");
		double z = main.getConfig().getDouble("spawn."+number+".z");
		final String world = main.getConfig().getString("spawn."+number+".world");
		final Location loc1 = new Location(Bukkit.getWorld(world),x,y,z);
		final Location loc2 = new Location(Bukkit.getWorld(world),x,y+1,z);
		double signx = main.getConfig().getDouble("sign.x");
		double signy = main.getConfig().getDouble("sign.y");
		double signz = main.getConfig().getDouble("sign.z");
		Location signloc = new Location(Bukkit.getWorld(world),signx,signy,signz);
		final Block block1 = Bukkit.getServer().getWorld(world).getBlockAt(loc1);
		final Block block2 = Bukkit.getServer().getWorld(world).getBlockAt(loc2);
		final Material oldblock1 = Bukkit.getServer().getWorld(world).getBlockAt(loc1).getType();
		final Material oldblock2 = Bukkit.getServer().getWorld(world).getBlockAt(loc2).getType();  
		chest.put("claimed", "no");
		chest.put("claimee", "no");
		chest.put("rarity", rarity());
		chest.put("claimed", "no");
		chestloc.put("loc", loc2);
		block1.setType(Material.EMERALD_BLOCK);
		block2.setType(Material.CHEST);
		final World w = Bukkit.getServer().getWorld(main.getConfig().getString("spawn."+number+".world"));
		w.playEffect(loc2, Effect.ENDER_SIGNAL, 5);
		w.playSound(loc1, Sound.ENDERMAN_TELEPORT, 1, 1);
		Bukkit.getServer().broadcastMessage("§3A random chest has appeared at spawn, §bfirst§3 person to open it claims it, so hurry! §5/spawn");
		Block sign = Bukkit.getServer().getWorld(world).getBlockAt(signloc);
		final Sign updatesign = (Sign) sign.getState();
		updatesign.setLine(0, "§3Chest Spawned at:");
		updatesign.setLine(1, "§5X: §b "+x);
		updatesign.setLine(2, "§5Y: §b "+y);
		updatesign.setLine(3, "§5Z: §b "+z);
		updatesign.update();
		loot.setLootAfterRarity(chest.get("rarity"),loc2);
		new BukkitRunnable() {
	        @Override
	        public void run() {
	    		Chest lootchest = (Chest) block2.getState();
	    		Inventory inv = lootchest.getInventory();
	    		inv.clear();
	            block1.setType(oldblock1);
	            block2.setType(oldblock2);
	            w.playEffect(loc2, Effect.EXPLOSION_HUGE, 5);
	    		w.playSound(loc1, Sound.EXPLODE, 1, 1);
	    		updatesign.setLine(0, "§bNo chest");
	    		updatesign.setLine(1, "§bright now");
	    		updatesign.setLine(2, "§3Last chest found");
	    		updatesign.setLine(3, "§3By:§5 "+chest.get("claimee"));
	    		updatesign.update();	    		
	    		if (chest.get("claimed").equalsIgnoreCase("no")) {
	    			String rarity = listeners.rarityAnnounc(chest.get("rarity"));
		    		Bukkit.getServer().broadcastMessage("§3A "+rarity+" §3chest despawned, no one claimed it!");
		    		}
	        	}
		}.runTaskLater(main, 6000);
	}
	
	public void clearChest() {
		int amount = main.getConfig().getConfigurationSection("spawn").getKeys(false).size();
		for (int i=1;i<=amount;i++) {
			double x = main.getConfig().getDouble("spawn."+i+".x");
			double y = main.getConfig().getDouble("spawn."+i+".y");
			double z = main.getConfig().getDouble("spawn."+i+".z");
			World w = Bukkit.getServer().getWorld(main.getConfig().getString("spawn."+i+".world"));
			Location loc = new Location(w,x,y,z);
			Block blockbottom = loc.getBlock();
			Block blocktop = loc.add(0, 1, 0).getBlock();
			if (blockbottom.getType() == Material.EMERALD_BLOCK && blocktop.getType() == Material.CHEST) {
				Chest clearchest = (Chest) blocktop.getState();
				Inventory inv = clearchest.getInventory();
				inv.clear();
	            w.playEffect(loc, Effect.EXPLOSION_HUGE, 5);
	    		w.playSound(loc, Sound.EXPLODE, 1, 1);
	    		blockbottom.setType(Material.AIR);
	    		blocktop.setType(Material.AIR);
			}
		}
	}
	static public String rarity() {
		Random random = new Random();
		int low = 1;
		int high = 101;
		int number = random.nextInt(high-low) + low;
		String rare = "nothing";
		if (number == 100) {
			rare = "Artifact";
		}
		if (number >= 97 && number < 100) {
			rare = "Legendary";
		}
		if (number >= 90 && number < 97) {
			rare = "Epic";
		}
		if (number >= 80 && number < 90) {
			rare = "Rare";
		}
		if (number >= 60 && number < 80) {
			rare = "Uncommon";
		}
		if (number >= 1 && number < 60) {
			rare = "Common";
		}
		return rare;
	}
}	

