package devlaunchers.byteeconomy.dailyworlds;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.playerdatahandler.PlayerDataHandler;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Better way to identify daily NPCs

public class DailyNpcListener implements Listener {
    @EventHandler
    public void onNpcRightClick(PlayerInteractEntityEvent e) {
        Entity entity = e.getRightClicked();
        if (entity instanceof NPC) {
            switch (entity.getCustomName()) {
                case "Daily Jim":
                    e.setCancelled(true);
                    onDailyJimInteraction(e);
                    break;
                case "Daily Tim":
                    e.setCancelled(true);
                    onDailyTimInteraction(e);
                    break;
                default:
                    break;
            }
        }
    }

    private void onDailyJimInteraction(PlayerInteractEntityEvent e) {
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();

        // Save player inventory
        PlayerDataHandler playerDataHandler = new PlayerDataHandler();
        playerDataHandler.savePlayerInventory(e.getPlayer());

        // Teleport player to main world
        server.dispatchCommand(server.getConsoleSender(), "mv tp "+player.getName()+" world");

        Bukkit.getScheduler().scheduleSyncDelayedTask(ByteEconomy.getInstance(), () -> {
            DailyWorldManager.initNpcInMainWorld(player);
        }, 20*5);
    }

    private void onDailyTimInteraction(PlayerInteractEntityEvent e) {
        Server server = Bukkit.getServer();
        Player player = e.getPlayer();
        World world = player.getWorld();

        // Load player inventory
        PlayerDataHandler playerDataHandler = new PlayerDataHandler();
        ArrayList<ItemStack> playerInventoryStacks = playerDataHandler.loadPlayerInventory(player);
        ItemStack[] contents = new ItemStack[playerInventoryStacks.size()];
        contents = playerInventoryStacks.toArray(contents);
        HashMap<Integer, ItemStack> itemsNotAdded = player.getInventory().addItem(contents);
        // Poop out items on player that weren't added
        for(ItemStack itemStack : itemsNotAdded.values()) {
            if (itemStack != null)
                world.dropItem(player.getLocation(), itemStack);
        }

        // Play an effect and remove Daily Tim
        LivingEntity entity = (LivingEntity)e.getRightClicked();
        for (int i=0; i<10; i++) {
            Location smokeLocation = entity.getLocation();
            world.playEffect(new Location(
                    world,
                    smokeLocation.getX()+(Math.random()-.5),
                    smokeLocation.getY()+(Math.random()-.5),
                    smokeLocation.getZ()+(Math.random()-.5)
            ), Effect.SMOKE, 1);
        }
        //world.spawnEntity(entity.getLocation(), EntityType.FIREWORK);
        entity.remove();
    }
}
