package devlaunchers.byteeconomy.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import devlaunchers.byteeconomy.config.ByteEconomyConfig;

import java.util.ArrayList;

public class ItemManager {

    private static ItemStack byteItem;

    public ItemManager() {
        initByteItem();
    }

    public static ItemStack getByteItem() {
        return byteItem;
    }


    private void initByteItem() {
    	// Load Item from Config
    	this.byteItem = ByteEconomyConfig.getInstance().getByteItem();

//        // Our custom variable which we will be changing around.
//        ItemStack item = new ItemStack(Material.PAPER, 1);
//
//        // The meta of the paper where we can change the name, and properties of the item.
//        ItemMeta meta = item.getItemMeta();
//        meta.setCustomModelData(8);
//        meta.setDisplayName(ChatColor.GREEN + "Byte$");
//
//        // Set some lore
//        ArrayList<String> lore = new ArrayList<>();
//        lore.add(ChatColor.WHITE+"Money!");
//        meta.setLore(lore);
//
//        // Now add some flags
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//
//        // Add enchantments
//        //item.addEnchantment(Enchantment.DAMAGE_ALL, 5);
//
//
//        // Set the meta of the sword to the edited meta.
//        item.setItemMeta(meta);
//
//        this.byteItem = item;
    }

}
