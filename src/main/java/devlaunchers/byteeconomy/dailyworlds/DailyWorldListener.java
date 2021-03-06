package devlaunchers.byteeconomy.dailyworlds;

import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.inventory.ItemStack;


// TODO: Clear player inventory when changing to daily world
// TODO: Set health to full when changing to daily world
// TODO: Set hunger to full when changing to daily world


public class DailyWorldListener implements Listener {

    private boolean isDailyWorld(World world) {
        return world.getName().equals("daily-world");
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent e) {
        Player player = e.getPlayer();
        World world = player.getWorld();
        if (isDailyWorld(world)) {
            teleportPlayerToSpawn(player);
            clearPlayerInventory(player);
            setPlayerHealthFull(player);
            setPlayerFoodLevelFull(player);
        }
    }

    private void teleportPlayerToSpawn(Player player) {
        player.teleport(player.getWorld().getSpawnLocation());
    }

    private void clearPlayerInventory(Player player) {
        player.getInventory().setContents(new ItemStack[0]);
    }

    private void setPlayerHealthFull(Player player) {
        System.out.println("SETTING MAX HEALTH");
        double maxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
        player.setHealth(maxHealth);
    }

    private void setPlayerFoodLevelFull(Player player) {
        System.out.println("SETTING MAX FOOD");
        player.setFoodLevel(20);
    }
}
