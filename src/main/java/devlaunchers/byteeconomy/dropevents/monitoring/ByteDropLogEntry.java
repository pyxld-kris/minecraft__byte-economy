package devlaunchers.byteeconomy.dropevents.monitoring;

import devlaunchers.byteeconomy.dropevents.DropRule;
import org.bukkit.Location;

import java.util.Date;

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
