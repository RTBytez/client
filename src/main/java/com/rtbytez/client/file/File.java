package com.rtbytez.client.file;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class File {

    private final HashMap<String, Line> lines = new HashMap<>();
    private final List<Line> linesInOrder = new ArrayList<>();

    public File() {

    }

    /**
     * Create a new line
     *
     * @param lineNumber Where this line is going to be stored
     * @return The line object that is created
     */
    public Line newLine(int lineNumber) {
        if (lineNumber == 0) {
            lineNumber = 1;
        }
        Line line = new Line("", lineNumber, "");
        lines.put(line.getId(), line);
        linesInOrder.add(lineNumber - 1, line);

        // Loop all lines that come after this one and add 1 to the line number
        for (int i = lineNumber; i < linesInOrder.size(); i++) {
            Line editingLine = linesInOrder.get(i);
            editingLine.setLineNumber(i + 1);
        }

        return line;
    }

    /**
     * Delete a line by it's assigned id
     *
     * @param id The id of the line to delete
     */
    public void deleteLineById(String id) {
        Line line = lines.get(id);
        lines.remove(id);
        linesInOrder.remove(line);

        // Loop all lines that come after this one and subtract 1 from the line number
        for (int i = line.getLineNumber(); i < linesInOrder.size(); i++) {
            Line editingLine = linesInOrder.get(i);
            editingLine.setLineNumber(i - 1);
        }

    }

    /**
     * Delete a line by it's line number
     *
     * @param lineNumber The line number to delete
     */
    public void deleteLineByLineNumber(int lineNumber) {
        Line line = linesInOrder.get(lineNumber);
        lines.remove(line.getId());
        linesInOrder.remove(lineNumber);

        // Loop all lines that come after this one and subtract 1 to the line number
        for (int i = line.getLineNumber(); i < linesInOrder.size(); i++) {
            Line editingLine = linesInOrder.get(i);
            editingLine.setLineNumber(i - 1);
        }

    }

    /**
     * Retrieve a line by it's id
     *
     * @param id The id of the line to retrieve
     * @return The line with that id
     */
    public Line getLineById(String id) {
        return lines.get(id);
    }

    /**
     * Retrieve a line by it's line number
     *
     * @param lineNumber The line number to retrieve
     * @return The line at that location
     */
    public Line getLineByLineNumber(int lineNumber) {
        return linesInOrder.get(lineNumber - 1);
    }

    /**
     * Take all the lines and build the file to a readable format
     *
     * @return The file as a string
     */
    public String buildFile() {
        if (!linesInOrder.isEmpty()) {
            StringBuilder stringBuilder = new StringBuilder();
            linesInOrder.forEach(line -> stringBuilder.append(line.toString()).append("\n"));
            String str = stringBuilder.toString();
            return str.substring(0, str.length() - 1);
        }
        return "";
    }

    /**
     * @see File#buildFile()
     */
    public String toString() {
        return buildFile();
    }

    /**
     * Retrieve the lines of a file
     *
     * @return The lines
     */
    public HashMap<String, Line> getLines() {
        return lines;
    }
}
