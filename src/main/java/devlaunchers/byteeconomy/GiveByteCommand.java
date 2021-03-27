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

            int numBytes = 1;
            if (args[0] != null)
                numBytes = Integer.parseInt(args[0]);

            // Set the amount of the ItemStack
            byteItem.setAmount(numBytes);

            // Give the player our items (comma-seperated list of all ItemStack)
            player.getInventory().addItem(byteItem);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}