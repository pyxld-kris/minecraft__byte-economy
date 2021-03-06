package devlaunchers.byteeconomy.playerdatahandler;

import devlaunchers.byteeconomy.ByteEconomy;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class InventoryLoadCommand implements CommandExecutor {

    private static final String SAVE_PATH = "ByteEconomy/playerInventory.yml";

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            /*
            if (args[0] != null) {
                player = Bukkit.getPlayerExact(args[0]);
                if (player == null) return false;
            }
             */

            PlayerDataHandler playerDataHandler = new PlayerDataHandler();
            ArrayList<ItemStack> playerInventoryStacks = playerDataHandler.loadPlayerInventory(player);

            // Convert ArrayList to array of ItemStacks
            ItemStack[] contents = new ItemStack[playerInventoryStacks.size()];
            contents = playerInventoryStacks.toArray(contents);

            player.getInventory().setContents(contents);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}
