package devlaunchers.byteeconomy.items;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.items.DevLauncherItem;
import devlaunchers.items.ItemRepository;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;

public class RecipeManager {

    public RecipeManager() {
        //initDiamondToByteRecipe();
        initByteToDiamondRecipe();
    }

    private void initRecipe(String recipeKey, ItemStack result, String[] recipeShape, HashMap<String, ItemStack> ingredientItemKeys) {
        // create a NamespacedKey for your recipe
        NamespacedKey key = new NamespacedKey(ByteEconomy.getInstance(), recipeKey);

        // Create our custom recipe variable
        ShapedRecipe recipe = new ShapedRecipe(key, result);

        // Here we will set the places. E and S can represent anything, and the letters can be anything. Beware; this is case sensitive.
        recipe.shape(recipeShape[0], recipeShape[1], recipeShape[2]);

        // Set what the letters represent.
        for (String itemKey : ingredientItemKeys.keySet()) {
            recipe.setIngredient(itemKey.charAt(0), ingredientItemKeys.get(itemKey));
        }

        // Finally, add the recipe to the bukkit recipes
        ByteEconomy.getInstance().getServer().addRecipe(recipe);
    }

    /*
    private void initDiamondToByteRecipe() {
        ItemStack craftingResult = ItemManager.getByteItem().clone();
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
    */

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
                    put("B", ItemRepository.getItem(DevLauncherItem.ECONOMY_BYTE_ITEM));
                }}
        );

        System.out.println("Initialized Byte to Diamond Recipe!");
    }
}
