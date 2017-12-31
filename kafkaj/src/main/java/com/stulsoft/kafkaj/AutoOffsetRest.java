package com.stulsoft.kafkaj;

/**
 * @author Yuriy Stul.
 */
public enum AutoOffsetRest {
    Earliest("earliest"),
    Latest("latest"),
    None("none");
    private final String value;

    AutoOffsetRest(final String value) {
        this.value = value;
    }


    @Override
    public String toString() {
        return value;
    }
}
