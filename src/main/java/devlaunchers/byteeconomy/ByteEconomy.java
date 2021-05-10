package devlaunchers.byteeconomy;

import devlaunchers.byteeconomy.commands.GiveByteCommand;
import devlaunchers.byteeconomy.config.ByteEconomyConfig;
import devlaunchers.byteeconomy.dropevents.BlockBreakByteDropper;
import devlaunchers.byteeconomy.dropevents.DropRule;
import devlaunchers.byteeconomy.dropevents.DropStrategy;
import devlaunchers.byteeconomy.dropevents.MobKillByteDropper;
import devlaunchers.byteeconomy.items.RecipeManager;
import devlaunchers.byteeconomy.populationevents.ChestBytePopulator;
import devlaunchers.items.DevLauncherItem;
import devlaunchers.plugin.DevLaunchersPlugin;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class ByteEconomy extends DevLaunchersPlugin {

	private static DevLaunchersPlugin instance;
	private static RecipeManager recipeManager;
	private static ByteEconomyConfig config;

	@Override
	public void onEnable() {
		System.out.println("\n" + " _______               __                      ________\n"
				+ "/       \\             /  |                    /        |\n"
				+ "$$$$$$$  | __    __  _$$ |_     ______        $$$$$$$$/   _______   ______   _______    ______   _____  ____   __    __\n"
				+ "$$ |__$$ |/  |  /  |/ $$   |   /      \\       $$ |__     /       | /      \\ /       \\  /      \\ /     \\/    \\ /  |  /  |\n"
				+ "$$    $$< $$ |  $$ |$$$$$$/   /$$$$$$  |      $$    |   /$$$$$$$/ /$$$$$$  |$$$$$$$  |/$$$$$$  |$$$$$$ $$$$  |$$ |  $$ |\n"
				+ "$$$$$$$  |$$ |  $$ |  $$ | __ $$    $$ |      $$$$$/    $$ |      $$ |  $$ |$$ |  $$ |$$ |  $$ |$$ | $$ | $$ |$$ |  $$ |\n"
				+ "$$ |__$$ |$$ \\__$$ |  $$ |/  |$$$$$$$$/       $$ |_____ $$ \\_____ $$ \\__$$ |$$ |  $$ |$$ \\__$$ |$$ | $$ | $$ |$$ \\__$$ |\n"
				+ "$$    $$/ $$    $$ |  $$  $$/ $$       |      $$       |$$       |$$    $$/ $$ |  $$ |$$    $$/ $$ | $$ | $$ |$$    $$ |\n"
				+ "$$$$$$$/   $$$$$$$ |   $$$$/   $$$$$$$/       $$$$$$$$/  $$$$$$$/  $$$$$$/  $$/   $$/  $$$$$$/  $$/  $$/  $$/  $$$$$$$ |\n"
				+ "          /  \\__$$ |                                                                                          /  \\__$$ |\n"
				+ "          $$    $$/                                                                                           $$    $$/\n"
				+ "           $$$$$$/                                                                                             $$$$$$/\n"
				+ "\n" + "Version 0.0.1 - Release\n" + "Created by DevLaunchers. For people, by people.");
		System.out.println("[ByteEconomy] [LOG] Plugin initializing..");
		instance = this;
		
		ByteEconomyLibrary.getInstance();
		
		registerItem(DevLauncherItem.ECONOMY_BYTE_ITEM, getConfig().getItemStack("byte.item"));
		
		recipeManager = new RecipeManager();
		config = ByteEconomyConfig.getInstance();

		this.getCommand("giveByte").setExecutor(new GiveByteCommand());

		System.out.println("[ByteEconomy] [LOG] giveByte command loaded into memory.");

		getServer().getPluginManager().registerEvents(new ChestBytePopulator(), this);

		System.out.println("[ByteEconomy] [LOG] ChestBytePopulator loaded successfully!");

		// Detect breaking blocks and maybe drop byte$
		getServer().getPluginManager().registerEvents(new BlockBreakByteDropper(config.getDropStrategy("block_break")),
				this);

		// Detect killing mobs and maybe drop byte$
		getServer().getPluginManager().registerEvents(new MobKillByteDropper(config.getDropStrategy("mob_kill")), this);
		System.out.println("[ByteEconomy] [LOG] Loading complete. Now earn some awesome byte$!");
	}

    public static DevLaunchersPlugin getInstance() {
        return instance;
    }

    public static RecipeManager getRecipeManager() {
        return recipeManager;
    }

    @Override
    public void onDisable() {
        System.out.println("[ByteEconomy] [LOG] Shutting down, Goodbye for now!");
    }
}


// Possible Byproducts:
//    - Currency (Byte$)
//    -

// Possible Actions:
//    - Interacting with others
//       - Making friends
//       - Competition
//       - Starting kingdoms/clans
//       - Business partners
//    - Exploring/Adventuring
//    - Mining
//    - Crafting
//    - Building
//       - Things that impress others
//       - Bases
//       - Outposts
//       - Pretty places
//       - Funny/cute things
//    - Collecting things...
//       - RARE STUFF
//    - Farming
//    - Surviving
//    - Building stories
//    - Meeting people
//    - Fighting monsters
//    - Fighting other players
//    - Selling/Buying/Trading
//    - Pets
//    - Maximizing efficiency
//       - Building auto farms and automated redstone devices


// FOCUS: Which behaviours do we want to encourage?
//  [COMMUNITY, EXPLORATION, BUILDING, CRAFTING]
//  - Interacting with other players
//  -

// Guillermo: Farming Crops, Mining, Killing Mobs, Enchanting, Using Workbenches like Anvils?

// TODO: Give people items when they first start in the server (Instructions, Byte$, Wood, etc.)
// TODO: Random NPCs appear that have to be chased/found, which then give bytes
// TODO: Advancements gives Byte$?
// TODO: Detect different fortune levels

// TODO: Leveling up gives Byte$?

// TODO: (EPIC) PVP challenges for bytes
// TODO: (EPIC) Quests for Byte$
// TODO: (EPIC) Custom advancement system
// TODO: Make it so Piglins can trade Byte$ (Cant find proper event - ON HOLD)

// UNSURE HOW TO IMPLEMENT
// TODO: Player placed byte bounties - players can place bounties on other players
// TODO: Building challenges
// TODO: Reputation score - game gives you more rewards for being a nicer player
// TODO: Reward players for interacting in chat?

// TODONE: Populate bytes in chests that appear while people are out exploring
// TODONE: Detect silk touch when dropping bytes
// TODONE: Create general byte dropping protection scheme that can be reused - based on radius and time period?
// TODONE: Drop byte$ from mining certain blocks (need to monitor so silk touch and farms don't break economy)
// TODONE: Killing enemies give Byte$
// TODONE: Make better config structure for Byte drop chances (make it so types can have separate chances of dropping, and maybe different timeout)
// TODONE: Make it so fortune has a higher chance to drop bytes
