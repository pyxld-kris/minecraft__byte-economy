package devlaunchers.byteeconomy.dropevents;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.dropevents.monitoring.ByteDropMonitor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreakByteDropper implements Listener {

    private ByteDropMonitor byteDropMonitor = new ByteDropMonitor();
    private DropStrategy dropStrategy;

    public BlockBreakByteDropper(DropStrategy dropChanceStrategy) {
        this.dropStrategy = dropChanceStrategy;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Block block = e.getBlock();
        Material material = block.getType();
        Player player = e.getPlayer();
        Location location = block.getLocation();

        DropRule dropRule = dropStrategy.getDropRuleByType(material);
        if (dropStrategy.checkShouldDrop(block.getType()) && byteDropMonitor.canDrop(location, dropRule) && !isHoldingSilkTouch(player)) {
            ItemStack byteItem = ByteEconomy.getItemUtil().getByteItem().clone();
            if (isHoldingFortune(player)) byteItem.setAmount(2);
            location.getWorld().dropItemNaturally(location, byteItem);

            byteDropMonitor.byteDropped(location, dropRule); // Log this byte drop to prevent abuse
        }
    }

    public boolean isHoldingSilkTouch(Player player) {
        if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            return true;
        }
        return false;
    }

    public boolean isHoldingFortune(Player player) {
        if (player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            return true;
        }
        return false;
    }
}
