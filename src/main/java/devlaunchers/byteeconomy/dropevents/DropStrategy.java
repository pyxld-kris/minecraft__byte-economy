package devlaunchers.byteeconomy.dropevents;

import java.util.HashMap;

public class DropStrategy {
    HashMap<String, DropRule> filterMap = new HashMap<String, DropRule>();

    public DropStrategy() {}
    public DropStrategy(HashMap<String, DropRule> filterMap) {
        this.filterMap = filterMap;
    }

    public DropRule getDropRuleByType(Enum<?> key) {
        return filterMap.get(key.toString());
    }

    public DropStrategy addDropChanceEntry(Enum<?> filterType, DropRule dropRule) {
        filterMap.put(filterType.toString(), dropRule);
        return this;
    }

    public boolean checkShouldDrop(Enum<?> filter) {
        DropRule dropRule = filterMap.get(filter.toString());
        if (dropRule != null) {
            int chance = dropRule.getChance();
            if (Math.random() * 100 < chance) {
                return true;
            }
        }
        return false;
    }
}
