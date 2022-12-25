package ru.tikhonov.stats.statStep;

public class BufferState {
    String requestId;

    public BufferState(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestId() {
        return requestId;
    }
}
