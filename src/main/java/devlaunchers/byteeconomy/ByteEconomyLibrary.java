package devlaunchers.byteeconomy;

import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import devlaunchers.economy.Economy;
import devlaunchers.items.DevLauncherItem;
import devlaunchers.items.ItemRepository;

public class ByteEconomyLibrary extends Economy {

  private ByteEconomyLibrary(Plugin economyPlugin) {
    super(economyPlugin);
  }

  public static Economy getInstance() {
    Economy economy = Economy.getInstance();
    if (economy == null) {
      return new ByteEconomyLibrary(ByteEconomy.getInstance());
    }
    return economy;
  }

  @Override
  public int getBalance(Player player) {
    ItemStack money = ItemRepository.getItem(DevLauncherItem.ECONOMY_BYTE_ITEM);

    return player
        .getInventory()
        .all(money)
        .values()
        .stream()
        .mapToInt((item) -> item.getAmount())
        .sum();
  }

  @Override
  public MoneyTransferResult giveMoney(Player player, int amount) {
    PlayerInventory inventory = player.getInventory();

    ItemStack money = ItemRepository.getItem(DevLauncherItem.ECONOMY_BYTE_ITEM).clone();

    HashMap<Integer, ItemStack> toFill = new HashMap<Integer, ItemStack>();

    for (int i = 0; i < inventory.getSize(); i++) {
      if (inventory.getItem(i) == null) {
        ItemStack clone = money.clone();
        if (amount > money.getType().getMaxStackSize()) {
          clone.setAmount(money.getType().getMaxStackSize());
          amount -= money.getType().getMaxStackSize();
        } else {
          clone.setAmount(amount);
          amount = 0;
        }
        toFill.put(i, clone);
      } else if (money.isSimilar(inventory.getItem(i))) {
        int iAmount = inventory.getItem(i).getAmount();
        if (iAmount < money.getType().getMaxStackSize()) {
          ItemStack clone = money.clone();
          if (iAmount + amount > money.getType().getMaxStackSize()) {
            clone.setAmount(money.getType().getMaxStackSize());
            toFill.put(i, clone);
            amount -= (money.getType().getMaxStackSize() - iAmount);
          } else {
            clone.setAmount(iAmount + amount);
            toFill.put(i, clone);
            amount = 0;
          }
        }
      }
    }
    if (amount > 0) {
      return MoneyTransferResult.INVENTORY_OVERFLOW;
    }
    toFill.forEach(
        (i, item) -> {
          inventory.setItem(i, item);
        });
    return MoneyTransferResult.SUCCESS;
  }

  @Override
  public MoneyTransferResult takeMoney(Player player, int amount) {
    if (getBalance(player) < amount) {
      return MoneyTransferResult.INSUFFICIENT_BALANCE;
    }
    _takeMoney(player, amount);
    return MoneyTransferResult.SUCCESS;
  }

  private void _takeMoney(Player player, int amount) {
    ItemStack money = ItemRepository.getItem(DevLauncherItem.ECONOMY_BYTE_ITEM);
    money.setAmount(amount);
    player.getInventory().remove(money);
  }

  @Override
  public MoneyTransferResult transferMoney(Player sender, Player receiver, int amount) {
    if (getBalance(sender) < amount) {
      return MoneyTransferResult.INSUFFICIENT_BALANCE;
    }
    if (giveMoney(receiver, amount) == MoneyTransferResult.INVENTORY_OVERFLOW) {
      return MoneyTransferResult.INVENTORY_OVERFLOW;
    }
    _takeMoney(sender, amount);
    return MoneyTransferResult.SUCCESS;
  }
}
