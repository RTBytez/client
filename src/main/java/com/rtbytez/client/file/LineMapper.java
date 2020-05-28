package com.rtbytez.client.file;

import com.intellij.util.containers.BidirectionalMap;
import com.rtbytez.client.RTBytezClient;
import com.rtbytez.common.comms.packets.file.request.RTPFileRequestHash;
import com.rtbytez.common.util.Console;

import java.util.HashMap;

public class LineMapper {

    final HashMap<String, LineMap> lineMaps = new HashMap<>();

    public void check(String path) {
        if (!lineMaps.containsKey(path)) {
            lineMaps.put(path, new LineMap());
        }
    }

    public void map(String path, String lineId, int lineNumber) {
        check(path);
        lineMaps.get(path).map(lineId, lineNumber);
    }

    public int lineNumberOf(String path, String lineId) {
        check(path);
        try {
            return lineMaps.get(path).lineNumberOf(lineId);
        } catch (Error e) {
            Console.error("MAPPER", "Couldn't find map of " + e.getMessage() + " in file " + path);
            RTBytezClient.getInstance().getPeer().emit(new RTPFileRequestHash("file", path));
            return 0;
        }
    }

    public String lineIdOf(String path, int lineNumber) {
        check(path);
        try {
            return lineMaps.get(path).lineIdOf(lineNumber);
        } catch (Error e) {
            Console.error("MAPPER", "Couldn't find map of " + e.getMessage() + " in file " + path);
            RTBytezClient.getInstance().getPeer().emit(new RTPFileRequestHash("file", path));
            return "";
        }
    }

    public void addLine(String path, String lineId, int lineNumber) {
        check(path);
        lineMaps.get(path).addLine(lineId, lineNumber);
    }

    public void removeLine(String path, String lineId) {
        check(path);
        lineMaps.get(path).removeLine(lineId);
    }

    public void removeLine(String path, int lineNumber) {
        check(path);
        lineMaps.get(path).removeLine(lineNumber);
    }

    static class LineMap {
        final BidirectionalMap<String, Integer> map = new BidirectionalMap<>();

        public void map(String lineId, int lineNumber) {
            map.put(lineId, lineNumber);
        }

        public int lineNumberOf(String lineId) {
            if (!map.containsKey(lineId)) {
                throw new Error("LineId " + lineId);
            } else {
                return map.get(lineId);
            }
        }

        public String lineIdOf(int lineNumber) {
            if (!map.containsValue(lineNumber)) {
                throw new Error("lineNumber " + lineNumber);
            } else {
                //noinspection ConstantConditions - This case shouldn't exist
                return map.getKeysByValue(lineNumber).get(0);
            }
        }

        public void addLine(String lineId, int lineNumber) {
            map.forEach((loopLineId, loopLineNumber) -> {
                if (loopLineNumber >= lineNumber) {
                    map(loopLineId, loopLineNumber + 1);
                }
            });
            map.put(lineId, lineNumber);
        }

        public void removeLine(int lineNumber) {
            map.removeValue(lineNumber);
            map.forEach((loopLineId, loopLineNumber) -> {
                if (loopLineNumber > lineNumber) {
                    map(loopLineId, loopLineNumber - 1);
                }
            });
        }

        public void removeLine(String lineId) {
            int lineNumber = map.remove(lineId);
            map.forEach((loopLineId, loopLineNumber) -> {
                if (loopLineNumber > lineNumber) {
                    map(loopLineId, loopLineNumber - 1);
                }
            });
        }
    }

}
