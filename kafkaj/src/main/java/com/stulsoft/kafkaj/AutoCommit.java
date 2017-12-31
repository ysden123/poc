package com.stulsoft.kafkaj;

/**
 * @author Yuriy Stul.
 */
public enum AutoCommit {
    EnabledAutoCommit(true),
    DisabledAutoCommit(false);
    private final boolean value;
    AutoCommit(final boolean value){
        this.value=value;
    }

    @Override
    public String toString() {
        return value? "true":"false";
    }
}
