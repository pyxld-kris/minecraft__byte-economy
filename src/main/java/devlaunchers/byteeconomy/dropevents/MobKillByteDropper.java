package devlaunchers.byteeconomy.dropevents;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.dropevents.monitoring.ByteDropMonitor;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class MobKillByteDropper implements Listener {

    private ByteDropMonitor byteDropMonitor = new ByteDropMonitor();
    private DropStrategy dropStrategy;

    public MobKillByteDropper(DropStrategy dropChanceStrategy) {
        this.dropStrategy = dropChanceStrategy;
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        Entity entity = e.getEntity();
        EntityType entityType = entity.getType();
        Location location = entity.getLocation();

        DropRule dropRule = dropStrategy.getDropRuleByType(entityType);
        if (dropStrategy.checkShouldDrop(entity.getType()) && byteDropMonitor.canDrop(location, dropRule)) {
            ItemStack byteItem = ByteEconomy.getItemUtil().getByteItem().clone();
            location.getWorld().dropItemNaturally(location, byteItem);

            byteDropMonitor.byteDropped(location, dropRule); // Log this byte drop to prevent abuse
        }
    }
}
