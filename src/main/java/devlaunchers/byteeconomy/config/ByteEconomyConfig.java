package devlaunchers.byteeconomy.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import devlaunchers.byteeconomy.ByteEconomy;

public final class ByteEconomyConfig {

	private static ByteEconomyConfig instance;

	private JavaPlugin byteEconomy;

	private ByteEconomyConfig(JavaPlugin byteEconomy) {
		this.byteEconomy = byteEconomy;

		byteEconomy.saveDefaultConfig();

		loadConfig();
	}

	private void loadConfig() {
		FileConfiguration config = byteEconomy.getConfig();

		
		
	
	}

	public static ByteEconomyConfig getInstance() {
		if (instance == null) {
			instance = new ByteEconomyConfig(ByteEconomy.getInstance());
		}
		return instance;
	}

}
