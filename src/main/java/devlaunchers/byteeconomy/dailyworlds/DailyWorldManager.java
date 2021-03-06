package devlaunchers.byteeconomy.dailyworlds;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.multiversehandler.MultiverseHelper;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CommandBlock;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.Date;

// TODO: Insert compass into player inventory when they join world
// TODO: Daily streaks!
// TODO: Display time left in world in action bar, or use boss health boss bar - or make progress bar in action bar


// Possible daily game modes
// - Easter egg hunts
// - Protecting a certain starting item while hunting for another one
// - Trophy hunting (stats above entity head)
// - Treasure hunt ()
// - Huge towers you have to scale

// IDEA: "Jaily Dim" chases you around and tries to jail you

public class DailyWorldManager {

    private static final String MAIN_WORLD_NAME = "world";
    private static final String DAILY_WORLD_NAME = "daily-world";

    private static final int TOTAL_WORLD_LIFETIME = 60*5; // In seconds
    private static final int[] WARNING_INTERVALS = new int[]{ // In seconds
            TOTAL_WORLD_LIFETIME/2,
            TOTAL_WORLD_LIFETIME-60,
            TOTAL_WORLD_LIFETIME-30,
            TOTAL_WORLD_LIFETIME-10,
            TOTAL_WORLD_LIFETIME-5,
            TOTAL_WORLD_LIFETIME-4,
            TOTAL_WORLD_LIFETIME-3,
            TOTAL_WORLD_LIFETIME-2,
            TOTAL_WORLD_LIFETIME-1
    };

    public static void init() {
        // World creation task
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ByteEconomy.getInstance(), () -> {
            buildNewDailyWorld();
        }, 0, 20*TOTAL_WORLD_LIFETIME);
    }

    private static void buildNewDailyWorld() {

        System.out.println("{{{{{--------------------------}}}}}");
        System.out.println("{{{{{BUILDING A NEW DAILY WORLD}}}}}");
        System.out.println("{{{{{--------------------------}}}}}");

        // Tear down old daily-world, create new one
        MultiverseHelper.deleteWorld("daily-world", () -> {
            MultiverseHelper.createWorld("daily-world", () -> {
                initNpcInDailyWorld();
            });
        });

        // Setup world deletion warning alerts
        for (int intervalAmount : WARNING_INTERVALS) {
            Bukkit.getScheduler().scheduleSyncDelayedTask(ByteEconomy.getInstance(), () -> {
                System.out.println("Sending reminder message");
                World dailyWorld = Bukkit.getServer().getWorld(DAILY_WORLD_NAME);
                for (Player player : dailyWorld.getPlayers()) {
                    player.sendMessage("World destruction in "+(TOTAL_WORLD_LIFETIME-intervalAmount)+" seconds!");
                }
            }, 20*intervalAmount);
        }
    }

    private static void initNpcInDailyWorld() {
        World dailyWorld = Bukkit.getServer().getWorld(DAILY_WORLD_NAME);
        Location spawnLocation = dailyWorld.getSpawnLocation();
        spawnLocation.setX(spawnLocation.getX()+1);
        spawnLocation.setY(dailyWorld.getHighestBlockYAt(spawnLocation)+1);
        LivingEntity dailyNpc = (LivingEntity) dailyWorld.spawnEntity(spawnLocation, EntityType.VILLAGER);
        dailyNpc.setAI(false);
        dailyNpc.setCustomName("Daily Jim");
    }

    public static void initNpcInMainWorld(Player player) {
        World dailyWorld = Bukkit.getServer().getWorld(MAIN_WORLD_NAME);

        Location location = player.getLocation();
        location.setX(location.getX()+1);
        location.setY(dailyWorld.getHighestBlockYAt(location)+1);

        LivingEntity dailyNpc = (LivingEntity) dailyWorld.spawnEntity(location, EntityType.VILLAGER);
        dailyNpc.setAI(false);
        dailyNpc.setCustomName("Daily Tim");
    }
}
