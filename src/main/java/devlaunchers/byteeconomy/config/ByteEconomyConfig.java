package devlaunchers.byteeconomy.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.dropevents.DropRule;
import devlaunchers.byteeconomy.dropevents.DropStrategy;
import devlaunchers.config.DevLauncherConfiguration;
import devlaunchers.plugin.DevLaunchersPlugin;

public final class ByteEconomyConfig {

	private static ByteEconomyConfig instance;

	private DevLaunchersPlugin byteEconomy;

	private ByteEconomyConfig(DevLaunchersPlugin byteEconomy) {
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
		return byteEconomy.getConfig().getItemStack("byte.item");
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
