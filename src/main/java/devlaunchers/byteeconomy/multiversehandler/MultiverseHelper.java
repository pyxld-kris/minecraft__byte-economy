package devlaunchers.byteeconomy.multiversehandler;

import devlaunchers.byteeconomy.ByteEconomy;
import org.bukkit.Bukkit;
import org.bukkit.Server;

public class MultiverseHelper {

    public static void createWorld(String worldName) {
        createWorld(worldName, () ->{});
    }
    public static void createWorld(String worldName, Runnable callback) {
        System.out.println("CREATING WORLD");

        Server server = Bukkit.getServer();

        // Create new daily-world
        server.dispatchCommand(server.getConsoleSender(), "mv create "+worldName+" NORMAL");

        Bukkit.getScheduler().scheduleSyncDelayedTask(ByteEconomy.getInstance(), () -> {
            callback.run();
        }, 20*10);
    }

    public static void deleteWorld(String worldName) {
        deleteWorld(worldName, () ->{});
    }
    public static void deleteWorld(String worldName, Runnable callback) {
        System.out.println("DELETING WORLD");

        Server server = Bukkit.getServer();

        // Clean up any existing world with the name "daily-world"
        server.dispatchCommand(server.getConsoleSender(), "mv delete "+worldName);

        // Wait until the previous "daily-world" has been deleted
        Bukkit.getScheduler().scheduleSyncDelayedTask(ByteEconomy.getInstance(), () -> {
            // Create new daily-world
            server.dispatchCommand(server.getConsoleSender(), "mvconfirm");

            // Wait until the previous "daily-world" has been deleted
            Bukkit.getScheduler().scheduleSyncDelayedTask(ByteEconomy.getInstance(), () -> {
                callback.run();
            }, 20*12);
        }, 20*2);
    }

}
