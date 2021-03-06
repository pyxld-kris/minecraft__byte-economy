package devlaunchers.byteeconomy;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveByteCommand implements CommandExecutor {

    // This method is called, when somebody uses our command
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;

            // Create a new ItemStack (type: diamond)
            ItemStack byteItem = ByteEconomy.getByteItem().clone();

            // Set the amount of the ItemStack
            byteItem.setAmount(1);

            // Give the player our items (comma-seperated list of all ItemStack)
            player.getInventory().addItem(byteItem);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}