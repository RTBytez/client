package com.rtbytez.client.trackers;

import com.rtbytez.common.util.Console;

import java.util.HashMap;
import java.util.Map;

public class FileModTracker {

    Map<String, Long> cacheSet = new HashMap<>();

    public void addCache(String path, long mod) {
        cacheSet.put(path, mod);
        Console.log("CACHE", path + " = " + mod);
    }

    public boolean exists(String path, long mod) {
        if (cacheSet.containsKey(path)) {
            Console.log("CACHE GET", path + " == " + mod + " ? " + cacheSet.containsKey(path));
            return cacheSet.get(path) == mod;
        }
        Console.log("CACHE GET", path + " == " + mod + " ? false");
        return false;
    }
}
