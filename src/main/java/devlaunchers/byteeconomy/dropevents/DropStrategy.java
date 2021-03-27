package devlaunchers.byteeconomy.dropevents;

import java.util.HashMap;

public class DropStrategy {
    HashMap<Object, DropRule> filterMap = new HashMap<Object, DropRule>();

    public DropStrategy() {}
    public DropStrategy(HashMap filterMap) {
        this.filterMap = filterMap;
    }

    public DropRule getDropRuleByType(Object key) {
        return filterMap.get(key);
    }

    public DropStrategy addDropChanceEntry(Object filterType, DropRule dropRule) {
        filterMap.put(filterType, dropRule);
        return this;
    }

    public boolean checkShouldDrop(Object filter) {
        DropRule dropRule = filterMap.get(filter);
        if (dropRule != null) {
            int chance = dropRule.getChance();
            if (Math.random() * 100 < chance) {
                return true;
            }
        }
        return false;
    }
}
