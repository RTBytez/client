package com.rtbytez.client.trackers;

import com.rtbytez.common.util.Console;

import java.util.HashMap;
import java.util.Map;

public class FileModTracker {

    final Map<String, Long> cacheSet = new HashMap<>();

    public void addCache(String path, long mod) {
        cacheSet.put(path, mod);
        Console.log("CACHE", path + " = " + mod);
    }

    public boolean exists(String path, long mod) {
        if (cacheSet.containsKey(path)) {
            boolean b = cacheSet.get(path) == mod;
            Console.log("CACHE GET", path + " == " + mod + " ? " + b);
            return b;
        }
        Console.log("CACHE GET", path + " == " + mod + " ? false");
        return false;
    }
}
