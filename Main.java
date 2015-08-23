package randomloot.blockynights;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin {
	private static Plugin plugin;
	
	public void onEnable(){
		final ChestSpawn chestspawn = new ChestSpawn(this,new LootHandler(this),new Listeners(this,null));
		plugin = this;
		getCommand("loot").setExecutor(new Commands(this, new ChestSpawn(this,new LootHandler(this),new Listeners(this,null))));
		registerEvents(this,new Listeners(this,new ChestSpawn(this,new LootHandler(this),new Listeners(this,null))));
		this.saveDefaultConfig();
        BukkitScheduler scheduler = Bukkit.getServer().getScheduler();
        scheduler.scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
            	chestspawn.spawnChest();
            }
        }, 0L, 66000L);
		
	}
	public void onDisable() {
		
	}
    
	public static void registerEvents(org.bukkit.plugin.Plugin plugin, Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
        }
    }
    
	public static Plugin getPlugin() {
        return plugin;
    }
}
