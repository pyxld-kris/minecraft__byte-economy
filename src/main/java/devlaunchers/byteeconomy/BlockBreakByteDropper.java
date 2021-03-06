package devlaunchers.byteeconomy;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.world.ChunkPopulateEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class BlockBreakByteDropper implements Listener {

    private static final int BYTE_DROP_CHANCE = 50;
    private static final ArrayList<Material> BLOCK_DROP_TYPES = new ArrayList<Material>(
            Arrays.asList(Material.STONE)
    );

    @EventHandler
    public void onChunkPopulate(BlockBreakEvent e) {
        Block block = e.getBlock();
        if (BLOCK_DROP_TYPES.contains(block.getType())) {
            if (Math.random()*100 < BYTE_DROP_CHANCE) {
                Location location = block.getLocation();

                ItemStack byteItem = ByteEconomy.getByteItem().clone();

                location.getWorld().dropItemNaturally(location, byteItem);
            }
        }
    }
}
