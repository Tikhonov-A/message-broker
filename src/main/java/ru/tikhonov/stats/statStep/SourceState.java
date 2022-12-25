package ru.tikhonov.stats.statStep;

import java.util.List;

public class SourceState {
    public String id;
    public String state;

    public SourceState(String id, String state) {
        this.id = id;
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
