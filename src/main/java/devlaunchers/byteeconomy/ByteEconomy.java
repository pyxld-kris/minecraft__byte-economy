package devlaunchers.byteeconomy;


import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

// Actions:
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

// Byproducts:
//    - Currency (Byte$)
//    -

// FOCUS: Which behaviours do we want to encourage?
//  [COMMUNITY, EXPLORATION, BUILDING, CRAFTING]
//  - Interacting with other players
//  -

// Guillermo: Farming Crops, Mining, Killing Mobs, Enchanting, Using Workbenches like Anvils?

// TODONE: Detect silk touch when dropping bytes
// TODONE: Create general byte dropping protection scheme that can be reused - based on radius and time period?
// TODONE: Drop byte$ from mining certain blocks (need to monitor so silk touch and farms don't break economy)
// TODONE: Killing enemies give Byte$
// TODONE: Make better config structure for Byte drop chances (make it so types can have separate chances of dropping, and maybe different timeout)
// TODONE: Make it so fortune has a higher chance to drop bytes
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

public final class ByteEconomy extends JavaPlugin {

    private static JavaPlugin instance;
    private static ItemStack byteItem;

    @Override
    public void onEnable() {
        instance = this;

        initByteItem();
        initDiamondToByteRecipe();
        initByteToDiamondRecipe();

        this.getCommand("giveByte").setExecutor(new GiveByteCommand());

        getServer().getPluginManager().registerEvents(new ChestBytePopulator(), this);

        // Detect breaking blocks and maybe drop byte$
        getServer().getPluginManager().registerEvents(new BlockBreakByteDropper(
                new DropStrategy(
                        new HashMap<Object, DropRule>(){{
                                put(Material.DIAMOND_ORE, new DropRule(50, 1, 60));
                                put(Material.EMERALD_ORE, new DropRule(100, 1, 60));
                    }}
                )),
                this);

        // Detect killing mobs and maybe drop byte$
        getServer().getPluginManager().registerEvents(new MobKillByteDropper(
                new DropStrategy(
                        new HashMap<Object, DropRule>(){{
                            put(EntityType.CAVE_SPIDER, new DropRule(5, 2, 60));
                            put(EntityType.CREEPER, new DropRule(5, 2, 60));
                            put(EntityType.DROWNED, new DropRule(2, 2, 60));
                            put(EntityType.ENDERMAN, new DropRule(2, 2, 60));
                            put(EntityType.GHAST, new DropRule(2, 2, 60));
                            put(EntityType.HOGLIN, new DropRule(2, 2, 60));
                            put(EntityType.PIGLIN, new DropRule(2, 2, 60));
                            put(EntityType.PIGLIN_BRUTE, new DropRule(5, 2, 60));
                            put(EntityType.PILLAGER, new DropRule(5, 2, 60));
                            put(EntityType.SLIME, new DropRule(5, 2, 60));
                            put(EntityType.SKELETON, new DropRule(5, 2, 60));
                            put(EntityType.SPIDER, new DropRule(2, 2, 60));
                            put(EntityType.STRIDER, new DropRule(10, 2, 60));
                            put(EntityType.WITCH, new DropRule(5, 2, 60));
                            put(EntityType.ZOMBIE, new DropRule(5, 2, 60));
                        }}
                )), this);

    }

    public static JavaPlugin getInstance() {
        return instance;
    }

    public static ItemStack getByteItem() {
        return byteItem;
    }

    private void initByteItem() {
        // Our custom variable which we will be changing around.
        ItemStack item = new ItemStack(Material.PAPER, 1);

        // The meta of the paper where we can change the name, and properties of the item.
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(8);
        meta.setDisplayName(ChatColor.GREEN + "Byte$");

        // Set some lore
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.WHITE+"Money!");
        meta.setLore(lore);

        // Now add some flags
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        // Add enchantments
        //item.addEnchantment(Enchantment.DAMAGE_ALL, 5);


        // Set the meta of the sword to the edited meta.
        item.setItemMeta(meta);

        this.byteItem = item;
    }

    private void initRecipe(String recipeKey, ItemStack result, String[] recipeShape, HashMap<String, ItemStack> ingredientItemKeys) {
        // create a NamespacedKey for your recipe
        NamespacedKey key = new NamespacedKey(this, recipeKey);

        // Create our custom recipe variable
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
        recipe.shape(recipeShape[0], recipeShape[1], recipeShape[2]);

        // Set what the letters represent.
        for (String itemKey : ingredientItemKeys.keySet()) {
            recipe.setIngredient(itemKey.charAt(0), ingredientItemKeys.get(itemKey));
        }

        // Finally, add the recipe to the bukkit recipes
        getServer().addRecipe(recipe);
    }

    private void initDiamondToByteRecipe() {
        ItemStack craftingResult = byteItem.clone();
        craftingResult.setAmount(9);
        initRecipe(
                "byte",                                 // Recipe internal name
                craftingResult,                                 // Resulting item
                new String[]{                                   // Crafting shape
                        "   ",
                        " D ",
                        "   "
                },
                new HashMap<String, ItemStack>() {{              // Key value pair corresponding to shape entries
                    put("D", new ItemStack(Material.DIAMOND));
                }}
        );

        System.out.println("Initialized Diamond to Byte Recipe!");
    }

    private void initByteToDiamondRecipe() {
        initRecipe(
                "diamond_from_byte",                              // Recipe internal name
                new ItemStack(Material.DIAMOND),                          // Resulting item
                new String[]{                                             // Crafting shape
                        "BBB",
                        "BBB",
                        "BBB"
                },
                new HashMap<String, ItemStack>() {{                     // Key value pair corresponding to shape entries
                    put("B", byteItem);
                }}
        );

        System.out.println("Initialized Byte to Diamond Recipe!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
