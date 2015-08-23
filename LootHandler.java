package randomloot.blockynights;

import java.util.Random;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class LootHandler {

	Main main;
	public LootHandler(Main main) { 
		this.main = main;
	}
	
	public void setLootAfterRarity(String rarity,Location loc) {
		rarity = rarity.toLowerCase();
		Block block = loc.getBlock();
		Chest chest = (Chest) block.getState();
		Inventory inv = chest.getInventory();
		int number = 1;
		int rarityget = 1;
		int amountspawned = 1;
		if (rarity.equalsIgnoreCase("common")) { rarityget = 1; }
		if (rarity.equalsIgnoreCase("uncommon")) { rarityget = 2; }
		if (rarity.equalsIgnoreCase("rare")) { rarityget = 3; }
		if (rarity.equalsIgnoreCase("epic")) { rarityget = 4; }
		if (rarity.equalsIgnoreCase("legendary")) { rarityget = 5; }
		if (rarity.equalsIgnoreCase("artifact")) { rarityget = 6; }		
		
		if (rarityget >= 1) {
			int lootamount = itemAmount(5);
			int i = 0;
			rarity = "common";
			while (i < lootamount) {
				number = getLootNumber(rarity);
				String stackable = main.getConfig().getString("item."+rarity+"."+number+".stacks");
				if (main.getConfig().getString("item."+rarity+"."+number+".amount") != null) {
					amountspawned = main.getConfig().getInt("item."+rarity+"."+number+".amount");
				} else { amountspawned = 1; }
				if (stackable.equalsIgnoreCase("yes")) {
					amountspawned = itemAmount(65);
				}
				ItemStack common = new ItemStack(common(number),amountspawned);
				if (checkEnchant(rarity,number)) {
					setEnchants(common,number,rarity);
				}
				inv.addItem(common);
				i++;
			}
		}
		if (rarityget >= 2) {
			int lootamount = itemAmount(4);
			int i = 0;
			rarity = "uncommon";
			while (i < lootamount) {
				number = getLootNumber(rarity);
				String stackable = main.getConfig().getString("item."+rarity+"."+number+".stacks");
				if (main.getConfig().getString("item."+rarity+"."+number+".amount") != null) {
					amountspawned = main.getConfig().getInt("item."+rarity+"."+number+".amount");
				} else { amountspawned = 1; }
				if (stackable.equalsIgnoreCase("yes")) {
					amountspawned = itemAmount(65);
				}
				ItemStack uncommon = new ItemStack(unCommon(number),amountspawned);
				if (checkEnchant(rarity,number)) {
					setEnchants(uncommon,number,rarity);
				}
				inv.addItem(uncommon);
				i++;
			}
		}
		if (rarityget >= 3) {
			int lootamount = itemAmount(3);
			int i = 0;
			rarity = "rare";
			while (i < lootamount) {
				number = getLootNumber(rarity);
				String stackable = main.getConfig().getString("item."+rarity+"."+number+".stacks");
				if (main.getConfig().getString("item."+rarity+"."+number+".amount") != null) {
					amountspawned = main.getConfig().getInt("item."+rarity+"."+number+".amount");
				} else { amountspawned = 1; }
				if (stackable.equalsIgnoreCase("yes")) {
					amountspawned = itemAmount(65);
				}
				ItemStack rare = new ItemStack(rare(number),amountspawned);
				if (checkEnchant(rarity,number)) {
					setEnchants(rare,number,rarity);
				}
				inv.addItem(rare);
				i++;
			}
		}
		if (rarityget >= 4) {
			int lootamount = itemAmount(3);
			int i = 0;
			rarity = "epic";
			while (i < lootamount) {
				number = getLootNumber(rarity);
				String stackable = main.getConfig().getString("item."+rarity+"."+number+".stacks");
				if (main.getConfig().getString("item."+rarity+"."+number+".amount") != null) {
					amountspawned = main.getConfig().getInt("item."+rarity+"."+number+".amount");
				} else { amountspawned = 1; }
				if (stackable.equalsIgnoreCase("yes")) {
					amountspawned = itemAmount(65);
				}
				ItemStack epic = new ItemStack(epic(number),amountspawned);
				if (checkEnchant(rarity,number)) {
					setEnchants(epic,number,rarity);
				}
				inv.addItem(epic);
				i++;
			}
		}
		if (rarityget >= 5) {
			int lootamount = itemAmount(3);
			int i = 0;
			rarity = "legendary";
			while (i < lootamount) {
				number = getLootNumber(rarity);
				String stackable = main.getConfig().getString("item."+rarity+"."+number+".stacks");
				if (main.getConfig().getString("item."+rarity+"."+number+".amount") != null) {
					amountspawned = main.getConfig().getInt("item."+rarity+"."+number+".amount");
				} else { amountspawned = 1; }
				if (stackable.equalsIgnoreCase("yes")) {
					amountspawned = itemAmount(65);
				}
				ItemStack legendary = new ItemStack(legendary(number),amountspawned);
				if (checkEnchant(rarity,number)) {
					setEnchants(legendary,number,rarity);
				}
				inv.addItem(legendary);
				i++;
			}
		}
		if (rarityget == 6) {
			rarity = "artifact";
				number = getLootNumber(rarity);
				String stackable = main.getConfig().getString("item."+rarity+"."+number+".stacks");
				if (main.getConfig().getString("item."+rarity+"."+number+".amount") != null) {
					amountspawned = main.getConfig().getInt("item."+rarity+"."+number+".amount");
				} else { amountspawned = 1; }
				if (stackable.equalsIgnoreCase("yes")) {
					amountspawned = itemAmount(65);
				}
				ItemStack artifact = new ItemStack(artifact(number),amountspawned);
				if (checkEnchant(rarity,number)) {
					setEnchants(artifact,number,rarity);
				}
				inv.addItem(artifact);
		}
	}
	
	
	public ItemStack setEnchants(ItemStack item,int number,String rarity) {
		int enchantamounts = main.getConfig().getConfigurationSection("item."+rarity+"."+number+".enchant").getKeys(false).size()+1;
		int i = 1;
		if (item.getType() == Material.ENCHANTED_BOOK) {
			Enchantment enchant = Enchantment.getByName(main.getConfig().getString("item."+rarity+"."+number+".enchant."+i+".type"));
			int enchantlevel = main.getConfig().getInt("item."+rarity+"."+number+".enchant."+i+".level");
			EnchantmentStorageMeta meta = (EnchantmentStorageMeta) item.getItemMeta();
	        meta.addStoredEnchant(enchant, enchantlevel,true);
	        item.setItemMeta(meta);
	        i++;
		} else {
			while (i < enchantamounts) {
				Enchantment enchant = Enchantment.getByName(main.getConfig().getString("item."+rarity+"."+number+".enchant."+i+".type"));
				int enchantlevel = main.getConfig().getInt("item."+rarity+"."+number+".enchant."+i+".level");
		        item.addEnchantment(enchant,enchantlevel);
		        i++;
			}
		}
		return item;
	}
	
	public boolean checkEnchant(String rarity,int number) {
		if (main.getConfig().getString("item."+rarity+"."+number+".enchant") != null) {
			return true;
		}
		return false;
	}
	
	
	
	public Material common(int number) {
		String material = main.getConfig().getString("item.common."+number+".item");
		return Material.getMaterial(material);
		
	}
	public Material unCommon(int number) {
		String material = main.getConfig().getString("item.uncommon."+number+".item");
		return Material.getMaterial(material);
	}
	public Material rare(int number) {
		String material = main.getConfig().getString("item.rare."+number+".item");
		return Material.getMaterial(material);
	}
	public Material epic(int number) {
		String material = main.getConfig().getString("item.epic."+number+".item");
		return Material.getMaterial(material);
	}
	public Material legendary(int number) {
		String material = main.getConfig().getString("item.legendary."+number+".item");
		return Material.getMaterial(material);
	}
	public Material artifact(int number) {
		String material = main.getConfig().getString("item.artifact."+number+".item");
		return Material.getMaterial(material);
	}
	
	
	
	public int getLootNumber(String rarity) {
		Random random = new Random();
		int low = 1;
		int high = main.getConfig().getConfigurationSection("item."+rarity).getKeys(false).size()+1;
		int number = random.nextInt(high-low) + low;
		return number;
	}
	
	public int itemAmount(int amount) {
		Random random = new Random();
		int low = 1;
		int high = amount;
		int number = random.nextInt(high-low) + low;
		return number;
	}
}
