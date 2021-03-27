package devlaunchers.byteeconomy;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

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
class ByteDropLog {

    final int TRIM_INTERVAL = 1000*60; // In milliseconds

    private ArrayList<ByteDropLogEntry> logEntries = new ArrayList<ByteDropLogEntry>();

    public ByteDropLog() {
        beginTrimCheckTimer();
    }

    public void addByteDropEntry(Location location, DropRule dropRule) {
        logEntries.add(logEntries.size(), new ByteDropLogEntry(location, dropRule)); // appends to the end of the list
    }

    public boolean locationRangeExists(Location location, int range) {
        for (ByteDropLogEntry logEntry : logEntries) {
            if (logEntry.getLocation().distance(location) < range)
                return true;
        }
        return false;
    }

    private void beginTrimCheckTimer() {
        // Every X seconds, trim our log
        Bukkit.getScheduler().scheduleSyncRepeatingTask(ByteEconomy.getInstance(), () -> {
            trimLogEntries();
        }, 0,20*30);
    }

    private void trimLogEntries() {
        long currentTime = new Date().getTime();
        for (int i=0; i<logEntries.size(); i++) {
            ByteDropLogEntry logEntry = logEntries.get(i);
            if (logEntry.getDropTime() < currentTime-logEntry.getDropRule().getProtectionDuration()) {
                // trim!
                logEntries.remove(i);
                i--;
            }
        }
    }
}

class ByteDropLogEntry {
    private Location location;
    private DropRule dropRule;
    private long dropTime;

    public ByteDropLogEntry(Location location, DropRule dropRule) {
        this.location = location;
        this.dropRule = dropRule;
        this.dropTime = new Date().getTime();
    }

    public Location getLocation() {
        return location;
    }

    public DropRule getDropRule() {
        return dropRule;
    }

    public long getDropTime() {
        return dropTime;
    }
}