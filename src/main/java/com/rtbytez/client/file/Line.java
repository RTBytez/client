package com.rtbytez.client.file;

public class Line {

    private String id;
    private int lineNumber;
    private String text;
    private boolean flag;

    public Line(String id, int lineNumber, String text) {
        this.id = id;
        this.lineNumber = lineNumber;
        this.text = text;
    }

    /**
     * Get this line's ID
     *
     * @return The ID of the line
     */
    public String getId() {
        return id;
    }

    /**
     * Set this line's ID
     *
     * @param id The ID of the line
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Get this line's line number
     *
     * @return The line's line number
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * Set this line's line number
     *
     * @param lineNumber The number to set this line's line number to
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Get what text this line currently stores
     *
     * @return The text
     */
    public String getText() {
        return text;
    }

    /**
     * Set what this line's text is
     *
     * @param text The text to which to set this line's text to
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Is this line flagged for manual review due to an edit conflict?
     *
     * @return True if it is so
     */
    public boolean isFlagged() {
        return flag;
    }

    /**
     * Flag a line for manual review because there was a edit conflict
     *
     * @param flag True if to flag the line
     */
    public void flag(boolean flag) {
        this.flag = flag;
    }

    /**
     * What text is this line set to?
     *
     * @return The text of this line
     */
    public String toString() {
        return this.text;
    }
}
