package ru.tikhonov.stats.statStep;

public class DeviceState {
    private final String id;
    private final String requestId;
    private final String state;
    private final String endTime;

    public DeviceState(String deviceId, String sourceNumber, String requestNumber, String state, String endTime) {
        this.id = deviceId;
        this.requestId = sourceNumber + "." + requestNumber;
        this.state = state;
        this.endTime = endTime;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    public String getEndTime() {
        return endTime;
    }
}
