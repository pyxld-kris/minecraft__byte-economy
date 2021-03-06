package devlaunchers.byteeconomy;

import devlaunchers.byteeconomy.dailyworlds.DailyNpcListener;
import devlaunchers.byteeconomy.dailyworlds.DailyWorldListener;
import devlaunchers.byteeconomy.dailyworlds.DailyWorldManager;
import devlaunchers.byteeconomy.playerdatahandler.InventoryLoadCommand;
import devlaunchers.byteeconomy.playerdatahandler.InventorySaveCommand;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: PVP challenges for bytes

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
        this.getCommand("saveInventory").setExecutor(new InventorySaveCommand());
        this.getCommand("loadInventory").setExecutor(new InventoryLoadCommand());

        getServer().getPluginManager().registerEvents(new ChestBytePopulator(), this);
        getServer().getPluginManager().registerEvents(new BlockBreakByteDropper(), this);
        getServer().getPluginManager().registerEvents(new DailyWorldListener(), this);
        getServer().getPluginManager().registerEvents(new DailyNpcListener(), this);

        DailyWorldManager.init();
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
