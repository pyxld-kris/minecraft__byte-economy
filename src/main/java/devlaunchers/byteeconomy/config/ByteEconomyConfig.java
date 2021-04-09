package devlaunchers.byteeconomy.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.dropevents.DropRule;
import devlaunchers.byteeconomy.dropevents.DropStrategy;

public final class ByteEconomyConfig {

	private static ByteEconomyConfig instance;

	private JavaPlugin byteEconomy;

	private ByteEconomyConfig(JavaPlugin byteEconomy) {
		this.byteEconomy = byteEconomy;

		// Writes default Config to Configuration Folder if it doesn't yet exist
		byteEconomy.saveDefaultConfig();
	}

	public static ByteEconomyConfig getInstance() {
		if (instance == null) {
			instance = new ByteEconomyConfig(ByteEconomy.getInstance());
		}
		return instance;
	}

	public ItemStack getByteItem() {
		FileConfiguration config = byteEconomy.getConfig();

		if (!config.contains("byte.item")) {
			byteEconomy.getLogger()
					.severe("Byte Economy Item isn't configured! Either add valid Settings or reset the Config!");
			return null;
		}

		Material material = Material.valueOf(config.getString("byte.item.material", "PAPER"));
		String displayName = config.getString("byte.item.displayName", "§aByte$");
		int customModel = config.getInt("byte.item.customModel", 8);
		List<String> lore = (List<String>) config.getList("byte.item.lore", Arrays.asList("§fMoney!"));

		ItemStack item = new ItemStack(material, 1);

		// The meta of the paper where we can change the name, and properties of the item.
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(customModel);
		meta.setDisplayName(displayName);

		// Set some lore
		meta.setLore(lore);

		// Now add some flags
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

		// Set the meta of the item to the edited meta.
		item.setItemMeta(meta);

		return item;
	}

	public DropStrategy getDropStrategy(String event) {
		HashMap<String, DropRule> drops = new HashMap<>();

		FileConfiguration config = byteEconomy.getConfig();

		if (!config.contains("byte.drop_strategies." + event)) {
			return new DropStrategy();
		}

		ConfigurationSection eventDropsSection = config.getConfigurationSection("byte.drop_strategies." + event);

		Set<String> keys = eventDropsSection.getKeys(false);

		for (String key : keys) {
			ConfigurationSection cs = eventDropsSection.getConfigurationSection(key);

			if (!cs.getKeys(false).containsAll(Arrays.asList("chance", "protectionRadius", "protectionDuration"))) {
				byteEconomy.getLogger().severe("Misconfigured Drop of event '" + event + "' with key '" + key
						+ "'. Requires 'chance', 'protectionRadius' and 'protectionDuration'!");
				continue;
			}

			int chance = cs.getInt("chance");
			int protectionRadius = cs.getInt("protectionRadius");
			int protectionDuration = cs.getInt("protectionDuration");

			drops.put(key, new DropRule(chance, protectionRadius, protectionDuration));
		}

		return new DropStrategy(drops);
	}
}
