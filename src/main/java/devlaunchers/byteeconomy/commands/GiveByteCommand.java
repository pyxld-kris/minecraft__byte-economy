package devlaunchers.byteeconomy.commands;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.economy.Economy;
import devlaunchers.items.DevLauncherItem;
import devlaunchers.items.ItemRepository;

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

            int numBytes = 1;
            if (args.length > 0 && args[0] != null)
                numBytes = Integer.parseInt(args[0]);

            Economy.getInstance().giveMoney(player, numBytes);

            System.out.println("[ByteEconomy] [LOG] Gave byte$! Amount: " + numBytes);
        }

        // If the player (or console) uses our command correct, we can return true
        return true;
    }
}