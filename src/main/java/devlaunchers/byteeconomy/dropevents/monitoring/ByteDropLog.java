package devlaunchers.byteeconomy.dropevents.monitoring;

import devlaunchers.byteeconomy.ByteEconomy;
import devlaunchers.byteeconomy.dropevents.DropRule;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Date;

class ByteDropLog {

    final int TRIM_INTERVAL = 1000 * 60; // In milliseconds

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
        }, 0, 20 * 30);
    }

    private void trimLogEntries() {
        long currentTime = new Date().getTime();
        for (int i = 0; i < logEntries.size(); i++) {
            ByteDropLogEntry logEntry = logEntries.get(i);
            if (logEntry.getDropTime() < currentTime - logEntry.getDropRule().getProtectionDuration()) {
                // trim!
                logEntries.remove(i);
                i--;
            }
        }
    }
}
