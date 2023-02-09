package com.connect_four.app.ai;

import java.util.LinkedHashMap;
import java.util.Map;

public class TranspositionTable {

    public static final int MAX_SIZE = 1000000;
    private final LinkedHashMap<Integer, TableEntry> table;

    public TranspositionTable() {
        table = new LinkedHashMap<Integer, TableEntry>() {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, TableEntry> eldest) {
                return size() > MAX_SIZE;
            }
        };
    }

    public int size() {
        return table.size();
    }

    public void put(int key, TableEntry entry) {
        table.put(key, entry);
    }

    public TableEntry get(int key) {
        return table.get(key);
    }

    public boolean containsKey(int key) {
        return table.containsKey(key);
    }

}
