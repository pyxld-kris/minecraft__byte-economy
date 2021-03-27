package devlaunchers.byteeconomy.dropevents.monitoring;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.dropevents.DropRule;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Date;

public class ByteDropMonitor {

    // When a byte is dropped, log the:
    //   - Location of the drop
    //   - time of the drop
    //   - The player who caused the drop?

    // When deciding if we should drop another byte:
    //   - Check if a byte has been dropped in this location in the last 5 minutes

    private ByteDropLog byteDropLog = new ByteDropLog();

    public ByteDropMonitor() {}

    public boolean canDrop(Location location, DropRule dropRule) {
        return !byteDropLog.locationRangeExists(location, dropRule.getProtectionRadius());
    }

    public void byteDropped(Location location, DropRule dropRule) {
        byteDropLog.addByteDropEntry(location, dropRule);
    }
}

