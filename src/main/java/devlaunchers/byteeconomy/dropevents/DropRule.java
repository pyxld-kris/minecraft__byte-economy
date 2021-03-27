package devlaunchers.byteeconomy.dropevents;

public class DropRule {
    private int chance;
    private int protectionRadius;
    private int protectionDuration;

    public DropRule(int chance, int protectionRadius, int protectionDuration) {
        this.chance = chance;
        this.protectionRadius = protectionRadius;
        this.protectionDuration = protectionDuration*1000; // Convert seconds to milliseconds
    }

    public int getChance() {
        return chance;
    }

    public int getProtectionRadius() {
        return protectionRadius;
    }

    public int getProtectionDuration() {
        return protectionDuration;
    }
}
